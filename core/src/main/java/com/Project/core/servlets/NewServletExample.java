package com.Project.core.servlets;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
//import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;


@Component(service = Servlet.class,immediate=true,
    property= { "sling.servlet.paths=/bin/slingmodelexample",
                "sling.servlet.selectors=one",
                "sling.servelt.extensions=json"
})


public class NewServletExample extends SlingAllMethodsServlet {

    public void doGet(SlingHttpServletRequest req,SlingHttpServletResponse res) throws IOException
    {
        res.setContentType("application/json");
        JsonObjectBuilder json=Json.createObjectBuilder();
        json.add("FirstName","Surge");
        json.add("CompanyName","Surge Software Solutions");
        json.add("URL","www.surge.com");
        
        res.getWriter().write(json.build().toString());
    }
    public void doPost(SlingHttpServletRequest req,SlingHttpServletResponse res) throws IOException
    {
        res.setContentType("application/json");
        JsonObjectBuilder json=Json.createObjectBuilder();
        json.add("FirstName","Surge");
        json.add("CompanyName","Surge Software Solutions");
        json.add("URL","www.surge.com");
        
        res.getWriter().write(json.build().toString());
    }
}
