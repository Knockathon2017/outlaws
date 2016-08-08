package com.juster.login.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.juster.R;
import com.juster.adapter.RecyclerViewAdapter;
import com.juster.prefs.PreferenceManagerSingleton;

/**
 * Created by deepakj on 6/8/16.
 */
public class SelectLocationActivity extends AppCompatActivity {

    Button btn_done;
    RecyclerView rv_top_destination;
    LinearLayoutManager linearLayoutManager;
    TextView tv_skip;
    ImageView tv_back;
    RelativeLayout rl_bottom;
    ImageView ic_search;
    EditText et_destination;
    ProgressBar progressBar;
    RecyclerViewAdapter myRecyclerViewAdapter;
    TextView tv_select_location;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);

        rl_bottom = (RelativeLayout) findViewById(R.id.rl_bottom);
        progressBar = (ProgressBar) findViewById(R.id.pb_verify);
        tv_select_location = (TextView) findViewById(R.id.tv_select_location);

        if (rl_bottom != null) {
            rl_bottom.setVisibility(View.GONE);
        }

        if(PreferenceManagerSingleton.getInstance(this).getTempisGuide())
            tv_select_location.setText("Search for Places");
        rv_top_destination= (RecyclerView) findViewById(R.id.lv_top_destination);
        et_destination = (EditText) findViewById(R.id.et_destination);
        btn_done = (Button) findViewById(R.id.btn_done);
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Handler handler = new Handler();
                hideLoginShowProgress();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideProgressShowLogin();
                        Intent navigateIntent = new Intent(SelectLocationActivity.this, MainActivity.class);
                        navigateIntent.putExtra("place", et_destination.getText().toString());
                        navigateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(navigateIntent);
                    }
                }, 1500);

            }
        });
        tv_skip = (TextView) findViewById(R.id.tv_skip);
        tv_back = (ImageView) findViewById(R.id.imv_verify_back);
        ic_search = (ImageView) findViewById(R.id.ic_search);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        myRecyclerViewAdapter = new RecyclerViewAdapter(this);
        rv_top_destination.setAdapter(myRecyclerViewAdapter);
        rv_top_destination.setLayoutManager(linearLayoutManager);
        rv_top_destination.setItemAnimator(new DefaultItemAnimator());

        ic_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Handler handler = new Handler();
                hideSoftKeyboard(ic_search);
                hideLoginShowProgress();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideProgressShowLogin();
                        rl_bottom.setVisibility(View.VISIBLE);
                        et_destination.getText();
                        myRecyclerViewAdapter.updateListData(et_destination.getText().toString().trim());
                    }
                }, 1000);
            }
        });

        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_skip.setEnabled(false);
        tv_skip.setVisibility(View.INVISIBLE);

        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent navigateIntent = new Intent(SelectLocationActivity.this, MainActivity.class);
                navigateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(navigateIntent);
            }
        });

        Handler handler = new Handler();
        hideLoginShowProgress();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideProgressShowLogin();
                rl_bottom.setVisibility(View.VISIBLE);
                et_destination.setText("Delhi");
                myRecyclerViewAdapter.updateListData("Delhi");
            }
        }, 1000);
    }

    private void hideLoginShowProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressShowLogin() {
        progressBar.setVisibility(View.GONE);
    }

    public void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}

