package com.umeeds.app.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.paypal.android.sdk.payments.ProofOfPayment;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.umeeds.app.MainActivity;
import com.umeeds.app.R;
import com.umeeds.app.adapter.BuyPlanAdapter;
import com.umeeds.app.ccAvenue.AvenuesParams;
import com.umeeds.app.ccAvenue.ForeignPaymentActivity;
import com.umeeds.app.model.BuyPlanModel;
import com.umeeds.app.model.MyPlanModel;
import com.umeeds.app.model.NotifiDeletModel;
import com.umeeds.app.model.PaytmModel;
import com.umeeds.app.model.PaytmSccessModel;
import com.umeeds.app.model.UserStatusModel;
import com.umeeds.app.network.UtilsMethod;
import com.umeeds.app.network.database.SharedPrefsManager;
import com.umeeds.app.network.networking.ApiClient;
import com.umeeds.app.network.networking.ApiInterface;
import com.umeeds.app.network.networking.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.umeeds.app.network.networking.Constant.CONFIRM_EMAIL;
import static com.umeeds.app.network.networking.Constant.LOGIN_ID;
import static com.umeeds.app.network.networking.Constant.MATRI_ID;
import static com.umeeds.app.network.networking.Constant.MOBILE;
import static com.umeeds.app.network.networking.Constant.STATUS;

public class BuyMemberShipPlanActivity extends AppCompatActivity implements PaymentResultListener, BuyPlanAdapter.BuyPlanClickListener {

//    AsyncHttpClient client;

    public static String checkSum, orderId, payStatus, amountNew, emailid, custId, paytmAmount;
    private final int PAYPAL_REQUEST_CODE = 1254;
//    private BraintreeFragment braintreeFragment;

    //    private static final PayPalConfiguration config = new PayPalConfiguration()
//            .environment(PayPalConfiguration.ENVIRONMENT_PRODUCTION)
//            .clientId(Config.PAYPAL_CLIENT_ID)
//            .merchantName("Example Merchant")
//            .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
//            .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));
//    ;
    ProgressBar progressBar;
    int userId;
    ImageView sellerImage, iv_back, iv_home;
    // Button bt_paynow;
    TextView tv_planType, tv_amount, tv_validity, tv_planchatcontact, tv_contact;
    String plan, planId, amount, validity, chatcontact, contact, plantype, email, mobileNo, planImageUrl;
    RecyclerView rc_plan;
    BuyPlanAdapter buyPlanAdapter;
    ProgressBar progress_bar;
    int amountInt, amountMultiply;
    String ccavenueAmmount;
    double paypalDollarAmtsend;
    private ApiInterface apiInterface;
    private UtilsMethod progress;
    private GifImageView gif_iv;
    private RelativeLayout note_rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_member_ship_plan);
        Checkout.preload(getApplicationContext());
//        Intent payPalIntent = new Intent(this, PayPalService.class);
//        payPalIntent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
//        startService(payPalIntent);

//        try {
//            braintreeFragment = BraintreeFragment.newInstance(this, Config.PAYPAL_CLIENT_ID);
//            // mBraintreeFragment is ready to use!
//        } catch (InvalidArgumentException e) {
//            // There was an issue with your authorization string.
//        }
        // bt_paynow = findViewById(R.id.bt_paynow);
        tv_planType = findViewById(R.id.tv_planType);
        tv_amount = findViewById(R.id.tv_amount);
        tv_validity = findViewById(R.id.tv_validity);
        tv_planchatcontact = findViewById(R.id.tv_planchatcontact);
        tv_contact = findViewById(R.id.tv_contact);
        sellerImage = findViewById(R.id.sellerImage);
        rc_plan = findViewById(R.id.rc_plan);
        iv_back = findViewById(R.id.iv_back);
        iv_home = findViewById(R.id.iv_home);
        progress_bar = findViewById(R.id.progress_bar);
        gif_iv = findViewById(R.id.gif_iv);
        note_rl = findViewById(R.id.note_rl);

        apiInterface = ApiClient.getInterface();
        progress = new UtilsMethod(this);
        tv_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(BuyMemberShipPlanActivity.this, SampleActivity.class));
                convertAndPayByPaypal();
//                startPayment(paypalDollarAmt * amountInt);
            }
        });

        if (ContextCompat.checkSelfPermission(BuyMemberShipPlanActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(BuyMemberShipPlanActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rc_plan.setLayoutManager(layoutManager);
        buyPlanAdapter = new BuyPlanAdapter(this, this);
        rc_plan.setAdapter(buyPlanAdapter);

        iv_back.setOnClickListener(v -> finish());

        iv_home.setOnClickListener(v -> {
            Intent intent = new Intent(BuyMemberShipPlanActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        Intent intent = getIntent();
        if (intent != null) {
            planId = intent.getStringExtra("planId");
            plan = intent.getStringExtra("plan");
            amount = intent.getStringExtra("amount");
            validity = intent.getStringExtra("validity");
            chatcontact = intent.getStringExtra("chatcontact");
            contact = intent.getStringExtra("contact");
            plantype = intent.getStringExtra("plantype");
            planImageUrl = intent.getStringExtra("planImage");

            email = SharedPrefsManager.getInstance().getString(CONFIRM_EMAIL);
            mobileNo = SharedPrefsManager.getInstance().getString(MOBILE);

            int gst = Integer.parseInt(amount) * 18 / 100;
            if (planImageUrl != null) {

                Glide.with(BuyMemberShipPlanActivity.this)
                        .load(planImageUrl)
                        .into(gif_iv);
            }

            amountInt = Integer.parseInt(amount) + gst;
            amountMultiply = amountInt * 100;
            ccavenueAmmount = String.valueOf(amountInt);
        }
        progress_bar.setVisibility(View.VISIBLE);
        getPaymentOption();
        tv_planType.setText("Plan Type : " + plan);


        TimeZone tz = TimeZone.getDefault();
        System.out.println("TimeZone   " + tz.getDisplayName(false, TimeZone.SHORT) + " Timezone id :: " + tz.getID());
        String timeZone = tz.getID();
        if (timeZone.equals("Asia/Kolkata") || timeZone.equals("Asia/Calcutta")) {
            tv_amount.setText("Amount : INR " + amount);
            note_rl.setVisibility(View.GONE);
        } else {
            tv_amount.setText("Amount : USD " + amount);
            note_rl.setVisibility(View.VISIBLE);

        }
        tv_validity.setText("Validity : " + validity + " Days");
        tv_planchatcontact.setText("Plan Chat Contact : " + chatcontact);
        tv_contact.setText("Allowed Contact : " + contact);
        generatechecksum(SharedPrefsManager.getInstance().getString(MATRI_ID), amount);
    }

    private void getPaymentOption() {
        apiInterface.getPaymentOption().enqueue(new Callback<BuyPlanModel>() {
            @Override
            public void onResponse(Call<BuyPlanModel> call, Response<BuyPlanModel> response) {
                if (response.isSuccessful()) {
                    BuyPlanModel buyPlanModel = response.body();
                    if (buyPlanModel != null) {
                        progress.cancleDialog();
                        progress_bar.setVisibility(View.GONE);

                        String respons = buyPlanModel.getResponse();
                        if (respons.equals("true")) {
                            List<BuyPlanModel.BuyPlanData> memberShipPlanDataList = buyPlanModel.getBuyPlanDataList();
                            if (memberShipPlanDataList.size() > 0) {
                                buyPlanAdapter.addCustomerList(memberShipPlanDataList);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BuyPlanModel> call, Throwable t) {
                Toast.makeText(BuyMemberShipPlanActivity.this, "Something is wrong", Toast.LENGTH_LONG).show();
                progress.cancleDialog();
                progress_bar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onEvent(BuyPlanModel.BuyPlanData buyPlanData, View view) {
        switch (view.getId()) {
            case R.id.layout_item:
                if (buyPlanData.getOption_type().equals("CCAvenue")) {
//                    startPayment();
                    startPaymentWithCCAvenue();
                } else {
//                    click_Event();
                    TimeZone tz = TimeZone.getDefault();
                    System.out.println("TimeZone   " + tz.getDisplayName(false, TimeZone.SHORT) + " Timezone id :: " + tz.getID());
                    String timeZone = tz.getID();
                    if (timeZone.equals("Asia/Kolkata") || timeZone.equals("Asia/Calcutta")) {
                        startPayment(Integer.parseInt(String.valueOf(amountMultiply)), "INR");
                    } else {
                        startPayment((Integer.parseInt(amount) * 100), "USD");
                    }
                    progress.showDialog();
                }
                break;
        }
    }

    private void startPaymentWithCCAvenue() {

        Intent intent = new Intent(this, ForeignPaymentActivity.class);
//          intent.putExtra(AvenuesParams.ACCESS_CODE, "AVJQ89HA89BH45QJHB");
        intent.putExtra(AvenuesParams.ACCESS_CODE, "AVLY90HB25AA87YLAA");
        intent.putExtra(AvenuesParams.MERCHANT_ID, "244890");
        Random rand = new Random();
        // intent.putExtra(AvenuesParams.ORDER_ID, "123" + String.format("%04d", rand.nextInt(10000)));
        intent.putExtra(AvenuesParams.CURRENCY, "INR");
        intent.putExtra(AvenuesParams.AMOUNT, ccavenueAmmount);

        intent.putExtra(AvenuesParams.ORDER_ID, SharedPrefsManager.getInstance().getString(MATRI_ID) + "I" + planId + "I" + String.format("%04d", rand.nextInt(10000)));

        intent.putExtra(AvenuesParams.REDIRECT_URL, "https://www.umeed.app/merchant/ccavResponseHandler.php");
        intent.putExtra(AvenuesParams.CANCEL_URL, "https://www.umeed.app/merchant/ccavResponseHandler.php");
        intent.putExtra(AvenuesParams.RSA_KEY_URL, "https://www.umeed.app/merchant/GetRSA.php");

       /*intent.putExtra(AvenuesParams.ACCESS_CODE, "AVRK88GK71AQ08KRQA");
        intent.putExtra(AvenuesParams.MERCHANT_ID, "145788");
        Random rand = new Random();
        intent.putExtra(AvenuesParams.ORDER_ID, "123" + String.format("%04d", rand.nextInt(10000)));
        intent.putExtra(AvenuesParams.CURRENCY, "USD");
        intent.putExtra(AvenuesParams.AMOUNT, "1000");
        intent.putExtra(AvenuesParams.REDIRECT_URL,"http://13.232.213.21/merchant/ccavResponseHandler.php");
        intent.putExtra(AvenuesParams.CANCEL_URL,  "http://13.232.213.21/merchant/ccavResponseHandler.php");
        intent.putExtra(AvenuesParams.RSA_KEY_URL, "http://13.232.213.21/merchant/GetRSA.php");*/
        startActivity(intent);
    }


    public void startPayment(int v, String usd) {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;
        final Checkout co = new Checkout();
        try {
            JSONObject options = new JSONObject();
            options.put("name", plan);
            options.put("description", "Validity : " + validity + " Days");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", usd);
            options.put("amount", v);
            options.put("callback_url", "https://umeed.app/orders/payment");

            JSONObject preFill = new JSONObject();
            preFill.put("email", email);
            preFill.put("contact", mobileNo);
            options.put("prefill", preFill);
            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }


    @SuppressWarnings("unused")
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
            razorpaysuccess(SharedPrefsManager.getInstance().getString(MATRI_ID), amount, razorpayPaymentID, "SUCCESS", planId);
        } catch (Exception e) {
            Log.e("pay...", "Exception in onPaymentSuccess", e);
            progress.cancleDialog();
        }
    }

    @SuppressWarnings("unused")
    @Override
    public void onPaymentError(int code, String response) {
        progress.cancleDialog();
        try {
            Log.e("rozar pay", +code + " " + response);
            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("pay..", "Exception in onPaymentError", e);
        }
    }

    private void razorpaysuccess(final String matriId, String amount, String transaction, String status, String planId) {
        apiInterface.razorpaysuccess(matriId, amount, transaction, status, planId).enqueue(new Callback<NotifiDeletModel>() {
            @Override
            public void onResponse(Call<NotifiDeletModel> call, Response<NotifiDeletModel> response) {
                progress.cancleDialog();
                if (response.isSuccessful()) {
                    NotifiDeletModel buyPlanModel = response.body();
                    if (buyPlanModel != null) {
                        progress_bar.setVisibility(View.GONE);
                        String respons = buyPlanModel.getResponse();
                        if (respons.equals("true")) {
                            Toast.makeText(BuyMemberShipPlanActivity.this, "Success", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(BuyMemberShipPlanActivity.this, "Failed", Toast.LENGTH_LONG).show();
                        }
                        get_my_current_plan(matriId);
                    }
                }
            }

            @Override
            public void onFailure(Call<NotifiDeletModel> call, Throwable t) {
                Toast.makeText(BuyMemberShipPlanActivity.this, "Something is wrong", Toast.LENGTH_LONG).show();
                progress_bar.setVisibility(View.GONE);
                progress.cancleDialog();
            }
        });
    }

    ////////////////////////////get checksum from server//////////////////////

    private void generatechecksum(String matriId, String amount) {
        apiInterface.paytmCheckSum(matriId, amount).enqueue(new Callback<PaytmModel>() {
            @Override
            public void onResponse(Call<PaytmModel> call, Response<PaytmModel> response) {
                if (response.isSuccessful()) {
                    PaytmModel paytmModle = response.body();
                    checkSum = paytmModle.getCheckSum();
                    orderId = paytmModle.getOrderId();
                    custId = paytmModle.getCusId();
                    amountNew = paytmModle.getAmount();
                    emailid = paytmModle.getEmailid();
                    System.out.println("aaaaaa" + checkSum + "\n" + orderId + "\n" + custId + "\n" + amountNew + "\n" + emailid);
                } else {
                    Toast.makeText(BuyMemberShipPlanActivity.this, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PaytmModel> call, Throwable t) {
                Toast.makeText(BuyMemberShipPlanActivity.this, "Something is wrong", Toast.LENGTH_LONG).show();
                progress_bar.setVisibility(View.GONE);
            }
        });
    }

    private void click_Event() {
        PaytmPGService Service = PaytmPGService.getProductionService();
        // PaytmPGService Service = PaytmPGService.getStagingService();
        Map<String, String> paramMap = new HashMap<String, String>();
        // paramMap.put("MID", "NUOthJ47980119138392");
        paramMap.put("MID", "GkTalN93579253223128");
        paramMap.put("ORDER_ID", orderId);
        paramMap.put("CUST_ID", custId);
        paramMap.put("EMAIL", emailid);
        paramMap.put("CHANNEL_ID", "WAP");
        paramMap.put("TXN_AMOUNT", amountNew);
        paramMap.put("WEBSITE", "DEFAULT");
        paramMap.put("INDUSTRY_TYPE_ID", "Retail");
        paramMap.put("CALLBACK_URL", Constant.CHECKSUM_VARIFICATION + orderId);
        paramMap.put("CHECKSUMHASH", checkSum);

        PaytmOrder Order = new PaytmOrder((HashMap<String, String>) paramMap);
        Service.initialize(Order, null);
        Service.startPaymentTransaction(BuyMemberShipPlanActivity.this, true, true, new
                PaytmPaymentTransactionCallback() {
                    public void someUIErrorOccurred(String inErrorMessage) {
                        //   Toast.makeText(getApplicationContext(), "UI Error " + inErrorMessage, Toast.LENGTH_LONG).show();
                    }

                    public void onTransactionResponse(Bundle inResponse) {
                        // Toast.makeText(getApplicationContext(), "Payment Transaction response " + inResponse.toString(), Toast.LENGTH_LONG).show();
                        String status = inResponse.getString("STATUS");
                        String ORDERID = inResponse.getString("ORDERID");
                        String TXNAMOUNT = inResponse.getString("TXNAMOUNT");
                        String TXNID = inResponse.getString("TXNID");

                        if (status.equals("TXN_SUCCESS")) {
                            // Toast.makeText(getApplicationContext(), "Payment transaction success" + inResponse.toString(), Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(), "Payment transaction success", Toast.LENGTH_LONG).show();
                            paytmsuccessApi(SharedPrefsManager.getInstance().getString(MATRI_ID), TXNAMOUNT, ORDERID, emailid, TXNID, planId, status);
                        } else {
                            //Toast.makeText(getApplicationContext(), "Payment transaction failed" + inResponse.toString(), Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(), "Payment transaction failed", Toast.LENGTH_LONG).show();
                        }

                        //      Bundle[{STATUS=TXN_SUCCESS, CHECKSUMHASH=08QNX0yk7+/laOULVZMNyKy20It2q6vEXqv5RuoMzMYxQNYybggn6vqDAGyG7EzFjhJXTrVH2Oa2YVgpZA4xterTNyDYziitzzbmMRPcHvU=, BANKNAME=JPMORGAN CHASE BANK, ORDERID=MAT964092, TXNAMOUNT=10.00, TXNDATE=2019-11-26 17:48:10.0, MID=NUOthJ47980119138392, TXNID=20191126111212800110168860001042235, RESPCODE=01, PAYMENTMODE=DC, BANKTXNID=777001402741078, CURRENCY=INR, GATEWAYNAME=HDFC, RESPMSG=Txn Success}]                       // paytmsuccessApi();
                    }

                    public void networkNotAvailable() {
                        //  Toast.makeText(getApplicationContext(), "Network connection error: Check your internet connectivity", Toast.LENGTH_LONG).show();
                    }

                    public void clientAuthenticationFailed(String inErrorMessage) {
                        //   Toast.makeText(getApplicationContext(), "Authentication failed: Server error" + inErrorMessage.toString(), Toast.LENGTH_LONG).show();
                    }

                    public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
                        Toast.makeText(getApplicationContext(), "Unable to load webpage" + inFailingUrl, Toast.LENGTH_LONG).show();
                    }

                    public void onBackPressedCancelTransaction() {
                        Toast.makeText(getApplicationContext(), "Transaction cancelled", Toast.LENGTH_LONG).show();
                    }

                    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                        Toast.makeText(getApplicationContext(), "Transaction Cancelled" + inResponse.toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }


    private void paytmsuccessApi(final String matrixID, String amount, String orderId, String email, String balance_transaction, String planid, String status) {
        apiInterface.paytmSuccessApi(matrixID, amount, orderId, email, balance_transaction, planid, status).enqueue(new Callback<PaytmSccessModel>() {
            @Override
            public void onResponse(Call<PaytmSccessModel> call, Response<PaytmSccessModel> response) {
                if (response.isSuccessful()) {
                    PaytmSccessModel paytmSccessModel = response.body();
                    if (paytmSccessModel.equals(true)) {
                        String message = paytmSccessModel.getMessage();
                        // Toast.makeText(BuyMemberShipPlanActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                    get_my_current_plan(matrixID);
                }
            }

            @Override
            public void onFailure(Call<PaytmSccessModel> call, Throwable t) {
                Toast.makeText(BuyMemberShipPlanActivity.this, "Something is wrong", Toast.LENGTH_LONG).show();
                //  progress_bar.setVisibility(View.GONE);
            }
        });
    }

    private void get_my_current_plan(String matriId) {
        //  progress_bar.setVisibility(VISIBLE);
        apiInterface.get_my_current_plan(matriId).enqueue(new Callback<MyPlanModel>() {
            @Override
            public void onResponse(Call<MyPlanModel> call, Response<MyPlanModel> response) {
                if (response.isSuccessful()) {
                    MyPlanModel myPlanModel = response.body();
                    if (myPlanModel != null) {
                        String respons = myPlanModel.getResponse();
                        if (respons.equals("true")) {
                            MyPlanModel.MyPlanData myPlanData = myPlanModel.getMyPlanData();
                            SharedPrefsManager.getInstance().setString(STATUS, myPlanData.getStatus());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MyPlanModel> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        user_status(SharedPrefsManager.getInstance().getString(LOGIN_ID));
    }

    private void user_status(String user_id) {
        apiInterface.user_status(user_id).enqueue(new Callback<UserStatusModel>() {
            @Override
            public void onResponse(Call<UserStatusModel> call, Response<UserStatusModel> response) {
                if (response.isSuccessful()) {
                    UserStatusModel userStatusModel = response.body();
                    if (userStatusModel != null) {
                        String respons = userStatusModel.getResponse();
                        String status = userStatusModel.getStatus();
                        if (status.equals("Active")) {

                        } else {
                            SharedPrefsManager.getInstance().clearPrefs();
                            Intent intent = new Intent(BuyMemberShipPlanActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<UserStatusModel> call, Throwable t) {
                Toast.makeText(BuyMemberShipPlanActivity.this, "Something is wrong", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    private void processPayPalPayment(String amount) {
        progress.cancleDialog();
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal("0.01"), "USD",
                "UMEED PLAN UPDATE", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
//        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    //paypal response
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    try {
                        progress.showDialog();
                        ProofOfPayment proof = confirmation.getProofOfPayment();
                        PayPalPayment payment = confirmation.getPayment();
//                        if (paypalDollarAmtsend.equals(payment.toJSONObject().getString("amount"))) {
                        paypalPaymentSuccess(SharedPrefsManager.getInstance().getString(MATRI_ID),
                                payment.toJSONObject().getString("amount") + " USD",
                                proof.toJSONObject().getString("id"), proof.toJSONObject().getString("state"),
                                planId);
//                        }
//                        else {
//                            progress.cancleDialog();
//                            Toast.makeText(this, "Something wrong please contact to admin", Toast.LENGTH_LONG).show();
//                        }
                        Log.i("proof", proof.toJSONObject().toString(4));
                        Log.i("proof", proof.toJSONObject().getString("id"));
                        Log.i("proof", proof.toJSONObject().getString("state"));
                        Log.i("pymnt", payment.toJSONObject().getString("amount"));
                        Log.i("pymnt", payment.toJSONObject().toString(4));
                    } catch (JSONException e) {
                        progress.cancleDialog();
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED)
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
        }
//for brain tree
//        else if (requestCode == 512) {
//            if (resultCode == Activity.RESULT_OK) {
//
//                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
//// here you will get nonce for the payment
//                String nNonce = result.getPaymentMethodNonce().getNonce();
//// you also can check payment type is from paypal or Card
//                String payment_type = result.getPaymentMethodType().getCanonicalName();
//
//
//                if (nNonce != null && payment_type != null) {
//                    System.out.print("Payment done, Send this nonce to server");
////                    postNonceToYourServer(nNonce,payment_type,user_id);
//                }
//
//            } else if (resultCode == Activity.RESULT_CANCELED) {
//                System.out.print("Payment cancelled by user, go back to previous activity");
//
//            } else {
//                // handle errors here, an exception may be available in
//                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
//                System.out.print("Get some unknown error, go back to previous activity");
//            }
//        }
    }

    // convert int to usd and pay by paypal.
    private void convertAndPayByPaypal() {
        String url = "https://api.exchangeratesapi.io/latest?symbols=USD&base=INR";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject person = new JSONObject(response);
                    JSONObject data = person.getJSONObject("rates");
                    double paypalDollarAmt = data.getDouble("USD");
                    if (amountInt != 0) {
                        //send to paypal
                        paypalDollarAmtsend = paypalDollarAmt * amountMultiply;
                        Log.e("testamount..", String.valueOf(paypalDollarAmt));
                        Log.e("testamount..", String.valueOf(paypalDollarAmtsend));
//                        processPayPalPayment(String.valueOf(paypalDollarAmt * amountInt));
//                        getClientToken();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, error -> Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_LONG).show());
        requestQueue.add(jsonObjectRequest);
    }

    private void paypalPaymentSuccess(final String matriId, String amount, String transaction, String status, String planId) {
        apiInterface.paypalPaymentSuccess(matriId, amount, transaction, status, planId).enqueue(new Callback<NotifiDeletModel>() {
            @Override
            public void onResponse(Call<NotifiDeletModel> call, Response<NotifiDeletModel> response) {
                progress.cancleDialog();
                if (response.isSuccessful()) {
                    NotifiDeletModel buyPlanModel = response.body();
                    if (buyPlanModel != null) {
                        progress_bar.setVisibility(View.GONE);
                        String respons = buyPlanModel.getResponse();

                        if (respons.equals("true")) {
                            Toast.makeText(BuyMemberShipPlanActivity.this, "Payment transaction success", Toast.LENGTH_LONG).show();
                            paymentSuccessDialog(amount);
                        } else {
                            Toast.makeText(BuyMemberShipPlanActivity.this, "Payment transaction failed", Toast.LENGTH_LONG).show();
                        }
                        get_my_current_plan(matriId);
                    }
                }
            }

            @Override
            public void onFailure(Call<NotifiDeletModel> call, Throwable t) {
                Toast.makeText(BuyMemberShipPlanActivity.this, "Something is wrong", Toast.LENGTH_LONG).show();
                progress_bar.setVisibility(View.GONE);
                progress.cancleDialog();

            }
        });
    }

    private void paymentSuccessDialog(String amount) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_payment_done);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        TextView tv_plandisplayname, tv_planamount, tv_planduration, tv_plannoofcontacts, tv_planchatcontact;

        tv_plandisplayname = dialog.findViewById(R.id.tv_plandisplayname);
        tv_planamount = dialog.findViewById(R.id.tv_planamount);
        tv_planduration = dialog.findViewById(R.id.tv_planduration);
        tv_plannoofcontacts = dialog.findViewById(R.id.tv_plannoofcontacts);
        tv_planchatcontact = dialog.findViewById(R.id.tv_planchatcontact);

        tv_plandisplayname.setText("Plan Type : " + plantype);
        tv_planamount.setText("Amount USD : " + amount.substring(0, 5));
        tv_planduration.setText("Validity : " + validity + " Days");
        tv_plannoofcontacts.setText("Allowed Contact : " + contact);
        tv_planchatcontact.setText("Plan Chat Contact : " + chatcontact);
        dialog.findViewById(R.id.ok_btn).setOnClickListener(view -> {
            dialog.dismiss();
            Intent intent = new Intent(BuyMemberShipPlanActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }

//    private void getClientToken() {
////        progressBar.show();
//        client = new AsyncHttpClient();
//        client.get("http://clarigo.host/BraintreePayments/main.php", new TextHttpResponseHandler() {
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                Log.e("SOooo", "Sorry");
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, String responseString) {
//                try {
////                    TokenResponse response = new Gson().fromJson(
////                            responseString, new TypeToken<TokenResponse>() {
////                            }.getType());
//                    Log.e("hashKey", responseString);
//                    // whenewer you get token from the server, pass it below in DropInRequest
//                    DropInRequest dropInRequest = new DropInRequest().clientToken(responseString);
//                    dropInRequest.amount("100");
//                    startActivityForResult(dropInRequest.getIntent(BuyMemberShipPlanActivity.this), 512);
//                    //progressBar.dismiss();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
}
