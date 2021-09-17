package net.jfun.legato.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import net.jfun.legato.BaseActivity;
import net.jfun.legato.MyData;
import net.jfun.legato.R;
import net.jfun.legato.WebviewActivity;
import net.jfun.legato.login.LoginActivity;
import net.jfun.legato.setting.instrument.IntrumentReplaceActivity;
import net.jfun.legato.setting.myinfo.MyInfoActivity;
import net.jfun.legato.setting.withdraw.WithdrawActivity;
import net.jfun.legato.tutorial.TutorialActivity;
import net.jfun.legato.util.Constant;
import net.jfun.legato.util.CustomDialog;
import net.jfun.legato.util.SharedPreferencesUtils;

public class SettingActivity extends BaseActivity {

    private Context mContext;

    private TextView mTvName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_setting);

        mContext = SettingActivity.this;

        mTvName = findViewById(R.id.setting_txt_name);
        mTvName.setText(MyData.getInstance().getName());

        if (Constant.EN.equals(SharedPreferencesUtils.getStringSharedPreference(mContext, Constant.PREF_LANGUAGE))) {
            findViewById(R.id.setting_txt_name_nim).setVisibility(View.GONE);
        }

    }

    public void onClick(View v){
        Intent intent;
        switch (v.getId()) {
            case R.id.frag_setting_btn_logout :
                logoutDialog();
                break;
            case R.id.frag_setting_buy_coffee :
                intent = new Intent(mContext, WebviewActivity.class);
                intent.putExtra(Constant.EXTRA_URL, "http://j-fun.batns.co.kr/sCoffee");
                startActivity(intent);
                break;
            case R.id.frag_setting_mypage :
                intent = new Intent(mContext, MyInfoActivity.class);
                startActivityForResult(intent, Constant.REQ_SET_MYPAGE);
//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//                // Create new fragment and transaction
//                Fragment newFragment = new MyInfoFragment();
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//
//                // Replace whatever is in the fragment_container view with this fragment,
//                // and add the transaction to the back stack
//                transaction.replace(R.id.main_fragment_area, newFragment);
//                transaction.addToBackStack(null);
//
//                // Commit the transaction
//                transaction.commitAllowingStateLoss();
                break;
            case R.id.frag_customer_center :
                intent = new Intent(mContext, WebviewActivity.class);
                intent.putExtra(Constant.EXTRA_URL, "http://j-fun.batns.co.kr/110");
                startActivity(intent);
                break;
            case R.id.frag_setting_device :
                intent = new Intent(mContext, IntrumentReplaceActivity.class);
                startActivity(intent);
                break;
            case R.id.frag_setting_clause_1 :
                intent = new Intent(mContext, WebviewActivity.class);
                intent.putExtra(Constant.EXTRA_URL, "http://j-fun.batns.co.kr/?mode=privacy");
                startActivity(intent);
                break;
            case R.id.frag_setting_clause_2 :
                intent = new Intent(mContext, WebviewActivity.class);
                intent.putExtra(Constant.EXTRA_URL, "http://j-fun.batns.co.kr/?mode=policy");
                startActivity(intent);
                break;
            case R.id.frag_setting_tutorial :
                intent = new Intent(mContext, WebviewActivity.class);
                intent.putExtra(Constant.EXTRA_URL, "http://j-fun.batns.co.kr/125?preview_mode=1");
                startActivity(intent);
//                intent = new Intent(mContext, TutorialActivity.class);
//                startActivity(intent);
                break;
            case R.id.frag_setting_withdraw :
                intent = new Intent(mContext, WithdrawActivity.class);
                startActivityForResult(intent, Constant.REQ_WITHDRAW);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQ_WITHDRAW) {
            if(resultCode == RESULT_OK) {
                //초기화 & 앱재시작
                MyData.getInstance().initMyData();
                SharedPreferencesUtils.putStringSharedPreference(mContext, Constant.PREF_ID, null);
                SharedPreferencesUtils.putStringSharedPreference(mContext, Constant.PREF_PW, null);

                Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
                startActivity(intent);
            }
        }
    }

    CustomDialog customDialog;
    public void logoutDialog(){
        customDialog = new CustomDialog(mContext, getString(R.string.msg_logout), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog.dismiss();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog.dismiss();
                MyData.getInstance().initMyData();
                SharedPreferencesUtils.putStringSharedPreference(mContext, Constant.PREF_ID, null);
                SharedPreferencesUtils.putStringSharedPreference(mContext, Constant.PREF_PW, null);

                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, "취소", "로그아웃");
        customDialog.show();
    }
}
