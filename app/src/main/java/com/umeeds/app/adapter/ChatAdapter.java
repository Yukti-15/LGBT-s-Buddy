package com.umeeds.app.adapter;

import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.umeeds.app.FirebaseChat.ChatInboxActivity;
import com.umeeds.app.R;
import com.umeeds.app.activity.MemberShipPlanActivity;
import com.umeeds.app.fragment.ChatFragment;
import com.umeeds.app.model.AcceptModel;
import com.umeeds.app.model.ReadMessageModel;
import com.umeeds.app.network.database.SharedPrefsManager;
import com.umeeds.app.network.networking.ApiClient;
import com.umeeds.app.network.networking.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.umeeds.app.network.networking.Constant.IMAGE_LOAD_USER1;
import static com.umeeds.app.network.networking.Constant.MATRI_ID;
import static com.umeeds.app.network.networking.Constant.STATUS;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.CustomerViewHolder> {

    private final FragmentActivity context;
    private final Fragment fragment;
    private final ApiInterface apiInterface;
    private final ProgressBar progressBar;
    private List<AcceptModel.AcceptData> chatDataList;

    public ChatAdapter(FragmentActivity context, Fragment fragment, List<AcceptModel.AcceptData> chatDataList, ProgressBar progress_bar) {
        this.context = context;
        this.fragment = fragment;
        this.chatDataList = chatDataList;
        apiInterface = ApiClient.getInterface();
        progressBar = progress_bar;
    }


    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_inbox_list, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        final AcceptModel.AcceptData customerList = chatDataList.get(position);

        holder.name.setText(customerList.getMatriId());
        holder.chat_message.setText(customerList.getGender());

        String rid = customerList.getMsg().getRid();

        try {
            String readStatus = customerList.getMsg().getReaded();
            if (readStatus.equalsIgnoreCase("No")) {
                holder.name.setTextColor(ContextCompat.getColor(context, R.color.green));
            } else {
                holder.name.setTextColor(ContextCompat.getColor(context, R.color.red));
            }
        } catch (Exception e) {

        }

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.no_photo)
                .error(R.drawable.no_photo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH).dontAnimate()
                .dontTransform();

        Glide.with(context)
                .load(IMAGE_LOAD_USER1 + customerList.getPhoto1())
                .apply(options)
                .into(holder.user_image);

        holder.layout_item.setOnClickListener(v -> {
            if (SharedPrefsManager.getInstance().getString(STATUS) != null) {
//                String status = SharedPrefsManager.getInstance().getString(STATUS);
//
//                int noOfchatcontact = Integer.parseInt(noOfchatcontacts);
                checkChatValidity(SharedPrefsManager.getInstance().getString(MATRI_ID), customerList.getMatriId(), customerList, position);

//                if (status.equals("Paid")) {
//                    if (rid != null && !TextUtils.isEmpty(rid)) {
//                        read_message(customerList.getMsg().getRid(), position);
//                        ((ChatFragment) fragment).user_chat_count_save(customerList.getMatriId(), SharedPrefsManager.getInstance().getString(MATRI_ID));
//                        Intent intent = new Intent(context, ChatInboxActivity.class);
//                        intent.putExtra("user_Name", customerList.getName());
//                        intent.putExtra("user_Image", customerList.getPhoto1());
//                        intent.putExtra("matriId", customerList.getMatriId());
//                        context.startActivity(intent);
//                    } else if (noOfchatcontact > 0) {
//                        ((ChatFragment) fragment).user_chat_count_save(customerList.getMatriId(), SharedPrefsManager.getInstance().getString(MATRI_ID));
//                        Intent intent = new Intent(context, ChatInboxActivity.class);
//                        intent.putExtra("user_Name", customerList.getName());
//                        intent.putExtra("user_Image", customerList.getPhoto1());
//                        intent.putExtra("matriId", customerList.getMatriId());
//                        context.startActivity(intent);
//                    } else {
//                        noContainShow();
//                    }
//
//                } else if (status.equals("Active")) {
//                    if (rid != null && !TextUtils.isEmpty(rid)) {
//                        read_message(customerList.getMsg().getRid(), position);
//                        ((ChatFragment) fragment).user_chat_count_save(customerList.getMatriId(), SharedPrefsManager.getInstance().getString(MATRI_ID));
//                        Intent intent = new Intent(context, ChatInboxActivity.class);
//                        intent.putExtra("user_Name", customerList.getName());
//                        intent.putExtra("user_Image", customerList.getPhoto1());
//                        intent.putExtra("matriId", customerList.getMatriId());
//                        context.startActivity(intent);
//                    } else if (noOfchatcontact > 0) {
//                        ((ChatFragment) fragment).user_chat_count_save(customerList.getMatriId(), SharedPrefsManager.getInstance().getString(MATRI_ID));
//                        Intent intent = new Intent(context, ChatInboxActivity.class);
//                        intent.putExtra("user_Name", customerList.getName());
//                        intent.putExtra("user_Image", customerList.getPhoto1());
//                        intent.putExtra("matriId", customerList.getMatriId());
//                        context.startActivity(intent);
//                    } else {
//                        noContainShow();
//                    }
//                }


//                if (status.equals("Paid") && noOfchatcontact > 0) {
//
//
//                    if (rid != null && !TextUtils.isEmpty(rid)) {
//                        read_message(customerList.getMsg().getRid(), position);
//                    }
//                    ((ChatFragment) fragment).user_chat_count_save(customerList.getMatriId(), SharedPrefsManager.getInstance().getString(MATRI_ID));
//                    Intent intent = new Intent(context, ChatInboxActivity.class);
//                    intent.putExtra("user_Name", customerList.getName());
//                    intent.putExtra("user_Image", customerList.getPhoto1());
//                    intent.putExtra("matriId", customerList.getMatriId());
//                    context.startActivity(intent);
//                } else if (status.equals("Active") && noOfchatcontact > 0) {
//                    if (rid != null && !TextUtils.isEmpty(rid)) {
//                        read_message(customerList.getMsg().getRid(), position);
//                    }
//
//                    ((ChatFragment) fragment).user_chat_count_save(customerList.getMatriId(), SharedPrefsManager.getInstance().getString(MATRI_ID));
//                    Intent intent = new Intent(context, ChatInboxActivity.class);
//                    intent.putExtra("user_Name", customerList.getName());
//                    intent.putExtra("user_Image", customerList.getPhoto1());
//                    intent.putExtra("matriId", customerList.getMatriId());
//                    context.startActivity(intent);
//                } else {
//                    noContainShow();
//                }
            } else {
                noContainShow();
            }
        });

    }

    private void noContainShow() {
        final Dialog dialog = new Dialog(context);
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
                Intent intent = new Intent(new Intent(context, MemberShipPlanActivity.class));
                context.startActivity(intent);
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

    @Override
    public int getItemCount() {
        return null == chatDataList ? 0 : chatDataList.size();
    }

    public void addCustomerList(List<AcceptModel.AcceptData> customerListList) {
        this.chatDataList = customerListList;
        notifyDataSetChanged();
    }

    public void read_message(String matriId, int position) {
        apiInterface.read_message(matriId).enqueue(new Callback<ReadMessageModel>() {
            @Override
            public void onResponse(Call<ReadMessageModel> call, Response<ReadMessageModel> response) {
                if (response.isSuccessful()) {
                    ReadMessageModel readMessageModel = response.body();
                    if (readMessageModel != null) {
                        String respnse = readMessageModel.getResponse();
                        if (respnse.equals("true")) {
                            chatDataList.get(position).getMsg().setReaded("Yes");
                            notifyItemChanged(position);

                        } else {
                            // Toast.makeText(MoreInfoActivity.this,"Already Success",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ReadMessageModel> call, Throwable t) {
                Toast.makeText(context, "something is wrong", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void checkChatValidity(String matriId, String to_id, AcceptModel.AcceptData customerList, int position) {
        progressBar.setVisibility(View.VISIBLE);

        apiInterface.checkChatValidity(matriId, to_id).enqueue(new Callback<ReadMessageModel>() {
            @Override
            public void onResponse(Call<ReadMessageModel> call, Response<ReadMessageModel> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    ReadMessageModel readMessageModel = response.body();
                    if (readMessageModel != null) {
                        String respnse = readMessageModel.getResponse();
//                        if (respnse.equals("true")) {
//                            read_message(customerList.getMsg().getRid(), position);
//                            ((ChatFragment) fragment).user_chat_count_save(customerList.getMatriId(), SharedPrefsManager.getInstance().getString(MATRI_ID));
//                            Intent intent = new Intent(context, ChatInboxActivity.class);
//                            intent.putExtra("user_Name", customerList.getName());
//                            intent.putExtra("user_Image", customerList.getPhoto1());
//                            intent.putExtra("matriId", customerList.getMatriId());
//                            intent.putExtra("chatId", customerList.getMsg().getRid());
//                            context.startActivity(intent);
//                        } else {
//                            noContainShow();
//                        }
                        read_message(customerList.getMsg().getRid(), position);
                        ((ChatFragment) fragment).user_chat_count_save(customerList.getMatriId(), SharedPrefsManager.getInstance().getString(MATRI_ID));
                        Intent intent = new Intent(context, ChatInboxActivity.class);
                        intent.putExtra("user_Name", customerList.getName());
                        intent.putExtra("user_Image", customerList.getPhoto1());
                        intent.putExtra("matriId", customerList.getMatriId());
                        intent.putExtra("chatId", customerList.getMsg().getRid());
                        intent.putExtra("response", respnse);
                        context.startActivity(intent);

                    } else {
                        noContainShow();
                    }
                }
            }

            @Override
            public void onFailure(Call<ReadMessageModel> call, Throwable t) {
                Toast.makeText(context, "something is wrong", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);

            }
        });
    }

    class CustomerViewHolder extends RecyclerView.ViewHolder {
        private final ImageView user_image;
        private final TextView name;
        private final TextView chat_message;
        private final LinearLayout layout_item;


        CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            user_image = itemView.findViewById(R.id.user_image);
            name = itemView.findViewById(R.id.name);
            chat_message = itemView.findViewById(R.id.chat_message);
            layout_item = itemView.findViewById(R.id.layout_item);
        }
    }
}