package com.juster.login.view;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.juster.R;
import com.juster.custom.FlowLayout;
import com.juster.prefs.PreferenceManagerSingleton;

import java.util.ArrayList;

/**
 * Created by deepakj on 6/8/16.
 */
public class HireGuideActivity extends AppCompatActivity {

    private FlowLayout chipLayout;
    private Button btn_find;
    TextView current_selected_location;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_guide);
        chipLayout = (FlowLayout) findViewById(R.id.chipLayout);
        current_selected_location = (TextView) findViewById(R.id.current_selected_location);
        current_selected_location.setText(PreferenceManagerSingleton.getInstance(this)
                .getTempSearchLocation() != null ? PreferenceManagerSingleton.getInstance(this)
                .getTempSearchLocation() : "No Place Selected");

        ArrayList<String> addList = new ArrayList<>();
        addList.add("English");
        addList.add("Hindi");

        showRoomList(addList);

        btn_find = (Button) findViewById(R.id.btn_find);
        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent navigateIntent = new Intent(HireGuideActivity.this, GuideListActivity.class);
                navigateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(navigateIntent);
            }
        });
    }

    private void showRoomList(ArrayList<String> langList) {
        //TODO Use Dimens instead of Hardcoded Values (Optimize)
        FlowLayout.LayoutParams params = new FlowLayout.LayoutParams(FlowLayout.LayoutParams
                .WRAP_CONTENT, FlowLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(8, 8, 8, 8);
        chipLayout.removeAllViews();
        for (int i = 0; i < langList.size(); i++) {
            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(30);
            gd.setStroke(2 , ContextCompat.getColor(this, R.color.blue_usher));
            gd.setColor(ContextCompat.getColor(this, R.color.blue_usher));
            final TextView t = new TextView(this);
            t.setLayoutParams(params);
            t.setPadding(40, 20, 40, 20);
            t.setText(langList.get(i));
            t.setTextSize(TypedValue.COMPLEX_UNIT_DIP , 14);
            t.setTextColor(ContextCompat.getColor(this, R.color.white));
            t.setBackground(gd);
            t.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            chipLayout.addView(t);
        }
    }
}
