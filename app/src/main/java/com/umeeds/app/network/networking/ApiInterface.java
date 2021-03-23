package com.umeeds.app.network.networking;


import com.umeeds.app.model.AboutUsModel;
import com.umeeds.app.model.AcceptModel;
import com.umeeds.app.model.BuyPlanModel;
import com.umeeds.app.model.ContactUsModel;
import com.umeeds.app.model.ForgotModel;
import com.umeeds.app.model.HeartModel;
import com.umeeds.app.model.HomeModel;
import com.umeeds.app.model.LoginModel;
import com.umeeds.app.model.MemberProfileModel;
import com.umeeds.app.model.MemberShipPlanModel;
import com.umeeds.app.model.MyPlanModel;
import com.umeeds.app.model.MyVisitedModel;
import com.umeeds.app.model.NewRequestModel;
import com.umeeds.app.model.NotifiDeletModel;
import com.umeeds.app.model.NotificationModel;
import com.umeeds.app.model.PaytmModel;
import com.umeeds.app.model.PaytmSccessModel;
import com.umeeds.app.model.PendingModel;
import com.umeeds.app.model.ProfileImageModel;
import com.umeeds.app.model.ProfileModel;
import com.umeeds.app.model.ProfileStatusModel;
import com.umeeds.app.model.ReadMessageModel;
import com.umeeds.app.model.RegisterModel;
import com.umeeds.app.model.UpdateProfileModel;
import com.umeeds.app.model.UserBlockModel;
import com.umeeds.app.model.UserStatusModel;
import com.umeeds.app.model.serachmodel.SmartSearchModel;
import com.umeeds.app.model.usermodel.VeryfyEmailRegisterModel;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login")
    Call<LoginModel> userLogin(@Field("username") String email, @Field("password") String password, @Field("devicetoken") String devicetoken);


    @FormUrlEncoded
    @POST("loginwidthmobile")
    Call<LoginModel> loginwidthmobile(@Field("mobile") String mobile);


    @FormUrlEncoded
    @POST("verify_email")
    Call<VeryfyEmailRegisterModel> verifyEmail(@Field("user_email") String email, @Field("phone") String phone);

    @FormUrlEncoded
    @POST("user_registration")
    Call<RegisterModel> userRegister(@Field("user_fname") String userFname, @Field("user_lname") String userLname, @Field("hobbies") String hobbies, @Field("dob") String dob
            , @Field("gender") String gender, @Field("phone") String phone, @Field("email") String email, @Field("password") String password
            , @Field("fcm_token") String fcmToken, @Field("terms") String terms,
                                     @Field("mobilecode") String countryCode);


    @FormUrlEncoded
    @POST("user_about_us")
    Call<LoginModel> tellusAbout(@Field("height") String height, @Field("merital_status") String meritalStatus, @Field("religion") String religion
            , @Field("mother_tongue") String motherTongue, @Field("education") String education, @Field("profession") String profession
            , @Field("country") String country, @Field("state") String state, @Field("city") String city, @Field("user_id") String userId
            , @Field("zipcode") String zipcode, @Field("nationality") String nationality, @Field("income") String income,
                                 @Field("childrenlivingstatus") String childrenlivingstatus, @Field("bio") String bio);


//    @Multipart
//    @POST("registration")
//    Call<RegisterModel> registration(@Part MultipartBody.Part userFnamePart, @Part MultipartBody.Part userLnamePart, @Part MultipartBody.Part dobPart
//            , @Part MultipartBody.Part genderPart, @Part MultipartBody.Part phonePart, @Part MultipartBody.Part emailPart,
//                                     @Part MultipartBody.Part passwordPart);
//

    @FormUrlEncoded
    @POST("set_partner_prefrence")
    Call<LoginModel> setPartnerPrefrence(@Field("show_gender") String height, @Field("min_age") String minAge, @Field("max_age") String maxAge
            , @Field("min_height") String minHeight, @Field("max_height") String maxHeight, @Field("income") String income
            , @Field("education") String education, @Field("profession") String profession, @Field("location") String location
            , @Field("special_case") String specialCase, @Field("user_id") String userId, @Field("PartnerExpectations") String PartnerExpectations);

    @FormUrlEncoded
    @POST("change_password")
    Call<LoginModel> changePassword(@Field("new_pwd") String email, @Field("con_pwd") String password, @Field("MatriID") String MatriID);

    @FormUrlEncoded
    @POST("forget_password")
    Call<ForgotModel> forgetPass(@Field("emailid") String email);

    @FormUrlEncoded
    @POST("user_detail")
    Call<ProfileModel> profileDetail(@Field("user_id") String userId);

//    @FormUrlEncoded
//    @POST("user_document_detail")
//    Call<ProfileStatusModel> profileDetailForDoc(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("profile_status_save")
    Call<ProfileStatusModel> profile_status_save(@Field("MatriID") String MatriID, @Field("status") String status);

    @FormUrlEncoded
    @POST("get_my_current_plan")
    Call<MyPlanModel> get_my_current_plan(@Field("MatriID") String MatriID);

    @FormUrlEncoded
    @POST("member_profile_details")
    Call<MemberProfileModel> member_profile_details(@Field("loginMatriID") String loginMatriID, @Field("MatriID") String MatriID);

    @FormUrlEncoded
    @POST("update_user_about_us1")
    Call<UpdateProfileModel> update_userAboutUs(@Field("user_fname") String userFname, @Field("user_lname") String userLname, @Field("dob") String dob
            , @Field("gender") String gender, @Field("mother_tongue") String motherTongue, @Field("religion") String religion
            , @Field("ethnicity") String ethnicity, @Field("height") String height, @Field("marital_status") String marital_status
            , @Field("user_id") String userId, @Field("childern_status") String childern_status);

    @FormUrlEncoded
    @POST("update_user_personal_details1")
    Call<UpdateProfileModel> update_userpersonhal(@Field("hobbies") String hobbies, @Field("interest") String interest, @Field("country") String country, @Field("state") String state
            , @Field("city") String city, @Field("phone_number") String phone_number, @Field("email") String email
            , @Field("user_id") String user_id, @Field("city_name") String city_name, @Field("PartnerExpectations") String PartnerExpectations);

    @FormUrlEncoded
    @POST("update_user_professional_details1")
    Call<UpdateProfileModel> updateprofessional_details(@Field("education") String education, @Field("profession") String profession, @Field("income") String income
            , @Field("bio") String bio, @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("get_plans")
    Call<MemberShipPlanModel> getPlans(@Field("user_id") String s);

    @FormUrlEncoded
    @POST("get_plans_int")
    Call<MemberShipPlanModel> getPlansForInternational(@Field("user_id") String s);

    @GET("get_payment_option")
    Call<BuyPlanModel> getPaymentOption();

    @FormUrlEncoded
    @POST("razorpaysuccess")
    Call<NotifiDeletModel> razorpaysuccess(@Field("MatriID") String MatriID, @Field("amount") String amount, @Field("balance_transaction") String balance_transaction
            , @Field("status") String status, @Field("planid") String planid);

    @FormUrlEncoded
    @POST("paypalsuccess")
    Call<NotifiDeletModel> paypalPaymentSuccess(@Field("MatriID") String MatriID, @Field("amount") String amount, @Field("balance_transaction") String balance_transaction
            , @Field("status") String status, @Field("planid") String planid);

    @FormUrlEncoded
    @POST("paytmpost")
    Call<PaytmModel> paytmCheckSum(@Field("MatriID") String MatriID, @Field("amount") String amount);

    @FormUrlEncoded
    @POST("paytmsuccess")
    Call<PaytmSccessModel> paytmSuccessApi(@Field("MatriID") String MatriID,
                                           @Field("amount") String amount,
                                           @Field("orderid") String orderid,
                                           @Field("email") String email,
                                           @Field("balance_transaction") String balance_transaction,
                                           @Field("planid") String planid,
                                           @Field("status") String status);

    @GET("about_us")
    Call<AboutUsModel> aboutUs();

    @GET("faq")
    Call<AboutUsModel> getFaqs();

    @GET("TermsandConditions")
    Call<AboutUsModel> termsCondition();

    @GET("contact_us")
    Call<ContactUsModel> contectUs();

    @FormUrlEncoded
    @POST("send_contact_us")
    Call<ContactUsModel> sencontectUs(@Field("email_id") String email, @Field("message") String message, @Field("name") String name);

    @FormUrlEncoded
    @POST("my_matches1")
    Call<HomeModel> homeMatch(@Field("id") String id, @Field("pagecount") String pagecount);

    @FormUrlEncoded
    @POST("send_friend_request")
    Call<LoginModel> sendFriendRequest(@Field("MatriID") String MatriID, @Field("loginmatriid") String loginmatriid);

    @FormUrlEncoded
    @POST("user_count_save")
    Call<LoginModel> user_count_save(@Field("MatriID") String MatriID, @Field("loginmatriid") String loginmatriid);

    @FormUrlEncoded
    @POST("sendheartlist")
    Call<LoginModel> sendheartlist(@Field("MatriID") String MatriID, @Field("loginmatriid") String loginmatriid, @Field("status") String status);

    @FormUrlEncoded
    @POST("requested_list")
    Call<NewRequestModel> requestedList(@Field("MatriID") String MatriID, @Field("pagecount") String pagecount);

    @FormUrlEncoded
    @POST("user_chat_count_save")
    Call<LoginModel> user_chat_count_save(@Field("MatriID") String MatriID, @Field("loginmatriid") String loginmatriid);

    @FormUrlEncoded
    @POST("my_heart_list")
    Call<HeartModel> myHeartList(@Field("MatriID") String MatriID, @Field("pagecount") String pagecount);

    @FormUrlEncoded
    @POST("my_pending_list")
    Call<PendingModel> pendingList(@Field("MatriID") String MatriID, @Field("pagecount") String pagecount);

    @FormUrlEncoded
    @POST("accept_friend_request")
    Call<PendingModel> acceptRequest(@Field("req_id") String req_id);

    @FormUrlEncoded
    @POST("cancel_send_request")
    Call<PendingModel> cancel_send_request(@Field("req_id") String req_id);

    /* @FormUrlEncoded
     @POST("my_chat_list")
     Call<AcceptModel> myChatList(@Field("MatriID") String MatriID, @Field("pagecount") String pagecount);
 */
    @FormUrlEncoded
    @POST("my_chat_list_new")
    Call<AcceptModel> myChatList(@Field("MatriID") String MatriID, @Field("pagecount") String pagecount);

    @Multipart
    @POST("update_user_profile_imagenew")
    Call<ProfileImageModel> updateProfileImage(@Part MultipartBody.Part imagenoPart, @Part MultipartBody.Part userIdPart, @Part MultipartBody.Part imagePart);

    @Multipart
    @POST("upload_documentnew")
    Call<ProfileImageModel> upload_document(@Part MultipartBody.Part matriidPart, @Part MultipartBody.Part imagePart);

    @FormUrlEncoded
    @POST("user_chat_save")
    Call<ProfileImageModel> save_chat(@Field("user_1_uuid") String MatriID, @Field("user_2_uuid") String pagecount, @Field("message") String message);

    @FormUrlEncoded
    @POST("save_user_block")
    Call<ProfileImageModel> save_user_block(@Field("from_id") String from_id, @Field("to_id") String to_id);

    @FormUrlEncoded
    @POST("user_unblock")
    Call<ProfileImageModel> user_unblock(@Field("from_id") String from_id, @Field("to_id") String to_id);

    @FormUrlEncoded
    @POST("blockunblokstatus")
    Call<UserBlockModel> blockunblokstatus(@Field("fromMatriID") String from_id, @Field("toMatriID") String toMatriID);


    @FormUrlEncoded
    @POST("smartSearch")
    Call<SmartSearchModel> smartSearch(@Field("loginmatriid") String MatriID, @Field("txtGender") String gender, @Field("Fage") String Fage, @Field("Tage") String Tage,
                                       @Field("txtphoto") String txtphoto, @Field("religion") String religion, @Field("txtprofile") String txtprofile
            , @Field("pagecount") String pagecount, @Field("caste") String caste);


    @FormUrlEncoded
    @POST("matrimonyIdSearch")
    Call<SmartSearchModel> matrimonyIdSearch(@Field("matrimonyId") String MatriID, @Field("txtGender") String gender,
                                             @Field("pagecount") String pagecount, @Field("loginmatriid") String loginmatriid);

    @FormUrlEncoded
    @POST("educationalSearch")
    Call<SmartSearchModel> educationalSearch(@Field("pagecount") String pagecount, @Field("Gender") String Gender, @Field("fromage") String fromage,
                                             @Field("toage") String toage, @Field("education") String education, @Field("txtphoto") String Txtphoto, @Field("loginmatriid") String loginmatriid);

    @FormUrlEncoded
    @POST("specialSearch")
    Call<SmartSearchModel> specialSearch(@Field("pagecount") String pagecount, @Field("Gender") String Gender, @Field("fromage") String fromage,
                                         @Field("toage") String toage, @Field("txtphoto") String Txtphoto, @Field("special_case") String special_case
            , @Field("religion") String religion, @Field("loginmatriid") String loginmatriid, @Field("caste") String caste);


    @FormUrlEncoded
    @POST("locationSearch")
    Call<SmartSearchModel> locationSearch(@Field("pagecount") String pagecount, @Field("Gender") String Gender, @Field("fromage") String fromage,
                                          @Field("toage") String toage, @Field("txtphoto") String Txtphoto, @Field("country") String country, @Field("state") String state
            , @Field("city") String city, @Field("loginmatriid") String loginmatriid, @Field("iam") String iam, @Field("caste") String caste);

    @FormUrlEncoded
    @POST("occupationSearch")
    Call<SmartSearchModel> occupationSearch(@Field("pagecount") String pagecount, @Field("Gender") String Gender, @Field("fromage") String fromage,
                                            @Field("toage") String toage, @Field("txtphoto") String Txtphoto, @Field("occupation") String occupation,
                                            @Field("loginmatriid") String loginmatriid, @Field("caste") String caste);

    @FormUrlEncoded
    @POST("advancedSearch")
    Call<SmartSearchModel> advancedSearch(@Field("pagecount") String pagecount, @Field("Gender") String Gender, @Field("fromage") String fromage,
                                          @Field("toage") String toage, @Field("txtphoto") String Txtphoto, @Field("maritalstatus") String maritalstatus,
                                          @Field("religion") String religion, @Field("country") String country, @Field("state") String state,
                                          @Field("city") String city, @Field("education") String education, @Field("occupation") String occupation,
                                          @Field("loginmatriid") String loginmatriid, @Field("caste") String caste, @Field("iam") String iam);

    @FormUrlEncoded
    @POST("all_notification_list")
    Call<NotificationModel> all_notification_list(@Field("MatriID") String MatriID);

    @FormUrlEncoded
    @POST("delete_notification")
    Call<NotifiDeletModel> delete_notification(@Field("nid") String nid);

    @FormUrlEncoded
    @POST("clearnotification")
    Call<NotifiDeletModel> delete_all_notification(@Field("id") String nid);

    @FormUrlEncoded
    @POST("read_notification")
    Call<NotifiDeletModel> read_notification(@Field("nid") String nid);

    @FormUrlEncoded
    @POST("delete_profile")
    Call<NotifiDeletModel> delete_profile(@Field("user_id") String user_id, @Field("reason") String resion);

    @FormUrlEncoded
    @POST("user_status")
    Call<UserStatusModel> user_status(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("read_message")
    Call<ReadMessageModel> read_message(@Field("rid") String rid);

    @FormUrlEncoded
    @POST("validity_check1")
    Call<ReadMessageModel> checkChatValidity(@Field("user_id") String userId, @Field("to_id") String to_id);

    @FormUrlEncoded
    @POST("viewContactCheck")
    Call<ReadMessageModel> checkContactVisit(@Field("user_id") String userId, @Field("to_id") String to_id);

    @FormUrlEncoded
    @POST("deleteChat")
    Call<ReadMessageModel> deleteChat(@Field("chat_id") String chat_id);

    @FormUrlEncoded
    @POST("visitedListNew")
    Call<MyVisitedModel> myProfileVisitedUsers(@Field("matri_id") String chat_id,
                                               @Field("pagecount") String pagecount);
    // http://clarigoinfotech.co.in/umeed/api/user_status
}