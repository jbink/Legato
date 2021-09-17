package net.jfun.legato.setting.myinfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import net.jfun.legato.BaseActivity;
import net.jfun.legato.MyData;
import net.jfun.legato.R;
import net.jfun.legato.api.API_Adapter;
import net.jfun.legato.api.BaseDTO;
import net.jfun.legato.login.FindPwActivity;
import net.jfun.legato.util.Constant;

public class ChangePwActivity extends BaseActivity {

    private Context mContext;
    private EditText mEdtCurrentPassword, mEdtNewPassword, mEdtNewPasswordConfirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_change_pw);

        mContext = ChangePwActivity.this;

        mEdtCurrentPassword = findViewById(R.id.change_pw_edt_cur_pw);
        mEdtNewPassword = findViewById(R.id.change_pw_edt_new_pw);
        mEdtNewPasswordConfirm = findViewById(R.id.change_pw_edt_new_pw_confirm);
    }

    public void onClick(View v){
        Intent intent;
        switch (v.getId()) {
            case R.id.change_pw_detail_btn_back :
                finish();
                break;
            case R.id.change_pw_detail_btn_save :
                intent = new Intent(mContext, MyInfoDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.change_pw_detail_btn_find_pw :
                intent = new Intent(mContext, FindPwActivity.class);
                startActivity(intent);
                break;
            case R.id.change_pw_btn_save :
                if (validationCheck() == true) {
                    api_SetPassword();
                }
                break;
        }
    }

    private boolean validationCheck() {
        if (TextUtils.isEmpty(mEdtCurrentPassword.getText().toString())
                || getString(R.string.current_pw).equals(mEdtCurrentPassword.getText().toString())) {
            Toast.makeText(mContext, getString(R.string.toast_input_pw), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(mEdtNewPassword.getText().toString())
                || getString(R.string.new_pw).equals(mEdtNewPassword.getText().toString())) {
            Toast.makeText(mContext, getString(R.string.toast_input_pw), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mEdtNewPassword.getText().toString().equals(mEdtNewPasswordConfirm.getText().toString())) {
            Toast.makeText(mContext, getString(R.string.password_not_equal), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 비밀번호 찾기
     */
    private void api_SetPassword(){

        //POST
        retrofit2.Call<BaseDTO> data = API_Adapter.getInstance().setPassword(
                mEdtNewPassword.getText().toString(), 
                String.valueOf(MyData.getInstance().getUid())
        );

        data.enqueue(new retrofit2.Callback<BaseDTO>() {
            @Override
            public void onResponse(retrofit2.Call<BaseDTO> call, retrofit2.Response<BaseDTO> response) {
                // 성공여부
                if(response.isSuccessful()){
                    BaseDTO member = response.body();
                    if(Constant.API_RESPONSE_SUCCESS == member.getResult()) {

                    } else if(Constant.API_RESPONSE_ERROR_0 == member.getResult()){
                        Toast.makeText(mContext, member.getMessage(), Toast.LENGTH_SHORT).show();
                    }else if( Constant.API_RESPONSE_ERROR_1 == member.getResult()){
                        Toast.makeText(mContext, member.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                    }
                } else {
                    Log.d("where", "error :"+response.message());
                }
            }

            @Override
            public void onFailure(retrofit2.Call<BaseDTO> call, Throwable t) {
                Log.d("where", "실패 : " +t.toString());
            }
        });
    }
}
