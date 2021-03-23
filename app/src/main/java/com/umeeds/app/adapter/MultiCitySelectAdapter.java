package com.umeeds.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.umeeds.app.R;
import com.umeeds.app.model.serachmodel.CityLivingInModel;

import java.util.ArrayList;

public class MultiCitySelectAdapter extends RecyclerView.Adapter<MultiCitySelectAdapter.ViewHolder> {
    private final ArrayList<CityLivingInModel> list;
    private final Context context;

    public MultiCitySelectAdapter(ArrayList<CityLivingInModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MultiCitySelectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.multiselect_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MultiCitySelectAdapter.ViewHolder holder, final int position) {
        if (list != null) {
            holder.checkBox.setChecked(list.get(position).isSelected());
            holder.checkBox.setText(list.get(position).getCity());
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            this.setIsRecyclable(false);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
}
