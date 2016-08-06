package com.juster.login.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.juster.R;
import com.juster.prefs.PreferenceManagerSingleton;

/**
 * Created by deepakj on 29/7/16.
 */
public class SignupActivity extends AppCompatActivity{

    ImageView imv_signup_back;
    Button btn_submit;
    EditText et_user_name;
    EditText et_licence;
    EditText et_contact_no;
    EditText et_password;
    TextView tv_guide;
    TextView tv_tourist;
    ProgressBar mPbSignUp;

    boolean isGuide = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_activity);
        initView();
    }

    private void initView() {

        et_user_name = (EditText) findViewById(R.id.et_user_name);
        et_contact_no = (EditText) findViewById(R.id.et_contact_no);
        et_licence = (EditText)  findViewById(R.id.et_licence);
        tv_tourist = (TextView) findViewById(R.id.tv_tourist);
        tv_guide = (TextView) findViewById(R.id.tv_guide);

        tv_guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleGuide(true);
            }
        });

        tv_tourist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleGuide(false);
            }
        });

        mPbSignUp = (ProgressBar) findViewById(R.id.pb_login);

        imv_signup_back = (ImageView) findViewById(R.id.imv_signup_back);
        imv_signup_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
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

    private void toggleGuide(boolean b) {
        if(b){
            isGuide = true;
            et_licence.setVisibility(View.VISIBLE);
            tv_guide.setTextColor(ContextCompat.getColor(this,R.color.blue_usher));
            tv_tourist.setTextColor(ContextCompat.getColor(this,R.color.dark_grey));
        }else {
            isGuide = false;
            et_licence.setVisibility(View.GONE);
            tv_tourist.setTextColor(ContextCompat.getColor(this,R.color.blue_usher));
            tv_guide.setTextColor(ContextCompat.getColor(this,R.color.dark_grey));
        }
    }

    private boolean ifLoginValid() {

        boolean isValid = true;
        String username = et_user_name.getText().toString().trim();
        String contact_no = et_contact_no.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String licencenumber = et_licence.getText().toString().trim();

        //validity check for username
        if (username.isEmpty()) {

            Toast.makeText(this, getString(R.string.login_err_us_username) , Toast.LENGTH_SHORT).show();
            isValid = false;

        } else if (!Patterns.PHONE.matcher(contact_no).matches()) {

            isValid = false;
            Toast.makeText(this, getString(R.string.forgot_err_exz_id_in), Toast
                    .LENGTH_SHORT).show();

        } else if (password.isEmpty()) {

            Toast.makeText(this, getString(R.string.login_err_ps) , Toast.LENGTH_SHORT).show();
            isValid = false;

        } else if(isGuide && licencenumber.isEmpty()){
            Toast.makeText(this, getString(R.string.login_err_licence_number) , Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        return isValid;
    }

    private void login() {
        hideSoftKeyboard(et_password);
        if (ifLoginValid()) {
            SignUpRequestToServer();
        }
    }

    public void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void SignUpRequestToServer() {
        final String username = et_user_name.getText().toString().trim();
        final String password = et_password.getText().toString().trim();
        final String licencenumber = et_licence.getText().toString().trim();
        final String contact_no = et_contact_no.getText().toString().trim();

        PreferenceManagerSingleton.getInstance(this).putTempContactNo(contact_no);
        PreferenceManagerSingleton.getInstance(this).putTempUserName(username);
        if(isGuide) {
            PreferenceManagerSingleton.getInstance(this).putTempLicenceNo(licencenumber);
        }
        PreferenceManagerSingleton.getInstance(this).putTempisGuide(isGuide);
        PreferenceManagerSingleton.getInstance(this).putTempPassword(password);

        Intent intent = new Intent(SignupActivity.this, VerifyMobileActivity.class);
        startActivity(intent);
    }
}