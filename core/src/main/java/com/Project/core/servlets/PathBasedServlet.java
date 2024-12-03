package com.Project.core.servlets;

import java.io.IOException;
import java.util.Random;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;

@Component(service = Servlet.class, property = {
        "sling.servlet.paths=/bin/randomword",
        "sling.servlet.methods=GET"
})
public class PathBasedServlet extends SlingAllMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        String action = request.getParameter("action");
        String randomWord = generateRandomWord();

        if ("session".equalsIgnoreCase(action)) {

            request.getSession(true).setAttribute("randomWord", randomWord);
            response.getWriter().write("{\"Random word when input is session\": \"" + randomWord + "\"}");
        } else if ("random".equalsIgnoreCase(action)) {

            response.getWriter().print("{\"Random word when input is random\": \"" + randomWord + "\"}");
        } else {

            response.getWriter().write("{\"error\": \"Invalid action parameter. Use 'session' or 'random'\"}");
        }
    }

    private String generateRandomWord() {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        String Numbers = "1234567890";
        Random random = new Random();
        StringBuilder word = new StringBuilder(6);

        for (int i = 0; i < 3; i++) {
            word.append(letters.charAt(random.nextInt(letters.length())));
        }
        for (int i = 0; i < 3; i++) {
            word.append(Numbers.charAt(random.nextInt(Numbers.length())));
        }

        return word.toString();
    }
}
