package co.jlabs.cersei_retailer.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import co.jlabs.cersei_retailer.R;

import co.jlabs.cersei_retailer.Rounded.CircularImageView;
import co.jlabs.cersei_retailer.Rounded.RoundedImageView;
import co.jlabs.cersei_retailer.StaticCatelog;
import co.jlabs.cersei_retailer.custom_components.ButtonModarno;
import co.jlabs.cersei_retailer.custom_components.TextViewModernM;

public class ShareNEarn extends AppCompatActivity implements View.OnClickListener {

    private TextViewModernM txt;
    private CoordinatorLayout coordinatorLayout;
    private TextViewModernM ref_code;
    private CircularImageView logo;
    private ImageView cross;
    private ButtonModarno share;
    String getstring;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_nearn);
        context=this;
        initView();

    }

    private void initView() {
        txt = (TextViewModernM) findViewById(R.id.txt);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .activity_share_nearn);
        ref_code = (TextViewModernM) findViewById(R.id.ref_code);
        logo = (CircularImageView) findViewById(R.id.logo);
        cross = (ImageView) findViewById(R.id.cross);
        share = (ButtonModarno) findViewById(R.id.share);
        if(StaticCatelog.getStringProperty(context,"api_key")!=null){
            ref_code.setText(""+StaticCatelog.getStringProperty(context,"referral_code"));
        }
        share.setOnClickListener(this);
        ref_code.setOnClickListener(this);
        cross.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share:
                shareApp();
                break;
            case R.id.ref_code:
                copyString();
                break;
            case R.id.cross:
                onBackPressed();
                break;

        }
    }
    public void copyString(){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        getstring = ref_code.getText().toString();
        ClipData clip = ClipData.newPlainText("Copied Text", getstring);
        clipboard.setPrimaryClip(clip);
        Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Referral code copied! ", Snackbar.LENGTH_SHORT);
        snackbar1.show();
    }
    public void shareApp(){
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Thiscounts");
            String sAux = "\nDownload this app and earn cashbacks\n\n";
            sAux = sAux + "http://jlabs.co/this_cus/app.apk \n\n"+"\n\nJust put this referral code: "+ref_code.getText().toString().trim();
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "Refer your friends"));
        } catch(Exception e) {
            //e.toString();
        }
    }
}
