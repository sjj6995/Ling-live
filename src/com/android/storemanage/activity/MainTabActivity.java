package com.android.storemanage.activity;

import com.android.storemanage.R;
import com.android.storemanage.fragment.ClassifyFragment;
import com.android.storemanage.fragment.FrontPageFragment;
import com.android.storemanage.fragment.MessageFragment;
import com.android.storemanage.fragment.MyFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author liujiao
 */
public class MainTabActivity extends FragmentActivity {
	// FragmentTabHost
	private FragmentTabHost mTabHost;

	//
	private LayoutInflater layoutInflater;
	private long lastTime = 0;

	// Fragment
	private Class fragmentArray[] = { FrontPageFragment.class, ClassifyFragment.class, MessageFragment.class,
			MyFragment.class };

	private int mImageViewArray[] = { R.drawable.tab_home_btn, R.drawable.tab_message_btn, R.drawable.tab_selfinfo_btn,
			R.drawable.tab_square_btn };

	private String mTextviewArray[] = { "首页", "分类", "消息", "我的" };

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_tab_layout);
		initView();
	}

	/**
	 * ��ʼ�����
	 */
	private void initView() {
		// ʵ��ֶ���
		layoutInflater = LayoutInflater.from(this);

		// ʵ��TabHost���󣬵õ�TabHost
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		// �õ�fragment�ĸ���
		int count = fragmentArray.length;

		for (int i = 0; i < count; i++) {
			// Ϊÿһ��Tab��ť����ͼ�ꡢ���ֺ�����
			TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));
			// ��Tab��ť��ӽ�Tabѡ���
			mTabHost.addTab(tabSpec, fragmentArray[i], null);
			// ����Tab��ť�ı���
			// mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);
		}
	}

	/**
	 * 
	 */
	private View getTabItemView(int index) {
		View view = layoutInflater.inflate(R.layout.tab_item_view, null);

		ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
		imageView.setImageResource(mImageViewArray[index]);

		TextView textView = (TextView) view.findViewById(R.id.textview);
		textView.setText(mTextviewArray[index]);
		// textView.setTextColor(R.color.text_selector_goodnumtowhite);
		return view;
	}

	@Override
	public void onBackPressed() {
		if (System.currentTimeMillis() - lastTime < 3000) {
			MainTabActivity.this.finish();
		} else {
			lastTime = System.currentTimeMillis();
			Toast.makeText(this, "再次按下退出程序", Toast.LENGTH_SHORT).show();
		}
	}
}
