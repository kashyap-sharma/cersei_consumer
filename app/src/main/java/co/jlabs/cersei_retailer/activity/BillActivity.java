package co.jlabs.cersei_retailer.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import co.jlabs.cersei_retailer.MainDashboard;
import co.jlabs.cersei_retailer.OrderPlace;
import co.jlabs.cersei_retailer.R;
import co.jlabs.cersei_retailer.Rounded.MyIconFonts;
import co.jlabs.cersei_retailer.Rounded.MyTextView;
import co.jlabs.cersei_retailer.StaticCatelog;
import co.jlabs.cersei_retailer.custom_components.ButtonModarno;
import co.jlabs.cersei_retailer.custom_components.Sqlite_cart;


public class BillActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    Activity activity;
    private MyIconFonts back;
    private Toolbar toolbar;
    private AppBarLayout appbar;
    private MyTextView sum;
    private MyTextView pay_mode;
    private TextView dashed;
    private MyIconFonts total;
    private MyTextView order;
    private MyTextView order_id;
    private MyTextView cus_name;
    private MyTextView name;
    private MyTextView add_name;
    private MyTextView address;
    private MyTextView con_name;
    private MyTextView contact;
    private ButtonModarno track;
    String newString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        context = this;
        activity = this;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("order_id");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("order_id");
        }
        initView();



    }

    private void initView() {
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        back = (MyIconFonts) findViewById(R.id.back);

        appbar = (AppBarLayout) findViewById(R.id.appbar);
        sum = (MyTextView) findViewById(R.id.sum);
        pay_mode = (MyTextView) findViewById(R.id.pay_mode);
        dashed = (TextView) findViewById(R.id.dashed);
        dashed.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        total = (MyIconFonts) findViewById(R.id.total);
        order = (MyTextView) findViewById(R.id.order);
        order_id = (MyTextView) findViewById(R.id.order_id);
        order_id.setText(newString);


        cus_name = (MyTextView) findViewById(R.id.cus_name);
        name = (MyTextView) findViewById(R.id.name);
        name.setText(StaticCatelog.getStringProperty(context,"name"));


        add_name = (MyTextView) findViewById(R.id.add_name);
        address = (MyTextView) findViewById(R.id.address);
        address.setText(StaticCatelog.getStringProperty(context,"name")+"\n"+110068);


        con_name = (MyTextView) findViewById(R.id.con_name);
        contact = (MyTextView) findViewById(R.id.contact);
        contact.setText(StaticCatelog.getStringProperty(context,"mobile"));


        track = (ButtonModarno) findViewById(R.id.track);
        track.setOnClickListener(this);
        back.setOnClickListener(this);


        total.setText(getString(R.string.rupee)+""+StaticCatelog.getStringProperty(context,"total_price"));
        StaticCatelog.setStringProperty(context,"total_price","N.A.");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.track:
                Intent intent =new Intent(this, OrderPlace.class);
                startActivity(intent);
                break;
            case R.id.back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed(){
        deleteAll();
        Intent intent =new Intent(this, MainDashboard.class);
        startActivity(intent);
    }
    public void deleteAll()
    {
        Sqlite_cart helper = new Sqlite_cart(context);
        SQLiteDatabase  db = helper.getWritableDatabase();
        // db.delete(TABLE_NAME,null,null);
        //db.execSQL("delete * from"+ TABLE_NAME);
        db.delete("Cart",null,null );
        db.close();
    }
}
