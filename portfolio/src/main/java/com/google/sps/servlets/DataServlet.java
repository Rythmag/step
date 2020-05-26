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
  
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query query = new Query("Comment");
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery  results = datastore.prepare(query);

    List<Comment> comments = new ArrayList<>();
    for (Entity entity : results.asIterable()){
      String statement = (String)entity.getProperty("statement");
      Comment newComment = new Comment(statement);
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

    Entity commentEntity = new Entity("Comment");
    commentEntity.setProperty("statement", comment);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(commentEntity);
    // response.setContentType("text/html");

    response.sendRedirect("/index.html");
  }

  private String getComment(HttpServletRequest request){
    String comment = request.getParameter("comment");
    // if(comment.length() < 0){
      // System.err.println("Comment is not valid");
      // return -1;
    // }
    return comment;
  }
}
