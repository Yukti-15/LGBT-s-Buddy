package com.umeeds.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MemberShipPlanModel {

    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("data")
    @Expose
    private List<MemberShipPlanData> memberShipPlanList;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<MemberShipPlanData> getMemberShipPlanList() {
        return memberShipPlanList;
    }

    public void setMemberShipPlanList(List<MemberShipPlanData> memberShipPlanList) {
        this.memberShipPlanList = memberShipPlanList;
    }

    public static class MemberShipPlanData {
        @SerializedName("planid")
        @Expose
        private String planId;
        @SerializedName("planname")
        @Expose
        private String planName;
        @SerializedName("plandisplayname")
        @Expose
        private String plandisplayName;
        @SerializedName("plannoofcontacts")
        @Expose
        private String plannoofcontacts;

        @SerializedName("planchatcontact")
        @Expose
        private String planchatcontact;
        @SerializedName("planduration")
        @Expose
        private String planduration;
        @SerializedName("planamount")
        @Expose
        private String planamount;
        @SerializedName("planoffers")
        @Expose
        private String planoffers;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("image")
        @Expose
        private String image;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getPlanId() {
            return planId;
        }

        public void setPlanId(String planId) {
            this.planId = planId;
        }

        public String getPlanName() {
            return planName;
        }

        public void setPlanName(String planName) {
            this.planName = planName;
        }

        public String getPlandisplayName() {
            return plandisplayName;
        }

        public void setPlandisplayName(String plandisplayName) {
            this.plandisplayName = plandisplayName;
        }

        public String getPlannoofcontacts() {
            return plannoofcontacts;
        }

        public void setPlannoofcontacts(String plannoofcontacts) {
            this.plannoofcontacts = plannoofcontacts;
        }

        public String getPlanchatcontact() {
            return planchatcontact;
        }

        public void setPlanchatcontact(String planchatcontact) {
            this.planchatcontact = planchatcontact;
        }

        public String getPlanduration() {
            return planduration;
        }

        public void setPlanduration(String planduration) {
            this.planduration = planduration;
        }

        public String getPlanamount() {
            return planamount;
        }

        public void setPlanamount(String planamount) {
            this.planamount = planamount;
        }

        public String getPlanoffers() {
            return planoffers;
        }

        public void setPlanoffers(String planoffers) {
            this.planoffers = planoffers;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

}
