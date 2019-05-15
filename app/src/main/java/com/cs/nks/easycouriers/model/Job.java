package com.cs.nks.easycouriers.model;
public class Job {

    private String jobID;
    private String jobName;
    private String jobDesc;

    public String getJobID() {
        return jobID;
    }

    public String getJobName() {
        return jobName;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public Job(String jobID, String jobName, String jobDesc) {
        super();
        this.jobID = jobID;
        this.jobName = jobName;
        this.jobDesc = jobDesc;

    }


}