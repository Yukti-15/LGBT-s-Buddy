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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.umeeds.app.R;
import com.umeeds.app.activity.MoreInfoActivity;
import com.umeeds.app.model.PendingModel;
import com.umeeds.app.network.networking.ApiClient;
import com.umeeds.app.network.networking.ApiInterface;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
import static com.umeeds.app.network.networking.Constant.IMAGE_LOAD_USER;


public class ChatPendingAdapter extends RecyclerView.Adapter<ChatPendingAdapter.CustomerViewHolder> {

    Fragment fragment;
    private final FragmentActivity context;
    private List<PendingModel.PendingData> homeDataList;
    private final ApiInterface apiInterface;

    public ChatPendingAdapter(FragmentActivity context, Fragment fragment, List<PendingModel.PendingData> homeDataList) {
        this.context = context;
        this.fragment = fragment;
        this.homeDataList = homeDataList;

        apiInterface = ApiClient.getInterface();

    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_pending_list, parent, false);

        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        final PendingModel.PendingData customerList = homeDataList.get(position);
        holder.name.setText(customerList.getMatriId());
        holder.chat_message.setText(customerList.getGender());
        final String reqId = customerList.getReq_id();
        String profileStatus = customerList.getProfile_status();

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.no_photo)
                .error(R.drawable.no_photo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH).dontAnimate()
                .dontTransform();


        if (profileStatus.equals("show")) {
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


        holder.bt_Cancel.setOnClickListener(v -> {
            // ((ChatPandingFragment) fragment).cancel_send_request(reqId);
            cancel_send_request(reqId, position);
        });


        holder.bt_moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MoreInfoActivity.class);
                intent.putExtra("martId", customerList.getMatriId());
                //  intent.putExtra("blur_status", customerList.getProfile_status());

                context.startActivity(intent);
            }
        });


        holder.layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


    }

    @Override
    public int getItemCount() {
        return null == homeDataList ? 0 : homeDataList.size();
    }

    public void addCustomerList(List<PendingModel.PendingData> homeListList) {
        this.homeDataList = homeListList;
        notifyDataSetChanged();
    }

    public void cancel_send_request(String req_id, int position) {
        apiInterface.cancel_send_request(req_id).enqueue(new Callback<PendingModel>() {
            @Override
            public void onResponse(Call<PendingModel> call, Response<PendingModel> response) {
                if (response.isSuccessful()) {
                    PendingModel homeModel = response.body();
                    if (homeModel != null) {
                        String respons = homeModel.getResponse();
                        if (respons.equals("true")) {
                            try {
                                homeDataList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemChanged(position);
                            } catch (Exception e) {

                            }
                        } else {
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<PendingModel> call, Throwable t) {
                Toast.makeText(context, "something is wrong", Toast.LENGTH_LONG).show();
            }
        });
    }

    class CustomerViewHolder extends RecyclerView.ViewHolder {
        private final ImageView user_image;
        private final TextView name;
        private final TextView chat_message;
        private final LinearLayout layout_item;
        private final Button bt_Cancel;
        private final Button bt_moreInfo;


        CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            user_image = itemView.findViewById(R.id.user_image);
            name = itemView.findViewById(R.id.name);
            chat_message = itemView.findViewById(R.id.chat_message);
            layout_item = itemView.findViewById(R.id.layout_item);
            bt_Cancel = itemView.findViewById(R.id.bt_Cancel);
            bt_moreInfo = itemView.findViewById(R.id.bt_moreInfo);
        }
    }


}