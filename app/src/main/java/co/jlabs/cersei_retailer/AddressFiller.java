package co.jlabs.cersei_retailer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class AddressFiller extends AppCompatActivity {

    private RadioGroup rg;
   TextView hide_newd, hide_svd;
    Boolean bx=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address);
        rg=(RadioGroup)findViewById(R.id.rg1);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
            // find which radio button is selected
                if(checkedId == R.id.rd1) {
                    Toast.makeText(getApplicationContext(), "choice: California",
                            Toast.LENGTH_SHORT).show();
                } else if(checkedId == R.id.rd2) {
                    Toast.makeText(getApplicationContext(), "choice: Chandani Chouk",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "choice: Gurgaon",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });



        hide_newd=(TextView)findViewById(R.id.hide_newd);
        hide_svd=(TextView)findViewById(R.id.hide_svd);
        hide_svd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               switcher(bx);

            }
        });


    }

    public void switcher(boolean b){
        if(b){
            rg.setVisibility(View.GONE);
        }
        else
            Log.i("sss", "sss");

    }
}
