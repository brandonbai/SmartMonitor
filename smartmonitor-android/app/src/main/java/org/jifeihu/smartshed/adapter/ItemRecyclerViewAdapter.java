package org.jifeihu.smartshed.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.jifeihu.smartshed.R;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder> {

    private static final String[] TYPES = new String[]{"温度","空气湿度","土壤湿度","光照强度"};
    private static final String[] UNITS = new String[]{" ℃"," RH"," RH"," Lx"};
    private static final int SIZE = 4;
    private int[] mDrawables;
    private byte[] mValues = new byte[SIZE];
    private byte[] mThresholds = new byte[SIZE];

    public ItemRecyclerViewAdapter() {
        mDrawables = new int[]{R.drawable.ic_temp, R.drawable.ic_air,
                R.drawable.ic_soil, R.drawable.ic_light};
    }
    public void setValues(byte[] values) {
        mValues = values;
        notifyDataSetChanged();
    }
    public void setThresholdsList(byte[] thresholds) {
        mThresholds = thresholds;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_card_direct_connect, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.typeImageView.setImageResource(mDrawables[position]);
        holder.typeView.setText(TYPES[position]);
        holder.valueView.setText("实时值：" + mValues[position] + UNITS[position]);
        holder.thresholdView.setText("阈值：" + mThresholds[position] + UNITS[position]);
    }

    @Override
    public int getItemCount() {
        return SIZE;
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView typeView, valueView, thresholdView;
        ImageView typeImageView;

        ViewHolder(View view) {
            super(view);
            typeView = (TextView) view.findViewById(R.id.text_view_type);
            valueView = (TextView) view.findViewById(R.id.text_view_value);
            thresholdView = (TextView) view.findViewById(R.id.text_view_threshold);
            typeImageView = (ImageView) view.findViewById(R.id.image_view_type);
        }
    }
}
