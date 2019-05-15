
package com.cs.nks.easycouriers.model;


import java.io.Serializable;

public class ProjectPhoto implements Serializable {

    private int nuCommentId;
    private int ddlJobStatus;
    private int cl;
    private String dt;
    private String job;
    private String fsize;
    private String latestcom1;
    private int jobId;
    private String actiondate;
    private String createdate;
    private int nuApproveeditByClient;
    private String latestcom;
    private int fileid;
    private int jid;
    private int viewStatusForClient;
    private int nuClientId;
    private int nuRejectByClient;
    private Object snoozDate;
    private int snooz;
    private int nuApproveByClient;
    private int iNTFID;
    private int iNTFileID;
    private String vCHARHeading;
    private String fILENAMEQuestion;
    private String fILENAME;
    private String actionStatusOLD;
    private String actionStatus;
    private String descr;
    private String fileHeading;
    private int id;
    private String imgName;

    /**
     * No args constructor for use in serialization
     * 
     */


    /**
     * 
     * @param dt
     * @param jobId
     * @param createdate
     * @param iNTFID
     * @param fsize
     * @param viewStatusForClient
     * @param vCHARHeading
     * @param actionStatus
     * @param id
     * @param fILENAME
     * @param jid
     * @param actionStatusOLD
     * @param nuRejectByClient
     * @param latestcom
     * @param nuCommentId
     * @param snoozDate
     * @param fileHeading
     * @param actiondate
     * @param job
     * @param fileid
     * @param iNTFileID
     * @param imgName
     * @param snooz
     * @param nuApproveeditByClient
     * @param nuApproveByClient
     * @param fILENAMEQuestion
     * @param latestcom1
     * @param cl
     * @param ddlJobStatus
     * @param nuClientId
     * @param descr
     */
    public ProjectPhoto(int nuCommentId, int ddlJobStatus,
                        int cl, String dt, String job, String fsize,
                        String latestcom1, int jobId, String actiondate, String createdate,
                        int nuApproveeditByClient, String latestcom, int fileid, int jid,

                        int viewStatusForClient, int nuClientId, int nuRejectByClient,
                        Object snoozDate, int snooz, int nuApproveByClient,

                        int iNTFID, int iNTFileID, String vCHARHeading,

                        String fILENAMEQuestion, String fILENAME,
                        String actionStatusOLD, String actionStatus,
                        String descr, String fileHeading,
                        int id, String imgName) {
        super();
        this.nuCommentId = nuCommentId;
        this.ddlJobStatus = ddlJobStatus;
        this.cl = cl;
        this.dt = dt;
        this.job = job;
        this.fsize = fsize;
        this.latestcom1 = latestcom1;
        this.jobId = jobId;
        this.actiondate = actiondate;
        this.createdate = createdate;
        this.nuApproveeditByClient = nuApproveeditByClient;
        this.latestcom = latestcom;
        this.fileid = fileid;
        this.jid = jid;
        this.viewStatusForClient = viewStatusForClient;
        this.nuClientId = nuClientId;
        this.nuRejectByClient = nuRejectByClient;
        this.snoozDate = snoozDate;
        this.snooz = snooz;
        this.nuApproveByClient = nuApproveByClient;
        this.iNTFID = iNTFID;
        this.iNTFileID = iNTFileID;
        this.vCHARHeading = vCHARHeading;
        this.fILENAMEQuestion = fILENAMEQuestion;
        this.fILENAME = fILENAME;
        this.actionStatusOLD = actionStatusOLD;
        this.actionStatus = actionStatus;
        this.descr = descr;
        this.fileHeading = fileHeading;
        this.id = id;
        this.imgName = imgName;
    }

    public int getNuCommentId() {
        return nuCommentId;
    }

    public void setNuCommentId(int nuCommentId) {
        this.nuCommentId = nuCommentId;
    }

    public int getDdlJobStatus() {
        return ddlJobStatus;
    }

    public void setDdlJobStatus(int ddlJobStatus) {
        this.ddlJobStatus = ddlJobStatus;
    }

    public int getCl() {
        return cl;
    }

    public void setCl(int cl) {
        this.cl = cl;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getFsize() {
        return fsize;
    }

    public void setFsize(String fsize) {
        this.fsize = fsize;
    }

    public String getLatestcom1() {
        return latestcom1;
    }

    public void setLatestcom1(String latestcom1) {
        this.latestcom1 = latestcom1;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getActiondate() {
        return actiondate;
    }

    public void setActiondate(String actiondate) {
        this.actiondate = actiondate;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public int getNuApproveeditByClient() {
        return nuApproveeditByClient;
    }

    public void setNuApproveeditByClient(int nuApproveeditByClient) {
        this.nuApproveeditByClient = nuApproveeditByClient;
    }

    public String getLatestcom() {
        return latestcom;
    }

    public void setLatestcom(String latestcom) {
        this.latestcom = latestcom;
    }

    public int getFileid() {
        return fileid;
    }

    public void setFileid(int fileid) {
        this.fileid = fileid;
    }

    public int getJid() {
        return jid;
    }

    public void setJid(int jid) {
        this.jid = jid;
    }

    public int getViewStatusForClient() {
        return viewStatusForClient;
    }

    public void setViewStatusForClient(int viewStatusForClient) {
        this.viewStatusForClient = viewStatusForClient;
    }

    public int getNuClientId() {
        return nuClientId;
    }

    public void setNuClientId(int nuClientId) {
        this.nuClientId = nuClientId;
    }

    public int getNuRejectByClient() {
        return nuRejectByClient;
    }

    public void setNuRejectByClient(int nuRejectByClient) {
        this.nuRejectByClient = nuRejectByClient;
    }

    public Object getSnoozDate() {
        return snoozDate;
    }

    public void setSnoozDate(Object snoozDate) {
        this.snoozDate = snoozDate;
    }

    public int getSnooz() {
        return snooz;
    }

    public void setSnooz(int snooz) {
        this.snooz = snooz;
    }

    public int getNuApproveByClient() {
        return nuApproveByClient;
    }

    public void setNuApproveByClient(int nuApproveByClient) {
        this.nuApproveByClient = nuApproveByClient;
    }

    public int getINTFID() {
        return iNTFID;
    }

    public void setINTFID(int iNTFID) {
        this.iNTFID = iNTFID;
    }

    public int getINTFileID() {
        return iNTFileID;
    }

    public void setINTFileID(int iNTFileID) {
        this.iNTFileID = iNTFileID;
    }

    public String getVCHARHeading() {
        return vCHARHeading;
    }

    public void setVCHARHeading(String vCHARHeading) {
        this.vCHARHeading = vCHARHeading;
    }

    public String getFILENAMEQuestion() {
        return fILENAMEQuestion;
    }

    public void setFILENAMEQuestion(String fILENAMEQuestion) {
        this.fILENAMEQuestion = fILENAMEQuestion;
    }

    public String getFILENAME() {
        return fILENAME;
    }

    public void setFILENAME(String fILENAME) {
        this.fILENAME = fILENAME;
    }

    public String getActionStatusOLD() {
        return actionStatusOLD;
    }

    public void setActionStatusOLD(String actionStatusOLD) {
        this.actionStatusOLD = actionStatusOLD;
    }

    public String getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(String actionStatus) {
        this.actionStatus = actionStatus;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getFileHeading() {
        return fileHeading;
    }

    public void setFileHeading(String fileHeading) {
        this.fileHeading = fileHeading;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

}
