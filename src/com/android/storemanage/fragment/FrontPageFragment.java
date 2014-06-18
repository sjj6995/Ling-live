package com.android.storemanage.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.GridLayout.LayoutParams;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.storemanage.R;
import com.android.storemanage.activity.WealthPrizeActivity;
import com.android.storemanage.adapter.FrontFotruneAdapter;
import com.android.storemanage.entity.CategoryEntity;
import com.android.storemanage.entity.CollectionData;
import com.android.storemanage.entity.InnerData;
import com.android.storemanage.entity.OuterData;
import com.android.storemanage.entity.WealthEntity;
import com.android.storemanage.net.AsyncHttpResponseHandler;
import com.android.storemanage.net.RequestParams;
import com.android.storemanage.net.XDHttpClient;
import com.android.storemanage.utils.CommonLog;
import com.android.storemanage.utils.CommonUtil;
import com.android.storemanage.utils.JFConfig;
import com.android.storemanage.utils.PhoneUtil;
import com.android.storemanage.view.CRAlertDialog;
import com.wlnet.wl.android.camera.CaptureActivity;

/**
 * @author liujiao 首页
 * 
 */
public class FrontPageFragment extends BaseFragment implements OnClickListener {

	private TextView imageButton;
	private TextView titleTextView;
	private int width;
	private List<String> lists;
	private ViewPager viewPager;
	private static final int REQUEST_SCAN = 1;
	private ImageButton button;
	private TextView mFortune;
	private CommonLog log = CommonLog.getInstance();
	private View view;
	private FrontFotruneAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_frontpage, null);
		}
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);
			return view;
		}
		initViews(view);
		log.i(PhoneUtil.getDeviceId((TelephonyManager) (getActivity().getSystemService(Context.TELEPHONY_SERVICE))));
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		width = dm.widthPixels;// 宽度
		lists = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			lists.add(new String("标题" + i * 2 + "" + "主体内容" + i * 5));
		}
		initData(view);
		// createNavMenu1(view);
		return view;
	}

	private void initData(final View view) {
		if (CommonUtil.checkNetState(getActivity())) {
			RequestParams params = new RequestParams();
			params.put("phoneimei", PhoneUtil.getDeviceId((TelephonyManager) (getActivity()
					.getSystemService(Context.TELEPHONY_SERVICE))));
			showProgressDialog(R.string.please_waiting);
			XDHttpClient.get(JFConfig.HOME_PAGE, params, new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, String content) {
					log.i("content===" + content);
					dismissProgressDialog();
					if (TextUtils.isEmpty(content)) {
						return;
					}
					OuterData outerData = JSON.parseObject(content, OuterData.class);
					InnerData innderData = outerData.getData().get(0);
					CollectionData commonData = innderData.getData().get(0);
					log.i("commonData" + commonData.getCommonData().getMsg());
					if ("true".equals(commonData.getCommonData().getReturnStatus())) {
						List<WealthEntity> wealthEntities = commonData.getWealthareaMapList();
						createNavMenu1(wealthEntities, view);
						ArrayList<CategoryEntity> categoryEntities = commonData.getCategoryMapList();
						if (null == adapter) {
							adapter = new FrontFotruneAdapter(getChildFragmentManager(), categoryEntities);
							viewPager.setAdapter(adapter);
							viewPager.setOffscreenPageLimit(1);
						} else {
							adapter.notifyDataSetChanged();
						}
						viewPager.setCurrentItem(0);
						imageButton.setText("会员：" + commonData.getCommonData().getUserAmount());
						mFortune.setText("我的财富：" + commonData.getCommonData().getUserwealth());
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

	private void initViews(View view) {
		imageButton = (TextView) view.findViewById(R.id.ib_back);
		titleTextView = (TextView) view.findViewById(R.id.tv_title);
		titleTextView.setText("首页");
		viewPager = (ViewPager) view.findViewById(R.id.vPager);
		// ArrayList<String> temp = new ArrayList<String>();
		// viewPager.setAdapter(new
		// FrontFotruneAdapter(getActivity().getSupportFragmentManager(),
		// temp));
		// viewPager.setPageTransformer(true, new DepthPageTransformer());
		button = (ImageButton) view.findViewById(R.id.imageview);
		button.setOnClickListener(this);
		mFortune = (TextView) view.findViewById(R.id.tv_fortune);

	}

	/**
	 * 扫描二维码
	 */
	private void gotoScanRequest() {
		startActivityForResult(new Intent(getActivity(), CaptureActivity.class), REQUEST_SCAN);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_SCAN) {
			if (resultCode == Activity.RESULT_OK) {
				String resultString = data.getStringExtra("result");

				// String resultId =
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * create menu by {@link MenuListEntity}
	 * 
	 * @param menuEntity
	 */
	protected void createNavMenu1(List<WealthEntity> wealthEntities, View v) {
		int maxRows = wealthEntities.size() % 2 == 0 ? wealthEntities.size() / 2 : wealthEntities.size() / 2 + 1;
		GridLayout gridLayout = (GridLayout) v.findViewById(R.id.menu_gridlayout);
		gridLayout.setRowCount(maxRows > 4 ? 4 : maxRows);
		gridLayout.setColumnCount(4);
		GridLayout.LayoutParams lp = null;
		GridLayout.Spec spec = null;

		int gridLayoutWidth = width - gridLayout.getPaddingLeft() - gridLayout.getPaddingRight();
		int padding = 5;
		gridLayout.setPadding(padding, 0, padding, 0);
		int itemSpace = 5;
		int minimumItemHeight = 54;
		int itemWidth = (gridLayoutWidth - padding * 2 - gridLayout.getPaddingLeft() - gridLayout.getPaddingRight() - itemSpace * 2) / 2;
		for (WealthEntity entity : wealthEntities) {
			// TODO 版本升级功能稳定后删除 更新menuItem
			RelativeLayout ll = (RelativeLayout) getActivity().getLayoutInflater().inflate(R.layout.icon_menu_item,
					gridLayout, false);
			ll.setOnClickListener(this);
			ll.setTag(entity);
			ll.setBackgroundResource(R.drawable.selector_gridlayout_bg);
			// ImageView imageView = (ImageView) ll
			// .findViewById(R.id.icon_imageView);
			// loadImage(item, imageView);

			TextView label = (TextView) ll.findViewById(R.id.description_textView);
			label.setText(entity.getWealthareaTitle());

			lp = new GridLayout.LayoutParams(ll.getLayoutParams());
			lp.width = itemWidth;
			// lp.height = itemWidth / 3 < minimumItemHeight ? minimumItemHeight
			// : itemWidth / 3;
			lp.height = LayoutParams.WRAP_CONTENT;
			lp.leftMargin = lp.rightMargin = lp.topMargin = lp.bottomMargin = itemSpace;
			spec = GridLayout.spec(GridLayout.UNDEFINED, 2, GridLayout.BASELINE);
			lp.columnSpec = spec;
			spec = GridLayout.spec(GridLayout.UNDEFINED, 2, GridLayout.BASELINE);
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
			Intent intent = new Intent(getActivity(), WealthPrizeActivity.class);
			intent.putExtra("wealthId", entity.getWealthareaId());
			intent.putExtra("wealthTitle", entity.getWealthareaTitle());
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}
