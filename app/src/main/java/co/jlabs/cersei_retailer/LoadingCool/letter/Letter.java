package co.jlabs.cersei_retailer.LoadingCool.letter;

import android.graphics.Canvas;

/**
 * Created by Kashyap on 02/19/16.
 */
public abstract class Letter {

    protected int mCurX;
    protected int mCurY;
    protected int mDuration = 2000;

    public Letter(int x, int y) {
        mCurX = x;
        mCurY = y;
    }

    public void startAnim() {

    }

    public void drawSelf(Canvas canvas) {

    }
}
