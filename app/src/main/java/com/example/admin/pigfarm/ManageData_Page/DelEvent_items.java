package com.example.admin.pigfarm.ManageData_Page;

public class DelEvent_items {
    public String event_delname="";
    public String event_delrecorddate="";

    public DelEvent_items(String event_delname, String event_delrecorddate){
        this.event_delname = event_delname;
        this.event_delrecorddate = event_delrecorddate;
    }

    public String getevent_name() {
        return event_delname;
    }

    public String getevent_recorddate() {
        return event_delrecorddate;
    }

    public void setEvent_delname(String event_delname) {
        if (event_delname != null)
        this.event_delname = event_delname;
    }

    public void setEvent_delrecorddate(String event_delrecorddate) {
        if (event_delrecorddate != null)
        this.event_delrecorddate = event_delrecorddate;
    }
}
