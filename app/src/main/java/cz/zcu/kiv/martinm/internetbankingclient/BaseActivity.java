package cz.zcu.kiv.martinm.internetbankingclient;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

/**
 * Defines customizable activity.
 */
public abstract class BaseActivity extends AppCompatActivity implements Customizable {

    @Override
    public void setTitle(String title) {
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setTitle(title);
        }
    }

}
