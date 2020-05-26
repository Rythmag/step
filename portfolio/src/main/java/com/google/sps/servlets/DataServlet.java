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
/** Servlet that returns some example content. TODO: modify this file to handle comments data */




@WebServlet("/data")
public class DataServlet extends HttpServlet {
  int displayNumber = 10;
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query query = new Query("Comment").addSort("timestamp", SortDirection.DESCENDING);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery  results = datastore.prepare(query);
    
    // int totalComments = query.size();
    int countComments = 0;
    List<Comment> comments = new ArrayList<>();
    for (Entity entity : results.asIterable()){
      if(displayNumber == countComments){
        break;
      }
      countComments++;
      String statement = (String)entity.getProperty("statement");
      long timestamp = (long)entity.getProperty("timestamp");
      Comment newComment = new Comment(statement, timestamp);
      comments.add(newComment);
    }

    response.setContentType("application/json;");
    Gson gson = new Gson();
    String json = gson.toJson(comments);
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String comment = getComment(request);
    // comments.add(newComment);
    displayNumber = getNumberOfComments(request);
    if(displayNumber == -1){
      displayNumber = 10;
    }
    if(comment.length() == 0)
    {
      response.sendRedirect("/index.html");
      return;
    }
    Entity commentEntity = new Entity("Comment");
    commentEntity.setProperty("statement", comment);
    long timestamp = System.currentTimeMillis();
    commentEntity.setProperty("timestamp", timestamp);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(commentEntity);
    // response.setContentType("text/html");

    response.sendRedirect("/index.html");
  }

  private String getComment(HttpServletRequest request){
    String comment = request.getParameter("comment");
    return comment;
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
