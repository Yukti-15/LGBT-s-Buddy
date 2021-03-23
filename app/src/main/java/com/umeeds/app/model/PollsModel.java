package com.umeeds.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PollsModel {
    @SerializedName("response")
    @Expose
    private String response;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private List<PollsData> pollsDataList;

    public List<PollsData> getPollsDataList() {
        return pollsDataList;
    }

    public void setPollsDataList(List<PollsData> pollsDataList) {
        this.pollsDataList = pollsDataList;
    }

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

    public static class PollsData {
        @SerializedName("post_id")
        @Expose
        private String postId;
        @SerializedName("post_title")
        @Expose
        private String postTitle;
        @SerializedName("select_option")
        @Expose
        private String selectOption;
        @SerializedName("all_option")
        @Expose
        private String allOption;
        @SerializedName("total_view")
        @Expose
        private String totalView;
        @SerializedName("people_ans_count")
        @Expose
        private String peopleansCount;
        @SerializedName("post_date")
        @Expose
        private String postDate;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("user_image")
        @Expose
        private String user_image;

        public String getSelectOption() {
            return selectOption;
        }

        public void setSelectOption(String selectOption) {
            this.selectOption = selectOption;
        }

        public String getAllOption() {
            return allOption;
        }

        public void setAllOption(String allOption) {
            this.allOption = allOption;
        }

        public String getTotalView() {
            return totalView;
        }

        public void setTotalView(String totalView) {
            this.totalView = totalView;
        }

        public String getPeopleansCount() {
            return peopleansCount;
        }

        public void setPeopleansCount(String peopleansCount) {
            this.peopleansCount = peopleansCount;
        }

        public String getPostDate() {
            return postDate;
        }

        public void setPostDate(String postDate) {
            this.postDate = postDate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUser_image() {
            return user_image;
        }

        public void setUser_image(String user_image) {
            this.user_image = user_image;
        }

        public String getPostId() {
            return postId;
        }

        public void setPostId(String postId) {
            this.postId = postId;
        }

        public String getPostTitle() {
            return postTitle;
        }

        public void setPostTitle(String postTitle) {
            this.postTitle = postTitle;
        }
    }
}
