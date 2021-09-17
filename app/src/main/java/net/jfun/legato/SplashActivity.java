package net.jfun.legato;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.jfun.legato.api.API_Adapter;
import net.jfun.legato.login.LoginActivity;
import net.jfun.legato.login.SignupDTO;
import net.jfun.legato.roast.temp.DataEntry;
import net.jfun.legato.tutorial.TutorialActivity;
import net.jfun.legato.tutorial.TutorialDetailActivity;
import net.jfun.legato.util.BASIC_FN;
import net.jfun.legato.util.Constant;
import net.jfun.legato.util.DialogUtils;
import net.jfun.legato.util.SharedPreferencesUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static net.jfun.legato.util.Util.getByteToArray;
import static net.jfun.legato.util.Util.hexStringToByteArray;

public class SplashActivity extends BaseActivity {

    private Context mContext;
    private DecimalFormat temperatureFormat;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mContext = SplashActivity.this;
        mHandler.sendEmptyMessageDelayed(0, 1000);


//        String test = Integer.toHexString(Integer.parseInt("1955"));//7a3
//        if(test.length() == 3) {
//            test = "0"+test;
//        }
//        byte[] temp= hexStringToByteArray(test);
//
//        for (int i=0 ; i<temp.length ; i++) {
//
//            Log.d("where" , "testValue  =>   "  +String.format("%02X", temp[i])); //
//        }
//        Log.d("where" , "testValue  =>   "  +String.format("%02X", temp[0])); //07
//        Log.d("where" , "testValue  =>   "  +String.format("%02X", temp[1])); //a3
//        Log.d("where" , "testValue  =>   "  +Integer.toBinaryString(100)); //1100100


//        Log.d("where" , "testValue - =>   "  +Integer.toHexString(1955)); //7a3



//        Log.d("where" , "testValue 0 =>   "  +Integer.toHexString(95)); //5f
        Log.d("where" , "testValue 0 =>   "  +hexStringToByteArray("7a"));
        Log.d("where" , "testValue 0 =>   "  +getByteToArray(hexStringToByteArray("7a")));//95

//        Log.d("where" , "testValue 1 =>   "  +Integer.toHexString(100));//64
//        Log.d("where" , "testValue 1 =>   "  +hexStringToByteArray(Integer.toHexString(100)));
//        Log.d("where" , "testValue 1 =>   "  +getByteToArray(hexStringToByteArray(Integer.toHexString(100)))); //100

//        int testValue = 100;
//        Log.d("where" , "testValue 1 =>   "  +Integer.toHexString(testValue) );
//        Log.d("where" , "testValue 2=>   "  +String.valueOf(testValue));
//        Log.d("where" , "testValue 3 =>   "  +String.format("%02x", testValue));
//        Log.d("where" , "testValue 4 =>   "  +Integer.parseInt(String.valueOf(testValue),16));
//        Log.d("where" , "testValue 5 =>   "  +Integer.parseInt(String.format("%02x", testValue).replaceAll(" ", ""), 16));

//
//        getByteToArray(hexStringToByteArray(String.valueOf(testValue)));

//        String targetTemperature = String.format("%02x ", (byte) 0x07 & 0xff) + String.format("%02x ", (byte) 0xA3 & 0xff);
//        int i_targetTemperature = Integer.parseInt(targetTemperature.replaceAll(" ", ""), 16);
//        Log.d("where" , "int =>   "  +i_targetTemperature );
//        Log.d("where" , "tetst =>   "  +Integer.toHexString(i_targetTemperature) );
//
//        float f_targetTemperature = (float)(Integer.parseInt(targetTemperature.replaceAll(" ", ""), 16)) / 10 ;
//
//
//        String test = Integer.toHexString(i_targetTemperature);
//        int a = 1955;
//        float tempA = (float)a/10;
//        temperatureFormat = new DecimalFormat("###.0");
//        Log.d("where" , "tempA =>   "  +Float.parseFloat(temperatureFormat.format(tempA)) );
//
//
//        if(test.length() < 4) {
//            test = "0"+test;
//        }
//        byte[] temp= hexStringToByteArray(test);
////        temp
//        for (int i=0 ; i<temp.length ; i++) {
//            Log.d("where", "i : " + i + " -> "+String.format("0x%02X ", temp[i]));
//        }
//        temp[0] = (byte) 0xFD;
//        temp[1] = (byte) 0xFD;
//        temp[2] = (byte) 0x00;
//        temp[3] = (byte) 0x00;
//        temp[4] = (byte) 0x07;
//        temp[5] = (byte) 0xE4;
//        temp[6] = (byte) 0x3C;
//        temp[7] = (byte) 0x3C;
//        temp[8] = (byte) 0x44;
//        temp[9] = (byte) 0x44;
//        temp[10] = (byte) 0x03;
//        temp[11] = (byte) 0xE8;
//        temp[12] = (byte) 0x02;//종료
//        temp[13] = (byte) 0x0A;
//        temp[14] = (byte) 0xFE;
//        temp[15] = (byte) 0xFE;

//                        temp[0] = (byte) 0xFD;
//                temp[1] = (byte) 0xFD;
//                temp[2] = (byte) 0x00;
//                temp[3] = (byte) 0x00;
//                temp[4] = (byte) 0x07;
//                temp[5] = (byte) 0xE4;
//                temp[6] = (byte) 0x3C;
//                temp[7] = (byte) 0x3C;
//                temp[8] = (byte) 0x44;
//                temp[9] = (byte) 0x44;
//                temp[10] = (byte) 0x03;
//                temp[11] = (byte) 0xE8;
//                temp[12] = (byte) 0x01;
//                temp[13] = (byte) 0x09;
//                temp[14] = (byte) 0xFE;
//                temp[15] = (byte) 0xFE;

//        temp = BASIC_FN.F01;
//        int test1 = Integer.parseInt(String.format("%02x ", temp[2]&0xFF).replaceAll(" ", ""), 16);
//        for (int i=2 ; i<12 ; i++ ){
//            Log.d("where", "i : " + i + " -> "+test + "   |    " + Integer.parseInt(String.format("%02x ", temp[i+1]&0xFF).replaceAll(" ", ""), 16));
//            test1 = test1 ^ Integer.parseInt(String.format("%02x ", temp[i+1]&0xFF).replaceAll(" ", ""), 16);
//            Log.d("where" , "tetst => " + test + " = " +Integer.toHexString(test1) + " - " + String.format("0x%02x", test1&0xFF) );
//            Log.d("where",  " ------------------------------------------------------------------ ");
//        }
//
//        temp[13] = (byte)test1;
//
//        Log.d("where",  "temp[13] : " + temp[13]);
//        Log.d("where",  "temp[13] : " + String.format("0x%02x", temp[13] & 0xff));
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (!TextUtils.isEmpty(SharedPreferencesUtils.getStringSharedPreference(mContext, Constant.PREF_ID))
                    && !TextUtils.isEmpty(SharedPreferencesUtils.getStringSharedPreference(mContext, Constant.PREF_PW))) {
                //로그인 처리
                api_Login();
            } else {
                //회원가입 페이지로 이동
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };

    public String setParityBit(byte[] temp){
        int parityBit = Integer.parseInt(String.format("%02x", temp[2]), 16);
        for (int i=2 ; i<=12 ; i++ ){
            Log.d("where", "temp : "+Integer.parseInt(String.format("%02x", temp[i+1]), 16) + "  temp toHexString : " + Integer.toHexString(Integer.parseInt(String.format("%02x", temp[i+1]), 16)) + " | 2진수 : " +Integer.toBinaryString(Integer.parseInt(String.format("%02x", temp[i+1]), 16)));
            parityBit = parityBit ^ Integer.parseInt(String.format("%02x", temp[i+1]), 16);
            Log.d("where", "parityBit : "+Integer.parseInt(String.format("%02x", parityBit), 16) + "  parityBit toHexString : " + Integer.toHexString(Integer.parseInt(String.format("%02x", parityBit), 16)));

            Log.d("where", "----------------------------------------");
        }

        Log.d("where", "RESULT  parityBit toHexString : " + Integer.toHexString(parityBit) + "  ->  " + String.format("0x%02x", parityBit&0xFF));

        return Integer.toHexString(parityBit);
    }
    /**
     * 로그인
     */
    private void api_Login(){


        //POST
        retrofit2.Call<SignupDTO> data = API_Adapter.getInstance().login(
                SharedPreferencesUtils.getStringSharedPreference(mContext, Constant.PREF_ID),
                SharedPreferencesUtils.getStringSharedPreference(mContext, Constant.PREF_PW)
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
                        DialogUtils.showCommonErrorDialog(SplashActivity.this);
                    }
                } else {
                    Log.d("where", "error :"+response.message());
                    DialogUtils.showCommonErrorDialog(SplashActivity.this);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<SignupDTO> call, Throwable t) {
                Log.d("where", "실패 : " +t.toString());
            }
        });
    }

    public byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    public static String stringToHex(String s) {
        String result = "";
        for (int i = 0; i < s.length(); i++) {
            result += String.format("%02x ", (int) s.charAt(i));
        }
        return result;

    }

    // 헥사 접두사 "0x" 붙이는 버전
    public static String stringToHex0x(String s) {
        String result = "";

        for (int i = 0; i < s.length(); i++) {
            result += String.format("0x%02X ", (int) s.charAt(i));
        }

        return result;
    }

}
