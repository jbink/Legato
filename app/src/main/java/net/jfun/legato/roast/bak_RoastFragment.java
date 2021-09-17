package net.jfun.legato.roast;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

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
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;

import net.jfun.legato.R;
import net.jfun.legato.roast.temp.MyMarkerView;
import net.jfun.legato.util.VerticalSeekbar;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

public class bak_RoastFragment extends Fragment implements OnChartValueSelectedListener, SeekBar.OnSeekBarChangeListener {

    private Context mContexk;

    private LineChart chart;
    private VerticalSeekbar[] FireSeekbar;
    private TextView[] FireSeekbarValue;
    private TextView ForceStop;

    private BluetoothDevice mBleDevice;

    public bak_RoastFragment(BluetoothDevice device) {

        mBleDevice = device;
        connectToSelectedDevice(mBleDevice);
    }

    public bak_RoastFragment() {

    }

    public bak_RoastFragment newInstance(BluetoothDevice device) {
//    public CoffeeFragment newInstance(int page) {
        bak_RoastFragment fragment = new bak_RoastFragment(device);

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

        mContexk = getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_roast_dashboard, container, false);

        Log.d("where", "Ble Name : " + mBleDevice.getName());
        chart = rootView.findViewById(R.id.frag_coffee_chart);
        ForceStop = rootView.findViewById(R.id.frag_roast_btn_force_stop);
        ForceStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte[] temp = new byte[16];
                for (int i=0 ; i<temp.length ; i++) {
                    temp[i] = 0;
                }
                temp[0] = -3;
                temp[1] = -3;
                temp[12] = 2;
                temp[14] = -2;
                temp[15] = -2;

                StringBuilder sb = new StringBuilder();
                for(final byte b: temp)
                    sb.append(String.format("%02x ", b&0xff));

                Log.d("where", "WRITE ==> " + sb.toString());
                write(temp);
            }
        });

        // background color
        chart.setBackgroundColor(Color.BLACK);

        // disable description text
        chart.getDescription().setEnabled(false);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // set listeners
        chart.setOnChartValueSelectedListener(this);
        chart.setDrawGridBackground(false);

        // create marker to display box when values are selected
        MyMarkerView mv = new MyMarkerView(getContext(), R.layout.custom_marker_view);

        // Set the marker to the chart
        mv.setChartView(chart);
        chart.setMarker(mv);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        // chart.setScaleXEnabled(true);
        // chart.setScaleYEnabled(true);

        // force pinch zoom along both axis
        chart.setPinchZoom(true);


        XAxis xAxis;
        {   // // X-Axis Style // //
            xAxis = chart.getXAxis();

            xAxis.setTextColor(Color.WHITE);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            // vertical grid lines
//            xAxis.enableGridDashedLine(10f, 10f, 0f);
        }

        YAxis yAxis;
        {   // // Y-Axis Style // //
            yAxis = chart.getAxisLeft();

            yAxis.setTextColor(Color.WHITE);
            // disable dual axis (only use LEFT axis)
            chart.getAxisRight().setEnabled(false);

            // horizontal grid lines
//            yAxis.enableGridDashedLine(10f, 10f, 0f);

            // axis range
            yAxis.setAxisMaximum(200f);
            yAxis.setAxisMinimum(0f);
        }


//        {   // // Create Limit Lines // //
//            LimitLine llXAxis = new LimitLine(9f, "Index 10");
//            llXAxis.setLineWidth(4f);
//            llXAxis.enableDashedLine(10f, 10f, 0f);
//            llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//            llXAxis.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
//            llXAxis.setTextSize(10f);
//
//            LimitLine ll1 = new LimitLine(150f, "Upper Limit");
//            ll1.setLineWidth(4f);
//            ll1.enableDashedLine(10f, 10f, 0f);
//            ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
//            ll1.setTextSize(10f);
//
//            LimitLine ll2 = new LimitLine(-30f, "Lower Limit");
//            ll2.setLineWidth(4f);
//            ll2.enableDashedLine(10f, 10f, 0f);
//            ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//            ll2.setTextSize(10f);
//
//            // draw limit lines behind data instead of on top
//            yAxis.setDrawLimitLinesBehindData(true);
//            xAxis.setDrawLimitLinesBehindData(true);
//
//            // add limit lines
//            yAxis.addLimitLine(ll1);
//            yAxis.addLimitLine(ll2);
//            //xAxis.addLimitLine(llXAxis);
//        }

        chart.getAxisLeft().setDrawGridLines(false);
        chart.getXAxis().setDrawGridLines(false);

        // add data
        setData(15, 10);

        // draw points over time
        chart.animateX(1500);

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();
        l.setTextColor(Color.WHITE);

        // draw legend entries as lines
        l.setForm(Legend.LegendForm.LINE);


        FireSeekbar = new VerticalSeekbar[]{
                rootView.findViewById(R.id.seekBar_0),
                rootView.findViewById(R.id.seekBar_1),
                rootView.findViewById(R.id.seekBar_2),
                rootView.findViewById(R.id.seekBar_3),
                rootView.findViewById(R.id.seekBar_4)
        };

        FireSeekbarValue = new TextView[]{
                rootView.findViewById(R.id.seekBar_value_0),
                rootView.findViewById(R.id.seekBar_value_1),
                rootView.findViewById(R.id.seekBar_value_2),
                rootView.findViewById(R.id.seekBar_value_3),
                rootView.findViewById(R.id.seekBar_value_4)
        };

        for (int i=0 ; i<FireSeekbar.length ; i++) {
            FireSeekbar[i].setPadding(0,0,0,0);
            FireSeekbar[i].setOnSeekBarChangeListener(this);
//            FireSeekbar[i].setOnTouchListener(this);
        }

        FireSeekbar[0].setEnableSeekbar(mContexk, false);
        FireSeekbar[0].setEnabled(false);
        FireSeekbar[1].setEnableSeekbar(mContexk, true);
        FireSeekbar[2].setEnableSeekbar(mContexk, true);
        FireSeekbar[3].setEnableSeekbar(mContexk, true);
        FireSeekbar[4].setEnableSeekbar(mContexk, false);
        FireSeekbar[4].setEnabled(false);

        return rootView;
    }

    int i=0;
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            addEntry();
            if (i > 20) {
                mHandler.removeMessages(0);
                mHandler = null;
            } else {
                i++;
                mHandler.sendEmptyMessageDelayed(0, 1000);
            }
        }
    };

    private void setData(int count, float range) {

        ArrayList<Entry> values1 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * range) + (i*2);
            values1.add(new Entry(i, val));
        }

        ArrayList<Entry> values2 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * range);
            values2.add(new Entry(i, val));
        }

        ArrayList<Entry> values3 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * range);
            values3.add(new Entry(i, val));
        }

        LineDataSet set1, set2, set3;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) chart.getData().getDataSetByIndex(1);
            set3 = (LineDataSet) chart.getData().getDataSetByIndex(2);
            set1.setValues(values1);
            set2.setValues(values2);
            set3.setValues(values3);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values1, "DataSet 1");
            set1.setMode(LineDataSet.Mode.LINEAR);
            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(Color.RED);
            set1.setVisible(true);
            set1.setValueTextSize(0f);
            set1.setDrawCircles(false);
            set1.setDrawValues(false);
//            set1.setCircleColor(Color.RED);
//            set1.setValueTextColor(Color.RED);
//            set1.setFillColor(ColorTemplate.getHoloBlue());
//            set1.setHighLightColor(Color.rgb(244, 117, 117));
//            set1.setLineWidth(1f);
//            set1.setCircleRadius(6f);
            set1.setDrawCircleHole(false);
//            set1.setCircleHoleRadius(5f);
//            set1.setCircleHoleColor(Color.BLACK);
            set1.setDrawFilled(true);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.graph_gradation);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.BLACK);
            }

            // create a dataset and give it a type
            set2 = new LineDataSet(values2, "DataSet 2");
            set2.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
            set2.setAxisDependency(YAxis.AxisDependency.RIGHT);
            set2.setColor(Color.YELLOW);
            set2.setCircleColor(Color.YELLOW);
            set2.setValueTextColor(Color.YELLOW);
            set2.setLineWidth(1f);
            set2.setCircleRadius(6f);
            set2.setDrawCircleHole(true);
            set2.setCircleHoleRadius(5f);
            set2.setCircleHoleColor(Color.BLACK);
            set2.setDrawFilled(true);
            set2.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.graph_gradation_yellow);
                set2.setFillDrawable(drawable);
            } else {
                set2.setFillColor(Color.BLACK);
            }

            set3 = new LineDataSet(values3, "DataSet 3");
            set3.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
            set3.setAxisDependency(YAxis.AxisDependency.RIGHT);
            set3.setColor(Color.GREEN);
            set3.setCircleColor(Color.GREEN);
            set3.setLineWidth(2f);
            set3.setCircleRadius(3f);
            set3.setFillAlpha(65);
            set3.setFillColor(ColorTemplate.colorWithAlpha(Color.GREEN, 200));
            set3.setDrawCircleHole(false);
            set3.setHighLightColor(Color.GREEN);
            set3.setLineWidth(1f);
            set3.setCircleRadius(6f);
            set3.setDrawCircleHole(true);
            set3.setCircleHoleRadius(5f);
            set3.setCircleHoleColor(Color.BLACK);

            // create a data object with the data sets
            LineData data = new LineData(set1);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(9f);

            // set data
            chart.setData(data);
        }
    }





    private void addEntry(){
        LineData data = chart.getData();
        Log.d("where", "getEntryCount: " +data.getEntryCount());
        if (data != null) {
            ILineDataSet set = data.getDataSetByIndex(0);
            data.addEntry(new Entry(set.getEntryCount(), (float)(Math.random() * 10)+(i*2)), 0);
            data.notifyDataChanged();
            chart.notifyDataSetChanged();
            chart.moveViewToX(data.getEntryCount());
            chart.setVisibleXRangeMaximum(200000);
        }
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {

        Log.d("where", e.toString());
        Log.d("where", "low: " + chart.getLowestVisibleX() + ", high: " + chart.getHighestVisibleX());
        Log.d("where", "xMin: " + chart.getXChartMin() + ", xMax: " + chart.getXChartMax() + ", yMin: " + chart.getYChartMin() + ", yMax: " + chart.getYChartMax());

    }

    @Override
    public void onNothingSelected() {
        Log.d("where","onNothingSelected");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        for (int i=0 ; i<FireSeekbar.length ; i++) {
            if (seekBar.getId() == FireSeekbar[i].getId()){
                FireSeekbarValue[i].setText(""+progress+"%");
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

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
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 1) // 연결 완료
                {
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
                    Toast.makeText(getActivity(),"Please check the device", Toast.LENGTH_SHORT).show();
                    try {
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


    final Handler handler = new Handler();

    //블루투스 데이터 수신 Listener
    protected void beginListenForData() {
        Log.d("where", "beginListenForData");
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
                    numBytes = mInputStream.read(readBuffer);String.valueOf(readBuffer[0]);
                    // Send the obtained bytes to the UI activity.
//                        Message readMsg = handler.obtainMessage(
//                                Constants.MESSAGE_READ, numBytes, -1,
//                                readBuffer);
//                        readMsg.sendToTarget();
//                    if("-3".equals(String.valueOf(readBuffer[0])) && "-3".equals(String.valueOf(readBuffer[1]))){
                        Log.d("where", ""+readBuffer[0]+" | "+readBuffer[1]+" | "+readBuffer[2]);
                        Log.d("where", byteArrayToHex(readBuffer));
                        Log.d("where", "-----------------------------------");
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

            Log.d("where", "sb : "+sb.toString());
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
}
