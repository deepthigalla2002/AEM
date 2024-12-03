package com.Project.core.servlets;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;

@Component(
    service = Servlet.class,
    property = {
        "sling.servlet.resourceTypes=Project/components/resource", // Corrected resource type
        "sling.servlet.methods=GET", // Defined GET and POST methods separately
        "sling.servlet.methods=POST",
        "sling.servlet.extensions=json" // Optional: If you need this servlet to respond only to .json
    }
)
public class ResourceBasedServlet extends SlingAllMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest req, SlingHttpServletResponse res) throws IOException {
        res.setContentType("application/json");
        JsonObjectBuilder json = Json.createObjectBuilder();
        json.add("FirstName", "Surge");
        json.add("CompanyName", "Surge Software Solutions");
        json.add("URL", "www.surge.com");

        res.getWriter().write(json.build().toString());
    }

    @Override
    protected void doPost(SlingHttpServletRequest req, SlingHttpServletResponse res) throws IOException {
        res.setContentType("application/json");
        JsonObjectBuilder json = Json.createObjectBuilder();
        json.add("FirstName", "Surge");
        json.add("CompanyName", "Surge Software Solutions");
        json.add("URL", "www.surge.com");

        res.getWriter().write(json.build().toString());
    }
}
