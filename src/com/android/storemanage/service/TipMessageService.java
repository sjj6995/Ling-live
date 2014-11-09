/**
 * 
 */
package com.android.storemanage.service;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.alibaba.fastjson.JSON;
import com.android.storemanage.R;
import com.android.storemanage.adapter.MessageAdapter;
import com.android.storemanage.entity.CollectionData;
import com.android.storemanage.entity.DataSaveEntity;
import com.android.storemanage.entity.InnerData;
import com.android.storemanage.entity.MessageEntity;
import com.android.storemanage.entity.OuterData;
import com.android.storemanage.entity.UserInforEntity;
import com.android.storemanage.net.AsyncHttpResponseHandler;
import com.android.storemanage.net.HttpClient;
import com.android.storemanage.net.RequestParams;
import com.android.storemanage.utils.CommonLog;
import com.android.storemanage.utils.CommonUtil;
import com.android.storemanage.utils.JFConfig;
import com.android.storemanage.view.CRAlertDialog;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;

/**
 * @author Administrator
 *
 */
public class TipMessageService extends Service {

	private  Timer timer;
	private TimerTask task ;
	private CommonLog log = CommonLog.getInstance();
	private SharedPreferences sp;
	private ServiceBinder serviceBinder = new ServiceBinder();
	public interface SendMSG{
		public String backTip();
	}
	@Override
	public IBinder onBind(Intent arg0) {
		return serviceBinder;
	}
	@Override
	public void onCreate() {
		sp = getSharedPreferences("messagetime", MODE_PRIVATE);
		super.onCreate();
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		System.out.println("---------create service");
//		userId = intent.getStringExtra("userId");
		 timer = new Timer(); 
		    task = new TimerTask() {  
		        @Override  
		        public void run() {  
		        	visitMy();
		        }  
		    };  
		timer.schedule(task, 1000, 30*60*1000);
		return super.onStartCommand(intent, flags, startId);
	}
	private void visitMy(){
		RequestParams params = new RequestParams();
		if (CommonUtil.checkNetState(this)) {
			HttpClient.post(JFConfig.MESSAGE_CENTER, params, new AsyncHttpResponseHandler() {

				@Override
				public void onSuccess(int statusCode, String content) {
					log.i("content===" + content);
					if (TextUtils.isEmpty(content)) {
						return;
					}
					OuterData outerData = JSON.parseObject(content, OuterData.class);
					InnerData innderData = outerData.getData().get(0);
					CollectionData commonData = innderData.getData().get(0);
					log.i("commonData" + commonData.getCommonData().getMsg());
					if ("true".equals(commonData.getCommonData().getReturnStatus())) {
						List<MessageEntity> msgEntity = innderData.getData().get(0).getMessageMapList();
						if(msgEntity == null&&msgEntity.size() ==0){return;}
						String lastTime = msgEntity.get(0).getPuntimeStamp();
						System.out.println(sp.getString("date", ""));
						if("".equals(sp.getString("date", ""))){
							Editor d =sp.edit();
							d.putString("date", CommonUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
							d.commit();
							Intent i = new Intent("com.lsh.newtip");
			            	sendBroadcast(i);
							return;
						}else{
							String dbTime = sp.getString("date", "");
							if(!lastTime.equals(dbTime)){
								Intent i = new Intent("com.lsh.newtip");
				            	sendBroadcast(i);
							}
						}
					}
				}

				@Override
				public void onFailure(Throwable arg0, String arg1) {
					super.onFailure(arg0, arg1);
					log.i("tipMessage网络请求错误");
				}
			});
		}
	}
	//此方法是为了可以在Acitity中获得服务的实例   

	public class ServiceBinder extends Binder {
	        public TipMessageService getService() {
	            return TipMessageService.this;
	        }
	    }
	public void getMessage(){
		System.out.println("---------create service");
//		userId = intent.getStringExtra("userId");
		 timer = new Timer(); 
		    task = new TimerTask() {  
		        @Override  
		        public void run() {  
		        	visitMy();
		        }  
		    };  
		timer.schedule(task, 1000, 30*60*1000);
	}

}
