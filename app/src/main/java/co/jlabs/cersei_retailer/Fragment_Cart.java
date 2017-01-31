package co.jlabs.cersei_retailer;

/**
 * Created by Pradeep on 12/25/2015.
 */
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import co.jlabs.cersei_retailer.activity.*;
import co.jlabs.cersei_retailer.activity.AddressFiller;
import co.jlabs.cersei_retailer.custom_components.ButtonModarno;
import co.jlabs.cersei_retailer.custom_components.Class_Cart;
import co.jlabs.cersei_retailer.custom_components.Sqlite_cart;

public class Fragment_Cart extends Fragment implements FragmentEventHandler{
    int fragVal;
    FragmentsEventInitialiser eventInitialiser=null;
    ListView lv;
    Sqlite_cart cart;
    Total_item_in_cart_text_handler handler;
    int total_item=0;
    double total_pice=0.0;
    View checkout_lay,checkout;
    View layoutView;
    buttonClick click;
    ButtonModarno foo;

    static Fragment_Cart init(int val) {
        Fragment_Cart truitonFrag = new Fragment_Cart();
        // Supply val input as an argument.
        Bundle args = new Bundle();
        args.putInt("val", val);
        truitonFrag.setArguments(args);
        return truitonFrag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragVal = getArguments() != null ? getArguments().getInt("val") : 3;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.cart_page, container,false);
        lv = (ListView) layoutView.findViewById(R.id.list_view);
        cart=new Sqlite_cart(getContext());
        checkout_lay=layoutView.findViewById(R.id.lay_checkout);
        checkout=layoutView.findViewById(R.id.checkout);
        ArrayList<Class_Cart> items = cart.getAllCart();
        ArrayList<Class_Cart> itema = cart.getDistinctRetailer();
        for (int i=0;i<items.size();i++)
        {

            total_item=total_item+items.get(i).quantity;
            total_pice=total_pice+items.get(i).price*items.get(i).quantity;
        }
        ((TextView)layoutView.findViewById(R.id.num_of_items_in_cart)).setText(""+total_item);
        ((TextView)layoutView.findViewById(R.id.money_foot)).setText(""+total_pice);

        handler = new Total_item_in_cart_text_handler() {
            @Override
            public void handleText_cart(int total,double price) {
                total_item=total_item+total;
                total_pice=total_pice+price;
                ((TextView)layoutView.findViewById(R.id.num_of_items_in_cart)).setText("" + (total_item));
                ((TextView)layoutView.findViewById(R.id.money_foot)).setText(""+total_pice);
                handleCheckoutVisibility();
            }
        };
        View no_item_cart_view = layoutView.findViewById(R.id.emptycartview);
         foo=(ButtonModarno)layoutView.findViewById(R.id.back_to_offers);
        foo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.buttonClicked(v);
            }
        });
        lv.setEmptyView(no_item_cart_view);
        Adapter_Cart adapter_cart = new Adapter_Cart(getContext(),items,itema,handler,eventInitialiser);
        lv.setAdapter(adapter_cart);
        handleCheckoutVisibility();
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(total_pice>=100){
                    Toast.makeText(getContext(),"Checkout",Toast.LENGTH_SHORT).show();
                    //check out page
                    if(StaticCatelog.getStringProperty(getContext(),"api_key")==null) {
                        Intent intent =new Intent(getContext(), LoginNum.class);
                        intent.putExtra("from","cart");
                        startActivity(intent);
                    }
                    else
                    {
                        Intent intent =new Intent(getContext(), ProcessOrder.class);
                        startActivity(intent);
                    }
                }
                else{
                    final Dialog dialog = new Dialog(getContext());
                    // Include dialog.xml file
                    dialog.setContentView(R.layout.min_order);
                    // Set dialog title
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    // set values for custom dialog components - text, image and button
                   // TextView text = (TextView) dialog.findViewById(R.id.textDialog);
                   // text.setText("Custom dialog Android example.");
                   // ImageView image = (ImageView) dialog.findViewById(R.id.imageDialog);
                   // image.setImageResource(R.drawable.image0);

                    dialog.show();

                    ButtonModarno declineButton = (ButtonModarno) dialog.findViewById(R.id.go_back);
                    ButtonModarno checkout = (ButtonModarno) dialog.findViewById(R.id.checkout);
                    // if decline button is clicked, close the custom dialog
                    declineButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Close dialog
                            dialog.dismiss();
                        }
                    });
                    checkout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(StaticCatelog.getStringProperty(getContext(),"api_key")==null) {
                                Intent intent =new Intent(getContext(), LoginNum.class);
                                intent.putExtra("from","cart");
                                startActivity(intent);
                            }
                            else
                            {
                                Intent intent =new Intent(getContext(), ProcessOrder.class);
                                startActivity(intent);
                            }

                        }
                    });
                }

            }
        });
        return layoutView;
    }
    interface buttonClick {
        void buttonClicked(View v);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        click = (buttonClick) activity;
    }

    @Override
    public void adjustCameraOrViewPager(boolean on) {
        //Do nothing for this
        if(on)
        {
            ArrayList<Class_Cart> items = cart.getAllCart();
            ArrayList<Class_Cart> itema = cart.getDistinctRetailer();
            int total_item=0;
            double total_price=0.0;
            for (int i=0;i<items.size();i++)
            {
                total_item=total_item+items.get(i).quantity;
                Log.e("some",""+total_item);
                total_price=total_price+items.get(i).price*items.get(i).quantity;
            }
            ((TextView)getView().findViewById(R.id.num_of_items_in_cart)).setText(""+total_item);
            ((TextView)layoutView.findViewById(R.id.money_foot)).setText(""+total_price);
            StaticCatelog.setStringProperty(getContext(),"total_price",String.valueOf(total_price));
            this.total_item=total_item;
            this.total_pice=total_price;
            Adapter_Cart adapter_cart = new Adapter_Cart(getContext(),items,itema,handler,eventInitialiser);
            lv.setAdapter(adapter_cart);
            handleCheckoutVisibility();
        }
    }

    @Override
    public void startLoadbylocation(String Area,String location) {
        //will be requiring to delete items on cart that are not in current location
        tellThatLoadedSuccessfully(true);
    }

    public void addInitialisationEvent(FragmentsEventInitialiser eventInitialiser)
    {
        this.eventInitialiser=eventInitialiser;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(eventInitialiser!=null)
            eventInitialiser.registerMyevent(fragVal,this);
        tellThatLoadedSuccessfully(true);

    }

    public void tellThatLoadedSuccessfully(final Boolean successfull)
    {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (eventInitialiser != null)
                    eventInitialiser.MyloadingCompleted(fragVal,successfull);
            }
        }, 1000);

    }


    public interface Total_item_in_cart_text_handler
    {
        void handleText_cart(int total,double price);
    }

    public void handleCheckoutVisibility()
    {
        if(total_item<=0)
        {
            checkout_lay.setVisibility(View.GONE);
        }
        else
        {
            checkout_lay.setVisibility(View.VISIBLE);
        }
    }
}