package co.jlabs.cersei_retailer;

import android.animation.Animator;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sackcentury.shinebuttonlib.ShineButton;

import org.json.JSONException;
import org.json.JSONObject;

import co.jlabs.cersei_retailer.LoadingCool.CoolAnimView;

import co.jlabs.cersei_retailer.changes.*;
import co.jlabs.cersei_retailer.custom_components.LocationPopup;
import co.jlabs.cersei_retailer.custom_components.NoInternetDialogBox;
import co.jlabs.cersei_retailer.custom_components.PagerSlidingStrip;
import co.jlabs.cersei_retailer.custom_components.PagerSlidingStrip.IconTabProvider;
import co.jlabs.cersei_retailer.custom_components.Popup_Filter;
import co.jlabs.cersei_retailer.custom_components.SmallBang;
import co.jlabs.cersei_retailer.custom_components.SmallBangListener;
import co.jlabs.cersei_retailer.custom_components.TabsView;


public class MainDashboard extends FragmentActivity implements View.OnClickListener,FragmentsEventInitialiser,Animation.AnimationListener,SlidingDrawer.OnDrawerOpenListener, LocationPopup.onLocationSelected,NoInternetDialogBox.onReloadPageSelected{
    static final int ITEMS = 3;
    static final int page_offers=0;
    static final int page_points=1;
    static final int page_barcode=3;
    static final int page_cart=2;

    MyAdapter mAdapter;
    DeactivatableViewPager mPager;
    PagerSlidingStrip strip;
    private SmallBang mSmallBang;
    View tab_point,tab_cart;
    LinearLayout selectLocation;
    LocationPopup dialog;
    Popup_Filter filter_popup;
    JSONObject json=null;
    FragmentEventHandler ViewpagerHandler=null,CameraHandler=null,ScoreHandler=null,CartHandler=null;
    AlertDialog loadingDialogBox;
    Boolean loaded_first_page,loaded_second_page,loaded_third_page,loaded_forth_page;
    Boolean was_success_loaded_first_page,was_success_loaded_second_page,was_success_loaded_third_page,was_success_loaded_forth_page;

    String url = StaticCatelog.geturl()+"cersei/consumer/location";

    NoInternetDialogBox noInternetDialogBox=null;

    View filter_Icon;




    //Drawer
    private ViewGroup hiddenPanel;
    private boolean isPanelShown;
    private ImageView imageView;
    private LinearLayout handle;
    private LinearLayout fift;
    private LinearLayout fif;
    private LinearLayout fi;
    private LinearLayout f;
    private LinearLayout fifth;
    private LinearLayout content;
    private SlidingDrawer slidingDrawer;
    Animation animation = null;
    //Drawer



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.app_bar_dashboard1);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mSmallBang = SmallBang.attach2Window(this);
        selectLocation= (LinearLayout)findViewById(R.id.location);
       // DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
        loadingDialogBox=new ProgressDialog(this);
        loadingDialogBox.setTitle("Loading");
        loadingDialogBox.setMessage("Please Wait");
        drwawerInitView();

        //Dialog Box
        RelativeLayout layout = new RelativeLayout(MainDashboard.this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        // layout.addView(new CoolAnimView(MainDashboard.this), params);
        loadingDialogBox = new AlertDialog.Builder(MainDashboard.this,R.style.Translucent_NoTitle)
                .setView(layout)
                .create();

        selectLocation.setOnClickListener(this);
       // ((TextView)selectLocation.findViewById(R.id.text_location)).setText(StaticCatelog.getStringProperty(this, "location"));
        ((TextView)selectLocation.findViewById(R.id.text_location)).setText("Menu");

//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.findViewById(R.id.lay_prof).setOnClickListener(this);
//        navigationView.findViewById(R.id.lay_myorders).setOnClickListener(this);
//        navigationView.findViewById(R.id.lay_myaddresses).setOnClickListener(this);
//        navigationView.findViewById(R.id.lay_savedcards).setOnClickListener(this);
//        navigationView.findViewById(R.id.lay_coupons).setOnClickListener(this);
//        navigationView.findViewById(R.id.lay_settings).setOnClickListener(this);

       // filter_Icon=findViewById(R.id.filter_icon);

        mAdapter = new MyAdapter(getSupportFragmentManager());
        mPager = (DeactivatableViewPager) findViewById(R.id.myviewpager);
        mPager.setPagingEnabled(false);

        Log.i("Simple List View","Touch dd "+mPager.getId());
        strip = (PagerSlidingStrip) findViewById(R.id.tabs);
        mPager.setAdapter(mAdapter);
        strip.setViewPager(mPager);
        //strip.manageFilterIcon(filter_Icon);
        mPager.setOffscreenPageLimit(3);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == page_offers) {
                    //redText(tab_point);
                    ViewpagerHandler.adjustCameraOrViewPager(true);
                }
//                else
//                    ViewpagerHandler.adjustCameraOrViewPager(false);
                if (position == page_points) {
                   // redText(tab_point);
                    ScoreHandler.adjustCameraOrViewPager(true);
                }
//                if (position == page_barcode)
//                    CameraHandler.adjustCameraOrViewPager(true);
//                else
//                    CameraHandler.adjustCameraOrViewPager(false);
                if (position == page_cart) {
                    //(tab_point);
                    if(CartHandler!=null)
                    CartHandler.adjustCameraOrViewPager(true);
                    ((TabsView) tab_cart).removeCartNotification();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tab_point = strip.returntab(page_points);
        tab_cart = strip.returntab(page_cart);
       // filter_Icon.setOnClickListener(this);
        update_ui_for_location();
    }


    //Drawer
    private void drwawerInitView() {
        //imageView = (ImageView) findViewById(R.id.imageView);
        handle = (LinearLayout) findViewById(R.id.handle);

        fift = (LinearLayout) findViewById(R.id.fift);
        fift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("click","fift");
            }
        });
        fif = (LinearLayout) findViewById(R.id.fif);
        fif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("click","fif");
            }
        });
        fi = (LinearLayout) findViewById(R.id.fi);
        fi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("click","fift");
            }
        });
        f = (LinearLayout) findViewById(R.id.f);
        fifth = (LinearLayout) findViewById(R.id.fifth);
        content = (LinearLayout) findViewById(R.id.content);
        slidingDrawer= (SlidingDrawer) findViewById(R.id.slidingDrawer);
        animation= AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.shake_drawer);


        slidingDrawer.setOnDrawerScrollListener(new SlidingDrawer.OnDrawerScrollListener() {
            @Override
            public void onScrollStarted() {
               // initialize();
            }

            @Override
            public void onScrollEnded() {
                handle.setVisibility(View.GONE);
            }
        });
        slidingDrawer.setOnDrawerOpenListener(this);
        content.setOnTouchListener(onThumbTouch );



        /*
        animation= AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.shake);
        animation.setAnimationListener(this);
        slidingDrawer = (SlidingDrawer) findViewById(R.id.slidingDrawer);
        final Handler ha = new Handler();
        fifth.performClick();
        content.animate().y(100).setStartDelay(1000).start();
        fifth.setY(0);
        f.setY(0);
        f.animate().y(100).start();
        fifth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ha.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        fifth.startAnimation(animation);
                        ha.postDelayed(this, 1000);
                        fifth.animate().y(100).start();

                        fif.animate().y(100).start();
                        fifth.animate().y(100).start();
                    }
                }, 1000);

            }
        });
*/
    }

    View.OnTouchListener onThumbTouch = new View.OnTouchListener() {
        float previouspoint = 0 ;
        float startPoint=0;
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch(v.getId()) {
                case R.id.content: // Give your R.id.sample ...
                    switch(event.getAction()){
                        case MotionEvent.ACTION_UP:
                            startPoint=event.getX();
                            if (slidingDrawer.isOpened()) {
                                slidingDrawer.close();
                            }
                            System.out.println("Action up,..."+event.getX());
                            break;
                        case MotionEvent.ACTION_MOVE:

                            break;
                        case MotionEvent.ACTION_CANCEL:
                            previouspoint=event.getX();
                            if(previouspoint > startPoint){
                                //Right side swipe
                            }else{
                                // Left side swipe
                            }
                            break;
                    }
                    break;
            }
            return true;
        }
    };



    @Override
    public void onAnimationEnd(Animation animations) {
        // Take any action after completing the animation

        // check for fade in animation
        if (animations == animation) {
            Toast.makeText(getApplicationContext(), "Animation Stopped",
                    Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onAnimationStart(Animation animation) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onDrawerOpened() {
        Log.i("Myapp","Drawer open");
       // handle.setVisibility(View.GONE);
//        slidingDrawer.setLayoutParams(new DrawerLayout.LayoutParams(DrawerLayout.LayoutParams.MATCH_PARENT, DrawerLayout.LayoutParams.WRAP_CONTENT));
        showall();
        fifth.animate()
                .translationY(0)
                .setDuration(450).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                content.setBackgroundColor(Color.parseColor("#345E7C"));
            }

            @Override
            public void onAnimationEnd(Animator anim) {
                fifth.startAnimation(animation);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        })
                .setStartDelay(0);
        f.animate()
                .translationY(0)
                .setDuration(600).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                content.setBackgroundColor(Color.parseColor("#6B5C7A"));
            }

            @Override
            public void onAnimationEnd(Animator anim) {
                f.startAnimation(animation);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        })

                .setStartDelay(250);
        fi.animate()
                .translationY(0)
                .setDuration(450).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                content.setBackgroundColor(Color.parseColor("#BF6A83"));
            }

            @Override
            public void onAnimationEnd(Animator anim) {
                fi.startAnimation(animation);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        })
                .setStartDelay(500);
        fif.animate()
                .translationY(0)
                .setDuration(450).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                content.setBackgroundColor(Color.parseColor("#F56D7F"));

            }

            @Override
            public void onAnimationEnd(Animator anim) {
                fif.startAnimation(animation);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        })

                .setStartDelay(750);
        fift.animate()
                .translationY(0)
                .setDuration(450).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                content.setBackgroundColor(Color.parseColor("#F7B295"));

            }

            @Override
            public void onAnimationEnd(Animator anim) {
                fift.startAnimation(animation);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        })

                .setStartDelay(1000);
    }

    public void initialize()
    {

        hideall();
        fifth.setY(-280);
        f.setY(-280);
        fi.setY(-280);
        fif.setY(-280);
        fift.setY(-280);
    }

    public void hideall()
    {
        fifth.setVisibility(View.INVISIBLE);
        f.setVisibility(View.INVISIBLE);
        fi.setVisibility(View.INVISIBLE);
        fif.setVisibility(View.INVISIBLE);
        fift.setVisibility(View.INVISIBLE);
    }

    public void showall()
    {
        fifth.setVisibility(View.VISIBLE);
        f.setVisibility(View.VISIBLE);
        fi.setVisibility(View.VISIBLE);
        fif.setVisibility(View.VISIBLE);
        fift.setVisibility(View.VISIBLE);
    }

    //Drawer
    @Override
    public void onBackPressed() {
        if (slidingDrawer.isOpened()) {
            slidingDrawer.close();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //ok
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        String s ="";
//        if (id == R.id.lay_prof) {
//            s="Profile";
//        } else if (id == R.id.lay_myorders) {
//            s="My Orders";
//        } else if (id == R.id.lay_myaddresses) {
//            s="My Addresses";
//        } else if (id == R.id.lay_savedcards) {
//            s="Saved Cards";
//        } else if (id == R.id.lay_coupons) {
//            s="Coupons";
//        } else if (id == R.id.lay_settings) {
//            s="Settings";
//        }
         if (id == R.id.location) {
            //download_locations();

            //develope

        }
//        else if(id==R.id.filter_icon)
//        {
//            createFilterPopup();
//        }
        if(s!="")
        {
            Toast.makeText(this, "Clicked " + s, Toast.LENGTH_SHORT).show();

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public void registerMyevent(int position, FragmentEventHandler eventHandler) {
        Log.i("Myapp", "Successfully registered event for " + position);
        if(position==page_offers)
            ViewpagerHandler=eventHandler;
        else if(position==page_points)
            ScoreHandler=eventHandler;
        else if(position==page_barcode)
            CameraHandler=eventHandler;
        else if(position==page_cart)
            CartHandler=eventHandler;
    }

    @Override
    public void MyloadingCompleted(int position,Boolean successfull) {
        switch (position)
        {
            case page_offers: loaded_first_page=true; was_success_loaded_first_page=successfull; break;
            case page_points: loaded_second_page=true; was_success_loaded_second_page=successfull; break;
            //  case page_barcode: loaded_third_page=true; was_success_loaded_third_page=successfull; break;
            case page_cart: loaded_forth_page=true; was_success_loaded_forth_page=successfull; break;
        }
        if(loaded_first_page&&loaded_second_page&&loaded_forth_page)
        {
            end_update_ui_for_location();
        }
    }

    @Override
    public void updateCart(Boolean add) {
        ((TabsView)tab_cart).giveCartNotification(add);

    }

    @Override
    public void Reload_No_Internet() {
        update_ui_for_location_withevent(StaticCatelog.getStringProperty(this, "area"),StaticCatelog.getStringProperty(this, "location"));
    }


    public class MyAdapter extends FragmentPagerAdapter implements IconTabProvider {

        private int tabIcons[] = {R.string.ico_home,R.string.rating,R.string.ico_cart};


        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);

        }

        @Override
        public int getCount() {
            return ITEMS;
        }


        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case page_offers :
                    Fragment_Offers firstPage = Fragment_Offers.init(position);
                    firstPage.addInitialisationEvent(MainDashboard.this);
                    return firstPage;
                case page_points :
                    co.jlabs.cersei_retailer.changes.Fragment_Points pointsFragment=  co.jlabs.cersei_retailer.changes.Fragment_Points.init(position);
                   // Fragment_Points pointsFragment = Fragment_Points.init(position);
                    pointsFragment.addInitialisationEvent(MainDashboard.this);
                    return pointsFragment;
//                case page_barcode :
//                    Fragment_Barcode fragment= Fragment_Barcode.init(position);
//                    fragment.addInitialisationEvent(MainDashboard.this);
//                    return fragment;
                case page_cart:
                    Fragment_Cart cartFragment = Fragment_Cart.init(position);
                    cartFragment.addInitialisationEvent(MainDashboard.this);
                    return cartFragment;

                default:
                    Fragment_Cart cartFr = Fragment_Cart.init(position);
                    cartFr.addInitialisationEvent(MainDashboard.this);
                    return cartFr;
            }
        }

        @Override
        public int getPageIconResId(int position) {
            return tabIcons[position];
        }

    }

//    public void redText(View view){
//        //((TextView)tab_point).setTextColor(0xFFCD8BF8);
//        mSmallBang.bang(view, 50, new SmallBangListener() {
//            @Override
//            public void onAnimationStart() {
//            }
//
//            @Override
//            public void onAnimationEnd() {
//                // Toast.makeText(MainDashboard.this,"Added",Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Myapp", "On Resume Called");
        if(CameraHandler!=null&&ViewpagerHandler!=null)
        {
            ViewpagerHandler.adjustCameraOrViewPager(true);
            CameraHandler.adjustCameraOrViewPager(false);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        if(CameraHandler!=null&&ViewpagerHandler!=null)
        {
            ViewpagerHandler.adjustCameraOrViewPager(false);
            CameraHandler.adjustCameraOrViewPager(false);
        }
        super.startActivity(intent);
    }

    public void update_ui_for_location_withevent(String Area,String location)
    {   Log.e("hiis",""+Area+location);
        ViewpagerHandler.startLoadbylocation(Area,location);
//        CameraHandler.startLoadbylocation(Area,location);
        ScoreHandler.startLoadbylocation(Area,location);
        CartHandler.startLoadbylocation(Area,location);
        update_ui_for_location();
    }

    public void update_ui_for_location()
    {

        loadingDialogBox.show();
        reset_loaded_fragments();


    }

    public void reset_loaded_fragments()
    {
        loaded_first_page=false;
        loaded_second_page=false;
        //  loaded_third_page=false;
        loaded_forth_page=false;
        was_success_loaded_first_page=false;
        was_success_loaded_second_page=false;
        // was_success_loaded_third_page=false;
        was_success_loaded_forth_page=false;
    }

    public void end_update_ui_for_location()
    {
        String s="";
        loadingDialogBox.dismiss();
        if(!(was_success_loaded_first_page&&was_success_loaded_second_page&&was_success_loaded_forth_page))
        {
            if(!was_success_loaded_first_page)
                s= s+" First ";
            if(!was_success_loaded_second_page)
                s=s+" Second ";
            if(!was_success_loaded_forth_page)
                s=s+" Third ";
//            if(!was_success_loaded_forth_page)
//                s=s+" Fourth ";
            Toast.makeText(this,"An Error occurred during loading data for page:"+s,Toast.LENGTH_SHORT).show();

            if(noInternetDialogBox==null)
                noInternetDialogBox= new NoInternetDialogBox(this, R.style.alert_dialog);
            noInternetDialogBox.setUpLayout(this);

        }
    }

    public void getLocation()
    {
        int success;
        try {
            success = json.getInt("success");
        } catch (JSONException e) {
            success=0;
        }
        //   if(dialog==null)
        dialog = new LocationPopup(this, R.style.alert_dialog);

        if(success==1)
        {   if(!dialog.isShowing())
            dialog.BuildDialog(MainDashboard.this, json);
        }
        else
        {
            Toast.makeText(this,"Invalid Data Received",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void update_location(String Area,String location) {
        Log.e("hii",""+Area+location);
       // ((TextView)findViewById(R.id.text_location)).setText(location);
        ((TextView)findViewById(R.id.text_location)).setText("Menu");
        StaticCatelog.setStringProperty(this, "area", Area);
//        StaticCatelog.setStringProperty(this, "location", "A Block Saket");
        StaticCatelog.setStringProperty(this, "location", "Menu");
        update_ui_for_location_withevent(Area,location);
    }


    private void download_locations() {

        String tag_json_obj = "json_obj_req_get_locations";

        Log.i("Myapp", "Calling url " + url);
        if(json==null) {
            Toast.makeText(this,"Fetching Locations Please Wait...",Toast.LENGTH_SHORT).show();
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    url, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(final JSONObject response) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    json = response;
                                    getLocation();
                                }
                            });

                        }
                    }, new Response.ErrorListener() {


                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("Error", "Error: " + error.getMessage());
                    Toast.makeText(MainDashboard.this,"Error! During fetching locations",Toast.LENGTH_SHORT).show();
                }
            });
            AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        }
        else
        {
            getLocation();
        }
    }

    public void createFilterPopup()
    {
        // if(filter_popup==null)
        //      filter_popup = new Popup_Filter(this, R.style.alert_dialog);
        //      filter_popup.setUpLayout();
        Toast.makeText(this,"Filter To be Added",Toast.LENGTH_SHORT).show();
    }


}
