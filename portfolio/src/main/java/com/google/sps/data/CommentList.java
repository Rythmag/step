package com.google.sps.data;
import java.util.ArrayList;
import java.util.List;

public class CommentList {
  private final List<String> comments = new ArrayList<>();

  public void addComment( String newComment){
    comments.add(newComment);
  }
}