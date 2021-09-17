package net.jfun.legato.util;

import android.view.View;

import net.jfun.legato.BaseActivity;
import net.jfun.legato.R;


public class DialogUtils {

    private static CustomDialog mCustomDialog;


    public static void showCommonErrorDialog(BaseActivity activity) {
        showCommonErrorDialog(activity, activity.getResources().getString(R.string.dlg_common_error_msg));
    }

    public static void showCommonErrorDialog(final BaseActivity activity,
                                             String errorMsg) {
        mCustomDialog = new CustomDialog(
                activity,
                errorMsg,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mCustomDialog.dismiss();
                        activity.finish();
                    }
                }, "확인");
        mCustomDialog.show();
    }

    public static void showTwoButtonDialog(
            BaseActivity activity,
            String msg,
            View.OnClickListener leftListener,
            View.OnClickListener rightListener,
            String leftBtnText,
            String rightBtnText) {
        mCustomDialog = new CustomDialog(activity,msg, leftListener, rightListener, leftBtnText, rightBtnText);
        mCustomDialog.show();
    }

    public static CustomDialog getCurrentDialog() {
        return mCustomDialog;
    }
}
