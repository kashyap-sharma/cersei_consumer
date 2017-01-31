package co.jlabs.cersei_retailer.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

import com.appnirman.vaidationutils.ValidationUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import co.jlabs.cersei_retailer.JSONfunctions;
import co.jlabs.cersei_retailer.OrderPlace;
import co.jlabs.cersei_retailer.R;
import co.jlabs.cersei_retailer.SelectLocation;
import co.jlabs.cersei_retailer.StaticCatelog;
import co.jlabs.cersei_retailer.custom_components.ButtonModarno;
import co.jlabs.cersei_retailer.custom_components.Class_Cart;
import co.jlabs.cersei_retailer.custom_components.Class_retailer;
import co.jlabs.cersei_retailer.custom_components.MEditText;
import co.jlabs.cersei_retailer.custom_components.Sqlite_cart;
import co.jlabs.cersei_retailer.custom_components.transforms.RadioButtonModarno;

public class ProcessOrder extends AppCompatActivity implements View.OnClickListener {

    private MEditText name;
    private TextInputLayout input_name;
    private MEditText flat;
    private TextInputLayout input_flat;
    private MEditText street;
    private TextInputLayout input_street;
    private MEditText locality;
    private TextInputLayout input_locality;
    private MEditText pincode;
    private TextInputLayout input_pincode;
    private CardView new_add;
    private RadioButtonModarno saved_add1;
    private RadioButtonModarno saved_add2;
    private RadioGroup radio_group;
    private RelativeLayout activity_process_order;
    private TextView text_location;
    private LinearLayout location;
    private ButtonModarno process_order;
    Sqlite_cart cart;
    ArrayList<Class_Cart> items;
    ArrayList<Class_Cart> itema;
    Context context;
    String url1 = StaticCatelog.geturl()+"cersei/consumer/order";
    private ProgressDialog pdia;
    ValidationUtils validationUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_order);
        context=this;
        initView();

    }

    private void initView() {
        cart=new Sqlite_cart(context);
        validationUtils = new ValidationUtils(context);
        items = cart.getAllCart();
        itema = cart.getDistinctRetailer();
        name = (MEditText) findViewById(R.id.name);
        input_name = (TextInputLayout) findViewById(R.id.input_name);
        flat = (MEditText) findViewById(R.id.flat);
        input_flat = (TextInputLayout) findViewById(R.id.input_flat);
        street = (MEditText) findViewById(R.id.street);
        input_street = (TextInputLayout) findViewById(R.id.input_street);
        locality = (MEditText) findViewById(R.id.locality);
        input_locality = (TextInputLayout) findViewById(R.id.input_locality);
        pincode = (MEditText) findViewById(R.id.pincode);
        input_pincode = (TextInputLayout) findViewById(R.id.input_pincode);
        new_add = (CardView) findViewById(R.id.new_add);
        saved_add1 = (RadioButtonModarno) findViewById(R.id.saved_add1);
        saved_add2 = (RadioButtonModarno) findViewById(R.id.saved_add2);
        radio_group = (RadioGroup) findViewById(R.id.radio_group);
        activity_process_order = (RelativeLayout) findViewById(R.id.activity_process_order);
        text_location = (TextView) findViewById(R.id.text_location);
        location = (LinearLayout) findViewById(R.id.location);
        process_order = (ButtonModarno) findViewById(R.id.process_order);
        process_order.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.process_order:
                getOrderReady();

                break;
        }
    }

    private void submit() {
        // validate
        String nameString = name.getText().toString().trim();
        if (TextUtils.isEmpty(nameString)) {
            Toast.makeText(this, "Name", Toast.LENGTH_SHORT).show();
            return;
        }

        String flatString = flat.getText().toString().trim();
        if (TextUtils.isEmpty(flatString)) {
            Toast.makeText(this, "Flat/House/Office No.", Toast.LENGTH_SHORT).show();
            return;
        }

        String streetString = street.getText().toString().trim();
        if (TextUtils.isEmpty(streetString)) {
            Toast.makeText(this, "Street/Society/Office Name", Toast.LENGTH_SHORT).show();
            return;
        }

        String localityString = locality.getText().toString().trim();
        if (TextUtils.isEmpty(localityString)) {
            Toast.makeText(this, "Locality", Toast.LENGTH_SHORT).show();
            return;
        }

        String pincodeString = pincode.getText().toString().trim();
        if (TextUtils.isEmpty(pincodeString)) {
            Toast.makeText(this, "Pincode", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }

    private void getOrderReady(){


        if (!validationUtils.isEmptyEditText(name.getText().toString())) {
            name.setError("Enter valid name");
            return;
        }
        if (!validationUtils.isValidAddress(flat.getText().toString())) {
            flat.setError("Enter valid address");
            return;
        }
        if (!validationUtils.isValidPincode(pincode.getText().toString())) {
            pincode.setError("Enter valid pincode");
            return;
        }

        for (int i=0;i< itema.size();i++){
            Log.e("process1: ",""+itema.get(i).retailer_id);
        }
        for (int i=0;i< items.size();i++){
            Log.e("process: ",""+items.get(i).offer_id);
        }


        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("name", name.getText().toString().trim());
            jsonObject.put("address", flat.getText().toString().trim()+", "+StaticCatelog.getStringProperty(this, "area")+", "+StaticCatelog.getStringProperty(this, "location")+", "+flat.getText().toString().trim());
            StaticCatelog.setStringProperty(context,"addr", flat.getText().toString().trim()+", "+StaticCatelog.getStringProperty(this, "location")+", "+StaticCatelog.getStringProperty(this, "area")+", "+pincode.getText().toString().trim());
            jsonObject.put("email", StaticCatelog.getStringProperty(context,"email"));
            jsonObject.put("phone", StaticCatelog.getStringProperty(context,"mobile"));
            jsonObject.put("api_key", StaticCatelog.getStringProperty(context,"api_key"));
            jsonObject.put("user_type", "user");
            jsonObject.put("user_id", StaticCatelog.getStringProperty(context,"user_id"));
            JSONArray order= new JSONArray();

            for (int i=0;i< itema.size();i++){
                JSONObject retailer=new JSONObject();
                retailer.put("retailer_id",itema.get(i).retailer_id);
                JSONArray offers=new JSONArray();
                ArrayList<Class_Cart> ret = cart.getRetailerOrderData(itema.get(i).retailer_id);
                for(int j=0;j<ret.size();j++){
                    JSONObject offer=new JSONObject();
                    offer.put("offer_id",ret.get(j).offer_id);
                    offer.put("qty",""+ret.get(j).quantity);
                    offers.put(offer);
                }
                retailer.put("offers",offers);
                order.put(retailer);
                Log.e("process1: ",""+itema.get(i).retailer_id);
            }
            jsonObject.put("order",order);
            Log.e("processs:5  ",""+jsonObject.toString());



        } catch (JSONException e) {
            e.printStackTrace();
        }

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
                    Log.e("datata",""+data.toString());
                    Intent intent =new Intent(context, BillActivity.class);
                    intent.putExtra("order_id",data.getString("order_id"));
                    startActivity(intent);
                }
                else {
                    Intent intent =new Intent(context, LoginNum.class);
                    intent.putExtra("from","process");
                    startActivity(intent);
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
            Toast.makeText(context, "Data Posted.", Toast.LENGTH_LONG).show();

        }
    }


}
