package com.umeeds.app.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.umeeds.app.R;
import com.umeeds.app.activity.MoreInfoActivity;
import com.umeeds.app.model.LoginModel;
import com.umeeds.app.model.serachmodel.SmartSearchModel;
import com.umeeds.app.network.database.SharedPrefsManager;
import com.umeeds.app.network.networking.ApiClient;
import com.umeeds.app.network.networking.ApiInterface;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
import static com.umeeds.app.network.networking.Constant.IMAGE_LOAD_USER;
import static com.umeeds.app.network.networking.Constant.MATRI_ID;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.CustomerViewHolder> {

    String heartlist;
    private final FragmentActivity context;
    private final Fragment fragment;
    private final ApiInterface apiInterface;
    private List<SmartSearchModel.SmartSearchData> homeDataList;

    public SearchAdapter(FragmentActivity context, Fragment fragment, List<SmartSearchModel.SmartSearchData> smartSearchDataList) {
        this.context = context;
        this.fragment = fragment;
        this.homeDataList = smartSearchDataList;

        apiInterface = ApiClient.getInterface();

    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_list, parent, false);

        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        final SmartSearchModel.SmartSearchData customerList = homeDataList.get(position);
        holder.name.setText(customerList.getMatriId());
        holder.tv_age.setText(customerList.getAge() + " Years");
        holder.tv_gender.setText(customerList.getGenderUser());
        final String matriId = customerList.getMatriId();
        final String sendrequest = customerList.getSendrequest();
        String profileStatus = customerList.getProfile_status();
        heartlist = customerList.getHeartlist();

        try {
            if (customerList.getVerify_status().equals("0")) {
                holder.tv_verified.setText("Not Verified");
                holder.iv_verified.setImageDrawable(context.getDrawable(R.drawable.ic_check_circle_black_24dp));
                DrawableCompat.setTint(holder.iv_verified.getDrawable(), ContextCompat.getColor(context, R.color.black));
            } else {
                holder.tv_verified.setText("Verified");
                holder.iv_verified.setImageDrawable(context.getDrawable(R.drawable.ic_check_circle_black_24dp));
                DrawableCompat.setTint(holder.iv_verified.getDrawable(), ContextCompat.getColor(context, R.color.blue));
            }
        } catch (Exception e) {

        }


        if (customerList.getSendrequest().equals("0")) {
            holder.bt_request.setText("Send request");

        } else {
            holder.bt_request.setText("Requested");
        }

        /*if (customerList.getHeartlist().equals("1")) {
            holder.iv_heart.setImageDrawable(context.getDrawable(R.drawable.ic_favorite_heart_pink));
        } else {
            holder.iv_heart.setImageDrawable(context.getDrawable(R.drawable.heart));
        }
*/
        if (heartlist.equals("1")) {
            holder.likeBtn.setVisibility(View.VISIBLE);
            holder.unlikeBtn.setVisibility(View.GONE);
        } else {
            holder.likeBtn.setVisibility(View.GONE);
            holder.unlikeBtn.setVisibility(View.VISIBLE);
        }

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.no_photo)
                .error(R.drawable.no_photo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH).dontAnimate()
                .dontTransform();

        if (customerList.getProfile_status().equals("show")) {
            Glide.with(context)
                    .load(IMAGE_LOAD_USER + customerList.getPhoto1())
                    .apply(options)
                    .into(holder.user_image);

        } else {
            Glide.with(context)
                    .load(IMAGE_LOAD_USER + customerList.getPhoto1())
                    .apply(bitmapTransform(new BlurTransformation(25)))
                    .into(holder.user_image);
        }

        holder.bt_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customerList.getSendrequest().equals("0")) {
                    // ((ResultSearchFragment) fragment).sendRequest(matriId,SharedPrefsManager.getInstance().getString(MATRI_ID));
                    sendRequest(matriId, SharedPrefsManager.getInstance().getString(MATRI_ID), position);
                }
            }
        });

/*
        holder.iv_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // ((ResultSearchFragment) fragment).sendheartlist(matriId,SharedPrefsManager.getInstance().getString(MATRI_ID),"1");
               sendheartlist(matriId,SharedPrefsManager.getInstance().getString(MATRI_ID),"1",position);
            }
        });
*/

        holder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendheartlist(matriId, SharedPrefsManager.getInstance().getString(MATRI_ID), "1", position);
                holder.likeBtn.setVisibility(View.GONE);
                holder.unlikeBtn.setVisibility(View.VISIBLE);
            }
        });

        holder.unlikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendheartlist(matriId, SharedPrefsManager.getInstance().getString(MATRI_ID), "1", position);
                holder.likeBtn.setVisibility(View.VISIBLE);
                holder.unlikeBtn.setVisibility(View.GONE);
            }
        });

        holder.bt_moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MoreInfoActivity.class);
                intent.putExtra("martId", customerList.getMatriId());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return null == homeDataList ? 0 : homeDataList.size();
    }

    public void addCustomerList(List<SmartSearchModel.SmartSearchData> homeListList) {
        this.homeDataList = homeListList;
        notifyDataSetChanged();
    }

    public void sendheartlist(final String matriId, String loginmatriid, String status, int position) {
        apiInterface.sendheartlist(matriId, loginmatriid, status).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    LoginModel homeModel = response.body();
                    if (homeModel != null) {
                        boolean respnse = homeModel.isResponse();
                        if (respnse) {
                            //  searchAdapter.notifyDataSetChanged();
                            if (homeDataList.get(position).getHeartlist().equals("1")) {
                                homeDataList.get(position).setHeartlist("0");
                                notifyItemChanged(position);
                            } else {
                                homeDataList.get(position).setHeartlist("1");
                                notifyItemChanged(position);
                            }
                        } else {
                            //   Toast.makeText(getContext(),"UnLike",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(context, "something is wrong", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void sendRequest(String matriId, String loginmatriid, int position) {
        apiInterface.sendFriendRequest(matriId, loginmatriid).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    LoginModel homeModel = response.body();
                    if (homeModel != null) {
                        boolean respnse = homeModel.isResponse();
                        if (respnse) {
                            Toast.makeText(context, "Sent request", Toast.LENGTH_SHORT).show();
                            try {
                                homeDataList.get(position).setSendrequest("1");
                            } catch (Exception e) {

                            }
                            notifyItemChanged(position);
                        } else {
                            Toast.makeText(context, "Already sent request", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(context, "something is wrong", Toast.LENGTH_LONG).show();
            }
        });

    }

    class CustomerViewHolder extends RecyclerView.ViewHolder {
        private final ImageView user_image;
        private final ImageView iv_verified;
        private final TextView name;
        private final TextView tv_verified;
        private final TextView tv_gender;
        private final TextView tv_age;
        private final LinearLayout layout_item;
        private final Button bt_request;
        private final Button bt_moreInfo;

        private final ImageView likeBtn;
        private final ImageView unlikeBtn;

        CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            user_image = itemView.findViewById(R.id.user_image);
            unlikeBtn = itemView.findViewById(R.id.iv_unlike);
            likeBtn = itemView.findViewById(R.id.iv_like);
            name = itemView.findViewById(R.id.name);
            tv_age = itemView.findViewById(R.id.tv_age);
            tv_gender = itemView.findViewById(R.id.tv_gender);
            layout_item = itemView.findViewById(R.id.layout_item);
            bt_request = itemView.findViewById(R.id.bt_request);
            bt_moreInfo = itemView.findViewById(R.id.bt_moreInfo);
            iv_verified = itemView.findViewById(R.id.iv_verified);
            tv_verified = itemView.findViewById(R.id.tv_verified);
        }
    }


}