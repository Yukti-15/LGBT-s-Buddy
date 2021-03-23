package com.umeeds.app.model.usermodel;

public class MotherToungModel {
    private String mother_tongue_id;
    private String mother_tongue;
    private String sortorder;
    private String status;


    public MotherToungModel(String mother_tongue_id, String mother_tongue, String sortorder, String status) {
        this.mother_tongue_id = mother_tongue_id;
        this.mother_tongue = mother_tongue;
        this.sortorder = sortorder;
        this.status = status;
    }


    public String getMother_tongue_id() {
        return mother_tongue_id;
    }

    public void setMother_tongue_id(String mother_tongue_id) {
        this.mother_tongue_id = mother_tongue_id;
    }

    public String getMother_tongue() {
        return mother_tongue;
    }

    public void setMother_tongue(String mother_tongue) {
        this.mother_tongue = mother_tongue;
    }

    public String getSortorder() {
        return sortorder;
    }

    public void setSortorder(String sortorder) {
        this.sortorder = sortorder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
