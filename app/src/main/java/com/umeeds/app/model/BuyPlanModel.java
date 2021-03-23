package com.umeeds.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BuyPlanModel {

    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("data")
    @Expose
    private List<BuyPlanData> buyPlanDataList;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<BuyPlanData> getBuyPlanDataList() {
        return buyPlanDataList;
    }

    public void setBuyPlanDataList(List<BuyPlanData> buyPlanDataList) {
        this.buyPlanDataList = buyPlanDataList;
    }

    public static class BuyPlanData {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("option_type")
        @Expose
        private String option_type;
        @SerializedName("status")
        @Expose
        private String status;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOption_type() {
            return option_type;
        }

        public void setOption_type(String option_type) {
            this.option_type = option_type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

}
