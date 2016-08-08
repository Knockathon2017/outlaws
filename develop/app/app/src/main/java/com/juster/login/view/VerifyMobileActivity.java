package com.juster.login.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
public class VerifyMobileActivity extends AppCompatActivity {

    private static final String TAG = VerifyMobileActivity.class.getName();
    ImageView imv_verify_back;
    EditText et_contact_no;
    EditText et_otp_code;
    Button btn_request_code;
    ProgressBar mPbSignUp;
    LocalSyncBroadcast mSyncBroadcaster;
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
        setContentView(R.layout.activity_verify_mobile_activity);
        mSyncBroadcaster = new LocalSyncBroadcast(this);
        initView();
    }

    private void initView() {

        mPbSignUp = (ProgressBar) findViewById(R.id.pb_verify);
        imv_verify_back = (ImageView) findViewById(R.id.imv_verify_back);
        imv_verify_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        et_contact_no = (EditText) findViewById(R.id.et_contact_no);
        et_contact_no.setText(PreferenceManagerSingleton.getInstance(this).getTempContactNo());
        if (et_contact_no != null) {
            et_contact_no.setEnabled(false);
        }
        et_otp_code = (EditText) findViewById(R.id.et_otp_code);

        btn_request_code = (Button) findViewById(R.id.btn_request_code);
        btn_request_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ifContactValid() && ifOTPValid()) {
                    SignUpRequestToServer();
                }
            }
        });
    }

    private void verifyContact() {
        hideSoftKeyboard(et_otp_code);

        if (ifOTPValid()) {
            hideSignUpShowProgress();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    hideProgressShowSignUp();
                    showMessage("SuccessFully SignUp");
                    PreferenceManagerSingleton.getInstance(VerifyMobileActivity.this).putTempisLogin(true);
                    Intent navigateIntent = new Intent(VerifyMobileActivity.this, SelectLocationActivity.class);
                    navigateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent
                            .FLAG_ACTIVITY_CLEAR_TASK | Intent
                            .FLAG_ACTIVITY_NEW_TASK);
                    startActivity(navigateIntent);
                    finish();
                }
            }, 2500);
        }
    }

    private boolean ifContactValid() {
        boolean isValid = true;
        String contact_no = et_contact_no.getText().toString().trim();
        //validity check for username
        if (contact_no.isEmpty()) {

            Toast.makeText(this, getString(R.string.contact_err_ps) , Toast.LENGTH_SHORT).show();
            isValid = false;

        } else if (!Patterns.PHONE.matcher(contact_no).matches()) {

            isValid = false;
            Toast.makeText(this, getString(R.string.login_err_invalid_contact), Toast
                    .LENGTH_SHORT).show();
        }

        return isValid;
    }

    private boolean ifOTPValid() {
        boolean isValid = true;
        String otp_code = et_otp_code.getText().toString().trim();

        //validity check for username
        if (otp_code.isEmpty()) {

            Toast.makeText(this, getString(R.string.otp_err_ps) , Toast.LENGTH_SHORT).show();
            isValid = false;

        } else if (otp_code.length() > 10 || otp_code.length() < 4) {

            isValid = false;
            Toast.makeText(this, getString(R.string.invalid_otp_code), Toast
                    .LENGTH_SHORT).show();
        }
        return isValid;
    }

    public void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context
                .INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void hideSignUpShowProgress() {
        et_otp_code.setEnabled(false);
        et_contact_no.setEnabled(false);
        btn_request_code.setEnabled(false);
        mPbSignUp.setVisibility(View.VISIBLE);
    }

    private void hideProgressShowSignUp() {
        et_otp_code.setEnabled(true);
        et_contact_no.setEnabled(true);
        btn_request_code.setEnabled(true);
        mPbSignUp.setVisibility(View.GONE);
    }

    private void showMessage(String msg) {
        hideProgressShowSignUp();
        Toast.makeText(this, msg , Toast.LENGTH_SHORT).show();
    }

    private BroadcastReceiver mUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LoggerUtils.info(TAG, "VerifyMobileActivity mUpdateReceiver::::");
            String action = intent.getAction();
            if (action.equals(UserIntentService.IActivityActions.ACTION_SIGNUP)) {
                if (intent.getIntExtra(UserIntentService.RESULT_TYPE, UserIntentService.RESULT_SUCCESS) ==
                        UserIntentService.RESULT_SUCCESS) {
                    LoggerUtils.info(TAG, "VerifyMobileActivity mUpdateReceiver :::: RESULT_SUCCESS");
                    verifyContact();
                } else {
                    LoggerUtils.info(TAG, "VerifyMobileActivity mUpdateReceiver :::: RESULT_MESSAGE");
                    showMessage("Oops Something went Wrong");
                }
            }
        }
    };

    public void SignUpRequestToServer() {
        //Request for Login
        hideSignUpShowProgress();
        Bundle bundle = new Bundle();
        startSignUpService(UserIntentService.IActivityActions.ACTION_SIGNUP, bundle);
    }

    private void startSignUpService(String action, Bundle data) {
        hideSignUpShowProgress();
        Intent intent = new Intent(this, UserIntentService.class);
        intent.setAction(action);
        intent.putExtra(UserIntentService.USER_BUNDLE_DATA, data);
        startService(intent);
    }
}
