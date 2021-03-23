package com.umeeds.app.model;

public class YardsItem {
    private String Address, email, mobileNo, schuduale, pinNo, cityNo;

    public YardsItem(String address, String email, String mobileNo, String schuduale, String pinNo, String cityNo) {
        Address = address;
        this.email = email;
        this.mobileNo = mobileNo;
        this.schuduale = schuduale;
        this.pinNo = pinNo;
        this.cityNo = cityNo;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getSchuduale() {
        return schuduale;
    }

    public void setSchuduale(String schuduale) {
        this.schuduale = schuduale;
    }

    public String getPinNo() {
        return pinNo;
    }

    public void setPinNo(String pinNo) {
        this.pinNo = pinNo;
    }

    public String getCityNo() {
        return cityNo;
    }

    public void setCityNo(String cityNo) {
        this.cityNo = cityNo;
    }
}
