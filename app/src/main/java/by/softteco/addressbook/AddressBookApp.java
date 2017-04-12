package by.softteco.addressbook;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

/**
 * Created by kirila on 12.4.17.
 */

public class AddressBookApp extends MultiDexApplication {

    private static Context mContext;

    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Context getAppContext() {
        return mContext;
    }

}
