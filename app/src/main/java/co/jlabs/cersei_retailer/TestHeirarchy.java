package co.jlabs.cersei_retailer;

/**
 * Created by JussConnect on 8/2/2015.
 */
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import co.jlabs.cersei_retailer.custom_components.ViewServer;


public class TestHeirarchy extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address);
        ViewServer.get(this).addWindow(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewServer.get(this).removeWindow(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        ViewServer.get(this).setFocusedWindow(this);
    }
}
