package com.umeeds.app.model.serachmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SmartSearchModel {


    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("data")
    @Expose
    private List<SmartSearchData> smartSearchDataList;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<SmartSearchData> getSmartSearchDataList() {
        return smartSearchDataList;
    }

    public void setSmartSearchDataList(List<SmartSearchData> smartSearchDataList) {
        this.smartSearchDataList = smartSearchDataList;
    }

    public static class SmartSearchData {
        @SerializedName("ID")
        @Expose
        private String loginId;
        @SerializedName("MatriID")
        @Expose
        private String matriId;
        @SerializedName("ConfirmEmail")
        @Expose
        private String confirmEmail;
        @SerializedName("Name")
        @Expose
        private String nameUser;
        @SerializedName("Gender")
        @Expose
        private String genderUser;
        @SerializedName("DOB")
        @Expose
        private String DOB;
        @SerializedName("Age")
        @Expose
        private String Age;
        @SerializedName("religion_name")
        @Expose
        private String religion_name;
        @SerializedName("City")
        @Expose
        private String City;
        @SerializedName("city_name")
        @Expose
        private String city_name;

        @SerializedName("Photo1")
        @Expose
        private String Photo1;

        @SerializedName("Photo1Approve")
        @Expose
        private String Photo1Approve;
        @SerializedName("sendrequest")
        @Expose
        private String sendrequest;
        @SerializedName("heartlist")
        @Expose
        private String heartlist;
        @SerializedName("verify_status")
        @Expose
        private String verify_status;

        @SerializedName("profile_status")
        @Expose
        private String profile_status;

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
            return confirmEmail;
        }

        public void setConfirmEmail(String confirmEmail) {
            this.confirmEmail = confirmEmail;
        }

        public String getNameUser() {
            return nameUser;
        }

        public void setNameUser(String nameUser) {
            this.nameUser = nameUser;
        }

        public String getGenderUser() {
            return genderUser;
        }

        public void setGenderUser(String genderUser) {
            this.genderUser = genderUser;
        }

        public String getDOB() {
            return DOB;
        }

        public void setDOB(String DOB) {
            this.DOB = DOB;
        }

        public String getAge() {
            return Age;
        }

        public void setAge(String age) {
            Age = age;
        }

        public String getReligion_name() {
            return religion_name;
        }

        public void setReligion_name(String religion_name) {
            this.religion_name = religion_name;
        }

        public String getCity() {
            return City;
        }

        public void setCity(String city) {
            City = city;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getPhoto1() {
            return Photo1;
        }

        public void setPhoto1(String photo1) {
            Photo1 = photo1;
        }

        public String getPhoto1Approve() {
            return Photo1Approve;
        }

        public void setPhoto1Approve(String photo1Approve) {
            Photo1Approve = photo1Approve;
        }

        public String getSendrequest() {
            return sendrequest;
        }

        public void setSendrequest(String sendrequest) {
            this.sendrequest = sendrequest;
        }

        public String getHeartlist() {
            return heartlist;
        }

        public void setHeartlist(String heartlist) {
            this.heartlist = heartlist;
        }
    }
}
