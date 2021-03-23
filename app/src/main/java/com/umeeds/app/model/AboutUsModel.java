package com.umeeds.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AboutUsModel {

    @SerializedName("response")
    @Expose
    private boolean response;

    @SerializedName("data")
    @Expose
    private AboutUsData loginData;

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public AboutUsData getLoginData() {
        return loginData;
    }

    public void setLoginData(AboutUsData loginData) {
        this.loginData = loginData;
    }

    public static class AboutUsData {
        @SerializedName("cms_id")
        @Expose
        private String cmsId;
        @SerializedName("content")
        @Expose
        private String content;
        @SerializedName("link")
        @Expose
        private String link;
        @SerializedName("title")
        @Expose
        private String title;

        public String getCmsId() {
            return cmsId;
        }

        public void setCmsId(String cmsId) {
            this.cmsId = cmsId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
