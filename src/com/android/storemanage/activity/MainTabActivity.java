package com.android.storemanage.activity;

import com.android.storemanage.R;
import com.android.storemanage.fragment.ClassifyFragment;
import com.android.storemanage.fragment.FrontPageFragment;
import com.android.storemanage.fragment.MessageFragment;
import com.android.storemanage.fragment.MyFragment;
import com.android.storemanage.service.MyTipMessageService;
import com.android.storemanage.service.TipMessageService;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author liujiao
 */
public class MainTabActivity extends FragmentActivity {
	// FragmentTabHost
	private FragmentTabHost mTabHost;
	private ImageView image_new,new_img_xiaoxi,new_img_wode;
	private String userId;
	private MyReceiver receiver;
	private TipMessageService tip;
	private MyTipMessageService mytip;
	//
	private LayoutInflater layoutInflater;
	private long lastTime = 0;
	private static final String ACTION="com.lsh.newtip";

	// Fragment
	private Class fragmentArray[] = { FrontPageFragment.class,
			ClassifyFragment.class, MessageFragment.class, MyFragment.class };

	private int mImageViewArray[] = { R.drawable.tab_home_btn,
			R.drawable.tab_message_btn, R.drawable.tab_selfinfo_btn,
			R.drawable.tab_square_btn };

	private String mTextviewArray[] = { "首页", "分类", "消息", "我的" };

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_tab_layout);
		userId = getIntent().getStringExtra("userId");
		gotoTipMessage();//开启消息提醒
		gotoMyTipMessage();
		//注册广播
		registerReceiver();
		initView();
		
	}
	private void registerReceiver(){
		receiver = new MyReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION);
		registerReceiver(receiver, filter);
		registerReceiver1();
	}
	private void registerReceiver1(){
		receiver = new MyReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.lsh.newtip_my");
		registerReceiver(receiver, filter);
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
			TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i])
					.setIndicator(getTabItemView(i));
			// ��Tab��ť��ӽ�Tabѡ���
			mTabHost.addTab(tabSpec, fragmentArray[i], null);
			mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
				
				@Override
				public void onTabChanged(String tabId) {
					System.out.println("------------tabId"+tabId);
					if("消息".equals(tabId)){
						new_img_xiaoxi.setVisibility(View.INVISIBLE);
					}
					if("我的".equals(tabId)){
						new_img_wode.setVisibility(View.INVISIBLE);
					}
					
				}
			});
			// ����Tab��ť�ı���
			// mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);
		}
	}

	/**
	 * 
	 */
	private View getTabItemView(int index) {
		View view = layoutInflater.inflate(R.layout.tab_item_view, null);
		image_new = (ImageView)view.findViewById(R.id.message_new);
		ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
		if(index == 2){
			new_img_xiaoxi = image_new;
		}
		if(index == 3){
			new_img_wode = image_new;
		}
		imageView.setImageResource(mImageViewArray[index]);

		TextView textView = (TextView) view.findViewById(R.id.textview);
		textView.setText(mTextviewArray[index]);
		return view;
	}

	@Override
	public void onBackPressed() {
		if (System.currentTimeMillis() - lastTime < 3000) {
//			Intent i = new Intent(this,TipMessageService.class);
//			Intent i2 = new Intent(this,MyTipMessageService.class);
//			stopService(i);
//			stopService(i2);
			MainTabActivity.this.finish();
		} else {
			lastTime = System.currentTimeMillis();
			Toast.makeText(this, "再次按下退出程序", Toast.LENGTH_SHORT).show();
		}
	}

	// 通知广播
	public class MyReceiver extends BroadcastReceiver {

		public MyReceiver() {
		}

		// 自定义一个广播接收器
		@Override
		public void onReceive(Context context, Intent intent) {
			if("com.lsh.newtip".equals(intent.getAction())){
				new_img_xiaoxi.setVisibility(View.VISIBLE);
				// 处理接收到的内容
			}else{
				new_img_wode.setVisibility(View.VISIBLE);
			}

		}
	}
	private void gotoTipMessage(){
		Intent itt = new Intent(this, TipMessageService.class);
//		startService(itt);
		bindService(itt, connection,BIND_AUTO_CREATE);
	}
	private void gotoMyTipMessage(){
		Intent itt1 = new Intent(this, MyTipMessageService.class);
		itt1.putExtra("userId", userId);
//		startService(itt1);
		bindService(itt1, connection2,BIND_AUTO_CREATE);
	}
	 ServiceConnection connection = new ServiceConnection() {  
	       
			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				tip = ((TipMessageService.ServiceBinder) service).getService();
				tip.getMessage();
			}
			@Override
			public void onServiceDisconnected(ComponentName name) {
				
			}  
	    };  
	    ServiceConnection connection2 = new ServiceConnection() {  
		       
			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				mytip = ((MyTipMessageService.ServiceBinder) service).getService();
				mytip.getMyMessage(userId);
			}
			@Override
			public void onServiceDisconnected(ComponentName name) {
				
			}  
	    };  
}
