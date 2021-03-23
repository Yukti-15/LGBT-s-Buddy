package com.umeeds.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterModel {
    @SerializedName("response")
    @Expose
    private String response;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("result")
    @Expose
    private RegisterData result;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public RegisterData getResult() {
        return result;
    }

    public void setResult(RegisterData result) {
        this.result = result;
    }

    public static class RegisterData {
        @SerializedName("ID")
        @Expose
        private String loginId;
        @SerializedName("MatriID")
        @Expose
        private String MatriID;
        @SerializedName("ConfirmEmail")
        @Expose
        private String ConfirmEmail;

        @SerializedName("Gender")
        @Expose
        private String Gender;
        @SerializedName("Mobile")
        @Expose
        private String Mobile;


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

        public String getGender() {
            return Gender;
        }

        public void setGender(String gender) {
            Gender = gender;
        }

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String mobile) {
            Mobile = mobile;
        }


        public String getLoginId() {
            return loginId;
        }

        public void setLoginId(String loginId) {
            this.loginId = loginId;
        }

        public String getMatriID() {
            return MatriID;
        }

        public void setMatriID(String matriID) {
            MatriID = matriID;
        }

        public String getConfirmEmail() {
            return ConfirmEmail;
        }

        public void setConfirmEmail(String confirmEmail) {
            ConfirmEmail = confirmEmail;
        }
    }

}
