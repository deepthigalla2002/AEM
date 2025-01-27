package com.Project.core.servlets;

import java.io.IOException;
import java.security.SecureRandom;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;

@Component(service = Servlet.class, enabled = true, immediate = true)
@SlingServletPaths(value = "/bin/randomvalue")
public class Task5 extends SlingAllMethodsServlet {

  private static final SecureRandom RANDOM = new SecureRandom();

  private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

  @Override
  protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
      throws ServletException, IOException {

    String sessionparameter = request.getParameter("letters");
    String randomparameter = request.getParameter("numbers");
    String random = request.getParameter("random");

    response.setContentType("text/plain");

    if (sessionparameter != null) {
      String randomword = generaterandomword(6);
      response.getWriter().write("Randomword is = " + randomword);
      response.getWriter().write("\n");
    }

    if (randomparameter != null) {
      int randomnumber = RANDOM.nextInt(1000000);
      response.getWriter().write("RandowmNumber is = " + randomnumber);
      response.getWriter().write("\n");
    }

    if (random != null) {
      // Generate mixed output
      String mixedOutput = mixedData();
      response.getWriter().write("Mixed Output is = " + mixedOutput);

      response.getWriter().write("\n");
    }

    if (sessionparameter == null && randomparameter == null && random == null) {

      response.getWriter().write("Enter letters or numbers or random parameter");
    }

  }

  private String generaterandomword(int length) {
    StringBuilder word = new StringBuilder();
    for (int i = 0; i < length; i++) {
      word.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
    }
    return word.toString();
  }

  private String mixedData() {
    String randomWord = generaterandomword(3);
    int randomNumber = 0;
    for (int i = 0; i < 3; i++) {
      randomNumber = RANDOM.nextInt(999);
      // randomNumber.append(randomNumber);

    }
    return randomWord + randomNumber;

  }

}
