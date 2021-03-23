package com.umeeds.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HeartModel {


    @SerializedName("response")
    @Expose
    private String response;

    @SerializedName("data")
    @Expose
    private List<HeartData> heartDataList;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<HeartData> getHeartDataList() {
        return heartDataList;
    }

    public void setHeartDataList(List<HeartData> heartDataList) {
        this.heartDataList = heartDataList;
    }


    public static class HeartData {
        @SerializedName("ID")
        @Expose
        private String loginId;
        @SerializedName("MatriID")
        @Expose
        private String matriId;
        @SerializedName("dum_id")
        @Expose
        private String dumId;
        @SerializedName("Termsofservice")
        @Expose
        private String Termsofservice;
        @SerializedName("ConfirmEmail")
        @Expose
        private String ConfirmEmail;
        @SerializedName("Name")
        @Expose
        private String Name;

        @SerializedName("Gender")
        @Expose
        private String Gender;

        @SerializedName("Age")
        @Expose
        private String Age;
        @SerializedName("pagecount")
        @Expose
        private String pagecount;

        @SerializedName("Photo1")
        @Expose
        private String photo1;

        @SerializedName("profile_status")
        @Expose
        private String profile_status;

        @SerializedName("verify_status")
        @Expose
        private String verify_status;

        @SerializedName("sendrequest")
        @Expose
        private String sendrequest;

        public String getSendrequest() {
            return sendrequest;
        }

        public void setSendrequest(String sendrequest) {
            this.sendrequest = sendrequest;
        }

        public String getProfile_status() {
            return profile_status;
        }

        public void setProfile_status(String profile_status) {
            this.profile_status = profile_status;
        }

        public String getVerify_status() {
            return verify_status;
        }

        public void setVerify_status(String verify_status) {
            this.verify_status = verify_status;
        }

        public String getPhoto1() {
            return photo1;
        }

        public void setPhoto1(String photo1) {
            this.photo1 = photo1;
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

        public String getDumId() {
            return dumId;
        }

        public void setDumId(String dumId) {
            this.dumId = dumId;
        }

        public String getTermsofservice() {
            return Termsofservice;
        }

        public void setTermsofservice(String termsofservice) {
            Termsofservice = termsofservice;
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

        public String getGender() {
            return Gender;
        }

        public void setGender(String gender) {
            Gender = gender;
        }

        public String getAge() {
            return Age;
        }

        public void setAge(String age) {
            Age = age;
        }

        public String getPagecount() {
            return pagecount;
        }

        public void setPagecount(String pagecount) {
            this.pagecount = pagecount;
        }
    }
}
