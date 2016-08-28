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
            TextView deliverable,points;
            View mask;
            ShoppingView newADD;
            ImageView dashed;
            RoundedImageView Pic;
            AddOrRemoveCart addOrRemoveCart;


            public ViewHolder(View v) {
                super(v);
                gridItem = v;
                text=((TextViewModernM)v.findViewById(R.id.pro_name));
                desc=((TextViewModernM)v.findViewById(R.id.pro_desc));
                Pic=(RoundedImageView) v.findViewById(R.id.pro_icon);
                text_price=((TextViewModernM)v.findViewById(R.id.price));
                dashed=((ImageView)v.findViewById(R.id.dashed));
                deliverable= (TextView) v.findViewById(R.id.deliverable);
                points= (TextView) v.findViewById(R.id.points);
                upper= (RelativeLayout) v.findViewById(R.id.upper);
                mask=v.findViewById(R.id.mask);
                newADD=(ShoppingView)v.findViewById(R.id.newAdd);
                addOrRemoveCart= (AddOrRemoveCart) v.findViewById(R.id.add_or_remove_cart);
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
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

            if (position == 0)
            {
                return;
            }
            else {
                final ViewHolder holder = (ViewHolder) viewHolder;
                String s1,s2,s3;

                try {
                    //holder.deliverable.setTextColor(((JSONObject) json_offers.get(position - 1)).getBoolean("delivery") ? appcolor : 0xfff20022);
                    JSONObject json=((JSONObject) json_offers.get(position - 1)).getJSONObject("item");
                    s1=json.getString("name");
                    s2=json.getString("desc");

                    s3=json.getString("price");
                   // holder.text.setText(StaticCatelog.SpanIt2(s1, s2, s3));
                    holder.text.setText(s1);
                    holder.desc.setText(s2);
                    holder.text_price.setText("₹" + s3);
                    holder.dashed.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                    if(position==2||position==4){
                        final int sdk = android.os.Build.VERSION.SDK_INT;
                        if(sdk >android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            holder.upper.setBackground( context.getResources().getDrawable(R.drawable.adap_pro_d));
                            holder.newADD.mPaintBg.setColor(Color.parseColor("#DE463B"));
                            holder.newADD.mPaintMinus.setColor(Color.parseColor("#DE463B"));

                            holder.points.setBackground( context.getResources().getDrawable(R.drawable.adap_pro3d));

                        } else {
                            holder.upper.setBackgroundDrawable( context.getResources().getDrawable(R.drawable.adap_pro_d) );
                            holder.newADD.mPaintBg.setColor(Color.parseColor("#DE463B"));
                            holder.newADD.mPaintMinus.setColor(Color.parseColor("#DE463B"));
                            holder.points.setBackgroundDrawable( context.getResources().getDrawable(R.drawable.adap_pro3d) );
                        }

                    }
                   else if(position==3){
                        final int sdk = android.os.Build.VERSION.SDK_INT;
                        if(sdk >android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            holder.upper.setBackground( context.getResources().getDrawable(R.drawable.adap_pro_c));
                            holder.newADD.mPaintBg.setColor(Color.parseColor("#0193E6"));
                            holder.newADD.mPaintMinus.setColor(Color.parseColor("#0193E6"));
                            holder.points.setBackground( context.getResources().getDrawable(R.drawable.adap_pro3c));

                        } else {
                            holder.upper.setBackgroundDrawable( context.getResources().getDrawable(R.drawable.adap_pro_c) );
                            holder.newADD.mPaintBg.setColor(Color.parseColor("#0193E6"));
                            holder.newADD.mPaintMinus.setColor(Color.parseColor("#0193E6"));
                            holder.points.setBackgroundDrawable( context.getResources().getDrawable(R.drawable.adap_pro3c) );
                        }

                    }
                    // holder.Pic.setImageUrl(json.getString("img"), imageLoader);

//                    Picasso.with(context)
//  fgjhygkjghk                          .load(json.getString("img"))
//                            .into(holder.Pic);


//                    holder.Pic.setOnImageChangeListner(new VolleyImageInterface() {
//                        @Override
//                        public void adjustColor(int color) {
//                            holder.mask.setBackgroundColor(color);
//                        }
//                    });
                    holder.addOrRemoveCart.addOnItemClickListner(new AddOrRemoveCart.ItemsClickListener() {
                        @Override
                        public int addItemClicked(int position) {
                            Toast.makeText(context, "Added To Cart", Toast.LENGTH_SHORT).show();
                            int quantity=0;
                            eventInitialiser.updateCart(true);
                            try{

                                quantity=cart.addToCart(((JSONObject) json_offers.get(position - 1)));
                            }
                            catch (Exception e)
                            {

                            }

                            return quantity;
                        }

                        @Override
                        public int removeItemClicked(View v,int position) {
                            Toast.makeText(context,"Removed From Cart",Toast.LENGTH_SHORT).show();
                            int quantity=0;
                            eventInitialiser.updateCart(false);
                            try {
                                quantity=cart.removeFromCart(((JSONObject) json_offers.get((position - 1))).getInt("offer_id"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            return quantity;
                        }
                    },position,cart.findIfOfferAlreadyExistsInCart(((JSONObject) json_offers.get((position - 1))).getInt("offer_id")));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                holder.gridItem.setOnClickListener(this);
                holder.gridItem.setTag(position);
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
        Log.e("hee","hee");
        i.putExtra("position",Integer.parseInt(v.getTag().toString()));
        i.putExtra("offers",json_offers.toString());
        //ActivityTransitionLauncher.with(getActivity(v)).from(v).launch(i);
        v.getContext().startActivity(i);

/*        final Dialog dialog = new Dialog(getActivity(v));
        dialog.setContentView(R.layout.activity_swipe_test);
        ButterKnife.inject(dialog);
        JSONArray offers=null;
        al = new ArrayList<>();
        getDummyData(al);
        arrayAdapter = new CardsAdapter(this.context, al );
        final SwipeCardView swipeCardView =(SwipeCardView)dialog.findViewById(R.id.card_stack_view);
        swipeCardView.setAdapter(arrayAdapter);
        swipeCardView.setFlingListener(new SwipeCardView.OnCardFlingListener() {
            @Override
            public void onCardExitLeft(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
//                makeToast(CardSwipeActivity.this, "Left!");
            }

            @Override
            public void onCardExitRight(Object dataObject) {
//                makeToast(CardSwipeActivity.this, "Right!");
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
//                al.add("XML ".concat(String.valueOf(i)));
//                arrayAdapter.notifyDataSetChanged();
//                Log.d("LIST", "notified");
//                i++;
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                View view = swipeCardView.getSelectedView();
//                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
//                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }

            @Override
            public void onCardExitTop(Object dataObject) {
//                makeToast(CardSwipeActivity.this, "Top!");
            }

            @Override
            public void onCardExitBottom(Object dataObject) {

            }
        });


        // Optionally add an OnItemClickListener
        swipeCardView.setOnItemClickListener(
                new SwipeCardView.OnItemClickListener() {
                    @Override
                    public void onItemClicked(int itemPosition, Object dataObject) {
                        Card card = (Card) dataObject;
                        makeToast(context, card.name);
                    }
                });

*/


        // set the custom dialog components - text, image and button
       // ButtonModarno submit =(ButtonModarno)dialog.findViewById(R.id.submit);
        // if button is clicked, close the custom dialog
      //  final EditText mEditText = (EditText) dialog.findViewById(R.id.edittext);
       // TextViewModernM textView = new TextViewModernM(getActivity());
        //textView.setText("Kashyap");


//        submit.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                mLayout.addView(createNewTextView(mEditText.getText().toString()));
//                dialog.dismiss();
//            }
//        });

        //dialog.show();

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
    private void getDummyData(ArrayList<Card> al) {

        Card card = new Card();
        card.name = "Card1";
        card.imageId = R.drawable.ic_cast_light;
        al.add(card);
        Card card2 = new Card();
        card2.name = "Card2";
        card2.imageId = R.drawable.ic_cast_light;
        al.add(card2);
        Card card3 = new Card();
        card3.name = "Card3";
        card3.imageId = R.drawable.ic_cast_on_1_light;
        al.add(card3);
        Card card4 = new Card();
        card4.name = "Card4";
        card4.imageId = R.drawable.ic_cast_light;
        al.add(card4);
        Card card5 = new Card();
        card5.name = "Card5";
        card5.imageId = R.drawable.ic_cast_light;
        al.add(card5);
        Card card56 = new Card();
        card56.name = "Card6";
        card56.imageId = R.drawable.ic_cast_light;
        al.add(card56);
    }
}