package co.jlabs.cersei_retailer.activity;

import android.app.AlertDialog;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import co.jlabs.cersei_retailer.JSONfunctions;
import co.jlabs.cersei_retailer.MainDashboard;
import co.jlabs.cersei_retailer.R;
import co.jlabs.cersei_retailer.SelectLocation;
import co.jlabs.cersei_retailer.StaticCatelog;
import co.jlabs.cersei_retailer.custom_components.ButtonModarno;
import co.jlabs.cersei_retailer.custom_components.MEditText;
import co.jlabs.cersei_retailer.custom_components.TextViewModernM;
import co.jlabs.cersei_retailer.otp.OtpView;

public class LoginNum extends AppCompatActivity implements View.OnClickListener {

    private TextViewModernM code;
    private MEditText phone;
    private Button que_but;
    private TextView bar;
    private ButtonModarno send_code;
    private LinearLayout login_window;
    private ButtonModarno submit;
    private TextView sitback;
    private TextView or;
    private OtpView otpview;
    private RelativeLayout otp;
    Context context;
    String s;
    String url1 = StaticCatelog.geturl()+"cersei/auth/ulogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        s = getIntent().getStringExtra("from");
        setContentView(R.layout.activity_login_num);
        initView();

    }

    private void initView() {
        code = (TextViewModernM) findViewById(R.id.code);
        phone = (MEditText) findViewById(R.id.phone);
        que_but = (Button) findViewById(R.id.que_but);
        bar = (TextView) findViewById(R.id.bar);
        send_code = (ButtonModarno) findViewById(R.id.send_code);
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
            case R.id.submit:
                String otpM = otpview.getOTP();
                if (TextUtils.getTrimmedLength(otpM)<4) {
                    Toast.makeText(this, "OTP invalid", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (s.equals("cart")) {
                    Intent intent =new Intent(this,ProcessOrder.class);
                    startActivity(intent);
                } else if(s.equals("share")) {
                    Intent intent =new Intent(this,ShareNEarn.class);
                    startActivity(intent);
                } else  {
                    Intent intent =new Intent(this,MainDashboard.class);
                    startActivity(intent);
                }
                break;
        }
    }

    private void submit() {
        // validate
        String phoneString = phone.getText().toString().trim();
        if (TextUtils.getTrimmedLength(phoneString)!=10) {
            Toast.makeText(context, "Phone Number", Toast.LENGTH_SHORT).show();
            return;
        }
        log_in();


        // TODO validate success, do something


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
            JSONObject obj= JSONfunctions.makenewHttpRequest(context, url1, object);
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
                login_window.setVisibility(View.GONE);
                otp.setVisibility(View.VISIBLE);
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
