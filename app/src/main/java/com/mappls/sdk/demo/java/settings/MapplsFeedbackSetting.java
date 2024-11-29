package com.mappls.sdk.demo.java.settings;


import com.mappls.sdk.feedback.model.FeedbackOptions;

public class MapplsFeedbackSetting {
    private static final MapplsFeedbackSetting INSTANCE= new MapplsFeedbackSetting();

    public static MapplsFeedbackSetting getInstance() {
        return INSTANCE;
    }

    private boolean enableProgress = true;
    private boolean enableCompletedProgress = true;
    private  int theme = FeedbackOptions.THEME_DAY  ;
    private Double latitude = 25.321684;
    private Double longitude = 82.987289;

    public boolean isEnableProgress() {
        return enableProgress;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setEnableProgress(boolean enableProgress) {
        this.enableProgress = enableProgress;
    }

    public boolean isEnableCompletedProgress() {
        return enableCompletedProgress;
    }

    public void setEnableCompletedProgress(boolean enableCompletedProgress) {
        this.enableCompletedProgress = enableCompletedProgress;
    }

    public int getTheme() {
        return theme;
    }

    public void setTheme(int theme) {
        this.theme = theme;
    }
}
