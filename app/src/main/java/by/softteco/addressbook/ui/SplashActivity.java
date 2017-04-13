package by.softteco.addressbook.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import by.softteco.addressbook.R;

/**
 * Created by kirila on 13.4.17.
 */

public class SplashActivity extends AppCompatActivity {

    private final int LAYOUT = R.layout.activity_splash;
    private final int DELAY = 3000;

    private Handler mHandler;
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            Intent intent = MainActivity.newIntent(SplashActivity.this);
            startActivity(intent);
            SplashActivity.this.finish();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        mHandler = new Handler();
        mHandler.postDelayed(mRunnable, DELAY);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRunnable);
    }
}
