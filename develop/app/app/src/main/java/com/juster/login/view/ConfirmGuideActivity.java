package com.juster.login.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.juster.R;
import com.juster.data.api.database.user.model.GuidesDetail;

/**
 * Created by deepakj on 6/8/16.
 */
public class ConfirmGuideActivity extends AppCompatActivity {

    TextView tv_name;
    RatingBar ratingBar;
    GuidesDetail guidesDetail;
    ImageView imv_verify_back;
    ProgressBar pb_login;
    ImageView sendFeedBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_confirm_guide);
        imv_verify_back = (ImageView) findViewById(R.id.imv_verify_back);
        pb_login = (ProgressBar) findViewById(R.id.pb_login);

        imv_verify_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sendFeedBack = (ImageView) findViewById(R.id.img_feedback);
        sendFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(ConfirmGuideActivity.this);
                View promptsView = li.inflate(R.layout.layout_prompt, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        ConfirmGuideActivity.this);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);
                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        if(userInput.getText().toString().isEmpty())
                                        {
                                            hideSoftKeyboard(tv_name);
                                            Toast.makeText(ConfirmGuideActivity.this, "Enter some" +
                                                    " Feedback", Toast.LENGTH_SHORT).show();
                                        } else  {
                                            ShowProgress();
                                            final Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    hideProgress();
                                                    hideSoftKeyboard(tv_name);
                                                    Toast.makeText(ConfirmGuideActivity.this,
                                                            "FeedBack Sent", Toast.LENGTH_SHORT)
                                                            .show();
                                                }
                                            }, 1500);
                                        }
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        tv_name = (TextView) findViewById(R.id.tvname);
        ratingBar = (RatingBar) findViewById(R.id.ratingbar);

        if(getIntent()!=null && getIntent().hasExtra("guide")) {
            guidesDetail = (GuidesDetail) getIntent().getSerializableExtra("guide");
        }

        if(guidesDetail != null) {
            tv_name.setText(guidesDetail.getName());
            ratingBar.setRating(guidesDetail.getRating());
        }
    }

    private void ShowProgress() {
        pb_login.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        pb_login.setVisibility(View.GONE);
    }

    public void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
