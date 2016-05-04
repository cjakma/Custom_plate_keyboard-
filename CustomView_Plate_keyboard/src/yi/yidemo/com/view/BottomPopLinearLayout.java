package yi.yidemo.com.view;

import java.util.Arrays;
import java.util.List;

import com.example.customview_plate_keyboard.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

/**
 * Created by 6335yz on 2016/4/27.
 */
public class BottomPopLinearLayout extends LinearLayout  {

	private View view;
	private GridView gridView;
	private Button btnFinish;
	public BottomPopLinearLayout(Context context) {
		super(context, null);
		init(context);
	}

	@SuppressLint("NewApi")
	public BottomPopLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
		init(context);
	}

	@SuppressLint("NewApi")
	public BottomPopLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr, 0);
		init(context);
	}

	private void init(Context context) {
		view = LayoutInflater.from(context).inflate(R.layout.layout_bottom_pop, null, true);
		gridView = (GridView) view.findViewById(R.id.gridView);
		gridView.setAdapter(new ImageAdapter(context));
		gridView.setFocusable(false);
		btnFinish = (Button) view.findViewById(R.id.btn_finish);
		addView(view);
	}
	
	private PopItemClickListener popItemClickListener;
	
	public interface PopItemClickListener {
		void onPopItemClickListener(String plateType);
	}
	
	public void setPopItemClickListener(PopItemClickListener popItemClickListener) {
		this.popItemClickListener = popItemClickListener;
	}
	
	public void setOnFinishButtonClickListener(View.OnClickListener clickListener) {
		btnFinish.setOnClickListener(clickListener);
	}
	enum PlateType{
		shanghai("沪"),
		beijing("京"),
		浙江("浙");
		private String value;
		private PlateType(String value) {
			this.value = value;
		}
	}
//	private EnumSet<PlateType> set = EnumSet.allOf(PlateType.class);
	
	private List<String> list = Arrays.asList("沪","浙","京","广","沪","浙","京","广","沪","浙","京","广","沪","浙");
	private class ImageAdapter extends BaseAdapter {

		
		private Context context;
		public ImageAdapter(Context context) {
			this.context = context;
		}
		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder=null;
			
			if(convertView == null) {
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(R.layout.layout_bottom_item, null);
				viewHolder.btn_plate_city = (Button)convertView.findViewById(R.id.btn_plate_city);
				convertView.setTag(viewHolder);
			}else {
				viewHolder = (ViewHolder)convertView.getTag();
			}
			/**
			 * custom position the last finish button in the bottom right of the grid view
			 */
//			if(list.get(position).equals("")) {
//				convertView.setVisibility(View.INVISIBLE);
//			}
//			else {
//				if(position == list.size() - 1) {
//					RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, 140);  
//					viewHolder.btn_plate_city.setBackgroundResource(R.drawable.plate_finish);
//					viewHolder.btn_plate_city.setText(list.get(position));
//					viewHolder.btn_plate_city.setLayoutParams(params);
//					final int height = viewHolder.btn_plate_city.getWidth();
//					final int width = viewHolder.btn_plate_city.getHeight();
//					final View parentView = (View) viewHolder.btn_plate_city.getParent();
//					parentView.post(new Runnable() {
//						@Override
//						public void run() {
//							AbsListView.LayoutParams params = new AbsListView.LayoutParams((int)(parentView.getWidth() * 1.5), parentView.getHeight());  
//							parentView.setLayoutParams(params);
//						}
//					});
//				}else {
//					viewHolder.btn_plate_city.setText(list.get(position));
//					viewHolder.btn_plate_city.setOnClickListener(onClickListener);
//				}
//			}
			final String plateType = list.get(position);
			viewHolder.btn_plate_city.setText(plateType);
			viewHolder.btn_plate_city.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.d("BottomPopLinearLayout", "plateType" + plateType);
					popItemClickListener.onPopItemClickListener(plateType);
				}
			});
			return convertView;
		}
		
		public final class ViewHolder{
			private Button btn_plate_city ;
		}
	}
}
