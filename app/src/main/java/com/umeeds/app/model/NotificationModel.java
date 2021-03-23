package com.umeeds.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationModel {
    @SerializedName("response")
    @Expose
    private boolean response;

    @SerializedName("data")
    @Expose
    private List<NotificationData> notificationDataList;

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public List<NotificationData> getNotificationDataList() {
        return notificationDataList;
    }

    public void setNotificationDataList(List<NotificationData> notificationDataList) {
        this.notificationDataList = notificationDataList;
    }

    public static class NotificationData {
        @SerializedName("Photo1")
        @Expose
        private String Photo1;
        @SerializedName("Name")
        @Expose
        private String Name;
        @SerializedName("Gender")
        @Expose
        private String Gender;

        @SerializedName("toid")
        @Expose
        private String toid;
        @SerializedName("nid")
        @Expose
        private String nid;
        @SerializedName("msg")
        @Expose
        private String msg;
        @SerializedName("readstatus")
        @Expose
        private String readstatus;
        @SerializedName("created_date")
        @Expose
        private String created_date;

        public String getPhoto1() {
            return Photo1;
        }

        public void setPhoto1(String photo1) {
            Photo1 = photo1;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getGender() {
            return Gender;
        }

        public void setGender(String gender) {
            Gender = gender;
        }

        public String getToid() {
            return toid;
        }

        public void setToid(String toid) {
            this.toid = toid;
        }

        public String getNid() {
            return nid;
        }

        public void setNid(String nid) {
            this.nid = nid;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getReadstatus() {
            return readstatus;
        }

        public void setReadstatus(String readstatus) {
            this.readstatus = readstatus;
        }

        public String getCreated_date() {
            return created_date;
        }

        public void setCreated_date(String created_date) {
            this.created_date = created_date;
        }
    }
}
