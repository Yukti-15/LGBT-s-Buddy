package com.umeeds.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaytmModel {
    @SerializedName("response")
    @Expose
    private Boolean response;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("checkSum")
    @Expose
    private String checkSum;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("emailid")
    @Expose
    private String emailid;
    @SerializedName("cus_id")
    @Expose
    private String cusId;

    public Boolean getResponse() {
        return response;
    }

    public void setResponse(Boolean response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(String checkSum) {
        this.checkSum = checkSum;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }


   /* @SerializedName("CHECKSUMHASH")
    @Expose
    private String cHECKSUMHASH;
    @SerializedName("ORDER_ID")
    @Expose
    private String oRDERID;
    @SerializedName("payt_STATUS")
    @Expose
    private String paytSTATUS;

    public String getCHECKSUMHASH() {
        return cHECKSUMHASH;
    }

    public void setCHECKSUMHASH(String cHECKSUMHASH) {
        this.cHECKSUMHASH = cHECKSUMHASH;
    }

    public String getORDERID() {
        return oRDERID;
    }

    public void setORDERID(String oRDERID) {
        this.oRDERID = oRDERID;
    }

    public String getPaytSTATUS() {
        return paytSTATUS;
    }

    public void setPaytSTATUS(String paytSTATUS) {
        this.paytSTATUS = paytSTATUS;
    }
*/
}
