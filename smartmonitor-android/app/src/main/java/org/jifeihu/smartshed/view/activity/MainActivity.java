package org.jifeihu.smartshed.view.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;

import org.jifeihu.smartshed.R;
import org.jifeihu.smartshed.adapter.SectionsPagerAdapter;
import org.jifeihu.smartshed.bean.MyResponse;
import org.jifeihu.smartshed.interfaces.IConstants;
import org.jifeihu.smartshed.network.OkHttpManager;
import org.jifeihu.smartshed.util.SharedPreferenceUtil;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private PasswordChangeTask mPasswordChangeTask;
    private SharedPreferenceUtil mSharedPreferenceUtil;
    private OkHttpManager mOkHttpManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 标题栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        ViewPager viewPager = (ViewPager) findViewById(R.id.container_main);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_main);
        tabLayout.setupWithViewPager(viewPager);
        // 抽屉布局
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        // 导航栏
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        mOkHttpManager = OkHttpManager.getOkHttpManager();
        mSharedPreferenceUtil = SharedPreferenceUtil.getInstance(this);
        mOkHttpManager.setPath(mSharedPreferenceUtil.getServerHome());
        mOkHttpManager.setToken(mSharedPreferenceUtil.getToken());
        ImageView headerBG = (ImageView)headerView.findViewById(R.id.iv_header);
        Glide.with(this).load(R.drawable.ic_brandon)
                .into(headerBG);
        // 在左侧菜单显示用户名
        TextView userTextView = (TextView)headerView.findViewById(R.id.textView_user);
        userTextView.setText(mSharedPreferenceUtil.getUserInfo());
        // 修改密码文本框
        TextView changePasswordTextView = (TextView)headerView.findViewById(R.id.textView_change_password);
        changePasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPasswordChangeTask != null) {
                    return;
                }
                showChangePasswordDialog();
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            Log.i(TAG,"- - - 抽屉退出 - - -");
        } else {
            Log.i(TAG,"- - - 主窗体退出 - - -");
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_news) {
            startActivity(new Intent(this, MessageActivity.class));
        } else if (id == R.id.nav_user) {
            startActivity(new Intent(MainActivity.this, UserActivity.class));
        } else if (id == R.id.nav_help) {
            startActivity(new Intent(MainActivity.this, InstructionsActivity.class));
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(this, AboutActivity.class));
        } else if (id == R.id.nav_logout) {
            mSharedPreferenceUtil.setToken(null);
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_notification) {
            boolean state = mSharedPreferenceUtil.getNotificationState();
            mSharedPreferenceUtil.setNotificationState(!state);
            invalidateOptionsMenu();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        MenuItem item = menu.findItem(R.id.action_notification);
        boolean state = mSharedPreferenceUtil.getNotificationState();
        if(!state) {
            item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_notifications_off_white_24dp));
        }else {
            item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_notifications_none_white_24dp));
        }

        return true;
    }

    /**
     * 弹出修改密码对话框
     */
    private void showChangePasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("修改密码");
        int inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText previousET = new EditText(this);
        previousET.setHint("原密码");
        previousET.setInputType(inputType);
        final EditText passwordET = new EditText(this);
        passwordET.setHint("新密码");
        passwordET.setInputType(inputType);
        final EditText repeatET = new EditText(this);
        repeatET.setHint("再次确认新密码");
        repeatET.setInputType(inputType);
        layout.addView(previousET);
        layout.addView(passwordET);
        layout.addView(repeatET);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String previous = previousET.getText().toString();
                String password = passwordET.getText().toString();
                String repeatPassword = repeatET.getText().toString();


                if(TextUtils.isEmpty(previous) || TextUtils.isEmpty(password)
                        || TextUtils.isEmpty(repeatPassword)) {
                    Toast.makeText(MainActivity.this, getString(R.string.error_null), Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!password.equals(repeatPassword)) {
                    Toast.makeText(MainActivity.this, getString(R.string.error_not_repeat_password), Toast.LENGTH_SHORT).show();
                    return;
                }
                mPasswordChangeTask = new PasswordChangeTask(password, previous);
                mPasswordChangeTask.execute((Void)null);

            }
        });
        builder.setView(layout).show();

    }

    /**
     * 异步修改密码任务
     */
    public class PasswordChangeTask extends AsyncTask<Void, Void, MyResponse> {

        private final String mPassword;
        private final String mNewPassword;

        PasswordChangeTask(String newPassword, String password) {
            mNewPassword = newPassword;
            mPassword = password;
        }

        @Override
        protected MyResponse doInBackground(Void... params) {
            String username = mSharedPreferenceUtil.getUserInfo();
            Map<String, String> map = new HashMap<>();
            map.put("username", username);
            map.put("newPassword", mNewPassword);
            map.put("password", mPassword);

            return mOkHttpManager.getMyResponseByPost(OkHttpManager.URL_CHANGE_PWD, map, new TypeToken<MyResponse>() {}.getType());

        }

        @Override
        protected void onPostExecute(final MyResponse result) {
            mPasswordChangeTask = null;
            if(result == null || result.getMeta() == null) {
                Toast.makeText(MainActivity.this, getString(R.string.request_failed),Toast.LENGTH_SHORT).show();
                return;
            }
            if(!result.getMeta().isSuccess()) {
                Toast.makeText(MainActivity.this, result.getMeta().getMessage(),Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(MainActivity.this, getString(R.string.request_success),Toast.LENGTH_SHORT).show();
            mSharedPreferenceUtil.setToken("");
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        @Override
        protected void onCancelled() {
            mPasswordChangeTask = null;
        }
    }

    public void showAlarm(String ticker, String title, String content) {

        NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle(title)
                .setContentText(content)
                .setTicker(ticker)
                .setAutoCancel(true)
                .setSmallIcon(android.R.drawable.ic_dialog_alert);

        SharedPreferences sharedPreferences = mSharedPreferenceUtil.getNotification();
        boolean hasSound = sharedPreferences.getBoolean(IConstants.KEY_SOUND, true);
        boolean hasVibrate = sharedPreferences.getBoolean(IConstants.KEY_VIBRATE, true);
        if(hasSound || hasVibrate) {
            int flag = 0;
            if(hasSound) {
                flag = Notification.DEFAULT_SOUND | flag;
            }
            if(hasVibrate) {
                flag = Notification.DEFAULT_VIBRATE | flag;
            }
            builder.setDefaults(flag);
        }

        nm.notify(0, builder.build());
    }
}
