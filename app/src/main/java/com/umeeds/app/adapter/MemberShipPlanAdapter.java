package com.umeeds.app.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.umeeds.app.R;
import com.umeeds.app.activity.BuyMemberShipPlanActivity;
import com.umeeds.app.model.MemberShipPlanModel;

import java.util.List;
import java.util.TimeZone;

import pl.droidsonroids.gif.GifImageView;


public class MemberShipPlanAdapter extends RecyclerView.Adapter<MemberShipPlanAdapter.CustomerViewHolder> {

    private final FragmentActivity context;
    private List<MemberShipPlanModel.MemberShipPlanData> chatDataList;

    public MemberShipPlanAdapter(FragmentActivity context) {
        this.context = context;
    }


    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.member_ship_plan_list, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        final MemberShipPlanModel.MemberShipPlanData customerList = chatDataList.get(position);
        holder.tv_planid.setText("Planid : " + customerList.getPlanId());
        holder.tv_plandisplayname.setText("Plan Type : " + customerList.getPlandisplayName());

        TimeZone tz = TimeZone.getDefault();
        System.out.println("TimeZone   " + tz.getDisplayName(false, TimeZone.SHORT) + " Timezone id :: " + tz.getID());
        String timeZone = tz.getID();
        if (timeZone.equals("Asia/Kolkata") || timeZone.equals("Asia/Calcutta")) {
            holder.tv_planamount.setText("Amount : INR " + customerList.getPlanamount());
        } else {
            holder.tv_planamount.setText("Amount : USD " + customerList.getPlanamount());
        }

//        if (customerList.getPlandisplayName().equals("Buy extra chats")) {
//            holder.tv_planduration.setText("Validity : " + customerList.getPlanduration());
//        } else {
        holder.tv_planduration.setText("Validity : " + customerList.getPlanduration() + " Days");
//        }

        holder.tv_planchatcontact.setText("Plan chat contact : " + customerList.getPlanchatcontact());
        holder.tv_plannoofcontacts.setText("Allowed Contact : " + customerList.getPlannoofcontacts().toUpperCase());


        if (customerList.getPlandisplayName().equals("GOLDEN")) {
            holder.sellerImagev.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_gold_bronze));
        }
        if (customerList.getPlandisplayName().equals("SILVER")) {
            holder.sellerImagev.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_bronze));
        }
        if (customerList.getPlandisplayName().equals("BRONZE")) {
            holder.sellerImagev.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_revenue_pink));
        }

        if (customerList.getImage() != null) {
            Glide.with(context)
                    .load(customerList.getImage())
                    .into(holder.gifImageView);
        }


        holder.bt_upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BuyMemberShipPlanActivity.class);
                intent.putExtra("planId", customerList.getPlanId());
                intent.putExtra("plan", customerList.getPlandisplayName());
                intent.putExtra("amount", customerList.getPlanamount());
                intent.putExtra("validity", customerList.getPlanduration());
                intent.putExtra("chatcontact", customerList.getPlanchatcontact());
                intent.putExtra("contact", customerList.getPlannoofcontacts());
                intent.putExtra("plantype", customerList.getPlandisplayName());
                intent.putExtra("planImage", customerList.getImage());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == chatDataList ? 0 : chatDataList.size();
    }

    public void addCustomerList(List<MemberShipPlanModel.MemberShipPlanData> customerListList) {
        this.chatDataList = customerListList;
        notifyDataSetChanged();
    }

    class CustomerViewHolder extends RecyclerView.ViewHolder {
        private final ImageView sellerImagev;
        private final TextView tv_planid;
        private final TextView tv_plandisplayname;
        private final TextView tv_planamount;
        private final TextView tv_planduration;
        private final TextView tv_plannoofcontacts;
        private final TextView tv_planchatcontact;
        private final Button bt_upgrade;
        private final GifImageView gifImageView;

        CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            sellerImagev = itemView.findViewById(R.id.sellerImagev);
            tv_planid = itemView.findViewById(R.id.tv_planid);
            tv_plandisplayname = itemView.findViewById(R.id.tv_plandisplayname);
            tv_planamount = itemView.findViewById(R.id.tv_planamount);
            tv_planduration = itemView.findViewById(R.id.tv_planduration);
            tv_plannoofcontacts = itemView.findViewById(R.id.tv_plannoofcontacts);
            tv_planchatcontact = itemView.findViewById(R.id.tv_planchatcontact);
            bt_upgrade = itemView.findViewById(R.id.bt_upgrade);
            gifImageView = itemView.findViewById(R.id.gif_iv);
        }
    }

}