package co.jlabs.cersei_retailer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import co.jlabs.cersei_retailer.adapter.GalleryAdapter;
import co.jlabs.cersei_retailer.fragmentsInitialiser.Image;


/**
 * Created by JLabs on 06/28/16.
 */
public class SavedAddress  extends Fragment {
    private String TAG = SavedAddress.class.getSimpleName();
    private static final String endpoint = "http://lannister-api.elasticbeanstalk.com/tyrion/address?vendor_id=1&email=";
    private ArrayList<Image> images;
    private ProgressDialog pDialog;
    private GalleryAdapter mAdapter;
    private RecyclerView recyclerView;
    String a,b,c,d;
    private OnFragmentInteractionListener mListener;
    public SavedAddress() {
        // Required empty public constructor
    }
    static AddressFiller newInstance() {
        AddressFiller f = new AddressFiller();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.saved_address, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        pDialog = new ProgressDialog(getActivity());
        images = new ArrayList<>();
        mAdapter = new GalleryAdapter(getActivity().getApplicationContext(), images);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new GalleryAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
               // Bundle bundle = new Bundle();
//                Bundle bundleSa = new Bundle();
//                bundleSa.putSerializable("image", images.get(position));
//                bundleSa.putInt("position", position);
                a=images.get(position).getAddress();
                b=images.get(position).getArea();
                c=images.get(position).getPhone();
                d=images.get(position).getPincode();
                onButtonPressed(123,a, b, c,d);
//                bundleSa.putString("hi","hello");
//                Intent intent = getActivity().getIntent();
//                intent.putExtras(bundleSa);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        fetchImages();
        return v;
    }

    public void onButtonPressed(int ch,String na,String aa,String la,String ca ) {
        if (mListener != null) {
            mListener.onFragmentInteraction(ch,na, aa, la,ca);
        }
    }
    private void fetchImages() {
        String tag_json_obj = "json_obj_req";
        pDialog.setMessage("Downloading json...");
        pDialog.show();
       // String email = StaticCatelog.getStringProperty(getContext(), "email");
        String email = "kashyap.sharma@jlabs.co";
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, endpoint+email, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d(TAG, response.toString());
                                pDialog.hide();
                                images.clear();
                                try {
                                    JSONArray object1 = response.getJSONArray("data");
                                    for (int i = 0; i < object1.length(); i++) {
                                        JSONArray arr = object1.getJSONArray(i);
                                        for (int j=0;j<arr.length(); j++) {
                                            JSONObject object = arr.getJSONObject(j);
                                            Image image = new Image();
                                            image.setPincode(object.getString("pincode"));
                                            image.setPhone(object.getString("phone"));
                                            image.setAddress(object.getString("address"));
                                            image.setArea(object.getString("area"));
                                            images.add(image);
                                        }
                                    }

                                } catch (JSONException e) {
                                    Log.e(TAG, "Tigeon" + "Json parsing error: " + e.getMessage());
                                }
                                mAdapter.notifyDataSetChanged();

                            }
                        });

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                pDialog.hide();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req,tag_json_obj);
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
