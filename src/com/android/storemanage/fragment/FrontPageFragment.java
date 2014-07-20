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
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.GridLayout.LayoutParams;
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
	private FrontFotruneAdapter adapter;
	/** 将小圆点的图片用数组表示 */
	private ImageView[] imageViews;
	private GridView gridview;
	// 包裹小圆点的LinearLayout
	private LinearLayout mViewPoints;
	private ImageView imageView;
	private int[] colors = new int[] { R.color.color_3, R.color.color_1,
			R.color.color_2, R.color.color_3, R.color.color_4, R.color.color_5,
			R.color.color_6, R.color.color_7 };

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
		initData(view);
		return view;
	}

	@Override
	public void onResume() {
		initData();
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
								gridview.setAdapter(new WealthAdapter(
										getActivity(), wealthEntities));
								// createNavMenu1(wealthEntities, view);
								ArrayList<CategoryEntity> categoryEntities = commonData
										.getCategoryMapList();
								if (null == adapter) {
									adapter = new FrontFotruneAdapter(
											getChildFragmentManager(),
											categoryEntities);
									initDots(categoryEntities.size());
									viewPager.setAdapter(adapter);
									viewPager.setOffscreenPageLimit(1);
								} else {
									adapter.notifyDataSetChanged();
								}
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

	protected void initDots(int size) {
		// 创建imageviews数组，大小是要显示的图片的数量
		size = size % JFConfig.PAGE_COUNT == 0 ? size / JFConfig.PAGE_COUNT
				: size / JFConfig.PAGE_COUNT + 1;
		imageViews = new ImageView[size];
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
									// Intent intent = new Intent(getActivity(),
									// cls)
									Intent intent = new Intent();
									intent.setAction("android.intent.action.VIEW");
									Uri content_url = Uri.parse(resultString);
									intent.setData(content_url);
									getActivity().startActivity(intent);
								}
							} else {
								dialog.show("服务器内部错误", 2000);
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

	/**
	 * create menu by {@link MenuListEntity}
	 * 
	 * @param menuEntity
	 */
	protected void createNavMenu1(List<WealthEntity> wealthEntities, View v) {
		int maxRows = wealthEntities.size() % 2 == 0 ? wealthEntities.size() / 2
				: wealthEntities.size() / 2 + 1;
		GridLayout gridLayout = (GridLayout) v
				.findViewById(R.id.menu_gridlayout);
		gridLayout.removeAllViews();
		gridLayout.setRowCount(maxRows > 4 ? 4 : maxRows);
		gridLayout.setColumnCount(4);
		GridLayout.LayoutParams lp = null;
		GridLayout.Spec spec = null;

		int gridLayoutWidth = width - gridLayout.getPaddingLeft()
				- gridLayout.getPaddingRight();
		int padding = 6;
		gridLayout.setPadding(padding, 0, padding, 0);
		int itemSpace = 6;
		int itemWidth = (gridLayoutWidth - padding * 2
				- gridLayout.getPaddingLeft() - gridLayout.getPaddingRight() - itemSpace * 2) / 2;
		for (int index = 0; index < wealthEntities.size(); index++) {
			WealthEntity entity = wealthEntities.get(index);
			RelativeLayout ll = (RelativeLayout) getActivity()
					.getLayoutInflater().inflate(R.layout.icon_menu_item,
							gridLayout, false);
			ll.setOnClickListener(this);
			ll.setTag(entity);
			ll.setBackgroundResource(R.drawable.selector_gridlayout_bg);

			TextView label = (TextView) ll
					.findViewById(R.id.description_textView);
			label.setTextColor(getResources().getColor(colors[index]));
			label.setText(entity.getWealthareaTitle());
			ImageView tvIsNew = (ImageView) ll.findViewById(R.id.iv_is_new);
			if (!TextUtils.isEmpty(entity.getWealthareaSfnew())
					&& "1".equals(entity.getWealthareaSfnew())) {
				tvIsNew.setVisibility(View.VISIBLE);
			} else {
				tvIsNew.setVisibility(View.INVISIBLE);
			}
			lp = new GridLayout.LayoutParams(ll.getLayoutParams());
			lp.width = itemWidth;
			lp.height = LayoutParams.WRAP_CONTENT;
			lp.leftMargin = lp.rightMargin = lp.topMargin = lp.bottomMargin = itemSpace;
			spec = GridLayout
					.spec(GridLayout.UNDEFINED, 2, GridLayout.BASELINE);
			lp.columnSpec = spec;
			spec = GridLayout
					.spec(GridLayout.UNDEFINED, 2, GridLayout.BASELINE);
			lp.rowSpec = spec;
			ll.setLayoutParams(lp);
			gridLayout.addView(ll);

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
			if(null != entity){
				DataSaveEntity tempDataSaveEntity = new DataSaveEntity();
				tempDataSaveEntity.setId(entity.getWealthareaId());
				tempDataSaveEntity.setTime(entity.getWealthareaOpptime()+"");
				db.insertDataSaveEntity("FrontPage", tempDataSaveEntity);
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
