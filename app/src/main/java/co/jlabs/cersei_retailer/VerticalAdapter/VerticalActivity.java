package co.jlabs.cersei_retailer.VerticalAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.gigamole.infinitecycleviewpager.VerticalInfiniteCycleViewPager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import co.jlabs.cersei_retailer.AppController;
import co.jlabs.cersei_retailer.R;
import co.jlabs.cersei_retailer.Rounded.CircularImageView;
import co.jlabs.cersei_retailer.custom_components.AddOrRemoveCart;
import co.jlabs.cersei_retailer.custom_components.ShoppingView;
import co.jlabs.cersei_retailer.custom_components.Sqlite_cart;
import co.jlabs.cersei_retailer.custom_components.TextViewModernM;
import co.jlabs.cersei_retailer.custom_components.transforms.MyCubeOutTransformer;

import static co.jlabs.cersei_retailer.VerticalAdapter.Utils.setupImage;


/**
 * Created by Kashyap on 8/18/16.
 */
public class VerticalActivity extends Activity {

    TextView tv_position;
    int num_item=0;
    Context context;
    Sqlite_cart cart;
    int so=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_vertical);
        JSONArray offers=null;
        context=this;
        int position=getIntent().getIntExtra("position",1);
        try {
            offers = new JSONArray(getIntent().getStringExtra("offers"));
            Log.e("point1",""+offers.toString());
            so=getIntent().getIntExtra("so",1);
            num_item=offers.length();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        cart=new Sqlite_cart(this);
        final HorizontalInfiniteCycleViewPager verticalInfiniteCycleViewPager =
                (HorizontalInfiniteCycleViewPager) findViewById(R.id.vicvp);
        tv_position = (TextView) findViewById(R.id.position);
        verticalInfiniteCycleViewPager.setAdapter(new VerticalPagerAdapter(this, offers));
        verticalInfiniteCycleViewPager.setCurrentItem(position - 1);
        tv_position.setVisibility(View.GONE);
        verticalInfiniteCycleViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               // tv_position.setText("" + (position + 1) + " of "+num_item+" offers");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        findViewById(R.id.dismissifclickedhere).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public class VerticalPagerAdapter extends PagerAdapter {


        Context mContext;
        LayoutInflater mLayoutInflater;
        ImageLoader imageLoader;
        JSONArray offers;



        public VerticalPagerAdapter(Context context,JSONArray offers) {
            mContext = context;
            this.offers=offers;
            mLayoutInflater = LayoutInflater.from(mContext);
            imageLoader = AppController.getInstance().getImageLoader();
        }

        // Returns the number of pages to be displayed in the ViewPager.
        @Override
        public int getCount() {
            return offers.length();
        }

        @Override
        public int getItemPosition(final Object object) {
            return POSITION_NONE;
        }

        // This method should create the page for the given position passed to it as an argument.
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // Inflate the layout for the page
            View itemView = mLayoutInflater.inflate(R.layout.adap_details, container, false);
            try {
                //JSONObject json=((JSONObject)offers.get(position)).getJSONObject("item");
                int points= ((JSONObject) offers.get(position)).getInt("cashback");
                Log.e("points",""+points);
                Log.e("points",""+offers.toString());


                TextViewModernM title=(TextViewModernM) itemView.findViewById(R.id.product_name);

                TextViewModernM cashback=(TextViewModernM) itemView.findViewById(R.id.cashback);
                CircularImageView pica=(CircularImageView) itemView.findViewById(R.id.pica);
                ShoppingView newADD=(ShoppingView) itemView.findViewById(R.id.newAdd);
                CardView card_view=  (CardView) itemView.findViewById(R.id.card_view);
//                ((NetworkImageView) itemView.findViewById(R.id.pic)).setImageUrl(json.getString("img"), imageLoader);
                title.setText(((JSONObject) offers.get(position)).getString("product_name"));
                cashback.setText(points+"%");
                pica.setShadowColor(Color.parseColor("#000000"));



                if(position==1){
                        card_view.setCardBackgroundColor(Color.parseColor("#57C7D3"));
                        pica.setBorderColor(Color.parseColor("#57C7D3"));
                        newADD.mPaintBg.setColor(Color.parseColor("#57C7D3"));
                        newADD.mPaintMinus.setColor(Color.parseColor("#57C7D3"));
                }
                else if(position==2){
                        card_view.setCardBackgroundColor(Color.parseColor("#F3CC02"));
                        pica.setBorderColor(Color.parseColor("#F3CC02"));
                        newADD.mPaintBg.setColor(Color.parseColor("#F3CC02"));
                        newADD.mPaintMinus.setColor(Color.parseColor("#F3CC02"));
                }
                newADD.setTextNum(so);



                JSONArray pic=((JSONObject) offers.get(position)).getJSONArray("img");
                String picas=pic.get(0).toString();
                Picasso.with(context)
                        .load(picas)
                        .into( ((CircularImageView) itemView.findViewById(R.id.pica)));

              //  ((CircularImageView) itemView.findViewById(R.id.pica)).setText(points+"% off on "+json.getString("name"));
               ((TextViewModernM) itemView.findViewById(R.id.descrip)).setText(  ((JSONObject) offers.get(position)).getString("detail"));
               ((TextViewModernM) itemView.findViewById(R.id.unity)).setText(  ((JSONObject) offers.get(position)).getString("weight"));
               ((TextViewModernM) itemView.findViewById(R.id.store_name)).setText(  ((JSONObject) offers.get(position)).getString("retailer_name"));
                ((TextViewModernM) itemView.findViewById(R.id.price)).setText("₹"+((JSONObject) offers.get(position)).getInt("price")+"");
//                ((AddOrRemoveCart)itemView.findViewById(R.id.add_or_remove_cart)).addOnItemClickListner(new AddOrRemoveCart.ItemsClickListener() {
//                    @Override
//                    public int addItemClicked(int position) {
//                        Toast.makeText(context, "Added To Cart", Toast.LENGTH_SHORT).show();
//                        int quantity = 0;
//
//                        try {
//
//                            quantity = cart.addToCart(((JSONObject) offers.get(position)));
//                        } catch (Exception e) {
//
//                        }
//
//                        return quantity;
//                    }
//                    @Override
//                    public int removeItemClicked(View v,int position) {
//                        Toast.makeText(context, "Removed From Cart", Toast.LENGTH_SHORT).show();
//                        int quantity = 0;
//
//                        try {
//                            quantity = cart.removeFromCart(((JSONObject) offers.get((position))).getString("detail"));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        return quantity;
//                    }
//                }, position, cart.findIfOfferAlreadyExistsInCart(((JSONObject) offers.get((position))).getString("detail")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            container.addView(itemView);
            return itemView;
        }

        // Removes the page from the container for the given position.
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(final View view, final Object object) {
            return view.equals(object);
        }

    }
}
