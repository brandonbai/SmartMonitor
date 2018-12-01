package org.jifeihu.smartshed.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jifeihu.smartshed.R;
import org.jifeihu.smartshed.adapter.ItemCommandRecyclerViewAdapter;
import org.jifeihu.smartshed.bean.Command;
import org.jifeihu.smartshed.bean.MyResponse;
import org.jifeihu.smartshed.interfaces.IConstants;
import org.jifeihu.smartshed.network.OkHttpManager;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DeviceDetailActivity extends AppCompatActivity {

    private Integer mDeviceId;
    private String mDeviceName;
    private OkHttpManager mOkHttpManager;
    private RecyclerView mRecyclerView;
    private ItemCommandRecyclerViewAdapter mAdapter;
    private ImageView mImageView;
    private String mImageUrl;
    private static Handler handler;
    private List<Command> commandList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_detail);
        Intent intent = getIntent();
        mDeviceId = intent.getIntExtra(IConstants.KEY_DEVICE_ID, 1);
        mDeviceName = intent.getStringExtra(IConstants.KEY_DEVICE_NAME);
        mImageUrl = intent.getStringExtra(IConstants.KEY_URI_IMAGE);
        initView();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0x12:
                        Snackbar.make(mImageView, getString(R.string.request_failed), Snackbar.LENGTH_SHORT).show();
                    break;
                    case 0x13:
                        String message = msg.getData().getString("msg");
                        Snackbar.make(mImageView, message, Snackbar.LENGTH_SHORT).show();
                        break;
                    case 0x14:
                        mAdapter.setCommands(commandList);
                        break;
                    default:
                        break;
                }
            }
        };
        initData();


    }

    /**
     * 设置数据
     */
    private void initData() {
        mOkHttpManager = OkHttpManager.getOkHttpManager();
        OkHttpClient client = mOkHttpManager.getClient();
        Request request = new Request.Builder().url(mOkHttpManager.getPath()+OkHttpManager.URL_COMMAND+mDeviceId)
                .cacheControl(new CacheControl.Builder().maxAge(10, TimeUnit.SECONDS).build())
                .addHeader("token", mOkHttpManager.getToken()).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(0x12);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                MyResponse<List<Command>> myResp =  new Gson().fromJson(result, new TypeToken<MyResponse<List<Command>>>() {}.getType());
                if(myResp==null || myResp.getMeta()==null) {
                    handler.sendEmptyMessage(0x12);
                    return;
                }
                if(!myResp.getMeta().isSuccess()) {
                    Message msg = new Message();
                    msg.what=0x13;
                    Bundle bundle = new Bundle();
                    bundle.putString("msg",myResp.getMeta().getMessage());
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
                commandList = myResp.getData();
                handler.sendEmptyMessage(0x14);
            }
        });
    }

    /**
     * 初始化视图
     */
    private void initView() {
        setActionBar(mDeviceName);
        mImageView = (ImageView) findViewById(R.id.iv_device);
        Glide.with(this).load(mImageUrl).error(R.drawable.ic_water).into(mImageView);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_command);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        mRecyclerView.setAdapter(mAdapter= new ItemCommandRecyclerViewAdapter());

    }


    /**
     * 设置标题栏
     */
    private void setActionBar(String title) {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(title);
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

}
