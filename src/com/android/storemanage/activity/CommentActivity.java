package com.android.storemanage.activity;

import com.alibaba.fastjson.JSON;
import com.android.storemanage.R;
import com.android.storemanage.entity.CollectionData;
import com.android.storemanage.entity.InnerData;
import com.android.storemanage.entity.OuterData;
import com.android.storemanage.net.AsyncHttpResponseHandler;
import com.android.storemanage.net.RequestParams;
import com.android.storemanage.net.HttpClient;
import com.android.storemanage.utils.CommonUtil;
import com.android.storemanage.utils.JFConfig;
import com.android.storemanage.view.CRAlertDialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author liujiao
 * 评论
 *
 */
public class CommentActivity extends BaseActivity {
	private EditText edit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		edit = (EditText) findViewById(R.id.edittext);
	}

	/**
	 * 提交
	 * 
	 * @param view
	 */
	public void gotoComment(View view) {
		String content = edit.getText().toString().trim();
		if (TextUtils.isEmpty(content)) {
			new CRAlertDialog(mContext).show("反馈内容不能为空!", 2000);
			return;
		}
		initData(content);
	}

	private void initData(String content) {
		if (CommonUtil.checkNetState(mContext)) {
			RequestParams params = new RequestParams();
			params.put("appversionNumber", CommonUtil.getVersion(mContext));
			params.put("phonetype", "android");
			params.put("userfeedbackNr", content);
			showProgressDialog(R.string.please_waiting);
			HttpClient.post(JFConfig.USER_FEEDBACK, params, new AsyncHttpResponseHandler() {
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
						String isSuccess = commonData.getCommonData().getFeedbackSuccess();
						if (!TextUtils.isEmpty(isSuccess) && "true".equals(isSuccess)) {
							Toast.makeText(mContext, R.string.submit_success, Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(mContext, R.string.submit_error, Toast.LENGTH_SHORT).show();
					}
					CommentActivity.this.finish();
				}

				@Override
				public void onFailure(Throwable arg0, String arg1) {
					super.onFailure(arg0, arg1);
					dismissProgressDialog();
					CommonUtil.onFailure(arg0, mContext);
//					CommentActivity.this.finish();
				}
			});
		} else {
			CRAlertDialog dialog = new CRAlertDialog(mContext);
			dialog.show(getString(R.string.pLease_check_network), 2000);
		}

	}
}
