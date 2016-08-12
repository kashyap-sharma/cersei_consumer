package co.jlabs.cersei_retailer.custom_components;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.jlabs.cersei_retailer.R;

/**
 * Created by Pradeep on 1/15/2016.
 */
public class AddOrRemoveCart extends LinearLayout implements View.OnClickListener {

    private View AddtoCart,RemoveFromCart;
    private TextView num_of_items;
    private  ItemsClickListener listner;
    private int position;
    ObjectAnimator AddtoCartColorAnimator,RemoveFromCartColorAnimator;
    Animation BUanimAccelerate,UBanimAccelerate;
    Context context;
    Boolean type_white;
    int color_but=0xFF777777;

    public AddOrRemoveCart(Context context) {
        super(context);
        init(context);
    }

    public AddOrRemoveCart(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AddOrRemoveCart, 0, 0);
        type_white = a.getBoolean(R.styleable.AddOrRemoveCart_TypeWhite, false);
        init(context);
    }

    public AddOrRemoveCart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AddOrRemoveCart, 0, 0);
        type_white = a.getBoolean(R.styleable.AddOrRemoveCart_TypeWhite, false);
        init(context);
    }

    private void init(Context context) {
        this.context=context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.add_or_remove_cart, this, true);
        BUanimAccelerate = AnimationUtils.loadAnimation(context, R.anim.bottom_from_up);
        UBanimAccelerate = AnimationUtils.loadAnimation(context, R.anim.up_from_bottom);
        RemoveFromCart = ((ViewGroup)getChildAt(0)).getChildAt(0);
        num_of_items = (TextView) ((ViewGroup)getChildAt(0)).getChildAt(1);
        AddtoCart = ((ViewGroup)getChildAt(0)).getChildAt(2);

            ((TextView) AddtoCart).setTextColor(Color.parseColor("#ffffff"));
            ((TextView)RemoveFromCart).setTextColor(Color.parseColor("#ffffff"));
            num_of_items.setTextColor(Color.parseColor("#000000"));
            color_but=0xffffffff;

        AddtoCartColorAnimator = ObjectAnimator.ofObject(AddtoCart,
                "textColor",
                new ArgbEvaluator(),
                0xFF004d00,
                color_but);
        AddtoCartColorAnimator.setDuration(400);
        RemoveFromCartColorAnimator = ObjectAnimator.ofObject(RemoveFromCart,
                "textColor",
                new ArgbEvaluator(),
                0xFF990000,
                color_but);
        RemoveFromCartColorAnimator.setDuration(400);
    }

    public void addOnItemClickListner(ItemsClickListener listner,int position,int quantity)
    {
        this.listner=listner;
        this.position=position;
        RemoveFromCart.setOnClickListener(this);
        AddtoCart.setOnClickListener(this);
        num_of_items.setText(""+quantity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.removefromcart :
                int quantity=listner.removeItemClicked(v,position);
                if(quantity>=0)
                 num_of_items.setText(""+quantity);
                 num_of_items.startAnimation(BUanimAccelerate);
                 RemoveFromCartColorAnimator.start();
                break;
            case R.id.addtocart :
                num_of_items.setText("" + listner.addItemClicked(position));
                num_of_items.startAnimation(UBanimAccelerate);
                AddtoCartColorAnimator.start();
                break;
        }
    }


    public interface ItemsClickListener
    {
        int addItemClicked(int position);
        int removeItemClicked(View v,int position);
    }

}