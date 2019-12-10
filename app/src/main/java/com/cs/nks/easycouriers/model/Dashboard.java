package com.cs.nks.easycouriers.model;
public class Dashboard {

    private String AssignedKilns;
    private String AssignedDate;
    private String VisitDate;
    private String SyncedDate;
    private boolean SyncStatus;


    public Dashboard(String assignedKilns, String assignedDate, String visitDate, String syncedDate, boolean syncStatus) {
      this.  AssignedKilns = assignedKilns;
        this.    AssignedDate = assignedDate;
        this.VisitDate = visitDate;
        this.SyncedDate = syncedDate;
        this.SyncStatus = syncStatus;
    }

    public String getAssignedKilns() {
        return AssignedKilns;
    }

    public void setAssignedKilns(String assignedKilns) {
        AssignedKilns = assignedKilns;
    }

    public String getAssignedDate() {
        return AssignedDate;
    }

    public void setAssignedDate(String assignedDate) {
        AssignedDate = assignedDate;
    }

    public String getVisitDate() {
        return VisitDate;
    }

    public void setVisitDate(String visitDate) {
        VisitDate = visitDate;
    }

    public String getSyncedDate() {
        return SyncedDate;
    }

    public void setSyncedDate(String syncedDate) {
        SyncedDate = syncedDate;
    }

    public boolean isSyncStatus() {
        return SyncStatus;
    }

    public void setSyncStatus(boolean syncStatus) {
        SyncStatus = syncStatus;
    }
}