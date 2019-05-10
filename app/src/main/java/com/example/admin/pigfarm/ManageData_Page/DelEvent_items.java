package com.example.admin.pigfarm.ManageData_Page;

public class DelEvent_items {
    public  String detail_id="";
    public String event_delname="";
    public String event_delrecorddate="";

    public DelEvent_items(String detail_id,String event_delname, String event_delrecorddate){
        this.detail_id = detail_id;
        this.event_delname = event_delname;
        this.event_delrecorddate = event_delrecorddate;
    }

    public String getDetail_id() {
        return detail_id;
    }

    public String getevent_name() {
        return event_delname;
    }

    public String getevent_recorddate() {
        return event_delrecorddate;
    }

}
