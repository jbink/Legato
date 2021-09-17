package net.jfun.legato.setting.withdraw;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import net.jfun.legato.BaseActivity;
import net.jfun.legato.MyData;
import net.jfun.legato.R;
import net.jfun.legato.api.API_Adapter;
import net.jfun.legato.api.BaseDTO;
import net.jfun.legato.qr.QrActivity;
import net.jfun.legato.setting.myinfo.MyInfoDetailActivity;
import net.jfun.legato.util.Constant;
import net.jfun.legato.util.CustomDialog;
import net.jfun.legato.util.DialogUtils;

public class WithdrawActivity extends BaseActivity {

    private Context mContext;

    private TextView mTvName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);

        mContext = WithdrawActivity.this;

        mTvName = findViewById(R.id.withdraw_name);
        mTvName.setText(MyData.getInstance().getName());
    }

    public void onClick(View v){
        Intent intent;
        switch (v.getId()) {
            case R.id.withdraw_btn_back :
                finish();
                break;
            case R.id.withdraw_btn_cancel :
                finish();
                break;
            case R.id.withdraw_btn_ok :
                confirmDialog();
                break;
        }
    }

    /**
     * Dialog
     */
    CustomDialog confirmDlg;
    public void confirmDialog(){
        boolean resultValue = false;
        confirmDlg = new CustomDialog(mContext, getString(R.string.confirm_withdraw), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDlg.dismiss();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDlg.dismiss();
                api_Withdraw();
            }
        }, getString(R.string.btn_cancel), getString(R.string.btn_ok));
        confirmDlg.show();
    }

    /**
     * 회원탈퇴
     */
    private void api_Withdraw(){

        //POST
        retrofit2.Call<BaseDTO> data = API_Adapter.getInstance().withdraw(
               String.valueOf(MyData.getInstance().getUid())
        );

        data.enqueue(new retrofit2.Callback<BaseDTO>() {
            @Override
            public void onResponse(retrofit2.Call<BaseDTO> call, retrofit2.Response<BaseDTO> response) {
                // 성공여부
                if(response.isSuccessful()){
                    BaseDTO member = response.body();
                    if(Constant.API_RESPONSE_SUCCESS == member.getResult()) {
                        setResult(RESULT_OK);
                        finish();
                    } else if(Constant.API_RESPONSE_ERROR_0 == member.getResult()){
                        Toast.makeText(mContext, member.getMessage(), Toast.LENGTH_SHORT).show();
                    }else if( Constant.API_RESPONSE_ERROR_1 == member.getResult()){
                        Toast.makeText(mContext, member.getMessage(), Toast.LENGTH_SHORT).show();
                    } else if( Constant.API_RESPONSE_SUCCESS_2 == member.getResult()){
                        setResult(RESULT_FIRST_USER);
                        finish();
                    } else if( Constant.API_RESPONSE_SUCCESS_3 == member.getResult()){
                        setResult(3);
                        finish();
                    }else {
                        DialogUtils.showCommonErrorDialog(WithdrawActivity.this);
                    }
                } else {
                    Log.d("where", "error :"+response.message());
                    DialogUtils.showCommonErrorDialog(WithdrawActivity.this);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<BaseDTO> call, Throwable t) {
                Log.d("where", "실패 : " +t.toString());
            }
        });
    }
}
