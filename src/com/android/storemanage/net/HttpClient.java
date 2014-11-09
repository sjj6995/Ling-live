package com.android.storemanage.net;

// Static wrapper library around AsyncHttpClient

import android.text.TextUtils;

import com.android.storemanage.utils.CommonLog;
import com.android.storemanage.utils.JFConfig;

public class HttpClient {
	protected static CommonLog log = CommonLog.getInstance();
	private static AsyncHttpClient client = new AsyncHttpClient();
	static {
		client.setTimeout(20000);
	}

	public static void get(String api, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		String absoluteUrl = getAbsoluteUrl(api);
		if (absoluteUrl.indexOf("?") != -1) {
			if (TextUtils.isEmpty(params.toString())) {
				log.i(getAbsoluteUrl(api));
			} else {
				log.i(getAbsoluteUrl(api) + "&" + params.toString());
			}
		} else {
			log.i(getAbsoluteUrl(api) + "?" + params.toString());
		}
		client.get(getAbsoluteUrl(api), params, responseHandler);
	}

	public static void post(String api, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		log.i(params.toString());
		log.i("full url----"+getAbsoluteUrl(api)+"&"+params.toString());
		client.post(getAbsoluteUrl(api), params, responseHandler);
	}

	private static String getAbsoluteUrl(String relativeUrl) {
		return JFConfig.HOST_URL+ relativeUrl;
	}

}