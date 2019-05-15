package com.cs.nks.easycouriers.model;

public class ClientUserAll {
    String userID ;
    String txt_Mail;
    String CompID ;
    String CompName ;
    String UserName ;
    String UserCategory ;
    String CaType ;
    String MasterName;
    String txt_Mobile;
    String report;
    String branch_id;



    public ClientUserAll(String userID, String txt_Mail, String compID,
                         String compName, String userName,
                         String userCategory, String caType,
                         String masterName, String txt_Mobile, String report, String branch_id) {
        this.userID = userID;
        this.txt_Mail = txt_Mail;
        this.CompID = compID;
        this.CompName = compName;
        this.UserName = userName;
        this.UserCategory = userCategory;
        this.CaType = caType;
        this. MasterName = masterName;
        this.txt_Mobile=txt_Mobile;
        this.report=report;
        this.branch_id=branch_id;

    }

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }
    public String getUserID() {
        return userID;
    }

    public String getTxt_Mail() {
        return txt_Mail;
    }

    public String getCompID() {
        return CompID;
    }

    public String getCompName() {
        return CompName;
    }

    public String getUserName() {
        return UserName;
    }

    public String getUserCategory() {
        return UserCategory;
    }

    public String getCaType() {
        return CaType;
    }

    public String getMasterName() {
        return MasterName;
    }
    public String gettxt_Mobile() {
        return txt_Mobile;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }
}
