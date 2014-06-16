package com.android.storemanage.net;
// Static wrapper library around AsyncHttpClient

import android.content.Context;

import com.android.storemanage.application.BaseApplication;
import com.android.storemanage.utils.CommonLog;
import com.android.storemanage.utils.JFConfig;

public class XDHttpClient {
    protected static CommonLog log = CommonLog.getInstance();
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static Context context = BaseApplication.getApplication();

    public static void get(String api, RequestParams params, AsyncHttpResponseHandler responseHandler) {
//    	if(XDUserInfo.getInstance(context).isLogin()){
//    		UserToken userToken = XDUserInfo.getInstance(context).getUserToken();
//        	params.put(xdConstant.key_token,userToken.getToken());
//    	}
//    	params.put(xdConstant.key_deviceid, PhoneUtil.getDeviceId((TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE)));
//    	params.put(xdConstant.key_platform, xdConstant.PLATFORM_ANDROID);
//    	params.put(xdConstant.key_version, "1");
  	
    	String absoluteUrl = getAbsoluteUrl(api);
    	if(absoluteUrl.indexOf("?") != -1){
        	log.i(getAbsoluteUrl(api) + "&" + params.toString());
    	}else{
    		log.i(getAbsoluteUrl(api) + "?" + params.toString());
    	}
    	
        client.get(getAbsoluteUrl(api), params, responseHandler);
    }

    public static void post(String api, RequestParams params, AsyncHttpResponseHandler responseHandler) {
//    	if(XDUserInfo.getInstance(context).isLogin()){
//    		UserToken userToken = XDUserInfo.getInstance(context).getUserToken();
//        	params.put(xdConstant.key_token,userToken.getToken());
//    	}
//    	params.put(xdConstant.key_platform, xdConstant.PLATFORM_ANDROID);
//    	params.put(xdConstant.key_version, "1");
    	log.i(params.toString());
        client.post(getAbsoluteUrl(api), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return JFConfig.HOST_URL + relativeUrl;
    }
    
}