package net.jfun.legato.roast.profile;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import net.jfun.legato.MyData;
import net.jfun.legato.R;
import net.jfun.legato.api.API_Adapter;
import net.jfun.legato.api.BaseDTO;
import net.jfun.legato.history.mode.ModeListDTO;
import net.jfun.legato.qr.QrActivity;
import net.jfun.legato.roast.profile.adapter.ProfileListAdapter_Basic;
import net.jfun.legato.roast.profile.adapter.ProfileListAdapter_Pro;
import net.jfun.legato.roast.profile.adapter.ProfileListAdapter_QR;
import net.jfun.legato.roast.profile.adapter.ProfileListAdapter_Select;
import net.jfun.legato.roast.profile.make.MakeProfileActivity;
import net.jfun.legato.tutorial.TutorialActivity;
import net.jfun.legato.tutorial.TutorialDetailActivity;
import net.jfun.legato.util.Constant;
import net.jfun.legato.util.CustomDialog;
import net.jfun.legato.util.DialogUtils;

public class ProfileListFragment extends Fragment implements View.OnClickListener {

    private final int TAB_COUNT = 4;

    private Context mContext;
    private ListView[] mListView;
    private TextView[] mTvTab;
    private ImageButton mIbTutorial;

    private BluetoothDevice mBleDevice;

    private ProfileListAdapter_Basic mAdapter_Basic;
    private ProfileListAdapter_Pro mAdapter_Pro;
    private ProfileListAdapter_QR mAdapter_QR;
    private ProfileListAdapter_Select mAdapter_Select;


    private CallSubProfileFragmentListener callSubProfileFragmentListener;
    public interface CallSubProfileFragmentListener {
        public void callSubProfileFragment(int uid, String name, String profileType);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callSubProfileFragmentListener = (CallSubProfileFragmentListener)getActivity();
    }


    public ProfileListFragment newInstance(BluetoothDevice device) {
//    public CoffeeFragment newInstance(int page) {
        ProfileListFragment fragment = new ProfileListFragment(device);

//        Bundle bundle = new Bundle();
//        bundle.putInt("page" , page);
//        fragment.setArguments(bundle);

        return fragment;
    }

    public ProfileListFragment(BluetoothDevice device) {

        mBleDevice = device;
        if (mBleDevice != null) {
//            connectToSelectedDevice(mBleDevice);
        }
    }

    public ProfileListFragment (){}



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null){
            Log.d("where", "bundle이 있다.");
//            mStartPage = bundle.getInt("page");
        }

        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_roast_profile_list, container, false);


        mTvTab = new TextView[]{
                rootView.findViewById(R.id.roast_profile_tab_0),
                rootView.findViewById(R.id.roast_profile_tab_1),
                rootView.findViewById(R.id.roast_profile_tab_2),
                rootView.findViewById(R.id.roast_profile_tab_3)
        };
        mListView = new ListView[]{
                rootView.findViewById(R.id.roast_profile_list_0),
                rootView.findViewById(R.id.roast_profile_list_1),
                rootView.findViewById(R.id.roast_profile_list_2),
                rootView.findViewById(R.id.roast_profile_list_3)
        };

//        mAdapter = new ProfileListAdapter(mContext);

        for (int i=0 ; i<TAB_COUNT ; i++) {
            mTvTab[i].setOnClickListener(this);
        }

        mAdapter_Basic = new ProfileListAdapter_Basic(mContext, ProfileListFragment.this);
        mAdapter_Pro = new ProfileListAdapter_Pro(mContext, ProfileListFragment.this);
        mAdapter_QR = new ProfileListAdapter_QR(mContext, ProfileListFragment.this);
        mAdapter_Select = new ProfileListAdapter_Select(mContext, ProfileListFragment.this);

        mIbTutorial = (ImageButton)rootView.findViewById(R.id.btn_tutorial);
        mIbTutorial.setOnClickListener(this);
        for (int i=0 ; i<TAB_COUNT ; i++) {
            mTvTab[i].setOnClickListener(this);
        }
        mListView[0].setAdapter(mAdapter_Basic);
        mListView[1].setAdapter(mAdapter_Pro);
        mListView[2].setAdapter(mAdapter_QR);
        mListView[3].setAdapter(mAdapter_Select);

        View header_pro = getLayoutInflater().inflate(R.layout.row_profile_header, null, false);
        header_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(getActivity(), MakeProfileActivity.class);
            intent.putExtra(Constant.EXTRA_PROFILE_TYPE, Constant.PROFILE_TYPE_PRO);
            startActivityForResult(intent, Constant.REQ_MAKE_PROFILE_PRO);
            }
        });
        mListView[1].addHeaderView(header_pro);

        View header_qr = getLayoutInflater().inflate(R.layout.row_profile_header, null, false);
        header_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), QrActivity.class);
                intent.putExtra(Constant.EXTRA_PROFILE_TYPE, Constant.PROFILE_TYPE_QR);
                startActivityForResult(intent, Constant.REQ_MAKE_PROFILE_QR);
            }
        });
        mListView[2].addHeaderView(header_qr);


        mListView[0].setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ModeListDTO.ContentBean.BasicBean data = (ModeListDTO.ContentBean.BasicBean)adapterView.getAdapter().getItem(i);
                callSubProfileFragmentListener.callSubProfileFragment(data.getUid(), data.getProfileName(), Constant.PROFILE_TYPE_BASIC);
            }
        });
        mListView[1].setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ModeListDTO.ContentBean.ProBean data = (ModeListDTO.ContentBean.ProBean)adapterView.getAdapter().getItem(i);
                callSubProfileFragmentListener.callSubProfileFragment(data.getUid(), data.getProfileName(), Constant.PROFILE_TYPE_PRO);
            }
        });
        mListView[2].setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ModeListDTO.ContentBean.QrBean data = (ModeListDTO.ContentBean.QrBean)adapterView.getAdapter().getItem(i);
                callSubProfileFragmentListener.callSubProfileFragment(data.getUid(), data.getProfileName(), Constant.PROFILE_TYPE_QR);
            }
        });
        mListView[3].setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ModeListDTO.ContentBean.SelectBean data = (ModeListDTO.ContentBean.SelectBean)adapterView.getAdapter().getItem(i);
                callSubProfileFragmentListener.callSubProfileFragment(data.getUid(), data.getProfileName(), Constant.PROFILE_TYPE_PRO);
            }
        });

        api_GetProfileList();

        return rootView;
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btn_tutorial :
                Intent intent = new Intent(mContext, TutorialDetailActivity.class);
                intent.putExtra(Constant.EXTRA_TUTORIAL, Constant.EXTRA_PROFILE_TUTORIAL_BASIC);
                startActivity(intent);
                break;
            case R.id.roast_profile_tab_0 :
                setTabButtonAndListView(0);
                break;
            case R.id.roast_profile_tab_1 :
                setTabButtonAndListView(1);
                break;
            case R.id.roast_profile_tab_2 :
                setTabButtonAndListView(2);
                break;
            case R.id.roast_profile_tab_3 :
                setTabButtonAndListView(3);
                break;
        }
    }

    private void setTabButtonAndListView(int position) {
        for (int i=0 ; i<TAB_COUNT ; i++) {
            mTvTab[i].setTextColor(ContextCompat.getColor(mContext, R.color.COLOR_3B3B3B));
            mTvTab[i].setBackground(ContextCompat.getDrawable(mContext, R.drawable.btn_tab_back));
            mListView[i].setVisibility(View.GONE);
        }
        mTvTab[position].setTextColor(ContextCompat.getColor(mContext, R.color.COLOR_6B6B6B));
        mTvTab[position].setBackground(ContextCompat.getDrawable(mContext, R.drawable.btn_tab_back_prs));
        mListView[position].setVisibility(View.VISIBLE);
        if (position == 0){
            mIbTutorial.setVisibility(View.VISIBLE);
        } else {
            mIbTutorial.setVisibility(View.GONE);
        }
    }

    public void setSelectList(int uid, boolean isSelect, String type) {
        api_UpdateProfileIsWish(uid, isSelect, type);
    }
    public void deleteList(int uid, String type) {
        confirmDialog(uid, type);
    }
    public void modifyList(int uid, String type) {
        Intent intent = new Intent(getActivity(), MakeProfileActivity.class);
        intent.putExtra(Constant.EXTRA_PROFILE_TYPE, type);
        intent.putExtra(Constant.EXTRA_PROFILE_UID, uid);
        startActivityForResult(intent, Constant.REQ_MAKE_PROFILE_PRO);
    }
    public void copyList(int uid, String type) {
        Intent intent = new Intent(getActivity(), MakeProfileActivity.class);
        intent.putExtra(Constant.EXTRA_PROFILE_TYPE, type);
        intent.putExtra(Constant.EXTRA_PROFILE_UID, uid);
        startActivityForResult(intent, Constant.REQ_MAKE_PROFILE_PRO);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQ_MAKE_PROFILE_PRO) {
            if (resultCode == getActivity().RESULT_OK){
                Log.d("where", "결과호출 됨 : success" );
                api_GetProfileList();
            }
        } else if (requestCode == Constant.REQ_MAKE_PROFILE_QR) {
            if (resultCode == getActivity().RESULT_OK){
                Toast.makeText(mContext, getString(R.string.qr_profile_success), Toast.LENGTH_SHORT).show();
                api_GetProfileList();
//                Intent intent = new Intent(getActivity(), MakeProfileActivity.class);
//                intent.putExtra(Constant.EXTRA_PROFILE_TYPE, Constant.PROFILE_TYPE_QR);
//                intent.putExtra(Constant.EXTRA_PROFILE_QR, data.getStringExtra("qr_value"));
//                startActivityForResult(intent, Constant.REQ_MAKE_PROFILE_PRO);
            } else if (resultCode == getActivity().RESULT_FIRST_USER){
                Toast.makeText(mContext, getString(R.string.qr_profile_duplicate), Toast.LENGTH_SHORT).show();
            } else if (resultCode == 3){
                Toast.makeText(mContext, getString(R.string.qr_profile_empty), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void test1(String s){
        Log.d("where", "Profile : " + s);
    }



    /**
     * Profile 찜하기
     */
    private void api_GetProfileList(){

        //POST
        retrofit2.Call<ModeListDTO> data = API_Adapter.getInstance().getProfileList(
                String.valueOf(MyData.getInstance().getUid())
        );

        data.enqueue(new retrofit2.Callback<ModeListDTO>() {
            @Override
            public void onResponse(retrofit2.Call<ModeListDTO> call, retrofit2.Response<ModeListDTO> response) {
                // 성공여부
                if(response.isSuccessful()){
                    ModeListDTO member = response.body();
                    if(Constant.API_RESPONSE_SUCCESS == member.getResult()) {
                        //api DTO에서 받은 값을 토대로 GraphDTO로 옮기는 작업이 필요 함
                        mAdapter_Basic.addAllData(member.getContent().getBasic());
                        mAdapter_Pro.addAllData(member.getContent().getPro());
                        mAdapter_QR.addAllData(member.getContent().getQr());
                        mAdapter_Select.addAllData(member.getContent().getSelect());

//                        mAdapter.addAllData(member.getContent());
//
//
//                        for (int i=0 ; i<member.getContent().size() ; i++) {
//                            if (Constant.PROFILE_TYPE_BASIC.equals(member.getContent().get(i).getProfileType())){
//                                mDataMode[0].add(contentBean);
//                            } else if (Constant.PROFILE_TYPE_PRO.equals(member.getContent().get(i).getProfileType())){
//                                mDataMode[1].add(member.getContent().get(i));
//                            } else if (Constant.PROFILE_TYPE_QR.equals(member.getContent().get(i).getProfileType())){
//                                mDataMode[2].add(member.getContent().get(i));
//                            } else if (Constant.PROFILE_TYPE_SELECT.equals(member.getContent().get(i).getProfileType())){
//                                mDataMode[3].add(member.getContent().get(i));
//                            }
//                        }
//                        mAdapter.addAllData(mDataMode[0]);
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
            public void onFailure(retrofit2.Call<ModeListDTO> call, Throwable t) {
                Log.d("where", "실패 : " +t.toString());
            }
        });
    }
    /**
     * Profile 삭제
     */
    private void api_DeleteProfile(int uid, String type){

        //POST
        retrofit2.Call<BaseDTO> data = API_Adapter.getInstance().deleteProfile(
                String.valueOf(uid), String.valueOf(MyData.getInstance().getUid()), type
        );

        data.enqueue(new retrofit2.Callback<BaseDTO>() {
            @Override
            public void onResponse(retrofit2.Call<BaseDTO> call, retrofit2.Response<BaseDTO> response) {
                // 성공여부
                if(response.isSuccessful()){
                    BaseDTO member = response.body();
                    if(Constant.API_RESPONSE_SUCCESS == member.getResult()) {
                        api_GetProfileList();
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
     * Profile 목록
     */
    private void api_UpdateProfileIsWish(int uid, boolean isSelect, String type){

        //POST
        retrofit2.Call<BaseDTO> data = API_Adapter.getInstance().updateProfileIsWish(
                String.valueOf(uid), String.valueOf(MyData.getInstance().getUid()), type, isSelect
        );

        data.enqueue(new retrofit2.Callback<BaseDTO>() {
            @Override
            public void onResponse(retrofit2.Call<BaseDTO> call, retrofit2.Response<BaseDTO> response) {
                // 성공여부
                if(response.isSuccessful()){
                    BaseDTO member = response.body();
                    if(Constant.API_RESPONSE_SUCCESS == member.getResult()) {
                        api_GetProfileList();
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
     * Dialog
     */
    CustomDialog confirmDlg;
    public void confirmDialog(final int uid, final String type){
        boolean resultValue = false;
        confirmDlg = new CustomDialog(mContext, getString(R.string.profile_delete_txt), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDlg.dismiss();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDlg.dismiss();
                api_DeleteProfile(uid, type);
            }
        }, getString(R.string.btn_cancel), getString(R.string.btn_ok));
        confirmDlg.show();
    }
}
