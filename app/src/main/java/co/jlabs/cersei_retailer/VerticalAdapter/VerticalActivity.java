package co.jlabs.cersei_retailer.VerticalAdapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gigamole.infinitecycleviewpager.VerticalInfiniteCycleViewPager;

import co.jlabs.cersei_retailer.R;


/**
 * Created by GIGAMOLE on 8/18/16.
 */
public class VerticalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_vertical);

        final VerticalInfiniteCycleViewPager verticalInfiniteCycleViewPager =
                (VerticalInfiniteCycleViewPager) findViewById(R.id.vicvp);
        verticalInfiniteCycleViewPager.setAdapter(new VerticalPagerAdapter(this, null));
    }

}
