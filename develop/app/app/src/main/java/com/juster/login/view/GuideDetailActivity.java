package com.juster.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.juster.R;
import com.juster.data.api.database.user.model.GuidesDetail;

/**
 * Created by deepakj on 6/8/16.
 */
public class GuideDetailActivity extends AppCompatActivity {

    GuidesDetail guidesDetail;
    TextView name;
    TextView review;
    TextView contact;
    TextView adress;
    RatingBar ratingbar;
    ImageView back;
    Button btn_done_main;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_detail);
        if(getIntent()!=null && getIntent().hasExtra("guide")) {
            guidesDetail = (GuidesDetail) getIntent().getSerializableExtra("guide");
        }

        name = (TextView) findViewById(R.id.tvname);
        review = (TextView) findViewById(R.id.feedback);
        contact = (TextView) findViewById(R.id.contact);
        adress = (TextView) findViewById(R.id.address);
        ratingbar = (RatingBar) findViewById(R.id.ratingbar);
        back = (ImageView) findViewById(R.id.imv_verify_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_done_main = (Button) findViewById(R.id.btn_done_main);
        btn_done_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuideDetailActivity.this, ConfirmGuideActivity.class);
                if(guidesDetail!=null)
                    intent.putExtra("guide", guidesDetail);
                startActivity(intent);
            }
        });

        if(guidesDetail!=null) {
            if(!guidesDetail.getName().isEmpty())
                name.setText(guidesDetail.getName().toUpperCase());
            if(guidesDetail.getMobile() != 0)
                contact.setText(String.valueOf(guidesDetail.getMobile()));
            if(!guidesDetail.getAddress().isEmpty())
                adress.setText(String.valueOf(guidesDetail.getAddress()));
            if(guidesDetail.getRating() == 0)
                ratingbar.setRating(guidesDetail.getRating());
            if(guidesDetail.getRatingCount() >= 0)
                review.setText(guidesDetail.getRatingCount()+ "  Reviews");
        }
    }
}
