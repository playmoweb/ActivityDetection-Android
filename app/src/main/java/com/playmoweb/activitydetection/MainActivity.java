package com.playmoweb.activitydetection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.ActivityRecognitionResult;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView {

    @Bind(R.id.activitiesRecyclerView)
    RecyclerView activitiesRecyclerView;

    private ActivityAdapter activityAdapter;

    private MainPresenter mPresenter;

    public MainActivity() {
        mPresenter = new MainPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initRecyclerView();
        mPresenter.attachView(this);
        mPresenter.startObserveActivities();
    }

    private void initRecyclerView() {
        activityAdapter = new ActivityAdapter();
        activitiesRecyclerView.setAdapter(activityAdapter);
        activitiesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        activitiesRecyclerView.requestFocus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        ButterKnife.unbind(this);
    }

    @Override
    public void showError(Throwable throwable) {
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDetectedActivity(DatedActivity datedActivity) {
        activityAdapter.addItem(datedActivity);
    }
}
