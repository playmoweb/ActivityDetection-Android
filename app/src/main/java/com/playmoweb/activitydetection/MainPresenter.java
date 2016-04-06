package com.playmoweb.activitydetection;

import android.content.Context;

import com.google.android.gms.location.ActivityRecognitionResult;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * MainPresenter
 *
 * @author Playmoweb
 */
public class MainPresenter {

    MainView mView;
    private final CompositeSubscription mSubscriptions;
    private final ReactiveLocationProvider mLocationProvider;

    public MainPresenter(Context context) {
        mSubscriptions = new CompositeSubscription();
        mLocationProvider = new ReactiveLocationProvider(context);
    }

    public void startObserveActivities() {
        Subscription subscription = mLocationProvider.getDetectedActivity(0)
                .map(new Func1<ActivityRecognitionResult, DatedActivity>() {
                    @Override
                    public DatedActivity call(ActivityRecognitionResult activityRecognitionResult) {
                        return new DatedActivity(activityRecognitionResult.getMostProbableActivity());
                    }
                })
                .subscribe(new Subscriber<DatedActivity>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e);
                    }

                    @Override
                    public void onNext(DatedActivity datedActivity) {
                        mView.showDetectedActivity(datedActivity);
                    }
                });

        mSubscriptions.add(subscription);
    }

    public void attachView(MainView mvpView) {
        this.mView = mvpView;
    }

    public void detachView() {
        mView = null;
        mSubscriptions.unsubscribe();
    }
}
