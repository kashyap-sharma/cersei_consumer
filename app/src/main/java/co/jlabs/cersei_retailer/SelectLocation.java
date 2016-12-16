package co.jlabs.cersei_retailer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import co.jlabs.cersei_retailer.custom_components.ButtonModarno;
import co.jlabs.cersei_retailer.custom_components.LocationPopup;
import co.jlabs.cersei_retailer.custom_components.MEditText;
import co.jlabs.cersei_retailer.custom_components.TextViewModernM;

public class SelectLocation extends Activity implements LocationPopup.onLocationSelected {
    LocationPopup dialog;
    Context context;
    JSONArray data=null;
    ListView lv;
    LinearLayout ll;
    MEditText phone;
    Button que_button;
    ButtonModarno send_code;
    TextViewModernM select;
    JSONObject json=null;
    String url = StaticCatelog.geturl()+"cersei/consumer/location";
    String url1 = StaticCatelog.geturl()+"cersei/auth/ulogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_location);

        context=this;
        ll=(LinearLayout)findViewById(R.id.login_window);
        phone=(MEditText) findViewById(R.id.phone);
        que_button=(Button)findViewById(R.id.que_but);
        send_code=(ButtonModarno)findViewById(R.id.send_code);
        select=(TextViewModernM)findViewById(R.id.select);
        ll.setVisibility(View.GONE);
        select.setVisibility(View.GONE);
        phone.setMaxEms(10);
        phone.addTextChangedListener(passwordWatcher);
        que_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage(getString(R.string.message_q));
                builder.setCancelable(true);

                final AlertDialog closedialog= builder.create();

                closedialog.show();

                final Timer timer2 = new Timer();
                timer2.schedule(new TimerTask() {
                    public void run() {
                        closedialog.dismiss();
                        timer2.cancel(); //this will cancel the timer of the system
                    }
                }, 5000); // the timer will count 5 seconds....

            }
        });




        // getLocation();
        if(StaticCatelog.getStringProperty(this,"api_key")==null) {
            ll.setVisibility(View.VISIBLE);
            send_code.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String contactString = phone.getText().toString().trim();
                    if (TextUtils.getTrimmedLength(contactString)!=10) {
                        Toast.makeText(context, "Phone Number", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    log_in();
                    download_locations();



                }
            });


        }

        else {
            if(StaticCatelog.getStringProperty(this,"location")==null) {
                download_locations();
            }
            else
            {
//            ((TextView)findViewById(R.id.select)).setText(StaticCatelog.getStringProperty(this, "location"));
//            ((TextView)findViewById(R.id.select)).setText(StaticCatelog.getStringProperty(this, "location"));
                start_activity();
            }
        }
    }

    @Override
    public void update_location(String Area,String location) {
        ((TextViewModernM)findViewById(R.id.select)).setText(location);
        StaticCatelog.setStringProperty(this, "area", Area);
        StaticCatelog.setStringProperty(this, "location", location);
        start_activity();
    }

    public void start_activity(){
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(SelectLocation.this, MainDashboard.class);
                startActivity(i);
                finish();
            }
        }, 1000);

    }

    public void getLocation()
    {
        int success=0;
        try {
            success = json.getInt("success");
        } catch (JSONException e) {
            success=0;
        }
        if(dialog==null)
            dialog = new LocationPopup(context, R.style.alert_dialog);

        if(success==1)
        {
            findViewById(R.id.select).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!dialog.isShowing())
                    dialog = new LocationPopup(context, R.style.alert_dialog);
                    dialog.BuildDialog(SelectLocation.this, json);
                }
            });
            dialog.BuildDialog(SelectLocation.this, json);
        }
        else
        {
            Toast.makeText(context,"Invalid Data Received",Toast.LENGTH_SHORT).show();
        }

    }


    private void download_locations() {

        String tag_json_obj = "json_obj_req_get_locations";

        Log.i("Myapp", "Calling url " + url);
        if(json==null) {
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
                    Toast.makeText(context,"Unable to get List of Locations",Toast.LENGTH_SHORT).show();
                }
            });
            AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        }
        else
        {
            getLocation();
        }
    }
    private void log_in() {
        String contactString = phone.getText().toString().trim();
        JSONObject jsonObject =new JSONObject();
        try {
            jsonObject.put("username","9711940752");
            jsonObject.put("password","827ccb0eea8a706c4c34a16891f84e7b");
            jsonObject.put("user_type","user");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("Myapp", "Calling url " + url1);
        new LoginPost(jsonObject).execute();
    }


    private class LoginPost extends AsyncTask<String,Void,Void>
    {
        JSONObject object;
        LoginPost(JSONObject obj)
        {
            object=obj;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... args) {
            JSONObject obj=JSONfunctions.makenewHttpRequest(context, url1, object);
            try {
                if (obj.getBoolean("success")) {
                    JSONObject data=obj.getJSONObject("data");
                    StaticCatelog.setStringProperty(context,"name",data.getString("name"));
                    StaticCatelog.setStringProperty(context,"mobile",data.getString("mobile_no"));
                    StaticCatelog.setStringProperty(context,"email",data.getString("email"));
                    StaticCatelog.setStringProperty(context,"api_key",data.getString("api_key"));
                    Log.e("api:  ",""+data.getString("api_key"));
                    StaticCatelog.setStringProperty(context,"user_id",data.getString("user_id"));
                } else {

                    Toast.makeText(context,"You did something wrong.",Toast.LENGTH_SHORT).show();

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void val) {
            super.onPostExecute(val);
            if(StaticCatelog.getStringProperty(context,"api_key")!=null){
                Toast.makeText(context, "Logged in.", Toast.LENGTH_LONG).show();
                ll.setVisibility(View.GONE);
                select.setVisibility(View.VISIBLE);
            }
        }
    }



    private final TextWatcher passwordWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // textView.setVisibility(View.VISIBLE);
        }

        public void afterTextChanged(Editable s) {
            if (s.length() == 10) {
                InputMethodManager inputManager =
                        (InputMethodManager) context.
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(
                        getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    };
}
