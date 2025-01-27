package com.Project.core.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.LoggerFactory;

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=Custom Servlet",
        "sling.servlet.methods=" + HttpConstants.METHOD_POST,
        "sling.servlet.paths=" + "/bin/user/save"
})
public class PracticePostServletByPath extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void doPost(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws IOException {
        logger.error("User Details >>>>>>>>>>>>>>> {}", req.getParameter("dataXml"));

        StringBuilder sb = new StringBuilder();
        BufferedReader reader = req.getReader();

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }

        } finally {
            reader.close();
        }

        logger.error("PracticePostServletByPath >>>>>>> {}", sb.toString());

        resp.setStatus(SlingHttpServletResponse.SC_OK);
        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().print("{\"response message\":\" Service Called\"}");
    }

}
