package net.jfun.legato.qr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

import net.jfun.legato.BaseActivity;
import net.jfun.legato.MyData;
import net.jfun.legato.R;
import net.jfun.legato.api.API_Adapter;
import net.jfun.legato.api.BaseDTO;
import net.jfun.legato.login.FindIdActivity;
import net.jfun.legato.util.Constant;
import net.jfun.legato.util.DialogUtils;

public class QrActivity extends BaseActivity {

    private Context mContext;
    IntentIntegrator integrator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_qr_scanner);

        mContext = QrActivity.this;

        integrator = new IntentIntegrator(QrActivity.this); //바코드 안의 텍스트
        integrator.setPrompt(getString(R.string.qr_text)); //바코드 인식시 소리 여부
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(true);
        integrator.setCaptureActivity(CaptureActivity.class); //바코드 스캐너 시작
        integrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                finish();
            } else { //qr코드를 읽어서 EditText에 입력해줍니다.
                api_SetProfileQr(result.getContents());
//                Intent intent = new Intent();
//                intent.putExtra("qr_value", result.getContents());
//                Toast.makeText(mContext, "Scanned: " + result.getContents(), Toast.LENGTH_SHORT).show();
//                setResult(RESULT_OK, intent);
//                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * QR Profile 등록
     */
    private void api_SetProfileQr(String profileName){


        //POST
        retrofit2.Call<BaseDTO> data = API_Adapter.getInstance().setProfileQr(
                profileName, String.valueOf(MyData.getInstance().getUid())
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
                        DialogUtils.showCommonErrorDialog(QrActivity.this);
                    }
                } else {
                    Log.d("where", "error :"+response.message());
                    DialogUtils.showCommonErrorDialog(QrActivity.this);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<BaseDTO> call, Throwable t) {
                Log.d("where", "실패 : " +t.toString());
            }
        });
    }
}
