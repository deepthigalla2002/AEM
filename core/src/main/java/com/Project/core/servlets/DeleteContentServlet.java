package com.Project.core.servlets;

import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;

import javax.jcr.Session;
import javax.servlet.Servlet;

import java.io.IOException;

import javax.jcr.Node;

@Component(service = Servlet.class, property = {
        "sling.servlet.methods=POST",
        "sling.servlet.paths=/bin/deleteContent"
})
public class DeleteContentServlet extends SlingAllMethodsServlet {

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        String id = request.getParameter("id");
        try {
            ResourceResolver resourceResolver = request.getResourceResolver();
            String path = "/content/Project/us/task11" + id; // Path to delete data from
            Resource resource = resourceResolver.getResource(path);
            if (resource != null) {
                Node node = resource.adaptTo(Node.class);
                Session session = resourceResolver.adaptTo(Session.class);
                if (session != null) {
                    node.remove();
                    session.save();
                    response.getWriter().write("Data deleted successfully!");
                }
            } else {
                response.getWriter().write("Data not found to delete.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.getWriter().write("Error deleting data: " + e.getMessage());
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }
}
