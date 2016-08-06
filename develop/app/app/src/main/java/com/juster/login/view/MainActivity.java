package com.juster.login.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.juster.R;
import com.juster.adapter.GridAdapter;
import com.juster.prefs.PreferenceManagerSingleton;

/**
 * Created by deepakj on 5/8/16.
 */
public class MainActivity extends Activity {

    Button btn_done;
    RecyclerView rv_top_destination;
    LinearLayoutManager linearLayoutManager;
    TextView tv_place_name;
    RelativeLayout rl_bottom;
    EditText et_destination;
    GridAdapter myRecyclerViewAdapter;
    String destination_name= "";

    ImageView tv_setting;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getIntent()!=null && getIntent().hasExtra("place")) {
            destination_name = getIntent().getStringExtra("place").toUpperCase();
            PreferenceManagerSingleton.getInstance(this).putTempSearchLocation(destination_name);
        }

        tv_setting = (ImageView) findViewById(R.id.tv_setting);
        rl_bottom = (RelativeLayout) findViewById(R.id.rl_bottom_main);
        btn_done = (Button) findViewById(R.id.btn_done_main);

        rv_top_destination= (RecyclerView) findViewById(R.id.lv_top_destination);
        et_destination = (EditText) findViewById(R.id.et_destination);
        tv_place_name = (TextView) findViewById(R.id.tv_selected_location);
        tv_place_name.setText(destination_name);
        btn_done = (Button) findViewById(R.id.btn_done_main);
        if(PreferenceManagerSingleton.getInstance(this).getTempisGuide())
            btn_done.setVisibility(View.GONE);
        else
            btn_done.setVisibility(View.VISIBLE);

        linearLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        myRecyclerViewAdapter = new GridAdapter(this, destination_name);
        rv_top_destination.setHasFixedSize(true);
        rv_top_destination.setItemAnimator(new DefaultItemAnimator());
        rv_top_destination.setLayoutManager(linearLayoutManager);
        rv_top_destination.setAdapter(myRecyclerViewAdapter);

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent navigateIntent = new Intent(MainActivity.this, HireGuideActivity.class);
                startActivity(navigateIntent);
            }
        });

        tv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    public void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
