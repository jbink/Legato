package net.jfun.legato.roast.profile.make;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import net.jfun.legato.R;

public class MakeProfileFragment extends Fragment implements View.OnClickListener {

    private final int TAB_COUNT = 4;

    private Context mContext;
    private TextView[] mTvTab;

    public MakeProfileFragment newInstance(BluetoothDevice device) {
//    public CoffeeFragment newInstance(int page) {
        MakeProfileFragment fragment = new MakeProfileFragment();

//        Bundle bundle = new Bundle();
//        bundle.putInt("page" , page);
//        fragment.setArguments(bundle);

        return fragment;
    }

    public MakeProfileFragment(){}



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
        View rootView = inflater.inflate(R.layout.frag_make_profile, container, false);

//        mTvTab = new TextView[]{
//                rootView.findViewById(R.id.roast_profile_tab_0),
//                rootView.findViewById(R.id.roast_profile_tab_1),
//                rootView.findViewById(R.id.roast_profile_tab_2),
//                rootView.findViewById(R.id.roast_profile_tab_3)
//        };
//
//        for (int i=0 ; i<TAB_COUNT ; i++) {
//            mTvTab[i].setOnClickListener(this);
//        }


        return rootView;
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.frag_make_profile_0_btn_minus :
                break;
        }
    }


    private String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder();
        for(final byte b: a)
            sb.append(String.format("%02x ", b&0xff));
        return sb.toString();
    }

}
