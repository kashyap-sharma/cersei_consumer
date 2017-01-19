package co.jlabs.cersei_retailer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.os.Handler;

import co.jlabs.cersei_retailer.activity.LoginNum;
import co.jlabs.cersei_retailer.splashIntro.IntroActivity;

public class Splash extends Activity {
    Activity context;
    public static int splash_time = 3000;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        context=this;


        if(StaticCatelog.getStringProperty(this,"api_key")==null) {
             intent =new Intent(this, IntroActivity.class);
        }else{
             intent =new Intent(this, SelectLocation.class);
        }
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                startActivity(intent);
                context.finish();
            }
        }, splash_time);

    }


}
