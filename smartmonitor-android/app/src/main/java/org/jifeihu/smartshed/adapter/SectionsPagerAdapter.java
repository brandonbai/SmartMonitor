package org.jifeihu.smartshed.adapter;

/**
 * Created by Administrator on 2017/3/15.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.jifeihu.smartshed.view.fragment.DeviceFragment;
import org.jifeihu.smartshed.view.fragment.SensorFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private Fragment mSensorFragment;
    private Fragment mDeviceFragment;

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
        mSensorFragment = SensorFragment.newInstance();
        mDeviceFragment = DeviceFragment.newInstance();

    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case 0:
                return mSensorFragment;
            case 1:
                return mDeviceFragment;
        }
        return mSensorFragment;
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "数据监测";
            case 1:
                return "设备管理";
        }
        return null;
    }
}