package developer.pardeep.workin;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.firebase.client.Firebase;

/**
 * Created by pardeep on 10-07-2016.
 */
public class MainApplication extends Application {
    MultiDexApplication multiDexApplication=new MultiDexApplication();

    public MainApplication(){

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
