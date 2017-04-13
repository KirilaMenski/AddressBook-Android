package by.softteco.addressbook.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import by.softteco.addressbook.R;
import by.softteco.addressbook.utils.FragmentUtil;

/**
 * Created by kirila on 13.4.17.
 */

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private final int LAYOUT = R.layout.activity_main;

    BottomNavigationView mBottomNavigationView;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        FragmentUtil.replaceFragment(this, R.id.main_container, MapFragment.newInstance(), false);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.map:
                FragmentUtil.replaceFragment(this, R.id.main_container, MapFragment.newInstance(), false);
                break;
            case R.id.list:
                FragmentUtil.replaceFragment(this, R.id.main_container, AddressListFragment.newInstance(), false);
                break;
            default:
                break;
        }
        return true;
    }
}
