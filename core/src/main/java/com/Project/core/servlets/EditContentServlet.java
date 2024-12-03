package com.Project.core.servlets;

import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.Resource;
import org.osgi.service.component.annotations.Component;

import javax.jcr.Node;
import javax.jcr.Session;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

@Component(service = SlingAllMethodsServlet.class, property = {
        "sling.servlet.methods=POST",
        "sling.servlet.paths=/bin/editContent"
})
public class EditContentServlet extends SlingAllMethodsServlet {

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        String nodeId = request.getParameter("nodeId"); // Unique identifier for the node to edit
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String subject = request.getParameter("subject");
        String message = request.getParameter("message");

        try {
            ResourceResolver resourceResolver = request.getResourceResolver();
            Session session = resourceResolver.adaptTo(Session.class);

            if (session != null) {
                String path = "/content/Project/us/task11" + nodeId; // Construct path to node
                Resource resource = resourceResolver.getResource(path);

                if (resource != null) {
                    Node node = resource.adaptTo(Node.class);
                    if (node != null) {
                        // Update node properties
                        node.setProperty("name", name);
                        node.setProperty("email", email);
                        node.setProperty("subject", subject);
                        node.setProperty("message", message);
                        session.save();
                        response.getWriter().write("Data updated successfully!");
                    } else {
                        response.getWriter().write("Node not found at path: " + path);
                    }
                } else {
                    response.getWriter().write("Resource not found at path: " + path);
                }
            } else {
                response.getWriter().write("Session is null!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.getWriter().write("Error updating data: " + e.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
