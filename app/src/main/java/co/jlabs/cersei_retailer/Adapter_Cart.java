package co.jlabs.cersei_retailer;

/**
 * Created by ` on 19-12-2015.
 */
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import android.widget.Toast;
import com.android.volley.toolbox.ImageLoader;
import com.github.florent37.fiftyshadesof.FiftyShadesOf;

import java.util.ArrayList;
import co.jlabs.cersei_retailer.custom_components.AddOrRemoveCart;
import co.jlabs.cersei_retailer.custom_components.Class_Cart;
import co.jlabs.cersei_retailer.custom_components.Class_retailer;
import co.jlabs.cersei_retailer.custom_components.MyImageView;
import co.jlabs.cersei_retailer.custom_components.Sqlite_cart;
import co.jlabs.cersei_retailer.custom_components.TextViewModernM;
import co.jlabs.cersei_retailer.custom_components.TextView_Triangle;
import static co.jlabs.cersei_retailer.R.id.father;


public class Adapter_Cart extends BaseAdapter {
    private Context context;
    ImageLoader imageLoader;
    ArrayList<Class_Cart> offers_list;
    ArrayList<Class_Cart> retailer_list;
    Sqlite_cart cart;
      int N=0;
    int qu=0;
    Fragment_Cart.Total_item_in_cart_text_handler totalItemInCartTextHandler;
    private int lastPosition = -1;
    Animation animation;
    FragmentsEventInitialiser eventInitialiser;


    public Adapter_Cart(Context context,ArrayList<Class_Cart>  offers_list,ArrayList<Class_Cart>  retailer_list,Fragment_Cart.Total_item_in_cart_text_handler handler,FragmentsEventInitialiser eventInitialiser) {
        this.context = context;
        cart=new Sqlite_cart(context);
        this.offers_list=retailer_list;
        this.retailer_list=retailer_list;
        this.totalItemInCartTextHandler=handler;
        imageLoader = AppController.getInstance().getImageLoader();
        this.eventInitialiser=eventInitialiser;
    }

    static class ViewHolder
    {
        co.jlabs.cersei_retailer.custom_components.TextViewModernM retailer_name;
        co.jlabs.cersei_retailer.custom_components.TextViewModernM minOrder;
        co.jlabs.cersei_retailer.custom_components.TextViewModernM total,add_more,strip;
        LinearLayout min_back,father;
        Button call;
        TextView_Triangle points;
        MyImageView Pic;
      //  AddOrRemoveCart addOrRemoveCart;
        View Close;
    }

    public View getView(final int position, View gridView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

       final ViewHolder viewHolder;



            gridView = inflater.inflate(R.layout.new_adap_cart, null);
            viewHolder = new ViewHolder();
            viewHolder.retailer_name = (co.jlabs.cersei_retailer.custom_components.TextViewModernM) gridView.findViewById(R.id.retailer_name);
            viewHolder.minOrder = (co.jlabs.cersei_retailer.custom_components.TextViewModernM) gridView.findViewById(R.id.min_order);
            viewHolder.total = (co.jlabs.cersei_retailer.custom_components.TextViewModernM) gridView.findViewById(R.id.total);
            viewHolder.call = (Button) gridView.findViewById(R.id.call);
            viewHolder.add_more = (co.jlabs.cersei_retailer.custom_components.TextViewModernM) gridView.findViewById(R.id.am);
            viewHolder.strip = (co.jlabs.cersei_retailer.custom_components.TextViewModernM) gridView.findViewById(R.id.strip);
            viewHolder.min_back = (LinearLayout) gridView.findViewById(R.id.min_order_back);
            viewHolder.father = (LinearLayout) gridView.findViewById(father);




//            viewHolder.points = (TextView_Triangle) gridView.findViewById(R.id.points);
       //     viewHolder.Pic= (MyImageView) gridView.findViewById(R.id.pic);
//            viewHolder.addOrRemoveCart= (AddOrRemoveCart) gridView.findViewById(R.id.add_or_remove_cart);
            //viewHolder.Close=gridView.findViewById(R.id.close);
            gridView.setTag(viewHolder);





     final   ArrayList<Class_Cart> tots = cart.getRetailerOrderData(offers_list.get(position).retailer_id);
     final   ArrayList<Class_retailer> ret = cart.getRetailerData(offers_list.get(position).retailer_id);
        int M= ret.size();
      //  Log.e("geet",""+ret.get(0).retailer_name);
        viewHolder.retailer_name.setText(ret.get(0).retailer_name);
        viewHolder.minOrder.setText(ret.get(0).min_order);

        //viewHolder.total = (co.jlabs.cersei_retailer.custom_components.TextViewModernM) gridView.findViewById(R.id.total);
        viewHolder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+ret.get(0).contact));
                context.startActivity(callIntent);
            }
        });
//        viewHolder.add_more = (co.jlabs.cersei_retailer.custom_components.TextViewModernM) gridView.findViewById(R.id.am);
//        viewHolder.min_back = (LinearLayout) gridView.findViewById(R.id.min_order_back);
        N=tots.size();

       //final co.jlabs.cersei_retailer.custom_components.TextViewModernM[] myTextViews = new co.jlabs.cersei_retailer.custom_components.TextViewModernM[N];


        for (int i = 0; i < N; i++) {


            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = vi.inflate(R.layout.new_adap_adap_cart, null);
            // create a new textview
            final TextViewModernM product_name = (TextViewModernM)v.findViewById(R.id.product_name);
            final TextViewModernM money = (TextViewModernM)v.findViewById(R.id.money);
            final MyImageView pic = (MyImageView) v.findViewById(R.id.pic);
            final AddOrRemoveCart addOrRemoveCart=(AddOrRemoveCart)v.findViewById(R.id.add_or_remove_cart);

            // set some properties of rowTextView or something
            product_name.setText(tots.get(i).product_name);

           // money.setText(""+tots.get(i).quantity);
            pic.setImageUrl(tots.get(i).img, imageLoader);
            // add the textview to the linearlayout
            viewHolder.father.addView(v);
             qu=tots.get(i).quantity;
            money.setText(""+tots.get(i).price);

            viewHolder.add_more.setText("Rs"+tots.get(i).price*tots.get(i).quantity);
            eventInitialiser.updateCart1(true,tots.get(i).price*tots.get(i).quantity);
            addOrRemoveCart.addOnItemClickListner(new AddOrRemoveCart.ItemsClickListener() {
                @Override
                public int addItemClicked(int i) {


                    if(tots.get(i).remaining_qrcodes>tots.get(i).quantity){
                        Toast.makeText(context, "Added To Cart", Toast.LENGTH_SHORT).show();
                        totalItemInCartTextHandler.handleText_cart(1,tots.get(i).price);
                        tots.get(i).quantity++;
                       // money.setText(""+tots.get(i).price);
                        Log.e("summ",""+tots.get(i).quantity);
                        viewHolder.add_more.setText("Rs"+tots.get(i).price*tots.get(i).quantity);
                        eventInitialiser.updateCart(true);
                        eventInitialiser.updateCart1(true,tots.get(i).price*tots.get(i).quantity);
                        return cart.addToCart(tots.get(i));
                    }else
                    {
                        Toast.makeText(context, "cant add To Cart", Toast.LENGTH_SHORT).show();
                        return cart.findIfOfferAlreadyExistsInCart(tots.get(i).offer_id);
                    }


                }

                @Override
                public int removeItemClicked(View v,final int i) {
                    Toast.makeText(context, "Removed From Cart", Toast.LENGTH_SHORT).show();

                    int quantity = cart.removeFromCart(tots.get(i).offer_id);

                    if (quantity == 0) {
                        View main = ((ViewGroup) v.getParent().getParent().getParent().getParent().getParent().getParent().getParent());
                        //Log.i("Myapp", "" + main.getId());
                        Animation animation = AnimationUtils.loadAnimation(context, R.anim.fade_out);
                        main.startAnimation(animation);

                        animation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                Log.e("dat",""+viewHolder.father.getChildCount());
                                Log.e("dat1",""+i);
                                tots.remove(i);
                                try {
                                    viewHolder.total.setText(""+tots.get(i).price*tots.get(i).quantity);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                //money.setText(""+tots.get(i).price*tots.get(i).quantity);
                                if ( viewHolder.father.getChildCount()==1) {
                                    offers_list.remove(position);
                                }

                                notifyDataSetChanged();
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });

                    }
                    eventInitialiser.updateCart(false);
                    eventInitialiser.updateCart1(true,tots.get(i).price*tots.get(i).quantity);
                    tots.get(i).quantity--;
                   // money.setText(""+tots.get(i).price);
                    Log.e("summ",""+tots.get(i).quantity);
                    viewHolder.add_more.setText("Rs"+tots.get(i).price*tots.get(i).quantity);
                    totalItemInCartTextHandler.handleText_cart(-1,-tots.get(i).price);
                    return quantity;
                }
            }, i, tots.get(i).quantity);


            // save a reference to the textview for later
            //myTextViews[i] = rowTextView;
        }







        Log.e("somedatat",":"+offers_list.get(position).retailer_id);





     //  viewHolder.add_more.setText("Rs " + offers_list.get(position).price);
//        viewHolder.points.setText("" + context.getResources().getString(R.string.rating) + offers_list.get(position).cashback);
 //       viewHolder.Pic.setImageUrl(offers_list.get(position).img, imageLoader);
//        viewHolder.Close.setTag(position);
//        viewHolder.Close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View v) {
//                Toast.makeText(context, "Removed From Cart", Toast.LENGTH_SHORT).show();
//                int quantity = cart.deleteFromCart(offers_list.get((int) v.getTag()).detail);
//                double price=offers_list.get((int) v.getTag()).price;
//                totalItemInCartTextHandler.handleText_cart(-quantity,price*-quantity);
//                View main = ((ViewGroup) v.getParent().getParent().getParent().getParent());
//                Animation animation = AnimationUtils.loadAnimation(context, R.anim.fade_out);
//                main.startAnimation(animation);
//                animation.setAnimationListener(new Animation.AnimationListener() {
//                    @Override
//                    public void onAnimationStart(Animation animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animation animation) {
//                        offers_list.remove((int) v.getTag());
//                        notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animation animation) {
//
//                    }
//                });
//
//            }
//        });

//        viewHolder.addOrRemoveCart.addOnItemClickListner(new AddOrRemoveCart.ItemsClickListener() {
//            @Override
//            public int addItemClicked(int position) {
//                Toast.makeText(context, "Added To Cart", Toast.LENGTH_SHORT).show();
//                totalItemInCartTextHandler.handleText_cart(1,offers_list.get(position).price);
//                return cart.addToCart(offers_list.get(position));
//            }
//
//            @Override
//            public int removeItemClicked(View v,final int position) {
//                Toast.makeText(context, "Removed From Cart", Toast.LENGTH_SHORT).show();
//
//                int quantity = cart.removeFromCart(offers_list.get(position).detail);
//                if (quantity == 0) {
//                    View main = ((ViewGroup) v.getParent().getParent().getParent().getParent().getParent().getParent().getParent());
//                    //Log.i("Myapp", "" + main.getId());
//                    Animation animation = AnimationUtils.loadAnimation(context, R.anim.fade_out);
//                    main.startAnimation(animation);
//
//                    animation.setAnimationListener(new Animation.AnimationListener() {
//                        @Override
//                        public void onAnimationStart(Animation animation) {
//
//                        }
//
//                        @Override
//                        public void onAnimationEnd(Animation animation) {
//                            offers_list.remove(position);
//                            notifyDataSetChanged();
//                        }
//
//                        @Override
//                        public void onAnimationRepeat(Animation animation) {
//
//                        }
//                    });
//
//                }
//                totalItemInCartTextHandler.handleText_cart(-1,-offers_list.get(position).price);
//                return quantity;
//            }
//        }, position, offers_list.get(position).quantity);
//        if((position > lastPosition)) {
//            animation = AnimationUtils.loadAnimation(context, R.anim.up_from_bottom);
//            gridView.startAnimation(animation);
//        }
//        lastPosition = position;
        if(( position> lastPosition)) {
            animation = AnimationUtils.loadAnimation(context, R.anim.up_from_bottom);
            gridView.startAnimation(animation);
        }
        lastPosition = position;
        return gridView;
    }

    @Override
    public int getCount() {
        return offers_list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}