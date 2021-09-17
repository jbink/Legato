package net.jfun.legato.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import net.jfun.legato.BaseActivity;
import net.jfun.legato.MainActivity;
import net.jfun.legato.MyData;
import net.jfun.legato.R;
import net.jfun.legato.api.API_Adapter;
import net.jfun.legato.util.Constant;
import net.jfun.legato.util.DialogUtils;
import net.jfun.legato.util.SharedPreferencesUtils;
import net.jfun.legato.util.Util;

public class LoginActivity extends BaseActivity {

    private Context mContext;
    private EditText mEdtID, mEdtPW;
    private ImageView mIvIconView;

    private boolean mBoolPwViewCheck = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = LoginActivity.this;

        mEdtID = findViewById(R.id.login_edt_id);
        mEdtPW = findViewById(R.id.login_edt_pw);
        mIvIconView = findViewById(R.id.login_pw_view);

    }

    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.login_btn_signup :
                intent = new Intent(mContext, SignupActivity.class);
                startActivityForResult(intent, 6666);
                break;
            case R.id.login_btn_ok :
                if (validationCheck() == true){
                    api_Login();
                }
                break;
            case R.id.login_pw_view :
                if(mBoolPwViewCheck == true) {
                    mEdtPW.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mIvIconView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.icon_pw_view_off));
                } else {
                    mEdtPW.setInputType(InputType.TYPE_CLASS_TEXT);
                    mIvIconView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.icon_pw_view_on));
                }
                mBoolPwViewCheck = !mBoolPwViewCheck;
                break;
            case R.id.login_btn_find_id :
                intent = new Intent(mContext, FindIdActivity.class);
                startActivityForResult(intent, 7777);
                break;
            case R.id.login_btn_find_pw :
                intent = new Intent(mContext, FindPwActivity.class);
                startActivityForResult(intent, 7778);
                break;
        }
    }

    private boolean validationCheck() {
        String email = mEdtID.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(mContext, getString(R.string.toast_input_email), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Util.isEmailValid(email) == false) {
            Toast.makeText(mContext, getString(R.string.toast_input_wrong_email), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(mEdtPW.getText().toString())) {
            Toast.makeText(mContext, getString(R.string.toast_input_pw), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 6666) {
            if (resultCode == RESULT_OK) {
                finish();
            }
        }
    }

    /**
     * 로그인
     */
    private void api_Login(){

//        String password = Util.SHA1(mEdtPw.getText().toString());
        String email = mEdtID.getText().toString();
        String password = mEdtPW.getText().toString();

        //POST
        retrofit2.Call<SignupDTO> data = API_Adapter.getInstance().login(
                email,
                password
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
                        MyData.getInstance().setDeviceID(member.getContent().getDeviceID());
                        MyData.getInstance().setMacAddress(member.getContent().getMacAddress());
                        MyData.getInstance().setUseCount(member.getContent().getUseCount());


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
                        DialogUtils.showCommonErrorDialog(LoginActivity.this);
                    }
                } else {
                    Log.d("where", "error :"+response.message());
                    DialogUtils.showCommonErrorDialog(LoginActivity.this);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<SignupDTO> call, Throwable t) {
                Log.d("where", "실패 : " +t.toString());
            }
        });
    }
}
