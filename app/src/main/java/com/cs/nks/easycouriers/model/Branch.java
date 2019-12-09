package com.cs.nks.easycouriers.model;

public class Branch {
  private   String branch_id;
    private   String branch_name;
    private   String mobile_number;
    private   String email;
    private   String address;
    private   String zip_code;

    public Branch(String branch_id, String branch_name, String mobile_number, String email, String address, String zip_code) {
        this.branch_id = branch_id;
        this.branch_name = branch_name;
        this.mobile_number = mobile_number;
        this.email = email;
        this.address = address;
        this.zip_code = zip_code;
    }

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }
}
