package co.jlabs.cersei_retailer.changes;

/**
 * Created by Pradeep on 12/25/2015.
 */
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.gigamole.library.ArcProgressStackView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import co.jlabs.cersei_retailer.AdapterPointsEarned;
import co.jlabs.cersei_retailer.AdapterReward_Redeemed;
import co.jlabs.cersei_retailer.AppController;
import co.jlabs.cersei_retailer.FragmentEventHandler;
import co.jlabs.cersei_retailer.FragmentsEventInitialiser;
import co.jlabs.cersei_retailer.R;
import co.jlabs.cersei_retailer.Rounded.MyIconButton;
import co.jlabs.cersei_retailer.Rounded.MyIconFonts;
import co.jlabs.cersei_retailer.StaticCatelog;
import co.jlabs.cersei_retailer.activity.AchievementsActivity;
import co.jlabs.cersei_retailer.activity.LoginNum;
import co.jlabs.cersei_retailer.custom_components.ButtonModarno;
import co.jlabs.cersei_retailer.custom_components.PagerSlidingStripPoints;
import co.jlabs.cersei_retailer.custom_components.SampleListView;
import co.jlabs.cersei_retailer.custom_components.ScrollTabHolder;
import co.jlabs.cersei_retailer.custom_components.SmallBangListener;
import co.jlabs.cersei_retailer.custom_components.StarBang;
import co.jlabs.cersei_retailer.custom_components.TextViewModernM;

public class Fragment_Points extends Fragment  implements ScrollTabHolder, ViewPager.OnPageChangeListener,FragmentEventHandler {
    int fragVal;


    public static final boolean NEEDS_PROXY = Integer.valueOf(Build.VERSION.SDK_INT).intValue() < 11;
    public final static int MODEL_COUNT = 1;
    private int[] mStartColors = new int[MODEL_COUNT];
    private int[] mEndColors = new int[MODEL_COUNT];
    private TextViewModernM name,level,next;
    private MyIconFonts cashback;
    private LinearLayout achieve;
    private RelativeLayout rl;
    private FrameLayout pica1,pica;
    private ButtonModarno loginf;
    private ProgressBar circular_progress_bar;

    //    private View mHeader;
//
//    private PagerSlidingStripPoints mPagerSlidingTabStrip;
//    private ViewPager mViewPager;
//    private PagerAdapter mPagerAdapter;
//
//    private int mMinHeaderHeight;
//    private int mHeaderHeight;
//    private int mMinHeaderTranslation;
//    private StarBang mSmallBang;
//
//    private TextView info;
//    private int mLastY;
    private ArcProgressStackView mArcProgressStackView;

    FragmentsEventInitialiser eventInitialiser=null;


    private RectF mRect1 = new RectF();
    private RectF mRect2 = new RectF();
    private AccelerateDecelerateInterpolator mSmoothInterpolator;

 //   View from_balance,to_balance,from_point,to_point,from_rating,to_rating;
    String url = StaticCatelog.geturl()+"cersei/consumer/reward?userid=user_1";
    JSONObject json=null;
    int x=0,scrollx=0;


   public static Fragment_Points init(int val) {
        Fragment_Points truitonFrag = new Fragment_Points();
        // Supply val input as an argument.
        Bundle args = new Bundle();
        args.putInt("val", val);
        truitonFrag.setArguments(args);
        return truitonFrag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // mSmallBang = StarBang.attach2Window(getActivity());
        fragVal = getArguments() != null ? getArguments().getInt("val") : 2;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.new_cashbacks, container,
                false);
        name=(TextViewModernM)layoutView.findViewById(R.id.name);
        level=(TextViewModernM)layoutView.findViewById(R.id.level);
        next=(TextViewModernM)layoutView.findViewById(R.id.next);
        cashback=(MyIconFonts)layoutView.findViewById(R.id.cashback);
        rl=(RelativeLayout)layoutView.findViewById(R.id.rl);
        loginf=(ButtonModarno) layoutView.findViewById(R.id.loginf);
        achieve=(LinearLayout) layoutView.findViewById(R.id.achieve);
        pica1=(FrameLayout) layoutView.findViewById(R.id.pica1);
        pica=(FrameLayout) layoutView.findViewById(R.id.pica);
        circular_progress_bar=(ProgressBar) layoutView.findViewById(R.id.circular_progress_bar);
        achieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(StaticCatelog.getStringProperty(getContext(),"api_key")==null) {
                    Toast.makeText(getActivity(), "You need to be logged in to see this.",
                            Toast.LENGTH_LONG).show();
                }else{
                    Intent intent= new Intent(getContext(), AchievementsActivity.class);
                    startActivity(intent);
                }

            }
        });

        if(StaticCatelog.getStringProperty(getContext(),"api_key")==null) {
          rl.setVisibility(View.GONE);
          loginf.setVisibility(View.VISIBLE);
            pica1.setVisibility(View.VISIBLE);
            pica.setVisibility(View.GONE);
        }else{
            rl.setVisibility(View.VISIBLE);
            loginf.setVisibility(View.GONE);
            pica.setVisibility(View.VISIBLE);
            pica1.setVisibility(View.GONE);
        }
        loginf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getContext(), LoginNum.class);
                intent.putExtra("from","points");
                startActivity(intent);
            }
        });


        if ( StaticCatelog.getIntProperty(getContext(),"total_cashback")>0) {
            cashback.setText(getString(R.string.wallet)+" "+StaticCatelog.getIntProperty(getContext(),"total_cashback"));
        } else {
            cashback.setText(getString(R.string.wallet)+" 0");
        }

        Log.e("data@1",""+StaticCatelog.getIntProperty(getContext(),"total_cashback")%1000);
        Log.e("data@1",""+(1000-StaticCatelog.getIntProperty(getContext(),"total_cashback")/1000));
        Log.e("data@1",""+StaticCatelog.getIntProperty(getContext(),"total_cashback"));
        level.setText("Level "+(1+StaticCatelog.getIntProperty(getContext(),"total_cashback")/1000));
        next.setText("+"+(1000-StaticCatelog.getIntProperty(getContext(),"total_cashback")%1000)+" CB until next level");
        circular_progress_bar.setProgress(StaticCatelog.getIntProperty(getContext(),"total_cashback")%1000);

        final String[] bgColors = getResources().getStringArray(R.array.medical_express);
        final String[] endColors = getResources().getStringArray(R.array.default_preview);
        final String[] startColors = getResources().getStringArray(R.array.polluted_waves);
        mArcProgressStackView = (ArcProgressStackView) layoutView.findViewById(R.id.apsv);

        for (int i = 0; i < MODEL_COUNT; i++) {
            mStartColors[i] = Color.parseColor(startColors[i]);
            mEndColors[i] = Color.parseColor(endColors[i]);
        }

        final ArrayList<ArcProgressStackView.Model> models = new ArrayList<>();

        models.add(new ArcProgressStackView.Model("Circle", 0, Color.parseColor(bgColors[0]), mStartColors[0]));
        mArcProgressStackView.setModels(models);

        // Set animator listener
        mArcProgressStackView.setAnimatorUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(final ValueAnimator animation) {
                // Update goes here
                Log.d("onAnimationUpdate: ", String.valueOf(animation.getAnimatedValue()));
            }
        });
        mArcProgressStackView.setAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(final Animator animation) {
                Toast.makeText(getActivity(), "ANIMATION", Toast.LENGTH_SHORT).show();
            }
        });

        // Start apsv animation on start
        mArcProgressStackView.postDelayed(new Runnable() {
            @Override
            public void run() {
//                btnAnimate.performClick();
            }
        }, 333);
        mArcProgressStackView.setSweepAngle(50);
//        mSmoothInterpolator = new AccelerateDecelerateInterpolator();
//
//        mMinHeaderHeight = getResources().getDimensionPixelSize(R.dimen.min_header_height);
//        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
//        mMinHeaderTranslation = -mMinHeaderHeight;
//
//
//        to_balance=layoutView.findViewById(R.id.to_balance);
//        to_point=layoutView.findViewById(R.id.to_points);
//        to_rating=layoutView.findViewById(R.id.to_rating);
//        mHeader = layoutView.findViewById(R.id.header);
//        from_balance=mHeader.findViewById(R.id.totalbalance);
//        from_point=mHeader.findViewById(R.id.points);
//        from_rating=mHeader.findViewById(R.id.rating);
//        mHeader.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                ViewPagerEvent.adjustViewPager(event);
//                return false;
//            }
//        });

//        info = (TextView) layoutView.findViewById(R.id.info);
//
//        mPagerSlidingTabStrip = (PagerSlidingStripPoints) layoutView.findViewById(R.id.tabs);
//        mViewPager = (ViewPager) layoutView.findViewById(R.id.pager);
//        mViewPager.setOffscreenPageLimit(3);
//        mHeader.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
////
////                final int action = event.getAction();
////
////             //   SampleListView.this.getParent().requestDisallowInterceptTouchEvent(true);
////
////
////                //  SampleListView.this.getParent().getParent().requestDisallowInterceptTouchEvent(true);
////                //  vp.requestDisallowInterceptTouchEvent(false);
////               switch (action) {
////                    case MotionEvent.ACTION_DOWN:
////                    //    ((CustomViewPager) mViewPager).setPaging(false);
////                        Log.i("Simple List View", "Touch Down ");
////                        x= (int) event.getX();
////                        scrollx=((ViewPager) v.getParent().getParent()).getScrollX();
////                        break;
////
////                    case MotionEvent.ACTION_UP:
////                    //    ((CustomViewPager)mViewPager).setPaging(true);
////                        int finalscroll=scrollx-((ViewPager) v.getParent().getParent()).getScrollX();
////                        Log.i("Simple List View", "Touch Up " + finalscroll);
////
////                        //x=0;
////                        if(finalscroll>220||finalscroll<-220)
////                        {
////                            if(finalscroll>220)
////                                ((ViewPager)v.getParent().getParent()).setCurrentItem(0);
////                            else
////                                ((ViewPager)v.getParent().getParent()).setCurrentItem(2);
////                        }
////                        else
////                            ((ViewPager)v.getParent().getParent()).scrollBy(finalscroll,0);
////
////
////                        //	SampleListView.this.getParent().requestDisallowInterceptTouchEvent(false);
////                        break;
////                    case MotionEvent.ACTION_MOVE:
////                        Log.i("Simple List View", "MotionEvent.ACTION_MOVE "+(x - event.getX()));
////                      //  ((ViewPager)v.getParent().getParent()).scrollBy((int) (x - event.getX()), 0);
////
////                        break;
////                    case MotionEvent.ACTION_CANCEL:
////                        Log.i("Simple List View", "MotionEvent.ACTION_CANCEL");
////                        break;
////                }
////
////                return true;
//            }
//        });
        return layoutView;
    }


    @Override
    public void onPageScrollStateChanged(int arg0) {
        // nothing
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        if (positionOffsetPixels > 0) {
          //  int currentItem = mViewPager.getCurrentItem();

//            SparseArrayCompat<ScrollTabHolder> scrollTabHolders = mPagerAdapter.getScrollTabHolders();
//            ScrollTabHolder currentHolder;
//
//            if (position < currentItem) {
//                currentHolder = scrollTabHolders.valueAt(position);
//            } else {
//                currentHolder = scrollTabHolders.valueAt(position + 1);
//            }
//
//            if (NEEDS_PROXY) {
//                // TODO is not good
//                currentHolder.adjustScroll(mHeader.getHeight() - mLastY);
//                mHeader.postInvalidate();
//            } else {
//                currentHolder.adjustScroll((int) (mHeader.getHeight() + mHeader.getTranslationY()));
//            }
        }
    }

    @Override
    public void onPageSelected(int position) {
//        SparseArrayCompat<ScrollTabHolder> scrollTabHolders = mPagerAdapter.getScrollTabHolders();
//        ScrollTabHolder currentHolder = scrollTabHolders.valueAt(position);
//        if(NEEDS_PROXY){
//            //TODO is not good
//            currentHolder.adjustScroll(mHeader.getHeight()-mLastY);
//            mHeader.postInvalidate();
//        }else{
//            currentHolder.adjustScroll((int) (mHeader.getHeight() + mHeader.getTranslationY()));
//        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition) {
//        if (mViewPager.getCurrentItem() == pagePosition) {
//            int scrollY = getScrollY(view);
//            if(NEEDS_PROXY){
//                mLastY=-Math.max(-scrollY, mMinHeaderTranslation);
//                info.setText(String.valueOf(scrollY));
//                mHeader.scrollTo(0, mLastY);
//                mHeader.postInvalidate();
//            }else{
//                mHeader.setTranslationY(Math.max(-scrollY, mMinHeaderTranslation));
//            }
//
//            float ratio = clamp(mHeader.getTranslationY() / mMinHeaderTranslation, 0.0f, 1.0f);
//
//            interpolate( from_balance,to_balance, mSmoothInterpolator.getInterpolation(ratio));
//            interpolate(from_point,to_point, mSmoothInterpolator.getInterpolation(ratio));
//            interpolate(from_rating,to_rating, mSmoothInterpolator.getInterpolation(ratio));
//        }
    }

    @Override
    public void adjustScroll(int scrollHeight) {
        // nothing
    }

    public int getScrollY(AbsListView view) {
        View c = view.getChildAt(0);
        if (c == null) {
            return 0;
        }

        int firstVisiblePosition = view.getFirstVisiblePosition();
        int top = c.getTop();

        int headerHeight = 0;
        if (firstVisiblePosition >= 1) {
          //  headerHeight = mHeaderHeight;
        }

        return -top + firstVisiblePosition * c.getHeight() + headerHeight;
    }

    public static float clamp(float value, float max, float min) {
        return Math.max(Math.min(value, min), max);
    }

    @Override
    public void adjustCameraOrViewPager(boolean on) {
//        animateTextView(4145, 5273, (TextView) from_point);
//        ((TextView)to_point).setText("5273");
    }

    @Override
    public void startLoadbylocation(String Area,String location) {
        //here I will load Only first list that shows offers based on location
        if(json==null)
            download_information("");
        else
            tellThatLoadedSuccessfully(true);
    }


    private class PagerAdapter extends android.support.v4.view.PagerAdapter implements PagerSlidingStripPoints.IconTabProvider {

        String num_rewards;
        String num_points_earned;
        String num_points_redeemed;
        JSONArray rewards;
        JSONArray points_earned;
        JSONArray points_redeemed;

        private SparseArrayCompat<ScrollTabHolder> mScrollTabHolders;
        private ScrollTabHolder mListener;
        Context context;

        PagerAdapter(Context context,String num_rewards,String num_points_earned,String num_points_redeemed,JSONArray rewards,JSONArray points_earned,JSONArray points_redeemed)
        {
            mScrollTabHolders = new SparseArrayCompat<ScrollTabHolder>();
            this.context=context;
            this.num_rewards=num_rewards;
            this.num_points_earned=num_points_earned;
            this.num_points_redeemed=num_points_redeemed;
            this.rewards=rewards;
            this.points_earned=points_earned;
            this.points_redeemed=points_redeemed;
        }

        public int getCount() {
            return 3;
        }

        public Object instantiateItem(View collection, int position) {

            LayoutInflater inflater = (LayoutInflater) collection.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            SampleListView lv = new SampleListView(context,inflater,position);



            mScrollTabHolders.put(position, lv);
            if (mListener != null) {
                lv.setScrollTabHolder(mListener);
            }
            switch (position) {
                default:
                    lv.setAdapter(new AdapterReward_Redeemed(context, rewards,0));
                    break;
                case 0:
                    lv.setAdapter(new AdapterReward_Redeemed(context, rewards,0));
                    break;
                case 1:
                    lv.setAdapter(new AdapterPointsEarned(context, points_earned));
                    break;
                case 2:
                    lv.setAdapter(new AdapterReward_Redeemed(context, points_redeemed,1));
                    break;
            }

            ((ViewPager) collection).addView(lv, 0);

            return lv;
        }

        @Override
        public CharSequence getPageIconResId(int position) {
            switch (position)
            {
                case 0: return StaticCatelog.SpanIt(num_rewards,"Rewards");
                case 1: return StaticCatelog.SpanIt(num_points_earned,"Points Earned");
                case 2: return StaticCatelog.SpanIt(num_points_redeemed,"Points Redeemed");
                default:return StaticCatelog.SpanIt(num_rewards,"Rewards");
            }
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2);

        }


        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        public void setTabHolderScrollingContent(ScrollTabHolder listener) {
            mListener = listener;
        }
        public SparseArrayCompat<ScrollTabHolder> getScrollTabHolders() {
            return mScrollTabHolders;
        }
    }

    private void interpolate(View view1, View view2, float interpolation) {
        getOnScreenRect(mRect1, view1);
        getOnScreenRect(mRect2, view2);

        float scaleX = 1.0F + interpolation * (mRect2.width() / mRect1.width() - 1.0F);
        float scaleY = 1.0F + interpolation * (mRect2.height() / mRect1.height() - 1.0F);
        float translationX = 0.5F * (interpolation * (mRect2.left + mRect2.right - mRect1.left - mRect1.right));
        float translationY = 0.5F * (interpolation * (mRect2.top + mRect2.bottom - mRect1.top - mRect1.bottom));

        view1.setTranslationX(translationX);
        //view1.setTranslationY(translationY - mHeader.getTranslationY());
        view1.setScaleX(scaleX);
        view1.setScaleY(scaleY);
    }

    private RectF getOnScreenRect(RectF rect, View view) {
        rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        return rect;
    }

    public void animateTextView(int initialValue, int finalValue, final TextView  textview) {

        ValueAnimator valueAnimator = ValueAnimator.ofInt(initialValue, finalValue);
        valueAnimator.setDuration(1500);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                textview.setText(valueAnimator.getAnimatedValue().toString());
            }

        });
        valueAnimator.start();
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //redText(from_point);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    public void redText(View view){
        //((TextView)tab_point).setTextColor(0xFFCD8BF8);
//        mSmallBang.bang(view, 300, new SmallBangListener() {
//            @Override
//            public void onAnimationStart() {
//            }
//
//            @Override
//            public void onAnimationEnd() {
//                // Toast.makeText(MainDashboard.this,"Added",Toast.LENGTH_SHORT).show();
//            }
//        });
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
        download_information("");

    }


    public void tellThatLoadedSuccessfully(final Boolean successfull)
    {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (eventInitialiser != null)
                    eventInitialiser.MyloadingCompleted(fragVal, successfull);
            }
        }, 1000);

    }

    private void download_information(String userid) {

        String tag_json_obj = "json_obj_req_my_earnings";

        if(json==null) {
            Log.i("Myapp", "Calling url " + url);
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    url, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(final JSONObject response) {
                            try {
                                 json = response.getJSONObject("data");
                                createcontentforthispage(json.getString("total_balance"), "" + json.getJSONArray("active_rewards").length(), json.getString("earning"), json.getString("total_redeemed"), json.getJSONArray("active_rewards"), json.getJSONArray("earning_list"), json.getJSONArray("redeemed"));
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
        else
        {
            try {
                createcontentforthispage(json.getString("total_balance"), ""+ json.getJSONArray("active_rewards").length(), json.getString("earning"), json.getString("total_redeemed"), json.getJSONArray("active_rewards"), json.getJSONArray("earning_list"), json.getJSONArray("redeemed"));
            } catch (JSONException e) {
                e.printStackTrace();
                tellThatLoadedSuccessfully(false);
            }
        }
    }

    public void createcontentforthispage(String total_balance,String num_rewards,String num_points_earned,String num_points_redeemed,JSONArray rewards,JSONArray points_earned,JSONArray points_redeemed)
    {
//
//        mPagerAdapter = new PagerAdapter(getContext(),num_rewards,num_points_earned,num_points_redeemed,rewards,points_earned,points_redeemed);
//        mPagerAdapter.setTabHolderScrollingContent(this);
//
//        mViewPager.setAdapter(mPagerAdapter);
//
//        mPagerSlidingTabStrip.setViewPager(mViewPager);
//        mPagerSlidingTabStrip.setOnPageChangeListener(this);
//        animateTextView(100, Integer.parseInt(total_balance), (TextView) from_point);
//        ((TextView)to_point).setText(total_balance);
//        mLastY=0;
//        tellThatLoadedSuccessfully(true);
    }
}