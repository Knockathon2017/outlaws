package com.juster.login.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.juster.R;
import com.juster.data.LocalSyncBroadcast;
import com.juster.data.api.database.user.service.UserIntentService;
import com.juster.logger.LoggerUtils;
import com.juster.prefs.PreferenceManagerSingleton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by deepakj on 29/7/16.
 */
public class LoginActivity extends AppCompatActivity{

    private static final String TAG = LoginActivity.class.getName();

    TextView tv_signup;
    EditText et_username;
    EditText et_password;
    Button btn_login;
    ProgressBar mPbLogin;
    LocalSyncBroadcast mSyncBroadcaster;
    CheckBox is_guide;
    private ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    protected void onStart() {
        super.onStart();
        mSyncBroadcaster.registerReceiver(mUpdateReceiver, UserIntentService.getServiceIntentFilters());
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSyncBroadcaster.unregisterReceiver(mUpdateReceiver);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(PreferenceManagerSingleton.getInstance(this).getTempisLogin()) {
            Intent navigateIntent = new Intent(LoginActivity.this, SelectLocationActivity.class);
            navigateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(navigateIntent);
            finish();
        }

        setContentView(R.layout.activity_login_activity);
        mSyncBroadcaster = new LocalSyncBroadcast(this);

        initView();
    }

    private void initView() {
        et_username = (EditText) findViewById(R.id.et_login_user_name);
        mPbLogin = (ProgressBar) findViewById(R.id.pb_login);
        is_guide = (CheckBox) findViewById(R.id.is_guide);
        tv_signup = (TextView) findViewById(R.id.tv_signup);
        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this , SignupActivity.class);
                startActivity(intent);
            }
        });

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        et_password = (EditText) findViewById(R.id.et_password);
        et_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    login();
                    handled = true;
                }
                return handled;
            }
        });
    }

    private boolean ifLoginValid() {

        boolean isValid = true;
        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        //validity check for username
        if (username.isEmpty()) {
            Toast.makeText(this, getString(R.string.login_err_us_ps) , Toast.LENGTH_SHORT).show();
            isValid = false;
        }else if (!Patterns.PHONE.matcher(username).matches()) {

            isValid = false;
            Toast.makeText(this, getString(R.string.forgot_err_exz_id_in), Toast
                    .LENGTH_SHORT).show();

        } else if (password.isEmpty()) {
            Toast.makeText(this, getString(R.string.login_err_ps) , Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        return isValid;
    }

    private void clearTempUserPreference(){
        PreferenceManagerSingleton.getInstance(this).putTempPassword(null);
        PreferenceManagerSingleton.getInstance(this).putTempUserName(null);
        PreferenceManagerSingleton.getInstance(this).putTempisGuide(false);
    }

    private void login() {
        hideSoftKeyboard(et_password);
        if (ifLoginValid()) {
            loginRequestToServer();
        }
    }

    public void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void hideLoginShowProgress() {
        et_username.setEnabled(false);
        et_password.setEnabled(false);
        btn_login.setEnabled(false);
        mPbLogin.setVisibility(View.VISIBLE);
    }

    private void hideProgressShowLogin() {
        et_username.setEnabled(true);
        et_password.setEnabled(true);
        btn_login.setEnabled(true);
        mPbLogin.setVisibility(View.GONE);
    }

    private void showMessage(String msg) {
        hideProgressShowLogin();
        Toast.makeText(this, msg , Toast.LENGTH_SHORT).show();
    }

    private BroadcastReceiver mUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LoggerUtils.info(TAG, "LoginActivity mUpdateReceiver::::");
            String action = intent.getAction();
            if (action.equals(UserIntentService.IActivityActions.ACTION_LOGIN)) {
                if (intent.getIntExtra(UserIntentService.RESULT_TYPE, UserIntentService.RESULT_SUCCESS) ==
                        UserIntentService.RESULT_SUCCESS) {
                    LoggerUtils.info(TAG, "LoginActivity mUpdateReceiver :::: RESULT_SUCCESS");
                    showMessage("SuccessFully Login");
                    PreferenceManagerSingleton.getInstance(LoginActivity.this).putTempisLogin(true);
                    Intent navigateIntent = new Intent(LoginActivity.this , SelectLocationActivity.class);
                    navigateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(navigateIntent);
                } else {
                    LoggerUtils.info(TAG, "LoginActivity mUpdateReceiver :::: RESULT_MESSAGE");
                    showMessage("Oops Something went Wrong");
                    clearTempUserPreference();
                }
            }
        }
    };

    public void loginRequestToServer() {
        final String userEmail = et_username.getText().toString().trim();
        final String password = et_password.getText().toString().trim();

        final boolean isGuide = true;
        PreferenceManagerSingleton.getInstance(this).putTempContactNo(userEmail);
        PreferenceManagerSingleton.getInstance(this).putTempPassword(password);
        PreferenceManagerSingleton.getInstance(this).putTempisGuide(is_guide.isChecked());
        //Request for Login
        hideLoginShowProgress();
        Bundle bundle = new Bundle();
        startLoginService(UserIntentService.IActivityActions.ACTION_LOGIN, bundle);
    }

    private void startLoginService(String action, Bundle data) {
        Intent intent = new Intent(this, UserIntentService.class);
        intent.setAction(action);
        intent.putExtra(UserIntentService.USER_BUNDLE_DATA, data);
        startService(intent);
    }
}