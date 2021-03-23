package com.umeeds.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class MemberProfileModel {
    @SerializedName("response")
    @Expose
    private boolean response;

    @SerializedName("data")
    @Expose
    private List<MemberProfileData> memberProfileDataList;

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public List<MemberProfileData> getMemberProfileDataList() {
        return memberProfileDataList;
    }

    public void setMemberProfileDataList(List<MemberProfileData> memberProfileDataList) {
        this.memberProfileDataList = memberProfileDataList;
    }

    public static class MemberProfileData {
        @SerializedName("MatriID")
        @Expose
        private String MatriID;
        @SerializedName("Prefix")
        @Expose
        private String Prefix;
        @SerializedName("Termsofservice")
        @Expose
        private String Termsofservice;
        @SerializedName("ConfirmEmail")
        @Expose
        private String ConfirmEmail;

        @SerializedName("Name")
        @Expose
        private String Name;
        @SerializedName("Gender")
        @Expose
        private String Gender;

        @SerializedName("DOB")
        @Expose
        private String DOB;
        @SerializedName("Age")
        @Expose
        private String Age;
        @SerializedName("Maritalstatus")
        @Expose
        private String Maritalstatus;
        @SerializedName("childrenlivingstatus")
        @Expose
        private String childrenlivingstatus;
        @SerializedName("Education")
        @Expose
        private String Education;
        @SerializedName("EducationDetails")
        @Expose
        private String EducationDetails;

        @SerializedName("Annualincome")
        @Expose
        private String Annualincome;
        @SerializedName("Religion")
        @Expose
        private String Religion;
        @SerializedName("Language")
        @Expose
        private String Language;
        @SerializedName("Height")
        @Expose
        private String Height;
        @SerializedName("Weight")
        @Expose
        private String Weight;

        @SerializedName("Mobile")
        @Expose
        private String Mobile;
        @SerializedName("Address")
        @Expose
        private String Address;
        @SerializedName("City")
        @Expose
        private String City;
        @SerializedName("Postal")
        @Expose
        private String Postal;
        @SerializedName("State")
        @Expose
        private String State;
        @SerializedName("Phone")
        @Expose
        private String Phone;

        @SerializedName("Country")
        @Expose
        private String Country;

        @SerializedName("Photo1")
        @Expose
        private String Photo1;
        @SerializedName("Photo2")
        @Expose
        private String Photo2;
        @SerializedName("Photo3")
        @Expose
        private String Photo3;

        @SerializedName("Photo4")
        @Expose
        private String Photo4;

        @SerializedName("Photo5")
        @Expose
        private String Photo5;
        @SerializedName("Photo6")
        @Expose
        private String Photo6;

        @SerializedName("Photo7")
        @Expose
        private String Photo7;

        @SerializedName("nationality")
        @Expose
        private String nationality;

        @SerializedName("Occupation")
        @Expose
        private String Occupation;
        @SerializedName("Status")
        @Expose
        private String Status;
        @SerializedName("Noofcontacts")
        @Expose
        private String Noofcontacts;

        @SerializedName("chatcontact")
        @Expose
        private String chatcontact;
        @SerializedName("Profile")
        @Expose
        private String Profile;

        @SerializedName("city_name")
        @Expose
        private String city_name;

        @SerializedName("contactview")
        @Expose
        private String contactview;

        @SerializedName("sendrequest")
        @Expose
        private String sendrequest;
        @SerializedName("PartnerExpectations")
        @Expose
        private String PartnerExpectations;

        @SerializedName("Hobbies")
        @Expose
        private String Hobbies;

        @SerializedName("show_gender")
        @Expose
        private String show_gender;

        @SerializedName("userHeight")
        @Expose
        private String userHeight;

        @SerializedName("profile_status")
        @Expose
        private String profile_status;


        public String getProfile_status() {
            return profile_status;
        }

        public void setProfile_status(String profile_status) {
            this.profile_status = profile_status;
        }

        public String getUserHeight() {
            return userHeight;
        }

        public void setUserHeight(String userHeight) {
            this.userHeight = userHeight;
        }

        public String getHobbies() {
            return Hobbies;
        }

        public void setHobbies(String hobbies) {
            Hobbies = hobbies;
        }

        public String getShow_gender() {
            return show_gender;
        }

        public void setShow_gender(String show_gender) {
            this.show_gender = show_gender;
        }

        public String getPartnerExpectations() {
            return PartnerExpectations;
        }

        public void setPartnerExpectations(String partnerExpectations) {
            PartnerExpectations = partnerExpectations;
        }

        public String getSendrequest() {
            return sendrequest;
        }

        public void setSendrequest(String sendrequest) {
            this.sendrequest = sendrequest;
        }

        public String getContactview() {
            return contactview;
        }

        public void setContactview(String contactview) {
            this.contactview = contactview;
        }

        public String getMatriID() {
            return MatriID;
        }

        public void setMatriID(String matriID) {
            MatriID = matriID;
        }

        public String getPrefix() {
            return Prefix;
        }

        public void setPrefix(String prefix) {
            Prefix = prefix;
        }

        public String getTermsofservice() {
            return Termsofservice;
        }

        public void setTermsofservice(String termsofservice) {
            Termsofservice = termsofservice;
        }

        public String getConfirmEmail() {
            return ConfirmEmail;
        }

        public void setConfirmEmail(String confirmEmail) {
            ConfirmEmail = confirmEmail;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getGender() {
            return Gender;
        }

        public void setGender(String gender) {
            Gender = gender;
        }

        public String getDOB() {
            return DOB;
        }

        public void setDOB(String DOB) {
            this.DOB = DOB;
        }

        public String getAge() {
            return Age;
        }

        public void setAge(String age) {
            Age = age;
        }

        public String getMaritalstatus() {
            return Maritalstatus;
        }

        public void setMaritalstatus(String maritalstatus) {
            Maritalstatus = maritalstatus;
        }

        public String getChildrenlivingstatus() {
            return childrenlivingstatus;
        }

        public void setChildrenlivingstatus(String childrenlivingstatus) {
            this.childrenlivingstatus = childrenlivingstatus;
        }

        public String getEducation() {
            return Education;
        }

        public void setEducation(String education) {
            Education = education;
        }

        public String getEducationDetails() {
            return EducationDetails;
        }

        public void setEducationDetails(String educationDetails) {
            EducationDetails = educationDetails;
        }

        public String getAnnualincome() {
            return Annualincome;
        }

        public void setAnnualincome(String annualincome) {
            Annualincome = annualincome;
        }

        public String getReligion() {
            return Religion;
        }

        public void setReligion(String religion) {
            Religion = religion;
        }

        public String getLanguage() {
            return Language;
        }

        public void setLanguage(String language) {
            Language = language;
        }

        public String getHeight() {
            return Height;
        }

        public void setHeight(String height) {
            Height = height;
        }

        public String getWeight() {
            return Weight;
        }

        public void setWeight(String weight) {
            Weight = weight;
        }

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String mobile) {
            Mobile = mobile;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String address) {
            Address = address;
        }

        public String getCity() {
            return City;
        }

        public void setCity(String city) {
            City = city;
        }

        public String getPostal() {
            return Postal;
        }

        public void setPostal(String postal) {
            Postal = postal;
        }

        public String getState() {
            return State;
        }

        public void setState(String state) {
            State = state;
        }

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String phone) {
            Phone = phone;
        }

        public String getCountry() {
            return Country;
        }

        public void setCountry(String country) {
            Country = country;
        }

        public String getPhoto1() {
            return Photo1;
        }

        public void setPhoto1(String photo1) {
            Photo1 = photo1;
        }

        public String getPhoto2() {
            return Photo2;
        }

        public void setPhoto2(String photo2) {
            Photo2 = photo2;
        }

        public String getPhoto3() {
            return Photo3;
        }

        public void setPhoto3(String photo3) {
            Photo3 = photo3;
        }

        public String getPhoto4() {
            return Photo4;
        }

        public void setPhoto4(String photo4) {
            Photo4 = photo4;
        }

        public String getPhoto5() {
            return Photo5;
        }

        public void setPhoto5(String photo5) {
            Photo5 = photo5;
        }

        public String getPhoto6() {
            return Photo6;
        }

        public void setPhoto6(String photo6) {
            Photo6 = photo6;
        }

        public String getPhoto7() {
            return Photo7;
        }

        public void setPhoto7(String photo7) {
            Photo7 = photo7;
        }

        public String getNationality() {
            return nationality;
        }

        public void setNationality(String nationality) {
            this.nationality = nationality;
        }

        public String getOccupation() {
            return Occupation;
        }

        public void setOccupation(String occupation) {
            Occupation = occupation;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
        }

        public String getNoofcontacts() {
            return Noofcontacts;
        }

        public void setNoofcontacts(String noofcontacts) {
            Noofcontacts = noofcontacts;
        }

        public String getChatcontact() {
            return chatcontact;
        }

        public void setChatcontact(String chatcontact) {
            this.chatcontact = chatcontact;
        }

        public String getProfile() {
            return Profile;
        }

        public void setProfile(String profile) {
            Profile = profile;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }
    }
}
