package com.Project.core.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.iterators.TransformIterator;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.commons.util.DamUtil;
import com.google.gson.Gson;

@SuppressWarnings("serial")
@Component(service = Servlet.class, property = {
        "sling.servlet.resourceTypes=/bin/dam-assets",
        "sling.servlet.methods=GET"
})
public class Demo extends SlingAllMethodsServlet {

    // Images update in crxde ...

    private static final Logger LOG = LoggerFactory.getLogger(Demo.class);

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        try {
            ResourceResolver resolver = request.getResourceResolver();
            Resource folderResource = resolver.getResource("/content/dam/images");

            if (folderResource == null) {
                LOG.error("Folder resource not found: /content/dam/images");
                response.setStatus(SlingHttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"Folder not found\"}");
                return;
            }

            Iterator<Resource> assets = folderResource.listChildren();
            Map<String, String> data = new TreeMap<>();

            while (assets.hasNext()) {
                Resource assetResource = assets.next();
                Asset asset = DamUtil.resolveToAsset(assetResource);

                if (asset != null) {
                    String assetPath = asset.getPath();
                    String assetName = asset.getName();
                    data.put(assetName, assetPath);
                }
            }

            // Check if the request is for JSON format
            String format = request.getParameter("format");
            if ("json".equalsIgnoreCase(format)) {
                // Return JSON response
                Gson gson = new Gson();
                String jsonData = gson.toJson(data);

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(jsonData);

            } else {
                // Default to DataSource for dropdown population
                DataSource ds = new SimpleDataSource(new TransformIterator<>(data.keySet().iterator(),
                        (Transformer<String, ValueMapResource>) o -> {
                            String dropValue = o;
                            ValueMap vm = new ValueMapDecorator(new HashMap<>());
                            vm.put("text", dropValue);
                            vm.put("value", data.get(dropValue));
                            return new ValueMapResource(resolver, new ResourceMetadata(), "nt:unstructured", vm);
                        }));

                request.setAttribute(DataSource.class.getName(), ds);
            }
        } catch (Exception e) {
            LOG.error("Error while fetching the data", e);
            response.setStatus(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Internal server error\"}");
        }
    }
}
