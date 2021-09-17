package net.jfun.legato.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import net.jfun.legato.BaseActivity;
import net.jfun.legato.MainActivity;
import net.jfun.legato.MyData;
import net.jfun.legato.R;
import net.jfun.legato.WebviewActivity;
import net.jfun.legato.api.API_Adapter;
import net.jfun.legato.api.BaseDTO;
import net.jfun.legato.util.Constant;
import net.jfun.legato.util.DialogUtils;
import net.jfun.legato.util.SharedPreferencesUtils;
import net.jfun.legato.util.Util;

public class SignupActivity extends BaseActivity {

    private Context mContext;

    private TextView mTvSendAuthCode, mTvConfirmAuthCode;
    private LinearLayout mLayInfo, mLayClause;
    private EditText mEdtEmail, mEdtAuthCode, mEdtPw, mEdtName, mEdtBirth, mEdtPhone, mEdtAddress;

    private CheckBox[] mChkClause;
    private CheckBox mChkClauseAll;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mContext = SignupActivity.this;

        mTvSendAuthCode = findViewById(R.id.signup_tv_send_authcode);
        mLayInfo = findViewById(R.id.signup_lay_info);
        mLayClause = findViewById(R.id.signup_lay_clause);
        mTvConfirmAuthCode = findViewById(R.id.signup_tv_confirm_authcode);
        mEdtEmail = findViewById(R.id.signup_edt_email);
        mEdtAuthCode = findViewById(R.id.signup_edt_authcode);
        mEdtPw = findViewById(R.id.signup_edt_pw);
        mEdtName = findViewById(R.id.signup_edt_name);
        mEdtBirth = findViewById(R.id.signup_edt_birth);
        mEdtPhone = findViewById(R.id.signup_edt_phone);
        mEdtAddress = findViewById(R.id.signup_edt_address);

        mChkClauseAll = findViewById(R.id.signup_chk_all);
        mChkClause = new CheckBox[]{
                findViewById(R.id.signup_chk_0),
                findViewById(R.id.signup_chk_1),
                findViewById(R.id.signup_chk_2),
                findViewById(R.id.signup_chk_3),
                findViewById(R.id.signup_chk_4),
                findViewById(R.id.signup_chk_5),
                findViewById(R.id.signup_chk_6)
        };
    }

    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.signup_btn_ok :
                if (validationCheck() == true){
                    api_Join();
                }
                break;
            case R.id.signup_chk_all :
                setViewCheckAllClauseLayout();
                break;
            case R.id.signup_btn_back_title :
            case R.id.signup_btn_back :
                finish();
                break;
            case R.id.signup_btn_0 : //만 14세 이상입니다
                break;
            case R.id.signup_btn_1 : //레가토계정 약관
                intent = new Intent(mContext, WebviewActivity.class);
                intent.putExtra(Constant.EXTRA_URL, "http://j-fun.batns.co.kr/?mode=policy");
                startActivity(intent);
                break;
            case R.id.signup_btn_2 : //레가토 통합서비스 약관
                intent = new Intent(mContext, WebviewActivity.class);
                intent.putExtra(Constant.EXTRA_URL, "http://j-fun.batns.co.kr/125?preview_mode=1");
                startActivity(intent);
                break;
            case R.id.signup_chk_0 :
            case R.id.signup_chk_1 :
            case R.id.signup_chk_2 :
            case R.id.signup_chk_3 :
            case R.id.signup_chk_4 :
            case R.id.signup_chk_5 :
                setViewCheckClauseLayout();
            break;
            case R.id.signup_btn_send_authcode :
                String email = mEdtEmail.getText().toString();
                if(!TextUtils.isEmpty(email) || !getString(R.string.email).equals(email)) {
                    api_SendAuthCode();
                } else {
                    Toast.makeText(mContext, getString(R.string.toast_input_authcode), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.signup_btn_confirm_authcode :
                if(!TextUtils.isEmpty(mEdtAuthCode.getText().toString())) {
                    api_ConfirmAuthCode();
                } else {
                    Toast.makeText(mContext, getString(R.string.toast_input_authcode), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private boolean validationCheck() {
        String email = mEdtEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(mContext, getString(R.string.toast_input_email), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Util.isEmailValid(email) == false) {
            Toast.makeText(mContext, getString(R.string.toast_input_wrong_email), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(mEdtPw.getText().toString())) {
            Toast.makeText(mContext, getString(R.string.toast_input_pw), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(mEdtName.getText().toString())) {
            Toast.makeText(mContext, getString(R.string.toast_input_name), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(mEdtPhone.getText().toString()) || getString(R.string.phone_number).equals(mEdtPhone.getText().toString())){
            Toast.makeText(mContext, getString(R.string.toast_input_phone_number), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Constant.KR.equals(SharedPreferencesUtils.getStringSharedPreference(mContext, Constant.PREF_LANGUAGE))) {
            if (mChkClause[0].isChecked() == false) {
                Toast.makeText(mContext, "만 14세 이상 확인 동의가 필요합니다.", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (mChkClause[1].isChecked() == false) {
                Toast.makeText(mContext, "레가토계정 약관에 동의가 필요합니다.", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (mChkClause[2].isChecked() == false) {
                Toast.makeText(mContext, "레가토 통합서비스 약관에 동의가 필요합니다.", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (mChkClause[4].isChecked() == false) {
                Toast.makeText(mContext, "개인정보 수집에 동의가 필요합니다.", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (mChkClause[6].isChecked() == false) {
                Toast.makeText(mContext, "품질 개선을 위한 프로파일 정보수집에 동의가 필요합니다.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }

    private void setViewCheckClauseLayout() {
        if (mChkClause[0].isChecked() == true
                && mChkClause[1].isChecked() == true
                && mChkClause[2].isChecked() == true
                && mChkClause[3].isChecked() == true
                && mChkClause[4].isChecked() == true
                && mChkClause[5].isChecked() == true
                && mChkClause[6].isChecked() == true) {
            mChkClauseAll.setChecked(true);
        } else {
            mChkClauseAll.setChecked(false);
        }
    }

    private void setViewCheckAllClauseLayout() {
        for (int i=0 ; i<mChkClause.length ; i++) {
            if (mChkClauseAll.isChecked() == true) {
                mChkClause[i].setChecked(true);
            } else {
                mChkClause[i].setChecked(false);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }



    /**
     * 회원가입
     */
    private void api_Join(){

//        String password = Util.SHA1(mEdtPw.getText().toString());
        String email = mEdtEmail.getText().toString();
        String authCode = mEdtAuthCode.getText().toString();
        String password = mEdtPw.getText().toString();
        String name = mEdtName.getText().toString();
        String birth = mEdtBirth.getText().toString();
        String phone = mEdtPhone.getText().toString();
        String address = mEdtAddress.getText().toString();

        boolean is14YearsOlder = mChkClause[0].isChecked();
        boolean isAcountTerms = mChkClause[1].isChecked();
        boolean isIntegService = mChkClause[2].isChecked();
        boolean isAddChannel = mChkClause[3].isChecked();
        boolean isPrivacy = mChkClause[4].isChecked();
        boolean isLocation = mChkClause[5].isChecked();
        boolean isAddProfile = mChkClause[6].isChecked();

        //POST
        retrofit2.Call<SignupDTO> data = API_Adapter.getInstance().join(
                email,
                password,
                name,
                birth,
                phone,
                "",//고유한 디바이스 ID를 가져오는 방법에 대해 논의가 필요
                "",//MacAddress는 빈값형태로 보
                is14YearsOlder,
                isAcountTerms,
                isIntegService,
                isAddChannel,
                isPrivacy,
                isLocation,
                isAddProfile,
                "home",
                address,
                null,//detailAddress,
                null//zipCode,
        );

        data.enqueue(new retrofit2.Callback<SignupDTO>() {
            @Override
            public void onResponse(retrofit2.Call<SignupDTO> call, retrofit2.Response<SignupDTO> response) {
                // 성공여부
                if(response.isSuccessful()){
                    SignupDTO member = response.body();
                    if(Constant.API_RESPONSE_SUCCESS == member.getResult()) {
                        MyData.getInstance().setEmail(member.getContent().getEmail());
                        MyData.getInstance().setName(member.getContent().getName());
                        MyData.getInstance().setUid(member.getContent().getUid());
                        MyData.getInstance().setPhone(member.getContent().getPhone());


                        SharedPreferencesUtils.putStringSharedPreference(mContext, Constant.PREF_ID, member.getContent().getEmail());
                        SharedPreferencesUtils.putStringSharedPreference(mContext, Constant.PREF_PW, member.getContent().getPassword());

                        Intent intent = new Intent(mContext, MainActivity.class);
                        startActivity(intent);
                        setResult(RESULT_OK);
                        finish();

                    } else if(Constant.API_RESPONSE_ERROR_0 == member.getResult()){
                        Toast.makeText(mContext, member.getMessage(), Toast.LENGTH_SHORT).show();
                    }else if( Constant.API_RESPONSE_ERROR_1 == member.getResult()){
                        Toast.makeText(mContext, member.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        DialogUtils.showCommonErrorDialog(SignupActivity.this);
                    }
                } else {
                    Log.d("where", "error :"+response.message());
                    DialogUtils.showCommonErrorDialog(SignupActivity.this);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<SignupDTO> call, Throwable t) {
                Log.d("where", "실패 : " +t.toString());
            }
        });
    }

    /**
     * 인증번호 요청
     */
    private void api_SendAuthCode() {

        String email = mEdtEmail.getText().toString();

        //POST
        retrofit2.Call<BaseDTO> data = API_Adapter.getInstance().sendAuthCode(
                email
        );

        data.enqueue(new retrofit2.Callback<BaseDTO>() {
            @Override
            public void onResponse(retrofit2.Call<BaseDTO> call, retrofit2.Response<BaseDTO> response) {
                // 성공여부
                if (response.isSuccessful()) {
                    BaseDTO member = response.body();
                    if (Constant.API_RESPONSE_SUCCESS == member.getResult()) {
                        mTvSendAuthCode.setVisibility(View.VISIBLE);
                    } else if (Constant.API_RESPONSE_ERROR_0 == member.getResult()) {
                        Toast.makeText(mContext, member.getMessage(), Toast.LENGTH_SHORT).show();
                    } else if (Constant.API_RESPONSE_ERROR_1 == member.getResult()) {
                        Toast.makeText(mContext, member.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        DialogUtils.showCommonErrorDialog(SignupActivity.this);
                    }
                } else {
                    Log.d("where", "error :" + response.message());
                    DialogUtils.showCommonErrorDialog(SignupActivity.this);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<BaseDTO> call, Throwable t) {
                Log.d("where", "실패 : " + t.toString());
            }
        });
    }
    /**
     * 인증번호 확인
     */
    private void api_ConfirmAuthCode() {

        String email = mEdtEmail.getText().toString();
        String authCode = mEdtAuthCode.getText().toString();

        //POST
        retrofit2.Call<BaseDTO> data = API_Adapter.getInstance().confirmAuthCode(
                email, authCode
        );

        data.enqueue(new retrofit2.Callback<BaseDTO>() {
            @Override
            public void onResponse(retrofit2.Call<BaseDTO> call, retrofit2.Response<BaseDTO> response) {
                // 성공여부
                if (response.isSuccessful()) {
                    BaseDTO member = response.body();
                    if (Constant.API_RESPONSE_SUCCESS == member.getResult()) {
                        mTvConfirmAuthCode.setVisibility(View.VISIBLE);
                        mLayInfo.setVisibility(View.VISIBLE);
                        if (Constant.EN.equals(SharedPreferencesUtils.getStringSharedPreference(mContext, Constant.PREF_LANGUAGE))){
                            mLayClause.setVisibility(View.GONE);
                        } else{
                            mLayClause.setVisibility(View.VISIBLE);
                        }
                        mTvConfirmAuthCode.setText(getString(R.string.toast_authcode_success));
                    } else if (Constant.API_RESPONSE_ERROR_0 == member.getResult()) {
                        Toast.makeText(mContext, getString(R.string.toast_authcode_fail), Toast.LENGTH_SHORT).show();
                    } else if (Constant.API_RESPONSE_ERROR_1 == member.getResult()) {
                        Toast.makeText(mContext, getString(R.string.toast_authcode_fail), Toast.LENGTH_SHORT).show();
                    } else {
                        DialogUtils.showCommonErrorDialog(SignupActivity.this);
                    }
                } else {
                    Log.d("where", "error :" + response.message());
                    DialogUtils.showCommonErrorDialog(SignupActivity.this);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<BaseDTO> call, Throwable t) {
                Log.d("where", "실패 : " + t.toString());
            }
        });
    }
}
