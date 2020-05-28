package com.google.sps.servlets;
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
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;


@WebServlet("/delete-data")
public class DeleteDataServelet extends HttpServlet {
    const String commentEntityType = "Comment"; 
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Query query = new Query(commentEntityType);
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery  results = datastore.prepare(query);

        for (Entity entity : results.asIterable()){
            Key k = entity.getKey();
            datastore.delete(k);
        }
        response.sendRedirect("/index.html");
    }
}