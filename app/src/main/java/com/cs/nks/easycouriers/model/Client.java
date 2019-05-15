package com.cs.nks.easycouriers.model;
public class Client {

    private String userID;
    private String txtMail;
    private String compID;
    private String compName;
    private String userName;
    private String userCategory;
    private String caType;
    private String dealerID;
    private String dtype;
    private String loginEmail;
    private String dealerName;
    private String status;

    private String Imagepath;
    private String  Masterstatus;
    /**
     * No args constructor for use in serialization
     *
     */

    /**
     *
     * @param userID
     * @param userCategory
     * @param loginEmail
     * @param txtMail
     * @param status
     * @param caType
     * @param compName
     * @param dtype
     * @param userName
     * @param dealerName
     * @param dealerID
     * @param compID
     */
    public Client(String userID, String txtMail, String compID,
                  String compName, String userName, String userCategory,
                  String caType, String dealerID, String dtype,
                  String loginEmail, String dealerName, String status
            ,String Imagepath,String Masterstatus) {
        super();
        this.userID = userID;
        this.txtMail = txtMail;
        this.compID = compID;
        this.compName = compName;
        this.userName = userName;
        this.userCategory = userCategory;
        this.caType = caType;
        this.dealerID = dealerID;
        this.dtype = dtype;
        this.loginEmail = loginEmail;
        this.dealerName = dealerName;
        this.status = status;
        this.Imagepath = Imagepath;
        this.Masterstatus = Masterstatus;


    }

    public String getUserID() {
        return userID;
    }

    public String getImagepath() {
        return Imagepath;
    }

    public String getMasterstatus() {
        return Masterstatus;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTxtMail() {
        return txtMail;
    }

    public void setTxtMail(String txtMail) {
        this.txtMail = txtMail;
    }

    public String getCompID() {
        return compID;
    }

    public void setCompID(String compID) {
        this.compID = compID;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(String userCategory) {
        this.userCategory = userCategory;
    }

    public String getCaType() {
        return caType;
    }

    public void setCaType(String caType) {
        this.caType = caType;
    }

    public String getDealerID() {
        return dealerID;
    }

    public void setDealerID(String dealerID) {
        this.dealerID = dealerID;
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    public String getLoginEmail() {
        return loginEmail;
    }

    public void setLoginEmail(String loginEmail) {
        this.loginEmail = loginEmail;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}