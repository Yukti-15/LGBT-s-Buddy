package com.umeeds.app.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.umeeds.app.R;
import com.umeeds.app.activity.BookMarkedActivity;
import com.umeeds.app.activity.MoreInfoActivity;
import com.umeeds.app.model.HeartModel;
import com.umeeds.app.network.database.SharedPrefsManager;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
import static com.umeeds.app.network.networking.Constant.IMAGE_LOAD_USER;
import static com.umeeds.app.network.networking.Constant.MATRI_ID;


public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.CustomerViewHolder> {

    private final FragmentActivity context;
    private List<HeartModel.HeartData> homeDataList;
    private final Activity activity;

    public BookmarkAdapter(FragmentActivity context, Activity activity, List<HeartModel.HeartData> homeDataList) {
        this.context = context;
        this.activity = activity;
        this.homeDataList = homeDataList;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bookmark_list, parent, false);

        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        final HeartModel.HeartData customerList = homeDataList.get(position);
        holder.name.setText(customerList.getMatriId());
        holder.chat_message.setText(customerList.getAge() + " Years");
        holder.tv_gender.setText(customerList.getGender());
        final String matriId = customerList.getMatriId();

        if (customerList.getVerify_status().equals("0")) {
            holder.tv_verified.setText("Not Verified");
            holder.iv_verified.setImageDrawable(context.getDrawable(R.drawable.ic_check_circle_black_24dp));
            DrawableCompat.setTint(holder.iv_verified.getDrawable(), ContextCompat.getColor(context, R.color.black));
        } else {
            holder.tv_verified.setText("Verified");
            holder.iv_verified.setImageDrawable(context.getDrawable(R.drawable.ic_check_circle_black_24dp));
            DrawableCompat.setTint(holder.iv_verified.getDrawable(), ContextCompat.getColor(context, R.color.blue));
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
                    .into(holder.iv_userprofile);
        } else {
            Glide.with(context)
                    .load(IMAGE_LOAD_USER + customerList.getPhoto1())
                    .apply(bitmapTransform(new BlurTransformation(25)))
                    .into(holder.iv_userprofile);
        }


        holder.bt_moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MoreInfoActivity.class);
                intent.putExtra("martId", customerList.getMatriId());
                context.startActivity(intent);
            }
        });

        if (customerList.getSendrequest().equals("0")) {
            holder.bt_request.setText("Send request");

        } else {
            holder.bt_request.setText("Requested");
        }


        holder.bt_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customerList.getSendrequest().equals("0")) {
                    ((BookMarkedActivity) activity).sendRequest(matriId, SharedPrefsManager.getInstance().getString(MATRI_ID));
                }
            }
        });



       /* holder.bt_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BookMarkedActivity) activity).sendRequest(matriId, SharedPrefsManager.getInstance().getString(MATRI_ID));
            }
        });*/


    }

    @Override
    public int getItemCount() {
        return null == homeDataList ? 0 : homeDataList.size();
    }

    public void addCustomerList(List<HeartModel.HeartData> homeListList) {
        this.homeDataList = homeListList;
        notifyDataSetChanged();
    }

    class CustomerViewHolder extends RecyclerView.ViewHolder {
        private final ImageView iv_userprofile;
        private final ImageView iv_verified;
        private final TextView name;
        private final TextView tv_verified;
        private final TextView tv_gender;
        private final TextView chat_message;
        private final LinearLayout layout_item;
        private final Button bt_request;
        private final Button bt_moreInfo;


        CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_userprofile = itemView.findViewById(R.id.iv_userprofile);
            iv_verified = itemView.findViewById(R.id.iv_verified);
            name = itemView.findViewById(R.id.name);
            tv_gender = itemView.findViewById(R.id.tv_gender);
            tv_verified = itemView.findViewById(R.id.tv_verified);
            chat_message = itemView.findViewById(R.id.chat_message);
            layout_item = itemView.findViewById(R.id.layout_item);
            bt_request = itemView.findViewById(R.id.bt_request);
            bt_moreInfo = itemView.findViewById(R.id.bt_moreInfo);
        }
    }

}