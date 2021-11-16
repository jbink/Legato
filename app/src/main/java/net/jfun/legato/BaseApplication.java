package net.jfun.legato;

import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {


    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();


//        AppCenter.start(this, "a52c4975-0517-4731-b0f8-85650461c3c5",
//                Analytics.class, Crashes.class);


    }

}
