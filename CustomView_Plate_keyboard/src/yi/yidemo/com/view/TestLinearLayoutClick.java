package yi.yidemo.com.view;


import com.example.customview_plate_keyboard.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

/**
 * Created by 6335yz on 2016/4/27.
 */
public class TestLinearLayoutClick extends LinearLayout  {

	private View view;
	private Button btn_test;
	private GridView gridView;
	public TestLinearLayoutClick(Context context) {
		super(context, null);
		init(context);
	}

	@SuppressLint("NewApi")
	public TestLinearLayoutClick(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
		init(context);
	}

	@SuppressLint("NewApi")
	public TestLinearLayoutClick(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr, 0);
		init(context);
	}

	private void init(Context context) {
		Log.d("TestLinearLayoutClick", "----------------init-------------- ");
		view = LayoutInflater.from(context).inflate(R.layout.layout_bottom_test, null, true);
		btn_test = (Button) view.findViewById(R.id.btn_test);
		gridView = (GridView) view.findViewById(R.id.gridView);
		btn_test.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("TestLinearLayoutClick", "----------------test-------------- ");
			}
		});
		addView(view);
	}
}
