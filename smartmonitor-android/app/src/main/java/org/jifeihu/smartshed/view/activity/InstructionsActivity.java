package org.jifeihu.smartshed.view.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import org.jifeihu.smartshed.R;
import org.jifeihu.smartshed.SmartShedApp;
import org.jifeihu.smartshed.interfaces.IConstants;
import org.jifeihu.smartshed.util.SharedPreferenceUtil;

public class InstructionsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private WebView mWebView;
    private ProgressBar mProgressBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mProgressBar = (ProgressBar) findViewById(R.id.instruction_progress);
        mProgressBar.setMax(100);
        SharedPreferenceUtil sharedPreferenceUtil = SharedPreferenceUtil.getInstance(this);

        mWebView = (WebView) findViewById(R.id.web_view_instruction);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setAppCachePath(SmartShedApp.cachePath);
        mWebView.setWebChromeClient(new WebClient());
        mWebView.loadUrl(sharedPreferenceUtil.getServerHome() + IConstants.URI_INSTRUCTION);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout_instruction);
        mSwipeRefreshLayout.setOnRefreshListener(this);

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
        mWebView.reload();
    }

    private class WebClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mProgressBar.setProgress(newProgress);

            if(newProgress == 100) {
                mSwipeRefreshLayout.setRefreshing(false);
                mProgressBar.setVisibility(View.GONE);
            }
        }

    }

    @Override
    protected void onDestroy() {
        mWebView.destroy();
        super.onDestroy();
    }
}
