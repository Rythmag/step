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

package com.google.sps;
import java.util.Collection;
import java.util.Collections;
import java.util.*; 
import com.google.gson.Gson;
// import com.google.sps.*;

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    // throw new UnsupportedOperationException("TODO: Implement this method.");
    List<TimeRange> blockedTimes = new ArrayList<>();
    Collection<TimeRange> availableSlots = new ArrayList<>();
    
    for(Event event : events){
      if(affectsTimeRangeByOptionalAttendees(event, request)){
        blockedTimes.add(event.getWhen());
      }
    }

    TimeRange.SortTimeRanges(blockedTimes);
    int duration = (int)request.getDuration();
    int curr_start = TimeRange.START_OF_DAY;
    int reqEnd = duration;
    for(TimeRange currEvent : blockedTimes){
      if(curr_start > currEvent.end()){
        continue;
      }
      // TimeRange newTR = TimeRange.fromStartEnd(curr_start, currEvent.start(), true);
      // if(currEvent.start() - curr_start >= duration && !currEvent.overlaps(newTR)){
      //   availableSlots.add(newTR);
      // }
      if(curr_start >= currEvent.start()){
        curr_start = currEvent.end();
        reqEnd = curr_start + duration;
        continue;
      }
      
      if(reqEnd <= currEvent.start()){
        TimeRange newTR = TimeRange.fromStartEnd(curr_start, currEvent.start(), false);
        availableSlots.add(newTR);
      }
      curr_start = currEvent.end();
      reqEnd = curr_start + duration;
    }
    if(reqEnd <= TimeRange.END_OF_DAY){
      TimeRange newTR = TimeRange.fromStartEnd(curr_start, TimeRange.END_OF_DAY, true);
        availableSlots.add(newTR);
    }

    if(availableSlots.size() > 0 || request.getAttendees().size() <= 0){
      return availableSlots;
    }
    return query2(events,request);

  }
  public Collection<TimeRange> query2(Collection<Event> events, MeetingRequest request) {
    // throw new UnsupportedOperationException("TODO: Implement this method.");
    List<TimeRange> blockedTimes = new ArrayList<>();
    Collection<TimeRange> availableSlots = new ArrayList<>();
    for(Event event : events){
      if(affectsTimeRange(event, request)){
        blockedTimes.add(event.getWhen());
      }
    }
    TimeRange.SortTimeRanges(blockedTimes);
    int duration = (int)request.getDuration();
    int curr_start = TimeRange.START_OF_DAY;
    int reqEnd = duration;
    for(TimeRange currEvent : blockedTimes){
      if(curr_start > currEvent.end()){
        continue;
      }
      // TimeRange newTR = TimeRange.fromStartEnd(curr_start, currEvent.start(), true);
      // if(currEvent.start() - curr_start >= duration && !currEvent.overlaps(newTR)){
      //   availableSlots.add(newTR);
      // }
      if(curr_start >= currEvent.start()){
        curr_start = currEvent.end();
        reqEnd = curr_start + duration;
        continue;
      }
      
      if(reqEnd <= currEvent.start()){
        TimeRange newTR = TimeRange.fromStartEnd(curr_start, currEvent.start(), false);
        availableSlots.add(newTR);
      }
      curr_start = currEvent.end();
      reqEnd = curr_start + duration;
    }
    if(reqEnd <= TimeRange.END_OF_DAY){
      TimeRange newTR = TimeRange.fromStartEnd(curr_start, TimeRange.END_OF_DAY, true);
        availableSlots.add(newTR);
    }
    return availableSlots;

  }
  private boolean affectsTimeRange(Event event, MeetingRequest request){
    Collection<String> attendees = request.getAttendees();
    Set<String> eventAttenders = event.getAttendees();
    for(String person : attendees){
      if(eventAttenders.contains(person)){
        return true;
      }
    }
    return false;
  }
  private boolean affectsTimeRangeByOptionalAttendees(Event event, MeetingRequest request){
    Collection<String> attendees = request.getAttendees();
    Set<String> eventAttenders = event.getAttendees();
    for(String person : attendees){
      if(eventAttenders.contains(person)){
        return true;
      }
    }
    // if(request.getOptionalAttendees() == null){
    //   System.err.println("-------------------------------------------------- list is empty" );
    // }
    try{
      Collection<String> optAttendees = optAttendees = request.getOptionalAttendees();
      for(String person : optAttendees){
        if(eventAttenders.contains(person)){
          return true;
        }
      }
      return false;
    }catch(Exception e){
      System.err.println("getOptionalAttendees() threw: " + e);
      return false;
    }
  }
}
