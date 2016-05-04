package yi.yidemo.com;


import com.example.customview_plate_keyboard.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import yi.yidemo.com.view.BottomPopLinearLayout;
import yi.yidemo.com.view.BottomPopLinearLayout.PopItemClickListener;
import yi.yidemo.com.view.TestLinearLayoutClick;

public class BottomPopActivity extends Activity {

	private Button btn_pop, btn_mv;
	private BottomPopLinearLayout bottomPopLinearLayout;
	private int count;
	private Animation animation;
	private TextView tv_plate_type;
	private TestLinearLayoutClick testLinearLayoutClick;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bottom_pop);
		initView();
		btn_pop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (count++ % 2 == 0) {
					openPopKeyboard();
				} else {
					closePopKeyboard();
				}
			}
		});
	}

	private void openPopKeyboard() {
		Animation animation = AnimationUtils.loadAnimation(BottomPopActivity.this, R.anim.bottom_enter);
		/**
		 * enable the click action in the custom view after animation
		 */
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				bottomPopLinearLayout.clearAnimation();
				MarginLayoutParams bottomPopMargins = (MarginLayoutParams) bottomPopLinearLayout.getLayoutParams();
				bottomPopMargins.bottomMargin = 0;
				bottomPopLinearLayout.setLayoutParams(bottomPopMargins);
			}
		});
		bottomPopLinearLayout.setAnimation(animation);
		bottomPopLinearLayout.startAnimation(animation);
	}

	private void closePopKeyboard() {
		Animation animation = AnimationUtils.loadAnimation(BottomPopActivity.this, R.anim.bottom_out);
		/**
		 * enable the click action in the custom view after animation
		 */
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				int height = bottomPopLinearLayout.getMeasuredHeight();
				MarginLayoutParams bottomPopMargins = (MarginLayoutParams) bottomPopLinearLayout.getLayoutParams();
				bottomPopMargins.bottomMargin = -height;
				bottomPopLinearLayout.setLayoutParams(bottomPopMargins);
				bottomPopLinearLayout.clearAnimation();
			}
		});
		bottomPopLinearLayout.setAnimation(animation);
		bottomPopLinearLayout.startAnimation(animation);
	}

	private void initView() {
		btn_pop = (Button) findViewById(R.id.btn_pop);
		bottomPopLinearLayout = (BottomPopLinearLayout) findViewById(R.id.layout_bottom_pop);
//		testLinearLayoutClick = (TestLinearLayoutClick) findViewById(R.id.layout_bottom_test);
//		btn_mv = (Button) findViewById(R.id.btn_mv);
		tv_plate_type = (TextView) findViewById(R.id.tv_plate_type);
		bottomPopLinearLayout.setOnFinishButtonClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				count++;
				closePopKeyboard();				
			}
		});
		bottomPopLinearLayout.setPopItemClickListener(new PopItemClickListener() {
			@Override
			public void onPopItemClickListener(String plateType) {
				count++;
				closePopKeyboard();
				tv_plate_type.setText(String.valueOf(plateType));
			}
		});
	}

	/**
	 * height would be 0 before oncreated method. so init the pop window here.
	 */
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		int height = bottomPopLinearLayout.getMeasuredHeight();
		MarginLayoutParams bottomPopMargins = (MarginLayoutParams) bottomPopLinearLayout.getLayoutParams();
		bottomPopMargins.bottomMargin = -height;
		bottomPopLinearLayout.setLayoutParams(bottomPopMargins);
	}
}
