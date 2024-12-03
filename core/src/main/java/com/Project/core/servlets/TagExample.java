package com.Project.core.servlets;

import java.io.IOException;
import java.util.Iterator;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;

@Component(service = Servlet.class,immediate = true,property={"sling.servlet.path=/bin/example/tag"})
public class TagExample extends SlingAllMethodsServlet {

    public void doGet(SlingHttpServletRequest req,SlingHttpServletResponse res) throws IOException
    {
        ResourceResolver resolver=req.getResourceResolver(); 
        TagManager tagManager=resolver.adaptTo(TagManager.class);
        Tag tag = tagManager.resolve("/content/cq:tags/learning/color");
        Iterator<Tag> listChildren =tag.listChildren();
        JsonObjectBuilder json =Json.createObjectBuilder();
        JsonArrayBuilder jsonArray=Json.createArrayBuilder();
        while(listChildren.hasNext())
        {
            Tag childTag = listChildren.next();
            json.add("title", childTag.getTitle());
            json.add("Path",childTag.getTitle());
            jsonArray.add(json);
        }
       
        res.getWriter().write(jsonArray.build().toString());
    }

}
