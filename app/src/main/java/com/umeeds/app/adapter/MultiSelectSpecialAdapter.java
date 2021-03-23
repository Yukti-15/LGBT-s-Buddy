package com.umeeds.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;

import com.umeeds.app.R;
import com.umeeds.app.model.serachmodel.SpecialCaseModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MultiSelectSpecialAdapter extends RecyclerView.Adapter<MultiSelectSpecialAdapter.CustomViewHolder> {

    private final ArrayList<SpecialCaseModel> list;
    private final Context context;
    //------------------------------------------------------------------------------------------------------------------

    public MultiSelectSpecialAdapter(ArrayList<SpecialCaseModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.multiselect_item, viewGroup, false);

        final CustomViewHolder holder = new CustomViewHolder(itemView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {
        if (list != null) {
            holder.checkBox.setChecked(list.get(position).isSelected());
            holder.checkBox.setText(list.get(position).getSpecialCase());
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    list.get(position).setSelected(holder.checkBox.isChecked());


                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //--------------------------------------------------------------------------------------------------------------
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.checkBox)
        CheckBox checkBox;

        CustomViewHolder(View view) {
            super(view);
            this.setIsRecyclable(false);
            ButterKnife.bind(this, itemView);

            checkBox = view.findViewById(R.id.checkBox);
        }

    }

}


