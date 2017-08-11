package razon.nctballbooksfree.utils;

/**
 * Created by razon30 on 01-08-17.
 */

import android.app.Application;

import io.realm.Realm;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
