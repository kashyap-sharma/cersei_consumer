package co.jlabs.cersei_retailer.trey;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import co.jlabs.cersei_retailer.R;


/**
 * Created by shamyl on 7/11/16.
 */
public class MultiTypeDemoAdapter extends SectioningAdapter {

	static final String TAG = MultiTypeDemoAdapter.class.getSimpleName();
	JSONObject datas;
	static final int USER_HEADER_TYPE_0 = 0;
	static final int USER_HEADER_TYPE_1 = 1;

	static final int USER_ITEM_TYPE_0 = 0;
	static final int USER_ITEM_TYPE_1 = 1;

	static final int USER_FOOTER_TYPE_0 = 0;
	static final int USER_FOOTER_TYPE_1 = 1;

	class Item {
		int type;
		String title;
		String detail;
		double cashback;

		public Item(int type, String title,String detail,double cashback) {
			this.type = type;
			this.title = title;
			this.detail = detail;
			this.cashback = cashback;
		}

		public int getType() {
			return type;
		}
		public double getCashback() {
			return cashback;
		}

		public String getTitle() {
			return title;
		}
		public String getDetail() {
			return detail;
		}
	}

	class Footer {
		int type;
		String title;

		public Footer(int type, String title) {
			this.type = type;
			this.title = title;
		}

		public int getType() {
			return type;
		}

		public String getTitle() {
			return title;
		}
	}

	class Section {
		int type;
		String title;
		ArrayList<Item> items = new ArrayList<>();
		Footer footer;

		public Section(int type, String title) {
			this.type = type;
			this.title = title;
		}

		public int getType() {
			return type;
		}

		public String getTitle() {
			return title;
		}

		public ArrayList<Item> getItems() {
			return items;
		}
		public Footer getFooter() {
			return footer;
		}

	}

	///////////////

	public class ItemViewHolder0 extends SectioningAdapter.ItemViewHolder {
		TextView textView;
		TextView cashback;
		TextView detail;
		ImageView up;
		RelativeLayout relative;

		public ItemViewHolder0(View itemView) {
			super(itemView);
			textView = (TextView) itemView.findViewById(R.id.month);
			cashback = (TextView) itemView.findViewById(R.id.cashback);
			detail = (TextView) itemView.findViewById(R.id.detail);
			up = (ImageView) itemView.findViewById(R.id.up);
			relative = (RelativeLayout) itemView.findViewById(R.id.relative);
		}
	}

	public class HeaderViewHolder0 extends SectioningAdapter.HeaderViewHolder {
		TextView textView;

		public HeaderViewHolder0(View itemView) {
			super(itemView);
			textView = (TextView) itemView.findViewById(R.id.month);
		}
	}
	public class FooterViewHolder0 extends SectioningAdapter.FooterViewHolder {
		TextView textView;

		public FooterViewHolder0(View itemView) {
			super(itemView);
			textView = (TextView) itemView.findViewById(R.id.textView);
		}
	}
	public class FooterViewHolder1 extends SectioningAdapter.FooterViewHolder {
		TextView textView;

		public FooterViewHolder1(View itemView) {
			super(itemView);
			textView = (TextView) itemView.findViewById(R.id.textView);
		}
	}

	///////////////

	public class ItemViewHolder1 extends SectioningAdapter.ItemViewHolder {
		TextView textView;
		TextView cashback;
		TextView detail;
		ImageView up;
		RelativeLayout relative;

		public ItemViewHolder1(View itemView) {
			super(itemView);
			textView = (TextView) itemView.findViewById(R.id.month);
			cashback = (TextView) itemView.findViewById(R.id.cashback);
			detail = (TextView) itemView.findViewById(R.id.detail);
			up = (ImageView) itemView.findViewById(R.id.up);
			relative = (RelativeLayout) itemView.findViewById(R.id.relative);
		}
	}

	public class HeaderViewHolder1 extends SectioningAdapter.HeaderViewHolder {
		TextView textView;

		public HeaderViewHolder1(View itemView) {
			super(itemView);
			textView = (TextView) itemView.findViewById(R.id.month);
		}
	}



	Random rng;
	ArrayList<Section> sections;

	public MultiTypeDemoAdapter(JSONObject data) {
		this.datas=data;
		rng = new Random();
		sections = new ArrayList<>();
		int numSections= 0;
		int numItemsPerSection=0;
		JSONArray orders;
		try {
			numSections = data.getJSONObject("cashback_history").length();
			numItemsPerSection = data.getJSONObject("cashback_history").length();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		for (int s = 0; s < numSections; s++) {
			int sectionType = s % 2;
			try {
				Log.v(TAG, "key = " + data.getJSONObject("cashback_history").names().getString(s) + " value = " + data.getJSONObject("cashback_history").get(data.getJSONObject("cashback_history").names().getString(0)));
				//String sectionName=data.getJSONObject("cashback_history").getJSONObject('').toString();
				Section section = new Section(sectionType, data.getJSONObject("cashback_history").names().getString(s));
				orders=data.getJSONObject("cashback_history").getJSONArray(data.getJSONObject("cashback_history").names().getString(s));
				numItemsPerSection=orders.length();
				JSONArray items;
				for (int i = 0; i < numItemsPerSection; i++) {
					items=orders.getJSONObject(i).getJSONArray("items");
					String details="";
					for (int j = 0; j < items.length(); j++){
						details=details+items.getJSONObject(j).getString("name")+" "+items.getJSONObject(j).getString("weight")+" ₹"+items.getJSONObject(j).getDouble("total")+"\n"+"From "+items.getJSONObject(j).getString("retailer_name")+"\n";
					}
					section.items.add(new Item(sectionType, orders.getJSONObject(i).getString("timestamp"),details,orders.getJSONObject(i).getDouble("cashback")));
				}
				section.footer = new Footer(sectionType, "Footer for section: " + s);
				sections.add(section);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public int getNumberOfSections() {
		return sections.size();
	}

	@Override
	public int getNumberOfItemsInSection(int sectionIndex) {
		return sections.get(sectionIndex).items.size();
	}

	@Override
	public boolean doesSectionHaveHeader(int sectionIndex) {
		return true;
	}

	@Override
	public boolean doesSectionHaveFooter(int sectionIndex) {
		return true;
	}

	@Override
	public int getSectionHeaderUserType(int sectionIndex) {
		return sections.get(sectionIndex).getType();
	}

	@Override
	public int getSectionItemUserType(int sectionIndex, int itemIndex) {
		Section section = sections.get(sectionIndex);
		return section.items.get(itemIndex).getType();
	}


	@Override
	public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int itemType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());

		switch (itemType) {
			case USER_ITEM_TYPE_0:
				return new ItemViewHolder0(inflater.inflate(R.layout.item_adapter, parent, false));

			case USER_ITEM_TYPE_1:
				return new ItemViewHolder1(inflater.inflate(R.layout.item_adapter, parent, false));
		}

		throw new IllegalArgumentException("Unrecognized itemType: " + itemType);
	}

	@Override
	public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());

		switch (headerType) {
			case USER_HEADER_TYPE_0:
				return new HeaderViewHolder0(inflater.inflate(R.layout.item1, parent, false));

			case USER_HEADER_TYPE_1:
				return new HeaderViewHolder1(inflater.inflate(R.layout.item1, parent, false));
		}

		throw new IllegalArgumentException("Unrecognized headerType: " + headerType);
	}
	@Override
	public FooterViewHolder onCreateFooterViewHolder(ViewGroup parent, int footerType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());

		switch (footerType) {
			case USER_FOOTER_TYPE_0:
				return new FooterViewHolder0(inflater.inflate(R.layout.it, parent, false));

			case USER_FOOTER_TYPE_1:
				return new FooterViewHolder1(inflater.inflate(R.layout.it, parent, false));
		}

		throw new IllegalArgumentException("Unrecognized footerType: " + footerType);
	}


	@Override
	public void onBindItemViewHolder(SectioningAdapter.ItemViewHolder viewHolder, int sectionIndex, int itemIndex, int itemType) {
		Section s = sections.get(sectionIndex);

		switch (itemType) {
			case USER_ITEM_TYPE_0: {
				final ItemViewHolder0 ivh = (ItemViewHolder0) viewHolder;
				ivh.textView.setText(s.items.get(itemIndex).getTitle());
				ivh.cashback.setText("Cashback ₹"+s.items.get(itemIndex).getCashback());
				ivh.detail.setText("Purchased "+s.items.get(itemIndex).getDetail());
				ivh.relative.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if(ivh.detail.getVisibility()==View.GONE){
							ivh.detail.setVisibility(View.VISIBLE);
							ivh.up.setRotation(180);
						}else {
							ivh.detail.setVisibility(View.GONE);
							ivh.up.setRotation(0);

						}
					}
				});
				break;
			}
			case USER_ITEM_TYPE_1: {
				final ItemViewHolder1 ivh = (ItemViewHolder1) viewHolder;
				ivh.textView.setText(s.items.get(itemIndex).getTitle());
				ivh.cashback.setText("Cashback ₹"+s.items.get(itemIndex).getCashback());
				ivh.detail.setText("Purchased "+s.items.get(itemIndex).getDetail());
				ivh.relative.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if(ivh.detail.getVisibility()==View.GONE){
							ivh.detail.setVisibility(View.VISIBLE);
							ivh.up.setRotation(180);
						}else {
							ivh.detail.setVisibility(View.GONE);
							ivh.up.setRotation(0);
						}
					}
				});
				break;
			}

			default:
				throw new IllegalArgumentException("Unrecognized itemType: " + itemType);
		}

	}

	@Override
	public void onBindHeaderViewHolder(SectioningAdapter.HeaderViewHolder viewHolder, int sectionIndex, int headerType) {
		Section s = sections.get(sectionIndex);

		switch (headerType) {
			case USER_HEADER_TYPE_0: {
				HeaderViewHolder0 hvh = (HeaderViewHolder0) viewHolder;
				hvh.textView.setText(s.getTitle());
				break;
			}
			case USER_HEADER_TYPE_1: {
				HeaderViewHolder1 hvh = (HeaderViewHolder1) viewHolder;
				hvh.textView.setText(s.getTitle());
				break;
			}

			default:
				throw new IllegalArgumentException("Unrecognized headerType: " + headerType);
		}

	}

	@Override
	public void onBindFooterViewHolder(SectioningAdapter.FooterViewHolder viewHolder, int sectionIndex, int footerType) {
		Section s = sections.get(sectionIndex);

		switch (footerType) {
			case USER_FOOTER_TYPE_0: {
				FooterViewHolder0 fvh = (FooterViewHolder0) viewHolder;
				fvh.textView.setText(s.footer.getTitle());
				break;
			}
			case USER_FOOTER_TYPE_1: {
				FooterViewHolder1 fvh = (FooterViewHolder1) viewHolder;
				fvh.textView.setText(s.footer.getTitle());
				break;
			}

			default:
				throw new IllegalArgumentException("Unrecognized footerType: " + footerType);
		}

	}

}
