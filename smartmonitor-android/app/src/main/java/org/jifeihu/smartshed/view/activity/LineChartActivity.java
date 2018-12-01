package org.jifeihu.smartshed.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import org.jifeihu.smartshed.R;
import org.jifeihu.smartshed.bean.MyResponse;
import org.jifeihu.smartshed.bean.SensorValue;
import org.jifeihu.smartshed.interfaces.IConstants;
import org.jifeihu.smartshed.network.OkHttpManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.listener.ViewportChangeListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PreviewLineChartView;

public class LineChartActivity extends AppCompatActivity implements View.OnClickListener,
        LineChartOnValueSelectListener, SwipeRefreshLayout.OnRefreshListener {

    // 传感器数据类型
    private String mSensorName;
    private Integer mSensorId;
    // 用于刷新组件
    private SwipeRefreshLayout mSwipeLayout;
    // 展示区域的折线图
    private LineChartView mLineChartView;
    // 预览区域的折线图
    private PreviewLineChartView mPreLineChartView;
    // 展示区域的数据
    private LineChartData mChartData;
    // 预览区域的数据
    private LineChartData mPreChartData;
    // 起始时间
    private TextView mFirstTimeTV;
    // 终止时间
    private TextView mLastTimeTV;
    // 折线图集合视图
    private View mChartsView;
    // 显示非正常请求结果的视图
    private TextView mRequestFiledView;
    // 异步获取数据任务
    private DataGetTask mDataGetTask;
    // 网络请求
    private OkHttpManager mOkHttpManager;
    // 日期格式化
    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);


        Intent intent = getIntent();
        mOkHttpManager = OkHttpManager.getOkHttpManager();
        mSensorName = intent.getStringExtra(IConstants.KEY_SENSOR_NAME);
        // 设置标题栏
        setActionBar(mSensorName);
        mSensorId = intent.getIntExtra(IConstants.KEY_SENSOR_ID, 1);
        initView(intent.getStringExtra(IConstants.KEY_TIME_FIRST), intent.getStringExtra(IConstants.KEY_TIME_LAST));
        mDataGetTask = new DataGetTask();
        mDataGetTask.execute();
    }

    /**
     * 初始化组件
     */
    private void initView(String ft, String lt) {

        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout_chart);
        mSwipeLayout.setOnRefreshListener(this);

        mChartsView = findViewById(R.id.linear_layout_line_chart);

        mRequestFiledView = (TextView) findViewById(R.id.text_view_request_filed);

        mFirstTimeTV = (TextView) findViewById(R.id.tv_first_date);
        mLastTimeTV = (TextView) findViewById(R.id.tv_last_date);

        mFirstTimeTV.setText(ft.replaceAll("\\s\\d{2}:\\d{2}:\\d{2}",""));
        mLastTimeTV.setText(lt.replaceAll("\\s\\d{2}:\\d{2}:\\d{2}",""));

        mFirstTimeTV.setOnClickListener(this);
        mLastTimeTV.setOnClickListener(this);

        mLineChartView = (LineChartView) findViewById(R.id.lineChartView);
        mLineChartView.setOnValueTouchListener(this);
        mPreLineChartView = (PreviewLineChartView) findViewById(R.id.previewLineChartView);
        // 预览区滑动监听
        mPreLineChartView.setViewportChangeListener(new ViewportListener());
    }

    /**
     * 设置选区和预览区的所有数据
     */
    private void setAllData(List<SensorValue> list) {

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
        line.setColor(ChartUtils.COLOR_GREEN);
        // 画点
        line.setHasPoints(true);

        List<Line> lines = new ArrayList<>();
        lines.add(line);

        // 设置数据其他属性 比如坐标轴
        mChartData = new LineChartData(lines);
        mChartData.setAxisXBottom(new Axis());
        mChartData.setAxisYLeft(new Axis().setHasLines(true));

        // 将相同的数据也设置到预览区 并更改颜色
        mPreChartData = new LineChartData(mChartData);
        mPreChartData.getLines().get(0).setColor(ChartUtils.DEFAULT_DARKEN_COLOR);

        // 设置选中区数据
        mLineChartView.setLineChartData(mChartData);
        // 设置预览区数据
        mPreLineChartView.setLineChartData(mPreChartData);
        // 禁用缩放
        mLineChartView.setZoomEnabled(false);
        // 禁用滚动
        mLineChartView.setScrollEnabled(false);
        // 初识默认显示1/4选区框
        previewX();
    }

    /**
     * 根据X方向预览
     */
    private void previewX() {
        Viewport tempViewport = new Viewport(mLineChartView.getMaximumViewport());
        // 原区域1/4
        float dx = tempViewport.width() / 4;
        // 设置临时窗口大小
        tempViewport.inset(dx, 0);
        // 根据是否有动画 设置数据
        mPreLineChartView.setCurrentViewportWithAnimation(tempViewport);
        // 只能水平缩放
        mPreLineChartView.setZoomType(ZoomType.HORIZONTAL);
    }

    /**
     * 设置标题栏
     */
    private void setActionBar(String sensorKey) {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(sensorKey+actionBar.getTitle());
        }
    }


    /**
     * 设置进度条与表单的显示
     * @param show 是否显示
     */
    private void showProcessBar(boolean show) {
        mSwipeLayout.setRefreshing(show);
        mChartsView.setVisibility(show ? View.GONE : View.VISIBLE);
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
    public void onValueSelected(int i, int i1, PointValue pointValue) {
        char[] label = pointValue.getLabelAsChars();
        Toast.makeText(LineChartActivity.this, new String(label) + "---" + pointValue.getY(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValueDeselected() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.tv_first_date) {
            showTimeChooseDialog(0);
        }else if(id == R.id.tv_last_date) {
            showTimeChooseDialog(1);
        }
    }

    // 显示时间选择对话框
    private void showTimeChooseDialog(final int index) {
        if(mDataGetTask != null) {
            mSwipeLayout.setRefreshing(false);
            return;
        }
        // 新建对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 设置标题
        builder.setTitle("日期选择");
        // 相对布局
        RelativeLayout layout = new RelativeLayout(this);
        // 日期选择器
        final DatePicker datePicker = new DatePicker(this);
        // 防止bug http://stackoverflow.com/questions/13379512/nullpointerexception-when-using-datepicker-setmaxdate-in-a-datepickerdialog
        datePicker.setCalendarViewShown(false);
        // 设置布局
        datePicker.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        // 可选择的最大日期为当前日期
        if(index == 0) {
            try {
                Date date = mSimpleDateFormat.parse(mLastTimeTV.getText().toString());
                datePicker.setMaxDate(date.getTime() - 24*6*60*1000);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else {
            try {
                Date date = mSimpleDateFormat.parse(mFirstTimeTV.getText().toString());
                datePicker.setMinDate(date.getTime() + 24*60*60*1000);
                datePicker.setMaxDate(System.currentTimeMillis());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        // 添加日期选择器到父布局
        layout.addView(datePicker);
        // 设置内容视图
        builder.setView(layout);
        // 设置确认按键及监听
        builder.setPositiveButton("确认", new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String date = datePicker.getYear() +"-"+(datePicker.getMonth() + 1) +"-"+ datePicker.getDayOfMonth();
                if(index == 0) {
                    mFirstTimeTV.setText(date);
                }else {
                    mLastTimeTV.setText(date);
                }
                String firstTime = mFirstTimeTV.getText().toString();
                String lastTime = mLastTimeTV.getText().toString();

                mDataGetTask = new DataGetTask();
                mDataGetTask.execute(firstTime, lastTime);

            }
        });
        builder.setNegativeButton("取消", null);
        // 显示对话框
        builder.show();
    }

    @Override
    public void onRefresh() {
        if(mDataGetTask != null) {
            mSwipeLayout.setRefreshing(false);
            return;
        }

        String firstTime = mFirstTimeTV.getText().toString();
        String lastTime = mLastTimeTV.getText().toString();

        mDataGetTask = new DataGetTask();
        mDataGetTask.execute(firstTime, lastTime);
    }

    /**
     * 预览区滑动监听
     */
    private class ViewportListener implements ViewportChangeListener {
        @Override
        public void onViewportChanged(Viewport newViewport) {
            // 直接设置当前窗口图表
            mLineChartView.setCurrentViewport(newViewport);
        }
    }
    /**
     * 异步获取数据任务
     */
    public class DataGetTask extends AsyncTask<String, Void, MyResponse> {

        @Override
        protected void onPreExecute() {
            showProcessBar(true);
            mRequestFiledView.setVisibility(View.GONE);
        }

        @Override
        protected MyResponse doInBackground(String... params) {
            if(params==null || params.length!=2) {
                return mOkHttpManager.getMyResponseByGet(OkHttpManager.URL_SENSOR_VALUE+mSensorId,
                        new TypeToken<MyResponse<List<SensorValue>>>() {}.getType());
            }
            Map<String, String> map = new HashMap<>();
            map.put(IConstants.KEY_TIME_FIRST, params[0]);
            map.put(IConstants.KEY_TIME_LAST, params[1]);

            return mOkHttpManager.getMyResponseByPost(OkHttpManager.URL_SENSOR_VALUE+mSensorId,
                    map, new TypeToken<MyResponse<List<SensorValue>>>() {}.getType());
        }

        @Override
        protected void onPostExecute(final MyResponse result) {
            mDataGetTask = null;
            showProcessBar(false);
            mSwipeLayout.setRefreshing(false);
            if(result == null || result.getMeta() == null) {
                mRequestFiledView.setText(getString(R.string.request_filed_and_check));
                mRequestFiledView.setVisibility(View.VISIBLE);
                return;
            }
            if(!result.getMeta().isSuccess()) {
                Snackbar.make(mLineChartView,result.getMeta().getMessage(),Snackbar.LENGTH_SHORT).show();
                return;
            }
            List<SensorValue> sensorValueList = (List<SensorValue>) result.getData();
            if(sensorValueList.size()==0) {
                Snackbar.make(mChartsView, getString(R.string.not_record), Snackbar.LENGTH_SHORT).show();
                return;
            }
            setAllData(sensorValueList);
        }

        @Override
        protected void onCancelled() {
            mDataGetTask = null;
            showProcessBar(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDataGetTask = null;
    }
}
