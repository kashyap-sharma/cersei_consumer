package co.jlabs.cersei_retailer.activity;

import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.jlabs.cersei_retailer.AppController;
import co.jlabs.cersei_retailer.JSONfunctions;
import co.jlabs.cersei_retailer.NewAddress;
import co.jlabs.cersei_retailer.R;
import co.jlabs.cersei_retailer.Rounded.MyIconFonts;
import co.jlabs.cersei_retailer.SavedAddress;
import co.jlabs.cersei_retailer.StaticCatelog;
import co.jlabs.cersei_retailer.fragmentsInitialiser.Image;


public class AddressFiller extends AppCompatActivity implements NewAddress.OnFragmentInteractionListener,SavedAddress.OnFragmentInteractionListener {
    private static final String endpoint = "http://lannister-api.elasticbeanstalk.com/tyrion/address?vendor_id=1&email=";
    private String tota,hi,email,fname,lname;
    boolean success=true;
    private String TAG = AddressFiller.class.getSimpleName();
    String pincode,contact,landmark,address;
    int sint;
    AppController app;
    Context context;
    MyIconFonts total;
    int pos;
    private ProgressDialog pDialog;
    CardView payview;
    Button place_order,done;
    Image image;
    private ArrayList<Image> images;
    int s;
    ViewPager mViewPager;
    TabLayout tabLayout;
//    public static My_info myinfo;
    private int selectedPosition = 0;
    RadioButton debit,cod,netbanking;
    JSONArray data = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        image=new Image();
        setContentView(R.layout.activity_address_filler);
        app = (AppController) context.getApplicationContext();
        total=(MyIconFonts)findViewById(R.id.total);
        place_order=(Button)findViewById(R.id.place_order);
        done=(Button)findViewById(R.id.done);
        payview=(CardView)findViewById(R.id.payview);
        pDialog = new ProgressDialog(this);
        fetchImages();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         mViewPager =(ViewPager) findViewById(R.id.container);


        done.setOnClickListener(done1);
        debit=(RadioButton)findViewById(R.id.debit);
        cod=(RadioButton)findViewById(R.id.cod);
        netbanking=(RadioButton)findViewById(R.id.netbanking);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
       // setupViewPager(mViewPager);
        tabLayout.setupWithViewPager(mViewPager);
       // tota=StaticCatelog.getStringProperty(context,"tota");
        tota="2525";
        float f = Float.parseFloat(tota);
        animateTextView(0,f,total);
        //email= StaticCatelog.getStringProperty(context,"email");
        email= "kashyap.sharma@jlabs.co";

        if(email==null)
        {

            Intent intent = getIntent();
            //myinfo = new My_info();
//            email = intent.getStringExtra("email");
//            fname = intent.getStringExtra("fname");
//            lname = intent.getStringExtra("lname");
            StaticCatelog.setStringProperty(context,"email","kashyap.sharma@jlabs.co");
            StaticCatelog.setStringProperty(context,"fname","Kashyap");
            StaticCatelog.setStringProperty(context,"lname","Sharma");
        }
        else
        {
            email=  "kashyap.sharma@jlabs.co";
            fname=  "Kashyap";
            lname= "Kashyap";
//            email=  StaticCatelog.getStringProperty(context, "email");
//            fname=  StaticCatelog.getStringProperty(context, "fname");
//            lname= StaticCatelog.getStringProperty(context, "lname");
        }


//        sint=getIntent().getExtras().getInt("sint");
//        Log.e("sint",""+sint+getIntent().getExtras().getString("pincode"));
//        if (sint==40){
//            Bundle b=new Bundle();
//            b.putInt("sint",sint);
//            b.putString("pincode",getIntent().getExtras().getString("pincode"));
//            b.putString("contact",getIntent().getExtras().getString("contact"));
//            b.putString("landmark",getIntent().getExtras().getString("landmark"));
//            b.putString("address",getIntent().getExtras().getString("address"));
//        }

    }








    View.OnClickListener done1 = new View.OnClickListener()
    {
        @Override
        public void onClick( View v )
        {
            // TODO Auto-generated method stub

            if( done.getId() == v.getId() )
            {
                JSONObject tempjson = new JSONObject();
                try {
                    tempjson.put("pincode", pincode);
                    tempjson.put("address", address);
                    tempjson.put("area", landmark);
                    tempjson.put("phone", contact);
//                    StaticCatelog.saveJson(context, tempjson);
                } catch (JSONException e) {

                }
                ArrayList<String> arr = new ArrayList<>();
//                ArrayList<JSONObject> jsonObjects = StaticCatelog.loadJson(context);
//                for(int i=0;i<jsonObjects.size();i++)
//                {
//                    arr.add(" " + jsonObjects.get(i).toString());
//                }
////                    address_list.setAdapter(new ArrayAdapter<>(context,android.R.layout.simple_list_item_1, android.R.id.text1,arr));

//                ArrayList<Order_Pizza> pizzas = app.getAllOrdersPizzas();
//                String s = "";
//                for (int i = 0; i < pizzas.size(); i++) {
//                    Log.i("Myapp Order Pizza", "Pizza " + i + " " + pizzas.get(i).toJSON());
//                    s = s + pizzas.get(i).toJSON();
//                }
//                JSONArray arr1 = new JSONArray();
//
//                for (int i = 0; i < pizzas.size(); i++) {
//                    arr1.put(pizzas.get(i).toJSON());
//                }
//                JSONObject finalJson = new JSONObject();
//                try {
////                    finalJson.put("order", arr1);
////                    Log.i("Orderaa", "or" + arr1);
//                    finalJson.put("vendor_id", 1);
//                    finalJson.put("email", email);
//                    finalJson.put("phone", contact);
//                    finalJson.put("name", fname+" "+lname);
//                    finalJson.put("area", landmark);
//                    finalJson.put("address", address+" "+pincode);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }


                JSONArray addr = new JSONArray();
                JSONObject jsons = new JSONObject();
                try {
                    jsons.put("phone", contact);
                    jsons.put("area", landmark);
                    jsons.put("address", address);
                    jsons.put("pincode", pincode);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("address", "numb" + contact);
                addr.put(jsons);
                JSONObject addressJson = new JSONObject();
                try {
                    addressJson.put("vendor_id", 1);
                    addressJson.put("email", email);
                    addressJson.put("address", addr);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i("address", "address" + addr);
                new AddressPost(addressJson).execute();
//                new OrderPizza(finalJson).execute();
                }
        }

    };






    View.OnClickListener btnClickListner = new View.OnClickListener()
    {
        @Override
        public void onClick( View v )
        {
            // TODO Auto-generated method stub
           final Intent  intent = new Intent(context, BillActivity.class);
            intent.putExtra("faddress",address+"\n"+pincode);
            intent.putExtra("landmark",landmark);
            intent.putExtra("contact",contact);

            if( cod.getId() == v.getId() )
            {
                place_order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intent.putExtra("mode","cod");
                        startActivity(intent);
                        done.performClick();
                    }
                });
            }
            else if( debit.getId() == v.getId() )
            {
                place_order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intent.putExtra("mode","debit");
                        startActivity(intent);
                        done.performClick();
                    }
                });
            }
            else if( netbanking.getId() == v.getId() )
            {
                place_order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intent.putExtra("mode","netbanking");
                        startActivity(intent);
                        done.performClick();
                    }
                });
            }


        }

    };


    public void animateTextView(float initialValue, float finalValue, final MyIconFonts  textview) {

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(initialValue, finalValue);
        valueAnimator.setDuration(1500);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                textview.setText(valueAnimator.getAnimatedValue().toString());

            }
        });
        valueAnimator.start();

    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        success=image.getSuccess();
        if(success){
            adapter.addFragment(new NewAddress(), "New Address");
            adapter.addFragment(new SavedAddress(), "Saved Address");
            viewPager.setAdapter(adapter);
        }
        else {
            adapter.addFragment(new NewAddress(), "New Address");
            viewPager.setAdapter(adapter);
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


//    @Override
//    public void updateName(int i,String name1,String address1,String contact1, String landmark1) {
//
//        s=i;
//        name=name1;
//        address=address1;
//        contact=contact1;
//        landmark=landmark1;
//        Log.e("some","activity"+name);
//        Log.e("some1","activity"+name1);
//        if (s>=7)
//        {
//
//            place_order.setBackgroundColor(Color.parseColor("#f26522"));
//            place_order.setTextColor(Color.parseColor("#ffffff"));
//            place_order.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent emailIntent = new Intent(AddressFiller.this, OrderStatusLast.class);
//                    startActivity(emailIntent);
//                }
//            });
//        }
//        else
//        {
//            place_order.setBackgroundColor(Color.parseColor("#DCDCDC"));
//            place_order.setTextColor(Color.parseColor("#adadad"));
//        }
//    }


    private void fetchImages( ) {
        String tag_json_obj = "json_obj_req";

        //String email = StaticCatelog.getStringProperty(context, "email");
        String email = "kashyap.sharma@jlabs.co";
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, endpoint+email, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d(TAG, response.toString());
                                pDialog.hide();
                                try {
                                     success=response.getBoolean("success");
                                     image.setSuccess(success);
                                    Log.e(TAG, "Tigeon" + "Json parsing error: " + success);
                                } catch (JSONException e) {
                                    Log.e(TAG, "Tigeon" + "Json parsing error: " + e.getMessage());
                                }

                                setupViewPager(mViewPager);
                            }
                        });

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());

            }

        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req,tag_json_obj);
    }

        @Override
        public void onFragmentInteraction(int ch,String userContent, String add, String land,String cont) {
            Log.e("some2",""+ch);
            pincode=userContent;
            address=add;
            landmark=land;
            contact=cont;

            if (ch==123) {
                place_order.setBackgroundColor(Color.parseColor("#f26522"));
                place_order.setTextColor(Color.parseColor("#ffffff"));
                place_order.setText("Pay Now");
                place_order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tabLayout.setVisibility(View.GONE);
                        mViewPager.setVisibility(View.GONE);
                        payview.setVisibility(View.VISIBLE);
                        debit.setOnClickListener(btnClickListner);
                        cod.setOnClickListener(btnClickListner);
                        netbanking.setOnClickListener(btnClickListner);
                    }
                });

            } else {
                place_order.setBackgroundColor(Color.parseColor("#DCDCDC"));
                place_order.setTextColor(Color.parseColor("#ADADAD"));
                place_order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       Log.e("data ", "error");
                    }
                });
            }
        }


    private class AddressPost extends AsyncTask<String, Void, Void> {
        JSONObject object1;

        AddressPost(JSONObject obj) {
            object1 = obj;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... args) {
            JSONfunctions.makenewHttpRequest(context, "http://lannister-api.elasticbeanstalk.com/tyrion/address", object1);
            return null;
        }

        @Override
        protected void onPostExecute(Void val) {
            super.onPostExecute(val);
            Toast.makeText(context, "Address Posted Successfully", Toast.LENGTH_LONG).show();

        }
    }
    private class OrderPizza extends AsyncTask<String,Void,Void>
    {
        JSONObject object;
        OrderPizza(JSONObject obj)
        {
            object=obj;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... args) {
            JSONObject obj= JSONfunctions.makenewHttpRequest(context, "http://lannister-api.elasticbeanstalk.com/tyrion/order", object);
            try {
                JSONObject c = obj.getJSONObject("data");
                Log.i("hipo","hipo"+c);
                String order_number = c.getString("order_number");
                Log.i("hipo","hipo1"+order_number);
                StaticCatelog.setStringProperty(context, "order_number","ThisCount_071");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void val) {
            super.onPostExecute(val);
            Toast.makeText(context, "Ordered Placed Successfully", Toast.LENGTH_LONG).show();
           // app.getAllOrdersPizzas().clear();
        }
    }

    @Override
    public void onBackPressed() {
        backButtonHandler();
    }
    public void backButtonHandler() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                AddressFiller.this);
        // Setting Dialog Title
        alertDialog.setTitle("Leave application?");
        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want to leave the application?");
        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.icon);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        dialog.cancel();
                    }
                });
        // Showing Alert Message
        alertDialog.show();
    }
}
