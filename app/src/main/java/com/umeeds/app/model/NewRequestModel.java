package com.umeeds.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewRequestModel {


    @SerializedName("response")
    @Expose
    private String response;

    @SerializedName("data")
    @Expose
    private List<NewRequestData> newRequestDataList;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<NewRequestData> getNewRequestDataList() {
        return newRequestDataList;
    }

    public void setNewRequestDataList(List<NewRequestData> newRequestDataList) {
        this.newRequestDataList = newRequestDataList;
    }

    public static class NewRequestData {
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
        private String name;
        @SerializedName("Photo1")
        @Expose
        private String Photo1;
        @SerializedName("Gender")
        @Expose
        private String Gender;

        @SerializedName("req_id")
        @Expose
        private String req_id;

        @SerializedName("rstatus")
        @Expose
        private String rstatus;
        @SerializedName("profile_status")
        @Expose
        private String profile_status;

        public String getProfile_status() {
            return profile_status;
        }

        public void setProfile_status(String profile_status) {
            this.profile_status = profile_status;
        }

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
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getRstatus() {
            return rstatus;
        }

        public void setRstatus(String rstatus) {
            this.rstatus = rstatus;
        }
    }
}
