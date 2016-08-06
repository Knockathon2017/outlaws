package com.juster.login.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
public class GuideListActivity extends AppCompatActivity {

    private static final String TAG = GuideListActivity.class.getName();
    Button btn_done;
    RecyclerView rv_top_destination;
    LinearLayoutManager linearLayoutManager;
    RelativeLayout rl_bottom;
    GuideAdapter myRecyclerViewAdapter;
    String destination_name;
    ArrayList<GuidesDetail> guidesDetails;
    LocalSyncBroadcast mSyncBroadcaster;
    ProgressBar pb_guides;

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
                    Toast.makeText(GuideListActivity.this, "Oops Something went Wrong", Toast
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
            GuideQuery activityQuery = new GuideQuery(GuideListActivity.this);
            //activityList.addAll(activityQuery.getAllClaims());
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
    ImageView tv_search_scross;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidelist);
        mSyncBroadcaster = new LocalSyncBroadcast(this);
        pb_guides = (ProgressBar) findViewById(R.id.pb_guides);
        rv_top_destination= (RecyclerView) findViewById(R.id.lv_guide_list);
        tv_search_scross = (ImageView) findViewById(R.id.tv_search_scross);
        tv_search_scross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        linearLayoutManager = new LinearLayoutManager(GuideListActivity.this,LinearLayoutManager
                .VERTICAL, false);
        myRecyclerViewAdapter = new GuideAdapter(this, new ArrayList<GuidesDetail>());
        rv_top_destination.setItemAnimator(new DefaultItemAnimator());
        rv_top_destination.setLayoutManager(linearLayoutManager);
        rv_top_destination.setAdapter(myRecyclerViewAdapter);

        guidesListRequestToServer();
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
