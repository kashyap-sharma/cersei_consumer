package co.jlabs.cersei_retailer.LoadingCool.letter;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;

import co.jlabs.cersei_retailer.LoadingCool.Config;


/**
 * Created by Kashyap on 02/19/16.
 */
public class ILetter extends Letter {
    public final static int STROKE_WIDTH = 20;
    public final static int WIDTH = STROKE_WIDTH;
    public final static int LENGTH = 120;
    private int mCurValue;
    private boolean isStart = false;
    private Paint mPaint;
    // 竖线弹出的时间
    private int mDuration1 = mDuration/3*2;
    // 圆球弹出的时间
    private int mDuration2 = mDuration/3;
    // 竖线变化的长度
    private float mLength1;
    // 圆球弹出的变化高度
    private float mLength2;
    // 圆球的半径
    private int mRadius;

    public ILetter(int x, int y) {
        super(x, y);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Config.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(20);
        mCurY += LENGTH / 2;
    }

    @Override
    public void startAnim() {
        ValueAnimator animator = ValueAnimator.ofInt(0, mDuration1 + mDuration2);
        animator.setDuration(mDuration1 + mDuration2);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (!isStart) {
                    return;
                }
                mCurValue = (int) animation.getAnimatedValue();
                if (mCurValue <= mDuration1) {
                    mLength1 = LENGTH * mCurValue / mDuration1;
                } else {
                    mCurValue -= mDuration1;
                    mRadius = 12 * mCurValue / 500;
                    mLength2 = 30 * mCurValue / 500;
                }

            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                isStart = true;
                mRadius = 0;
            }
        });
        animator.start();
    }

    @Override
    public void drawSelf(Canvas canvas) {
        if (isStart) {
            // 绘制竖线
            canvas.drawLine(mCurX, mCurY, mCurX, mCurY - mLength1, mPaint);
            // 绘制圆点
            canvas.drawCircle(mCurX, mCurY - mLength1 + 30 - mLength2 - 20, mRadius, mPaint);
        }
    }
}
