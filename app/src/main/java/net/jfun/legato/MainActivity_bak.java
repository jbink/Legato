package net.jfun.legato;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.zxing.integration.android.IntentIntegrator;

import net.jfun.legato.bluetooth.BluetoothFragment;
import net.jfun.legato.history.mode.ModeListFragment;
import net.jfun.legato.qr.QrFragment2;
import net.jfun.legato.roast.RoastFragment;
import net.jfun.legato.roast.profile.ProfileListFragment;
import net.jfun.legato.roast.temp.TempFragment;
import net.jfun.legato.setting.SettingFragment;
import net.jfun.legato.util.Constant;
import net.jfun.legato.util.CustomDialog;
import net.jfun.legato.util.ProgressDialogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity_bak extends BaseActivity implements BluetoothFragment.PairDeviceClickListener, RoastFragment.SendDataListener, TempFragment.SendDataListener{

    private final int FRAG_PROFILE_LIBRARY = 0;
    private final int FRAG_QR = 1;
    private final int FRAG_HISTORY = 2;
    private final int FRAG_MAIN = 3;
    private final int FRAG_FAN = 4;
    private final int FRAG_MY_PAGE = 5;

    private Context mContext;
    private TextView mTvTitle;
    private ImageView mTvTitleIcon;
    private BluetoothDevice mBleDevice = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity_bak.this;

        mTvTitle = findViewById(R.id.main_title);
        mTvTitleIcon = findViewById(R.id.main_title_icon);

        fragmentLeftReplace(FRAG_MAIN);
        findViewById(R.id.main_menu_btn_main).setBackground(ContextCompat.getDrawable(mContext, R.drawable.icon_main_on));
    }



    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_menu_btn_roast :
//                if (mBleDevice == null) {
//                    Toast.makeText(mContext, "연결된 Device가 없습니다.", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                mTvTitle.setText(R.string.profile_library);
                mTvTitleIcon.setBackground(ContextCompat.getDrawable(mContext, R.drawable.title_roasting));
                fragmentLeftReplace(FRAG_PROFILE_LIBRARY);
                findViewById(R.id.main_menu_btn_roast).setBackground(ContextCompat.getDrawable(mContext, R.drawable.icon_roast_on));
                break;
            case R.id.main_menu_btn_qr :
                mTvTitle.setText(R.string.qr_code);
                mTvTitleIcon.setBackground(ContextCompat.getDrawable(mContext, R.drawable.icon_qr_on));
                fragmentLeftReplace(FRAG_QR);
                findViewById(R.id.main_menu_btn_qr).setBackground(ContextCompat.getDrawable(mContext, R.drawable.icon_qr_on));
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
//        if (reqNewFragmentIndex == 3) {
//            transaction.addToBackStack(null); //프래그먼트의 페이지가 스택에 쌓인다.
//        }
        transaction.commitAllowingStateLoss();




//        getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
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
        Log.d("where", "MAIN - onActivityResult - ");
        connectToSelectedDevice(mBleDevice);
        if (requestCode == Constant.REQ_MAKE_PROFILE_PRO || requestCode == Constant.REQ_MAKE_PROFILE_QR){
            ProfileListFragment profileListFragment = (ProfileListFragment)getSupportFragmentManager().findFragmentById(R.id.main_fragment_area);
            profileListFragment.onActivityResult(requestCode, resultCode, data);
        } else if (requestCode == IntentIntegrator.REQUEST_CODE){
            QrFragment2 cutRightFragment = (QrFragment2)getSupportFragmentManager().findFragmentById(R.id.main_fragment_area);
            cutRightFragment.onActivityResult(requestCode, resultCode, data);
        } else if (requestCode == Constant.REQ_WITHDRAW){
            SettingFragment settingFragment = (SettingFragment)getSupportFragmentManager().findFragmentById(R.id.main_fragment_area);
            settingFragment.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void onBackPressed() {
        int backStackPageCount = getSupportFragmentManager().getBackStackEntryCount();
        Log.d("where", "backStackPageCount : " + backStackPageCount);

        if (backStackPageCount == 0)
            finishDialog();
        else
            super.onBackPressed();
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
                finish();
            }
        }, "취소", "종료");
        finishDlg.show();
    }



    @Override
    public void itemClicked(BluetoothDevice device) {
        mBleDevice = device;
        connectToSelectedDevice(mBleDevice);
//        fragmentLeftReplace(0);
//        findViewById(R.id.main_menu_btn_roast).setBackground(ContextCompat.getDrawable(mContext, R.drawable.icon_roast_on));
    }

    @Override
    public boolean isSocketConnected() {
        return false;
    }


    //Bluetooth Data
    BluetoothSocket mSocket;
    InputStream mInputStream;
    OutputStream mOutputStream;
    Thread mWorkerThread;
    int readBufferPositon;      //버퍼 내 수신 문자 저장 위치
    byte[] readBuffer;      //수신 버퍼
    byte mDelimiter = 24;


    private void connectToSelectedDevice(final BluetoothDevice device) {
        //블루투스 기기에 연결하는 과정이 시간이 걸리기 때문에 그냥 함수로 수행을 하면 GUI에 영향을 미친다
        //따라서 연결 과정을 thread로 수행하고 thread의 수행 결과를 받아 다음 과정으로 넘어간다.

        //handler는 thread에서 던지는 메세지를 보고 다음 동작을 수행시킨다.
        final ProgressDialogUtil progressDialogUtil = new ProgressDialogUtil();
        progressDialogUtil.showProgress(mContext, "Connecting...");
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                progressDialogUtil.dismissProgress();
                if (msg.what == 1) {// 연결 완료
                    final Fragment frag = getSupportFragmentManager().findFragmentById(R.id.main_fragment_area);
                    if (frag instanceof BluetoothFragment) {
                        ((BluetoothFragment) frag).setViewChangedConnect(true);
                    }
                    try {
                        //연결이 완료되면 소켓에서 outstream과 inputstream을 얻는다. 블루투스를 통해
                        //데이터를 주고 받는 통로가 된다.
                        mOutputStream = mSocket.getOutputStream();
                        mInputStream = mSocket.getInputStream();
                        // 데이터 수신 준비
                        beginListenForData();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {    //연결 실패
                    mBleDevice = null;
                    Log.d("where", "Please check the device : " );
//                    Toast.makeText(mContext,"Please check the device", Toast.LENGTH_SHORT).show();
                    try {
                        if (mSocket != null)
                            mSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        //연결과정을 수행할 thread 생성
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
                try {
                    // 소켓 생성
                    mSocket = device.createRfcommSocketToServiceRecord(uuid);
                    mSocket.connect();
                    mHandler.sendEmptyMessage(1);
                } catch (Exception e) {
                    Log.d("<< ", e.getLocalizedMessage());
                    // 블루투스 연결 중 오류 발생
                    mHandler.sendEmptyMessage(-1);
                }
            }
        });

        //연결 thread를 수행한다
        thread.start();
    }


    final Handler handler = new Handler(){};

    //블루투스 데이터 수신 Listener
    protected void beginListenForData() {
        readBuffer = new byte[24];  //  수신 버퍼
        readBufferPositon = 0;        //   버퍼 내 수신 문자 저장 위치

        // 문자열 수신 쓰레드
        mWorkerThread = new Thread(new Runnable() {
            @Override
            public void run() {

                int numBytes; // bytes returned from read()


                // Keep listening to the InputStream until an exception occurs.
                while (true) {
                    try {
                        // Read from the InputStream.
                        numBytes = mInputStream.read(readBuffer);
                        String.valueOf(readBuffer[0]);


                        // Send the obtained bytes to the UI activity.
                        Message readMsg = handler.obtainMessage(
                                MessageConstants.MESSAGE_READ, numBytes, -1,
                                readBuffer);
                        readMsg.sendToTarget();
//                    if("-3".equals(String.valueOf(readBuffer[0])) && "-3".equals(String.valueOf(readBuffer[1]))
//                        && "-2".equals(String.valueOf(readBuffer[22])) && "-2".equals(String.valueOf(readBuffer[23])) ){
//                        Log.d("where", ""+readBuffer[0]+" | "+readBuffer[1]+" | "+readBuffer[22]+" | "+readBuffer[23]);
                        final Fragment frag = getSupportFragmentManager().findFragmentById(R.id.main_fragment_area);
                        if (frag instanceof ProfileListFragment) {
                            ((ProfileListFragment) frag).test1(byteArrayToHex(readBuffer));
                        } else if (frag instanceof RoastFragment){
//                            ((RoastFragment) frag).test2(byteArrayToHex(readBuffer));
                        } else if (frag instanceof TempFragment){//-3은 fd,  -2는 fe
                            if(Constant.FD.equals(String.valueOf(readBuffer[0])) && Constant.FD.equals(String.valueOf(readBuffer[1]))
                                && Constant.FE.equals(String.valueOf(readBuffer[22])) && Constant.FE.equals(String.valueOf(readBuffer[23])) ){
                                ((TempFragment) frag).testNormal((readBuffer));
                            } else {
                                ((TempFragment) frag).testAbnormal(readBuffer);
                            }
                        } else {
                            Log.d("where", "Main : " + readBuffer);
                        }
//                        Log.d("where", "---------------------------- | -------------------------- |");
//                    } else {
//                        Log.d("where", "ERROR : " +readBuffer[0]+" | "+readBuffer[1]+" | "+readBuffer[22]+" | "+readBuffer[23]);
//                    }

                    } catch (IOException e) {
                        Log.d("<< ", "Input stream was disconnected", e);
                        break;
                    }
                }

            }
        });
        //데이터 수신 thread 시작
        mWorkerThread.start();
    }

    private interface MessageConstants {
        public static final int MESSAGE_READ = 0;
        public static final int MESSAGE_WRITE = 1;
        public static final int MESSAGE_TOAST = 2;

        // ... (Add other message types here as needed.)
    }


    public void write(byte[] bytes) {
        try {

            StringBuilder sb = new StringBuilder();
            for(final byte b: bytes)
                sb.append(String.format("%02x ", b&0xff));

            mOutputStream.write(bytes);

            // Share the sent message with the UI activity.
            Message writtenMsg = handler.obtainMessage(
                    MessageConstants.MESSAGE_WRITE, -1, -1, readBuffer);
            writtenMsg.sendToTarget();
        } catch (IOException e) {

            Log.d("where", "ERROR : "+e.getMessage());
            // Send a failure message back to the activity.
            Message writeErrorMsg =
                    handler.obtainMessage(MessageConstants.MESSAGE_TOAST);
            Bundle bundle = new Bundle();
            bundle.putString("toast",
                    "Couldn't send data to the other device");
            writeErrorMsg.setData(bundle);
            handler.sendMessage(writeErrorMsg);
        }
    }

    private String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder();
        for(final byte b: a)
            sb.append(String.format("%02x ", b&0xff));
        return sb.toString();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mSocket != null) {
            try {
                mSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    public int byte2Int(byte[] src) {
        int s1 = src[0] & 0xFF;
        int s2 = src[1] & 0xFF;
        int s3 = src[2] & 0xFF;
        int s4 = src[3] & 0xFF;

        int value = ((s1 << 24) + (s2 << 16) + (s3 << 8) + (s4 << 0));

        Log.d("where", "byte2Int ==> " + value);

        return value;
    }

    private byte[] intToByte(int a){
        byte[] value = new byte[4];


        value[0] |= (byte)((a&0xFF000000)>>24);
        value[1] |= (byte)((a&0xFF0000)>>16);
        value[2] |= (byte)((a&0xFF00)>>8);
        value[3] |= (byte)(a&0xFF);

        StringBuilder sb = new StringBuilder();
        for(final byte b: value)
            sb.append(String.format("%02x ", b&0xff));

        Log.d("where", "intToByte --> " + sb.toString());
        return value;
    }


    @Override
    public void sendData(byte[] bytes) {
        write(bytes);
    }

    @Override
    public void isViewTransparent(boolean value) {

    }

    @Override
    public boolean getFanOnOff() {
        return false;
    }


    @Override
    public void sendData1(byte[] bytes) {
        write(bytes);
    }

}