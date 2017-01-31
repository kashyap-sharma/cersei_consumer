package co.jlabs.cersei_retailer.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appnirman.vaidationutils.ValidationUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import co.jlabs.cersei_retailer.JSONfunctions;
import co.jlabs.cersei_retailer.MainDashboard;
import co.jlabs.cersei_retailer.OrderPlace;
import co.jlabs.cersei_retailer.R;
import co.jlabs.cersei_retailer.SelectLocation;
import co.jlabs.cersei_retailer.StaticCatelog;
import co.jlabs.cersei_retailer.custom_components.ButtonModarno;
import co.jlabs.cersei_retailer.custom_components.MEditText;
import co.jlabs.cersei_retailer.custom_components.TextViewModernM;
import co.jlabs.cersei_retailer.otp.OtpView;
import co.jlabs.cersei_retailer.sunburn.SunBabyLoadingView;

public class LoginNum extends AppCompatActivity implements View.OnClickListener {

    private TextViewModernM code;
    private MEditText phone,fname, lname, email, reff_code;
    private Button que_but;
    private TextView bar;
    private ButtonModarno send_code,regi_conf ;
    private LinearLayout login_window;
    private ButtonModarno submit;
    private TextView sitback;
    private TextView or;
    private OtpView otpview;
    private RelativeLayout otp;
    private View content_regis;
    Context context;
    String s;
    String url1 = StaticCatelog.geturl()+"cersei/consumer/phone";
    String url2 = StaticCatelog.geturl()+"cersei/consumer/register";
    private ProgressDialog pdia;
    SunBabyLoadingView sblv;
    private ValidationUtils validationUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        s = getIntent().getStringExtra("from");
        setContentView(R.layout.activity_login_num);
        initView();

    }

    private void initView() {
        validationUtils = new ValidationUtils(context);
        code = (TextViewModernM) findViewById(R.id.code);
        phone = (MEditText) findViewById(R.id.phone);
        fname  = (MEditText) findViewById(R.id.fname);
        lname = (MEditText) findViewById(R.id.lname);
        email  = (MEditText) findViewById(R.id.email);
        content_regis=findViewById(R.id.content_regis);
        reff_code  = (MEditText) findViewById(R.id.reff_code);
        que_but = (Button) findViewById(R.id.que_but);
        bar = (TextView) findViewById(R.id.bar);
        send_code = (ButtonModarno) findViewById(R.id.send_code);
        regi_conf  = (ButtonModarno) findViewById(R.id.regi_conf);
        login_window = (LinearLayout) findViewById(R.id.login_window);
        submit = (ButtonModarno) findViewById(R.id.submit);
        sitback = (TextView) findViewById(R.id.sitback);
        or = (TextView) findViewById(R.id.or);
        otpview = (OtpView) findViewById(R.id.otpview);
        otp = (RelativeLayout) findViewById(R.id.otp);
        phone.setMaxEms(10);
        phone.addTextChangedListener(passwordWatcher);
        que_but.setOnClickListener(this);
        send_code.setOnClickListener(this);
        submit.setOnClickListener(this);
        regi_conf.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.que_but:
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
                }, 5000);
                break;
            case R.id.send_code:
                submit();
                break;
            case R.id.regi_conf:
                submit1();
//                submit();
                break;
            case R.id.submit:
                String otpM = otpview.getOTP();
                if (TextUtils.getTrimmedLength(otpM)<4) {
                    Toast.makeText(this, "OTP invalid", Toast.LENGTH_SHORT).show();
                    return;
                }
                log_in();

                break;
        }
    }




    private void submit() {
        // validate

        if (!validationUtils.isValidMobile(phone.getText().toString())) {
            phone.setError("Enter valid mobile number");
            return;
        }
        login_window.setVisibility(View.GONE);
        otp.setVisibility(View.VISIBLE);



        // TODO validate success, do something


    }

    private void submit1() {
        // validate
        if (!validationUtils.isValidFirstName(fname.getText().toString().trim())) {
            fname.setError("Enter valid first name");
            return;
        }
        if (!validationUtils.isValidFirstName(lname.getText().toString().trim())) {
            lname.setError("Enter valid last name");
            return;
        }

        if (!validationUtils.isValidEmail(email.getText().toString().trim())) {
            email.setError("Enter valid email");
            return;
        }
        String ref=reff_code.getText().toString().trim();
        String phoneString = phone.getText().toString().trim();
        JSONObject jsonObject1 =new JSONObject();
        try {
            jsonObject1.put("name",fname.getText().toString().trim()+" "+lname.getText().toString().trim());
            jsonObject1.put("mobile_no",phoneString);
            StaticCatelog.setStringProperty(context,"email",email.getText().toString().trim());
            jsonObject1.put("email",email.getText().toString().trim());
            jsonObject1.put("referral_code",ref);
            Log.e("apss",":"+jsonObject1.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new LoginPost1(jsonObject1).execute();
        Log.e("apss",":"+StaticCatelog.getStringProperty(context,"api_key"));


    }



    private void log_in() {
        String contactString = phone.getText().toString().trim();
        JSONObject jsonObject =new JSONObject();
        try {
            jsonObject.put("mobile_no",contactString);
            jsonObject.put("otp","5555");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("Myapp", "Calling url " + url1);
        Log.i("Myapp", "Calling url " + url2 );
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
            pdia = new ProgressDialog(context);
            pdia.setMessage("Loading...");
            pdia.show();
        }

        @Override
        protected Void doInBackground(String... args) {
            JSONObject obj= JSONfunctions.makenewHttpRequest(context, url1, object);
            try {
                if (obj.getBoolean("success")) {
                    JSONObject data=obj.getJSONObject("data");
                    if(obj.getJSONObject("data").getBoolean("new_user")){
                        StaticCatelog.setStringProperty(context,"api_key",null);
                    }
                    else
                    {
                    StaticCatelog.setBooleanProperty(context,"new_user",data.getBoolean("new_user"));
                    StaticCatelog.setStringProperty(context,"name",data.getString("name"));
                    StaticCatelog.setStringProperty(context,"mobile",data.getString("mobile_no"));
                    StaticCatelog.setStringProperty(context,"email",data.getString("email"));
                    StaticCatelog.setStringProperty(context,"api_key",data.getString("api_key"));
                    StaticCatelog.setIntProperty(context,"total_cashback",data.getInt("total_cashback"));
                    //Log.e("total_cashback:  ",""+StaticCatelog.getStringProperty(context,"total_cashback"));
                    StaticCatelog.setStringProperty(context,"user_id",data.getString("user_id"));
                    StaticCatelog.setStringProperty(context,"referral_code",data.getString("referral_code"));
                    }
                }
                else {
                    try {
                        Toast.makeText(context,"You did something wrong.",Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void val) {
            super.onPostExecute(val);
            pdia.dismiss();
//            if(StaticCatelog.getStringProperty(context,"api_key")!=null){
//                Toast.makeText(context, "Logged in.", Toast.LENGTH_LONG).show();
//
//            }

            if((StaticCatelog.getStringProperty(context,"api_key")==null)){
                login_window.setVisibility(View.GONE);
                otp.setVisibility(View.GONE);
                content_regis.setVisibility(View.VISIBLE);
            }else{
                Toast.makeText(context, "Logged in.", Toast.LENGTH_LONG).show();
                if (s.equals("cart")) {
                    Intent intent =new Intent(context,ProcessOrder.class);
                    startActivity(intent);
                } else if(s.equals("share")) {
                    Intent intent =new Intent(context,ShareNEarn.class);
                    startActivity(intent);
                }   else if(s.equals("process")) {
                    Intent intent =new Intent(context,ProcessOrder.class);
                    startActivity(intent);
                }  else if(s.equals("tracker")) {
                    Intent intent =new Intent(context, OrderPlace.class);
                    startActivity(intent);
                } else  {
                    Intent intent =new Intent(context,MainDashboard.class);
                    startActivity(intent);
                }
            }
        }
    }



    private class LoginPost1 extends AsyncTask<String,Void,Void>
    {
        JSONObject object;
        LoginPost1(JSONObject obj)
        {
            object=obj;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdia = new ProgressDialog(context);
            pdia.setMessage("Loading...");
            pdia.show();
        }

        @Override
        protected Void doInBackground(String... args) {
            JSONObject obj= JSONfunctions.makenewHttpRequest(context, url2, object);
            try {
                if (obj.getBoolean("success")) {
                        JSONObject data=obj.getJSONObject("data");
                        StaticCatelog.setStringProperty(context,"name",data.getString("name"));
                        StaticCatelog.setStringProperty(context,"mobile",data.getString("mobile_no"));
                        StaticCatelog.setStringProperty(context,"email",data.getString("email"));
                        StaticCatelog.setStringProperty(context,"api_key",data.getString("api_key"));
                        Log.e("api:  ",""+data.getString("api_key"));
                        StaticCatelog.setStringProperty(context,"user_id",data.getString("user_id"));
                        StaticCatelog.setStringProperty(context,"referral_code",data.getString("referral_code"));

                }
                else {
                    try {
                        Toast.makeText(context,"You did something wrong.",Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void val) {
            super.onPostExecute(val);
            pdia.dismiss();
            Log.e("dipasds",""+StaticCatelog.getStringProperty(context,"api_key"));
            if(!(StaticCatelog.getStringProperty(context,"api_key").equals("12"))){
                if (s.equals("cart")) {
                    Intent intent =new Intent(LoginNum.this,ProcessOrder.class);
                    startActivity(intent);
                } else if(s.equals("share")) {
                    Intent intent =new Intent(LoginNum.this,ShareNEarn.class);
                    startActivity(intent);
                }   else if(s.equals("process")) {
                    Intent intent =new Intent(LoginNum.this,ProcessOrder.class);
                    startActivity(intent);
                }  else if(s.equals("tracker")) {
                    Intent intent =new Intent(context, OrderPlace.class);
                    startActivity(intent);
                } else  {
                    Intent intent =new Intent(LoginNum.this,MainDashboard.class);
                    startActivity(intent);
                }

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
