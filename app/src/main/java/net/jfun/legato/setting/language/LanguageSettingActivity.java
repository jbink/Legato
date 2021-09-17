package net.jfun.legato.setting.language;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import net.jfun.legato.BaseActivity;
import net.jfun.legato.R;
import net.jfun.legato.util.Constant;
import net.jfun.legato.util.SharedPreferencesUtils;

import java.util.Locale;

public class LanguageSettingActivity extends BaseActivity {

    private Context mContext;
    Locale mLocale;
    Configuration mConfigure;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.language_select_dialog);

        mContext = LanguageSettingActivity.this;

        mConfigure = new Configuration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mLocale = getApplicationContext().getResources().getConfiguration().getLocales().get(0);
        } else {
            mLocale = getApplicationContext().getResources().getConfiguration().locale;
        }


    }



    public void onClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.lan_select_eitire :
                finish();
                break;
            case R.id.lan_select_kr :
                SharedPreferencesUtils.putStringSharedPreference(mContext, Constant.PREF_LANGUAGE, Constant.KR);
                changeLanguage(Locale.KOREA.getLanguage());
                getResources().updateConfiguration(mConfigure, getResources().getDisplayMetrics());
                intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
                startActivity(intent);
                break;
            case R.id.lan_select_en :
                SharedPreferencesUtils.putStringSharedPreference(mContext, Constant.PREF_LANGUAGE, Constant.EN);
                changeLanguage(Locale.ENGLISH.getLanguage());
                getResources().updateConfiguration(mConfigure, getResources().getDisplayMetrics());
                intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
                startActivity(intent);

                break;
        }
    }
    public void changeLanguage(String lan) {
        LocaleWrapper.setLocale(lan);
        recreate();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleWrapper.wrap(newBase));
    }

}
