package com.Project.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service = { Servlet.class }, property = {
        "sling.servlet.paths=/bin/project",
        "sling.servlet.method=GET",
        "sling.servlt.selectors={one,two}",
        "sling.servlet.extensions=txt" })
public class SlingServletByPath extends SlingSafeMethodsServlet {

    @Reference
    com.Project.core.services.PracticeService PracticeService;
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        String[] selectors = req.getRequestPathInfo().getSelectors();

        if (ArrayUtils.isNotEmpty(selectors) && selectors.length > 1) {
            resp.getWriter().write("Selectors Size =" + selectors.length + ", Selector value = " + selectors[0]);
        } else {
            resp.getWriter().write("Hello World !!!!!!!!");
        }
    }
}
