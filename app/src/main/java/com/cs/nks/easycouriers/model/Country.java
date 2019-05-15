package com.cs.nks.easycouriers.model;
public class Country {

    private String Country_Id_Pk;

    public Country(String country_Id_Pk, String country_Name) {
        Country_Id_Pk = country_Id_Pk;
        Country_Name = country_Name;
    }

    private String Country_Name;
    public String getCountry_Id_Pk() {
        return Country_Id_Pk;
    }

    public void setCountry_Id_Pk(String country_Id_Pk) {
        Country_Id_Pk = country_Id_Pk;
    }

    public String getCountry_Name() {
        return Country_Name;
    }

    public void setCountry_Name(String country_Name) {
        Country_Name = country_Name;
    }

    @Override
    public String toString() {
        return  Country_Name;
    }
}