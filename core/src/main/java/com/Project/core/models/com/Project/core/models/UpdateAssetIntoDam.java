package com.Project.core.models.com.Project.core.models;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.AssetManager;
import com.drew.lang.annotations.NotNull;
import org.apache.commons.lang.StringUtils;
import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import java.io.File;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.*;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.io.InputStream;

import java.util.Collections;
import java.util.Dictionary;
import com.day.cq.dam.api.DamConstants;
import com.day.cq.commons.jcr.JcrConstants;

@Component ( service = Servlet.class, property = {
        Constants.SERVICE_DESCRIPTION + " = This servlet is used to create or update file in dam from Kafka consumer.",
        "sling.servlet.paths=" + "/bin/tools/updateOrCreateCreditCardPricingFromKafka",
        "sling.servlet.methods=" + HttpConstants.METHOD_POST
} )
public class UpdateAssetIntoDam extends SlingAllMethodsServlet{

    private  static final transient Logger LOG = LoggerFactory.getLogger(UpdateAssetIntoDam.class);
    @SuppressWarnings("unused")
    private static final String CONFIG_PID = "com.responsive.tool.config.CreditCardPricingJsonUploadDetail";
    private transient Dictionary<String, Object> props = null;

    @Reference
    private transient ResourceResolverFactory resolverFactory;

    @Reference
    private transient ConfigurationAdmin configurationAdmin;

    @Override
    protected
    void doPost( SlingHttpServletRequest request, SlingHttpServletResponse response ) throws ServletException, IOException {

        try (ResourceResolver resourceResolver = resolverFactory.getServiceResourceResolver(
                Collections.singletonMap(ResourceResolverFactory.SUBSERVICE, "kafkaUser"))) {

            String token = request.getHeader("lock-token");
            String brand = request.getParameter("brand") != null ? request.getParameter("brand") : "temp.json";
            LOG.debug("brand value is {}", brand);
            props = configurationAdmin.getConfiguration(CONFIG_PID).getProperties();
            if( ! checkForAuthentication(token) ) {
                response.getWriter().write("Invalid token");
                return;
            }

            InputStream fileStream = request.getInputStream();
            String kafkaJsonFileRootPath = StringUtils.isEmpty(String.valueOf(props.get("kafkaToAEMJsonFileRootPath"))) ? String.valueOf(props.get("kafkaToAEMJsonFileRootPath")) : "/content/dam/strategic-pricing";

            String destinationPath = kafkaJsonFileRootPath + File.separator + brand + ".json";


            String uploadedFilePath = createOrUpdateFile( fileStream, destinationPath, resourceResolver);
            response.setContentType("application/json");
            response.getWriter().write("File uploaded successfully to path.. " + uploadedFilePath);
        }
        catch ( Exception e ) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error uploading file: " + e.getMessage());
        }
    }

    private
    String createOrUpdateFile( InputStream is, String path, ResourceResolver resourceResolver ) throws RepositoryException {
        Session session = resourceResolver.adaptTo(Session.class);
        ValueFactory valueFactory = session.getValueFactory();
        Binary bin = valueFactory.createBinary(is);
        @NotNull Node damPath = JcrUtils.getOrCreateByPath(path, false, null, DamConstants.NT_DAM_ASSET, session, false);
        @NotNull AssetManager assetManager = resourceResolver.adaptTo(AssetManager.class);
        @NotNull Asset asset = assetManager.createOrUpdateAsset(damPath.getPath(), bin, "application/json", true);
        Node assetNode = asset.adaptTo(Node.class);
        Node jcrContentNode = assetNode.getNode(JcrConstants.JCR_CONTENT);
        jcrContentNode.setProperty(DamConstants.PN_NAME,assetNode.getName());
        JcrUtils.getOrAddNode(jcrContentNode, "metadata");
        LOG.debug("new node=={}", damPath.getPath());

        session.save();
        return damPath.getPath();
    }

    private
    boolean checkForAuthentication( String tokenFromReq )  {
        @NotNull String tokenFromAEM = props != null ? String.valueOf(props.get("token")) : null;
        LOG.debug("Token from AEM {}", tokenFromAEM);
        LOG.debug("Token from Request{}", tokenFromReq);
        return StringUtils.equalsIgnoreCase(tokenFromAEM, tokenFromReq);

    }
}