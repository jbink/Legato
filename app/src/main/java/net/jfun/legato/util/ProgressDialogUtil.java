package net.jfun.legato.util;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogUtil {
private ProgressDialog mProgressDialog;
	
	public void showProgress(Context context, String content) {
		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDialog(context);
			mProgressDialog.setIndeterminate(true);
			mProgressDialog.setMessage(content);
			mProgressDialog.setCancelable(true);
			mProgressDialog.show();
//			mProgressDialog.setContentView(R.layout.custom_progress_dialog);
//			if (Build.VERSION_CODES.KITKAT < Build.VERSION.SDK_INT) {
//              R.style.ProgressDialogStyle은 커스텀으로 정의한 스타일임
//				mProgressDialog = new ProgressDialog(context, R.style.ProgressDialogStyle);
//			}
//			else {
//				mProgressDialog = new ProgressDialog(context, R.style.ProgressDialogStyle_lowVersion);
//			}
//			mProgressDialog.setMessage("Loading...");
//			mProgressDialog.setIndeterminate(true);
		}
		mProgressDialog.show();
	}
	
	public void dismissProgress() {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}
}
