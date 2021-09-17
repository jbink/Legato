package net.jfun.legato.roast;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import net.jfun.legato.BaseActivity;
import net.jfun.legato.R;
import net.jfun.legato.setting.language.LocaleWrapper;
import net.jfun.legato.util.Constant;
import net.jfun.legato.util.SharedPreferencesUtils;

import java.util.Locale;

public class FanOnDialogActivity extends BaseActivity {

    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fan_on_dialog);

        mContext = FanOnDialogActivity.this;

    }



    public void onClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.lan_select_eitire :
            case R.id.fan_on_ok_lay :
            case R.id.fan_on_ok :
                finish();
                break;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleWrapper.wrap(newBase));
    }

}
