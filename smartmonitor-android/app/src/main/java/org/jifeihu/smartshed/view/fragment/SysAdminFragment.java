package org.jifeihu.smartshed.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.leakcanary.RefWatcher;

import org.jifeihu.smartshed.R;
import org.jifeihu.smartshed.SmartShedApp;
import org.jifeihu.smartshed.network.OkHttpManager;

/**
 * create an instance of this fragment.
 */
public class SysAdminFragment extends Fragment {

    private OkHttpManager mOkHttpManager;

    /**
     * 使用工厂方法创建此fragment的实例
     *
     * @return A new instance of fragment SysAdminFragment.
     */
    public static SysAdminFragment newInstance() {
        return new SysAdminFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOkHttpManager = OkHttpManager.getOkHttpManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_system_admin, container, false);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = SmartShedApp.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

}
