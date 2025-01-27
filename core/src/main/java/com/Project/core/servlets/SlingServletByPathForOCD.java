package com.Project.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.Project.core.services.PracticeOCDService;

import javax.annotation.Resource;
import javax.servlet.Servlet;
import java.io.IOException;

@Component(service = Servlet.class, property = {
        Constants.SERVICE_DESCRIPTION + "=Custom Servlet",
        "sling.servlet.methods=" + HttpConstants.METHOD_POST,
        "sling.servlet.paths=" + "/bin/practice/ocd"
})
public class SlingServletByPathForOCD extends SlingSafeMethodsServlet {

    private static final long serialVersionUID = 1L;

    @Reference
    PracticeOCDService practiceOCDService;

    @Resource(name = "BundleContext")
    private BundleContext context;

    @Override
    protected void doGet(final SlingHttpServletRequest req,
            final SlingHttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");

        resp.getWriter().write("Hello World, " + practiceOCDService.getName() +
                ", Country at index 1 = " + practiceOCDService.getCountries()[1]);
    }
}
