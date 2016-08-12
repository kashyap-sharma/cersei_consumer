package co.jlabs.cersei_retailer;

import android.widget.SlidingDrawer;

/**
 * Created by JLabs on 08/09/16.
 */
public abstract class OnScrollObserver implements SlidingDrawer.OnDrawerScrollListener {






    public void on(SlidingDrawer view, int scrollState) {
    }

    int last = 0;
    boolean control = true;


    public void onScroll(SlidingDrawer view, int current, int visibles, int total) {
        if (current < last && !control) {
            onScrollStarted();
            control = true;
        } else if (current > last && control) {
            onScrollEnded();
            control = false;
        }

        last = current;
    }

}