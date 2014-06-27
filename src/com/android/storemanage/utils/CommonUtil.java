package com.android.storemanage.utils;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.client.HttpResponseException;

import com.android.storemanage.net.AsyncHttpResponseHandler;
import com.android.storemanage.net.RequestParams;
import com.android.storemanage.net.HttpClient;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * @author rongfzh
 * 
 */
public class CommonUtil {

	public static SharedPreferences getSharedPreferences(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences("xdsharepreferences", Context.MODE_PRIVATE);
		return sharedPreferences;
	}

	public static boolean hasSDCard() {
		String status = Environment.getExternalStorageState();
		if (!status.equals(Environment.MEDIA_MOUNTED)) {
			return false;
		}
		return true;
	}

	public static String getRootFilePath() {
		if (hasSDCard()) {
			return Environment.getExternalStorageDirectory().getAbsolutePath() + "/";// filePath:/sdcard/
		} else {
			return Environment.getDataDirectory().getAbsolutePath() + "/data/"; // filePath:
																				// /data/data/
		}
	}

	public static boolean checkNetState(Context context) {
		boolean netstate = false;
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						netstate = true;
						break;
					}
				}
			}
		}
		return netstate;
	}

	public static void showToast(Context context, String tip) {
		// Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
	}

	@SuppressWarnings("deprecation")
	public static int getScreenWidth(Context context) {
		WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		return display.getWidth();
	}

	@SuppressWarnings("deprecation")
	public static int getScreenHeight(Context context) {
		WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		return display.getHeight();
	}

	public static boolean checkEmail(String email) {
		// 验证邮箱的正则表达式
		String format = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$";
		if (email.matches(format)) {
			return true;
			// 邮箱名合法，返回true
		} else {
			return false;
			// 邮箱名不合法，返回false
		}
	}

	public static boolean checkUser(String user) {
		String format = "^[a-zA-Z0-9\u4e00-\u9fa5]{3,}$";
		if (user.matches(format)) {
			return true;
			// 邮箱名合法，返回true
		} else {
			return false;
			// 邮箱名不合法，返回false
		}
	}

	public static boolean checkPassword(String password) {
		String format = "^[a-zA-Z0-9]{6,}$";
		if (password.matches(format)) {
			return true;
			// 邮箱名合法，返回true
		} else {
			return false;
			// 邮箱名不合法，返回false
		}
	}

	public static String getMD5(String string) {
		byte[] source = string.getBytes();
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };// 用来将字节转换成16进制表示的字符
		String s = null;
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest();// MD5 的计算结果是一个 128 位的长整数，
			// 用字节表示就是 16 个字节
			char str[] = new char[16 * 2];// 每个字节用 16 进制表示的话，使用两个字符， 所以表示成 16
			// 进制需要 32 个字符
			int k = 0;// 表示转换结果中对应的字符位置
			for (int i = 0; i < 16; i++) {// 从第一个字节开始，对 MD5 的每一个字节// 转换成 16
				// 进制字符的转换
				byte byte0 = tmp[i];// 取第 i 个字节
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];// 取字节中高 4 位的数字转换,// >>>
				// 为逻辑右移，将符号位一起右移
				str[k++] = hexDigits[byte0 & 0xf];// 取字节中低 4 位的数字转换
			}
			s = new String(str);// 换后的结果转换为字符串

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * 获取版本号
	 * 
	 * @return 当前应用的版本号
	 */
	public static String getVersion(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
			String version = info.versionName;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String getDateTime(String dateStr) {
		if (!TextUtils.isEmpty(dateStr)) {
			dateStr = dateStr.substring(0, 10);
		}
		return dateStr;
	}

	/**
	 * 
	 * @param context
	 * @param isShowAlert
	 *            是否弹出已经是最新版本按钮
	 */
	static public void checkVersion(final Context context, final Boolean isShowAlert) {
		if (CommonUtil.checkNetState(context)) {
			RequestParams params = new RequestParams();
			HttpClient.post(JFConfig.HOST_URL, params, new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, String content) {
				}

				@Override
				public void onFailure(Throwable arg0, String arg1) {
					super.onFailure(arg0, arg1);
				}
			});
		} else {
		}
	}

	/**
	 * 转换文件大小
	 * 
	 * @param fileS
	 * @return B/KB/MB/GB
	 */
	public static String formatFileSize(long fileS) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
		String fileSizeString = "";
		if(fileS == 0){
			fileSizeString ="0KB";
		}else if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "KB";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "MB";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	/**
	 * 转换文件大小
	 * 
	 * @param fileS
	 * @return B/KB/MB/GB
	 */
	public static String formatFileSizeMB(long fileS) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("0.##");
		String fileSizeString = "";
		fileSizeString = df.format((double) fileS / 1048576) + "MB";
		return fileSizeString;
	}

	// long类型转换为String类型
	// currentTime要转换的long类型的时间
	// formatType要转换的string类型的时间格式
	public static String longToString(long currentTime, String formatType) throws ParseException {
		Date date = longToDate(currentTime, formatType); // long类型转成Date类型
		String strTime = dateToString(date, formatType); // date类型转成String
		return strTime;
	}

	// long转换为Date类型
	// currentTime要转换的long类型的时间
	// formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
	public static Date longToDate(long currentTime, String formatType) throws ParseException {
		Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
		String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
		Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
		return date;
	}

	// date类型转换为String类型
	// formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
	// data Date类型的时间
	public static String dateToString(Date data, String formatType) {
		return new SimpleDateFormat(formatType).format(data);
	}

	// string类型转换为date类型
	// strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
	// HH时mm分ss秒，
	// strTime的时间格式必须要与formatType的时间格式相同
	public static Date stringToDate(String strTime, String formatType) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(formatType);
		Date date = null;
		date = formatter.parse(strTime);
		return date;
	}

	public static void onFailure(Throwable error, Context mContext) {
		if (error instanceof UnknownHostException) {
			Toast.makeText(mContext, "网络中断,请检查您的网络状况", Toast.LENGTH_SHORT).show();
		} else if (error instanceof HttpResponseException) {
			// 请求错误详情
			Toast.makeText(mContext, "请求错误，请稍后重试", Toast.LENGTH_SHORT).show();
		} else if (error instanceof SocketTimeoutException) {
			// 请求超时详情
			// mMessageBar.show(getResources().getString(R.string.load_socket_timeout_error));
			Toast.makeText(mContext, "网络超时，请检查网络后重试", Toast.LENGTH_SHORT).show();
		}
	}

}
