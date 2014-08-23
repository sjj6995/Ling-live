package com.android.storemanage.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.storemanage.R;
import com.android.storemanage.activity.WealthDetailActivity;
import com.android.storemanage.activity.WealthPrizeActivity;
import com.android.storemanage.adapter.FrontFotruneAdapter;
import com.android.storemanage.adapter.WealthAdapter;
import com.android.storemanage.entity.CategoryEntity;
import com.android.storemanage.entity.CollectionData;
import com.android.storemanage.entity.DataSaveEntity;
import com.android.storemanage.entity.InnerData;
import com.android.storemanage.entity.OuterData;
import com.android.storemanage.entity.WealthEntity;
import com.android.storemanage.net.AsyncHttpResponseHandler;
import com.android.storemanage.net.RequestParams;
import com.android.storemanage.net.HttpClient;
import com.android.storemanage.utils.CommonLog;
import com.android.storemanage.utils.CommonUtil;
import com.android.storemanage.utils.DisplayUtil;
import com.android.storemanage.utils.JFConfig;
import com.android.storemanage.utils.PhoneUtil;
import com.android.storemanage.view.CRAlertDialog;
import com.zxing.activity.CaptureActivity;

/**
 * @author liujiao 首页
 * 
 */
public class FrontPageFragment extends BaseFragment implements OnClickListener {

	private TextView imageButton;
	private TextView titleTextView;
	private int width;
	private ViewPager viewPager;
	private static final int REQUEST_SCAN = 1;
	private ImageButton button;
	private TextView mFortune;
	private CommonLog log = CommonLog.getInstance();
	private View view;
	// private FrontFotruneAdapter adapter;
	/** 将小圆点的图片用数组表示 */
	private ImageView[] imageViews;
	private GridView gridview;
	// 包裹小圆点的LinearLayout
	private LinearLayout mViewPoints;
	private ImageView imageView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_frontpage, null);
		}
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);
			return view;
		}
		initViews(view);
		log.i(PhoneUtil.getDeviceId((TelephonyManager) (getActivity()
				.getSystemService(Context.TELEPHONY_SERVICE))));
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		width = dm.widthPixels;// 宽度
		RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) viewPager
				.getLayoutParams();
		params.height = (width - DisplayUtil.dip2px(getActivity(), 2)) / 2
				+ DisplayUtil.dip2px(getActivity(), 5);
		viewPager.setLayoutParams(params);
		// createNavMenu1(view);
		return view;
	}

	@Override
	public void onResume() {
		initData(view);
		// initData();
		super.onResume();
	}

	private void initData() {
		if (CommonUtil.checkNetState(getActivity())) {
			RequestParams params = new RequestParams();
			params.put("userId", application.getUserId());
			showProgressDialog(R.string.please_waiting);
			HttpClient.post(JFConfig.GET_WEALTH_AND_USERAMOUNT, params,
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int statusCode, String content) {
							log.i("content===" + content);
							dismissProgressDialog();
							if (TextUtils.isEmpty(content)) {
								return;
							}
							OuterData outerData = JSON.parseObject(content,
									OuterData.class);
							InnerData innderData = outerData.getData().get(0);
							CollectionData commonData = innderData.getData()
									.get(0);
							log.i("commonData"
									+ commonData.getCommonData().getMsg());
							if ("true".equals(commonData.getCommonData()
									.getReturnStatus())) {
								imageButton.setText("会员："
										+ commonData.getCommonData()
												.getUserAmount());
								mFortune.setText("我的财富："
										+ commonData.getCommonData()
												.getUserwealth());

							} else {
								// CRAlertDialog dialog = new
								// CRAlertDialog(getActivity());
								// dialog.show(commonData.getCommonData().getMsg(),
								// 2000);
							}
						}

						@Override
						public void onFailure(Throwable arg0, String arg1) {
							super.onFailure(arg0, arg1);
							dismissProgressDialog();
						}
					});
		} else {
			CRAlertDialog dialog = new CRAlertDialog(getActivity());
			dialog.show(getString(R.string.pLease_check_network), 2000);
		}

	}

	private void initData(final View view) {
		if (CommonUtil.checkNetState(getActivity())) {
			RequestParams params = new RequestParams();
			params.put("userId", application.getUserId());
			showProgressDialog(R.string.please_waiting);
			HttpClient.post(JFConfig.HOME_PAGE, params,
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int statusCode, String content) {
							log.i("content===" + content);
							dismissProgressDialog();
							if (TextUtils.isEmpty(content)) {
								return;
							}
							OuterData outerData = JSON.parseObject(content,
									OuterData.class);
							InnerData innderData = outerData.getData().get(0);
							CollectionData commonData = innderData.getData()
									.get(0);
							log.i("commonData"
									+ commonData.getCommonData().getMsg());
							if ("true".equals(commonData.getCommonData()
									.getReturnStatus())) {
								List<WealthEntity> wealthEntities = commonData
										.getWealthareaMapList();
								List<DataSaveEntity> tempEntities = db
										.queryAll(JFConfig.FRONT_PAGE);
								fillData(wealthEntities, tempEntities);
								gridview.setAdapter(new WealthAdapter(
										getActivity(), wealthEntities));
								ArrayList<CategoryEntity> categoryEntities = commonData
										.getCategoryMapList();
								initDots(categoryEntities.size());
								viewPager.setAdapter(new FrontFotruneAdapter(
										getChildFragmentManager(),
										categoryEntities));
								viewPager.setOffscreenPageLimit(1);
								viewPager.setCurrentItem(0);
								imageButton.setText("会员："
										+ commonData.getCommonData()
												.getUserAmount());
								mFortune.setText("我的财富："
										+ commonData.getCommonData()
												.getUserwealth());
							} else {
								CRAlertDialog dailog = new CRAlertDialog(
										getActivity());
								dailog.show(
										getString(R.string.server_data_exception),
										2000);
							}
						}

						@Override
						public void onFailure(Throwable arg0, String arg1) {
							super.onFailure(arg0, arg1);
							dismissProgressDialog();
							CommonUtil.onFailure(arg0, getActivity());
						}
					});
		} else {
			CRAlertDialog dialog = new CRAlertDialog(getActivity());
			dialog.show(getString(R.string.pLease_check_network), 2000);
		}
	}

	protected void fillData(List<WealthEntity> wealthEntities,
			List<DataSaveEntity> tempEntities) {
		if (null != tempEntities && tempEntities.size() > 0) {
			for (int i = 0; i < tempEntities.size(); i++) {
				DataSaveEntity temp = tempEntities.get(i);
				String tempId = temp.getId();
				for (int j = 0; j < wealthEntities.size(); j++) {
					WealthEntity entity = wealthEntities.get(j);
					String id = entity.getWealthareaId();
					if (!TextUtils.isEmpty(tempId) && !TextUtils.isEmpty(id)
							&& id.equals(tempId)) {
						entity.setDbOpptime(Long.parseLong(temp.getTime()));
					}
				}

			}
		}

	}

	protected void initDots(int size) {
		// 创建imageviews数组，大小是要显示的图片的数量
		size = size % JFConfig.PAGE_COUNT == 0 ? size / JFConfig.PAGE_COUNT
				: size / JFConfig.PAGE_COUNT + 1;
		imageViews = new ImageView[size];
		mViewPoints.removeAllViews();
		// 添加小圆点的图片
		for (int i = 0; i < size; i++) {
			imageView = new ImageView(getActivity());
			// 设置小圆点imageview的参数
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					16, 16);
			layoutParams.setMargins(5, 0, 5, 0);
			imageView.setLayoutParams(layoutParams);// 创建一个宽高均为20 的布局
			// 将小圆点layout添加到数组中
			imageViews[i] = imageView;
			// 默认选中的是第一张图片，此时第一个小圆点是选中状态，其他不是
			if (i == 0) {
				imageViews[i].setBackgroundResource(R.drawable.dot_pressed);
			} else {
				imageViews[i].setBackgroundResource(R.drawable.dot_normal);
			}
			// 将imageviews添加到小圆点视图组
			mViewPoints.addView(imageViews[i]);
		}

	}

	private void initViews(View view) {
		imageButton = (TextView) view.findViewById(R.id.ib_back);
		titleTextView = (TextView) view.findViewById(R.id.tv_title);
		titleTextView.setText("首页");
		gridview = (GridView) view.findViewById(R.id.gridview);
		// 实例化小圆点的linearLayout和viewpager
		mViewPoints = (LinearLayout) view.findViewById(R.id.viewGroup);
		viewPager = (ViewPager) view.findViewById(R.id.vPager);
		viewPager.setOnPageChangeListener(new NavigationPageChangeListener());
		// viewPager.setPageTransformer(true, new DepthPageTransformer());
		button = (ImageButton) view.findViewById(R.id.imageview);
		button.setOnClickListener(this);
		mFortune = (TextView) view.findViewById(R.id.tv_fortune);
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				WealthEntity entity = (WealthEntity) arg0
						.getItemAtPosition(arg2);
				if (null != entity) {
					DataSaveEntity tempDataSaveEntity = new DataSaveEntity();
					tempDataSaveEntity.setId(entity.getWealthareaId());
					tempDataSaveEntity.setTime(entity.getWealthareaOpptime()
							+ "");
					db.insertDataSaveEntity(JFConfig.FRONT_PAGE,
							tempDataSaveEntity);
					Intent intent = new Intent(getActivity(),
							WealthPrizeActivity.class);
					intent.putExtra("wealthId", entity.getWealthareaId());
					intent.putExtra("wealthTitle", entity.getWealthareaTitle());
					startActivity(intent);
				}
			}
		});
	}

	/**
	 * 扫描二维码
	 */
	private void gotoScanRequest() {
		startActivityForResult(
				new Intent(getActivity(), CaptureActivity.class), REQUEST_SCAN);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null) {
			return;
		}
		if (requestCode == REQUEST_SCAN) {
			if (resultCode == Activity.RESULT_OK) {
				String resultString = data.getStringExtra("result");
				if (!TextUtils.isEmpty(resultString)) {
					sendMessageToServer(resultString);
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * @param resultString
	 *            扫描二维码后发送网络请求
	 */
	private void sendMessageToServer(final String resultString) {
		if (CommonUtil.checkNetState(getActivity())) {
			RequestParams params = new RequestParams();
			params.put("userId", application.getUserId());
			params.put("qrcodeInfo", resultString);
			params.put(JFConfig.LSH_TOKEN, CommonUtil.getMD5(application.getUserId()+JFConfig.COMMON_MD5_STR));
			showProgressDialog(R.string.please_waiting);
			HttpClient.post(JFConfig.SCAN_REQUEST, params,
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int statusCode, String content) {
							log.i("content===" + content);
							dismissProgressDialog();
							if (TextUtils.isEmpty(content)) {
								return;
							}
							OuterData outerData = JSON.parseObject(content,
									OuterData.class);
							InnerData innderData = outerData.getData().get(0);
							CollectionData commonData = innderData.getData()
									.get(0);
							CRAlertDialog dialog = new CRAlertDialog(
									getActivity());
							log.i("commonData"
									+ commonData.getCommonData().getMsg());
							if ("true".equals(commonData.getCommonData()
									.getReturnStatus())) {
								int userAddValue = commonData.getCommonData()
										.getUserAddWealthValue();
								if (userAddValue > 0) {
									dialog.show("您增加了" + userAddValue + "个财富值",
											2000);
								}
							}
							try {
								Intent intent = new Intent();
								intent.setAction("android.intent.action.VIEW");
								Uri content_url = Uri.parse(resultString);
								intent.setData(content_url);
								getActivity().startActivity(intent);

							} catch (Exception e) {
								Intent intent = new Intent(getActivity(),WealthDetailActivity.class);
								intent.putExtra("url", resultString);
								getActivity().startActivity(intent);
							}
						}

						@Override
						public void onFailure(Throwable arg0, String arg1) {
							super.onFailure(arg0, arg1);
							dismissProgressDialog();
							CommonUtil.onFailure(arg0, getActivity());
							try {
								Intent intent = new Intent();
								intent.setAction("android.intent.action.VIEW");
								Uri content_url = Uri.parse(resultString);
								intent.setData(content_url);
								getActivity().startActivity(intent);

							} catch (Exception e) {
								Intent intent = new Intent(getActivity(),WealthDetailActivity.class);
								intent.putExtra("url", resultString);
								getActivity().startActivity(intent);
							}
						}
					});
		} else {
			CRAlertDialog dialog = new CRAlertDialog(getActivity());
			dialog.show(getString(R.string.pLease_check_network), 2000);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imageview:// 扫描二维码
			gotoScanRequest();
			break;
		case R.id.rl_menu:
			WealthEntity entity = (WealthEntity) v.getTag();
			if (null != entity) {
				DataSaveEntity tempDataSaveEntity = new DataSaveEntity();
				tempDataSaveEntity.setId(entity.getWealthareaId());
				tempDataSaveEntity.setTime(entity.getWealthareaOpptime() + "");
				db.insertDataSaveEntity(JFConfig.FRONT_PAGE, tempDataSaveEntity);
			}
			Intent intent = new Intent(getActivity(), WealthPrizeActivity.class);
			intent.putExtra("wealthId", entity.getWealthareaId());
			intent.putExtra("wealthTitle", entity.getWealthareaTitle());
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	private class NavigationPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int position) {
			for (int i = 0; i < imageViews.length; i++) {
				imageViews[position]
						.setBackgroundResource(R.drawable.dot_pressed);
				// 不是当前选中的page，其小圆点设置为未选中的状态
				if (position != i) {
					imageViews[i].setBackgroundResource(R.drawable.dot_normal);
				}
			}
		}

	}

}
