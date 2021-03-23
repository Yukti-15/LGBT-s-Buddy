package com.umeeds.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.umeeds.app.R;
import com.umeeds.app.activity.MoreInfoActivity;
import com.umeeds.app.model.LoginModel;
import com.umeeds.app.model.MyVisitedModel;
import com.umeeds.app.network.UtilsMethod;
import com.umeeds.app.network.database.SharedPrefsManager;
import com.umeeds.app.network.networking.ApiClient;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.umeeds.app.network.networking.Constant.IMAGE_LOAD_USER;
import static com.umeeds.app.network.networking.Constant.MATRI_ID;

public class VisitedUserProfileAdapter extends RecyclerView.Adapter<VisitedUserProfileAdapter.Holder> {
    List<MyVisitedModel.DataBean> dataBeans;
    Context context;

    public VisitedUserProfileAdapter(List<MyVisitedModel.DataBean> dataBeans, Context context) {
        this.dataBeans = dataBeans;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_profile_visits, parent, false);
        return new VisitedUserProfileAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final String matriId = dataBeans.get(position).getMatriid();
        holder.name_tv.setText(dataBeans.get(position).getMatriid());
        holder.gender_category.setText(dataBeans.get(position).getGender());
        holder.age_and_height_tv.setText(dataBeans.get(position).getAge() + "Year | " + dataBeans.get(position).getUserheight());
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try {
            Date date = format.parse(dataBeans.get(position).getVisited_date());
            holder.time_tv.setText(UtilsMethod.getTimeString(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (dataBeans.get(position).getStatus().equals("Paid")) {
            holder.gifDrawable.setVisibility(View.VISIBLE);

            if (dataBeans.get(position).getPlan_name().equals("UMEED")) {
                Glide.with(context).load(R.drawable.gold_flag).into(holder.gifDrawable);
            } else if (dataBeans.get(position).getPlan_name().equals("HOPE")) {
                Glide.with(context).load(R.drawable.gold_c).into(holder.gifDrawable);
            } else if (dataBeans.get(position).getPlan_name().equals("TRIAL PACK")) {
                Glide.with(context).load(R.drawable.silver).into(holder.gifDrawable);
            }
        } else {
            holder.gifDrawable.setVisibility(View.GONE);
        }

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.no_photo)
                .error(R.drawable.no_photo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH).dontAnimate()
                .dontTransform();

        if (!dataBeans.get(position).getPhoto1().equals("nophoto.gif")) {

            Glide.with(context)
                    .load(IMAGE_LOAD_USER + dataBeans.get(position).getPhoto1())
                    .apply(options)
                    .into(holder.circleImageView);
        } else {
            Glide.with(context)
                    .load(context.getResources().getDrawable(R.drawable.app_logo))
                    .apply(options)
                    .into(holder.circleImageView);
        }
        if (dataBeans.get(position).getSendrequest().equals("0")) {
            holder.sendRequest.setText("Request");
            holder.sendRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dataBeans.get(position).getSendrequest().equals("0")) {
                        holder.sendRequest.setVisibility(View.GONE);
                        sendRequest(matriId, SharedPrefsManager.getInstance().getString(MATRI_ID), position);
                    }
                }
            });
        } else {
            holder.sendRequest.setText("Requested");
        }


        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MoreInfoActivity.class);
            intent.putExtra("martId", matriId);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dataBeans.size();
    }

    public void addList(List<MyVisitedModel.DataBean> dataBeans) {
        this.dataBeans = dataBeans;
        notifyDataSetChanged();
    }

    public void sendRequest(String matriId, String loginmatriid, final int position) {
        ApiClient.getInterface().sendFriendRequest(matriId, loginmatriid).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    LoginModel homeModel = response.body();
                    if (homeModel != null) {
                        boolean respnse = homeModel.isResponse();
                        if (respnse) {
                            Toast.makeText(context, "Sent request", Toast.LENGTH_SHORT).show();
                            dataBeans.get(position).setSendrequest("1");
                            notifyItemChanged(position);
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

    public static class Holder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView name_tv, age_and_height_tv, time_tv, gender_category, sendRequest;
        GifImageView gifDrawable;


        public Holder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.user_profile_civ);
            name_tv = itemView.findViewById(R.id.user_name_tv);
            age_and_height_tv = itemView.findViewById(R.id.user_age_height);
            time_tv = itemView.findViewById(R.id.visit_date);
            sendRequest = itemView.findViewById(R.id.send_request);
            gender_category = itemView.findViewById(R.id.gender_category);
            gifDrawable = itemView.findViewById(R.id.gif_iv);

        }
    }
}
