package yi.yidemo.com;


import com.example.customview_plate_keyboard.R;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView tv_dignotics_result_msg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv_dignotics_result_msg = (TextView)findViewById(R.id.tv_dignotics_result_msg);
		Drawable drawable = getResources().getDrawable(R.drawable.onstar_arrow_right);
		drawable.setBounds(0,0,drawable.getMinimumWidth(),(int)(drawable.getMinimumHeight()));
		tv_dignotics_result_msg.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
	}

}
