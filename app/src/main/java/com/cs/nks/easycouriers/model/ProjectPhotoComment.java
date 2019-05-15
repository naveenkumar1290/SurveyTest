
package com.cs.nks.easycouriers.model;


public class ProjectPhotoComment {

    private Integer jobid;
    private Integer id;
    private Integer fileid;
    private Integer clientid;
    private Integer clientVSt;
    private Integer vrCommentBy;
    private Integer idPk;
    private String uploadDate;
    private String uploadedBy;
    private String comment;
    private String status;
    private String imgname;
    private String fileUrl;

    /**
     * No args constructor for use in serialization
     * 
     */

    /**
     * 
     * @param uploadedBy
     * @param status
     * @param fileid
     * @param jobid
     * @param idPk
     * @param fileUrl
     * @param clientVSt
     * @param id
     * @param clientid
     * @param vrCommentBy
     * @param imgname
     * @param comment
     * @param uploadDate
     */
    public ProjectPhotoComment(Integer jobid, Integer id, Integer fileid, Integer clientid, Integer clientVSt, Integer vrCommentBy, Integer idPk, String uploadDate, String uploadedBy, String comment, String status, String imgname, String fileUrl) {
        super();
        this.jobid = jobid;
        this.id = id;
        this.fileid = fileid;
        this.clientid = clientid;
        this.clientVSt = clientVSt;
        this.vrCommentBy = vrCommentBy;
        this.idPk = idPk;
        this.uploadDate = uploadDate;
        this.uploadedBy = uploadedBy;
        this.comment = comment;
        this.status = status;
        this.imgname = imgname;
        this.fileUrl = fileUrl;
    }

    public Integer getJobid() {
        return jobid;
    }

    public void setJobid(Integer jobid) {
        this.jobid = jobid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFileid() {
        return fileid;
    }

    public void setFileid(Integer fileid) {
        this.fileid = fileid;
    }

    public Integer getClientid() {
        return clientid;
    }

    public void setClientid(Integer clientid) {
        this.clientid = clientid;
    }

    public Integer getClientVSt() {
        return clientVSt;
    }

    public void setClientVSt(Integer clientVSt) {
        this.clientVSt = clientVSt;
    }

    public Integer getVrCommentBy() {
        return vrCommentBy;
    }

    public void setVrCommentBy(Integer vrCommentBy) {
        this.vrCommentBy = vrCommentBy;
    }

    public Integer getIdPk() {
        return idPk;
    }

    public void setIdPk(Integer idPk) {
        this.idPk = idPk;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImgname() {
        return imgname;
    }

    public void setImgname(String imgname) {
        this.imgname = imgname;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

}
