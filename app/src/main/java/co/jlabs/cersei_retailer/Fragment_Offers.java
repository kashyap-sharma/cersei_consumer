package co.jlabs.cersei_retailer;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import co.jlabs.cersei_retailer.custom_components.AutoScrollViewPager;
import co.jlabs.cersei_retailer.custom_components.Sqlite_cart;
import co.jlabs.cersei_retailer.custom_components.transforms.*;


public class Fragment_Offers extends Fragment implements FragmentEventHandler {
    int fragVal;
  //  AutoScrollViewPager vpPager=null;
    FragmentsEventInitialiser eventInitialiser=null;
    String url = StaticCatelog.geturl()+"cersei/consumer/show_offers";
    JSONArray json=null;
    View header;
    JSONArray json_retailer;
    Context context;
    Sqlite_cart cart;
    RecyclerView recyclerView;

    static Fragment_Offers init(int val) {
        Fragment_Offers truitonFrag = new Fragment_Offers();
        // Supply val input as an argument.
        Bundle args = new Bundle();
        args.putInt("val", val);
        truitonFrag.setArguments(args);
        return truitonFrag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragVal = getArguments() != null ? getArguments().getInt("val") : 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.firstpage, container,
                false);
        cart = new Sqlite_cart(getContext());
        header = inflater.inflate(R.layout.header_xml, null, false);

        recyclerView = (RecyclerView) layoutView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position = 1;
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        return layoutView;
    }

    @Override
    public void adjustCameraOrViewPager(boolean on) {
/*        if(vpPager!=null)
        if(on)
            vpPager.startAutoScroll();
        else
            vpPager.stopAutoScroll();*/
    }

    @Override
    public void startLoadbylocation(String Area,String location) {
        json=null;
        download_offers(Area,location);
        download_retailer(Area,location);

    }

 /*   class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;
        ImageLoader imageLoader;
        JSONArray banners;
        int len=0;

        public CustomPagerAdapter(Context context,JSONArray banners) {
            mContext = context;
            this.banners=banners;
            len=banners.length();
            mLayoutInflater = LayoutInflater.from(mContext);
            imageLoader = AppController.getInstance().getImageLoader();
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // Inflate the layout for the page
            View itemView = mLayoutInflater.inflate(R.layout.header_imageview, container, false);
            NetworkImageView Pic= (NetworkImageView) itemView.findViewById(R.id.pic);
            try {
                Pic.setImageUrl(((JSONObject)banners.get(position%len)).getString("img"), imageLoader);
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            if(position%3==0)
//            {
//                Pic.setImageUrl("http://i.imgur.com/QbRBD6M.jpg", imageLoader);
//            }
//            else if(position%3==1)
//            {
//                Pic.setImageUrl("http://grofers.com/blog/wp-content/uploads/2015/02/Reliance-Fresh2-1.jpg", imageLoader);
//            }
//            else
//            {
//                Pic.setImageUrl("http://4.bp.blogspot.com/-jyuw-AWcTEs/VeAa6qVm0EI/AAAAAAAAHIM/eqOSuuYKRJc/s400/rakhi_banner.jpg", imageLoader);
//            }
            container.addView(itemView);
            return itemView;
        }

        // Removes the page from the container for the given position.
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
*/
    public void addInitialisationEvent(FragmentsEventInitialiser eventInitialiser)
    {
        this.eventInitialiser=eventInitialiser;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(eventInitialiser!=null)
            eventInitialiser.registerMyevent(fragVal,this);
        if(json==null)
            download_offers(StaticCatelog.getStringProperty(getContext(),"area"),StaticCatelog.getStringProperty(getContext(),"location"));
            download_retailer(StaticCatelog.getStringProperty(getContext(),"area"),StaticCatelog.getStringProperty(getContext(),"location"));


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


    private void download_offers(String Area,String location) {

        String tag_json_obj = "json_obj_req_get_offers";
        try {
            location=URLEncoder.encode(location, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Area = URLEncoder.encode(Area);

        url=StaticCatelog.geturl()+"cersei/consumer/list_offers?location=Dwarka&area=sector%202";
        //url=StaticCatelog.geturl()+"cersei/consumer/list_offers?location=Dwarka"+Area+"&location="+location;

        Log.i("Myapp", "Calling url " + url);
        if(json==null) {
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    url, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(final JSONObject response) {
                                    //json = response;
                            try {
                                json= (JSONArray) response.getJSONArray("data");
                                show_offers(json);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                tellThatLoadedSuccessfully(false);
                            }
                        }
                    }, new Response.ErrorListener() {


                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("Error", "Error: " + error.getMessage());
                    tellThatLoadedSuccessfully(false);
                }
            });
            AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        }
//        else
//        {
//            try {
//               // show_offers(json.getJSONArray("banners"),json.getJSONArray("offers"));
//            } catch (JSONException e) {
//                e.printStackTrace();
//                tellThatLoadedSuccessfully(false);
//            }
//        }
    }



    private void download_retailer(String Area,String location) {

        String tag_json_obj = "json_obj_req_get_offers";
        try {
            location=URLEncoder.encode(location, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Area = URLEncoder.encode(Area);

        url=StaticCatelog.geturl()+"cersei/consumer/retailer/list_offers?location=Dwarka&area=sector%202";
        //url=StaticCatelog.geturl()+"cersei/consumer/list_offers?location=Dwarka"+Area+"&location="+location;

        Log.i("Myapp", "Calling url " + url);
        if(json==null) {
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    url, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(final JSONObject response) {
                            //json = response;
                            try {
                                json= (JSONArray) response.getJSONArray("data");
                                save_retailer(json);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                tellThatLoadedSuccessfully(false);
                            }
                        }
                    }, new Response.ErrorListener() {


                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("Error", "Error: " + error.getMessage());
                    tellThatLoadedSuccessfully(false);
                }
            });
            AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        }
//        else
//        {
//            try {
//               // show_offers(json.getJSONArray("banners"),json.getJSONArray("offers"));
//            } catch (JSONException e) {
//                e.printStackTrace();
//                tellThatLoadedSuccessfully(false);
//            }
//        }
    }

    public void show_offers(JSONArray offers)
    {

        final RecyclerAdapter elementsAdapter = new RecyclerAdapter(getContext(),offers,eventInitialiser);
        elementsAdapter.setHeader(header);

/*        CustomPagerAdapter adapter = new CustomPagerAdapter(getContext(),banners);

        vpPager = (AutoScrollViewPager) header.findViewById(R.id.pager);

        vpPager.setAdapter(adapter);
        vpPager.setCurrentItem(500);
        vpPager.setAutoScrollDurationFactor(8);
        vpPager.setSwipeScrollDurationFactor(3);
        vpPager.setInterval(2000);
        vpPager.startAutoScroll();
        vpPager.setPageTransformer(true, new ZoomOutTranformer());*/

        recyclerView.setAdapter(elementsAdapter);
        tellThatLoadedSuccessfully(true);

    }
    public void save_retailer(JSONArray offers)

    {

        int position= offers.length();
        Log.e("Length",""+offers.length());

        try {
            for(int i=0;i<position;i++){
                cart.addRetailer( offers.getJSONObject(i));
                Log.e("Lengths",""+i);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


/*        CustomPagerAdapter adapter = new CustomPagerAdapter(getContext(),banners);

        vpPager = (AutoScrollViewPager) header.findViewById(R.id.pager);

        vpPager.setAdapter(adapter);
        vpPager.setCurrentItem(500);
        vpPager.setAutoScrollDurationFactor(8);
        vpPager.setSwipeScrollDurationFactor(3);
        vpPager.setInterval(2000);
        vpPager.startAutoScroll();
        vpPager.setPageTransformer(true, new ZoomOutTranformer());*/



    }
}
