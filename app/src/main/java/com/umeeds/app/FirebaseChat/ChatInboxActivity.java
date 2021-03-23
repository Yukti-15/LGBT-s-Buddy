package com.umeeds.app.FirebaseChat;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.umeeds.app.R;
import com.umeeds.app.activity.ChatActivity;
import com.umeeds.app.activity.MemberShipPlanActivity;
import com.umeeds.app.activity.MoreInfoActivity;
import com.umeeds.app.model.ProfileImageModel;
import com.umeeds.app.model.ReadMessageModel;
import com.umeeds.app.model.UserBlockModel;
import com.umeeds.app.network.UtilsMethod;
import com.umeeds.app.network.database.SharedPrefsManager;
import com.umeeds.app.network.networking.ApiClient;
import com.umeeds.app.network.networking.ApiInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.umeeds.app.network.networking.Constant.IMAGE_LOAD_USER1;
import static com.umeeds.app.network.networking.Constant.MATRI_ID;

public class ChatInboxActivity extends AppCompatActivity {
    public static final String TAG = "ChatInboxActivity";
    String name, userName, onlinestatus, Gender, matrixId, photo, matrixID;
    Firebase reference1, reference2;
    String mUserName;
    String dateToStr;
    TextView matchUserName;
    ImageView chatBackArrow, matchUserImage, onlineIndicator, btEmoji, settingProfile;
    RelativeLayout chatParentView;
    DatabaseReference myRef;
    FirebaseDatabase database;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String msgContent, response;
    String user_Name, user_Image, myId, userMatriId;
    ImageView back_arrow, iv_info;
    String toblocked = "", fromblocked = "";
    TextView tv_block;
    Button ok;
    Dialog dialog;
    private EmojiconEditText mMessageEditText;
    private EmojIconActions emojIcon;
    private View contentRoot;
    private ApiInterface apiInterface;
    private UtilsMethod progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_inbox);
        back_arrow = findViewById(R.id.back_arrow);
        iv_info = findViewById(R.id.iv_info);

        Firebase.setAndroidContext(this);

        dialog = new Dialog(ChatInboxActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.blockuser_view);

        tv_block = dialog.findViewById(R.id.tv_block);
        TextView delete_chat_tv = dialog.findViewById(R.id.delete_chat_tv);
        ok = dialog.findViewById(R.id.ok);


        apiInterface = ApiClient.getInterface();
        progress = new UtilsMethod(this);

        Intent intent = getIntent();
        user_Name = intent.getStringExtra("user_Name");
        //  user_Id = intent.getStringExtra("user_Id");
        user_Image = intent.getStringExtra("user_Image");
        userMatriId = intent.getStringExtra("matriId");
        String chatId = intent.getStringExtra("chatId");
        response = intent.getStringExtra("response");

        myId = SharedPrefsManager.getInstance().getString(MATRI_ID);
        delete_chat_tv.setOnClickListener(v -> {
            reference1.removeValue();
//            deleteChat(chatId);
            dialog.cancel();
            FirebasechatMethod();
        });
        back_arrow.setOnClickListener(v -> finish());

        iv_info.setOnClickListener(v -> BlockShow());
        init();
    }

    public void init() {
        chatBackArrow = findViewById(R.id.back_arrow);
        contentRoot = findViewById(R.id.contentRoot);
        chatBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btEmoji = (ImageView) findViewById(R.id.emoji);
        mMessageEditText = (EmojiconEditText) findViewById(R.id.typemsg);
        emojIcon = new EmojIconActions(ChatInboxActivity.this, contentRoot, mMessageEditText, btEmoji);
        emojIcon.ShowEmojIcon();
        onlineIndicator = findViewById(R.id.imgOnlineIndicator);
        matchUserImage = findViewById(R.id.sellerImage);
        matchUserName = findViewById(R.id.sellerUserName);
        chatParentView = findViewById(R.id.chat_parent_view);
        settingProfile = findViewById(R.id.preview_setting);
        Date today = new Date();
         /*dateToStr = format.format(today);
        System.out.println(dateToStr);
       */
        matchUserName.setText(user_Name);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.logo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform();

        Glide.with(this)
                .load(IMAGE_LOAD_USER1 + user_Image)
                .apply(options)
                .into(matchUserImage);

        matchUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatInboxActivity.this, MoreInfoActivity.class);
                intent.putExtra("martId", userMatriId);
                startActivity(intent);
            }
        });

        matchUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatInboxActivity.this, MoreInfoActivity.class);
                intent.putExtra("martId", userMatriId);
                startActivity(intent);
            }
        });
        FirebasechatMethod();
    }

    private void BlockShow() {
       /* final Dialog dialog = new Dialog(ChatInboxActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.blockuser_view);
         TextView tv_memberShip = dialog.findViewById(R.id.tv_memberShip);
         tv_block = dialog.findViewById(R.id.tv_block);
         Button ok = dialog.findViewById(R.id.ok);
*/
        tv_block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_block.getText().toString().equals("Block")) {
                    save_user_block(SharedPrefsManager.getInstance().getString(MATRI_ID), userMatriId);
                } else {
                    user_unblock(SharedPrefsManager.getInstance().getString(MATRI_ID), userMatriId);
                }
                dialog.dismiss();
                /*
                FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                Query query = rootRef.collection("users").whereArrayContains("blockedUsers", uid);
                 */
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        blockunblokstatus(SharedPrefsManager.getInstance().getString(MATRI_ID), userMatriId);
    }

    public void FirebasechatMethod() {
        //                                                                                      me               friend
        reference1 = new Firebase("https://umeed-2d7e6.firebaseio.com/Chat_message/" + myId + "_" + userMatriId);
        //                                                                                      friend           me
        reference2 = new Firebase("https://umeed-2d7e6.firebaseio.com/Chat_message/" + userMatriId + "_" + myId);

        final RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.chat_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        final List<ChatMsg> msgDtoList = new ArrayList<ChatMsg>();
       /* ChatMsg msgDto = new ChatMsg(ChatMsg.MSG_TYPE_RECEIVED,  "hi", dateToStr);
        msgDtoList.add(msgDto);
*/
        final ChatMsgAdapter chatMsgAdapter = new ChatMsgAdapter(msgDtoList);
        mRecyclerView.setAdapter(chatMsgAdapter);
        mMessageEditText.setSelection(mMessageEditText.getText().length());
        ImageView msgSendButton = (ImageView) findViewById(R.id.sendbtn);
        msgSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (response.equals("true")) {
                    if (fromblocked.equals("1") || toblocked.equals("1")) {
                        //   Toast.makeText(ChatInboxActivity.this,"Please Unblock the user",Toast.LENGTH_SHORT).show();
                    } else {
                        msgContent = mMessageEditText.getText().toString();
                        long stamp = System.currentTimeMillis();
                        SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
                        Calendar calobj = Calendar.getInstance();
                        dateToStr = format.format(calobj.getTime());
                        System.out.println(format.format(calobj.getTime()));
                        if (!TextUtils.isEmpty(msgContent)) {
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("message", msgContent);
                            //                  friend
                            map.put("sender_Id", userMatriId);
                            map.put("photo", photo);
                            map.put("status", "1");
                            map.put("timestamp", dateToStr);
                            //   map.put("mart_id", mart_id);
                            map.put("name", user_Name);
                            reference1.push().setValue(map);
                            reference2.push().setValue(map);
                            // SaveChatApi(matrixID,matrixId,msgContent);
                            save_chat(SharedPrefsManager.getInstance().getString(MATRI_ID), userMatriId, msgContent);

                        }
                    }
                } else {
                    noContainShow();
                }

            }
        });

        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String timestamp = map.get("timestamp").toString();
                String userName1 = map.get("sender_Id").toString();

                if (fromblocked.equals("1") || toblocked.equals("1")) {
                    //   Toast.makeText(ChatInboxActivity.this,"Please Unblock the user",Toast.LENGTH_SHORT).show();
                } else {
                    // me
                    if (userName1.equals(myId)) {
                        ChatMsg msgDto = new ChatMsg(ChatMsg.MSG_TYPE_RECEIVED, message, timestamp);
                        msgDtoList.add(msgDto);
                        int newMsgPosition = msgDtoList.size() - 1;
                        chatMsgAdapter.notifyItemInserted(newMsgPosition);
                        mRecyclerView.scrollToPosition(newMsgPosition);
                        mMessageEditText.setText("You:-\n" + message);
                        mMessageEditText.setText("");
                        //addMessageBox("You:-\n" + message, 1);
                        //addMessageBox();
                    } else {
                        Log.i("rahul", "else....yes");
                        ChatMsg msgDto = new ChatMsg(ChatMsg.MSG_TYPE_SENT, message, timestamp);
                        msgDtoList.add(msgDto);
                        int newMsgPosition = msgDtoList.size() - 1;
                        chatMsgAdapter.notifyItemInserted(newMsgPosition);
                        mRecyclerView.scrollToPosition(newMsgPosition);
                        mMessageEditText.setText(UserDetails.chatWith + message);
                        mMessageEditText.setText("");
                        //addMessageBox(UserDetails.chatWith + ":-\n" + message, 2);
                    }
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    public void addMessageBox(String message, int type) {
        TextView textView = new TextView(ChatInboxActivity.this);
        textView.setText(message);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 10);
        textView.setLayoutParams(lp);

        if (type == 1) {
            textView.setBackgroundResource(R.drawable.ic_action_send_now);
        } else {
            textView.setBackgroundResource(R.drawable.ic_lock_gray);
        }
    }


    private void save_chat(String from_id, String to_id, String message) {
        apiInterface.save_chat(from_id, to_id, message).enqueue(new Callback<ProfileImageModel>() {
            @Override
            public void onResponse(@NonNull Call<ProfileImageModel> call, @NonNull Response<ProfileImageModel> response) {
                if (response.isSuccessful()) {
                    ProfileImageModel profileModel = response.body();
                    if (profileModel != null) {
                        progress.cancleDialog();
                        String respons = profileModel.getResponse();
                        if (respons.equals("true")) {

                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProfileImageModel> call, @NonNull Throwable t) {
                Toast.makeText(ChatInboxActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
                progress.cancleDialog();

            }
        });

    }


    //  from_id,to_id
    private void save_user_block(String from_id, String to_id) {

        apiInterface.save_user_block(from_id, to_id).enqueue(new Callback<ProfileImageModel>() {
            @Override
            public void onResponse(Call<ProfileImageModel> call, Response<ProfileImageModel> response) {
                if (response.isSuccessful()) {
                    ProfileImageModel profileModel = response.body();
                    if (profileModel != null) {
                        progress.cancleDialog();
                        String respons = profileModel.getResponse();
                        if (respons.equals("true")) {
                            Toast.makeText(ChatInboxActivity.this, profileModel.getMessage(), Toast.LENGTH_LONG).show();
                            blockunblokstatus(SharedPrefsManager.getInstance().getString(MATRI_ID), userMatriId);

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileImageModel> call, Throwable t) {
                Toast.makeText(ChatInboxActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
                progress.cancleDialog();

            }
        });

    }


    private void user_unblock(String from_id, String to_id) {

        apiInterface.user_unblock(from_id, to_id).enqueue(new Callback<ProfileImageModel>() {
            @Override
            public void onResponse(Call<ProfileImageModel> call, Response<ProfileImageModel> response) {
                if (response.isSuccessful()) {
                    ProfileImageModel profileModel = response.body();
                    if (profileModel != null) {
                        progress.cancleDialog();
                        String respons = profileModel.getResponse();
                        if (respons.equals("true")) {
                            blockunblokstatus(SharedPrefsManager.getInstance().getString(MATRI_ID), userMatriId);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileImageModel> call, Throwable t) {
                Toast.makeText(ChatInboxActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
                progress.cancleDialog();

            }
        });

    }


    private void blockunblokstatus(String fromMatriID, String toMatriID) {

        apiInterface.blockunblokstatus(fromMatriID, toMatriID).enqueue(new Callback<UserBlockModel>() {
            @Override
            public void onResponse(Call<UserBlockModel> call, Response<UserBlockModel> response) {
                if (response.isSuccessful()) {
                    UserBlockModel profileModel = response.body();
                    if (profileModel != null) {
                        progress.cancleDialog();
                        String respons = profileModel.getResponse();
                        if (respons.equals("true")) {
                            fromblocked = profileModel.getFromblocked();
                            toblocked = profileModel.getToblocked();
                            if (fromblocked.equals("1")) {
                                tv_block.setText("Unblock");
                            } else {
                                tv_block.setText("Block");
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<UserBlockModel> call, Throwable t) {
                Toast.makeText(ChatInboxActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
                progress.cancleDialog();
            }
        });

    }


    private void deleteChat(String chatID) {

        apiInterface.deleteChat(chatID).enqueue(new Callback<ReadMessageModel>() {
            @Override
            public void onResponse(Call<ReadMessageModel> call, Response<ReadMessageModel> response) {
                if (response.isSuccessful()) {
                    ReadMessageModel profileModel = response.body();
                    if (profileModel != null) {
                        progress.cancleDialog();
                        if (profileModel.getResponse().equals("true")) {
                            Intent intent = new Intent(ChatInboxActivity.this, ChatActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<ReadMessageModel> call, Throwable t) {
                Toast.makeText(ChatInboxActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
                progress.cancleDialog();
            }
        });

    }

    private void noContainShow() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.no_contain_view);

        TextView tv_memberShip = dialog.findViewById(R.id.tv_memberShip);
        TextView tv_upgrade = dialog.findViewById(R.id.tv_upgrade);
        Button ok = dialog.findViewById(R.id.ok);


        tv_memberShip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tv_upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(new Intent(ChatInboxActivity.this, MemberShipPlanActivity.class));
                startActivity(intent);
                dialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


}
