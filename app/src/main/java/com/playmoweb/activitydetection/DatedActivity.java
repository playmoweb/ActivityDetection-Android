package com.playmoweb.activitydetection;

import com.google.android.gms.location.DetectedActivity;

import java.util.Date;

/**
 * DatedActivity
 *
 * @author Playmoweb
 */
public class DatedActivity {

    DetectedActivity detectedActivity;
    Date date;

    public DatedActivity(DetectedActivity detectedActivity) {
        this.detectedActivity = detectedActivity;
        date = new Date();
    }

    public String getType() {
        return DetectedActivity.zzho(detectedActivity.getType());
    }

    public int getConfidence() {
        return detectedActivity.getConfidence();
    }
}
