package com.Project.core.servlets;

import java.io.IOException;
import java.util.*;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.event.jobs.JobManager;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.*;

public class SlingServletByPath2 extends SlingAllMethodsServlet {

    @Reference
    private JobManager jobManager;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws IOException {
        final Map<String, Object> props = new HashMap<>();
        props.put("data", "test");
        jobManager.addJob("practice/job", props);

        logger.error("Practice Servlet called *******");

        resp.setStatus(SlingHttpServletResponse.SC_OK);
        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().print("{\"response message\" : \"Servlet Called\"}");

    }

}
