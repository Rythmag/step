package com.google.sps.servlets;

import com.google.gson.Gson;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/user")
public class CheckLogin extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    

    UserService userService = UserServiceFactory.getUserService();
    if (userService.isUserLoggedIn()) {
        response.setContentType("application/json;");
        CurrentState currState = new CurrentState();
        currState.state = true;
        currState.correspondingURL = userService.createLogoutURL("/index.html");
        Gson gson = new Gson();
        String json = gson.toJson(currState);
        response.getWriter().println(json);
    } else {
        response.setContentType("application/json;");
        CurrentState currState = new CurrentState();
        currState.state = false;
        currState.correspondingURL = userService.createLoginURL("/index.html");
        Gson gson = new Gson();
        String json = gson.toJson(currState);
        response.getWriter().println(json);
    }
  }
  private class CurrentState{
      boolean state = false;
      String correspondingURL;
  }
}
