package com.umeeds.app.adapter;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.umeeds.app.R;
import com.umeeds.app.activity.NotificationActivity;
import com.umeeds.app.model.NotificationModel;

import java.util.List;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.CustomerViewHolder> {

    private final FragmentActivity context;
    private final Activity activity;

    private List<NotificationModel.NotificationData> notificationDataList;

    public NotificationAdapter(FragmentActivity context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }


    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_inbox_list, parent, false);
        return new CustomerViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, final int position) {
        final NotificationModel.NotificationData customerList = notificationDataList.get(position);
        holder.name.setText(customerList.getName());
        holder.chat_message.setText(customerList.getMsg());


        if (customerList.getReadstatus().equals("No")) {
            // holder.rl_layout.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.rl_layout.setBackgroundColor(ContextCompat.getColor(context, R.color.white));

        } else {
            // holder.rl_layout.setBackgroundColor(Color.parseColor("#000000"));
            holder.rl_layout.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));

        }

/*
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.locality_logo_small)
                .error(R.drawable.locality_logo_small)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH).dontAnimate()
                .dontTransform();

        Glide.with(context)
                .load(IMAGE_LOAD_USER + singleItem.getUserImage())
                .apply(options)
                .into(holder.userImage);*/


        holder.layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NotificationActivity) activity).read_notification(customerList.getNid(), position, notificationDataList);
            }
        });


        holder.iv_delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((NotificationActivity) activity).delete_notification(customerList.getNid(), position, notificationDataList);

            }
        });


    }

    @Override
    public int getItemCount() {
        return null == notificationDataList ? 0 : notificationDataList.size();
    }

    public void addCustomerList(List<NotificationModel.NotificationData> customerListList) {
        this.notificationDataList = customerListList;
        notifyDataSetChanged();
    }

    class CustomerViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rl_layout;
        private final ImageView user_image;
        private final ImageView iv_delet;
        private final TextView name;
        private final TextView chat_message;
        private final LinearLayout layout_item;

        CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            user_image = itemView.findViewById(R.id.user_image);
            iv_delet = itemView.findViewById(R.id.iv_delet);
            name = itemView.findViewById(R.id.name);
            chat_message = itemView.findViewById(R.id.chat_message);
            layout_item = itemView.findViewById(R.id.layout_item);
            rl_layout = itemView.findViewById(R.id.rl_layout);

        }
    }

}