package co.jlabs.cersei_retailer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import java.util.List;

import co.jlabs.cersei_retailer.R;
import co.jlabs.cersei_retailer.Rounded.MyIconButton;
import co.jlabs.cersei_retailer.Rounded.MyIconFonts;
import co.jlabs.cersei_retailer.activity.AddressFiller;
import co.jlabs.cersei_retailer.fragmentsInitialiser.Image;


/**
 * Created by Lincoln on 31/03/16.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {
    public int mSelectedItem = -1;
    private List<Image> images;
    private Context mContext,con;
    private OnFragmentInteractionListener mListener;
    private int lastCheckedPosition = -1;
    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public MyIconFonts pincode,phone,address,area;
        public MyIconButton edit,remove;
        public RadioButton mRadio;
        public RelativeLayout savvy;

        public MyViewHolder(View view) {
            super(view);
            mRadio = (RadioButton) view.findViewById(R.id.radio);
            pincode = (MyIconFonts) view.findViewById(R.id.pincode);
            phone = (MyIconFonts) view.findViewById(R.id.contact);
            address = (MyIconFonts) view.findViewById(R.id.address);
            area = (MyIconFonts) view.findViewById(R.id.area);
            edit =(MyIconButton)view.findViewById(R.id.edit);
            remove =(MyIconButton)view.findViewById(R.id.remove);
            savvy=(RelativeLayout)view.findViewById(R.id.savvy);

        }
    }


    public GalleryAdapter(Context context, List<Image> images) {
        mContext = context;
        this.images = images;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.saved_adapter, parent, false);

        return new MyViewHolder(itemView);
    }
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(int ch, String names, String add, String la, String land);
    }



    public void removeAt(int position) {
        images.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, images.size());
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Image image = images.get(position);
        holder.savvy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastCheckedPosition = position;
                notifyItemRangeChanged(0, images.size());
                Log.e("Hips",""+lastCheckedPosition);

            }
        });
        holder.mRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastCheckedPosition = position;
                notifyItemRangeChanged(0, images.size());
                Log.e("Hips",""+lastCheckedPosition);

            }
        });
        holder.mRadio.setChecked(position==lastCheckedPosition);
        holder.pincode.setText(mContext.getResources().getString(R.string.pincode)+""+image.getPincode());
        holder.phone.setText(mContext.getResources().getString(R.string.contact)+""+image.getPhone());
        holder.area.setText(mContext.getResources().getString(R.string.area)+""+image.getArea());
        holder.address.setText(mContext.getResources().getString(R.string.addresses)+""+image.getAddress());
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeAt(position);
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  a=images.get(position).getAddress();
                String  b=images.get(position).getArea();
                String  c=images.get(position).getPhone();
                String  d=images.get(position).getPincode();
                image.setA(a);
                onEditPressed(123,a, b, c,d);
                Intent intent= new Intent(mContext, AddressFiller.class);
                intent.putExtra("sint",40);
                intent.putExtra("address",a);
                intent.putExtra("landmark",b);
                intent.putExtra("contact",c);
                intent.putExtra("pincode",d);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
                //onEditPressed(123,a, b, c,d);

            }
        });
    }







    public void onEditPressed(int ch,String na,String aa,String la,String ca ) {
        if (mListener != null) {
            mListener.onFragmentInteraction(ch,na, aa, la,ca);

        }
    }
    @Override
    public int getItemCount() {
        return images.size();
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private GalleryAdapter.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final GalleryAdapter.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}