package net.jfun.legato.login;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import net.jfun.legato.BaseActivity;
import net.jfun.legato.R;
import net.jfun.legato.api.API_Adapter;
import net.jfun.legato.api.BaseDTO;
import net.jfun.legato.util.Constant;
import net.jfun.legato.util.DialogUtils;

public class FindPwActivity extends BaseActivity {

    private Context mContext;
    private EditText mEdtEmail,mEdtPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pw);

        mContext = FindPwActivity.this;

        mEdtPhone = findViewById(R.id.findpw_edt_phone);
        mEdtEmail = findViewById(R.id.findpw_edt_id);
    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.findpw_btn_back:
                finish();
                break;
            case R.id.findpw_btn_ok :
                if (validationCheck() == true){
                    api_FindPw();
                }
                break;
        }
    }

    private boolean validationCheck() {
        String email = mEdtEmail.getText().toString();
        String phone = mEdtPhone.getText().toString();
        if (TextUtils.isEmpty(email) || email.equals(getString(R.string.email))) {
            Toast.makeText(mContext, getString(R.string.toast_input_email), Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(phone) || phone.equals(getString(R.string.phone_number))) {
            Toast.makeText(mContext, getString(R.string.toast_input_phone_number), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    /**
     * 회원가입
     */
    private void api_FindPw(){

        String email = mEdtEmail.getText().toString();
        String phone = mEdtPhone.getText().toString();

        //POST
        retrofit2.Call<BaseDTO> data = API_Adapter.getInstance().findPw(
                email, phone
        );

        data.enqueue(new retrofit2.Callback<BaseDTO>() {
            @Override
            public void onResponse(retrofit2.Call<BaseDTO> call, retrofit2.Response<BaseDTO> response) {
                // 성공여부
                if(response.isSuccessful()){
                    BaseDTO member = response.body();
                    if(Constant.API_RESPONSE_SUCCESS == member.getResult()) {
                        DialogUtils.showCommonErrorDialog(FindPwActivity.this, member.getContent());
                    } else if(Constant.API_RESPONSE_ERROR_0 == member.getResult()){
                        Toast.makeText(mContext, member.getMessage(), Toast.LENGTH_SHORT).show();
                    }else if( Constant.API_RESPONSE_ERROR_1 == member.getResult()){
                        Toast.makeText(mContext, member.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        DialogUtils.showCommonErrorDialog(FindPwActivity.this);
                    }
                } else {
                    Log.d("where", "error :"+response.message());
                    DialogUtils.showCommonErrorDialog(FindPwActivity.this);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<BaseDTO> call, Throwable t) {
                Log.d("where", "실패 : " +t.toString());
            }
        });
    }
}
