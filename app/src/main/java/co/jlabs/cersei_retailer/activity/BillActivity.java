package co.jlabs.cersei_retailer.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import co.jlabs.cersei_retailer.R;
import co.jlabs.cersei_retailer.Rounded.MyIconFonts;
import co.jlabs.cersei_retailer.Rounded.MyTextView;
import co.jlabs.cersei_retailer.StaticCatelog;


public class BillActivity extends AppCompatActivity {

    MyTextView pay_mode,order_id,name,address,contact;
    MyIconFonts total,back;
    TextView dashed;
    Button track;
    String mode,orderID,cus_name,add,num;
    Context context;
    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        context=this;
        activity=this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pay_mode=(MyTextView)findViewById(R.id.pay_mode);
        order_id=(MyTextView)findViewById(R.id.order_id);
        name=(MyTextView)findViewById(R.id.name);
        address=(MyTextView)findViewById(R.id.address);
        contact=(MyTextView)findViewById(R.id.contact);
        dashed=(TextView) findViewById(R.id.dashed);
        total=(MyIconFonts)findViewById(R.id.total);
        back=(MyIconFonts)findViewById(R.id.back);
        dashed.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        track=(Button)findViewById(R.id.track);
        Intent intent = getIntent();
        mode=intent.getStringExtra("mode");
        //orderID=intent.getStringExtra("mode");
        //cus_name=intent.getStringExtra("mode");
        add=intent.getStringExtra("faddress");
        num=intent.getStringExtra("contact");
        if (mode.equals("cod")){
            pay_mode.setText("COD");
        }else {
            pay_mode.setText("Prepaid");
            pay_mode.setTextColor(Color.parseColor("#ffffff"));
            pay_mode.setBackgroundColor(Color.parseColor("#079450"));
        }
        order_id.setText(StaticCatelog.getStringProperty(context,"order_number"));
        name.setText(StaticCatelog.getStringProperty(context,"fname")+" "+StaticCatelog.getStringProperty(context,"lname"));
        address.setText(add);
        contact.setText(num);
        total.setText(context.getResources().getString(R.string.rupee)+StaticCatelog.getStringProperty(context,"tota"));

    }

}
