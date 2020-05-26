package com.google.sps.data;
import java.util.ArrayList;
import java.util.List;

public final class Comment {
  private String statement;
  private long timestamp;
  public Comment( String newComment, long timestamp){
    this.statement = newComment;
    this.timestamp = timestamp;
  }
}