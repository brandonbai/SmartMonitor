package org.jifeihu.smartshed.view.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import org.jifeihu.smartshed.R;
import org.jifeihu.smartshed.bean.MyResponse;
import org.jifeihu.smartshed.interfaces.IConstants;
import org.jifeihu.smartshed.network.OkHttpManager;
import org.jifeihu.smartshed.util.SharedPreferenceUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 登陆窗体
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * 跟踪登录任务，以确保可以取消请求
     */
    private UserLoginTask mAuthTask = null;

    private OkHttpManager mOkHttpManager;

    private SharedPreferenceUtil mSharedPreferenceUtil;

    // UI引用
    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mOkHttpManager = OkHttpManager.getOkHttpManager();
        mSharedPreferenceUtil = SharedPreferenceUtil.getInstance(LoginActivity.this.getApplicationContext());
        mOkHttpManager.setPath(mSharedPreferenceUtil.getServerHome());
        String token = mSharedPreferenceUtil.getToken();
        if(!TextUtils.isEmpty(token)) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        //设置登录表单
        mUsernameView = (AutoCompleteTextView) findViewById(R.id.email);
        String username = mSharedPreferenceUtil.getUserInfo();
        mUsernameView.setText(username);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    /**
     * 尝试登录或注册登录表单指定的帐户。
     * 如果有表单错误（无效的电子邮件，缺少字段等），
     * 提供错误，并且不进行实际登录尝试。
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        mUsernameView.setError(null);
        mPasswordView.setError(null);

        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            // 显示进度条，开启后台的登录任务
            showProgress(true);
            mAuthTask = new UserLoginTask(username, password);
            mAuthTask.execute((Void) null);
        }
    }

    /**
     * 显示进度UI并隐藏登录表单。
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * 异步登录验证任务
     */
    public class UserLoginTask extends AsyncTask<Void, Void, MyResponse> {

        private final String mUsername;
        private final String mPassword;

        UserLoginTask(String username, String password) {
            mUsername = username;
            mPassword = password;
        }

        @Override
        protected MyResponse doInBackground(Void... params) {
            Map<String, String> map = new HashMap<>();
            map.put("username", mUsername);
            map.put("password", mPassword);
            return mOkHttpManager.getMyResponseByPost(OkHttpManager.URL_LOGIN, map,
                    new TypeToken<MyResponse<String>>() {}.getType());
        }

        @Override
        protected void onPostExecute(final MyResponse myResponse) {
            mAuthTask = null;
            showProgress(false);
            if(myResponse == null || myResponse.getMeta() == null) {
                Snackbar.make(mPasswordView,getString(R.string.request_failed),Snackbar.LENGTH_SHORT).show();
                return;
            }
            if(!myResponse.getMeta().isSuccess()) {
                mPasswordView.setError(myResponse.getMeta().getMessage());
                mPasswordView.requestFocus();
                return;
            }
            String token = (String)myResponse.getData();
            mSharedPreferenceUtil.setToken(token);
            mSharedPreferenceUtil.setUserInfo(mUsername);
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_login,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_conn_cooper) {
            startActivity(new Intent(LoginActivity.this, DirectConnectionActivity.class));
            finish();
        } else if(id == R.id.menu_set_conn) {
            showNetworkDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 显示连接设置对话框
     */
    private void showNetworkDialog() {
        final SharedPreferenceUtil mSharedPreferenceUtil = SharedPreferenceUtil.getInstance(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("网络设置");
        String[] networkInfo = mSharedPreferenceUtil.getNetworkInfo();
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText ipET = new EditText(this);
        ipET.setText(networkInfo[0]);
        final EditText portET = new EditText(this);
        portET.setText(networkInfo[1]);
        layout.addView(ipET);
        layout.addView(portET);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String ip = ipET.getText().toString();
                String port = portET.getText().toString();

                if(TextUtils.isEmpty(ip) || TextUtils.isEmpty(port)) {
                    Toast.makeText(LoginActivity.this, getString(R.string.error_null), Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!ip.matches(IConstants.REGEX_IP)) {
                    Toast.makeText(LoginActivity.this, getString(R.string.error_format_ip), Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!port.matches(IConstants.REGEX_PORT)) {
                    Toast.makeText(LoginActivity.this, getString(R.string.error_format_port), Toast.LENGTH_SHORT).show();
                    return;
                }

                mSharedPreferenceUtil.setNetworkInfo(ip, port);
                mOkHttpManager.setPath(mSharedPreferenceUtil.getServerHome());
            }
        });
        builder.setView(layout).show();
    }
}

