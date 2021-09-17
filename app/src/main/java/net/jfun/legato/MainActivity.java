package net.jfun.legato;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.zxing.integration.android.IntentIntegrator;

import net.jfun.legato.bluetooth.BluetoothFragment;
import net.jfun.legato.bluetooth.ConnectedThread;
import net.jfun.legato.history.HistoryFragment;
import net.jfun.legato.history.mode.ModeListFragment;
import net.jfun.legato.qr.QrActivity;
import net.jfun.legato.qr.QrFragment2;
import net.jfun.legato.roast.FanOnDialogActivity;
import net.jfun.legato.roast.RoastFragment;
import net.jfun.legato.roast.profile.ProfileListFragment;
import net.jfun.legato.roast.profile.make.MakeProfileActivity;
import net.jfun.legato.roast.temp.TempFragment;
import net.jfun.legato.setting.SettingFragment;
import net.jfun.legato.util.Constant;
import net.jfun.legato.util.CustomDialog;
import net.jfun.legato.util.ProgressDialogUtil;
import net.jfun.legato.util.SharedPreferencesUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.UUID;

import static net.jfun.legato.util.Util.byteArrayToHex;

public class MainActivity extends BaseActivity implements
        BluetoothFragment.PairDeviceClickListener,
        RoastFragment.SendDataListener,
        TempFragment.SendDataListener,
        ProfileListFragment.CallSubProfileFragmentListener,
        ModeListFragment.CallSubModeFragmentListener{

    private final int FRAG_PROFILE_LIBRARY = 0;
    private final int FRAG_QR = 1;
    private final int FRAG_HISTORY = 2;
    private final int FRAG_MAIN = 3;
    private final int FRAG_FAN = 4;
    private final int FRAG_MY_PAGE = 5;

    // #defines for identifying shared types between calling functions
    private final static int REQUEST_ENABLE_BT = 1; // used to identify adding bluetooth names
    public final static int MESSAGE_READ = 2; // used in bluetooth handler to identify message update
    private final static int CONNECTING_STATUS = 3; // used in bluetooth handler to identify message status.
    public final static int SOCKET_CLOSED = -99; // used in bluetooth handler to identify message status.
    private static final UUID BT_MODULE_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // "random" unique identifier

    private Context mContext;
    private TextView mTvTitle;
    private ImageView mTvTitleIcon;
    private BluetoothDevice mBleDevice = null;

    private RelativeLayout mLayoutTransparent;

    private boolean mBoolFanOnOff = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;

        mTvTitle = findViewById(R.id.main_title);
        mTvTitleIcon = findViewById(R.id.main_title_icon);
        mLayoutTransparent = findViewById(R.id.graph_ing_background);

        mTvTitle.setText(R.string.main);
        fragmentLeftReplace(FRAG_MAIN);
        findViewById(R.id.main_menu_btn_main).setBackground(ContextCompat.getDrawable(mContext, R.drawable.icon_main_on));

        if (SharedPreferencesUtils.getBooleanSharedPreference(mContext, Constant.PREF_CHANGE_PART) == true) {
            if (MyData.getInstance().getUseCount() >= 300 && MyData.getInstance().getUseCount() % 100 == 0) {
                changePartDialog();
            }
        }
    }



    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_menu_btn_fan1:
                if (mBleDevice == null) {
                    Toast.makeText(mContext, getString(R.string.bluetooth_no_device), Toast.LENGTH_SHORT).show();
                    return;
                }
                Fragment currFrag =  getSupportFragmentManager().findFragmentByTag("sub"+FRAG_PROFILE_LIBRARY);
                if (currFrag != null) {
                    if (currFrag instanceof RoastFragment){
                        if(((RoastFragment) currFrag).mBoolCheckConnection == true){
                            Toast.makeText(mContext, getString(R.string.roasring_not_allow_ing), Toast.LENGTH_SHORT).show();
                            return;
                        };
                    }
                }
                if (mBoolFanOnOff == false) {
                    fanOnTest();
                    mBoolFanOnOff = true;
                    findViewById(R.id.main_menu_btn_fan1).setBackground(ContextCompat.getDrawable(mContext, R.drawable.icon_fan_on));
                    fanDialog();
//                    Intent intent = new Intent(mContext, FanOnDialogActivity.class);
//                    startActivity(intent);
                } else {
                    fanOffTest();
                    mBoolFanOnOff = false;
                    findViewById(R.id.main_menu_btn_fan1).setBackground(ContextCompat.getDrawable(mContext, R.drawable.icon_fan_off));
                }
                break;
            case R.id.graph_ing_background:
                Toast.makeText(mContext, getString(R.string.bluetooth_change_screen), Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_menu_btn_roast :
                if (mBleDevice == null) {
                    Toast.makeText(mContext, getString(R.string.bluetooth_no_device), Toast.LENGTH_SHORT).show();
                    return;
                }
                mTvTitle.setText(R.string.profile_library);
                mTvTitleIcon.setBackground(ContextCompat.getDrawable(mContext, R.drawable.title_roasting));
                fragmentLeftReplace(FRAG_PROFILE_LIBRARY);
                findViewById(R.id.main_menu_btn_roast).setBackground(ContextCompat.getDrawable(mContext, R.drawable.icon_roast_on));
                break;
            case R.id.main_menu_btn_qr :
                mTvTitle.setText(R.string.qr_code);
                mTvTitleIcon.setBackground(ContextCompat.getDrawable(mContext, R.drawable.icon_qr_on));
//                fragmentLeftReplace(FRAG_QR);
                Intent intent = new Intent(mContext, QrActivity.class);
                startActivityForResult(intent, 4546);
//                findViewById(R.id.main_menu_btn_qr).setBackground(ContextCompat.getDrawable(mContext, R.drawable.icon_qr_on));
                break;
            case R.id.main_menu_btn_history :
                mTvTitle.setText(R.string.roast_history);
                mTvTitleIcon.setBackground(ContextCompat.getDrawable(mContext, R.drawable.icon_history_on));
                fragmentLeftReplace(FRAG_HISTORY);
                findViewById(R.id.main_menu_btn_history).setBackground(ContextCompat.getDrawable(mContext, R.drawable.icon_history_on));
                break;
            case R.id.main_menu_btn_main :
                mTvTitle.setText(R.string.main);
                mTvTitleIcon.setBackground(ContextCompat.getDrawable(mContext, R.drawable.icon_main_on));
                fragmentLeftReplace(FRAG_MAIN);
                findViewById(R.id.main_menu_btn_main).setBackground(ContextCompat.getDrawable(mContext, R.drawable.icon_main_on));
                break;
            case R.id.main_menu_btn_fan :
                mTvTitle.setText(R.string.fan);
                mTvTitleIcon.setBackground(ContextCompat.getDrawable(mContext, R.drawable.icon_fan_on));
                fragmentLeftReplace(FRAG_FAN);
                findViewById(R.id.main_menu_btn_fan).setBackground(ContextCompat.getDrawable(mContext, R.drawable.icon_fan_on));
                break;
            case R.id.main_menu_btn_my :
                mTvTitle.setText(R.string.my_page);
                mTvTitleIcon.setBackground(ContextCompat.getDrawable(mContext, R.drawable.icon_my_on));
                fragmentLeftReplace(FRAG_MY_PAGE);
                findViewById(R.id.main_menu_btn_my).setBackground(ContextCompat.getDrawable(mContext, R.drawable.icon_my_on));
                break;
        }
    }

    byte[] mByteProfileData = new byte[7];
    private void fanOnTest(){
        mByteProfileData[0] = (byte) 0xFD;
        mByteProfileData[1] = (byte) 0xFD;
        mByteProfileData[2] = (byte) 0x46;
        mByteProfileData[3] = (byte) 0x01;
        mByteProfileData[4] = (byte) 0x47;
        mByteProfileData[5] = (byte) 0xFE;
        mByteProfileData[6] = (byte) 0xFE;

        sendData(mByteProfileData);
    }
    private void fanOffTest(){
        mByteProfileData[0] = (byte) 0xFD;
        mByteProfileData[1] = (byte) 0xFD;
        mByteProfileData[2] = (byte) 0x46;
        mByteProfileData[3] = (byte) 0x02;
        mByteProfileData[4] = (byte) 0x44;
        mByteProfileData[5] = (byte) 0xFE;
        mByteProfileData[6] = (byte) 0xFE;
        sendData(mByteProfileData);
    }


    private void setMainMenuButtonImage() {
        findViewById(R.id.main_menu_btn_roast).setBackground(ContextCompat.getDrawable(mContext, R.drawable.icon_roast_off));
        findViewById(R.id.main_menu_btn_qr).setBackground(ContextCompat.getDrawable(mContext, R.drawable.icon_qr_off));
        findViewById(R.id.main_menu_btn_history).setBackground(ContextCompat.getDrawable(mContext, R.drawable.icon_history_off));
        findViewById(R.id.main_menu_btn_main).setBackground(ContextCompat.getDrawable(mContext, R.drawable.icon_main_off));
        findViewById(R.id.main_menu_btn_fan).setBackground(ContextCompat.getDrawable(mContext, R.drawable.icon_fan_off));
        findViewById(R.id.main_menu_btn_my).setBackground(ContextCompat.getDrawable(mContext, R.drawable.icon_my_off));
    }


//    private FragmentManager.OnBackStackChangedListener getListener(){
//        FragmentManager.OnBackStackChangedListener result = new FragmentManager.OnBackStackChangedListener(){
//            public void onBackStackChanged(){
//                FragmentManager manager = getSupportFragmentManager();
//                int i = manager.getBackStackEntryCount();
//                Log.d("where", "getBackStackEntryCount : "+i);
//                if(i != 2)
//                    return;
//                if (manager != null){
////                    CalendarFragment currFrag = (CalendarFragment) manager.findFragmentByTag("FRAG_WARNING");
//                    Fragment currFrag =  manager.findFragmentByTag("FRAG_WARNING");
////                    if(currFrag instanceof CalendarFragment) {
////                        ((CalendarFragment) currFrag).onFragmentResume();
////                    }
//                }
//            }
//        };
//        return result;
//    }

    public void fragmentLeftReplace(int reqNewFragmentIndex) {
        setMainMenuButtonImage();
        Fragment newFragment = null;
        newFragment = getLeftFragment(reqNewFragmentIndex);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


        transaction.replace(R.id.main_fragment_area, newFragment, "main" + String.valueOf(reqNewFragmentIndex));
        if (reqNewFragmentIndex == FRAG_PROFILE_LIBRARY) {

            Fragment subFragment = null;
            subFragment = new RoastFragment().newInstance();
            transaction.add(R.id.main_fragment_area, subFragment, "sub" + String.valueOf(reqNewFragmentIndex));
        }
        if (reqNewFragmentIndex == FRAG_HISTORY) {

            Fragment subFragment = null;
            subFragment = new HistoryFragment().newInstance();
            transaction.add(R.id.main_fragment_area, subFragment, "sub" + String.valueOf(reqNewFragmentIndex));
        }
//        if (reqNewFragmentIndex == 3) {
//            transaction.addToBackStack(null); //프래그먼트의 페이지가 스택에 쌓인다.
//        }
        transaction.commitAllowingStateLoss();
    }

    private Fragment getLeftFragment(int idx) {
        Fragment newFragment = null;
        switch (idx) {
            case FRAG_PROFILE_LIBRARY:
//                newFragment = new RoastFragment().newInstance(mBleDevice);
                newFragment = new ProfileListFragment().newInstance(mBleDevice);
                break;
            case FRAG_QR:
                newFragment = new QrFragment2().newInstance();
//                newFragment = new TempFragment().newInstance(mBleDevice);
                break;
            case FRAG_HISTORY:
                newFragment = new ModeListFragment().newInstance();
                break;
            case FRAG_MAIN:
                newFragment = new BluetoothFragment().newInstance();
                break;
            case FRAG_FAN:
//                newFragment = new FanFragment().newInstance();
                newFragment = new TempFragment().newInstance(mBleDevice);
                break;
            case FRAG_MY_PAGE:
                newFragment = new SettingFragment().newInstance();
//                newFragment = new FanFragment().newInstance();
                break;
        }
        return newFragment;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if(mBleDevice != null) {
//            connectToSelectedDevice(mBleDevice);
//        }
        if (requestCode == Constant.REQ_MAKE_PROFILE_PRO || requestCode == Constant.REQ_MAKE_PROFILE_QR){
            ProfileListFragment profileListFragment = (ProfileListFragment)getSupportFragmentManager().findFragmentById(R.id.main_fragment_area);
            profileListFragment.onActivityResult(requestCode, resultCode, data);
        } else if (requestCode == IntentIntegrator.REQUEST_CODE){
            QrFragment2 cutRightFragment = (QrFragment2)getSupportFragmentManager().findFragmentById(R.id.main_fragment_area);
            cutRightFragment.onActivityResult(requestCode, resultCode, data);
        } else if (requestCode == Constant.REQ_WITHDRAW){
            SettingFragment settingFragment = (SettingFragment)getSupportFragmentManager().findFragmentById(R.id.main_fragment_area);
            settingFragment.onActivityResult(requestCode, resultCode, data);
        } else if (requestCode == 4546){
            if (resultCode == RESULT_OK) {
                Toast.makeText(mContext, getString(R.string.qr_profile_success), Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_FIRST_USER){
                Toast.makeText(mContext, getString(R.string.qr_profile_duplicate), Toast.LENGTH_SHORT).show();
            } else if (resultCode == 3){
                Toast.makeText(mContext, getString(R.string.qr_profile_empty), Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == Constant.REQ_SAVE_DATA) {
            RoastFragment roastFragment = (RoastFragment)getSupportFragmentManager().findFragmentById(R.id.main_fragment_area);
            roastFragment.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void onBackPressed() {
        if (mLayoutTransparent.getVisibility() == View.VISIBLE){
            Toast.makeText(mContext, getString(R.string.bluetooth_change_screen), Toast.LENGTH_SHORT).show();
            return;
        }
        int backStackPageCount = getSupportFragmentManager().getBackStackEntryCount();
        Log.d("where", "backStackPageCount : " + backStackPageCount);

        if (backStackPageCount == 0)
            finishDialog();
        else {
            Fragment frag = getSupportFragmentManager().findFragmentById(R.id.main_fragment_area);
            if (frag != null) {
                if (frag instanceof HistoryFragment) {
                    ((HistoryFragment) frag).initFargment();
                } else if (frag instanceof RoastFragment) {
                    ((RoastFragment) frag).initRoastFargment();
                }
            }
            super.onBackPressed();
        }
    }


    CustomDialog finishDlg;
    public void finishDialog(){
        finishDlg = new CustomDialog(mContext, getString(R.string.app_finish), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishDlg.dismiss();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishDlg.dismiss();
                if(mConnectedThread != null){
                    mConnectedThread.cancel();
                }
                finish();
            }
        }, getString(R.string.btn_cancel), getString(R.string.btn_finish));
        finishDlg.show();
    }

    CustomDialog socketClosedDlg;
    public void socketClosedhDialog(){
        finishDlg = new CustomDialog(mContext, getString(R.string.bluetooth_scoket_closed), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                socketClosedDlg.dismiss();
            }
        }, getString(R.string.btn_ok));
        socketClosedDlg.show();
    }

    CustomDialog fanDlg;
    public void fanDialog(){
        fanDlg = new CustomDialog(mContext, getString(R.string.fan_on_text), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fanDlg.dismiss();
            }
        }, getString(R.string.btn_ok));
        fanDlg.show();
    }

    CustomDialog changePartDlg;
    public void changePartDialog(){
        changePartDlg = new CustomDialog(mContext, getString(R.string.change_part_text), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePartDlg.dismiss();
            }
        }, getString(R.string.btn_ok));
        changePartDlg.show();
    }



    @Override
    public void itemClicked(BluetoothDevice device) {
        mBleDevice = device;
        connectToSelectedDevice(mBleDevice);
    }

    @Override
    public boolean isSocketConnected() {
        if (mSocket != null)
            return mSocket.isConnected();
        else
            return false;
    }


    //Bluetooth Data
    BluetoothSocket mSocket;
    private ConnectedThread mConnectedThread; // bluetooth background worker thread to send and receive data
    final ProgressDialogUtil progressDialogUtil = new ProgressDialogUtil();

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        try {
            final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", UUID.class);
            return (BluetoothSocket) m.invoke(device, BT_MODULE_UUID);
        } catch (Exception e) {
            Log.e("where", "Could not create Insecure RFComm Connection",e);
        }
        return  device.createRfcommSocketToServiceRecord(BT_MODULE_UUID);
    }

    private void connectToSelectedDevice(final BluetoothDevice device1) {

        progressDialogUtil.showProgress(mContext, "Connecting...");
        // Spawn a new thread to avoid blocking the GUI one
        new Thread()
        {
            @Override
            public void run() {
                boolean fail = false;
                BluetoothAdapter mBTAdapter = BluetoothAdapter.getDefaultAdapter();
                BluetoothDevice device = mBTAdapter.getRemoteDevice(device1.getAddress());

                try {
                    mSocket = createBluetoothSocket(device);
                } catch (IOException e) {
                    fail = true;
                    Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_SHORT).show();
                }
                // Establish the Bluetooth socket connection.
                try {
                    mSocket.connect();
                } catch (IOException e) {
                    try {
                        fail = true;
                        mSocket.close();
                        mHandler.obtainMessage(CONNECTING_STATUS, -1, -1)
                                .sendToTarget();
                    } catch (IOException e2) {
                        //insert code to deal with this
                        Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_SHORT).show();
                    }
                }
                if(!fail) {
                    mConnectedThread = new ConnectedThread(mSocket, mHandler);
                    mConnectedThread.start();

                    mHandler.obtainMessage(CONNECTING_STATUS, 1, -1, device1.getName())
                            .sendToTarget();
                }
            }
        }.start();

    }

    Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg){
            if(msg.what == MESSAGE_READ){
                String readMessage = null;
                try {
                    readMessage = new String((byte[]) msg.obj, "UTF-8");

                    Log.d("where1", "Main : " + byteArrayToHex((byte[]) msg.obj));
                    Fragment currFrag =  getSupportFragmentManager().findFragmentByTag("sub"+FRAG_PROFILE_LIBRARY);
                    if (currFrag != null) {
                        if (currFrag instanceof RoastFragment){
                            ((RoastFragment) currFrag).getDataToDevice((byte[]) msg.obj);
//                            Log.d("where", "RoastFragment : " + byteArrayToHex((byte[]) msg.obj));
                        } else {
                            Log.d("where", "currFrag : " + byteArrayToHex((byte[]) msg.obj));
                        }
                    } else {
                        final Fragment frag = getSupportFragmentManager().findFragmentById(R.id.main_fragment_area);
//                        if (frag instanceof RoastFragment){
//                            ((RoastFragment) frag).getDataToDevice((byte[]) msg.obj);
//                        } else {
                            Log.d("where", "Main : " + byteArrayToHex((byte[]) msg.obj));
//                        }
                    }

//                    final Fragment frag = getSupportFragmentManager().findFragmentById(R.id.main_fragment_area);
//                    if (frag instanceof RoastFragment){
//                        ((RoastFragment) frag).getDataToDevice((byte[]) msg.obj);
//                    } else {
//                        Log.d("where", "Main : " + byteArrayToHex((byte[]) msg.obj));
//                    }


//                    Log.d("where", "" + byteArrayToHex((byte[]) msg.obj));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            else if (msg.what == SOCKET_CLOSED){
                mBleDevice = null;
                Toast.makeText(mContext, getString(R.string.bluetooth_scoket_closed), Toast.LENGTH_SHORT).show();
//                socketClosedhDialog();
                final Fragment frag = getSupportFragmentManager().findFragmentById(R.id.main_fragment_area);
                if (frag instanceof BluetoothFragment) {
                    ((BluetoothFragment) frag).setViewChangedConnect(false);
                }
            }  else if(msg.what == CONNECTING_STATUS){
                progressDialogUtil.dismissProgress();
                if(msg.arg1 == 1) {
                    final Fragment frag = getSupportFragmentManager().findFragmentById(R.id.main_fragment_area);
                    if (frag instanceof BluetoothFragment) {
                        ((BluetoothFragment) frag).setViewChangedConnect(true);
                    }
//                    mBluetoothStatus.setText("Connected to Device: " + msg.obj);
                }else
                    Toast.makeText(mContext, "Connection Failed", Toast.LENGTH_SHORT).show();
            }
        }
    };



    @Override
    public void onPause() {
        super.onPause();
//        if (mSocket != null) {
//            try {
//                mSocket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }


    @Override
    public void sendData(byte[] bytes) {
        Log.d("where", "Write : " + byteArrayToHex(bytes));
        mConnectedThread.write(bytes);
//        write(bytes);
    }

    @Override
    public void isViewTransparent(boolean value) {
        Log.e("where", "isViewTransparent : " + value);
        if(value == true){
            mLayoutTransparent.setVisibility(View.VISIBLE);
        } else {
            mLayoutTransparent.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean getFanOnOff() {
        return mBoolFanOnOff;
    }

    @Override
    public void callSubProfileFragment(int uid, String name, String profileType) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment currFrag =  getSupportFragmentManager().findFragmentByTag("sub"+FRAG_PROFILE_LIBRARY);
//        ft.add(R.id.main_fragment_area, currFrag, "FRAGMENT_D");
        ft.hide(getSupportFragmentManager().findFragmentByTag("main"+FRAG_PROFILE_LIBRARY));
        ft.show(getSupportFragmentManager().findFragmentByTag("sub"+FRAG_PROFILE_LIBRARY));
        ((RoastFragment)currFrag).deliverProfileUID(uid, name, profileType);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void callSubModeFragment(int uid, String name, String profileType) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment currFrag =  getSupportFragmentManager().findFragmentByTag("sub"+FRAG_HISTORY);
//        ft.add(R.id.main_fragment_area, currFrag, "FRAGMENT_D");
        ft.hide(getSupportFragmentManager().findFragmentByTag("main"+FRAG_HISTORY));
        ft.show(getSupportFragmentManager().findFragmentByTag("sub"+FRAG_HISTORY));
        ((HistoryFragment)currFrag).deliverProfileUIDAndName(uid, name, profileType);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
    }


    @Override
    public void sendData1(byte[] bytes) {
        Log.d("where", "sendData1 : " + byteArrayToHex(bytes));
        mConnectedThread.write(bytes);
//        write(bytes);
    }




}