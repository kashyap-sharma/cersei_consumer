package co.jlabs.cersei_retailer.Rounded;

/**
 * Created by Kashyap on 9/22/2015.
 */
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import co.jlabs.cersei_retailer.R;
import co.jlabs.cersei_retailer.custom_components.FontCache;



public class MyTextView extends TextView {


    public MyTextView(Context context) {
        super(context);
        if(isInEditMode())
        {
            return;
        }

        Typeface tf = FontCache.get("fonts/Lato-Semibold.ttf", context);
        if(tf != null) {
            this.setTypeface(tf);
        }
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface tf;
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.textfontstyle, 0, 0);
        String font_name = a.getString(R.styleable.textfontstyle_fontname);
        a.recycle();
        if(font_name==null)
        {
            font_name="lr";
        }
        if(font_name.equals("ll"))
        {
            tf= FontCache.get("fonts/Lato-Light.ttf", context);
        }
        else if(font_name.equals("lr"))
        {
            tf= FontCache.get("fonts/Lato-Regular.ttf", context);

        }
        else if(font_name.equals("lsm"))
        {
            tf= FontCache.get("fonts/Lato-Semibold.ttf", context);

        }
        else
        {
            tf= FontCache.get("fonts/Lato-Bold.ttf", context);
        }

        if(tf != null) {
            this.setTypeface(tf);
        }
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface tf = FontCache.get("fonts/Lato-Semibold.ttf", context);
        if(tf != null) {
            this.setTypeface(tf);
        }
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
    }

}