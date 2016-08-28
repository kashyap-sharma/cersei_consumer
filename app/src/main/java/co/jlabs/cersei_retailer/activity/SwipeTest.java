package co.jlabs.cersei_retailer.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import java.util.ArrayList;

import co.jlabs.cersei_retailer.R;
import co.jlabs.cersei_retailer.custom_components.Sqlite_cart;
import co.jlabs.cersei_retailer.swipecardlib.SwipeCardView;
import co.jlabs.cersei_retailer.swiper.Card;
import co.jlabs.cersei_retailer.swiper.CardsAdapter;

public class SwipeTest extends Activity {
    private ArrayList<Card> al;
    private CardsAdapter arrayAdapter;
    private int i;
    TextView tv_position;
    int num_item=0;
    Context context;
    Sqlite_cart cart;


    @InjectView(R.id.card_stack_view)
    SwipeCardView swipeCardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_test);
        ButterKnife.inject(this);
        JSONArray offers=null;
        context=this;
        int position=getIntent().getIntExtra("position",1);
        try {
            offers = new JSONArray(getIntent().getStringExtra("offers"));
            num_item=offers.length();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        al = new ArrayList<>();
        getDummyData(al);
        arrayAdapter = new CardsAdapter(this, al );

        swipeCardView.setAdapter(arrayAdapter);
        swipeCardView.setFlingListener(new SwipeCardView.OnCardFlingListener() {
            @Override
            public void onCardExitLeft(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
//                makeToast(CardSwipeActivity.this, "Left!");
            }

            @Override
            public void onCardExitRight(Object dataObject) {
//                makeToast(CardSwipeActivity.this, "Right!");
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
//                al.add("XML ".concat(String.valueOf(i)));
//                arrayAdapter.notifyDataSetChanged();
//                Log.d("LIST", "notified");
//                i++;
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                View view = swipeCardView.getSelectedView();
//                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
//                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }

            @Override
            public void onCardExitTop(Object dataObject) {
//                makeToast(CardSwipeActivity.this, "Top!");
            }

            @Override
            public void onCardExitBottom(Object dataObject) {

            }
        });


        // Optionally add an OnItemClickListener
        swipeCardView.setOnItemClickListener(
                new SwipeCardView.OnItemClickListener() {
                    @Override
                    public void onItemClicked(int itemPosition, Object dataObject) {
                        Card card = (Card) dataObject;
                        makeToast(SwipeTest.this, card.name);
                    }
                });
    }
    static void makeToast(Context ctx, String s){
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }
    private void getDummyData(ArrayList<Card> al) {
        Card card = new Card();
        card.name = "Card1";
        card.imageId = R.drawable.ic_cast_dark;
        al.add(card);
        Card card2 = new Card();
        card2.name = "Card2";
        card2.imageId = R.drawable.ic_cast_disabled_light;
        al.add(card2);
        Card card3 = new Card();
        card3.name = "Card3";
        card3.imageId = R.drawable.ic_cast_on_1_light;
        al.add(card3);
        Card card4 = new Card();
        card4.name = "Card4";
        card4.imageId = R.drawable.ic_edit_mobile;
        al.add(card4);
        Card card5 = new Card();
        card5.name = "Card5";
        card5.imageId = R.drawable.ic_cast_light;
        al.add(card5);
        Card card56 = new Card();
        card56.name = "Card6";
        card56.imageId = R.drawable.mr_ic_audio_vol;
        al.add(card56);
    }
}
