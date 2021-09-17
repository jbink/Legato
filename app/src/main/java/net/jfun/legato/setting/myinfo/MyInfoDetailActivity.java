package net.jfun.legato.setting.myinfo;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import net.jfun.legato.BaseActivity;
import net.jfun.legato.MyData;
import net.jfun.legato.R;

public class MyInfoDetailActivity extends BaseActivity {

    private Context mContext;
    private TextView mTvEmail;
    private EditText mEdtName, mEdtAddress, mEdtPhone, mEdtBirth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_myinfo_detail);

        mContext = MyInfoDetailActivity.this;

        mTvEmail = findViewById(R.id.setting_myinfo_detail_tv_email);
        mEdtName = findViewById(R.id.setting_myinfo_detail_edt_name);
        mEdtAddress = findViewById(R.id.setting_myinfo_detail_edt_address);
        mEdtPhone = findViewById(R.id.setting_myinfo_detail_edt_phone);
        mEdtBirth = findViewById(R.id.setting_myinfo_detail_edt_birth);

        mTvEmail.setText(MyData.getInstance().getEmail());
        mEdtName.setText(MyData.getInstance().getName());
        mEdtAddress.setText(MyData.getInstance().getAddress());
        mEdtPhone.setText(MyData.getInstance().getPhone());
        mEdtBirth.setText(MyData.getInstance().getBirth());
    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.setting_myinfo_detail_btn_back :
                finish();
                break;
            case R.id.setting_myinfo_detail_btn_save :
                break;
        }
    }
}
