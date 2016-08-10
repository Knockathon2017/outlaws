package com.juster.login.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.juster.R;
import com.juster.adapter.GuideAdapter;
import com.juster.data.LocalSyncBroadcast;
import com.juster.data.api.database.user.controller.GuideQuery;
import com.juster.data.api.database.user.model.GuidesDetail;
import com.juster.data.api.database.user.service.GuideIntentService;
import com.juster.logger.LoggerUtils;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by deepakj on 6/8/16.
 */
public class PlaceDetailActivity extends AppCompatActivity {

    private static final String TAG = PlaceDetailActivity.class.getName();
    ImageView imageView;
    TextView tv_image_detail;
    RecyclerView guideList;
    Button guide_me;
    ImageView tv_back;
    String place_name;

    RecyclerView rv_top_destination;
    LinearLayoutManager linearLayoutManager;
    RelativeLayout rl_bottom;
    GuideAdapter myRecyclerViewAdapter;
    String destination_name;
    ArrayList<GuidesDetail> guidesDetails;
    LocalSyncBroadcast mSyncBroadcaster;
    ProgressBar pb_guides;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);
        mSyncBroadcaster = new LocalSyncBroadcast(this);

        if(getIntent()!=null && getIntent().hasExtra("place_name")) {
            place_name = getIntent().getStringExtra("place_name");
        }
        tv_back = (ImageView) findViewById(R.id.imv_verify_back);
        imageView = (ImageView) findViewById(R.id.imv_place_detail);
        tv_image_detail = (TextView) findViewById(R.id.tv_detail);
        guideList = (RecyclerView) findViewById(R.id.lv_guide_list);
        guide_me = (Button) findViewById(R.id.btn_done_main);

        guide_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent navigateIntent = new Intent(PlaceDetailActivity.this, HireGuideActivity.class);
                navigateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(navigateIntent);
            }
        });

        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(place_name.equals("Taj Mahal")) {
            tv_image_detail.setText(getString(R.string.taj_desc));
            imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_taj_mahal));
        } else if (place_name.equals("Agra temple")) {
            tv_image_detail.setText(getString(R.string.agra_temple));
            imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.agra_temple));
        } else if (place_name.equals("Buland Darwaza")) {
            tv_image_detail.setText(getString(R.string.buland_darwaza));
            imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.buland_darwaza_detail));
        } else if (place_name.equals("akbar_tomb")) {
            tv_image_detail.setText(getString(R.string.taj_desc));
            imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.akbar_tomb));
        } else if (place_name.equals("Moti Mosq")) {
            tv_image_detail.setText(getString(R.string.moti_mosq));
            imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.moti_mosk));
        } else if (place_name.equals("India Gate")) {
            tv_image_detail.setText(getString(R.string.taj_desc));
            imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_india_gate));
        } else if (place_name.equals("Akshar Dham")) {
            tv_image_detail.setText(getString(R.string.akshardham));
            imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.akshar_dam_detail));
        } else if (place_name.equals("Lotus Temple")) {
            tv_image_detail.setText(getString(R.string.lotus_temple));
            imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.lotus_temp_detail));
        } else if (place_name.equals("Humayun Tomb")) {
            tv_image_detail.setText(getString(R.string.humayun_tomb));
            imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.humayun_tomp_detail));
        }

        pb_guides = (ProgressBar) findViewById(R.id.pb_verify);
        linearLayoutManager = new LinearLayoutManager(PlaceDetailActivity.this, LinearLayoutManager
                .VERTICAL, false);
        myRecyclerViewAdapter = new GuideAdapter(this, new ArrayList<GuidesDetail>());
        guideList.setItemAnimator(new DefaultItemAnimator());
        guideList.setLayoutManager(linearLayoutManager);
        guideList.setAdapter(myRecyclerViewAdapter);

        guidesListRequestToServer();
    }

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

    private BroadcastReceiver mUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LoggerUtils.info(TAG, "LoginActivity mUpdateReceiver::::");
            String action = intent.getAction();
            hideProgress();
            if (action.equals(GuideIntentService.IActivityActions.ACTION_LIST)) {
                if (intent.getIntExtra(GuideIntentService.RESULT_TYPE, GuideIntentService.RESULT_SUCCESS) ==
                        GuideIntentService.RESULT_SUCCESS) {
                    LoggerUtils.info(TAG, "LoginActivity mUpdateReceiver :::: RESULT_SUCCESS");
                    loadClaimsListData();
                } else {
                    LoggerUtils.info(TAG, "LoginActivity mUpdateReceiver :::: RESULT_MESSAGE");
                    Toast.makeText(PlaceDetailActivity.this, "Oops Something went Wrong", Toast
                            .LENGTH_SHORT).show();
                }
            }
        }
    };

    private void loadClaimsListData() {
        executorService.execute(new LoadClaimsListDataRunnable());
    }

    private ExecutorService executorService = Executors.newCachedThreadPool();
    ArrayList<GuidesDetail> activityList;

    class LoadClaimsListDataRunnable implements Runnable {
        @Override
        public void run() {
            Handler handlerList = new Handler(Looper.getMainLooper());
            // Moves the current Thread into the background
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
            LoggerUtils.info(TAG, "LoadClaimsListDataRunnable");
            activityList = new ArrayList<>();
            GuideQuery activityQuery = new GuideQuery(PlaceDetailActivity.this);
            activityList.clear();
            activityList.addAll(activityQuery.getUserData());

            handlerList.post(new Runnable() {
                @Override
                public void run() {
                    updateList(activityList);
                }
            });
        }
    }

    private void updateList(ArrayList<GuidesDetail> claimsModelArrayList) {
        if (claimsModelArrayList != null && claimsModelArrayList.size() > 0) {
            myRecyclerViewAdapter.updateListData("" ,claimsModelArrayList);
        }
    }

    public void guidesListRequestToServer() {
        ShowProgress();
        Bundle bundle = new Bundle();
        startLoginService(GuideIntentService.IActivityActions.ACTION_LIST, bundle);
    }

    private void startLoginService(String action, Bundle data) {
        Intent intent = new Intent(this, GuideIntentService.class);
        intent.setAction(action);
        intent.putExtra(GuideIntentService.USER_BUNDLE_DATA, data);
        startService(intent);
    }


    private void ShowProgress() {
        pb_guides.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        pb_guides.setVisibility(View.GONE);
    }
}
