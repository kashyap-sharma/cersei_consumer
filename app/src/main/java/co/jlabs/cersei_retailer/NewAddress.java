package co.jlabs.cersei_retailer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andreabaccega.widget.FormEditText;

import co.jlabs.cersei_retailer.adapter.GalleryAdapter;


/**
 * Created by JLabs on 06/28/16.
 */
public class NewAddress  extends Fragment implements GalleryAdapter.OnFragmentInteractionListener{
    FormEditText name, address, landmark, contact;
    String u,x,y,z;
    int sint;
//    Pizza_Objecto pra_test;
//    Context context;
//    OrderApplication app;
    String n,a,l,c;
    int check=0;
    public NewAddress() {
        // Required empty public constructor
    }
    private OnFragmentInteractionListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static NewAddress inits(int val) {
        NewAddress truitonFrag = new NewAddress();
        // Supply val input as an argument.
        Bundle args = new Bundle();
        args.putInt("val", val);
        truitonFrag.setArguments(args);
        return truitonFrag;
    }


    public void setMyData(int sint,String pin,String con,String land,String add) {
        this.sint = sint;
        this.u = pin;
        this.x = con;
        this.y = land;
        this.z = add;
    }
    public String getMypin() {
       return u;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         // Inflate the layout for this fragment
         final View v=  inflater.inflate(R.layout.new_last_page_content, container, false);
         name=(FormEditText)v.findViewById(R.id.name);
         address=(FormEditText)v.findViewById(R.id.address);
         landmark=(FormEditText)v.findViewById(R.id.landmark);
         contact=(FormEditText)v.findViewById(R.id.contact);
         final Bundle b = getActivity().getIntent().getExtras();
         //int sint = b.getInt("sint");
         int sint =45;
         Log.e("Errrrrrrr",""+sint);
         if (sint==40){
            name.setText(b.getString("pincode"));
            address.setText(b.getString("address"));
            landmark.setText(b.getString("landmark"));
            contact.setText(b.getString("contact"));
         }

         contact.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length()>9&&s.length()<14) {
                     check=123;
                   n=name.getText().toString();
                   a=address.getText().toString();
                   l=landmark.getText().toString();
                   c=contact.getText().toString();
                    onClickNext(v);

                } else {

                }
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {


                // TODO Auto-generated method stub
            }
        });

         return v;
    }

    public void onButtonPressed(int ch,String na,String aa,String la,String ca ) {
        if (mListener != null) {
            mListener.onFragmentInteraction(ch,na, aa, la,ca);
        }
    }

    @Override
    public void onFragmentInteraction(int ch,String userContent, String add, String land,String cont) {
        Log.e("some2",""+ch);


        if (ch==123) {
            Log.e("some2",""+ch);
        } else {
            Log.e("some",""+add);
        }
    }

    public void onClickNext(View v) {
        FormEditText[] allFields    = { name, address, contact, landmark };


        boolean allValid = true;
        for (FormEditText field: allFields) {
            allValid = field.testValidity() && allValid;
        }

        if (allValid) {
            onButtonPressed(check,n, a, l,c);

        } else {
            // EditText are going to appear with an exclamation mark and an explicative message.
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(int ch, String names, String add, String la, String land);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
