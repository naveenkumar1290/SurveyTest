package com.cs.nks.easycouriers.model;
public class City {

    private String City_Id_Pk;
    private String State_Id;
    private String City_Name;
    private String IsDelete;

    public City(String city_Id_Pk, String state_Id, String city_Name, String isDelete) {
        City_Id_Pk = city_Id_Pk;
        State_Id = state_Id;
        City_Name = city_Name;
        IsDelete = isDelete;
    }

    public String getCity_Id_Pk() {
        return City_Id_Pk;
    }

    public String getState_Id() {
        return State_Id;
    }

    public String getCity_Name() {
        return City_Name;
    }

    public String getIsDelete() {
        return IsDelete;
    }

    @Override
    public String toString() {
        return  City_Name;
    }
}