package co.jlabs.cersei_retailer.splashIntro;

import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;

import co.jlabs.cersei_retailer.Splash;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onStart() {
        super.onStart();

        TaskStackBuilder.create(this)
                //.addNextIntentWithParentStack(new Intent(this, Splash.class))
                .addNextIntent(new Intent(this, Splash.class))
                .startActivities();
    }
}