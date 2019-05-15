package com.cs.nks.easycouriers.model;
public class Geo {

    private String lat;
    private String lng;
    private String address;
    private String branch_Id;
    private String branch_Name;

    public Geo(String lat, String lng, String address, String branch_Id, String branch_Name) {
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        this.branch_Id = branch_Id;
        this.branch_Name = branch_Name;
    }

    public String getBranch_Id() {
        return branch_Id;
    }

    public void setBranch_Id(String branch_Id) {
        this.branch_Id = branch_Id;
    }

    public String getBranch_Name() {
        return branch_Name;
    }

    public void setBranch_Name(String branch_Name) {
        this.branch_Name = branch_Name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}