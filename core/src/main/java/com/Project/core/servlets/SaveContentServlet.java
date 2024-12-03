package com.Project.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;

import javax.jcr.Node;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class, property = {
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
        "sling.servlet.paths=/bin/saveContent"
})
public class SaveContentServlet extends SlingAllMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get the ResourceResolver and Session
            ResourceResolver resourceResolver = request.getResourceResolver();
            Session session = resourceResolver.adaptTo(Session.class);

            if (session != null) {
                // Path to create the node
                String parentPath = "/content/Project/us/task11/jcr:content/root/container";

                // Check if the parent node exists
                if (session.nodeExists(parentPath)) {
                    Node parentNode = session.getNode(parentPath);

                    // Create a unique child node
                    Node newNode = parentNode.addNode("entry" + System.currentTimeMillis(), "nt:unstructured");

                    // Add properties
                    newNode.setProperty("name", request.getParameter("name"));
                    newNode.setProperty("email", request.getParameter("email"));
                    newNode.setProperty("subject", request.getParameter("subject"));
                    newNode.setProperty("message", request.getParameter("message"));

                    // Save changes
                    session.save();

                    response.getWriter().write("Data saved successfully!");
                } else {
                    response.getWriter().write("Parent node does not exist: " + parentPath);
                }
            } else {
                response.getWriter().write("Session not found!");
            }
        } catch (Exception e) {
            response.getWriter().write("Error saving data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
