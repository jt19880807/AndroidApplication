package com.JTAppStore.common;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Created by Devin.Jiang on 2016-06-06.
 */
public class AppApplication extends Application {
    private static AppApplication appContext;
    @Override
    public void onCreate() {
        super.onCreate();
        appContext=this;
    }

    public static AppApplication getAppContext(){
        if (appContext==null){
            appContext=new AppApplication();
        }
        return appContext;
    }

    public String getVersion(){

        try {
            PackageManager manager=this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(),0);
            String version=info.versionName;
            Log.d("Tag",version);
            return "V  " + version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "未知";
        }

    }
}
