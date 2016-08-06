package com.juster.login.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.juster.R;
import com.juster.data.LocalSyncBroadcast;
import com.juster.data.api.database.user.service.GuideIntentService;
import com.juster.data.api.database.user.service.UserIntentService;
import com.juster.logger.LoggerUtils;
import com.juster.prefs.PreferenceManagerSingleton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by deepakj on 6/8/16.
 */
public class SettingActivity extends AppCompatActivity {

    private static final String TAG = SettingActivity.class.getName();
    TextView logout;
    CheckBox is_checkbox;
    LocalSyncBroadcast mSyncBroadcaster;
    ProgressBar pb_login;
    ImageView btn_settings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        pb_login = (ProgressBar) findViewById(R.id.pb_login);
        mSyncBroadcaster = new LocalSyncBroadcast(this);
        btn_settings = (ImageView) findViewById(R.id.imv_verify_back);

        is_checkbox = (CheckBox) findViewById(R.id.is_available);

        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(!PreferenceManagerSingleton.getInstance(this).getTempisGuide()) {
            is_checkbox.setVisibility(View.GONE);
        } else {
            if(PreferenceManagerSingleton.getInstance(this).getTempguideisAvailable()) {
                is_checkbox.setChecked(true);
            } else {
                is_checkbox.setChecked(false);
            }
            is_checkbox.setVisibility(View.VISIBLE);
        }

        logout = (TextView) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceManagerSingleton.deleteSharedPreference();
                Intent navigateIntent = new Intent(SettingActivity.this, LoginActivity.class);
                navigateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(navigateIntent);
                finish();
            }
        });

        is_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                loginRequestToServer();
            }
        });
    }

    private ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    protected void onStart() {
        super.onStart();
        mSyncBroadcaster.registerReceiver(mUpdateReceiver, GuideIntentService.getServiceIntentFilters());
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSyncBroadcaster.unregisterReceiver(mUpdateReceiver);
    }

    private void hideLoginShowProgress() {
        pb_login.setVisibility(View.VISIBLE);
    }

    private void hideProgressShowLogin() {
        pb_login.setVisibility(View.GONE);
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

            if (action.equals(GuideIntentService.IActivityActions.ACTION_SEND_STATUS)) {
                if (intent.getIntExtra(GuideIntentService.RESULT_TYPE, GuideIntentService.RESULT_SUCCESS) ==
                        GuideIntentService.RESULT_SUCCESS) {
                    LoggerUtils.info(TAG, "LoginActivity mUpdateReceiver :::: RESULT_SUCCESS");
                    showMessage("Successfully status sent");

                    if(is_checkbox.isChecked()) {
                        PreferenceManagerSingleton.getInstance(SettingActivity.this)
                                .putTempguideisAvailable(true);
                    } else {
                        PreferenceManagerSingleton.getInstance(SettingActivity.this)
                                .putTempguideisAvailable(false);
                    }

                } else {
                    LoggerUtils.info(TAG, "LoginActivity mUpdateReceiver :::: RESULT_MESSAGE");
                    showMessage("Oops Something went Wrong");
                    if(is_checkbox.isChecked()) {
                        is_checkbox.setChecked(false);
                        PreferenceManagerSingleton.getInstance(SettingActivity.this)
                                .putTempguideisAvailable(false);
                    } else {
                        is_checkbox.setChecked(true);
                        PreferenceManagerSingleton.getInstance(SettingActivity.this)
                                .putTempguideisAvailable(true);
                    }
                }
            }
        }
    };

    public void loginRequestToServer() {

        if(is_checkbox.isChecked()) {
            PreferenceManagerSingleton.getInstance(this).putTempguideisAvailable(true);
        } else {
            PreferenceManagerSingleton.getInstance(this).putTempguideisAvailable(false);
        }

        //Request for Login
        hideLoginShowProgress();
        Bundle bundle = new Bundle();
        startLoginService(GuideIntentService.IActivityActions.ACTION_SEND_STATUS, bundle);
    }

    private void startLoginService(String action, Bundle data) {
        Intent intent = new Intent(this, GuideIntentService.class);
        intent.setAction(action);
        intent.putExtra(GuideIntentService.USER_BUNDLE_DATA, data);
        startService(intent);
    }

}
