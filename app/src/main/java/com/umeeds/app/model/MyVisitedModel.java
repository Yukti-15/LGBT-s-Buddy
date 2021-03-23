package com.umeeds.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyVisitedModel {

    @Expose
    @SerializedName("data")
    private List<DataBean> data;
    @Expose
    @SerializedName("response")
    private boolean response;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public boolean getResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public static class DataBean {
        @Expose
        @SerializedName("heartlist")
        private String heartlist;

        @Expose
        @SerializedName("contactview")
        private String contactview;

        @Expose
        @SerializedName("sendrequest")
        private String sendrequest;
        @Expose
        @SerializedName("profile_status")
        private String profileStatus;
        @Expose
        @SerializedName("nationality")
        private String nationality;
        @Expose
        @SerializedName("show_gender")
        private String showGender;
        @Expose
        @SerializedName("Expectations_app")
        private String expectationsApp;
        @Expose
        @SerializedName("Photo7")
        private String photo7;
        @Expose
        @SerializedName("Photo6")
        private String photo6;
        @Expose
        @SerializedName("Photo5")
        private String photo5;
        @Expose
        @SerializedName("Photo4")
        private String photo4;
        @Expose
        @SerializedName("Photo3Approve")
        private String photo3approve;
        @Expose
        @SerializedName("Photo3")
        private String photo3;
        @Expose
        @SerializedName("Photo2Approve")
        private String photo2approve;
        @Expose
        @SerializedName("Photo2")
        private String photo2;
        @Expose
        @SerializedName("profile_approve")
        private String profileApprove;
        @Expose
        @SerializedName("Photo1Approve")
        private String photo1approve;
        @Expose
        @SerializedName("Photo1")
        private String photo1;
        @Expose
        @SerializedName("Orderstatus")
        private String orderstatus;
        @Expose
        @SerializedName("expdays")
        private String expdays;
        @Expose
        @SerializedName("MemshipExpiryDate")
        private String memshipexpirydate;
        @Expose
        @SerializedName("Regdate")
        private String regdate;
        @Expose
        @SerializedName("memtype")
        private String memtype;
        @Expose
        @SerializedName("Status")
        private String status;
        @Expose
        @SerializedName("Noofcontacts")
        private String noofcontacts;
        @Expose
        @SerializedName("chatcontact")
        private String chatcontact;
        @Expose
        @SerializedName("Hobbies")
        private String hobbies;
        @Expose
        @SerializedName("PE_Countrylivingin")
        private String peCountrylivingin;
        @Expose
        @SerializedName("PE_Education")
        private String peEducation;
        @Expose
        @SerializedName("pecaste_name")
        private String pecasteName;
        @Expose
        @SerializedName("pereligion_name")
        private String pereligionName;
        @Expose
        @SerializedName("PartnerExpectations")
        private String partnerexpectations;
        @Expose
        @SerializedName("UPE_Height2")
        private String upeHeight2;
        @Expose
        @SerializedName("UPE_Height")
        private String upeHeight;
        @Expose
        @SerializedName("PE_Height2")
        private String peHeight2;
        @Expose
        @SerializedName("PE_Height")
        private String peHeight;
        @Expose
        @SerializedName("PE_ToAge")
        private String peToage;
        @Expose
        @SerializedName("PE_FromAge")
        private String peFromage;
        @Expose
        @SerializedName("Profile")
        private String profile;
        @Expose
        @SerializedName("mobilecode")
        private String mobilecode;
        @Expose
        @SerializedName("Mobile")
        private String mobile;
        @Expose
        @SerializedName("Phone")
        private String phone;
        @Expose
        @SerializedName("Country")
        private String country;
        @Expose
        @SerializedName("State")
        private String state;
        @Expose
        @SerializedName("Postal")
        private String postal;
        @Expose
        @SerializedName("city_name")
        private String cityName;
        @Expose
        @SerializedName("City")
        private String city;
        @Expose
        @SerializedName("Complexion")
        private String complexion;
        @Expose
        @SerializedName("spe_cases")
        private String speCases;
        @Expose
        @SerializedName("Weight")
        private String weight;
        @Expose
        @SerializedName("userHeight")
        private String userheight;
        @Expose
        @SerializedName("Height")
        private String height;
        @Expose
        @SerializedName("Language")
        private String language;
        @Expose
        @SerializedName("caste_name")
        private String casteName;
        @Expose
        @SerializedName("religion_name")
        private String religionName;
        @Expose
        @SerializedName("Religion")
        private String religion;
        @Expose
        @SerializedName("Annualincome")
        private String annualincome;
        @Expose
        @SerializedName("Occupation")
        private String occupation;
        @Expose
        @SerializedName("Education")
        private String education;
        @Expose
        @SerializedName("childrenlivingstatus")
        private String childrenlivingstatus;
        @Expose
        @SerializedName("Maritalstatus")
        private String maritalstatus;
        @Expose
        @SerializedName("Age")
        private String age;
        @Expose
        @SerializedName("DOB")
        private String dob;
        @Expose
        @SerializedName("Gender")
        private String gender;
        @Expose
        @SerializedName("Name")
        private String name;
        @Expose
        @SerializedName("ConfirmEmail")
        private String confirmemail;
        @Expose
        @SerializedName("Termsofservice")
        private String termsofservice;
        @Expose
        @SerializedName("MatriID")
        private String matriid;
        @Expose
        @SerializedName("ID")
        private String id;

        @Expose
        @SerializedName("visited_date")
        private String visited_date;

        @Expose
        @SerializedName("plan_name")
        private String plan_name;

        public String getPlan_name() {
            return plan_name;
        }

        public void setPlan_name(String plan_name) {
            this.plan_name = plan_name;
        }

        public String getVisited_date() {
            return visited_date;
        }

        public void setVisited_date(String visited_date) {
            this.visited_date = visited_date;
        }

        public String getHeartlist() {
            return heartlist;
        }

        public void setHeartlist(String heartlist) {
            this.heartlist = heartlist;
        }

        public String getContactview() {
            return contactview;
        }

        public void setContactview(String contactview) {
            this.contactview = contactview;
        }

        public String getSendrequest() {
            return sendrequest;
        }

        public void setSendrequest(String sendrequest) {
            this.sendrequest = sendrequest;
        }

        public String getProfileStatus() {
            return profileStatus;
        }

        public void setProfileStatus(String profileStatus) {
            this.profileStatus = profileStatus;
        }

        public String getNationality() {
            return nationality;
        }

        public void setNationality(String nationality) {
            this.nationality = nationality;
        }

        public String getShowGender() {
            return showGender;
        }

        public void setShowGender(String showGender) {
            this.showGender = showGender;
        }

        public String getExpectationsApp() {
            return expectationsApp;
        }

        public void setExpectationsApp(String expectationsApp) {
            this.expectationsApp = expectationsApp;
        }

        public String getPhoto7() {
            return photo7;
        }

        public void setPhoto7(String photo7) {
            this.photo7 = photo7;
        }

        public String getPhoto6() {
            return photo6;
        }

        public void setPhoto6(String photo6) {
            this.photo6 = photo6;
        }

        public String getPhoto5() {
            return photo5;
        }

        public void setPhoto5(String photo5) {
            this.photo5 = photo5;
        }

        public String getPhoto4() {
            return photo4;
        }

        public void setPhoto4(String photo4) {
            this.photo4 = photo4;
        }

        public String getPhoto3approve() {
            return photo3approve;
        }

        public void setPhoto3approve(String photo3approve) {
            this.photo3approve = photo3approve;
        }

        public String getPhoto3() {
            return photo3;
        }

        public void setPhoto3(String photo3) {
            this.photo3 = photo3;
        }

        public String getPhoto2approve() {
            return photo2approve;
        }

        public void setPhoto2approve(String photo2approve) {
            this.photo2approve = photo2approve;
        }

        public String getPhoto2() {
            return photo2;
        }

        public void setPhoto2(String photo2) {
            this.photo2 = photo2;
        }

        public String getProfileApprove() {
            return profileApprove;
        }

        public void setProfileApprove(String profileApprove) {
            this.profileApprove = profileApprove;
        }

        public String getPhoto1approve() {
            return photo1approve;
        }

        public void setPhoto1approve(String photo1approve) {
            this.photo1approve = photo1approve;
        }

        public String getPhoto1() {
            return photo1;
        }

        public void setPhoto1(String photo1) {
            this.photo1 = photo1;
        }

        public String getOrderstatus() {
            return orderstatus;
        }

        public void setOrderstatus(String orderstatus) {
            this.orderstatus = orderstatus;
        }

        public String getExpdays() {
            return expdays;
        }

        public void setExpdays(String expdays) {
            this.expdays = expdays;
        }

        public String getMemshipexpirydate() {
            return memshipexpirydate;
        }

        public void setMemshipexpirydate(String memshipexpirydate) {
            this.memshipexpirydate = memshipexpirydate;
        }

        public String getRegdate() {
            return regdate;
        }

        public void setRegdate(String regdate) {
            this.regdate = regdate;
        }

        public String getMemtype() {
            return memtype;
        }

        public void setMemtype(String memtype) {
            this.memtype = memtype;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getNoofcontacts() {
            return noofcontacts;
        }

        public void setNoofcontacts(String noofcontacts) {
            this.noofcontacts = noofcontacts;
        }

        public String getChatcontact() {
            return chatcontact;
        }

        public void setChatcontact(String chatcontact) {
            this.chatcontact = chatcontact;
        }

        public String getHobbies() {
            return hobbies;
        }

        public void setHobbies(String hobbies) {
            this.hobbies = hobbies;
        }

        public String getPeCountrylivingin() {
            return peCountrylivingin;
        }

        public void setPeCountrylivingin(String peCountrylivingin) {
            this.peCountrylivingin = peCountrylivingin;
        }

        public String getPeEducation() {
            return peEducation;
        }

        public void setPeEducation(String peEducation) {
            this.peEducation = peEducation;
        }

        public String getPecasteName() {
            return pecasteName;
        }

        public void setPecasteName(String pecasteName) {
            this.pecasteName = pecasteName;
        }

        public String getPereligionName() {
            return pereligionName;
        }

        public void setPereligionName(String pereligionName) {
            this.pereligionName = pereligionName;
        }

        public String getPartnerexpectations() {
            return partnerexpectations;
        }

        public void setPartnerexpectations(String partnerexpectations) {
            this.partnerexpectations = partnerexpectations;
        }

        public String getUpeHeight2() {
            return upeHeight2;
        }

        public void setUpeHeight2(String upeHeight2) {
            this.upeHeight2 = upeHeight2;
        }

        public String getUpeHeight() {
            return upeHeight;
        }

        public void setUpeHeight(String upeHeight) {
            this.upeHeight = upeHeight;
        }

        public String getPeHeight2() {
            return peHeight2;
        }

        public void setPeHeight2(String peHeight2) {
            this.peHeight2 = peHeight2;
        }

        public String getPeHeight() {
            return peHeight;
        }

        public void setPeHeight(String peHeight) {
            this.peHeight = peHeight;
        }

        public String getPeToage() {
            return peToage;
        }

        public void setPeToage(String peToage) {
            this.peToage = peToage;
        }

        public String getPeFromage() {
            return peFromage;
        }

        public void setPeFromage(String peFromage) {
            this.peFromage = peFromage;
        }

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public String getMobilecode() {
            return mobilecode;
        }

        public void setMobilecode(String mobilecode) {
            this.mobilecode = mobilecode;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getPostal() {
            return postal;
        }

        public void setPostal(String postal) {
            this.postal = postal;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getComplexion() {
            return complexion;
        }

        public void setComplexion(String complexion) {
            this.complexion = complexion;
        }

        public String getSpeCases() {
            return speCases;
        }

        public void setSpeCases(String speCases) {
            this.speCases = speCases;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getUserheight() {
            return userheight;
        }

        public void setUserheight(String userheight) {
            this.userheight = userheight;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getCasteName() {
            return casteName;
        }

        public void setCasteName(String casteName) {
            this.casteName = casteName;
        }

        public String getReligionName() {
            return religionName;
        }

        public void setReligionName(String religionName) {
            this.religionName = religionName;
        }

        public String getReligion() {
            return religion;
        }

        public void setReligion(String religion) {
            this.religion = religion;
        }

        public String getAnnualincome() {
            return annualincome;
        }

        public void setAnnualincome(String annualincome) {
            this.annualincome = annualincome;
        }

        public String getOccupation() {
            return occupation;
        }

        public void setOccupation(String occupation) {
            this.occupation = occupation;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public String getChildrenlivingstatus() {
            return childrenlivingstatus;
        }

        public void setChildrenlivingstatus(String childrenlivingstatus) {
            this.childrenlivingstatus = childrenlivingstatus;
        }

        public String getMaritalstatus() {
            return maritalstatus;
        }

        public void setMaritalstatus(String maritalstatus) {
            this.maritalstatus = maritalstatus;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getConfirmemail() {
            return confirmemail;
        }

        public void setConfirmemail(String confirmemail) {
            this.confirmemail = confirmemail;
        }

        public String getTermsofservice() {
            return termsofservice;
        }

        public void setTermsofservice(String termsofservice) {
            this.termsofservice = termsofservice;
        }

        public String getMatriid() {
            return matriid;
        }

        public void setMatriid(String matriid) {
            this.matriid = matriid;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
