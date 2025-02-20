package com.Project.core.servlets;

import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;

import org.osgi.service.component.annotations.Component;
import javax.jcr.Node;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import java.io.IOException;

@Component(service = Servlet.class, property = {
        "sling.servlet.methods=" + HttpConstants.METHOD_POST,
        "sling.servlet.paths=/bin/createContent"
})
public class CreateContentServlet extends SlingAllMethodsServlet {
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        try {
            ResourceResolver resourceResolver = request.getResourceResolver();
            Session session = resourceResolver.adaptTo(Session.class);

            if (session != null) {
                String path = "/content/Project/us/task11/jcr:content";

                if (session.nodeExists(path)) {
                    Node jcrContent = session.getNode(path);
                    String name = jcrContent.hasProperty("name") ? jcrContent.getProperty("name").getString() : "n/a";
                    String email = jcrContent.hasProperty("email") ? jcrContent.getProperty("email").getString()
                            : "n/a";
                    String subject = jcrContent.hasProperty("subject") ? jcrContent.getProperty("subject").getString()
                            : "n/a";
                    String message = jcrContent.hasProperty("message") ? jcrContent.getProperty("message").getString()
                            : "n/a";

                    response.getWriter().write(String.format("Name: %s, Email: %s, Subject: %s, Message: %s",
                            name, email, subject, message));
                } else {
                    response.getWriter().write("jcr:content node does not exist!");
                }
            } else {
                response.getWriter().write("Session not found!");
            }
        } catch (Exception e) {
            response.getWriter().write("Error fetching data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        try {
            Session session = request.getResourceResolver().adaptTo(Session.class);
            if (session != null) {
                String path = "/content/Project/us/task11";
                Node rootNode = session.getNode(path);

                if (rootNode.hasNode("jcr:content")) {
                    Node jcrContent = rootNode.getNode("jcr:content");
                    jcrContent.setProperty("name", request.getParameter("name"));
                    jcrContent.setProperty("email", request.getParameter("email"));
                    jcrContent.setProperty("subject", request.getParameter("subject"));
                    jcrContent.setProperty("message", request.getParameter("message"));
                } else {
                    Node jcrContent = rootNode.addNode("jcr:content", "cq:PageContent");
                    jcrContent.setProperty("name", request.getParameter("name"));
                    jcrContent.setProperty("email", request.getParameter("email"));
                    jcrContent.setProperty("subject", request.getParameter("subject"));
                    jcrContent.setProperty("message", request.getParameter("message"));
                }

                session.save();
                response.getWriter().write("Data saved successfully!");
            } else {
                response.getWriter().write("Session not found!");
            }
        } catch (Exception e) {
            response.getWriter().write("Error saving data: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
