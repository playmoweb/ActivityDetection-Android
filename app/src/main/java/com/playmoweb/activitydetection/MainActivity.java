package com.playmoweb.activitydetection;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.ActivityRecognitionResult;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.activitiesRecyclerView)
    RecyclerView activitiesRecyclerView;

    private ActivityAdapter activityAdapter;

    ActivityDetectionService mService;

    Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initRecyclerView();

        serviceIntent = ActivityDetectionService.getStartIntent(this);
        startService(serviceIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindService(serviceIntent, mConnection, Context.BIND_AUTO_CREATE);
    }

    private void initRecyclerView() {
        activityAdapter = new ActivityAdapter();
        activitiesRecyclerView.setAdapter(activityAdapter);
        activitiesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        activitiesRecyclerView.requestFocus();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(mConnection);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to the running Service, cast the IBinder and get instance
            ActivityDetectionService.LocalBinder binder = (ActivityDetectionService.LocalBinder) service;
            mService = binder.getService();
            mService.getObservable().subscribe(new Action1<DatedActivity>() {
                @Override
                public void call(DatedActivity datedActivity) {
                    activityAdapter.addItem(datedActivity);
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mService = null;
        }
    };
}
