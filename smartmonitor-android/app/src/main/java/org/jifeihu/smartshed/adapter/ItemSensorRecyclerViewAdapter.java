package org.jifeihu.smartshed.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.jifeihu.smartshed.R;
import org.jifeihu.smartshed.bean.Sensor;
import org.jifeihu.smartshed.interfaces.IConstants;
import org.jifeihu.smartshed.view.activity.SensorDetailActivity;
import org.jifeihu.smartshed.view.activity.ThresholdActivity;

import java.util.List;

public class ItemSensorRecyclerViewAdapter extends RecyclerView.Adapter<ItemSensorRecyclerViewAdapter.ViewHolder> {

    private List<Sensor> mSensorList;
    private List<Byte> mValues;
    private String mImgPrefix;

    public void setValues(List<Byte> values) {
        mValues = values;
        notifyDataSetChanged();
    }
    public void setSensor(List<Sensor> sensorList) {
        mSensorList = sensorList;
        notifyDataSetChanged();
    }

    public void setImgPrefix(String prefix) {
        mImgPrefix = prefix;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_sensor, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Sensor sensor = mSensorList.get(position);
        Glide.with(holder.typeImageView.getContext())
                .load(mImgPrefix+sensor.getImgUrl()).error(R.drawable.ic_soil)
                .into(holder.typeImageView);
        holder.typeView.setText(sensor.getName());
        if(mValues != null) {
            holder.valueView.setText("实时值：" + mValues.get(position) + sensor.getUnit());
        }else {
            holder.valueView.setText("实时值：-- " + sensor.getUnit());
        }
        holder.areaView.setText(sensor.getAreaName());
        holder.nodeView.setText(sensor.getNodeName());
        holder.thresholdView.setText("阈值：" + sensor.getThreshold().getMin()+"~"
                +sensor.getThreshold().getMax());
    }

    @Override
    public int getItemCount() {
        return mSensorList==null?0:mSensorList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView typeView, valueView, thresholdView, areaView, nodeView;
        ImageView typeImageView;
        ImageButton moreBtn;

        ViewHolder(View view) {
            super(view);
            typeView = (TextView) view.findViewById(R.id.tv_sensor_name);
            valueView = (TextView) view.findViewById(R.id.tv_sensor_value);
            thresholdView = (TextView) view.findViewById(R.id.tv_sensor_threshold);
            areaView = (TextView) view.findViewById(R.id.tv_sensor_area);
            nodeView = (TextView) view.findViewById(R.id.tv_sensor_node);
            typeImageView = (ImageView) view.findViewById(R.id.tv_sensor_image);
            moreBtn = (ImageButton) view.findViewById(R.id.img_btn_more);
            moreBtn.setOnClickListener(this);
            view.setOnClickListener(this);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Snackbar.make(v,"进入设置阈值界面？",Snackbar.LENGTH_LONG).setAction("确认",
                            ViewHolder.this).show();
                    return true;
                }
            });
        }

        @Override
        public void onClick(View v) {
            int position = ViewHolder.this.getAdapterPosition();
            Sensor sensor = mSensorList.get(position);
            int id = v.getId();
            if(id == R.id.img_btn_more) {
                showMenu(v, sensor);
            }else if(id == this.itemView.getId()) {
                startActivity(v.getContext(), SensorDetailActivity.class, sensor);
            }else {
                startActivity(v.getContext(), ThresholdActivity.class, sensor);
            }
        }
    }

    /**
     * 弹出菜单
     * @param v
     * @param sensor
     */
    private void showMenu(final View v, final Sensor sensor) {
        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
        popupMenu.inflate(R.menu.menu_item_more);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if(id==R.id.menu_data_detail) {
                    startActivity(v.getContext(), SensorDetailActivity.class, sensor);
                }else {
                    startActivity(v.getContext(), ThresholdActivity.class, sensor);
                }
                return true;
            }
        });
        popupMenu.show();
    }

    /**
     * 跳转到新activity
     * @param context
     * @param clz
     * @param sensor
     */
    public void startActivity(Context context, Class<? extends Activity> clz, Sensor sensor) {
        Intent intent = new Intent(context, clz);
        intent.putExtra(IConstants.KEY_SENSOR_NAME, sensor.getName());
        intent.putExtra(IConstants.KEY_SENSOR_ID, sensor.getId());
        intent.putExtra(IConstants.KEY_SENSOR_UNIT, sensor.getUnit());
        intent.putExtra(IConstants.KEY_THRESHOLD_MIN, sensor.getThreshold().getMin());
        intent.putExtra(IConstants.KEY_THRESHOLD_MAX, sensor.getThreshold().getMax());
        context.startActivity(intent);
    }

}
