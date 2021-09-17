package net.jfun.legato.setting.myinfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import net.jfun.legato.BaseActivity;
import net.jfun.legato.R;

public class MyInfoActivity extends BaseActivity {

    private Context mContext;
    private RelativeLayout mLayMyPage, mLayAddress, mLayChangePw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_myinfo);

        mContext = MyInfoActivity.this;

        mLayMyPage = findViewById(R.id.setting_myinfo_info);
        mLayChangePw = findViewById(R.id.setting_myinfo_change_pw);
    }

    public void onClick(View v){
        Intent intent;
        switch (v.getId()) {
            case R.id.setting_myinfo_btn_back :
                finish();
                break;
            case R.id.setting_myinfo_info :
                intent = new Intent(mContext, MyInfoDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.setting_myinfo_change_pw :
                intent = new Intent(mContext, ChangePwActivity.class);
                startActivity(intent);
                break;
        }
    }
}
