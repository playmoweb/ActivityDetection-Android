package com.playmoweb.activitydetection;

/**
 * MainView
 *
 * @author Playmoweb
 */
public interface MainView {
    void showError(Throwable throwable);
    void showDetectedActivity(DatedActivity datedActivity);
}
