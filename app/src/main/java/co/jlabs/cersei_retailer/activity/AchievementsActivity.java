package co.jlabs.cersei_retailer.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import co.jlabs.cersei_retailer.JSONfunctions;
import co.jlabs.cersei_retailer.R;
import co.jlabs.cersei_retailer.StaticCatelog;
import co.jlabs.cersei_retailer.custom_components.TextViewModernM;
import co.jlabs.cersei_retailer.fragmentsInitialiser.Image;
import co.jlabs.cersei_retailer.trey.MultiTypeDemoAdapter;
import co.jlabs.cersei_retailer.trey.StickyHeaderLayoutManager;


public class AchievementsActivity extends AppCompatActivity {
    public JSONObject data;
    String url1 =  "http://lannister-api.elasticbeanstalk.com/cersei/consumer/account";
    Context context;
    ImageView back;
    TextViewModernM total;
    private static final String STATE_SCROLL_POSITION = "DemoActivity.STATE_SCROLL_POSITION";
    RecyclerView recyclerView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activity_main2);
        getAch();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        back=(ImageView)findViewById(R.id.back);
        recyclerView.setLayoutManager(new StickyHeaderLayoutManager());
        total=(TextViewModernM)findViewById(R.id.total);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Log.e("check1",""+data.toString());
//        ItemAdapter adapter = new ItemAdapter(context,data);




    }


    private void getAch() {

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("api_key", StaticCatelog.getStringProperty(context,"api_key"));
            jsonObject.put("user_type", "user");
            jsonObject.put("user_id", StaticCatelog.getStringProperty(context,"user_id"));
            Log.e("processs:order_list:  ",""+jsonObject.toString());



        } catch (JSONException e) {
            e.printStackTrace();
        }

        new OrderList(jsonObject).execute();
    }

    private class OrderList extends AsyncTask<String,Void,Void>
    {
        JSONObject object;
        OrderList(JSONObject obj)
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
                    data=obj.getJSONObject("data");
                   // Log.e("datata",""+data.toString());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void val) {
            super.onPostExecute(val);
            Log.e("datata",""+data.toString());
            Toast.makeText(context, "Data Posted.", Toast.LENGTH_LONG).show();
            try {
                total.setText("₹"+data.getInt("total_cashback"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            recyclerView.setAdapter(new MultiTypeDemoAdapter(data));
//            Intent intent =new Intent(context, OrderPlace.class);
//            startActivity(intent);
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();
        Parcelable scrollState = lm.onSaveInstanceState();
        outState.putParcelable(STATE_SCROLL_POSITION, scrollState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(savedInstanceState.getParcelable(STATE_SCROLL_POSITION));
        }
    }

}
