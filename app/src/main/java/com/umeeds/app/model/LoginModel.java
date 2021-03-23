package com.umeeds.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginModel {
    @SerializedName("response")
    @Expose
    private boolean response;

    @SerializedName("data")
    @Expose
    private List<LoginData> loginDataList;

    public List<LoginData> getLoginDataList() {
        return loginDataList;
    }

    public void setLoginDataList(List<LoginData> loginDataList) {
        this.loginDataList = loginDataList;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public static class LoginData {
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
        @SerializedName("Mobile")
        @Expose
        private String mobileUser;

        @SerializedName("Status")
        @Expose
        private String Status;

        @SerializedName("show_gender")
        @Expose
        private String show_gender;

        public String getShow_gender() {
            return show_gender;
        }

        public void setShow_gender(String show_gender) {
            this.show_gender = show_gender;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
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

        public String getMobileUser() {
            return mobileUser;
        }

        public void setMobileUser(String mobileUser) {
            this.mobileUser = mobileUser;
        }
    }
}
