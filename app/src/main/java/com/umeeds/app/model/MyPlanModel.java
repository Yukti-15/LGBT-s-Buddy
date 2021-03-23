package com.umeeds.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyPlanModel {
    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private MyPlanData myPlanData;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MyPlanData getMyPlanData() {
        return myPlanData;
    }

    public void setMyPlanData(MyPlanData myPlanData) {
        this.myPlanData = myPlanData;
    }

    public static class MyPlanData {
        @SerializedName("Status")
        @Expose
        private String Status;

        @SerializedName("MemshipExpiryDate")
        @Expose
        private String MemshipExpiryDate;

        @SerializedName("Noofcontacts")
        @Expose
        private String Noofcontacts;

        @SerializedName("chatcontact")
        @Expose
        private String chatcontact;

        @SerializedName("plandisplayname")
        @Expose
        private String plandisplayname;

        public String getPlandisplayname() {
            return plandisplayname;
        }

        public void setPlandisplayname(String plandisplayname) {
            this.plandisplayname = plandisplayname;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
        }

        public String getMemshipExpiryDate() {
            return MemshipExpiryDate;
        }

        public void setMemshipExpiryDate(String memshipExpiryDate) {
            MemshipExpiryDate = memshipExpiryDate;
        }

        public String getNoofcontacts() {
            return Noofcontacts;
        }

        public void setNoofcontacts(String noofcontacts) {
            Noofcontacts = noofcontacts;
        }

        public String getChatcontact() {
            return chatcontact;
        }

        public void setChatcontact(String chatcontact) {
            this.chatcontact = chatcontact;
        }
    }
}
