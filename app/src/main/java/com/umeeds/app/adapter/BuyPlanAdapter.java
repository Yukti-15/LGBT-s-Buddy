package com.umeeds.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.umeeds.app.R;
import com.umeeds.app.model.BuyPlanModel;

import java.util.List;
import java.util.TimeZone;


public class BuyPlanAdapter extends RecyclerView.Adapter<BuyPlanAdapter.CustomerViewHolder> {

    private final FragmentActivity context;
    private final BuyPlanClickListener buyPlanClickListener;
    private List<BuyPlanModel.BuyPlanData> buyPlanDataList;


    public BuyPlanAdapter(FragmentActivity context, BuyPlanClickListener buyPlanClickListener) {
        this.context = context;
        this.buyPlanClickListener = buyPlanClickListener;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.buy_plan_list, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        final BuyPlanModel.BuyPlanData customerList = buyPlanDataList.get(position);
        TimeZone tz = TimeZone.getDefault();
        System.out.println("TimeZone   " + tz.getDisplayName(false, TimeZone.SHORT) + " Timezone id :: " + tz.getID());
        String timeZone = tz.getID();
        if (timeZone.equals("Asia/Kolkata") || timeZone.equals("Asia/Calcutta")) {
            if (customerList.getOption_type().equals("CCAvenue")) {
                holder.tv_buyplan.setText(customerList.getOption_type() + " (Credit Card/Debit Card/NetBanking/Paytm)");
                holder.iv_payicon.setImageDrawable(context.getDrawable(R.drawable.ccavenue_logo));
                holder.iv_payicon.setColorFilter(ContextCompat.getColor(context, R.color.red), android.graphics.PorterDuff.Mode.MULTIPLY);
            } else {
                holder.tv_buyplan.setText("RazorPay");
                holder.iv_payicon.setImageDrawable(context.getDrawable(R.drawable.razorpay));
            }
        } else {
            if (customerList.getOption_type().equals("CCAvenue")) {
//                holder.tv_buyplan.setText(customerList.getOption_type() + " (Credit Card/Debit Card/NetBanking/Paytm)");
//                holder.iv_payicon.setImageDrawable(context.getDrawable(R.drawable.ccavenue_logo));
//                holder.iv_payicon.setColorFilter(ContextCompat.getColor(context, R.color.red), android.graphics.PorterDuff.Mode.MULTIPLY);
                holder.itemView.setVisibility(View.GONE);
            } else {
                holder.tv_buyplan.setText("RazorPay");
                holder.iv_payicon.setImageDrawable(context.getDrawable(R.drawable.razorpay));
            }
        }

    }

    @Override
    public int getItemCount() {
        return null == buyPlanDataList ? 0 : buyPlanDataList.size();
    }

    public void addCustomerList(List<BuyPlanModel.BuyPlanData> customerListList) {
        this.buyPlanDataList = customerListList;
        notifyDataSetChanged();
    }

    public interface BuyPlanClickListener {
        void onEvent(BuyPlanModel.BuyPlanData buyPlanData, View view);
    }

    class CustomerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView tv_buyplan;
        private final LinearLayout layout_item;
        ImageView iv_payicon;

        CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_buyplan = itemView.findViewById(R.id.tv_buyplan);
            layout_item = itemView.findViewById(R.id.layout_item);
            iv_payicon = itemView.findViewById(R.id.iv_payicon);

            layout_item.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            buyPlanClickListener.onEvent(buyPlanDataList.get(getAdapterPosition()), v);
        }
    }

}