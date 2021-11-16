package net.jfun.legato.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import net.jfun.legato.MyData;
import net.jfun.legato.R;
import net.jfun.legato.WebviewActivity;
import net.jfun.legato.api.API_Adapter;
import net.jfun.legato.api.BaseDTO;
import net.jfun.legato.tutorial.TutorialActivity;
import net.jfun.legato.util.Constant;
import net.jfun.legato.util.ProgressDialogUtil;
import net.jfun.legato.util.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.Set;

public class BluetoothFragment extends Fragment {

    private LinearLayout mLayAfterConnectRight,mLayAfterConnectLeft, mLayBefroreConnectRight;
    private RelativeLayout mLayBefroreConnectLeft;

    private BluetoothAdapter mBluetoothAdapter;

    private ArrayList<BluetoothDevice> mDiscoveredDevices = new ArrayList<>();
    private ListView mScanDeviceListView;
    private DeviceScanAdapter mBluetoothDevicesAdapter;


    Set<BluetoothDevice> mPairedDevices;
    private ArrayList<BluetoothDevice> mPairDevices = new ArrayList<>();
    private ListView mPairDeviceListView;
    private PairDeviceScanAdapter mBluetoothPairDevicesAdapter;

    private ProgressDialogUtil progressDialogUtil;
    private TextView mTvRoastingTotalCount, mTvRoastingTotalCount2;


    public BluetoothFragment newInstance() {
//    public CoffeeFragment newInstance(int page) {
        BluetoothFragment fragment = new BluetoothFragment();

//        Bundle bundle = new Bundle();
//        bundle.putInt("page" , page);
//        fragment.setArguments(bundle);

        return fragment;
    }

    private PairDeviceClickListener pairDeviceClickListener;
    public interface PairDeviceClickListener {
        public void itemClicked(BluetoothDevice device);
        public boolean isSocketConnected();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        pairDeviceClickListener = (PairDeviceClickListener)getActivity();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null){
            Log.d("where", "bundle이 있다.");
//            mStartPage = bundle.getInt("page");
        }

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_bluetooth, container, false);

        mTvRoastingTotalCount = rootView.findViewById(R.id.frag_ble_connect_right_roasting_count);
        mTvRoastingTotalCount.setText(String.valueOf(MyData.getInstance().getUseCount()) + "회");
        mTvRoastingTotalCount2 = rootView.findViewById(R.id.frag_ble_connect_right_roasting_count_2);
        mTvRoastingTotalCount2.setText(String.valueOf(MyData.getInstance().getUseCount()) + "회");
        mLayBefroreConnectLeft = rootView.findViewById(R.id.frag_ble_connect_left_before);
        mLayBefroreConnectRight = rootView.findViewById(R.id.frag_ble_connect_right_before);
        mLayAfterConnectLeft = rootView.findViewById(R.id.frag_ble_connect_left_after);
        mLayAfterConnectRight = rootView.findViewById(R.id.frag_ble_connect_right_after);

        mPairDeviceListView = rootView.findViewById(R.id.pair_device);
        mScanDeviceListView = rootView.findViewById(R.id.scan_device);

        mDiscoveredDevices = new ArrayList<>();
        mBluetoothDevicesAdapter = new DeviceScanAdapter(getActivity());
        mBluetoothPairDevicesAdapter = new PairDeviceScanAdapter(getActivity());


        mPairDeviceListView.setOnItemClickListener(mItemPairClickListener);
        mPairDeviceListView.setAdapter(mBluetoothPairDevicesAdapter);

        mScanDeviceListView.setOnItemClickListener(mItemScanClickListener);
        mScanDeviceListView.setAdapter(mBluetoothDevicesAdapter);

        rootView.findViewById(R.id.btn_tutorial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TutorialActivity.class);
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.bluetooth_btn_tutorial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebviewActivity.class);
                intent.putExtra(Constant.EXTRA_URL, "http://j-fun.batns.co.kr/119?preview_mode=1");
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.frag_ble_btn_scan_device).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                discoverDevicesIntent.addAction(BluetoothDevice.ACTION_PAIRING_REQUEST);          //연결 끊김 확인
                discoverDevicesIntent.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
                discoverDevicesIntent.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
                discoverDevicesIntent.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
                discoverDevicesIntent.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
                discoverDevicesIntent.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
                getActivity().registerReceiver(mBroadcastDeviceFound, discoverDevicesIntent);
                mBluetoothAdapter.startDiscovery();
            }
        });

        listPairedDevices();


        Log.d("where", "setViewChangedConnect = " + pairDeviceClickListener.isSocketConnected());
        setViewChangedConnect(pairDeviceClickListener.isSocketConnected());

        return rootView;
    }



    void listPairedDevices() {
        if (mBluetoothAdapter.isEnabled()) {
            mPairedDevices = mBluetoothAdapter.getBondedDevices();

            if (mPairedDevices.size() > 0) {
                for (BluetoothDevice device : mPairedDevices) {
                    Log.d("where", "onItemClick: deviceName = " + device.getName());
                    mPairDevices.add(device);
                    mBluetoothPairDevicesAdapter.addOneData(device);
                    //mListPairedDevices.add(device.getName() + "\n" + device.getAddress());
                }
            } else {
                Toast.makeText(getActivity(), getString(R.string.no_paired_device), Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(getActivity(), getString(R.string.bluetooth_disabled), Toast.LENGTH_SHORT).show();
        }
    }

    AdapterView.OnItemClickListener mItemPairClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            BluetoothDevice device = mPairDevices.get(i);
            //BluetoothDevice device = mPairedDevices.get(clickedItemIndex);

            String deviceName = device.getName();
            String deviceAddress = device.getAddress();

//            if(!Constant.DEVICE_NAME.equals(device.getName())) {
//                Toast.makeText(getActivity(), "LEGATO 로스터기를 선택해주세요.", Toast.LENGTH_SHORT).show();
//                return;
//            }
            api_CheckMacAddress(device);

        }
    };

    AdapterView.OnItemClickListener mItemScanClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            BluetoothDevice device = mDiscoveredDevices.get(i);
            //BluetoothDevice device = mPairedDevices.get(clickedItemIndex);

            String deviceName = device.getName();
            String deviceAddress = device.getAddress();
            Log.d("where", "onItemClick: deviceName = " + device.getBondState());

            /*
            BOND_BONDED = 12;
            BOND_BONDING = 11;
            BOND_NONE = 10;
             */

            if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                Log.d("where", "BOND_BONDED 페어링 성공?");
                mBluetoothPairDevicesAdapter.addOneData(device);
                mBluetoothDevicesAdapter.removeOneData(device.getName());
//                connectToSelectedDevice(device);
//                pairDeviceClickListener.itemClicked(device);
            } else  if (device.getBondState() == BluetoothDevice.BOND_BONDING) {
                Log.d("where", "BOND_BONDING");
            } else if (device.getBondState() == BluetoothDevice.BOND_NONE) {
                Log.d("where", "BOND_NONE");
//                if(Constant.DEVICE_NAME.equals(device.getName())) {
//                    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
//                    filter.addAction(BluetoothDevice.ACTION_PAIRING_REQUEST);
//                    getActivity().registerReceiver(mBroadcastDeviceFound, filter);
                    device.createBond();
//                }
            }
        }
    };

    // Create a BroadcastReceiver for ACTION_FOUND
    private final BroadcastReceiver mBroadcastDeviceFound = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            Log.d("where", "action -> " + action);
            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                progressDialogUtil = new ProgressDialogUtil();
                progressDialogUtil.showProgress(getActivity(), "Scanning...");
                mBluetoothPairDevicesAdapter.removeAllData();
            }
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                // Add the name and address to an array adapter to show in a ListView
                mDiscoveredDevices.add(device);
                mBluetoothDevicesAdapter.addOneData(device);
//                mBluetoothDevicesAdapter.addAllData(mDiscoveredDevices);
            }
            if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                Log.d("where", "~~~~~~~~~~~ ACTION_BOND_STATE_CHANGED !!");
                Log.d("where", "BOND_State" + device.getBondState());
                if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                    listPairedDevices();
                    mBluetoothDevicesAdapter.removeOneData(device.getName());
                }
            }
            if (BluetoothDevice.ACTION_PAIRING_REQUEST.equals(action)) {
                Log.d("where", "ACTION_PAIRING_REQUEST !!");

                try {
//                    int pin=intent.getIntExtra("android.bluetooth.device.extra.PAIRING_KEY", 2020);
//                    //the pin in case you need to accept for an specific pin
//                    Log.d("where", "PIN : " + intent.getIntExtra("android.bluetooth.device.extra.PAIRING_KEY",0));
//                    //maybe you look for a name or address
//                    byte[] pinBytes;
//                    pinBytes = (""+2020).getBytes("UTF-8");
//                    device.setPin(pinBytes);
                    //setPairing confirmation if neeeded
                    device.setPairingConfirmation(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Log.d("where", "ACTION_DISCOVERY_FINISHED !!");
                progressDialogUtil.dismissProgress();
            }
//            if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
//                setViewChangedConnect(true);
//            }
//            if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
//                setViewChangedConnect(false);
//            }
        }
    };

    public void setViewChangedConnect(boolean isConneted) {
        if (isConneted == true) {
            mLayBefroreConnectLeft.setVisibility(View.GONE);
            mLayBefroreConnectRight.setVisibility(View.GONE);
            mLayAfterConnectLeft.setVisibility(View.VISIBLE);
            mLayAfterConnectRight.setVisibility(View.VISIBLE);
        } else {
            mLayBefroreConnectLeft.setVisibility(View.VISIBLE);
            mLayBefroreConnectRight.setVisibility(View.VISIBLE);
            mLayAfterConnectLeft.setVisibility(View.GONE);
            mLayAfterConnectRight.setVisibility(View.GONE);

        }
    }

    /**
     * 블루투스 기기 체크
     */
    private void api_CheckMacAddress(final BluetoothDevice device){

        //POST
        retrofit2.Call<BaseDTO> data = API_Adapter.getInstance().checkMacAddress(
                device.getAddress(),
                device.getName(),
                SharedPreferencesUtils.getStringSharedPreference(getActivity(), Constant.PREF_ID));

        data.enqueue(new retrofit2.Callback<BaseDTO>() {
            @Override
            public void onResponse(retrofit2.Call<BaseDTO> call, retrofit2.Response<BaseDTO> response) {
                // 성공여부
                if(response.isSuccessful()){
                    BaseDTO member = response.body();
                    /**
                     * { Result = -1, Message = "등록되지 않은 계정입니다." }
                     * { Result = 0, Message = "등록가능한 기기입니다." }
                     * { Result = 1, Message = "매칭이 된 MacAddress입니다." }
                     * { Result = 2, Message = "매칭되는 MacAddress가 없습니다." }
                     */
                    if(Constant.API_RESPONSE_SUCCESS == member.getResult()) {
                        pairDeviceClickListener.itemClicked(device);
                    } else if(Constant.API_RESPONSE_ERROR_0 == member.getResult()){
                        pairDeviceClickListener.itemClicked(device);
                        Toast.makeText(getActivity(), member.getMessage(), Toast.LENGTH_SHORT).show();
                    } else if( Constant.API_RESPONSE_ERROR_1 == member.getResult()){
                    } else if( Constant.API_RESPONSE_ERROR_2 == member.getResult()){
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

}

