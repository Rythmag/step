// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.google.sps.servlets;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.sps.data.Comment;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
/** Servlet that returns some example content. TODO: modify this file to handle comments data */




@WebServlet("/data")
public class DataServlet extends HttpServlet {
  final int defaultdisplayNumber = 10;
  final String statementProperty = "statement";
  final String commentEntityType = "Comment"; 
  final String timestampProperty = "timestamp";
  int displayNumber = defaultdisplayNumber;
  final String userProperty = "user";
  final UserService userService = UserServiceFactory.getUserService();
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json;");

    if(!userService.isUserLoggedIn()){
      System.err.println("user not logged in");
      return;
    }
    List<Comment> comments = new ArrayList<>();
    try{
      Query query = new Query(commentEntityType).addSort(timestampProperty, SortDirection.DESCENDING);
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      PreparedQuery  results = datastore.prepare(query);

      int countComments = 0;
      for (Entity entity : results.asIterable()){
        if(displayNumber == countComments){
          break;
        }
        countComments++;
        String statement = (String)entity.getProperty(statementProperty);
        long timestamp = (long)entity.getProperty(timestampProperty);
        String user = (String)entity.getProperty(userProperty);
        Comment newComment = new Comment(statement, timestamp, user);
        comments.add(newComment);
      }
    }catch (Exception e){
      System.err.println("Could not fetch comments. Error:" + e);
    }

    Gson gson = new Gson();
    String json = gson.toJson(comments);
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    if(!userService.isUserLoggedIn()){
      response.sendRedirect("/index.html");
      return;
    }
    //Post request adds a cooment and also decides the number of comments to be fetched for the user.
    displayNumber = getNumberOfComments(request);
    if(displayNumber == -1){
      displayNumber = defaultdisplayNumber;
    }
    String comment = getComment(request);
    //if comment is empty
    if(comment.length() == 0){
      response.sendRedirect("/index.html");
      return;
    }
    try{
      Entity commentEntity = new Entity(commentEntityType);
      commentEntity.setProperty(statementProperty, comment);
      long timestamp = System.currentTimeMillis();
      commentEntity.setProperty(timestampProperty, timestamp);
      String user = userService.getCurrentUser().getEmail();
      commentEntity.setProperty(userProperty, user);
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      datastore.put(commentEntity);
    }catch(Exception e){
      System.err.println("Could not add comment. Error: " + e);
    }
    response.sendRedirect("/index.html");
  }

  private String getComment(HttpServletRequest request){
    return request.getParameter("comment");
  }

  private int getNumberOfComments(HttpServletRequest request){
    String numString = request.getParameter("numberOfComments");
    int numberOfComments;
    try {
      numberOfComments = Integer.parseInt(numString);
    } catch (NumberFormatException e){
      System.err.println("Could not convert to int: " + numString);
      return -1;
    }

    if(numberOfComments < 0 || numberOfComments > 101 ){
      System.err.println("Out of range : " + numString);
      return -1;
    }
    return numberOfComments;
  }
}