package com.android.storemanage.activity;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.android.storemanage.R;
import com.android.storemanage.entity.CollectionData;
import com.android.storemanage.entity.InnerData;
import com.android.storemanage.entity.OuterData;
import com.android.storemanage.entity.UserPrizeEntity;
import com.android.storemanage.net.AsyncHttpResponseHandler;
import com.android.storemanage.net.RequestParams;
import com.android.storemanage.net.HttpClient;
import com.android.storemanage.swipelistview.SwipeListView;
import com.android.storemanage.utils.CommonUtil;
import com.android.storemanage.utils.JFConfig;
import com.android.storemanage.view.CRAlertDialog;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author 我的奖品
 * 
 */
public class MyPrizeActivity extends BaseActivity {

	private TextView tView;
	private SwipeListView swipeListView;
	private TextView tvNoTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_prize);
		tView = (TextView) findViewById(R.id.tv_title);
		swipeListView = (SwipeListView) findViewById(R.id.lv_my_prize);
		tView.setText("我的奖品");
		tvNoTextView = (TextView) findViewById(R.id.tv_no_data);
		swipeListView.setEmptyView(tvNoTextView);
		swipeListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				UserPrizeEntity entity = (UserPrizeEntity) arg0
						.getItemAtPosition(position);
				gotoUserPrizeDetailActivity(entity);
			}

		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		initData();
	}

	private void gotoUserPrizeDetailActivity(UserPrizeEntity entity) {
		if (null != entity) {
			if(entity.getUserprizeCategory() == 1){
				Intent intent = new Intent(MyPrizeActivity.this,
						UserPrizeDetailActivity2.class);
				intent.putExtra("userprizeId", entity.getUserprizeId());
				intent.putExtra("isused",
						"1".equals(entity.getUserprizeSfused()) ? true : false);
				startActivity(intent);
			}else{
				Intent intent = new Intent(MyPrizeActivity.this,
						UserPrizeDetailActivity.class);
				intent.putExtra("userprizeId", entity.getUserprizeId());
				intent.putExtra("isused",
						"1".equals(entity.getUserprizeSfused()) ? true : false);
				startActivity(intent);
			}
		}
	}

	private UserPrizeAdapter adapter;
	public void initData() {
		if (CommonUtil.checkNetState(mContext)) {
			RequestParams params = new RequestParams();
			params.put("userId", application.getUserId());
			showProgressDialog(R.string.please_waiting);
			HttpClient.post(JFConfig.GET_MY_PRIZE, params,
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
								if(null == adapter){
									adapter = new UserPrizeAdapter(
											mContext, commonData
											.getUserPrizeMapList(),
											MyPrizeActivity.this);
									swipeListView.setAdapter(adapter);
								}else{
									adapter.setLists(commonData
											.getUserPrizeMapList());
									adapter.notifyDataSetChanged();
								}
							} else {
//								CRAlertDialog dialog = new CRAlertDialog(
//										mContext);
//								dialog.show(
//										commonData.getCommonData().getMsg(),
//										2000);
							}
						}

						@Override
						public void onFailure(Throwable arg0, String arg1) {
							super.onFailure(arg0, arg1);
							dismissProgressDialog();
							CommonUtil.onFailure(arg0, mContext);
						}
					});
		} else {
			CRAlertDialog dialog = new CRAlertDialog(mContext);
			dialog.show(getString(R.string.pLease_check_network), 2000);
		}

	}

	class UserPrizeAdapter extends BaseAdapter {
		private Context mContext;
		private List<UserPrizeEntity> lists;
		private MyPrizeActivity activity;

		public UserPrizeAdapter() {
			super();
		}

		public UserPrizeAdapter(Context mContext, List<UserPrizeEntity> lists,
				MyPrizeActivity activity) {
			super();
			this.mContext = mContext;
			this.lists = lists;
			this.activity = activity;

		}
		
		/**
		 * @param lists the lists to set
		 */
		public void setLists(List<UserPrizeEntity> lists) {
			this.lists = lists;
		}

		@Override
		public int getCount() {
			return null != lists ? lists.size() : 0;
		}

		@Override
		public Object getItem(int arg0) {
			return lists.get(arg0);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (null == convertView) {
				convertView = View
						.inflate(mContext, R.layout.package_row, null);
				holder = new ViewHolder();
				holder.ivImageView = (ImageView) convertView
						.findViewById(R.id.iv_has_used_or_not);
				holder.ivIconImageView = (ImageView) convertView
						.findViewById(R.id.iv_icon);
				holder.tvValidateTimeTextView = (TextView) convertView
						.findViewById(R.id.tv_validate_time);
				holder.tvNameTextView = (TextView) convertView
						.findViewById(R.id.tv_name);
				holder.tvWealthTextView = (TextView) convertView
						.findViewById(R.id.tv_wealth_value);
				holder.tvToTime = (TextView) convertView
						.findViewById(R.id.tv_to_time);
				holder.deleteBtn = (Button) convertView
						.findViewById(R.id.example_row_b_action_3);
				holder.front = (LinearLayout) convertView.findViewById(R.id.ll);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final UserPrizeEntity entity = lists.get(position);
			if (null != entity) {
				holder.tvNameTextView.setText(entity.getUserprizeTitle());

				Picasso.with(mContext)
						.load(JFConfig.HOST_URL + entity.getUserprizeImgpath())
						.placeholder(R.drawable.img_empty)
						.into(holder.ivIconImageView);
				String isUsedOrNotString = entity.getUserprizeSfused();
				if (!TextUtils.isEmpty(isUsedOrNotString)) {
					if ("1".equals(isUsedOrNotString)) {
						holder.ivImageView
								.setImageResource(R.drawable.btn_hasused);
					} else {
						holder.ivImageView
								.setImageResource(R.drawable.btn_unused);
					}
					int result = Integer.parseInt(isUsedOrNotString);
					if (1 == result) {
//						holder.tvValidateTimeTextView.setText("该奖品已使用");
						holder.tvValidateTimeTextView.setText("该奖品已失效");
					} else if (result == 0) {
						holder.tvValidateTimeTextView.setText("有效期剩余"
								+ entity.getUserprizeExpirydate() + "天");
					}
				} else {
					holder.ivImageView.setImageResource(R.drawable.btn_unused);
				}
				holder.tvToTime.setText("兑换时间："+ entity.getUserprizeDuihuantime());
//				try {
//					holder.tvToTime.setText("兑换时间："
//							+ CommonUtil.longToString(
//									entity.getUserprizeDuihuantime(),
//									"yyyy-MM-dd"));
//				} catch (NumberFormatException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (ParseException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				holder.tvWealthTextView.setText(entity.getUserprizeNeedwealth()
						+ "");
				holder.deleteBtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						initData(entity.getUserprizeId());
					}
				});
				holder.front.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						log.i("onclick()-----------------------------------------------");
						activity.gotoUserPrizeDetailActivity(entity);
					}
				});
				holder.ivIconImageView
						.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								activity.gotoUserPrizeDetailActivity(entity);
							}
						});
			}
			return convertView;
		}

		class ViewHolder {
			ImageView ivImageView;
			ImageView ivIconImageView;
			TextView tvNameTextView;
			TextView tvValidateTimeTextView;
			TextView tvWealthTextView;
			Button deleteBtn;
			LinearLayout front;
			TextView tvToTime;

		}

		private void initData(String prizeId) {
			if (CommonUtil.checkNetState(mContext)) {
				RequestParams params = new RequestParams();
				params.put("userId", application.getUserId());
				params.put("userprizeId", prizeId);
				showProgressDialog(R.string.please_waiting);
				HttpClient.post(JFConfig.DELETE_USER_PRIZE, params,
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
								InnerData innderData = outerData.getData().get(
										0);
								CollectionData commonData = innderData
										.getData().get(0);
								log.i("commonData"
										+ commonData.getCommonData().getMsg());
								CRAlertDialog dialog = new CRAlertDialog(
										mContext);
								if ("true".equals(commonData.getCommonData()
										.getReturnStatus())) {
									String deleteSuccess = commonData
											.getCommonData().getDeleteSuccess();
									if (!TextUtils.isEmpty(deleteSuccess)
											&& "true".equals(deleteSuccess)) {
										dialog.show("删除成功", 2000);
										activity.initData();
									}
								} else {
									dialog.show(commonData.getCommonData()
											.getMsg(), 2000);
								}
							}

							@Override
							public void onFailure(Throwable arg0, String arg1) {
								super.onFailure(arg0, arg1);
								dismissProgressDialog();
								CommonUtil.onFailure(arg0, mContext);
							}
						});
			} else {
				CRAlertDialog dialog = new CRAlertDialog(mContext);
				dialog.show(mContext.getString(R.string.pLease_check_network),
						2000);
			}

		}

	}

}
