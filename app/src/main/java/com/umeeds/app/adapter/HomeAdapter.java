package com.umeeds.app.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.umeeds.app.model.HomeModel;
import com.umeeds.app.model.LoginModel;
import com.umeeds.app.network.database.SharedPrefsManager;
import com.umeeds.app.network.networking.ApiClient;
import com.umeeds.app.network.networking.ApiInterface;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
import static com.umeeds.app.network.networking.Constant.IMAGE_LOAD_USER;
import static com.umeeds.app.network.networking.Constant.MATRI_ID;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.CustomerViewHolder> {

    private final FragmentActivity context;
    private final Fragment fragment;
    private final List<HomeModel.HomeData> homeDataList;
    private final ApiInterface apiInterface;
    private String heartlist;

    public HomeAdapter(FragmentActivity context, Fragment fragment, List<HomeModel.HomeData> homeDataList) {
        this.context = context;
        this.fragment = fragment;
        this.homeDataList = homeDataList;
        apiInterface = ApiClient.getInterface();
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_list, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomerViewHolder holder, final int position) {
        final HomeModel.HomeData customerList = homeDataList.get(position);
        holder.name.setText(customerList.getMatriId());
        holder.tv_age.setText(customerList.getAge() + " Years");
        holder.tv_gender.setText(customerList.getGenderUser());
        final String matriId = customerList.getMatriId();
        final String sendrequest = customerList.getSendrequest();

        heartlist = customerList.getHeartlist();
        String status = customerList.getStatus();
        String profileStatus = customerList.getProfile_status();
        String verify_status = customerList.getVerify_status();

        if (customerList.getStatus().equals("Paid")) {
            holder.gifDrawable.setVisibility(View.VISIBLE);

            if (customerList.getPlan_name().equals("UMEED")) {
                Glide.with(context).load(R.drawable.gold_flag).into(holder.gifDrawable);
            } else if (customerList.getPlan_name().equals("HOPE")) {
                Glide.with(context).load(R.drawable.gold_c).into(holder.gifDrawable);
            } else if (customerList.getPlan_name().equals("TRIAL PACK")) {
                Glide.with(context).load(R.drawable.silver).into(holder.gifDrawable);
            }
        } else {
            holder.gifDrawable.setVisibility(View.GONE);
        }
        holder.total_visit_tv.setText(customerList.getTotalvisit() + " View");

        if (verify_status.equals("0")) {
            holder.tv_verified.setText("Not Verified");
            holder.iv_verified.setImageDrawable(context.getDrawable(R.drawable.ic_check_circle_black_24dp));
            DrawableCompat.setTint(holder.iv_verified.getDrawable(), ContextCompat.getColor(context, R.color.black));
        } else {
            holder.tv_verified.setText("Verified");
            holder.iv_verified.setImageDrawable(context.getDrawable(R.drawable.ic_check_circle_black_24dp));
            DrawableCompat.setTint(holder.iv_verified.getDrawable(), ContextCompat.getColor(context, R.color.blue));
        }

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

        if (!homeDataList.get(position).getPhoto1().equals("nophoto.gif")) {
            if (profileStatus.equals("show")) {
                Glide.with(context)
                        .load(IMAGE_LOAD_USER + homeDataList.get(position).getPhoto1())
                        .apply(options)
                        .into(holder.user_image);
            } else {
                Glide.with(context)
                        .load(IMAGE_LOAD_USER + homeDataList.get(position).getPhoto1())
                        .apply(bitmapTransform(new BlurTransformation(25)))
                        .into(holder.user_image);
            }
        } else {
            Glide.with(context)
                    .load(context.getResources().getDrawable(R.drawable.app_logo))
                    .apply(options)
                    .into(holder.user_image);
        }


//        if (profileStatus.equals("show")) {
//            Glide.with(context)
//                    .load(IMAGE_LOAD_USER + customerList.getPhoto1())
//                    .apply(options)
//                    .into(holder.user_image);
//        } else {
//            Glide.with(context)
//                    .load(IMAGE_LOAD_USER + customerList.getPhoto1())
//                    .apply(bitmapTransform(new BlurTransformation(25)))
//                    .into(holder.user_image);
//        }

        if (customerList.getSendrequest().equals("0")) {
            holder.bt_request.setText("Send request");

        } else {
            holder.bt_request.setText("Requested");
        }

        holder.bt_request.setOnClickListener(v -> {
            if (customerList.getSendrequest().equals("0")) {
                holder.bt_request.setText("Requested");
                sendRequest(matriId, SharedPrefsManager.getInstance().getString(MATRI_ID), position);
            }
        });

        holder.likeBtn.setOnClickListener(v -> {
            // ((MainFragment) fragment).sendheartlist(matriId, SharedPrefsManager.getInstance().getString(MATRI_ID), "1");
            sendheartlist(matriId, SharedPrefsManager.getInstance().getString(MATRI_ID), "1", position);
            holder.likeBtn.setVisibility(View.GONE);
            holder.unlikeBtn.setVisibility(View.VISIBLE);

        });

        holder.unlikeBtn.setOnClickListener(v -> {
            //   ((MainFragment) fragment).sendheartlist(matriId, SharedPrefsManager.getInstance().getString(MATRI_ID), "1");
            sendheartlist(matriId, SharedPrefsManager.getInstance().getString(MATRI_ID), "1", position);
            holder.likeBtn.setVisibility(View.VISIBLE);
            holder.unlikeBtn.setVisibility(View.GONE);
        });

        holder.bt_moreInfo.setOnClickListener(v -> {
            Intent intent = new Intent(context, MoreInfoActivity.class);
            intent.putExtra("martId", customerList.getMatriId());
            //   intent.putExtra("blur_status", profileStatus);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return null == homeDataList ? 0 : homeDataList.size();
    }

    public void addCustomerList(HomeModel.HomeData homeListList) {
        homeDataList.add(homeListList);
        notifyDataSetChanged();
    }

    public void addAll(List<HomeModel.HomeData> postItems) {
        for (int i = 0; i < postItems.size(); i++) {
            addCustomerList(postItems.get(i));
        }
    }

    public void sendheartlist(String matriId, String loginmatriid, String status, final int position) {
        apiInterface.sendheartlist(matriId, loginmatriid, status).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    LoginModel homeModel = response.body();
                    if (homeModel != null) {
                        boolean respnse = homeModel.isResponse();
                        if (respnse) {
                            if (homeDataList.get(position).getHeartlist().equals("1")) {
                                homeDataList.get(position).setHeartlist("0");
                                notifyItemChanged(position);
                            } else {
                                homeDataList.get(position).setHeartlist("1");
                                notifyItemChanged(position);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(context, "something is wrong", Toast.LENGTH_LONG).show();
                //     progress.cancleDialog();
            }
        });
    }

    public void sendRequest(String matriId, String loginmatriid, final int position) {
        apiInterface.sendFriendRequest(matriId, loginmatriid).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    LoginModel homeModel = response.body();
                    if (homeModel != null) {
                        boolean respnse = homeModel.isResponse();
                        if (respnse) {
                            Toast.makeText(context, "Sent request", Toast.LENGTH_SHORT).show();
                            /*if (homeDataList.get(position).getHeartlist().equals("1")){
                                homeDataList.get(position).setHeartlist("0");
                                notifyItemChanged(position);
                            }else {
                                homeDataList.get(position).setHeartlist("1");
                                notifyItemChanged(position);
                            }*/
                            homeDataList.get(position).setSendrequest("1");
                            notifyItemChanged(position);

                        } else {
                            //  Toast.makeText(getContext(),"Already sent request",Toast.LENGTH_SHORT).show();
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
        private final TextView tv_age;
        private final TextView tv_verified;
        private final TextView tv_gender;
        private final TextView total_visit_tv;
        private final LinearLayout layout_item;
        private final Button bt_request;
        private final Button bt_moreInfo;
        private final ImageView likeBtn;
        private final ImageView unlikeBtn;
        private final ImageView paid_iv;
        RelativeLayout rv_image;
        GifImageView gifDrawable;

        CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            user_image = itemView.findViewById(R.id.user_image);
            // iv_heart = itemView.findViewById(R.id.iv_heart);
            unlikeBtn = itemView.findViewById(R.id.iv_unlike);
            likeBtn = itemView.findViewById(R.id.iv_like);
            total_visit_tv = itemView.findViewById(R.id.total_visit_tv);

            name = itemView.findViewById(R.id.name);
            tv_age = itemView.findViewById(R.id.tv_age);
            tv_gender = itemView.findViewById(R.id.tv_gender);
            layout_item = itemView.findViewById(R.id.layout_item);
            bt_request = itemView.findViewById(R.id.bt_request);
            bt_moreInfo = itemView.findViewById(R.id.bt_moreInfo);
            tv_verified = itemView.findViewById(R.id.tv_verified);
            iv_verified = itemView.findViewById(R.id.iv_verified);
            rv_image = itemView.findViewById(R.id.rv_image);
            paid_iv = itemView.findViewById(R.id.paid_iv);
            gifDrawable = itemView.findViewById(R.id.gif_iv);
        }
    }
}