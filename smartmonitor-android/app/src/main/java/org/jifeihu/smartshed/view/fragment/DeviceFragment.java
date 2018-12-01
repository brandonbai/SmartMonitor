package org.jifeihu.smartshed.view.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.squareup.leakcanary.RefWatcher;

import org.jifeihu.smartshed.R;
import org.jifeihu.smartshed.SmartShedApp;
import org.jifeihu.smartshed.adapter.ItemDeviceRecyclerViewAdapter;
import org.jifeihu.smartshed.bean.Device;
import org.jifeihu.smartshed.bean.MyResponse;
import org.jifeihu.smartshed.network.OkHttpManager;

import java.util.ArrayList;
import java.util.List;

/**
 * create an instance of this fragment.
 */
public class DeviceFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private OkHttpManager mOkHttpManager;
    // 用于刷新组件
    private SwipeRefreshLayout mSwipeLayout;
    private RecyclerView mRecyclerView;
    // 适配器
    private ItemDeviceRecyclerViewAdapter mAdapter;
    // 异步任务
    private DataGetTask mDataGetTask;

    /**
     * 使用工厂方法创建此fragment的实例
     */
    public static DeviceFragment newInstance() {

        return new DeviceFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOkHttpManager = OkHttpManager.getOkHttpManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_control, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_device);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setAdapter(mAdapter = new ItemDeviceRecyclerViewAdapter());

        mAdapter.setImgPrefix(mOkHttpManager.getPath());

        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout_device);
        mSwipeLayout.setOnRefreshListener(this);
        mDataGetTask = new DataGetTask();
        mDataGetTask.execute((Void) null);
//        Glide.with(getActivity()).load(R.drawable.heng_4)
//                .into((ImageView) view.findViewById(R.id.iv_bg));
        return view;
    }

    @Override
    public void onRefresh() {
        if(mDataGetTask != null) {
            return;
        }
        mDataGetTask = new DataGetTask();
        mDataGetTask.execute((Void) null);
    }

    /**
     * 异步获取数据任务
     */
    public class DataGetTask extends AsyncTask<Void, Void, MyResponse> {

        @Override
        protected void onPreExecute() {
            mSwipeLayout.setRefreshing(true);
        }

        @Override
        protected MyResponse doInBackground(Void... params) {

            return mOkHttpManager.getMyResponseByGet(OkHttpManager.URL_DEVICE,
                    new TypeToken<MyResponse<List<Device>>>() {}.getType());
        }

        @Override
        protected void onPostExecute(final MyResponse result) {
            mDataGetTask = null;
            mSwipeLayout.setRefreshing(false);
            if(result == null || result.getMeta() == null) {
                Snackbar.make(mRecyclerView,getString(R.string.request_failed),Snackbar.LENGTH_SHORT).show();
                return;
            }
            if(!result.getMeta().isSuccess()) {
                Snackbar.make(mRecyclerView,result.getMeta().getMessage(),Snackbar.LENGTH_SHORT).show();
                return;
            }
            mAdapter.setDevice((ArrayList)result.getData());
        }

        @Override
        protected void onCancelled() {
            mDataGetTask = null;
            mSwipeLayout.setRefreshing(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = SmartShedApp.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
