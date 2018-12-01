package org.jifeihu.smartshed.view.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import org.jifeihu.smartshed.R;
import org.jifeihu.smartshed.adapter.NewsRecyclerViewAdapter;
import org.jifeihu.smartshed.bean.Log;
import org.jifeihu.smartshed.bean.MyResponse;
import org.jifeihu.smartshed.network.OkHttpManager;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    // 请求结果视图
    private TextView mResultView;
    // 刷新组件
    private SwipeRefreshLayout mSwipeLayout;
    // 消息列表
    private RecyclerView mRecyclerView;
    // 适配器
    private NewsRecyclerViewAdapter mAdapter;
    private OkHttpManager mOkHttpManager;
    // 异步任务
    private LogTask mLogTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mResultView = (TextView)findViewById(R.id.text_view_request_filed_news);

        mSwipeLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_layout_news);
        mSwipeLayout.setOnRefreshListener(this);

        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view_message);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setAdapter(mAdapter = new NewsRecyclerViewAdapter());

        mOkHttpManager = OkHttpManager.getOkHttpManager();

        mLogTask = new LogTask();
        mLogTask.execute((Void)null);

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
        if(mLogTask != null) {
            mSwipeLayout.setRefreshing(false);
            return;
        }
        mLogTask = new LogTask();
        mLogTask.execute((Void)null);
    }

    /**
     * 异步任务
     */
    public class LogTask extends AsyncTask<Void, Void, MyResponse> {

        @Override
        protected void onPreExecute() {
            mSwipeLayout.setRefreshing(true);
            mResultView.setVisibility(View.GONE);
        }

        @Override
        protected MyResponse doInBackground(Void... params) {

            return mOkHttpManager.getMyResponseByGet(OkHttpManager.URL_LOG,
                    new TypeToken<MyResponse<List<Log>>>() {}.getType());
        }

        @Override
        protected void onPostExecute(final MyResponse result) {
            mLogTask = null;
            mSwipeLayout.setRefreshing(false);
            if(result == null) {
                mResultView.setText(getString(R.string.request_filed_and_check));
                mResultView.setVisibility(View.VISIBLE);
                return;
            }
            if(!result.getMeta().isSuccess()) {
                Snackbar.make(mRecyclerView, result.getMeta().getMessage(), Snackbar.LENGTH_SHORT).show();
                return;
            }
            mAdapter.setLogs((ArrayList)result.getData());
        }

        @Override
        protected void onCancelled() {
            mLogTask = null;
            mSwipeLayout.setRefreshing(false);
        }
    }
}
