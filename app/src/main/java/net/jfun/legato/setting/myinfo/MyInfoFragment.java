package net.jfun.legato.setting.myinfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import net.jfun.legato.MyData;
import net.jfun.legato.R;
import net.jfun.legato.login.LoginActivity;
import net.jfun.legato.setting.withdraw.WithdrawActivity;
import net.jfun.legato.tutorial.TutorialActivity;
import net.jfun.legato.util.Constant;
import net.jfun.legato.util.CustomDialog;
import net.jfun.legato.util.SharedPreferencesUtils;

public class MyInfoFragment extends Fragment implements View.OnClickListener{

    private Context mContext;

    private TextView mTvName;

    private RelativeLayout mLayMyPage, mLayAddress, mLayChangePw;

    public MyInfoFragment newInstance() {
//    public CoffeeFragment newInstance(int page) {
        MyInfoFragment fragment = new MyInfoFragment();

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
        View rootView = inflater.inflate(R.layout.frag_setting, container, false);

        mContext = getActivity();

        mTvName = rootView.findViewById(R.id.setting_txt_name);
        mTvName.setText(MyData.getInstance().getName());
        if (Constant.EN.equals(SharedPreferencesUtils.getStringSharedPreference(mContext, Constant.PREF_LANGUAGE))) {
            rootView.findViewById(R.id.setting_txt_name_nim).setVisibility(View.GONE);
        }

        rootView.findViewById(R.id.frag_setting_btn_logout).setOnClickListener(this);
        rootView.findViewById(R.id.frag_setting_mypage).setOnClickListener(this);
        rootView.findViewById(R.id.frag_setting_withdraw).setOnClickListener(this);


        return rootView;
    }

    @Override
    public void onClick(View v){
        Intent intent;
        switch (v.getId()) {
            case R.id.frag_setting_btn_logout :
                logoutDialog();
                break;
            case R.id.frag_setting_buy_coffee :
                break;
            case R.id.frag_setting_mypage :
                intent = new Intent(mContext, MyInfoActivity.class);
                getActivity().startActivityForResult(intent, Constant.REQ_SET_MYPAGE);
//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//                // Create new fragment and transaction
//                Fragment newFragment = new MyInfoFragment();
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//
//                // Replace whatever is in the fragment_container view with this fragment,
//                // and add the transaction to the back stack
//                transaction.replace(R.id.main_fragment_area, newFragment);
//                transaction.addToBackStack(null);
//
//                // Commit the transaction
//                transaction.commitAllowingStateLoss();
                break;
            case R.id.frag_customer_center :
                break;
            case R.id.frag_setting_device :
                break;
            case R.id.frag_setting_clause_1 :
                break;
            case R.id.frag_setting_clause_2 :
                break;
            case R.id.frag_setting_tutorial :
                intent = new Intent(mContext, TutorialActivity.class);
                startActivity(intent);
                break;
            case R.id.frag_setting_withdraw :
                intent = new Intent(mContext, WithdrawActivity.class);
                getActivity().startActivityForResult(intent, Constant.REQ_WITHDRAW);
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
        }, getString(R.string.btn_cancel), getString(R.string.logout));
        customDialog.show();
    }

}
