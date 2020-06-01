package com.google.sps.data;
import java.util.ArrayList;
import java.util.List;

public final class Comment {
  private String statement;
  private long timestamp;
  private String user;
  public Comment( String newComment, long timestamp, String user){
    this.statement = newComment;
    this.timestamp = timestamp;
    this.user = user;
  }
}