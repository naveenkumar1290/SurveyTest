package com.cs.nks.easycouriers.model;

import com.cs.nks.easycouriers.R;

/**
 * Created by Abhishek on 16/02/17.
 */

public class DamageDetail {
    public String   type_worker;
    public String   type_labor;
    public String   type_BrickKilns;
    public String    workAssigned;
    public String   name;
    public String   age;
    public String   gender;
    public String   Address;
    public String   comments;

    public String  UploadedPhotoUrl;
    public boolean RemoveIconVisible;


    public DamageDetail(String type_worker, String type_labor, String type_BrickKilns, String workAssigned, String name, String age, String gender, String address, String comments, String uploadedPhotoUrl, boolean removeIconVisible) {
        this.type_worker = type_worker;
        this.type_labor = type_labor;
        this.type_BrickKilns = type_BrickKilns;
        this.workAssigned = workAssigned;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this. Address = address;
        this.comments = comments;
        this. UploadedPhotoUrl = uploadedPhotoUrl;
        this. RemoveIconVisible = removeIconVisible;
    }

    public String getType_worker() {
        return type_worker;
    }

    public void setType_worker(String type_worker) {
        this.type_worker = type_worker;
    }

    public String getType_labor() {
        return type_labor;
    }

    public void setType_labor(String type_labor) {
        this.type_labor = type_labor;
    }

    public String getType_BrickKilns() {
        return type_BrickKilns;
    }

    public void setType_BrickKilns(String type_BrickKilns) {
        this.type_BrickKilns = type_BrickKilns;
    }

    public String getWorkAssigned() {
        return workAssigned;
    }

    public void setWorkAssigned(String workAssigned) {
        this.workAssigned = workAssigned;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getUploadedPhotoUrl() {
        return UploadedPhotoUrl;
    }

    public void setUploadedPhotoUrl(String uploadedPhotoUrl) {
        UploadedPhotoUrl = uploadedPhotoUrl;
    }

    public boolean isRemoveIconVisible() {
        return RemoveIconVisible;
    }

    public void setRemoveIconVisible(boolean removeIconVisible) {
        RemoveIconVisible = removeIconVisible;
    }

    public static DamageDetail addDamageDetail(){
      return  new DamageDetail("", "", "","","","","","","","",true);
    }
    public static DamageDetail getInitDamageDetail(){
        return  new DamageDetail("", "", "","","","","","","","",false);
    }


}