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
import com.google.gson.Gson;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@WebServlet("/delete-data")
public class DeleteDataServelet extends HttpServlet {
    final public String commentEntityType = "Comment";
    final UserService userService = UserServiceFactory.getUserService(); 
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(!userService.isUserLoggedIn()){
            response.sendRedirect("/index.html");
            System.err.println("Unauthorised user tried to delete data");
            return;
        }
        try{
            Query query = new Query(commentEntityType);
            DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
            PreparedQuery  results = datastore.prepare(query);

            for (Entity entity : results.asIterable()){
                Key k = entity.getKey();
                datastore.delete(k);
            }
        }catch(Exception e){
            System.err.println("Could not delete comments. Error: " + e);
        }finally{
            response.sendRedirect("/index.html");
        }
    }
}
