package co.jlabs.cersei_retailer;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import co.jlabs.cersei_retailer.ActivityTransition.ActivityTransitionLauncher;
import co.jlabs.cersei_retailer.Rounded.RoundedImageView;
import co.jlabs.cersei_retailer.VerticalAdapter.VerticalActivity;
import co.jlabs.cersei_retailer.activity.SwipeTest;
import co.jlabs.cersei_retailer.custom_components.AddOrRemoveCart;
import co.jlabs.cersei_retailer.custom_components.MyImageView;
import co.jlabs.cersei_retailer.custom_components.ShoppingView;
import co.jlabs.cersei_retailer.custom_components.Sqlite_cart;
import co.jlabs.cersei_retailer.custom_components.TextViewModernM;
import co.jlabs.cersei_retailer.custom_components.VolleyImageInterface;
import co.jlabs.cersei_retailer.swipecardlib.SwipeCardView;
import co.jlabs.cersei_retailer.swiper.Card;
import co.jlabs.cersei_retailer.swiper.CardsAdapter;


/**
 * Created by Pradeep on 12/31/2015.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private static final int VIEW_HEADER = 0;
    private static final int VIEW_NORMAL = 1;
    private View headerView;
    ImageLoader imageLoader;

    JSONArray json_offers;
    Sqlite_cart cart;
    Context context;
    RecyclerAdapter adapter;
    FragmentsEventInitialiser eventInitialiser;
    //private int lastPosition = -1;
    int appcolor;
    int so=0;
//Changes

    private ArrayList<Card> al;
    private CardsAdapter arrayAdapter;
    private int i;
    TextView tv_position;
    int num_item=0;


    //Changes


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
            public HeaderViewHolder(View v) {
                super(v);
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            RelativeLayout upper;
            View gridItem;
            TextViewModernM text,desc,text_price;
            TextView deliverable,points,retailer;
            View mask;
            ShoppingView newADD;
            ImageView dashed;
            RoundedImageView Pic;
           // AddOrRemoveCart addOrRemoveCart;


            public ViewHolder(View v) {
                super(v);
                gridItem = v;
                text=((TextViewModernM)v.findViewById(R.id.pro_name));
                desc=((TextViewModernM)v.findViewById(R.id.pro_desc));
                retailer=((TextViewModernM)v.findViewById(R.id.retailer));
                Pic=(RoundedImageView) v.findViewById(R.id.pro_icon);
                text_price=((TextViewModernM)v.findViewById(R.id.price));
                dashed=((ImageView)v.findViewById(R.id.dashed));
                deliverable= (TextView) v.findViewById(R.id.deliverable);
                points= (TextView) v.findViewById(R.id.points);
                upper= (RelativeLayout) v.findViewById(R.id.upper);
                mask=v.findViewById(R.id.mask);
                newADD=(ShoppingView)v.findViewById(R.id.newAdd);
            //    addOrRemoveCart= (AddOrRemoveCart) v.findViewById(R.id.add_or_remove_cart);
            }
        }



        public RecyclerAdapter(Context context,JSONArray json,FragmentsEventInitialiser eventInitialiser) {
            this.adapter=this;
            this.json_offers=json;
            cart = new Sqlite_cart(context);
            this.context=context;
            appcolor=context.getResources().getColor(R.color.result_view1);
            imageLoader = AppController.getInstance().getImageLoader();
            this.eventInitialiser=eventInitialiser;
        }

        public void setHeader(View v) {
            this.headerView = v;
        }

        @Override
        public int getItemViewType(int position) {
            return position == 0 ? VIEW_HEADER : VIEW_NORMAL;
        }

        @Override
        public int getItemCount() {
            int count=0;
            try
            {
                count=json_offers.length()+1;
            }
            catch (Exception e)
            {

            }

            return count;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == VIEW_HEADER) {
                return new HeaderViewHolder(headerView);

            } else {

                View textView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adap_products, parent, false);
                return new ViewHolder(textView);
            }
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {

            if (position == 0)
            {
                return;
            }
            else {
                final ViewHolder holder = (ViewHolder) viewHolder;
                String s1,s2,s3,s4;

                try {
                    //holder.deliverable.setTextColor(((JSONObject) json_offers.get(position - 1)).getBoolean("delivery") ? appcolor : 0xfff20022);
                   // JSONObject json=((JSONObject) json_offers.get(position - 1)).getJSONObject("item");

                    int points= ((JSONObject) json_offers.get(position - 1)).getInt("cashback");

                    //TO DO later
                    Log.e("Delete",""+cart.findIfOfferAlreadyExistsInCart(( json_offers.getJSONObject((position - 1))).getString("offer_id")));
                    if (cart.findIfOfferAlreadyExistsInCart(( json_offers.getJSONObject((position - 1))).getString("offer_id"))>0) {
                        holder.newADD.setTextNum(cart.findIfOfferAlreadyExistsInCart(( json_offers.getJSONObject((position - 1))).getString("offer_id")));
                    }

                    //TO DO later
                    s1=((JSONObject) json_offers.get(position - 1)).getString("product_name");
                    s4=((JSONObject) json_offers.get(position - 1)).getString("retailer_name");
                    s2=((JSONObject) json_offers.get(position - 1)).getString("detail");
                  //  Log.e("somelog",json.toString());
                    s3=((JSONObject) json_offers.get(position - 1)).getInt("price")+"";
                   // holder.text.setText(StaticCatelog.SpanIt2(s1, s2, s3));
                    holder.text.setText(s1);
                    holder.desc.setText(s2);
                    holder.retailer.setText(s4);
                    holder.points.setText(points+"%");
                    holder.text_price.setText("₹" + s3);
                    holder.dashed.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                    if(position==2||position==4){
                        final int sdk = android.os.Build.VERSION.SDK_INT;
                        if(sdk >android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            holder.upper.setBackground( context.getResources().getDrawable(R.drawable.adap_pro_d));
                            holder.newADD.mPaintBg.setColor(Color.parseColor("#F56D7F"));
                            holder.newADD.mPaintMinus.setColor(Color.parseColor("#F56D7F"));

                            holder.points.setBackground( context.getResources().getDrawable(R.drawable.adap_pro3d));

                        } else {
                            holder.upper.setBackgroundDrawable( context.getResources().getDrawable(R.drawable.adap_pro_d) );
                            holder.newADD.mPaintBg.setColor(Color.parseColor("#F56D7F"));
                            holder.newADD.mPaintMinus.setColor(Color.parseColor("#F56D7F"));
                            holder.points.setBackgroundDrawable( context.getResources().getDrawable(R.drawable.adap_pro3d) );
                        }

                    }
                   else if(position==3){
                        final int sdk = android.os.Build.VERSION.SDK_INT;
                        if(sdk >android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            holder.upper.setBackground( context.getResources().getDrawable(R.drawable.adap_pro_c));
                            holder.newADD.mPaintBg.setColor(Color.parseColor("#BF6A83"));


                            holder.newADD.mPaintMinus.setColor(Color.parseColor("#BF6A83"));
                            holder.points.setBackground( context.getResources().getDrawable(R.drawable.adap_pro3c));

                        } else {
                            holder.upper.setBackgroundDrawable( context.getResources().getDrawable(R.drawable.adap_pro_c) );
                            holder.newADD.mPaintBg.setColor(Color.parseColor("#BF6A83"));
                            holder.newADD.mPaintMinus.setColor(Color.parseColor("#BF6A83"));
                            holder.points.setBackgroundDrawable( context.getResources().getDrawable(R.drawable.adap_pro3c) );
                        }

                    }
                   // holder.newADD.setTextNum(cart.addToCart( json_offers.getJSONObject(position - 1)));
                    holder.newADD.setOnShoppingClickListener(new ShoppingView.ShoppingClickListener() {
                        @Override
                        public void onAddClick(int num) {

                            int quantity=0;
                            try {
                                int remaining_qrcodes= ((JSONObject) json_offers.get(position - 1)).getInt("remaining_qrcodes");
                                Log.e(""+position,"remaining_qrcodes:"+remaining_qrcodes);
                                if (remaining_qrcodes>cart.findIfOfferAlreadyExistsInCart((( json_offers.getJSONObject((position - 1))).getString("offer_id")))) {
                                    eventInitialiser.updateCart(true);
                                    try{

                                        quantity=cart.addToCart( json_offers.getJSONObject(position - 1));
                                        so=quantity;
                                        Log.e("ero","0"+ json_offers.getJSONObject(position - 1).getString("item_id"));
                                        holder.newADD.setTextNum(quantity);
                                        Toast.makeText(context, "Added To Cart", Toast.LENGTH_SHORT).show();
                                    }
                                    catch (Exception e)
                                    {

                                    }
                                } else {
                                    holder.newADD.setTextNum(remaining_qrcodes);
                                    Toast.makeText(context, "Limited Inventory", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onMinusClick(int num) {
                            Toast.makeText(context,"Removed From Cart",Toast.LENGTH_SHORT).show();
                            int quantity=0;

                            eventInitialiser.updateCart(false);
                            try {

                                quantity=cart.removeFromCart((( json_offers.getJSONObject((position - 1))).getString("offer_id")));
                                so=quantity;
                                Log.e("somee",""+quantity);
                                holder.newADD.setTextNum(quantity);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("somee1",""+quantity);
                            }
                        }
                    });
                    // holder.Pic.setImageUrl(json.getString("img"), imageLoader);



                    JSONArray pic=((JSONObject) json_offers.get(position - 1)).getJSONArray("img");
                    String picas=pic.get(0).toString();
                    Log.e("Picas",picas);
                    try {
                        Picasso.with(context)
                                .load(picas)
                                .into(holder.Pic);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


//                    holder.Pic.setOnImageChangeListner(new VolleyImageInterface() {
//                        @Override
//                        public void adjustColor(int color) {
//                            holder.mask.setBackgroundColor(color);
//                        }
//                    });
//                    holder.addOrRemoveCart.addOnItemClickListner(new AddOrRemoveCart.ItemsClickListener() {
//                        @Override
//                        public int addItemClicked(int position) {
//                            Toast.makeText(context, "Added To Cart", Toast.LENGTH_SHORT).show();
//                            int quantity=0;
//                            eventInitialiser.updateCart(true);
//                            try{
//
//                                quantity=cart.addToCart(((JSONObject) json_offers.get(position - 1)));
//                            }
//                            catch (Exception e)
//                            {
//
//                            }
//
//                            return quantity;
//                        }
//
//                        @Override
//                        public int removeItemClicked(View v,int position) {
//                            Toast.makeText(context,"Removed From Cart",Toast.LENGTH_SHORT).show();
//                            int quantity=0;
//                            eventInitialiser.updateCart(false);
//                            try {
//                                quantity=cart.removeFromCart(((JSONObject) json_offers.get((position - 1))).getInt("detail"));
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            return quantity;
//                        }
//                    },position,cart.findIfOfferAlreadyExistsInCart(((JSONObject) json_offers.get((position - 1))).getInt("detail")));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                holder.upper.setOnClickListener(this);
                holder.upper.setTag(position);
            }

//            Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
//            viewHolder.itemView.startAnimation(animation);
//            lastPosition = position;
        }

    @Override
    public void onClick(View v) {
//        eventHandler.adjustCameraOrViewPager(false);
//        Intent i = new Intent(v.getContext(),SwipeTest.class);
//        Log.e("hee","hee");
//        i.putExtra("position",Integer.parseInt(v.getTag().toString()));
//        i.putExtra("offers",json_offers.toString());
//        ActivityTransitionLauncher.with(getActivity(v)).from(v).launch(i);
//        v.getContext().startActivity(i);





        Intent i = new Intent(v.getContext(),VerticalActivity.class);
        Log.e(" SearchableSpinner",""+json_offers.toString());
        Log.e(" SearchableSpinner",""+1);
        i.putExtra("position",Integer.parseInt(v.getTag().toString()));
        i.putExtra("so",so);
        i.putExtra("offers",json_offers.toString());
        //ActivityTransitionLauncher.with(getActivity(v)).from(v).launch(i);
        v.getContext().startActivity(i);

    }

    private Activity getActivity(View v) {
        Context context = v.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }
    static void makeToast(Context ctx, String s){
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }

}