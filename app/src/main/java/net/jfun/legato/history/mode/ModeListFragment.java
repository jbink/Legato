package net.jfun.legato.history.mode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import net.jfun.legato.history.mode.basic.ModeListAdapter_Basic;
import net.jfun.legato.history.mode.basic.ModeListAdapter_Pro;
import net.jfun.legato.history.mode.basic.ModeListAdapter_QR;
import net.jfun.legato.history.mode.basic.ModeListAdapter_Select;
import net.jfun.legato.roast.profile.ProfileListFragment;
import net.jfun.legato.tutorial.TutorialActivity;
import net.jfun.legato.util.Constant;

public class ModeListFragment extends Fragment implements View.OnClickListener {

    private final int TAB_COUNT = 4;

    private Context mContext;
    private ListView[] mListView;
    private TextView[] mTvTab;
//    private List<ModeListDTO.ContentBean>[] mDataMode = new ArrayList[TAB_COUNT];;

    private ModeListAdapter_Basic mAdapter_Basic;
    private ModeListAdapter_Pro mAdapter_Pro;
    private ModeListAdapter_QR mAdapter_QR;
    private ModeListAdapter_Select mAdapter_Select;

    public ModeListFragment newInstance() {
//    public CoffeeFragment newInstance(int page) {
        ModeListFragment fragment = new ModeListFragment();

//        Bundle bundle = new Bundle();
//        bundle.putInt("page" , page);
//        fragment.setArguments(bundle);

        return fragment;
    }

    private CallSubModeFragmentListener callSubModdeFragmentListener;
    public interface CallSubModeFragmentListener {
        public void callSubModeFragment(int uid, String name, String profileType);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callSubModdeFragmentListener = (CallSubModeFragmentListener)getActivity();
    }

    public ModeListFragment(){}


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

        rootView.findViewById(R.id.btn_tutorial).setOnClickListener(this);
        rootView.findViewById(R.id.btn_tutorial).setVisibility(View.GONE);

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

        mAdapter_Basic = new ModeListAdapter_Basic(mContext);
        mAdapter_Pro = new ModeListAdapter_Pro(mContext);
        mAdapter_QR = new ModeListAdapter_QR(mContext);
        mAdapter_Select = new ModeListAdapter_Select(mContext);

        for (int i=0 ; i<TAB_COUNT ; i++) {
            mTvTab[i].setOnClickListener(this);
        }
        mListView[0].setAdapter(mAdapter_Basic);
        mListView[1].setAdapter(mAdapter_Pro);
        mListView[2].setAdapter(mAdapter_QR);
        mListView[3].setAdapter(mAdapter_Select);

        mListView[0].setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ModeListDTO.ContentBean.BasicBean data = (ModeListDTO.ContentBean.BasicBean)adapterView.getAdapter().getItem(i);

                callSubModdeFragmentListener.callSubModeFragment(data.getUid(), data.getProfileName(), Constant.PROFILE_TYPE_BASIC);

//                Fragment newFragment = new HistoryFragment(MyData.getInstance().getUid(), data.getUid(), data.getProfileName());
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.main_fragment_area, newFragment);
//                transaction.addToBackStack(null);
//
//                transaction.commitAllowingStateLoss();
            }
        });
        mListView[1].setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ModeListDTO.ContentBean.ProBean data = (ModeListDTO.ContentBean.ProBean)adapterView.getAdapter().getItem(i);

                callSubModdeFragmentListener.callSubModeFragment(data.getUid(), data.getProfileName(), Constant.PROFILE_TYPE_PRO);

//                Fragment newFragment = new HistoryFragment(MyData.getInstance().getUid(), data.getUid(), data.getProfileName());
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.main_fragment_area, newFragment);
//                transaction.addToBackStack(null);
//                transaction.commitAllowingStateLoss();
            }
        });

        api_GetProfileList();

        return rootView;
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btn_tutorial :
                Intent intent = new Intent(mContext, TutorialActivity.class);
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
//
//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//        ModeListDTO.ContentBean data = (ModeListDTO.ContentBean)adapterView.getAdapter().getItem(i);
//
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//        // Create new fragment and transaction
//        Fragment newFragment = new HistoryFragment(data.getMemberUid(), data.getUid());
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//
//        // Replace whatever is in the fragment_container view with this fragment,
//        // and add the transaction to the back stack
//        transaction.replace(R.id.main_fragment_area, newFragment);
//        transaction.addToBackStack(null);
//
//        // Commit the transaction
//        transaction.commitAllowingStateLoss();
//
//    }

    private void setTabButtonAndListView(int position) {
        for (int i=0 ; i<TAB_COUNT ; i++) {
            mTvTab[i].setTextColor(ContextCompat.getColor(mContext, R.color.COLOR_3B3B3B));
            mTvTab[i].setBackground(ContextCompat.getDrawable(mContext, R.drawable.btn_tab_back));
            mListView[i].setVisibility(View.GONE);
        }
        mTvTab[position].setTextColor(ContextCompat.getColor(mContext, R.color.COLOR_6B6B6B));
        mTvTab[position].setBackground(ContextCompat.getDrawable(mContext, R.drawable.btn_tab_back_prs));
        mListView[position].setVisibility(View.VISIBLE);
    }

    /**
     * Profile 목록
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


}
