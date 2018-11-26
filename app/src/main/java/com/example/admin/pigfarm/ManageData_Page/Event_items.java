package com.example.admin.pigfarm.ManageData_Page;

public class Event_items {

    public String eventname ="";
    public String event_recorddate= "";

    public Event_items(String eventname, String event_recorddate){
        this.eventname = eventname;
        this.event_recorddate = event_recorddate;
    }

    public String getEventname() {
        return eventname;
    }

    public String getEvent_recorddate() {
        return event_recorddate;
    }

}
