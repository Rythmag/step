<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<% BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
   String uploadUrl = blobstoreService.createUploadUrl("/my-form-handler"); %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Random  Images</title>
    <script src="blob.js"></script>
  </head>
  <body onload="fetchImages()">
    <h1>Random  Images</h1>
    <p>Type a message and click submit:</p>

    <form method="POST" enctype="multipart/form-data" action="<%= uploadUrl %>">
      <p>Type some text:</p>
      <textarea name="message"></textarea>
      <br/>
      <p>Upload an image:</p>
      <input type="file" name="image">
      <br/><br/>
      <button>Submit</button>
    </form>
    <div id="image-container">

    </div>
  </body>
</html>