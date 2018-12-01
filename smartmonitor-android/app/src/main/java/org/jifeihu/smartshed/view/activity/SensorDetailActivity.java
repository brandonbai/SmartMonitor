package org.jifeihu.smartshed.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import org.jifeihu.smartshed.R;
import org.jifeihu.smartshed.bean.MyResponse;
import org.jifeihu.smartshed.bean.SensorValue;
import org.jifeihu.smartshed.interfaces.IConstants;
import org.jifeihu.smartshed.network.OkHttpManager;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class SensorDetailActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    // 异步任务
    private DataGetTask mDataGetTask;
    // 网络请求
    private OkHttpManager mOkHttpManager;
    // 标题
    private String mTitle;
    // 时间显示
    private TextView mDateTimeTV;
    // 用于刷新组件
    private SwipeRefreshLayout mSwipeLayout;
    // 实时数据
    private TextView mRealDataTV;
    // 实时数据
    private TextView mMaxDataTV;
    // 实时数据
    private TextView mMinDataTV;
    // 实时数据
    private TextView mAvgDataTV;
    // 实时数据
    private TextView mSpeedDataTV;
    // 实时数据
    private TextView mDataNumTV;
    // 折线图数据
    private LineChartData mChartData;
    // 折线图
    private LineChartView mLineChartView;
    // 传感器id
    private int mSensorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_detail);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            mTitle = getIntent().getStringExtra(IConstants.KEY_SENSOR_NAME);
            setActionBar(mTitle);
        }
        mOkHttpManager = OkHttpManager.getOkHttpManager();

        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout_sensor_detail);
        mSwipeLayout.setOnRefreshListener(this);

        mLineChartView = (LineChartView) findViewById(R.id.line_chart_view_sensor_detail);

        mDateTimeTV = (TextView) findViewById(R.id.tv_sensor_title);
        mRealDataTV = (TextView) findViewById(R.id.tv_sensor_real_time_data);
        mMinDataTV = (TextView) findViewById(R.id.tv_sensor_data_min);
        mMaxDataTV = (TextView) findViewById(R.id.tv_sensor_data_max);
        mAvgDataTV = (TextView) findViewById(R.id.tv_sensor_average);
        mSpeedDataTV = (TextView) findViewById(R.id.tv_sensor_data_speed);
        mDataNumTV = (TextView) findViewById(R.id.tv_sensor_data_num);

        mSensorId = getIntent().getIntExtra(IConstants.KEY_SENSOR_ID, 1);
        mDataGetTask = new DataGetTask(mSensorId);
        mDataGetTask.execute((Void) null);
    }

    /**
     * 设置标题栏
     */
    private void setActionBar(String title) {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(title+actionBar.getTitle());
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        if(mDataGetTask == null) {
            mDataGetTask = new DataGetTask(mSensorId);
            mDataGetTask.execute((Void) null);
        }
    }

    /**
     * 异步获取数据任务
     */
    public class DataGetTask extends AsyncTask<Void, Void, MyResponse> {

        private int sensorId;

        DataGetTask(int sensorId) {
            this.sensorId = sensorId;
        }

        @Override
        protected void onPreExecute() {
            mSwipeLayout.setRefreshing(true);
        }

        @Override
        protected MyResponse doInBackground(Void... params) {

            return mOkHttpManager.getMyResponseByGet(OkHttpManager.URL_SENSOR_VALUE+sensorId,
                    new TypeToken<MyResponse<List<SensorValue>>>() {}.getType());
        }

        @Override
        protected void onPostExecute(final MyResponse result) {
            mDataGetTask = null;
            mSwipeLayout.setRefreshing(false);
            if(result == null || result.getMeta() == null) {
                Snackbar.make(mLineChartView,getString(R.string.request_failed),Snackbar.LENGTH_SHORT).show();
                return;
            }
            if(!result.getMeta().isSuccess()) {
                Snackbar.make(mLineChartView,result.getMeta().getMessage(),Snackbar.LENGTH_SHORT).show();
                return;
            }
            setSensorDetail((List<SensorValue>) result.getData());
        }

        @Override
        protected void onCancelled() {
            mDataGetTask = null;
            mSwipeLayout.setRefreshing(false);
        }
    }

    public void setSensorDetail(List<SensorValue> sensorValueList) {
        if(sensorValueList==null || sensorValueList.size()==0) {
            return;
        }
        int max = 0;
        int min = 100;
        double sum = 0;

        for (SensorValue sensorValue : sensorValueList) {
            int value = sensorValue.getValue();
            sum += value;
            max = max < value?value:max;
            min = min > value?value:min;
        }

        double average = sum/sensorValueList.size();
        mDateTimeTV.setText(sensorValueList.get(0).getTime()+" ~ "+sensorValueList.get(sensorValueList.size()-1).getTime());
        mMinDataTV.setText(min+"");
        mMaxDataTV.setText(max+"");
        mAvgDataTV.setText(average+"");
        mSpeedDataTV.setText("3");
        mRealDataTV.setText("--");
        mDataNumTV.setText(""+sensorValueList.size());
        setAllData(sensorValueList, min, max);
    }

    /**
     * 设置折线图数据
     */
    private void setAllData(final List<SensorValue> list, int min, int max) {

        if(list.size() == 0) {
            return;
        }

        // 给每个点设置值
        List<PointValue> values = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            SensorValue sensorValue = list.get(i);
            String label = sensorValue.getTime();
            values.add(new PointValue(i, sensorValue.getValue())
                    .setLabel(label));
        }

        // 设置线的相关属性
        Line line = new Line(values);
        int color = ContextCompat.getColor(this, R.color.line_sensor_detail);
        line.setColor(color);
        // 画点
        line.setHasPoints(false);
        line.setCubic(true);

        // 背景线
        List<PointValue> bgValues = new ArrayList<>();
        bgValues.add(new PointValue(1, min-5));
        bgValues.add(new PointValue(2, max+5));
        Line bgLine = new Line(bgValues);
        bgLine.setColor(Color.argb(0,0,0,0));

        // 线条集合
        List<Line> lines = new ArrayList<>();
        lines.add(line);
        lines.add(bgLine);

        // 设置数据其他属性 比如坐标轴
        mChartData = new LineChartData(lines);
        mChartData.setAxisXBottom(new Axis());

        // 设置选中区数据
        mLineChartView.setLineChartData(mChartData);
        // 禁用缩放
        mLineChartView.setZoomEnabled(false);
        // 禁用滚动
        mLineChartView.setScrollEnabled(false);

        mLineChartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SensorDetailActivity.this, LineChartActivity.class);
                intent.putExtra(IConstants.KEY_SENSOR_ID, mSensorId);
                intent.putExtra(IConstants.KEY_SENSOR_NAME, mTitle);
                intent.putExtra(IConstants.KEY_TIME_FIRST, list.get(0).getTime());
                intent.putExtra(IConstants.KEY_TIME_LAST, list.get(list.size()-1).getTime());
                startActivity(intent);
            }
        });
    }

}
