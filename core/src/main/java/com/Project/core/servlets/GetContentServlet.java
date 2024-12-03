package com.Project.core.servlets;

import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;

import com.google.gson.Gson;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.servlet.Servlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component(service = Servlet.class, property = {
        "sling.servlet.methods=GET",
        "sling.servlet.paths=/bin/getContent"
})
public class GetContentServlet extends SlingAllMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        List<HashMap<String, String>> contentList = new ArrayList<>();
        try {
            ResourceResolver resourceResolver = request.getResourceResolver();
            String path = "/content/Project/us/task11"; // Path to fetch data from
            Resource resource = resourceResolver.getResource(path);
            if (resource != null) {
                Node node = resource.adaptTo(Node.class);
                if (node != null) {
                    NodeIterator nodes = node.getNodes();
                    while (nodes.hasNext()) {
                        Node itemNode = nodes.nextNode();
                        HashMap<String, String> content = new HashMap<>();

                        // Add null checks for properties
                        content.put("id", itemNode.getName());
                        content.put("name",
                                itemNode.hasProperty("name") ? itemNode.getProperty("name").getString() : "N/A");
                        content.put("email",
                                itemNode.hasProperty("email") ? itemNode.getProperty("email").getString() : "N/A");
                        content.put("subject",
                                itemNode.hasProperty("subject") ? itemNode.getProperty("subject").getString() : "N/A");
                        content.put("message",
                                itemNode.hasProperty("message") ? itemNode.getProperty("message").getString() : "N/A");

                        contentList.add(content);
                    }
                }
            } else {
                response.setStatus(SlingHttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"Resource not found at path: " + path + "\"}");
                return;
            }

            // Write JSON response
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(contentList));

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Error fetching data. Please check server logs for details.\"}");
        }
    }
}
