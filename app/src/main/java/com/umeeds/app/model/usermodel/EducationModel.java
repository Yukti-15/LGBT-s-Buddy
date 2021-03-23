package com.umeeds.app.model.usermodel;

public class EducationModel {
    boolean selected;
    private String education_id;
    private String education;
    private String status;
    private String sortorder;

    public EducationModel(String education_id, String education, String status, String sortorder) {
        this.education_id = education_id;
        this.education = education;
        this.status = status;
        this.sortorder = sortorder;
    }

    public EducationModel(String education_id, String education, String status, String sortorder, boolean selected) {
        this.education_id = education_id;
        this.education = education;
        this.status = status;
        this.sortorder = sortorder;
        this.selected = selected;
    }

    public String getEducation_id() {
        return education_id;
    }

    public void setEducation_id(String education_id) {
        this.education_id = education_id;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSortorder() {
        return sortorder;
    }

    public void setSortorder(String sortorder) {
        this.sortorder = sortorder;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
