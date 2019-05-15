package com.cs.nks.easycouriers.model;

public class State {

    public State(String state_Id_Pk, String state_Name) {
        State_Id_Pk = state_Id_Pk;
        State_Name = state_Name;
    }

    String State_Id_Pk;

    public String getState_Id_Pk() {
        return State_Id_Pk;
    }

    public String getState_Name() {
        return State_Name;
    }

    @Override
    public String toString() {
        return  State_Name ;

    }

    String State_Name;


}