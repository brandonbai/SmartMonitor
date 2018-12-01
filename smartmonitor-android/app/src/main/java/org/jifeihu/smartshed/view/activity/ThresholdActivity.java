package org.jifeihu.smartshed.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.gson.Gson;

import org.jifeihu.smartshed.R;
import org.jifeihu.smartshed.bean.MyResponse;
import org.jifeihu.smartshed.interfaces.IConstants;
import org.jifeihu.smartshed.network.OkHttpManager;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ThresholdActivity extends AppCompatActivity implements IConstants {

    private int mSensorId;
    // 阈值编辑框
    private EditText mMaxET;
    private EditText mMinET;
    private OkHttpManager mOkHttpManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thresholds);

        Intent intent = getIntent();
        String title = intent.getStringExtra(KEY_SENSOR_NAME);
        mSensorId = intent.getIntExtra(KEY_SENSOR_ID, 1);
        int min = intent.getIntExtra(KEY_THRESHOLD_MIN, 0);
        int max = intent.getIntExtra(KEY_THRESHOLD_MAX, 0);
        setActionBar(title);
        initView(min, max);

        mOkHttpManager = OkHttpManager.getOkHttpManager();

    }

    /**
     * 设置标题栏
     */
    private void setActionBar(String title) {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setTitle(title+actionBar.getTitle());
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * 初始化组件
     */
    private void initView(int min, int max) {

        mMaxET = (EditText) findViewById(R.id.et_max);
        mMaxET.setText(max+"");
        mMinET = (EditText) findViewById(R.id.et_min);
        mMinET.setText(min+"");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_thresholds,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home) {
            finish();
            return true;
        }else if(id == R.id.menu_ok) {
            setThresholds();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 修改阈值
     */

    private void setThresholds() {

        if(checkValue(mMaxET, mMinET)) {
            OkHttpClient client = mOkHttpManager.getClient();
            RequestBody requestBody = new FormBody.Builder().add(IConstants.KEY_THRESHOLD_MAX,mMaxET.getText().toString())
                    .add(IConstants.KEY_THRESHOLD_MIN, mMinET.getText().toString()).add("id", mSensorId+"").build();
            Request request = new Request.Builder().url(mOkHttpManager.getPath()+OkHttpManager.URL_THRESHOLD_UPDATE)
                    .addHeader("token", mOkHttpManager.getToken()).post(requestBody).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Snackbar.make(mMaxET, getString(R.string.request_failed), Snackbar.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String result = response.body().string();
                    MyResponse myResp =  new Gson().fromJson(result, MyResponse.class);
                    if(myResp==null || myResp.getMeta()==null) {
                        Snackbar.make(mMaxET, getString(R.string.request_failed), Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                    if(!myResp.getMeta().isSuccess()) {
                        Snackbar.make(mMaxET, myResp.getMeta().getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                    Snackbar.make(mMaxET, getString(R.string.success_change), Snackbar.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * 检验阈值的最小值是否小于最大值
     * @param maxET 最大值编辑框
     * @param minET 最小值编辑框
     * @return 校验结果
     */
    private boolean checkValue(EditText maxET, EditText minET) {
        maxET.setError(null);
        boolean isGT = Integer.parseInt(maxET.getText().toString()) > Integer.parseInt(minET.getText().toString());
        if(!isGT) {
            maxET.setError(getString(R.string.error_max_lower_than_min));
            maxET.requestFocus();
        }
        return isGT;
    }
}
