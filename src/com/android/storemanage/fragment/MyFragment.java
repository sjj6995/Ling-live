package com.android.storemanage.fragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.android.storemanage.R;
import com.android.storemanage.activity.AboutUsActivity;
import com.android.storemanage.activity.CommentActivity;
import com.android.storemanage.activity.ModifyPasswordActivity;
import com.android.storemanage.activity.MyPrizeActivity;
import com.android.storemanage.activity.SignInActivity1;
import com.android.storemanage.activity.SplashActivity;
import com.android.storemanage.activity.WealthDetailActivity;
import com.android.storemanage.adapter.ZhaoPinListAdapter;
import com.android.storemanage.dialog.RetryDialog;
import com.android.storemanage.dialog.RetryDialog.OnConfirmClick;
import com.android.storemanage.entity.CollectionData;
import com.android.storemanage.entity.DataSaveEntity;
import com.android.storemanage.entity.InnerData;
import com.android.storemanage.entity.OuterData;
import com.android.storemanage.entity.UserInforEntity;
import com.android.storemanage.entity.ZhaoPin;
import com.android.storemanage.net.AsyncHttpResponseHandler;
import com.android.storemanage.net.RequestParams;
import com.android.storemanage.net.HttpClient;
import com.android.storemanage.service.UpdateService;
import com.android.storemanage.utils.CommonLog;
import com.android.storemanage.utils.CommonUtil;
import com.android.storemanage.utils.JFConfig;
import com.android.storemanage.view.CRAlertDialog;
import com.android.storemanage.wxapi.WXEntryActivity;
import com.squareup.picasso.Picasso;
import com.tencent.mm.sdk.openapi.IWXAPI;
//import com.tencent.mm.sdk.openapi.SendMessageToWX;
//import com.tencent.mm.sdk.openapi.SendMessageToWX.Req;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
//import com.tencent.mm.sdk.openapi.WXMediaMessage;
//import com.tencent.mm.sdk.openapi.WXTextObject;
//import com.tencent.mm.sdk.openapi.WXWebpageObject;
import com.umeng.socialize.bean.MultiStatus;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeConfig;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.MulStatusListener;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.net.v;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.RenrenSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateFormat;
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
			mOpinionTextView, mCacheSize, fix_password1, umShare, my_sign;
	private IWXAPI api;
	private RelativeLayout rl;
	private long cacheSize;
	private SnsPostListener snsPostListener;
	private static final int THUMB_SIZE = 150;
	private UMSocialService mController;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 111:
				CRAlertDialog dialog = new CRAlertDialog(getActivity());
				dialog.show("缓存清理完成!", 1000);
				dismissProgressDialog();
				// cacheSize =
				// Picasso.with(getActivity()).getSnapshot().totalDownloadSize;
				mCacheSize.setText("0KB");
				break;
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_my, null);
		sp = getActivity().getSharedPreferences("share",
				getActivity().MODE_PRIVATE);
		initViews(view);
		initData();
		umInit();
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
							CommonUtil.onFailure(arg0, getActivity());
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
		if (Integer.parseInt(entity.getMinDay()) > 0
				&& Integer.parseInt(entity.getMinDay()) < 4) {
			mMyFortuneTextView.setText(Html
					.fromHtml("我的奖品  <font color=\"#ff0000\">(您有奖品"
							+ entity.getMinDay() + "天后过期哦</font>)"));
		}
		mPhoneTextView.setText("手机号码：" + entity.getPhonenumber());
		mFotuneTextView.setText("财富：" + entity.getUserwealth());
		mEmailtTextView.setText("邮箱：" + entity.getUseremail());
		String version = CommonUtil.getVersion(getActivity());
		if (!TextUtils.isEmpty(version)) {
			mCurrentVersionTextView.setText("版本号：V" + version);
		}
	}

	private void initViews(View view) {
		imageButton = (ImageButton) view.findViewById(R.id.ib_back);
		imageButton.setVisibility(View.INVISIBLE);
		titleTextView = (TextView) view.findViewById(R.id.tv_title);
		titleTextView.setText("我的");

		my_sign = (TextView) view.findViewById(R.id.my_sign);
		my_sign.setOnClickListener(this);
		umShare = (TextView) view.findViewById(R.id.tv_share_all);
		mFotuneTextView = (TextView) view.findViewById(R.id.tv_fortune);
		mPhoneTextView = (TextView) view.findViewById(R.id.tv_phone);
		mEmailtTextView = (TextView) view.findViewById(R.id.tv_email);
		fix_password1 = (TextView) view.findViewById(R.id.fix_password);
		fix_password1.setOnClickListener(this);
		mMyFortuneTextView = (TextView) view.findViewById(R.id.tv_my_prize);
		mMyFortuneTextView.setOnClickListener(this);
		mAboutUsTextView = (TextView) view.findViewById(R.id.tv_about_us);
		mAboutUsTextView.setOnClickListener(this);
		mOpinionTextView = (TextView) view.findViewById(R.id.tv_opinion);
		mOpinionTextView.setOnClickListener(this);
		mCurrentVersionTextView = (TextView) view
				.findViewById(R.id.tv_current_version);
		mCurrentVersionTextView.setOnClickListener(this);
		// mShareTextView = (TextView)
		// view.findViewById(R.id.tv_share_to_friends);
		// mShareTextView.setOnClickListener(this);
		umShare.setOnClickListener(this);
		mCacheSize = (TextView) view.findViewById(R.id.tv_cachesize);
		cacheSize = Picasso.with(getActivity()).getSnapshot().totalDownloadSize;
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
		case R.id.my_sign:// 分享至朋友圈
			gotoMySign(v);
			break;
		case R.id.tv_share_all:
			umShareToAll(v);
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
		case R.id.fix_password:
			gotoFixPassword(v);
			break;
		case R.id.rl:
			new Thread() {
				public void run() {
					showProgressDialog(R.string.clearing);
					File downloadCacheFolder = application.getCacheDir();
					File filePath = new File(downloadCacheFolder,
							"picasso-cache");
					File[] filelists = filePath.listFiles();
					log.i("filelists.length------" + filelists.length);
					for (File fileList : filelists) {
						if (!fileList.isDirectory()) {
							fileList.delete();
						}
					}
					// db.deleteDataSaveEntity(JFConfig.BRAND_LIST);
					// db.deleteDataSaveEntity(JFConfig.MESSAGE_LIST);
					// db.deleteDataSaveEntity(JFConfig.FRONT_PAGE);
					// db.deleteDataSaveEntity(JFConfig.PRIZE_LIST);
					handler.sendEmptyMessage(111);
				};
			}.start();

			break;

		default:
			break;
		}
	}

	// 签到
	public void gotoMySign(View view) {
		Intent intent = new Intent(view.getContext(), SignInActivity1.class);
		startActivity(intent);
	}

	// um分享
	public void umShareToAll(View view) {
		mController.openShare(getActivity(), snsPostListener);

	}

	public void gotoFixPassword(View view) {
		Intent intent = new Intent(view.getContext(),
				ModifyPasswordActivity.class);
		startActivity(intent);
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
			HttpClient.post(JFConfig.CHECK_UPDATE, params,
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
								Toast.makeText(getActivity(),
										R.string.server_data_exception,
										Toast.LENGTH_SHORT).show();
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
			dialog.show(getActivity().getString(R.string.pLease_check_network),
					2000);
		}

	}

	private void gotoWebPage(String resultString) {
		try {
			Intent intent = new Intent();
			intent.setAction("android.intent.action.VIEW");
			Uri content_url = Uri.parse(resultString);
			intent.setData(content_url);
			getActivity().startActivity(intent);

		} catch (Exception e) {
			Intent intent = new Intent(getActivity(),
					WealthDetailActivity.class);
			intent.putExtra("url", resultString);
			getActivity().startActivity(intent);
		}
	}

	private void chooseDifferentStatus(final CollectionData commonData) {
		int appversionNeedUpdate = commonData.getAppVersionData()
				.getSfNeedUpdate();
		final RetryDialog dialog = new RetryDialog(getActivity());
		switch (appversionNeedUpdate) {
		case 0:// 必须更新
			dialog.setConfirmText("更新");
			dialog.setContent(commonData.getAppVersionData()
					.getAppversionUpdateinfo());
			dialog.setOnConfirmClick(new OnConfirmClick() {

				@Override
				public void onClick(View view) {
					switch (view.getId()) {
					case R.id.sureBtn:
						// gotoUpdateService(commonData.getAppVersionData().getAppversionUpdateurl());
						gotoWebPage(commonData.getAppVersionData()
								.getAppversionUpdateurl());
						break;
					case R.id.cancelBtn:
						dialog.dismiss();
						break;
					}
				}
			});
			dialog.show();
			break;
		case 1:// 可以更新
			dialog.setConfirmText("更新");
			dialog.setContent(commonData.getAppVersionData()
					.getAppversionUpdateinfo());
			dialog.setOnConfirmClick(new OnConfirmClick() {

				@Override
				public void onClick(View view) {
					switch (view.getId()) {
					case R.id.sureBtn:
						// gotoUpdateService(commonData.getAppVersionData().getAppversionUpdateurl());
						gotoWebPage(commonData.getAppVersionData()
								.getAppversionUpdateurl());
						break;
					case R.id.cancelBtn:
						dialog.dismiss();
						break;
					}
				}
			});
			dialog.show();
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
	// public void shareToFriends(View view) {
	// sendReq(getActivity(), getResources().getString(R.string.app_name),
	// BitmapFactory.decodeResource(getResources(), R.drawable.share_icon),
	// Req.WXSceneTimeline);
	// }

	// public void sendReq(Context context, String text, Bitmap bmp, int type) {
	// api = WXAPIFactory.createWXAPI(context, JFConfig.WECHAT_SHARA_APP_IP,
	// true);
	// api.registerApp(JFConfig.WECHAT_SHARA_APP_IP);
	//
	// if (!api.isWXAppInstalled()) {
	// // showToast("您没有安装微信，请下载后分享...");
	// Toast.makeText(context, "您没有安装微信，请下载后分享...", Toast.LENGTH_LONG).show();
	// return;
	// }
	// WXTextObject obj = new WXTextObject();
	// obj.text = "零生活，凌驾你的生活！";
	//
	// String url =
	// getResources().getString(R.string.share_app_download_address);//
	// 收到分享的好友点击信息会跳转到这个地址去
	// WXWebpageObject localWXWebpageObject = new WXWebpageObject();
	// localWXWebpageObject.webpageUrl = url;
	// WXMediaMessage localWXMediaMessage = new
	// WXMediaMessage(localWXWebpageObject);
	// localWXMediaMessage.title =
	// "零生活是北京地区领先的生活服务类型的媒体推介APP平台，零生活不仅为客户端用户提供商户的各类信息推介";//
	// 不能太长，否则微信会提示出错。不过博主没验证过具体能输入多长。
	// localWXMediaMessage.description = "零生活，凌驾你的生活！";
	// localWXMediaMessage.thumbData = getBitmapBytes(bmp, false);
	// SendMessageToWX.Req localReq = new SendMessageToWX.Req();
	// localReq.transaction = String.valueOf(System.currentTimeMillis());
	// localReq.message = localWXMediaMessage;
	// localReq.scene = type;
	//
	// api.sendReq(localReq);
	// }

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

	// 友盟生成分享
	private void umInit() {
		// 首先在您的Activity中添加如下成员变量
		mController = UMServiceFactory.getUMSocialService("com.umeng.share");
		// 设置分享内容
		// mController.setAppWebSite("http://112.126.72.53:8181/app/pages/main/index.html");
		mController
				.setShareContent("零生活是北京地区领先的生活服务类型的媒体推介APP平台，零生活不仅为客户端用户提供商户的各类信息推介，同时提供餐饮购物休闲娱乐等诸多领域的免费礼品赠送。注册请填写我的邀请码："
						+ application.getUserEmail());
		// 设置分享图片, 参数2为图片的url地址
		mController.setShareMedia(new UMImage(getActivity(), R.drawable.icon));
		mController.getConfig().removePlatform(SHARE_MEDIA.QQ,
				SHARE_MEDIA.QZONE, SHARE_MEDIA.TENCENT);
		// 添加人人分享
		mController.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN,
				SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA);
		addWeiXin();
		addSina();
		addRenRen();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/** 使用SSO授权必须添加如下代码 */
		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
				requestCode);
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	public void addWeiXin() {
		// wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
		String appID = "wxc11a20943368d63f";
		String appSecret = "faf2ecc98e04b8644620c8fd774e58af";
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(getActivity(), appID, appSecret);
		wxHandler.addToSocialSDK();
		// 设置微信好友分享内容
		WeiXinShareContent weixinContent = new WeiXinShareContent();
		// 设置分享文字
		weixinContent
				.setShareContent("零生活是北京地区领先的生活服务类型的媒体推介APP平台，零生活不仅为客户端用户提供商户的各类信息推介，同时提供餐饮购物休闲娱乐等诸多领域的免费礼品赠送。注册请填写我的邀请码："
						+ application.getUserEmail());
		// 设置title
		weixinContent.setTitle("零生活");
		// 设置分享内容跳转URL
		weixinContent
				.setTargetUrl("http://112.126.72.53:8181/app/pages/main/index.html");
		// 设置分享图片
		weixinContent
				.setShareImage(new UMImage(getActivity(), R.drawable.icon));
		mController.setShareMedia(weixinContent);

		// 设置微信朋友圈分享内容
		CircleShareContent circleMedia = new CircleShareContent();
		 circleMedia.setShareContent("零生活是北京地区领先的生活服务类型的媒体推介APP平台，零生活不仅为客户端用户提供商户的各类信息推介，同时提供餐饮购物休闲娱乐等诸多领域的免费礼品赠送。注册请填写我的邀请码："+application.getUserEmail());
		// 设置朋友圈title
		circleMedia.setTitle("零生活是北京地区领先的生活服务类型的媒体推介APP平台，零生活不仅为客户端用户提供商户的各类信息推介，同时提供餐饮购物休闲娱乐等诸多领域的免费礼品赠送。注册请填写我的邀请码："+application.getUserEmail());
		circleMedia.setShareImage(new UMImage(getActivity(), R.drawable.icon));
		circleMedia
				.setTargetUrl("http://112.126.72.53:8181/app/pages/main/index.html");
		mController.setShareMedia(circleMedia);
		// 支持微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(getActivity(), appID,
				appSecret);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();
		snsPostListener = new SnsPostListener() {

			@Override
			public void onStart() {

			}

			@Override
			public void onComplete(SHARE_MEDIA platform, int stCode,
					SocializeEntity entity) {
				System.out.println("------------------" + stCode);

				if (stCode == 200) {
					Toast.makeText(getActivity(), "分享成功", Toast.LENGTH_SHORT)
							.show();

					if (platform.equals(SHARE_MEDIA.WEIXIN)) {
						if (equalAdd("weixin")) {
							addWealthValue();
						}
					} else if (platform.equals(SHARE_MEDIA.WEIXIN_CIRCLE)) {
						if (equalAdd("weixinCircle")) {
							addWealthValue();
						}
					} else {
						if (equalAdd("sina")) {
							addWealthValue();
						}
					}

				} else {
					Toast.makeText(getActivity(), "分享失败 ", Toast.LENGTH_SHORT)
							.show();
				}

			}
		};
	}

	public boolean equalAdd(String plat) {
		String weixin = sp.getString(plat, "");
		System.out.println("weixin-----------------time");
		Date now = new Date();
		if ("".equals(weixin)) {
			Editor d = sp.edit();
			d.putString(plat, CommonUtil.dateToString(new Date(), "yyyy-MM-dd"));
			d.commit();
			return true;
		} else if (weixin.trim().equals(
				DateFormat.format("yyyy-MM-dd", now).toString())) {
			return false;
		} else {
			return true;
		}

	}

	public void addSina() {
		// 设置新浪SSO handler
		mController.getConfig().setSsoHandler(new SinaSsoHandler());
	}

	public void addRenRen() {
		// 添加人人网SSO授权功能
		// APPID:272964
		// API Key:28401c0964f04a72a14c812d6132fcef
		// Secret:3bf66e42db1e4fa9829b955cc300b737
		RenrenSsoHandler renrenSsoHandler = new RenrenSsoHandler(getActivity(),
				"272964", "ddd10e414332466fa88183697e39136f",
				"b2498161f48b4169a74c5064caedbed8");
		mController.getConfig().setSsoHandler(renrenSsoHandler);
	}

	public void addWealthValue() {
		if (CommonUtil.checkNetState(getActivity())) {
			RequestParams params = new RequestParams();
			params.put("useremail", application.getUserEmail());
			params.put("userId", application.getUserId());
			params.put(
					"lshToken",
					CommonUtil.getMD5(application.getUserEmail()
							+ JFConfig.COMMON_MD5_STR));
			showProgressDialog(R.string.please_waiting);
			HttpClient.post(JFConfig.ADDONE, params,
					new AsyncHttpResponseHandler() {

						@Override
						public void onSuccess(int statusCode, String content) {
							log.i("content===" + content);
							System.out.println("content====" + content);
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
							CRAlertDialog dialog = new CRAlertDialog(
									getActivity());
							if ("true".equals(commonData.getCommonData()
									.getReturnStatus())) {

								dialog.show("加一财富值", 2000);
							} else {
								dialog.show(
										commonData.getCommonData().getMsg(),
										2000);
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

}