package com.umeeds.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AcceptModel {

    @SerializedName("response")
    @Expose
    private String response;

    @SerializedName("data")
    @Expose
    private List<AcceptData> pendingData;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<AcceptData> getPendingData() {
        return pendingData;
    }

    public void setPendingData(List<AcceptData> pendingData) {
        this.pendingData = pendingData;
    }

    public static class AcceptData {
        @SerializedName("ID")
        @Expose
        private String loginId;
        @SerializedName("MatriID")
        @Expose
        private String matriId;
        @SerializedName("ConfirmEmail")
        @Expose
        private String ConfirmEmail;
        @SerializedName("Name")
        @Expose
        private String Name;
        @SerializedName("Photo1")
        @Expose
        private String Photo1;
        @SerializedName("Gender")
        @Expose
        private String Gender;
        @SerializedName("req_id")
        @Expose
        private String req_id;
        @SerializedName("Status")
        @Expose
        private String Status;
        @SerializedName("rstatus")
        @Expose
        private String rstatus;

        @SerializedName("MemshipExpiryDate")
        @Expose
        private String MemshipExpiryDate;

        @SerializedName("chatcontact")
        @Expose
        private String chatcontact;

        @SerializedName("Noofcontacts")
        @Expose
        private String Noofcontacts;
        @SerializedName("fromblocked")
        @Expose
        private String fromblocked;
        @SerializedName("toblocked")
        @Expose
        private String toblocked;

        @SerializedName("Msg")
        @Expose
        private Msg msg;

        public String getLoginId() {
            return loginId;
        }

        public void setLoginId(String loginId) {
            this.loginId = loginId;
        }

        public String getMatriId() {
            return matriId;
        }

        public void setMatriId(String matriId) {
            this.matriId = matriId;
        }

        public String getConfirmEmail() {
            return ConfirmEmail;
        }

        public void setConfirmEmail(String confirmEmail) {
            ConfirmEmail = confirmEmail;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getPhoto1() {
            return Photo1;
        }

        public void setPhoto1(String photo1) {
            Photo1 = photo1;
        }

        public String getGender() {
            return Gender;
        }

        public void setGender(String gender) {
            Gender = gender;
        }

        public String getReq_id() {
            return req_id;
        }

        public void setReq_id(String req_id) {
            this.req_id = req_id;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
        }

        public String getRstatus() {
            return rstatus;
        }

        public void setRstatus(String rstatus) {
            this.rstatus = rstatus;
        }

        public String getMemshipExpiryDate() {
            return MemshipExpiryDate;
        }

        public void setMemshipExpiryDate(String memshipExpiryDate) {
            MemshipExpiryDate = memshipExpiryDate;
        }

        public String getChatcontact() {
            return chatcontact;
        }

        public void setChatcontact(String chatcontact) {
            this.chatcontact = chatcontact;
        }

        public String getNoofcontacts() {
            return Noofcontacts;
        }

        public void setNoofcontacts(String noofcontacts) {
            Noofcontacts = noofcontacts;
        }

        public String getFromblocked() {
            return fromblocked;
        }

        public void setFromblocked(String fromblocked) {
            this.fromblocked = fromblocked;
        }

        public String getToblocked() {
            return toblocked;
        }

        public void setToblocked(String toblocked) {
            this.toblocked = toblocked;
        }

        public Msg getMsg() {
            return msg;
        }

        public void setMsg(Msg msg) {
            this.msg = msg;
        }

        public static class Msg {
            @SerializedName("Msg")
            @Expose
            private String Msg;
            @SerializedName("readed")
            @Expose
            private String readed;
            @SerializedName("rid")
            @Expose
            private String rid;
            @SerializedName("SendDate")
            @Expose
            private String SendDate;

            public String getSendDate() {
                return SendDate;
            }

            public void setSendDate(String sendDate) {
                SendDate = sendDate;
            }

            public String getRid() {
                return rid;
            }

            public void setRid(String rid) {
                this.rid = rid;
            }

            public String getMsg() {
                return Msg;
            }

            public void setMsg(String msg) {
                Msg = msg;
            }

            public String getReaded() {
                return readed;
            }

            public void setReaded(String readed) {
                this.readed = readed;
            }
        }

    }
}
