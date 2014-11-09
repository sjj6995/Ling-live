/**
 * 
 */
package com.android.storemanage.service;

import java.util.Timer;
import java.util.TimerTask;

import com.alibaba.fastjson.JSON;
import com.android.storemanage.R;
import com.android.storemanage.entity.CollectionData;
import com.android.storemanage.entity.InnerData;
import com.android.storemanage.entity.OuterData;
import com.android.storemanage.entity.UserInforEntity;
import com.android.storemanage.net.AsyncHttpResponseHandler;
import com.android.storemanage.net.HttpClient;
import com.android.storemanage.net.RequestParams;
import com.android.storemanage.service.TipMessageService.ServiceBinder;
import com.android.storemanage.utils.CommonLog;
import com.android.storemanage.utils.CommonUtil;
import com.android.storemanage.utils.JFConfig;
import com.android.storemanage.view.CRAlertDialog;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;

/**
 * @author Administrator
 *
 */
public class MyTipMessageService extends Service {

	private  Timer timer;
	private TimerTask task_xiaoxi,task_my;
	private CommonLog log = CommonLog.getInstance();
	private static String userId;
	private Handler handler;
	private ServiceBinder serviceBinder = new ServiceBinder();
	public interface SendMSG{
		public String backTip();
	}
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return serviceBinder;
	}
	@Override
	public void onCreate() {
 
		super.onCreate();
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		System.out.println("---------create MYservice");
		 timer = new Timer();   
		    task_my = new TimerTask() {  
		        @Override  
		        public void run() {  
		        	visitMy(userId);
		            // 需要做的事:发送消息  
		        }  
		    };  
		timer.schedule(task_my, 1000, 60*60*1000);
		return super.onStartCommand(intent, flags, startId);
	}
	private void visitMy(String userId){
		if (CommonUtil.checkNetState(this)) {
			RequestParams params = new RequestParams();
			params.put("userId", userId);
			HttpClient.post(JFConfig.MY_INFOR, params, new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, String content) {
					log.i("content===" + content);
					OuterData outerData = JSON.parseObject(content, OuterData.class);
					InnerData innderData = outerData.getData().get(0);
					CollectionData collectionData = innderData.getData().get(0);
					log.i("commonData" + collectionData.getCommonData().getMsg());
					if ("true".equals(collectionData.getCommonData().getReturnStatus())) {
						UserInforEntity entity = collectionData.getUserInfoMap();
						if(Integer.parseInt(entity.getMinDay())>0&&Integer.parseInt(entity.getMinDay())<4){
							Intent intent = new Intent("com.lsh.newtip_my");
							sendBroadcast(intent);
						}
					}
				}

				@Override
				public void onFailure(Throwable arg0, String arg1) {
					super.onFailure(arg0, arg1);
			
				}
			});
		}
	}
	public void getMyMessage(final String userId){
		 timer = new Timer();   
		    task_my = new TimerTask() {  
		        @Override  
		        public void run() {  
		        	visitMy(userId);
		            // 需要做的事:发送消息  
		        }  
		    };  
		timer.schedule(task_my, 1000, 60*60*1000);
	}
	public class ServiceBinder extends Binder {
        public MyTipMessageService getService() {
            return MyTipMessageService.this;
        }
    }

}
