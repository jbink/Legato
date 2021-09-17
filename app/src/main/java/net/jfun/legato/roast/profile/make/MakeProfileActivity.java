package net.jfun.legato.roast.profile.make;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import net.jfun.legato.BaseActivity;
import net.jfun.legato.MyData;
import net.jfun.legato.R;
import net.jfun.legato.api.API_Adapter;
import net.jfun.legato.api.BaseDTO;
import net.jfun.legato.roast.profile.ProfileDTO;
import net.jfun.legato.util.Constant;

import java.text.DecimalFormat;

import static net.jfun.legato.util.Util.getByteToArray;
import static net.jfun.legato.util.Util.hexStringToByteArray;

public class MakeProfileActivity extends BaseActivity {

    private int MAX_COUNT = 5;
    private String MINUS = "Minus";
    private String PLUS = "Plus";


    private Context mContext;
    private Button[] mBtnMinus, mBtnPlus;
    private TextView[] mTvRoastValue;
    private TextView mTvRoastingTime, mTvTargetTemperature;
    private int[] mIntRoastValue;
    private int[] mIntRoastPercent;
    private double mDoubleTargetTemperature = 198.0;

    private EditText mEdtName, mEdtAmount, mEdtCoffee, mEdtDescription;

    private int mIntProfileUID = 0;
    private String mStrProfileType, mStrProfileQr;

    private DecimalFormat mFormatTemperature;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_profile);

        mContext = MakeProfileActivity.this;

        mIntProfileUID = getIntent().getIntExtra(Constant.EXTRA_PROFILE_UID, 0);
        mStrProfileType = getIntent().getStringExtra(Constant.EXTRA_PROFILE_TYPE);
        mStrProfileQr = getIntent().getStringExtra(Constant.EXTRA_PROFILE_QR);


        mFormatTemperature = new DecimalFormat("###.0");

//        for (int i=0 ; i<MAX_COUNT ; i++) {
//            mIntRoastValue[i] = 10;
//            mTvRoastValue[i] = findViewById(getResources().getIdentifier("id", "make_profile_"+i+"_txt_value", this.getPackageName()));
//            mBtnMinus[i] = findViewById(getResources().getIdentifier("id", "make_profile_"+i+"_btn_minus", this.getPackageName()));
//            mBtnPlus[i] = findViewById(getResources().getIdentifier("id", "make_profile_"+i+"_btn_plus", this.getPackageName()));
//        }

        mIntRoastValue = new int[]{0, 0, 0, 0, 0};
        mIntRoastPercent = new int[]{60,60,60,60,60};
        mBtnMinus = new Button[]{
                findViewById(R.id.make_profile_0_btn_minus),
                findViewById(R.id.make_profile_1_btn_minus),
                findViewById(R.id.make_profile_2_btn_minus),
                findViewById(R.id.make_profile_3_btn_minus),
                findViewById(R.id.make_profile_4_btn_minus)
        };
        mBtnPlus = new Button[]{
                findViewById(R.id.make_profile_0_btn_plus),
                findViewById(R.id.make_profile_1_btn_plus),
                findViewById(R.id.make_profile_2_btn_plus),
                findViewById(R.id.make_profile_3_btn_plus),
                findViewById(R.id.make_profile_4_btn_plus)
        };
        mTvRoastValue = new TextView[]{
                findViewById(R.id.make_profile_0_txt_value),
                findViewById(R.id.make_profile_1_txt_value),
                findViewById(R.id.make_profile_2_txt_value),
                findViewById(R.id.make_profile_3_txt_value),
                findViewById(R.id.make_profile_4_txt_value)
        };
        mTvTargetTemperature = findViewById(R.id.make_profile_target_temperature);
        mTvRoastingTime = findViewById(R.id.make_profile_roasting_time);

        mEdtName = findViewById(R.id.make_profile_edt_name);
        mEdtAmount = findViewById(R.id.make_profile_edt_amount);
        mEdtCoffee = findViewById(R.id.make_profile_edt_coffee);
        mEdtDescription = findViewById(R.id.make_profile_edt_description);

        setTotalRoastingTime();

        if (mIntProfileUID != 0 && Constant.PROFILE_TYPE_BASIC.equals(mStrProfileType)) {//
            api_GetProfile(mIntProfileUID, mStrProfileType);
            mStrProfileType = Constant.PROFILE_TYPE_PRO; //Basic Data를 불러와서 저장할 때는 pro로 저장을 해야하므로 변경
            mIntProfileUID = 0;
        } else if (mIntProfileUID != 0 && Constant.PROFILE_TYPE_QR.equals(mStrProfileType)) {
            api_GetProfile(mIntProfileUID, mStrProfileType);
            mStrProfileType = Constant.PROFILE_TYPE_PRO;
            mIntProfileUID = 0;
        } else if (mIntProfileUID != 0 && Constant.PROFILE_TYPE_PRO.equals(mStrProfileType)) {
            api_GetProfile(mIntProfileUID, mStrProfileType);
        } else if (!TextUtils.isEmpty(mStrProfileQr) && Constant.PROFILE_TYPE_QR.equals(mStrProfileType)) {
            api_GetProfileQrInfo(mStrProfileQr);
        } else if (!TextUtils.isEmpty(mStrProfileQr) && Constant.PROFILE_TYPE_QR.equals(mStrProfileType)) {
            api_GetProfileQrInfo(mStrProfileQr);
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.make_profile_btn_back :
                finish();
                break;
            case R.id.make_profile_btn_save :
                api_SetProfilePro(setParameter());
//                setRoastingSectionTemperature();
                break;
            case R.id.make_profile_target_btn_minus :
                setOutputTargetText(MINUS);
                break;
            case R.id.make_profile_target_btn_plus :
                setOutputTargetText(PLUS);
                break;
            case R.id.make_profile_0_btn_minus :
                setOutputText(0, MINUS);
                break;
            case R.id.make_profile_1_btn_minus :
                setOutputText(1, MINUS);
                break;
            case R.id.make_profile_2_btn_minus :
                setOutputText(2, MINUS);
                break;
            case R.id.make_profile_3_btn_minus :
                setOutputText(3, MINUS);
                break;
            case R.id.make_profile_4_btn_minus :
                setOutputText(4, MINUS);
                break;

            case R.id.make_profile_0_btn_plus :
                setOutputText(0, PLUS);
                break;
            case R.id.make_profile_1_btn_plus :
                setOutputText(1, PLUS);
                break;
            case R.id.make_profile_2_btn_plus :
                setOutputText(2, PLUS);
                break;
            case R.id.make_profile_3_btn_plus :
                setOutputText(3, PLUS);
                break;
            case R.id.make_profile_4_btn_plus :
                setOutputText(4, PLUS);
                break;
        }
    }
    private void setOutputTargetText(String type){

        if (type.equals(MINUS)){
            mDoubleTargetTemperature = mDoubleTargetTemperature - 0.5;
        } else if (type.equals(PLUS)){
            mDoubleTargetTemperature = mDoubleTargetTemperature + 0.5;
        }

        double curValue = mDoubleTargetTemperature;
        if (curValue <= 198){
            mTvTargetTemperature.setText(String.valueOf(198.0));
            mDoubleTargetTemperature = mDoubleTargetTemperature + 0.5;
        } else if (curValue >209) {
            mTvTargetTemperature.setText(String.valueOf(209.0));
            mDoubleTargetTemperature = mDoubleTargetTemperature - 0.5;
        } else {
            mTvTargetTemperature.setText(String.valueOf(curValue));
        }

        setTotalRoastingTime();

        //토탈 값 구하는 공식임
//       int totalTime = 5*(100 - (mIntRoastValue[index]*5))/10;
//
//        Log.d("where", "A : "+ (mIntRoastValue[index]*5));
//        Log.d("where", "B : "+ totalTime);
//        Log.d("where", "T : "+ (totalTime + 15));
    }

    private void setOutputText(int index, String type){
//        if (mIntRoastValue[index] == 1){
//            Toast.makeText(mContext, "최소값이 설정 되어야 합니다.", Toast.LENGTH_SHORT).show();
//            return;
//        } else if (mIntRoastValue[index] == 20){
//            Toast.makeText(mContext, "최대값이 설정 되어야 합니다.", Toast.LENGTH_SHORT).show();
//            return;
//        }

        Log.d("where", "mIntRoastPercent 0 : "+ mIntRoastPercent[index]);
        if (type.equals(MINUS)){
            mIntRoastPercent[index] = mIntRoastPercent[index] - 10;
        } else if (type.equals(PLUS)){
            mIntRoastPercent[index] = mIntRoastPercent[index] + 10;
        }

        Log.d("where", "mIntRoastPercent 1 : "+ mIntRoastPercent[index]);
        Log.d("where", "Time 1 : "+ getRoastingSectionTime(mIntRoastPercent[index]));
        int curValue = mIntRoastPercent[index];
        if (curValue < 50){
            mTvRoastValue[index].setText(String.valueOf(50) + "%");
            mIntRoastPercent[index] = mIntRoastPercent[index] + 10;
        } else if (curValue >100) {
            mTvRoastValue[index].setText(String.valueOf(100) + "%");
            mIntRoastPercent[index] = mIntRoastPercent[index] - 10;
        } else {
            mTvRoastValue[index].setText(String.valueOf(curValue) + "%");
        }

        setTotalRoastingTime();

        //토탈 값 구하는 공식임
//       int totalTime = 5*(100 - (mIntRoastValue[index]*5))/10;
//
//        Log.d("where", "A : "+ (mIntRoastValue[index]*5));
//        Log.d("where", "B : "+ totalTime);
//        Log.d("where", "T : "+ (totalTime + 15));
    }

    private void setTotalRoastingTime() {
        int totalTime = 0;
        for (int i=0 ; i<mIntRoastPercent.length-1 ; i++ ) {
            if (i==0){
                totalTime = totalTime + getRoastingSectionTime(mIntRoastPercent[i]) * 6;
            }else {
                totalTime = totalTime + getRoastingSectionTime(mIntRoastPercent[i]) * 4;
            }
        }

        totalTime = totalTime + 180;
        Log.d("where", "totalTime : "+ totalTime);
        mTvRoastingTime.setText(""+totalTime/60+"분 "+totalTime%60+"초");
    }

    private void setRoastingSectionTemperature(){
        for (int i=0 ; i<mIntRoastValue.length ; i++ ) {
            mIntRoastValue[i] = (5*(100 - (mIntRoastPercent[i]*5))/10)+15;
            Log.d("where", "mIntRoastValue i : "+i+ "  value :  "+ mIntRoastValue[i]);
        }

//        api_SetProfilePro();
    }

    private String[] setParameter(){
        String target = String.valueOf(mDoubleTargetTemperature).replace(".", "");
        String test = Integer.toHexString(Integer.parseInt(target));
        if(test.length() < 4) {
            test = "0"+test;
        }
        byte[] temp= hexStringToByteArray(test);

        String strProfileType = null, styProfileNum = null;
        if (Constant.PROFILE_TYPE_PRO.equals(mStrProfileType)){//PRO 프로필 등록할 때 고정값이 바이트
            strProfileType = "01";
            styProfileNum = "00";
        } else if(Constant.PROFILE_TYPE_QR.equals(mStrProfileType)){
            strProfileType = "02";
            styProfileNum = "13";
        } else {
            return null;
        }
        for (int i=0 ; i<temp.length ; i++) {
            Log.d("where" , "testValue  =>   "  +String.format("%02X", temp[i])); //
        }
        String[] strByteData = new String[]{
                "FD",
                "FD",
                strProfileType,//2: pro 01 , qr 02 - 프로그램 구분
                styProfileNum,//3: pro 00 , qr 13 - 프로그램 번호
                String.format("%02X", temp[0]),//4: 설정온도 1
                String.format("%02X", temp[1]),//5: 설정온도 1
                String.valueOf(Integer.toHexString(getRoastingSectionTime(mIntRoastPercent[0]))),
                String.valueOf(Integer.toHexString(getRoastingSectionTime(mIntRoastPercent[1]))),//7: 2구간 제어시간
                String.valueOf(Integer.toHexString(getRoastingSectionTime(mIntRoastPercent[2]))),//8: 3구간 제어시간
                String.valueOf(Integer.toHexString(getRoastingSectionTime(mIntRoastPercent[3]))),//9: 4구간 제어시간
//                String.valueOf(Integer.toHexString(mIntRoastPercent[0]*5)),//6: 1구간 제어시간
//                String.valueOf(Integer.toHexString(mIntRoastPercent[1]*5)),//7: 2구간 제어시간
//                String.valueOf(Integer.toHexString(mIntRoastPercent[2]*5)),//8: 3구간 제어시간
//                String.valueOf(Integer.toHexString(mIntRoastPercent[3]*5)),//9: 4구간 제어시간
                "03",
                "E8",
                "01",//12: 시작 01, 종료 02
                "00",//13: XOR연산값 (초기값 00)
                "FE",
                "FE"
        };

        byte[] tempByteData = new byte[strByteData.length];
        for (int i=0 ; i<strByteData.length ; i++) {
            if (strByteData[i].length() == 1 || strByteData[i].length() == 3 ){
                strByteData[i] = "0"+strByteData[i];
            }
            tempByteData[i] = getByteToArray(hexStringToByteArray(strByteData[i]));
        }

        strByteData[13] = setParityBit(tempByteData);
        if (strByteData[13].length() == 1 || strByteData[13].length() == 3 ){
            strByteData[13] = "0"+strByteData[13];
        }
        for (int i=0 ; i<strByteData.length ; i++) {
            Log.d("where", "i : " + i + "  strByteData : " + strByteData[i]);
        }

        return strByteData;

    }

    public String setParityBit(byte[] temp){
        int parityBit = Integer.parseInt(String.format("%02x ", temp[2]&0xff).replaceAll(" ", ""), 16);
        for (int i=2 ; i<=12 ; i++ ){
            parityBit = parityBit ^ Integer.parseInt(String.format("%02x ", temp[i+1]&0xff).replaceAll(" ", ""), 16);
        }

        Log.d("where", "  parityBit : " + parityBit);
        Log.d("where", "  parityBit : " + Integer.toHexString(parityBit));
        return Integer.toHexString(parityBit);
    }


    /**
     * Profile 저장
     */
    private void api_SetProfilePro(String[] param){

        String name = mEdtName.getText().toString();
        String amount = mEdtAmount.getText().toString();
        String coffee = mEdtCoffee.getText().toString();
        String description = mEdtDescription.getText().toString();

        //POST
        retrofit2.Call<BaseDTO> data = API_Adapter.getInstance().setProfilePro(
                String.valueOf(mIntProfileUID),
                String.valueOf(MyData.getInstance().getUid()),
                name,
                mStrProfileType,
                mTvTargetTemperature.getText().toString(),
                param[0],
                param[1],
                param[2],
                param[3],
                param[4],
                param[5],
                param[6],
                param[7],
                param[8],
                param[9],
                param[10],
                param[11],
                param[12],
                param[13],
                param[14],
                param[15],
                amount,
                coffee,
                description,
                mTvRoastingTime.getText().toString()
        );

        data.enqueue(new retrofit2.Callback<BaseDTO>() {
            @Override
            public void onResponse(retrofit2.Call<BaseDTO> call, retrofit2.Response<BaseDTO> response) {
                // 성공여부
                if(response.isSuccessful()){
                    BaseDTO member = response.body();
                    if(Constant.API_RESPONSE_SUCCESS == member.getResult()) {
                        Toast.makeText(mContext, R.string.save_success, Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
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

    /**
     * Profile 상세
     */
    private void api_GetProfile(int uid, String type){
        //POST
        retrofit2.Call<ProfileDTO> data = API_Adapter.getInstance().getProfile(
                String.valueOf(uid), String.valueOf(MyData.getInstance().getUid()), type
        );

        data.enqueue(new retrofit2.Callback<ProfileDTO>() {
            @Override
            public void onResponse(retrofit2.Call<ProfileDTO> call, retrofit2.Response<ProfileDTO> response) {
                // 성공여부
                if(response.isSuccessful()){
                    ProfileDTO data = response.body();
                    if(Constant.API_RESPONSE_SUCCESS == data.getResult()) {

                        mEdtName.setText(String.valueOf(data.getContent().getProfileName()));
                        mEdtAmount.setText(String.valueOf(data.getContent().getInputQuantity()));
                        mEdtCoffee.setText(String.valueOf(data.getContent().getBeanName()));
                        mEdtDescription.setText(String.valueOf(data.getContent().getNotes()));

//                        int targerTemperatue = Integer.parseInt((data.getContent().getGxyz_4() + data.getContent().getGxyz_5()), 16);
//                        String strTemp = String.valueOf(targerTemperatue);
//                        if(strTemp.length()>=4){
//                            strTemp = strTemp.substring(0,3);
//                        }
                        mTvTargetTemperature.setText(data.getContent().getTargetTemperature());
                        try {
                            mDoubleTargetTemperature = Double.parseDouble(data.getContent().getTargetTemperature());
                        }catch (Exception e){
                            mDoubleTargetTemperature = 198.0;
                        }


                        int curValue = Integer.parseInt(data.getContent().getAyz_6(), 16);
                        Log.d("where", "curValue : "+curValue); // 25초
                        Log.d("where", "getRoastingSectionPercent : "+getRoastingSectionPercent(curValue)); // 80%
                        mTvRoastValue[0].setText(String.valueOf(getRoastingSectionPercent(curValue)) + "%");
                        mIntRoastPercent[0] = getRoastingSectionPercent(curValue);

                        curValue = Integer.parseInt(data.getContent().getByz_7(), 16);
                        mTvRoastValue[1].setText(String.valueOf(getRoastingSectionPercent(curValue)) + "%");
                        mIntRoastPercent[1] = getRoastingSectionPercent(curValue);

                        curValue = Integer.parseInt(data.getContent().getCyz_8(), 16);
                        mTvRoastValue[2].setText(String.valueOf(getRoastingSectionPercent(curValue)) + "%");
                        mIntRoastPercent[2] = getRoastingSectionPercent(curValue);

                        curValue = Integer.parseInt(data.getContent().getDyz_9(), 16);
                        mTvRoastValue[3].setText(String.valueOf(getRoastingSectionPercent(curValue)) + "%");
                        mIntRoastPercent[3] = getRoastingSectionPercent(curValue);

                        Log.d("where", "1 : "+Integer.parseInt("4B", 16)); // 75
                        Log.d("where", "1 - : "+Integer.toHexString(Integer.parseInt("4B", 16))); //4B
                        Log.d("where", "2 : "+Integer.parseInt("C8", 16));
                        Log.d("where", "2 - : "+Integer.toHexString(Integer.parseInt("C8", 16)));
                        Log.d("where", "2 : "+Integer.parseInt("C8", 16));
                        Log.d("where", "3 : "+data.getContent().getAyz_6());
                        Log.d("where", "3 - : "+hexStringToByteArray(data.getContent().getAyz_6()));
                        Log.d("where", "3 = : "+Integer.toHexString(Integer.parseInt(data.getContent().getAyz_6(), 16)));

                        setTotalRoastingTime();

//                        double dTemperature = targerTemperatue / 10;
//                        mTvTargetTemperature.setText(mFormatTemperature.format(dTemperature));

//                        mByteProfileData[0] = getByteToArray(hexStringToByteArray(data.getContent().getStartbit_0()));
//                        mByteProfileData[1] = getByteToArray(hexStringToByteArray(data.getContent().getStartbit_1()));
//                        mByteProfileData[2] = getByteToArray(hexStringToByteArray(data.getContent().getPz_2()));
//                        mByteProfileData[3] = getByteToArray(hexStringToByteArray(data.getContent().getNyz_3()));
//                        mByteProfileData[4] = getByteToArray(hexStringToByteArray(data.getContent().getGxyz_4()));
//                        mByteProfileData[5] = getByteToArray(hexStringToByteArray(data.getContent().getGxyz_5()));
//                        mByteProfileData[6] = getByteToArray(hexStringToByteArray(data.getContent().getAyz_6()));
//                        mByteProfileData[7] = getByteToArray(hexStringToByteArray(data.getContent().getByz_7()));
//                        mByteProfileData[8] = getByteToArray(hexStringToByteArray(data.getContent().getCyz_8()));
//                        mByteProfileData[9] = getByteToArray(hexStringToByteArray(data.getContent().getDyz_9()));
//                        mByteProfileData[10] = getByteToArray(hexStringToByteArray(data.getContent().getUwxyz_10()));
//                        mByteProfileData[11] = getByteToArray(hexStringToByteArray(data.getContent().getRz_11()));
//                        mByteProfileData[12] = getByteToArray(hexStringToByteArray(data.getContent().getRz_12()));
//                        mByteProfileData[13] = getByteToArray(hexStringToByteArray(data.getContent().getXoR_13()));
//                        mByteProfileData[14] = getByteToArray(hexStringToByteArray(data.getContent().getStopbit_14()));
//                        mByteProfileData[15] = getByteToArray(hexStringToByteArray(data.getContent().getStopbit_15()));

                    } else if(Constant.API_RESPONSE_ERROR_0 == data.getResult()){
                        Toast.makeText(mContext, data.getMessage(), Toast.LENGTH_SHORT).show();
                    }else if( Constant.API_RESPONSE_ERROR_1 == data.getResult()){
                        Toast.makeText(mContext, data.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                    }
                } else {
                    Log.d("where", "error :"+response.message());
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ProfileDTO> call, Throwable t) {
                Log.d("where", "실패 : " +t.toString());
            }
        });
    }

    private int getRoastingSectionPercent(int time){
        int percent = 130 - (2*time);
        Log.d("where", "percent : " + percent +" | time : "+ time);
        return percent;
    }
    private int getRoastingSectionTime(int percent){
        int time = (130 - percent)/2;
        Log.d("where", "TIME : " + time + " | PERCENT : " + percent);
        return time;
    }
//
//    private int getRoastingSectionPercent(int time){
//        time = time-15;
//        time = 100 - (2*time);
//        return time;
//    }

    /**
     * Profile 상세
     */
    private void api_GetProfileQrInfo(String qrCode){
        //POST
        retrofit2.Call<ProfileDTO> data = API_Adapter.getInstance().getProfileQrInfo(
                qrCode, String.valueOf(MyData.getInstance().getUid())
        );

        data.enqueue(new retrofit2.Callback<ProfileDTO>() {
            @Override
            public void onResponse(retrofit2.Call<ProfileDTO> call, retrofit2.Response<ProfileDTO> response) {
                // 성공여부
                if(response.isSuccessful()){
                    ProfileDTO data = response.body();
                    if(Constant.API_RESPONSE_SUCCESS == data.getResult()) {

                        mEdtName.setText(String.valueOf(data.getContent().getProfileName()));
                        mEdtAmount.setText(String.valueOf(data.getContent().getInputQuantity()));
                        mEdtCoffee.setText(String.valueOf(data.getContent().getBeanName()));
                        mEdtDescription.setText(String.valueOf(data.getContent().getNotes()));

                        int targerTemperatue = Integer.parseInt((data.getContent().getGxyz_4() + data.getContent().getGxyz_5()), 16);
                        String strTemp = String.valueOf(targerTemperatue);
                        if(strTemp.length()>=4){
                            strTemp = strTemp.substring(0,3);
                        }
                        mTvTargetTemperature.setText(strTemp);


                        int curValue = Integer.parseInt(data.getContent().getAyz_6(), 16);
                        mTvRoastValue[0].setText(String.valueOf(getRoastingSectionPercent(curValue)) + "%");
                        mIntRoastPercent[0] =  getRoastingSectionPercent(curValue);

                        curValue = Integer.parseInt(data.getContent().getByz_7(), 16);
                        mTvRoastValue[1].setText(String.valueOf(getRoastingSectionPercent(curValue)) + "%");
                        mIntRoastPercent[1] =  getRoastingSectionPercent(curValue);

                        curValue = Integer.parseInt(data.getContent().getCyz_8(), 16);
                        mTvRoastValue[2].setText(String.valueOf(getRoastingSectionPercent(curValue)) + "%");
                        mIntRoastPercent[2] =  getRoastingSectionPercent(curValue);

                        curValue = Integer.parseInt(data.getContent().getDyz_9(), 16);
                        mTvRoastValue[3].setText(String.valueOf(getRoastingSectionPercent(curValue)) + "%");
                        mIntRoastPercent[3] =  getRoastingSectionPercent(curValue);


                        Log.d("where", "1 : "+Integer.parseInt("4B", 16)); // 75
                        Log.d("where", "1 - : "+Integer.toHexString(Integer.parseInt("4B", 16))); //4B
                        Log.d("where", "2 : "+Integer.parseInt("C8", 16));
                        Log.d("where", "2 - : "+Integer.toHexString(Integer.parseInt("C8", 16)));
                        Log.d("where", "2 : "+Integer.parseInt("C8", 16));
                        Log.d("where", "3 : "+data.getContent().getAyz_6());
                        Log.d("where", "3 - : "+hexStringToByteArray(data.getContent().getAyz_6()));
                        Log.d("where", "3 = : "+Integer.toHexString(Integer.parseInt(data.getContent().getAyz_6(), 16)));

                        setTotalRoastingTime();

                    } else if(Constant.API_RESPONSE_ERROR_0 == data.getResult()){
                        Toast.makeText(mContext, data.getMessage(), Toast.LENGTH_SHORT).show();
                    }else if( Constant.API_RESPONSE_ERROR_1 == data.getResult()){
                        Toast.makeText(mContext, data.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                    }
                } else {
                    Log.d("where", "error :"+response.message());
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ProfileDTO> call, Throwable t) {
                Log.d("where", "실패 : " +t.toString());
            }
        });
    }
}
