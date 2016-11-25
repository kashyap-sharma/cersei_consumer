package co.jlabs.cersei_retailer;

/**
 * Created by ` on 19-12-2015.
 */
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.toolbox.ImageLoader;
import java.util.ArrayList;
import co.jlabs.cersei_retailer.custom_components.AddOrRemoveCart;
import co.jlabs.cersei_retailer.custom_components.Class_Cart;
import co.jlabs.cersei_retailer.custom_components.Class_retailer;
import co.jlabs.cersei_retailer.custom_components.MyImageView;
import co.jlabs.cersei_retailer.custom_components.Sqlite_cart;
import co.jlabs.cersei_retailer.custom_components.TextView_Triangle;


public class Adapter_Cart extends BaseAdapter {
    private Context context;
    ImageLoader imageLoader;
    ArrayList<Class_Cart> offers_list;
    ArrayList<Class_Cart> retailer_list;
    Sqlite_cart cart;
    Fragment_Cart.Total_item_in_cart_text_handler totalItemInCartTextHandler;
    private int lastPosition = -1;
    Animation animation;
    String[] str;

    public Adapter_Cart(Context context,ArrayList<Class_Cart>  offers_list,ArrayList<Class_Cart>  retailer_list,Fragment_Cart.Total_item_in_cart_text_handler handler) {
        this.context = context;
        cart=new Sqlite_cart(context);
        this.offers_list=retailer_list;
        this.retailer_list=retailer_list;
        this.totalItemInCartTextHandler=handler;
        imageLoader = AppController.getInstance().getImageLoader();
    }

    static class ViewHolder
    {
        TextView retailer_name;
        TextView minOrder;
        TextView total,add_more;
        LinearLayout min_back,father;
        Button call;
        TextView_Triangle points;
        MyImageView Pic;
        AddOrRemoveCart addOrRemoveCart;
        View Close;
    }

    public View getView(int position, View gridView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ViewHolder viewHolder;

        if (gridView == null) {

            gridView = inflater.inflate(R.layout.new_adap_cart, null);
            viewHolder = new ViewHolder();
            viewHolder.retailer_name = (TextView) gridView.findViewById(R.id.retailer_name);
            viewHolder.minOrder = (TextView) gridView.findViewById(R.id.min_order);
            viewHolder.total = (TextView) gridView.findViewById(R.id.total);
            viewHolder.call = (Button) gridView.findViewById(R.id.call);
            viewHolder.add_more = (TextView) gridView.findViewById(R.id.am);
            viewHolder.min_back = (LinearLayout) gridView.findViewById(R.id.min_order_back);
            viewHolder.father = (LinearLayout) gridView.findViewById(R.id.father);




//            viewHolder.points = (TextView_Triangle) gridView.findViewById(R.id.points);
//            viewHolder.Pic= (MyImageView) gridView.findViewById(R.id.pic);
//            viewHolder.addOrRemoveCart= (AddOrRemoveCart) gridView.findViewById(R.id.add_or_remove_cart);
            //viewHolder.Close=gridView.findViewById(R.id.close);
            gridView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) gridView.getTag();
        }


        viewHolder.retailer_name.setText(offers_list.get(position).retailer_name);
        viewHolder.minOrder.setText(offers_list.get(position).retailer_id);
        ArrayList<Class_Cart> tots = cart.getRetailerdata(offers_list.get(position).retailer_id);
        final int N=tots.size();
        final TextView[] myTextViews = new TextView[N];
        for (int i = 0; i < N; i++) {
            // create a new textview
            final TextView rowTextView = new TextView(context);

            // set some properties of rowTextView or something
            rowTextView.setText(tots.get(i).product_name);

            // add the textview to the linearlayout
            viewHolder.father.addView(rowTextView);

            // save a reference to the textview for later
            myTextViews[i] = rowTextView;
        }
        Log.e("somedatat",":"+offers_list.get(position).retailer_id);





//        viewHolder.price.setText("Rs " + offers_list.get(position).price);
//        viewHolder.points.setText("" + context.getResources().getString(R.string.rating) + offers_list.get(position).cashback);
//        viewHolder.Pic.setImageUrl(offers_list.get(position).img, imageLoader);
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