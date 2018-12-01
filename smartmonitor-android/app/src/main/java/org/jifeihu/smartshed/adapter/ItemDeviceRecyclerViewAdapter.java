package org.jifeihu.smartshed.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.jifeihu.smartshed.R;
import org.jifeihu.smartshed.bean.Device;
import org.jifeihu.smartshed.interfaces.IConstants;
import org.jifeihu.smartshed.view.activity.DeviceDetailActivity;

import java.util.List;

public class ItemDeviceRecyclerViewAdapter extends RecyclerView.Adapter<ItemDeviceRecyclerViewAdapter.ViewHolder> {

    private List<Device> mDeviceList;
    private String mImgPrefix;

    public void setDevice(List<Device> sensorList) {
        mDeviceList = sensorList;
        notifyDataSetChanged();
    }

    public void setImgPrefix(String prefix) {
        mImgPrefix = prefix;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_sensor, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Device device = mDeviceList.get(position);
        Glide.with(holder.typeImageView.getContext())
                .load(mImgPrefix+device.getImgUrl()).error(R.drawable.ic_soil)
                .into(holder.typeImageView);
        holder.typeView.setText(device.getName());
        holder.areaView.setText(device.getAreaName());
        holder.nodeView.setText(device.getNodeName());
        holder.stateView.setText(device.isState()?"状态：已开":"状态：已关");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DeviceDetailActivity.class);
                intent.putExtra(IConstants.KEY_DEVICE_NAME, device.getName());
                intent.putExtra(IConstants.KEY_DEVICE_ID, device.getId());
                intent.putExtra(IConstants.KEY_URI_IMAGE, mImgPrefix+device.getImgUrl());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDeviceList ==null?0: mDeviceList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView typeView, stateView, toDoView, areaView, nodeView;
        ImageView typeImageView;

        ViewHolder(View view) {
            super(view);
            typeView = (TextView) view.findViewById(R.id.tv_sensor_name);
            stateView = (TextView) view.findViewById(R.id.tv_sensor_value);
            toDoView = (TextView) view.findViewById(R.id.tv_sensor_threshold);
            areaView = (TextView) view.findViewById(R.id.tv_sensor_area);
            nodeView = (TextView) view.findViewById(R.id.tv_sensor_node);
            typeImageView = (ImageView) view.findViewById(R.id.tv_sensor_image);
            view.findViewById(R.id.img_btn_more).setVisibility(View.INVISIBLE);
        }
    }
}
