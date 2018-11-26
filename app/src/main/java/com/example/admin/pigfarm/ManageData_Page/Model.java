package com.example.admin.pigfarm.ManageData_Page;



public class Model {
    private String pig_id;
    private String pig_no;


    public Model(String pig_id, String pig_no){
        this.pig_id = pig_id;
        this.pig_no = pig_no;
    }

    public String getPig_id() {
        return pig_id;
    }

    public String getPig_no() {
        return pig_no;
    }

}
