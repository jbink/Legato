package net.jfun.legato.login;

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
import net.jfun.legato.MainActivity;
import net.jfun.legato.MyData;
import net.jfun.legato.R;
import net.jfun.legato.api.API_Adapter;
import net.jfun.legato.api.BaseDTO;
import net.jfun.legato.util.Constant;
import net.jfun.legato.util.DialogUtils;
import net.jfun.legato.util.SharedPreferencesUtils;
import net.jfun.legato.util.Util;

public class FindIdActivity extends BaseActivity {

    private Context mContext;
    private EditText mEdtEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id);

        mContext = FindIdActivity.this;

        mEdtEmail = findViewById(R.id.findid_edt_id);
    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.findid_btn_back:
                finish();
                break;
            case R.id.findid_btn_ok :
                if (validationCheck() == true){
                    api_FindId();
                }
                break;
        }
    }

    private boolean validationCheck() {
        String email = mEdtEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(mContext, getString(R.string.toast_input_phone_number), Toast.LENGTH_SHORT).show();
            return false;
        } else if (email.equals(getString(R.string.phone_number))) {
            Toast.makeText(mContext, getString(R.string.toast_input_phone_number), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    /**
     * 아이디찾기
     */
    private void api_FindId(){

        String email = mEdtEmail.getText().toString();

        //POST
        retrofit2.Call<BaseDTO> data = API_Adapter.getInstance().findEmail(
                email
        );

        data.enqueue(new retrofit2.Callback<BaseDTO>() {
            @Override
            public void onResponse(retrofit2.Call<BaseDTO> call, retrofit2.Response<BaseDTO> response) {
                // 성공여부
                if(response.isSuccessful()){
                    BaseDTO member = response.body();
                    if(Constant.API_RESPONSE_SUCCESS == member.getResult()) {
                        DialogUtils.showCommonErrorDialog(FindIdActivity.this, member.getContent());
                    } else if(Constant.API_RESPONSE_ERROR_0 == member.getResult()){
                        Toast.makeText(mContext, member.getMessage(), Toast.LENGTH_SHORT).show();
                    }else if( Constant.API_RESPONSE_ERROR_1 == member.getResult()){
                        Toast.makeText(mContext, member.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        DialogUtils.showCommonErrorDialog(FindIdActivity.this);
                    }
                } else {
                    Log.d("where", "error :"+response.message());
                    DialogUtils.showCommonErrorDialog(FindIdActivity.this);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<BaseDTO> call, Throwable t) {
                Log.d("where", "실패 : " +t.toString());
            }
        });
    }
}
