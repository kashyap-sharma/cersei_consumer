package co.jlabs.cersei_retailer;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


import co.jlabs.cersei_retailer.StepView.HorizontalStepView;
import co.jlabs.cersei_retailer.StepView.bean.StepBean;
import co.jlabs.cersei_retailer.activity.ProcessOrder;
import co.jlabs.cersei_retailer.custom_components.Class_Cart;

public class OrderPlace extends AppCompatActivity {
    Context context;
    private ImageView back;
    private LinearLayout header;
    private TextView placed_time;
    private RecyclerView recyclerView;
    public JSONArray data;
    String url1 = StaticCatelog.geturl()+"cersei/consumer/order/list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.order_place);
        ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(5);

        // This schedule a runnable task every 2 minutes
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                getOrders();
                initView();
            }
        }, 0, 30, TimeUnit.SECONDS);



    }

    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        header = (LinearLayout) findViewById(R.id.header);
        placed_time = (TextView) findViewById(R.id.placed_time);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }
    @Override
    public void onBackPressed(){


        Intent i = new Intent(OrderPlace.this, MainDashboard.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
        //startActivity(intent);

    }


    private static class RecyclerViewAdapter extends RecyclerView.Adapter<FakeViewHolder> {
        JSONArray data;
        int[] drawables;
        int[] text;
        int[] notif_count;
        JSONObject[] jsonObjects;





        public RecyclerViewAdapter(int index, JSONArray data ) {
            this.data=data;
            Log.e("one",""+data.toString());
            Log.e("onee",""+this.data.toString());

            if (index==1) {
//                drawables = new int[] {
//                        R.drawable.settings,
//                        R.drawable.calendar,
//                        R.drawable.fambond_white,
//                        R.drawable.vault,
//                        R.drawable.contacts,
//                        R.drawable.feeds,
//                        R.drawable.polls,
//                        R.drawable.events,
//                        R.drawable.tasks
//                };
//                text = new int[] {
//                        R.string.settings,
//                        R.string.calendar,
//                        R.string.bonds,
//                        R.string.vault,
//                        R.string.contacts,
//                        R.string.feed,
//                        R.string.polls,
//                        R.string.events,
//                        R.string.tasks
//
//                };
//                notif_count = new int[] {
//                        R.string.settings1,
//                        R.string.calendar1,
//                        R.string.bonds1,
//                        R.string.vault1,
//                        R.string.contacts1,
//                        R.string.feed1,
//                        R.string.polls1,
//                        R.string.events1,
//                        R.string.tasks1
//                };
            }

        }

        @Override
        public FakeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new FakeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_place_adapter, parent, false));
        }

        @Override
        public void onBindViewHolder(final FakeViewHolder holder, final int position) {
            JSONObject jo;
            List<StepBean> stepsBeanList = new ArrayList<>();
            StepBean stepBean0 = new StepBean("Processing",-1);
            StepBean stepBean1 = new StepBean("Accepted",-1);
            StepBean stepBean2 = new StepBean("Out for delivery",-1);
            StepBean stepBean3 = new StepBean("Delivered",-1);



            try {
                holder.retailer_name.setText(""+data.getJSONObject(position).getString("retailer_id"));
                holder.ret_total.setText(String.valueOf(data.getJSONObject(position).getInt("order_total")));
                holder.order_id.setText(""+data.getJSONObject(position).getString("suborder_id"));
                String status=data.getJSONObject(position).getJSONArray("status").getJSONObject(0).getString("status");
                switch (status){
                    case "placed":
                        stepBean0 = new StepBean("Processing",0);
                        stepsBeanList.add(stepBean0);
                        stepsBeanList.add(stepBean1);
                        stepsBeanList.add(stepBean2);
                        stepsBeanList.add(stepBean3);
                        break;
                    case "accepted":
                        stepBean1 = new StepBean("Accepted",0);
                        stepsBeanList.add(stepBean0);
                        stepsBeanList.add(stepBean1);
                        stepsBeanList.add(stepBean2);
                        stepsBeanList.add(stepBean3);
                        break;
                    case "canceled":
                        stepBean0 = new StepBean("Processing",0);
                        stepsBeanList.add(stepBean0);
                        stepsBeanList.add(stepBean1);
                        stepsBeanList.add(stepBean2);
                        stepsBeanList.add(stepBean3);
                        break;
                    case "ready":
                        stepBean2 = new StepBean("Out for delivery",0);
                        stepsBeanList.add(stepBean0);
                        stepsBeanList.add(stepBean1);
                        stepsBeanList.add(stepBean2);
                        stepsBeanList.add(stepBean3);
                        break;
                    case "dispatched":
                        stepBean2 = new StepBean("Out for delivery",0);
                        stepsBeanList.add(stepBean0);
                        stepsBeanList.add(stepBean1);
                        stepsBeanList.add(stepBean2);
                        stepsBeanList.add(stepBean3);
                        break;
                    case "delivered":
                        stepBean3 = new StepBean("Delivered",0);
                        stepsBeanList.add(stepBean0);
                        stepsBeanList.add(stepBean1);
                        stepsBeanList.add(stepBean2);
                        stepsBeanList.add(stepBean3);
                        break;
                    default:
                        stepBean0 = new StepBean("Processing",0);
                        stepsBeanList.add(stepBean0);
                        stepsBeanList.add(stepBean1);
                        stepsBeanList.add(stepBean2);
                        stepsBeanList.add(stepBean3);
                        break;
                }

                holder.step_view.setStepViewTexts(stepsBeanList);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        @Override
        public int getItemCount() {
            return data.length();
        }
    }
    private static class FakeViewHolder extends RecyclerView.ViewHolder {

        TextView retailer_name,ret_total,order_id,delivery_charge;
        HorizontalStepView step_view;
        public FakeViewHolder(View itemView) {
            super(itemView);
            retailer_name = (TextView) itemView.findViewById(R.id.retailer_name);
            ret_total = (TextView) itemView.findViewById(R.id.ret_total);
            order_id = (TextView) itemView.findViewById(R.id.order_id);
            delivery_charge = (TextView) itemView.findViewById(R.id.delivery_charge);
            step_view = (HorizontalStepView) itemView.findViewById(R.id.step_view);

            step_view//总步骤
                    .setTextSize(8)//set textSize
                    .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(itemView.getContext(), R.color.line))//Line
                    .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(itemView.getContext(), R.color.line))//设置StepsViewIndicator未完成线的颜色
                    .setStepViewComplectedTextColor(ContextCompat.getColor(itemView.getContext(), android.R.color.black))//设置StepsView text完成线的颜色
                    .setStepViewUnComplectedTextColor(ContextCompat.getColor(itemView.getContext(), R.color.black))//设置StepsView text未完成线的颜色
                    .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(itemView.getContext(), R.drawable.default_icon))//设置StepsViewIndicator CompleteIcon
                    .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(itemView.getContext(), R.drawable.default_icon))//设置StepsViewIndicator DefaultIcon
                    .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(itemView.getContext(), R.drawable.success_done));//设置StepsViewIndicator AttentionIcon
        }
    }


    private void getOrders() {

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
                    data=obj.getJSONArray("data");
                    Log.e("datata",""+data.toString());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void val) {
            super.onPostExecute(val);
           // Toast.makeText(context, "Data Posted.", Toast.LENGTH_LONG).show();

            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),1);
            recyclerView.setAdapter(new RecyclerViewAdapter(1,data));
            recyclerView.setLayoutManager(layoutManager);

//            Intent intent =new Intent(context, OrderPlace.class);
//            startActivity(intent);
        }
    }

}
