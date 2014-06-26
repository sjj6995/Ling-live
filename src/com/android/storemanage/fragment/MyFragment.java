package com.android.storemanage.fragment;

import java.io.ByteArrayOutputStream;
import java.io.File;

import com.alibaba.fastjson.JSON;
import com.android.storemanage.R;
import com.android.storemanage.activity.AboutUsActivity;
import com.android.storemanage.activity.CommentActivity;
import com.android.storemanage.activity.MyPrizeActivity;
import com.android.storemanage.dialog.RetryDialog;
import com.android.storemanage.dialog.RetryDialog.OnConfirmClick;
import com.android.storemanage.entity.CollectionData;
import com.android.storemanage.entity.InnerData;
import com.android.storemanage.entity.OuterData;
import com.android.storemanage.entity.UserInforEntity;
import com.android.storemanage.net.AsyncHttpResponseHandler;
import com.android.storemanage.net.RequestParams;
import com.android.storemanage.net.HttpClient;
import com.android.storemanage.service.UpdateService;
import com.android.storemanage.utils.CommonLog;
import com.android.storemanage.utils.CommonUtil;
import com.android.storemanage.utils.JFConfig;
import com.android.storemanage.view.CRAlertDialog;
import com.squareup.picasso.Picasso;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXWebpageObject;
import com.tencent.mm.sdk.openapi.SendMessageToWX.Req;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author liujiao 我的
 * 
 */
public class MyFragment extends BaseFragment implements OnClickListener {
	private ImageButton imageButton;
	private TextView titleTextView;
	private CommonLog log = CommonLog.getInstance();
	private TextView mPhoneTextView;
	private TextView mFotuneTextView, mEmailtTextView, mMyFortuneTextView;
	private TextView mShareTextView, mCurrentVersionTextView, mAboutUsTextView,
			mOpinionTextView, mCacheSize;
	private IWXAPI api;
	private RelativeLayout rl;
	private long cacheSize;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_my, null);
		initViews(view);
		initData();
		return view;
	}

	private void initData() {
		if (CommonUtil.checkNetState(getActivity())) {
			RequestParams params = new RequestParams();
			params.put("userId", application.getUserId());
			HttpClient.post(JFConfig.MY_INFOR, params,
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
							CollectionData collectionData = innderData
									.getData().get(0);
							log.i("commonData"
									+ collectionData.getCommonData().getMsg());
							if ("true".equals(collectionData.getCommonData()
									.getReturnStatus())) {
								UserInforEntity entity = collectionData
										.getUserInfoMap();
								fillData(entity);
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

	protected void fillData(UserInforEntity entity) {
		if (null == entity) {
			return;
		}
		mPhoneTextView.setText("手机号码：" + entity.getPhonenumber());
		mFotuneTextView.setText("财富：" + entity.getUserwealth());
		mEmailtTextView.setText("邮箱：" + entity.getUseremail());
		String version = CommonUtil.getVersion(getActivity());
		if (!TextUtils.isEmpty(version)) {
			mCurrentVersionTextView.setText("版本号：" + version);
		}
	}

	private void initViews(View view) {
		imageButton = (ImageButton) view.findViewById(R.id.ib_back);
		imageButton.setVisibility(View.INVISIBLE);
		titleTextView = (TextView) view.findViewById(R.id.tv_title);
		titleTextView.setText("我的");

		mFotuneTextView = (TextView) view.findViewById(R.id.tv_fortune);
		mPhoneTextView = (TextView) view.findViewById(R.id.tv_phone);
		mEmailtTextView = (TextView) view.findViewById(R.id.tv_email);

		mMyFortuneTextView = (TextView) view.findViewById(R.id.tv_my_prize);
		mMyFortuneTextView.setOnClickListener(this);
		mAboutUsTextView = (TextView) view.findViewById(R.id.tv_about_us);
		mAboutUsTextView.setOnClickListener(this);
		mOpinionTextView = (TextView) view.findViewById(R.id.tv_opinion);
		mOpinionTextView.setOnClickListener(this);
		mCurrentVersionTextView = (TextView) view
				.findViewById(R.id.tv_current_version);
		mCurrentVersionTextView.setOnClickListener(this);
		mShareTextView = (TextView) view.findViewById(R.id.tv_share_to_friends);
		mShareTextView.setOnClickListener(this);
		mCacheSize = (TextView) view.findViewById(R.id.tv_cachesize);
		cacheSize = Picasso.with(getActivity()).getSnapshot().totalDownloadSize;
		// mCacheSize.setText(CommonUtil.formatFileSize(cacheSize));
		// cacheSize = imageLoader.getDiskCache().getDirectory().length();
		mCacheSize.setText(CommonUtil.formatFileSize(cacheSize));
		rl = (RelativeLayout) view.findViewById(R.id.rl);
		rl.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_about_us:// 关于我们
			gotoAboutUs(v);
			break;
		case R.id.tv_share_to_friends:// 分享至朋友圈
			shareToFriends(v);
			break;
		case R.id.tv_current_version:// 版本号
			getNewVersion(v);
			break;
		case R.id.tv_opinion:// 意见反馈
			opinionComment(v);
			break;
		case R.id.tv_my_prize:// 我的奖品
			gotoMyPrize(v);
			break;
		case R.id.rl:
			Picasso.with(getActivity()).getSnapshot().dump();
			File downloadCacheFolder = application.getCacheDir();
			File filePath = new File(downloadCacheFolder, "picasso-cache");
			String[] filelist = filePath.list();
			log.i("filePath------" + filelist.length);
			for (int i = 0; i < filelist.length; i++) {
				File delfile = new File(filePath + "\\" + filelist[i]);
				if (!delfile.isDirectory()) {
					delfile.delete();
				}
			}
			cacheSize = Picasso.with(getActivity()).getSnapshot().totalDownloadSize;
			mCacheSize.setText(CommonUtil.formatFileSize(cacheSize));
			break;

		default:
			break;
		}
	}

	/**
	 * @param view
	 *            我的奖品
	 */
	public void gotoMyPrize(View view) {
		Intent intent = new Intent(view.getContext(), MyPrizeActivity.class);
		startActivity(intent);
	}

	/**
	 * @param view
	 *            关于我们
	 */
	public void gotoAboutUs(View view) {
		Intent itt = new Intent(getActivity(), AboutUsActivity.class);
		startActivity(itt);
	}

	/**
	 * @param view
	 *            意见反馈
	 */
	public void opinionComment(View view) {
		Intent itt = new Intent(getActivity(), CommentActivity.class);
		startActivity(itt);
	}

	/**
	 * @param view
	 *            版本号
	 */
	public void getNewVersion(View view) {
		/**
		 * 检查版本更新
		 */
		if (CommonUtil.checkNetState(getActivity())) {
			RequestParams params = new RequestParams();
			params.put("phonetype", "android");
			params.put("appversionNumber", CommonUtil.getVersion(getActivity()));
			showProgressDialog(R.string.please_waiting);
			HttpClient.post(JFConfig.CHECK_ISORNOT_REGISTERED, params,
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
							if ("true".equals(commonData.getCommonData()
									.getReturnStatus())) {
								chooseDifferentStatus(commonData);
							} else {
								//
								Toast.makeText(getActivity(), "服务器内部错误", 0)
										.show();
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
			dialog.show(getActivity().getString(R.string.pLease_check_network),
					2000);
		}

	}

	private void chooseDifferentStatus(final CollectionData commonData) {
		int appversionNeedUpdate = commonData.getAppVersionData()
				.getSfNeedUpdate();
		final RetryDialog dialog = new RetryDialog(getActivity());
		switch (appversionNeedUpdate) {
		case 0:// 必须更新
			dialog.setConfirmText("必须更新");
			dialog.setContent(commonData.getAppVersionData().getUpdateExplain());
			dialog.setOnConfirmClick(new OnConfirmClick() {

				@Override
				public void onClick(View view) {
					switch (view.getId()) {
					case R.id.sureBtn:
						gotoUpdateService(commonData.getAppVersionData()
								.getAppversionUpdateurl());
						break;
					case R.id.cancelBtn:
						dialog.dismiss();
						break;
					}
				}
			});
			break;
		case 1:// 可以更新
			dialog.setConfirmText("可以更新");
			dialog.setContent(commonData.getAppVersionData().getUpdateExplain());
			dialog.setOnConfirmClick(new OnConfirmClick() {

				@Override
				public void onClick(View view) {
					switch (view.getId()) {
					case R.id.sureBtn:
						gotoUpdateService(commonData.getAppVersionData()
								.getAppversionUpdateurl());
						break;
					case R.id.cancelBtn:
						dialog.dismiss();
						break;
					}
				}
			});
			break;
		case 2:// 无需更新
			CRAlertDialog alertDialog = new CRAlertDialog(getActivity());
			alertDialog.show("当前已经是最新版本", 2000);
			break;
		default:
			break;
		}
	}

	private void gotoUpdateService(String url) {
		Intent itt = new Intent(getActivity(), UpdateService.class);
		itt.putExtra("url", url);
		getActivity().startService(itt);
	}

	/**
	 * @param view
	 *            分享到朋友圈
	 */
	public void shareToFriends(View view) {
		sendReq(getActivity(), "测试", BitmapFactory.decodeResource(getActivity()
				.getResources(), R.drawable.ic_launcher), Req.WXSceneTimeline);
	}

	public void sendReq(Context context, String text, Bitmap bmp, int type) {
		api = WXAPIFactory.createWXAPI(context, JFConfig.WECHAT_SHARA_APP_IP,
				true);
		api.registerApp(JFConfig.WECHAT_SHARA_APP_IP);

		if (!api.isWXAppInstalled()) {
			// showToast("您没有安装微信，请下载后分享...");
			Toast.makeText(context, "您没有安装微信，请下载后分享...", 1).show();
			return;
		}

		String url = "http://www.kdt360.com/download/download.html";// 收到分享的好友点击信息会跳转到这个地址去
		WXWebpageObject localWXWebpageObject = new WXWebpageObject();
		localWXWebpageObject.webpageUrl = url;
		WXMediaMessage localWXMediaMessage = new WXMediaMessage(
				localWXWebpageObject);
		localWXMediaMessage.title = "快递通";// 不能太长，否则微信会提示出错。不过博主没验证过具体能输入多长。
		localWXMediaMessage.description = text;
		localWXMediaMessage.thumbData = getBitmapBytes(bmp, false);
		SendMessageToWX.Req localReq = new SendMessageToWX.Req();
		localReq.transaction = String.valueOf(System.currentTimeMillis());
		localReq.message = localWXMediaMessage;
		localReq.scene = type;

		// WXTextObject textObject = new WXTextObject();
		// textObject.text = "分享快递通给好友";
		//
		// WXMediaMessage mediaMessage = new WXMediaMessage();
		// mediaMessage.title = "分享到微信";
		// mediaMessage.mediaObject = textObject;
		// mediaMessage.description = text;
		//
		// SendMessageToWX.Req localReq = new SendMessageToWX.Req();
		// localReq.transaction = String.valueOf(System.currentTimeMillis());
		// localReq.message = mediaMessage;
		// localReq.scene = type;

		api.sendReq(localReq);
	}

	// 需要对图片进行处理，否则微信会在log中输出thumbData检查错误
	private static byte[] getBitmapBytes(Bitmap bitmap, boolean paramBoolean) {
		Bitmap localBitmap = Bitmap.createBitmap(80, 80, Bitmap.Config.RGB_565);
		Canvas localCanvas = new Canvas(localBitmap);
		int i;
		int j;
		if (bitmap.getHeight() > bitmap.getWidth()) {
			i = bitmap.getWidth();
			j = bitmap.getWidth();
		} else {
			i = bitmap.getHeight();
			j = bitmap.getHeight();
		}
		while (true) {
			localCanvas.drawBitmap(bitmap, new Rect(0, 0, i, j), new Rect(0, 0,
					80, 80), null);
			if (paramBoolean)
				bitmap.recycle();
			ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
			localBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
					localByteArrayOutputStream);
			localBitmap.recycle();
			byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
			try {
				localByteArrayOutputStream.close();
				return arrayOfByte;
			} catch (Exception e) {

			}
			i = bitmap.getHeight();
			j = bitmap.getHeight();
		}
	}

}