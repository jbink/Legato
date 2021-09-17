package net.jfun.legato.setting;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;

import net.jfun.legato.BuildConfig;
import net.jfun.legato.MyData;
import net.jfun.legato.R;
import net.jfun.legato.login.LoginActivity;
import net.jfun.legato.roast.temp.MyMarkerView;
import net.jfun.legato.roast.temp.TempFragment;
import net.jfun.legato.setting.language.LanguageSettingActivity;
import net.jfun.legato.setting.myinfo.MyInfoActivity;
import net.jfun.legato.setting.myinfo.MyInfoFragment;
import net.jfun.legato.setting.withdraw.WithdrawActivity;
import net.jfun.legato.tutorial.TutorialActivity;
import net.jfun.legato.util.Constant;
import net.jfun.legato.util.CustomDialog;
import net.jfun.legato.util.SharedPreferencesUtils;
import net.jfun.legato.util.VerticalSeekbar;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

public class SettingFragment extends Fragment implements View.OnClickListener{

    private Context mContext;

    private TextView mTvVersion, mTvLanguage;
    private CheckBox mChkChangePart;

    public SettingFragment newInstance() {
//    public CoffeeFragment newInstance(int page) {
        SettingFragment fragment = new SettingFragment();

//        Bundle bundle = new Bundle();
//        bundle.putInt("page" , page);
//        fragment.setArguments(bundle);


        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null){
            Log.d("where", "bundle이 있다.");
//            mStartPage = bundle.getInt("page");
        }

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_setting_main, container, false);

        mContext = getActivity();

        mChkChangePart = rootView.findViewById(R.id.setting_main_change_part);
        mTvVersion = rootView.findViewById(R.id.setting_main_version);
        mTvVersion.setText(BuildConfig.VERSION_NAME);
        mTvLanguage = rootView.findViewById(R.id.setting_main_language_txt);
        if (Constant.EN.equals(SharedPreferencesUtils.getStringSharedPreference(mContext, Constant.PREF_LANGUAGE))){
            mTvLanguage.setText(getString(R.string.language_en));
        } else{
            mTvLanguage.setText(getString(R.string.language_kr));
        }

        if(SharedPreferencesUtils.getBooleanSharedPreference(mContext, Constant.PREF_CHANGE_PART) == true){
            mChkChangePart.setChecked(true);
        } else {
            mChkChangePart.setChecked(false);
        }


        rootView.findViewById(R.id.setting_main_personal_info_arrow).setOnClickListener(this);
        rootView.findViewById(R.id.setting_main_personal_info).setOnClickListener(this);
        rootView.findViewById(R.id.setting_main_language_arrow).setOnClickListener(this);
        rootView.findViewById(R.id.setting_main_language_txt).setOnClickListener(this);
        rootView.findViewById(R.id.setting_main_language).setOnClickListener(this);
        rootView.findViewById(R.id.setting_main_change_part).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v){
        Intent intent;
        switch (v.getId()) {
            case R.id.frag_setting_btn_logout :
                logoutDialog();
                break;
            case R.id.setting_main_personal_info_arrow :
            case R.id.setting_main_personal_info :
                intent = new Intent(mContext, SettingActivity.class);
                getActivity().startActivityForResult(intent, Constant.REQ_SET_SETTING);
                break;
            case R.id.setting_main_language_arrow :
            case R.id.setting_main_language_txt :
            case R.id.setting_main_language :
                intent = new Intent(mContext, LanguageSettingActivity.class);
                startActivity(intent);
                break;
            case R.id.frag_setting_tutorial :
                intent = new Intent(mContext, TutorialActivity.class);
                startActivity(intent);
                break;
            case R.id.setting_main_change_part :
                if (mChkChangePart.isChecked() == true) {
                    SharedPreferencesUtils.putBooleanSharedPreference(mContext, Constant.PREF_CHANGE_PART, true);
                } else {
                    SharedPreferencesUtils.putBooleanSharedPreference(mContext, Constant.PREF_CHANGE_PART, false);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQ_WITHDRAW) {
            if(resultCode == getActivity().RESULT_OK) {

            }
        }
    }

    CustomDialog customDialog;
    public void logoutDialog(){
        customDialog = new CustomDialog(getActivity(), getString(R.string.msg_logout), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog.dismiss();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog.dismiss();
                MyData.getInstance().initMyData();
                SharedPreferencesUtils.putStringSharedPreference(mContext, Constant.PREF_ID, null);
                SharedPreferencesUtils.putStringSharedPreference(mContext, Constant.PREF_PW, null);

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        }, "취소", "로그아웃");
        customDialog.show();
    }



}
