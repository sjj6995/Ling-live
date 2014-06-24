package com.android.storemanage.fragment;

import com.android.storemanage.R;
import com.android.storemanage.application.BaseApplication;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class BaseFragment extends Fragment {
	private Dialog progressDialog;
//	protected DisplayImageOptions options;
//	protected ImageLoader imageLoader = ImageLoader.getInstance();
	protected BaseApplication application;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		application = BaseApplication.getApplication();
//		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.img_empty)
//				.showImageForEmptyUri(R.drawable.img_empty).showImageOnFail(R.drawable.img_empty).cacheInMemory(true)
//				.cacheOnDisk(true).considerExifParams(true).displayer(new RoundedBitmapDisplayer(20)).build();
	}
	
	public final void showProgressDialog(int msgId) {
		showProgressDialog(msgId, false);
	}

	public final void showProgressDialog(final int msgId, final boolean cancelable) {
		if (getActivity() == null) {
			return;
		}
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (progressDialog == null || !progressDialog.isShowing()) {
					progressDialog = new Dialog(getActivity());
					progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					progressDialog.setContentView(R.layout.progress_dialog);
					progressDialog.setCancelable(cancelable);
					progressDialog.setCanceledOnTouchOutside(false);
					if (cancelable) {
						progressDialog.setOnCancelListener(new OnCancelListener() {
							@Override
							public void onCancel(DialogInterface dialog) {
								showToast(getString(android.R.string.cancel));
							}
						});
					}
				}
				progressDialog.show();
			}
		});
	}
	
	public final void showProgressDialog(final int msgId, final boolean cancelable, final int theme) {
		if (getActivity() == null) {
			return;
		}
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (progressDialog == null || !progressDialog.isShowing()) {
					progressDialog = new Dialog(getActivity());
					progressDialog.setContentView(R.layout.progress_dialog);
					TextView content = (TextView) progressDialog.findViewById(R.id.content);
					content.setText(msgId);
					progressDialog.setCancelable(cancelable);
					progressDialog.setCanceledOnTouchOutside(false);
					if (cancelable) {
						progressDialog.setOnCancelListener(new OnCancelListener() {
							@Override
							public void onCancel(DialogInterface dialog) {
								showToast(getString(android.R.string.cancel));
							}
						});
					}
				}
			}
		});
	}
	
	public final void dismissProgressDialog() {
		if (getActivity() == null) {
			return;
		}
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (progressDialog != null && progressDialog.isShowing()) {
					progressDialog.dismiss();
				}
			}});
	}

	
	/**
	 * show toast, the method can be called in thread.
	 * 
	 * @param message
	 *            message to show
	 */
	protected final void showToast(final String message) {
		if (getActivity() == null) {
			return;
		}
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG).show();
			}
		});
	}
}
