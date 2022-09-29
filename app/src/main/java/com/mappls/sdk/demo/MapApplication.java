package com.mappls.sdk.demo;

import android.app.Application;

import com.mappls.sdk.maps.Mappls;
import com.mappls.sdk.services.account.MapplsAccountManager;

/**
 * Created by CEINFO on 29-06-2018.
 */

public class MapApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MapplsAccountManager.getInstance().setRestAPIKey(getRestAPIKey());
        MapplsAccountManager.getInstance().setMapSDKKey(getMapSDKKey());
        MapplsAccountManager.getInstance().setAtlasClientId(getAtlasClientId());
        MapplsAccountManager.getInstance().setAtlasClientSecret(getAtlasClientSecret());
        Mappls.getInstance(this);
    }

    public String getAtlasClientId() {
        return "";
    }

    public String getAtlasClientSecret() {
        return "";
    }

    public String getMapSDKKey() {
        return "";
    }

    public String getRestAPIKey() {
        return "";
    }
}
