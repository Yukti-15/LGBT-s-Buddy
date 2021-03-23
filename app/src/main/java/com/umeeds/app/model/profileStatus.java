package com.umeeds.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class profileStatus {

    @SerializedName("response")
    @Expose
    private Boolean response;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private Result result;

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

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("MatriID")
        @Expose
        private String matriID;
        @SerializedName("type")
        @Expose
        private Object type;
        @SerializedName("heading")
        @Expose
        private Object heading;
        @SerializedName("proofid")
        @Expose
        private Object proofid;
        @SerializedName("photo")
        @Expose
        private String photo;
        @SerializedName("admin_approve")
        @Expose
        private String adminApprove;
        @SerializedName("dateadded")
        @Expose
        private Object dateadded;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMatriID() {
            return matriID;
        }

        public void setMatriID(String matriID) {
            this.matriID = matriID;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public Object getHeading() {
            return heading;
        }

        public void setHeading(Object heading) {
            this.heading = heading;
        }

        public Object getProofid() {
            return proofid;
        }

        public void setProofid(Object proofid) {
            this.proofid = proofid;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getAdminApprove() {
            return adminApprove;
        }

        public void setAdminApprove(String adminApprove) {
            this.adminApprove = adminApprove;
        }

        public Object getDateadded() {
            return dateadded;
        }

        public void setDateadded(Object dateadded) {
            this.dateadded = dateadded;
        }
    }
}
