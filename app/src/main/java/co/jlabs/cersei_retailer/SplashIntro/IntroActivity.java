package co.jlabs.cersei_retailer.splashIntro;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.view.View;
import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.MessageButtonBehaviour;
import agency.tango.materialintroscreen.SlideFragmentBuilder;
import agency.tango.materialintroscreen.animations.IViewTranslation;
import co.jlabs.cersei_retailer.R;
import co.jlabs.cersei_retailer.SelectLocation;
import co.jlabs.cersei_retailer.custom_components.Sqlite_cart;

public class IntroActivity extends MaterialIntroActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            deleteAll();
        } catch (Exception e) {
            e.printStackTrace();
        }


        enableLastSlideAlphaExitTransition(true);

        getBackButtonTranslationWrapper()
                .setEnterTranslation(new IViewTranslation() {
                    @Override
                    public void translate(View view, @FloatRange(from = 0, to = 1.0) float percentage) {
                        view.setAlpha(percentage);
                    }
                });

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.first_slide_background)
                        .buttonsColor(R.color.first_slide_buttons)
                        .image(R.drawable.search)
                        .title("Buy your groceries with us")
                        .description("Would you try?")
                        .build(),
                new MessageButtonBehaviour(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showMessage("We provide maximum cashback on our products.");
                    }
                }, "Amazing Offers"));

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.second_slide_background)
                        .buttonsColor(R.color.second_slide_buttons)
                        .image(R.drawable.cart)
                        .title("Fill your cart with unbeatable offers")
                        .description("We are sirius")
                        .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.third_slide_background)
                .buttonsColor(R.color.third_slide_buttons)
                .image(R.drawable.money)
                .title("Save money on every purchase")
                .description("and earn with referrals too")
                .build());
//                new MessageButtonBehaviour(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        showMessage("We provide maximum cashback on our products.");
//                    }
//                }, "Amazing Offers"));

//        addSlide(new SlideFragmentBuilder()
//                .backgroundColor(R.color.second_slide_background)
//                .buttonsColor(R.color.second_slide_buttons)
//                .title("Want more?")
//                .description("Go on")
//                .build());

//        addSlide(new CustomSlide());

//        addSlide(new SlideFragmentBuilder()
//                        .backgroundColor(R.color.third_slide_background)
//                        .buttonsColor(R.color.third_slide_buttons)
//                        .possiblePermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_SMS})
//                        .neededPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
//                        .image(R.drawable.img_equipment)
//                        .title("We provide best tools")
//                        .description("ever")
//                        .build(),
//                new MessageButtonBehaviour(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        showMessage("Try us!");
//                    }
//                }, "Tools"));

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.fourth_slide_background)
                .buttonsColor(R.color.fourth_slide_buttons)
                .title("That's it")
                .description("Would you join us?")
                .build());
    }

    @Override
    public void onFinish() {
        super.onFinish();
        Intent intent =new Intent(this, SelectLocation.class);
        startActivity(intent);
    }


    public void deleteAll()
    {
        try {
            Sqlite_cart helper = new Sqlite_cart(this);
            SQLiteDatabase db = helper.getWritableDatabase();
            // db.delete(TABLE_NAME,null,null);
            //db.execSQL("delete * from"+ TABLE_NAME);
            db.delete("Cart",null,null );
            db.delete("Retailer",null,null );
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}