package net.jfun.legato.setting.instrument;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import net.jfun.legato.BaseActivity;
import net.jfun.legato.MyData;
import net.jfun.legato.R;
import net.jfun.legato.api.API_Adapter;
import net.jfun.legato.api.BaseDTO;
import net.jfun.legato.util.Constant;
import net.jfun.legato.util.CustomDialog;
import net.jfun.legato.util.DialogUtils;

public class IntrumentReplaceActivity extends BaseActivity {

    private Context mContext;
    private TextView mTvDeviceID;
    private TextView mTvDeviceAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replace_instrument);

        mContext = IntrumentReplaceActivity.this;

        mTvDeviceID = findViewById(R.id.replace_instrument_id);
        mTvDeviceID.setText(MyData.getInstance().getDeviceID());
        mTvDeviceAddress = findViewById(R.id.replace_instrument_address);
        mTvDeviceAddress.setText(MyData.getInstance().getMacAddress());
    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.replace_instrument_btn_back:
                finish();
                break;
            case R.id.replace_instrument_delete :
                confirmDialog();
                break;
        }
    }


    /**
     * MacAddress 변경
     */
    private void api_DeleteMacAddress(){

        String email = MyData.getInstance().getEmail();

        //POST
        retrofit2.Call<BaseDTO> data = API_Adapter.getInstance().setMacAddress(
                null, null, email
        );

        data.enqueue(new retrofit2.Callback<BaseDTO>() {
            @Override
            public void onResponse(retrofit2.Call<BaseDTO> call, retrofit2.Response<BaseDTO> response) {
                // 성공여부
                if(response.isSuccessful()){
                    BaseDTO member = response.body();
                    if(Constant.API_RESPONSE_SUCCESS == member.getResult()) {
                        DialogUtils.showCommonErrorDialog(IntrumentReplaceActivity.this, member.getContent());
                    } else if(Constant.API_RESPONSE_ERROR_0 == member.getResult()){
                        Toast.makeText(mContext, member.getMessage(), Toast.LENGTH_SHORT).show();
                        MyData.getInstance().setDeviceID("");
                        MyData.getInstance().setMacAddress("");
                        mTvDeviceID.setText(MyData.getInstance().getDeviceID());
                        mTvDeviceAddress.setText(MyData.getInstance().getMacAddress());
                    }else if( Constant.API_RESPONSE_ERROR_1 == member.getResult()){
                        Toast.makeText(mContext, member.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        DialogUtils.showCommonErrorDialog(IntrumentReplaceActivity.this);
                    }
                } else {
                    Log.d("where", "error :"+response.message());
                    DialogUtils.showCommonErrorDialog(IntrumentReplaceActivity.this);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<BaseDTO> call, Throwable t) {
                Log.d("where", "실패 : " +t.toString());
            }
        });
    }

    /**
     * Dialog
     */
    CustomDialog confirmDlg;
    public void confirmDialog(){
        boolean resultValue = false;
        confirmDlg = new CustomDialog(mContext, getString(R.string.instrument_delete_txt), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDlg.dismiss();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDlg.dismiss();
                api_DeleteMacAddress();
            }
        }, getString(R.string.btn_cancel), getString(R.string.btn_ok));
        confirmDlg.show();
    }
}
