package net.jfun.legato.roast.temp;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import net.jfun.legato.R;

public class TempFragment extends Fragment {

    private Context mContext;
    private TextView mTvNormal, mTvAbnormal;

    private BluetoothDevice mBleDevice;

    public TempFragment(BluetoothDevice device) {

        mBleDevice = device;
        if (mBleDevice != null) {
//            connectToSelectedDevice(mBleDevice);
        }
    }

    public TempFragment() {

    }

    private SendDataListener sendDataClickListener;
    public interface SendDataListener {
        public void sendData1(byte[] bytes);
    }

    public TempFragment newInstance(BluetoothDevice device) {
//    public CoffeeFragment newInstance(int page) {
        TempFragment fragment = new TempFragment(device);

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

        mContext = getActivity();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        sendDataClickListener = (SendDataListener)getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_soket_temp, container, false);

        mTvNormal = rootView.findViewById(R.id.temp_normal);
        mTvAbnormal = rootView.findViewById(R.id.temp_abnormal);

        (rootView.findViewById(R.id.temp_btn_start)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                byte[] temp = new byte[16];
                for (int i=0 ; i<temp.length ; i++) {
                    temp[i] = 0;
                }
                temp[0] = (byte) 0xFD;
                temp[1] = (byte) 0xFD;
                temp[2] = (byte) 0x00;
                temp[3] = (byte) 0x00;
                temp[4] = (byte) 0x07;
                temp[5] = (byte) 0xE4;
                temp[6] = (byte) 0x3C;
                temp[7] = (byte) 0x3C;
                temp[8] = (byte) 0x44;
                temp[9] = (byte) 0x44;
                temp[10] = (byte) 0x03;
                temp[11] = (byte) 0xE8;
                temp[12] = (byte) 0x01;//시작
                temp[13] = (byte) 0x09;
                temp[14] = (byte) 0xFE;
                temp[15] = (byte) 0xFE;

                StringBuilder sb = new StringBuilder();
                for(final byte b: temp)
                    sb.append(String.format("%02x ", b&0xff));

                Log.d("where", "START WRITE ==> " + sb.toString());
                sendDataClickListener.sendData1(temp);

            }
        });
        (rootView.findViewById(R.id.temp_btn_stop)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte[] temp = new byte[16];
                for (int i=0 ; i<temp.length ; i++) {
                    temp[i] = 0;
                }
                temp[0] = (byte) 0xFD;
                temp[1] = (byte) 0xFD;
                temp[2] = (byte) 0x00;
                temp[3] = (byte) 0x00;
                temp[4] = (byte) 0x07;
                temp[5] = (byte) 0xE4;
                temp[6] = (byte) 0x3C;
                temp[7] = (byte) 0x3C;
                temp[8] = (byte) 0x44;
                temp[9] = (byte) 0x44;
                temp[10] = (byte) 0x03;
                temp[11] = (byte) 0xE8;
                temp[12] = (byte) 0x02;//종료
                temp[13] = (byte) 0x0A;
                temp[14] = (byte) 0xFE;
                temp[15] = (byte) 0xFE;

                StringBuilder sb = new StringBuilder();
                for(final byte b: temp)
                    sb.append(String.format("%02x ", b&0xff));

                Log.d("where", "START WRITE ==> " + sb.toString());
                sendDataClickListener.sendData1(temp);
            }
        });

        return rootView;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private int a =0 , b = 0;
    public void testNormal(final byte[] s){
        final String ss =""+ a++ +" : " + byteArrayToHex(s) + "\n" + mTvNormal.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable(){
                        @Override
                        public void run() {
                            mTvNormal.setText(ss);
                        }
                });
            }}
        }).start();
        Log.d("where", "Normal : " + s);

    }
    public void testAbnormal(final byte[] s){
        final String ss = ""+ b++ +" : " + byteArrayToHex(s) + "\n" + mTvAbnormal.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTvAbnormal.setText(ss);
                        }
                    });
                }
            }
        }).start();
        Log.d("where", "ALL : " + byteArrayToHex(s));
        String test = String.format("%02x ", s[4]&0xff) + String.format("%02x ", s[5]&0xff);
        Log.d("where", "Abnormal4 : " + String.format("%02x ", s[4]&0xff));
        Log.d("where", "Abnormal5 : " + String.format("%02x ", s[5]&0xff));
        Log.d("where", "test : " + Integer.parseInt(test.replaceAll(" ",""), 16));
    }

    private String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder();
        for(final byte b: a)
            sb.append(String.format("%02x ", b&0xff));
        return sb.toString();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
