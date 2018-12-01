package org.jifeihu.smartshed.view.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.jifeihu.smartshed.R;
import org.jifeihu.smartshed.adapter.ItemRecyclerViewAdapter;
import org.jifeihu.smartshed.util.TerminalService;

public class DirectConnectionActivity extends AppCompatActivity implements View.OnClickListener {

    private ItemRecyclerViewAdapter mAdapter;
    private SwitchCompat mSwitchCompat;
    private SocketTask mSocketTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_connection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view_monitor);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(mAdapter = new ItemRecyclerViewAdapter());
        initialButtons();

    }

    private void initialButtons() {
        findViewById(R.id.btn_pause).setOnClickListener(this);
        findViewById(R.id.btn_slow_down).setOnClickListener(this);
        findViewById(R.id.btn_speed_up).setOnClickListener(this);
        findViewById(R.id.btn_anticlockwise).setOnClickListener(this);
        findViewById(R.id.btn_clockwise).setOnClickListener(this);
        mSwitchCompat = (SwitchCompat) findViewById(R.id.switch_water_motor);
        mSwitchCompat.setOnClickListener(this);
        mSocketTask = new SocketTask(TerminalService.QUERY);
        mSocketTask.execute((Void)null);
    }

    @Override
    public void onClick(View v) {
        if(mSocketTask != null) {
            return;
        }
        int id = v.getId();
        byte[] data;
        switch (id) {
            case R.id.btn_clockwise:
                data = TerminalService.DOOR_ON;
                break;
            case R.id.btn_anticlockwise:
                data = TerminalService.DOOR_OFF;
                break;
            case R.id.btn_speed_up:
                data = TerminalService.DOOR_SPEED;
                break;
            case R.id.btn_slow_down:
                data = TerminalService.DOOR_SLOW;
                break;
            case R.id.btn_pause:
                data = TerminalService.DOOR_STOP;
                break;
            case R.id.switch_water_motor:
                data = mSwitchCompat.isChecked()?TerminalService.WATER_OFF:TerminalService.WATER_ON;
                break;
            default:
                return;
        }
        mSocketTask = new SocketTask(data);
        mSocketTask.execute((Void)null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_connect) {
            if(mSocketTask != null) {
                return true;
            }
            mSocketTask = new SocketTask(TerminalService.QUERY);
            mSocketTask.execute((Void)null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_direct_connect, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 异步任务
     */
    private class SocketTask extends AsyncTask<Void, Void, Byte[]> {

        private byte[] mData;

        SocketTask(byte[] data) {
            mData = data;
        }

        @Override
        protected Byte[] doInBackground(Void... params) {

            TerminalService terminalService = new TerminalService(DirectConnectionActivity.this);

            return terminalService.send(mData);
        }

        @Override
        protected void onPostExecute(Byte[] bytes) {
            mSocketTask = null;
            if(bytes != null) {
                mAdapter.setValues(new byte[]{bytes[0],bytes[1],bytes[2],bytes[3]});
                mSwitchCompat.setChecked(bytes[4] > 0);
            }else {
                if(mData == TerminalService.WATER_OFF || mData == TerminalService.WATER_ON) {
                    mSwitchCompat.setChecked(!mSwitchCompat.isChecked());
                }
            }

            Snackbar.make(mSwitchCompat, bytes != null ? getString(R.string.request_success)
                    : getString(R.string.request_failed), Snackbar.LENGTH_SHORT).show();
        }

        @Override
        protected void onCancelled() {
            mSocketTask = null;
        }
    }

}