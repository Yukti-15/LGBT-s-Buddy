package com.umeeds.app.model.usermodel;

public class ProfessionModel {
    boolean selected;
    private String occupation_id;
    private String occupation;
    private String status;
    private String sortorder;


    public ProfessionModel(String occupation_id, String occupation, String status, String sortorder) {
        this.occupation_id = occupation_id;
        this.occupation = occupation;
        this.status = status;
        this.sortorder = sortorder;
    }

    public String getOccupation_id() {
        return occupation_id;
    }

    public void setOccupation_id(String occupation_id) {
        this.occupation_id = occupation_id;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
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
