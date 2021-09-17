package net.jfun.legato.roast;

import android.content.Context;
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
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.github.mikephil.charting.utils.Utils;

import net.jfun.legato.MyData;
import net.jfun.legato.R;
import net.jfun.legato.api.API_Adapter;
import net.jfun.legato.api.BaseDTO;
import net.jfun.legato.roast.profile.ProfileDTO;
import net.jfun.legato.roast.save.SaveDataActivity;
import net.jfun.legato.roast.temp.DataEntry;
import net.jfun.legato.roast.temp.MyMarkerView;
import net.jfun.legato.util.Constant;
import net.jfun.legato.util.CustomDialog;
import net.jfun.legato.util.Util;
import net.jfun.legato.util.VerticalSeekbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static net.jfun.legato.util.Util.byteArrayToHex;
import static net.jfun.legato.util.Util.getByteToArray;
import static net.jfun.legato.util.Util.hexStringToByteArray;

public class RoastFragment extends Fragment implements OnChartValueSelectedListener, SeekBar.OnSeekBarChangeListener, View.OnClickListener {

    private Context mContext;

    private LineChart[] mLineGraphs;
    private VerticalSeekbar[] FireSeekbar;
    private TextView[] FireSeekbarValue;
    private ImageView[] mIvStep;
    private TextView ForceStop, ForceStart, ForceRestart, ForceTempReset;
    private TextView mTvProfileType, mTvProfileName;

    private ImageButton[] mIbtnGraphType;
    private TextView[] mTvGraphType;

    private DecimalFormat sdf, temperatureFormat;
    private TextView mTvCurrentTemp, mTvTargetTemp, mTvAD, mTvRoastingTime, mTvTotalTime, mTvROR;
    private int mIntTotalTime = 0;
    private float mIntROR_Value = 0;

    byte[] mByteProfileData = new byte[16];

    private String mStrProfileUid, mStrProfileType, mStrProfileName;

    private boolean mBoolCheckAddRorData = false; //2번째 ROR 데이터부터 넣기 위함
    public boolean mBoolCheckConnection = false; //기기로부터 데이터를 받는 순간

    float f_prefvTemperature = 0; //1분 전 온도를 기억하기 위한 변수

    private int mCurrentRoastingCount = 0;

    int mIntStep = -1;//시작 버튼과 동시에 step이 0으로 들어오면 첫 번째 seekbar 부터 disable 처리 후 단계 기억하기 위한 변수

    private boolean mBoolReadyRoasting = true;


    ArrayList<DataEntry> mHistoryData_Roast = new ArrayList<>();
    ArrayList<DataEntry> mHistoryData_ROR = new ArrayList<>();
    ArrayList<DataEntry> mHistoryData_AD = new ArrayList<>();

    public RoastFragment() {

    }

    public RoastFragment newInstance() {
//    public CoffeeFragment newInstance(int page) {
        RoastFragment fragment = new RoastFragment();

        return fragment;
    }

    private SendDataListener sendDataClickListener;



    public interface SendDataListener {
        public void sendData(byte[] bytes);
        public void isViewTransparent(boolean value);
        public boolean getFanOnOff();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        sendDataClickListener = (SendDataListener)getActivity();
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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_roast_dashboard, container, false);

        sdf = new DecimalFormat("00");
        temperatureFormat = new DecimalFormat("###.0");
        mTvAD = rootView.findViewById(R.id.roast_dashboard_ad);
        mTvProfileType = rootView.findViewById(R.id.roast_dashboard_txt_profile_type);
        mTvProfileName = rootView.findViewById(R.id.roast_dashboard_txt_profile_name);
        mTvTargetTemp = rootView.findViewById(R.id.roast_dashboard_target_temperature);
        mTvCurrentTemp = rootView.findViewById(R.id.roast_dashboard_current_temperature);
        mTvRoastingTime = rootView.findViewById(R.id.roast_dashboard_roasting_time);
        mTvROR = rootView.findViewById(R.id.roast_dashboard_ror);
        mTvTotalTime = rootView.findViewById(R.id.roast_dashboard_time);
        mIbtnGraphType = new ImageButton[]{
                rootView.findViewById(R.id.roast_dashboard_ibtn_roast),
                rootView.findViewById(R.id.roast_dashboard_ibtn_ror),
                rootView.findViewById(R.id.roast_dashboard_ibtn_ad)
        };
        mTvGraphType = new TextView[]{
                rootView.findViewById(R.id.roast_dashboard_txt_roast),
                rootView.findViewById(R.id.roast_dashboard_txt_ror),
                rootView.findViewById(R.id.roast_dashboard_txt_ad)
        };
        mLineGraphs = new LineChart[]{
                rootView.findViewById(R.id.frag_coffee_chart_roast),
                rootView.findViewById(R.id.frag_coffee_chart_ror),
                rootView.findViewById(R.id.frag_coffee_chart_ad)
        };
        mIvStep = new ImageView[]{
                rootView.findViewById(R.id.roast_dashboard_iv_step_0),
                rootView.findViewById(R.id.roast_dashboard_iv_step_1),
                rootView.findViewById(R.id.roast_dashboard_iv_step_2),
                rootView.findViewById(R.id.roast_dashboard_iv_step_3)
        };
        for (int i=0 ; i<3 ; i++) {
            mIbtnGraphType[i].setOnClickListener(this);
            mTvGraphType[i].setOnClickListener(this);
        }
        //원두 로스팅 시작
        ForceStart = rootView.findViewById(R.id.frag_roast_btn_force_start);
        ForceStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sendDataClickListener.getFanOnOff() == true) {
                    Toast.makeText(getActivity(), getString(R.string.roasring_not_allow), Toast.LENGTH_SHORT).show();
                    return;
                } else if (mCurrentRoastingCount > 5){
                    Toast.makeText(getActivity(), getString(R.string.roasring_not_allow_5_times), Toast.LENGTH_SHORT).show();
                    return;
                }
                mByteProfileData[12] = (byte) 0x01;
                mByteProfileData[13] = setParityBit(mByteProfileData);
            sendDataClickListener.sendData(mByteProfileData);
            }
        });
        ForceStop = rootView.findViewById(R.id.frag_roast_btn_force_stop);
        ForceStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishDialog();
            }
        });
        //원두 반복 로스팅
        ForceRestart = rootView.findViewById(R.id.frag_roast_btn_force_restart);
        ForceRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sendDataClickListener.getFanOnOff() == true) {
                    Toast.makeText(getActivity(), getString(R.string.roasring_not_allow), Toast.LENGTH_SHORT).show();
                    return;
                } else if (mCurrentRoastingCount > 5){
                    Toast.makeText(getActivity(), getString(R.string.roasring_not_allow_5_times), Toast.LENGTH_SHORT).show();
                    return;
                }
                initRoastFargment();
                mByteProfileData[12] = (byte) 0x01;
                mByteProfileData[13] = setParityBit(mByteProfileData);
                sendDataClickListener.sendData(mByteProfileData);
            }
        });
        //온도재설정
        ForceTempReset = rootView.findViewById(R.id.frag_roast_btn_force_reset_temp);
        ForceTempReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), getString(R.string.temperature_resetting), Toast.LENGTH_SHORT).show();
                sendDataClickListener.sendData(mByteProfileData);
            }
        });

        // add data
//        setData(3, 10);

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
            FireSeekbar[i].setMax(20);
            FireSeekbar[i].setPadding(0,0,0,0);
            FireSeekbar[i].setOnSeekBarChangeListener(this);
//            FireSeekbar[i].setOnTouchListener(this);
        }

        initGraph();
//        addROREntry(0);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.roast_dashboard_ibtn_roast :
            case R.id.roast_dashboard_txt_roast :
                setButtonText(0);
                break;
            case R.id.roast_dashboard_ibtn_ror :
            case R.id.roast_dashboard_txt_ror :
                setButtonText(1);
                break;
            case R.id.roast_dashboard_ibtn_ad :
            case R.id.roast_dashboard_txt_ad :
                setButtonText(2);
                break;

        }
    }

    private void initGraph() {
        for (int i=0 ; i<3 ; i++){
            mLineGraphs[i].setBackgroundColor(Color.BLACK);// background color
            mLineGraphs[i].getDescription().setEnabled(false);// disable description text
            mLineGraphs[i].setTouchEnabled(true);// enable touch gestures
            mLineGraphs[i].setOnChartValueSelectedListener(this); // set listeners
            mLineGraphs[i].setDrawGridBackground(false);
            mLineGraphs[i].setDragEnabled(true);// enable scaling and dragging
            mLineGraphs[i].setScaleEnabled(true);
            // chart.setScaleXEnabled(true);
            // chart.setScaleYEnabled(true);
            mLineGraphs[i].setPinchZoom(true);

            MyMarkerView mv = new MyMarkerView(getContext(), R.layout.custom_marker_view);// create marker to display box when values are selected
            mv.setChartView(mLineGraphs[i]);// Set the marker to the chart
            mLineGraphs[i].setMarker(mv);

            Legend l = mLineGraphs[i].getLegend();// get the legend (only possible after setting data)
            l.setTextColor(Color.WHITE);
            l.setForm(Legend.LegendForm.LINE);// draw legend entries as lines

            XAxis xAxis;
            {   // // X-Axis Style // //
                xAxis = mLineGraphs[i].getXAxis();

                xAxis.setTextColor(Color.WHITE);
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setAxisMinimum(1f);
                xAxis.setDrawGridLines(false);
                // vertical grid lines
                //            xAxis.enableGridDashedLine(10f, 10f, 0f);
            }

            YAxis yAxis;
            {   // // Y-Axis Style // //
                yAxis = mLineGraphs[i].getAxisLeft();

                yAxis.setTextColor(Color.WHITE);
                // disable dual axis (only use LEFT axis)
                mLineGraphs[i].getAxisRight().setEnabled(false);
                yAxis.setDrawGridLines(false);
                // horizontal grid lines
                //            yAxis.enableGridDashedLine(10f, 10f, 0f);

                yAxis.setAxisMinimum(0f);
            }
            String dataSetName;
            if (i == 0) {
                yAxis.setAxisMaximum(240f);
                dataSetName = "Roasting";
            } else if (i == 1) {
                yAxis.setAxisMaximum(30f);
                dataSetName = "ROR";
            } else {
                yAxis.setAxisMaximum(80f);
                dataSetName = "AD";
            }
            ArrayList<Entry> values = new ArrayList<>();
//            for (int j = 0; j < 2; j++) {
//                float val = (float) (Math.random() * 100) + (j*2);
//                values.add(new Entry(j, val));
//            }

            LineDataSet set1 = new LineDataSet(values, dataSetName);
            set1.setMode(LineDataSet.Mode.LINEAR);
            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(Color.RED);
            set1.setVisible(true);
            set1.setValueTextSize(0f);
            set1.setDrawCircles(false);
            set1.setDrawValues(false);
            set1.setDrawCircleHole(false);
//            set1.setCircleHoleRadius(5f);
//            set1.setCircleHoleColor(Color.BLACK);
            set1.setDrawFilled(true);
            final int idx = i;
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return mLineGraphs[idx].getAxisLeft().getAxisMinimum();
                }
            });
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.graph_gradation);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.BLACK);
            }

            // create a data object with the data sets
            LineData data = new LineData(set1);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(9f);

            // set data
            mLineGraphs[i].setData(data);
        }
    }


    private void addRoastEntry( float value){
        LineData data = mLineGraphs[0].getData();
        if (data != null) {
            ILineDataSet set = data.getDataSetByIndex(0);
//            data.addEntry(new Entry(set.getEntryCount(), (float)(Math.random() * 10)+(i*2)), 0);
            data.addEntry(new Entry(set.getEntryCount(), value), 0);
            mHistoryData_Roast.add(new DataEntry(set.getEntryCount(), value));
            data.notifyDataChanged();
            mLineGraphs[0].notifyDataSetChanged();
            mLineGraphs[0].moveViewToX(data.getEntryCount());
            mLineGraphs[0].setVisibleXRangeMaximum(200000);
        }
    }

    private void addROREntry(float value) {
        LineData data = mLineGraphs[1].getData();
        if (data != null) {
            ILineDataSet set = data.getDataSetByIndex(0);
            data.addEntry(new Entry(set.getEntryCount(), value), 0);
            mHistoryData_ROR.add(new DataEntry(set.getEntryCount(), value));
            data.notifyDataChanged();
            mLineGraphs[1].notifyDataSetChanged();
            mLineGraphs[1].moveViewToX(data.getEntryCount());
            mLineGraphs[1].setVisibleXRangeMaximum(200000);
        }
    }

    private void addADEntry(float value){
        LineData data = mLineGraphs[2].getData();
        if (data != null) {
            ILineDataSet set = data.getDataSetByIndex(0);
            data.addEntry(new Entry(set.getEntryCount(), value), 0);
            mHistoryData_AD.add(new DataEntry(set.getEntryCount(), value));
            data.notifyDataChanged();
            mLineGraphs[2].notifyDataSetChanged();
            mLineGraphs[2].moveViewToX(data.getEntryCount());
            mLineGraphs[2].setVisibleXRangeMaximum(200000);
        }
    }

    private void setButtonText(int position) {
        for (int i=0 ; i<3 ; i++) {
            mTvGraphType[i].setTextColor(ContextCompat.getColor(getActivity(), R.color.COLOR_3B3B3B));
            mIbtnGraphType[i].setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.button_cirde_off));
            mLineGraphs[i].setVisibility(View.GONE);
        }
        mTvGraphType[position].setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
        mIbtnGraphType[position].setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.button_cirde_on));
        mLineGraphs[position].setVisibility(View.VISIBLE);
    }

    private String getGraphData(String stringExtra){
        JSONObject allData = new JSONObject();
        try {

            JSONArray arr_roasting = new JSONArray();
            allData.put("profileUid", mStrProfileUid);
            allData.put("profileType", mStrProfileType);
            for (int i=0 ; i<mHistoryData_Roast.size() ; i++) {
                JSONObject data = new JSONObject();
                data.put("x", mHistoryData_Roast.get(i).getX());
                data.put("y", mHistoryData_Roast.get(i).getY());
                arr_roasting.put(data);
            }
            JSONArray arr_ror = new JSONArray();
            for (int i=0 ; i<mHistoryData_ROR.size() ; i++) {
                JSONObject data = new JSONObject();
                data.put("x", mHistoryData_ROR.get(i).getX());
                data.put("y", mHistoryData_ROR.get(i).getY());
                arr_ror.put(data);
            }
            JSONArray arr_ad = new JSONArray();
            for (int i=0 ; i<mHistoryData_AD.size() ; i++) {
                JSONObject data = new JSONObject();
                data.put("x", mHistoryData_AD.get(i).getX());
                data.put("y", mHistoryData_AD.get(i).getY());
                arr_ad.put(data);
            }
            allData.put("name", mStrProfileName);
            allData.put("saveDataName", stringExtra);
            allData.put("roasting", arr_roasting);
            allData.put("ror", arr_ror);
            allData.put("ad", arr_ad);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("where", "allData : " + allData.toString());
        return allData.toString();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        Log.d("where", e.toString());
//        Log.d("where", "low: " + mLineGraphs.getLowestVisibleX() + ", high: " + mLineGraphs.getHighestVisibleX());
//        Log.d("where", "xMin: " + mLineGraphs.getXChartMin() + ", xMax: " + mLineGraphs.getXChartMax() + ", yMin: " + mLineGraphs.getYChartMin() + ", yMax: " + mLineGraphs.getYChartMax());

    }

    @Override
    public void onNothingSelected() {
        Log.d("where","onNothingSelected");
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        if (progress < 10 || progress > 20) {
            seekBar.setProgress(10);
            return;
        }
        for (int i=0 ; i<FireSeekbar.length ; i++) {
            if (seekBar.getId() == FireSeekbar[i].getId()){
                if (progress < 10){
                    FireSeekbarValue[i].setText("50%");
                } else {
                    progress = progress * 5;
                    FireSeekbarValue[i].setText("" + progress + "%");
//                mByteProfileData[i+5] = getByteToArray(hexStringToByteArray(Integer.toHexString(progress)));

                    String test = Integer.toHexString(getRoastingSectionTime(progress));

                    if (test.length() == 1 || test.length() == 3 ){
                        Log.d("where", "tes before : " +test );
                        test = "0"+test;
                    }
                    Log.d("where", "test : " +test );
                    byte[] temp = hexStringToByteArray(test);
                    mByteProfileData[i + 6] = temp[0];


                    mByteProfileData[13] = setParityBit(mByteProfileData);
                }
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void getDataToDevice(byte[] bytes){


        int operation = Integer.parseInt(String.format("%02x ", bytes[20]&0xff).replaceAll(" ", ""), 16);
        if (operation == 0) {
            mBoolReadyRoasting = true;
        } else if (operation == 1) {
            if (mBoolCheckConnection == false){
                mBoolCheckConnection = true;
                sendDataClickListener.isViewTransparent(true);
                mIntTotalTime = 0;
                timeHandler.sendEmptyMessage(0);
                api_UpdateMemberUseCount();
                mCurrentRoastingCount++;

                ForceStart.setVisibility(View.GONE);
                ForceTempReset.setVisibility(View.VISIBLE);
                ForceRestart.setVisibility(View.GONE);
            }

            String targetTemperature = String.format("%02x ", bytes[4] & 0xff) + String.format("%02x ", bytes[5] & 0xff);
//            mTvTargetTemp.setText(strTargetTemp.substring(0,3) + "." +strTargetTemp.substring(3,4) + "℃");
            float f_targetTemperature = (float)(Integer.parseInt(targetTemperature.replaceAll(" ", ""), 16)) / 10 ;
            Log.d("where", "f_targetTemperature1 : " + temperatureFormat.format(f_targetTemperature));
            mTvTargetTemp.setText(temperatureFormat.format(f_targetTemperature));

            String currentTemperature = String.format("%02x ", bytes[6] & 0xff) + String.format("%02x ", bytes[7] & 0xff);
            Log.d("where", "currentTemperature : " + Integer.parseInt(currentTemperature.replaceAll(" ", ""), 16));
//            String test = strCurTargetTemp.substring(0, strCurTargetTemp.length()-1) + "." + strCurTargetTemp.substring(strCurTargetTemp.length()-1, strCurTargetTemp.length());

            float f_currentTemperature = (float)Integer.parseInt(currentTemperature.replaceAll(" ", ""), 16) / 10 ;
//            Log.d("where", "f_currentTemperature : " + temperatureFormat.format(f_currentTemperature));
            mTvCurrentTemp.setText(temperatureFormat.format(f_currentTemperature));
//            String currentTemperatureValue = temperatureFormat.format(Integer.parseInt(currentTemperature.replaceAll(" ", ""), 16));
            addRoastEntry(f_currentTemperature);


            String roastingStep = String.format("%02x ", bytes[10] & 0xff);
            int iStep = Integer.parseInt(roastingStep.replaceAll(" ", ""), 16);
            for (int i=0 ; i<4 ; i++) {
                mIvStep[i].setBackgroundResource(getResources().getIdentifier("btn_step_"+(i+1)+"_disable", "drawable", getActivity().getPackageName()));
            }
            if (mIntStep != iStep){
                Log.d("where", "iStep : " + iStep);
                setProgressBarEnable(iStep);
                mIntStep = iStep;
            }
            mIvStep[iStep].setBackgroundResource(getResources().getIdentifier("btn_step_"+(iStep+1)+"_enable", "drawable",  getActivity().getPackageName()));
            Log.d("where", "roastingStep : " + Integer.parseInt(roastingStep.replaceAll(" ", ""), 16));


            String roastingTime = String.format("%02x ", bytes[8] & 0xff) + String.format("%02x ", bytes[9] & 0xff);
            Log.d("where", "roastingTime : " + Integer.parseInt(roastingTime.replaceAll(" ", ""), 16));
            int totalTime = Integer.parseInt(roastingTime.replaceAll(" ", ""), 16);
            mTvRoastingTime.setText(""+sdf.format(totalTime/60)+" : "+sdf.format(totalTime%60));

            //ROR 값 체크
            if (totalTime%60 == 0 && iStep>0){//60초마다 이전 ROR값이 차이만큼 저장
                mIntROR_Value = f_currentTemperature - f_prefvTemperature;
                f_prefvTemperature = f_currentTemperature;
                Log.d("where", "f_currentTemperature : " + f_currentTemperature);
                Log.d("where", "mIntROR_Value2 : " + mIntROR_Value);
                if (mBoolCheckAddRorData == false) {
                    mBoolCheckAddRorData = true;
                } else {
                    Log.d("where", "mBoolCheckAddRorData : " + mIntROR_Value);
                    mTvROR.setText(temperatureFormat.format(mIntROR_Value));
                    addROREntry(mIntROR_Value);
                }
            }

            String AD_Value = String.format("%02x ", bytes[11] & 0xff) + String.format("%02x ", bytes[12] & 0xff);;
//            Log.d("where", "AD_Value : " + Integer.parseInt(AD_Value.replaceAll(" ", ""), 16));
            mTvAD.setText(String.valueOf(Integer.parseInt(AD_Value.replaceAll(" ", ""), 16)));
            addADEntry(Float.valueOf(String.valueOf(Integer.parseInt(AD_Value.replaceAll(" ", ""), 16))));

            String fan = String.format("%02x ", bytes[13] & 0xff);
            Log.d("where", "fan : " + Integer.parseInt(fan.replaceAll(" ", ""), 16));

            String step1 = String.format("%02x ", bytes[14] & 0xff);
            Log.d("where", "step1 : " + Integer.parseInt(step1.replaceAll(" ", ""), 16));
            Log.d("where", "step1-1 : " + getRoastingSectionPercent(Integer.parseInt(step1.replaceAll(" ", ""), 16)));

            String step2 = String.format("%02x ", bytes[15] & 0xff);
            Log.d("where", "step2 : " + getRoastingSectionPercent(Integer.parseInt(step2.replaceAll(" ", ""), 16)));

            String step3 = String.format("%02x ", bytes[16] & 0xff);
            Log.d("where", "step3 : " + Integer.parseInt(step3.replaceAll(" ", ""), 16));
            Log.d("where", "step3-1 : " + getRoastingSectionPercent(Integer.parseInt(step3.replaceAll(" ", ""), 16)));

            String step4 = String.format("%02x ", bytes[17] & 0xff);
            Log.d("where", "step4 : " + getRoastingSectionPercent(Integer.parseInt(step4.replaceAll(" ", ""), 16)));

            String totalCount = String.format("%02x ", bytes[18] & 0xff) + String.format("%02x ", bytes[19] & 0xff);;
            Log.d("where", "totalCount : " + Integer.parseInt(totalCount.replaceAll(" ", ""), 16));

//            byte[] stepValue = new byte[4];
//            for (int i=0 ; i<stepValue.length ; i++){
//                stepValue[i] = bytes[i+14];
//            }
//            setFireSeekbarValue(stepValue);
        } else if (operation == 2) {
            if(mBoolReadyRoasting == false){
                return;
            }
            mBoolReadyRoasting = false;

            mBoolCheckAddRorData = false;
            mBoolCheckConnection = false;

            ForceStart.setVisibility(View.GONE);
            ForceTempReset.setVisibility(View.GONE);
            ForceRestart.setVisibility(View.VISIBLE);

            mByteProfileData[12] = (byte) 0x00;//대기로 변경
            mByteProfileData[13] = setParityBit(mByteProfileData);
            if(timeHandler != null) {
                timeHandler.removeMessages(0);
            }

            sendDataClickListener.sendData(mByteProfileData);
            sendDataClickListener.isViewTransparent(false);

            Intent intent = new Intent(getActivity(), SaveDataActivity.class);
            startActivityForResult(intent, Constant.REQ_SAVE_DATA);
        }
        else {

        }
    }


    private int getRoastingSectionPercent(int time){
        int percent = 130 - (2*time);
        Log.d("where", "percent : " + percent +" | time : "+ time);
        return percent;
    }
    private int getRoastingSectionTime(int percent){
        int time = (130 - percent)/2;
        Log.d("where", "TIME : " + time + " | PERCENT : " + percent);
        return time;
    }

    private void setProgressBarEnable(int step){
        if (Constant.PROFILE_TYPE_BASIC.equals(mStrProfileType)) {
            for (int i = 0; i < FireSeekbar.length; i++) {
                FireSeekbar[i].setEnableSeekbar(mContext, false);
                FireSeekbar[i].setEnabled(false);
            }
        } else {
            for (int i = 0; i < FireSeekbar.length; i++) {
                if (i <= step) {
                    FireSeekbar[i].setEnableSeekbar(mContext, false);
                    FireSeekbar[i].setEnabled(false);
                } else {
                    FireSeekbar[i].setEnableSeekbar(mContext, true);
                    FireSeekbar[i].setEnabled(true);
                }
            }
        }
    }

    public void deliverProfileUID(int uid, String name, String profileType){//클릭된_uid값_전달해야_함) {

        mStrProfileUid = String.valueOf(uid);
        mStrProfileType = profileType;
        mStrProfileName = name;

        mTvProfileType.setText(profileType);
        mTvProfileName.setText(mStrProfileName);


        if (Constant.PROFILE_TYPE_BASIC.equals(profileType)) {
            for (int i = 0; i < FireSeekbar.length; i++) {
                FireSeekbar[i].setEnableSeekbar(mContext, false);
                FireSeekbar[i].setEnabled(false);
            }
        } else {
            FireSeekbar[0].setEnabled(true);
            FireSeekbar[1].setEnabled(true);
            FireSeekbar[2].setEnabled(true);
            FireSeekbar[3].setEnabled(true);
            FireSeekbar[4].setEnabled(true);
            FireSeekbar[0].setEnableSeekbar(mContext, true);
            FireSeekbar[1].setEnableSeekbar(mContext, true);
            FireSeekbar[2].setEnableSeekbar(mContext, true);
            FireSeekbar[3].setEnableSeekbar(mContext, true);
            FireSeekbar[4].setEnableSeekbar(mContext, true);
        }

        api_GetProfile();
    }

    public void setFireSeekbarValue(byte[] temp) {
        int value0 = 100;//60으로 고정
        FireSeekbar[4].setProgress(value0/5);
        FireSeekbarValue[4].setText(""+value0+"%");
        for (int i=0 ; i<temp.length ; i++){
            FireSeekbar[i].setProgress(getRoastingSectionPercent(temp[i])/5);
            FireSeekbarValue[i].setText(""+getRoastingSectionPercent(temp[i])+"%");
        }

        Log.d("where", "mByteProfileData 2 : " + mByteProfileData[5]);


//        int value1 = getRoastingSectionPercent(Integer.parseInt(step1.replaceAll(" ", ""), 16));
//        FireSeekbar[1].setProgress(value1/5);
//        FireSeekbarValue[1].setText(""+value1+"%");
//
//        String step2 = String.format("%02x ", temp[1] & 0xff);
//        int value2 = getRoastingSectionPercent(Integer.parseInt(step2.replaceAll(" ", ""), 16));
//        FireSeekbar[2].setProgress(value2/5);
//        FireSeekbarValue[2].setText(""+value2+"%");
//
//        String step3 = String.format("%02x ", temp[2] & 0xff);
//        int value3 = getRoastingSectionPercent(Integer.parseInt(step3.replaceAll(" ", ""), 16));
//        FireSeekbar[3].setProgress(value3/5);
//        FireSeekbarValue[3].setText(""+value3+"%");
//
//        String step4 = String.format("%02x ", temp[3] & 0xff);
//        int value4 = getRoastingSectionPercent(Integer.parseInt(step4.replaceAll(" ", ""), 16));
//        FireSeekbar[4].setProgress(value4/5);
//        FireSeekbarValue[4].setText(""+value4+"%");
    }

    public byte setParityBit(byte[] temp){
        int parityBit = Integer.parseInt(String.format("%02x ", temp[2]&0xff).replaceAll(" ", ""), 16);
        for (int i=2 ; i<12 ; i++ ){
//            Log.d("where", "i : " + i + " -> "+parityBit + "   |    " + Integer.parseInt(String.format("%02x ", temp[i+1]&0xff).replaceAll(" ", ""), 16));
            parityBit = parityBit ^ Integer.parseInt(String.format("%02x ", temp[i+1]&0xff).replaceAll(" ", ""), 16);
//            Log.d("where" , "tetst => " + parityBit + " = " +Integer.toHexString(parityBit) + " - " + String.format("0x%02x ", parityBit&0xff));
//            Log.d("where",  " ------------------------------------------------------------------ ");

        }

        return (byte)parityBit;
    }

    Handler timeHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            mIntTotalTime++;
            Log.d("time" , ""+sdf.format(mIntTotalTime/60)+" : "+sdf.format(mIntTotalTime%60));
            mTvTotalTime.setText(""+sdf.format(mIntTotalTime/60)+" : "+sdf.format(mIntTotalTime%60));
            timeHandler.sendEmptyMessageDelayed(0, 1000);
        }
    };

    public void initRoastFargment() {
        mIntTotalTime = 0;
        mIntROR_Value = 0;
        mBoolCheckAddRorData = false;
        mBoolCheckConnection = false;
        byte[] mByteProfileData = new byte[16];
        mCurrentRoastingCount = 0;

        ForceStart.setVisibility(View.VISIBLE);
        ForceTempReset.setVisibility(View.GONE);
        ForceRestart.setVisibility(View.GONE);

        for (int i=0 ; i<4 ; i++) {
            mIvStep[i].setBackgroundResource(getResources().getIdentifier("btn_step_"+(i+1)+"_disable", "drawable", getActivity().getPackageName()));
        }
        mTvRoastingTime.setText("00 : 00");
        mTvTotalTime.setText(""+sdf.format(mIntTotalTime/60)+" : "+sdf.format(mIntTotalTime%60));
        mTvAD.setText(String.valueOf(0));
        mTvTargetTemp.setText(String.valueOf(0));
        mTvCurrentTemp.setText(String.valueOf(0));

        //저장되는 데이터 초기화
        mHistoryData_Roast = new ArrayList<>();
        mHistoryData_ROR = new ArrayList<>();
        mHistoryData_AD = new ArrayList<>();


        initGraph();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Constant.REQ_SAVE_DATA) {
            if (resultCode == getActivity().RESULT_OK) {
                //TODO: SAVE DATA
                api_SetHistoryData(getGraphData(data.getStringExtra(Constant.EXTRA_SAVE_DATA_NAME)));
            }
        }
    }

    /**
     * Profile 상세
     */
    private void api_GetProfile(){
        //POST
        retrofit2.Call<ProfileDTO> data = API_Adapter.getInstance().getProfile(
                mStrProfileUid, String.valueOf(MyData.getInstance().getUid()), mStrProfileType
        );

        data.enqueue(new retrofit2.Callback<ProfileDTO>() {
            @Override
            public void onResponse(retrofit2.Call<ProfileDTO> call, retrofit2.Response<ProfileDTO> response) {
                // 성공여부
                if(response.isSuccessful()){
                    ProfileDTO data = response.body();
                    if(Constant.API_RESPONSE_SUCCESS == data.getResult()) {
                        mByteProfileData[0] = getByteToArray(hexStringToByteArray(data.getContent().getStartbit_0()));
                        mByteProfileData[1] = getByteToArray(hexStringToByteArray(data.getContent().getStartbit_1()));
                        mByteProfileData[2] = getByteToArray(hexStringToByteArray(data.getContent().getPz_2()));
                        mByteProfileData[3] = getByteToArray(hexStringToByteArray(data.getContent().getNyz_3()));
                        mByteProfileData[4] = getByteToArray(hexStringToByteArray(data.getContent().getGxyz_4()));
                        mByteProfileData[5] = getByteToArray(hexStringToByteArray(data.getContent().getGxyz_5()));
                        mByteProfileData[6] = getByteToArray(hexStringToByteArray(data.getContent().getAyz_6()));
                        mByteProfileData[7] = getByteToArray(hexStringToByteArray(data.getContent().getByz_7()));
                        mByteProfileData[8] = getByteToArray(hexStringToByteArray(data.getContent().getCyz_8()));
                        mByteProfileData[9] = getByteToArray(hexStringToByteArray(data.getContent().getDyz_9()));
                        mByteProfileData[10] = getByteToArray(hexStringToByteArray(data.getContent().getUwxyz_10()));
                        mByteProfileData[11] = getByteToArray(hexStringToByteArray(data.getContent().getRz_11()));
                        mByteProfileData[12] = getByteToArray(hexStringToByteArray(data.getContent().getRz_12()));
                        mByteProfileData[13] = getByteToArray(hexStringToByteArray(data.getContent().getXoR_13()));
                        mByteProfileData[14] = getByteToArray(hexStringToByteArray(data.getContent().getStopbit_14()));
                        mByteProfileData[15] = getByteToArray(hexStringToByteArray(data.getContent().getStopbit_15()));

                        Log.d("where", "mByteProfileData[5] 0 : " + mByteProfileData[5]);

                        byte[] stepValue = new byte[4];
                        for (int i=0 ; i<stepValue.length ; i++){
                            stepValue[i] = mByteProfileData[i+6];
                        }
                        setFireSeekbarValue(stepValue);
                        setParityBit(mByteProfileData);
                        String targetTemperature = String.format("%02x ", mByteProfileData[4] & 0xff) + String.format("%02x ", mByteProfileData[5] & 0xff);

                        float f_targetTemperature = (float)(Integer.parseInt(targetTemperature.replaceAll(" ", ""), 16)) / 10 ;
                        Log.d("where", "f_targetTemperature2 : " + temperatureFormat.format(f_targetTemperature));
                        mTvTargetTemp.setText(temperatureFormat.format(f_targetTemperature));



                    } else if(Constant.API_RESPONSE_ERROR_0 == data.getResult()){
                        Toast.makeText(mContext, data.getMessage(), Toast.LENGTH_SHORT).show();
                    }else if( Constant.API_RESPONSE_ERROR_1 == data.getResult()){
                        Toast.makeText(mContext, data.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                    }
                } else {
                    Log.d("where", "error :"+response.message());
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ProfileDTO> call, Throwable t) {
                Log.d("where", "실패 : " +t.toString());
            }
        });
    }


    /**
     * Data 저장
     */
    private void api_SetHistoryData(String jsonData){
        //POST
        retrofit2.Call<BaseDTO> data = API_Adapter.getInstance().setHistoryData(
                jsonData, String.valueOf(MyData.getInstance().getUid())
        );

        data.enqueue(new retrofit2.Callback<BaseDTO>() {
            @Override
            public void onResponse(retrofit2.Call<BaseDTO> call, retrofit2.Response<BaseDTO> response) {
                // 성공여부
                if(response.isSuccessful()){
                    BaseDTO data = response.body();
                    if(Constant.API_RESPONSE_SUCCESS == data.getResult()) {

                        byte[] stepValue = new byte[4];
                        for (int i=0 ; i<stepValue.length ; i++){
                            stepValue[i] = mByteProfileData[i+6];
                        }
                        setFireSeekbarValue(stepValue);
                        setParityBit(mByteProfileData);
                        Toast.makeText(getActivity(),getString(R.string.save_data_success),Toast.LENGTH_SHORT).show();
                    } else if(Constant.API_RESPONSE_ERROR_0 == data.getResult()){
                        Toast.makeText(mContext, data.getMessage(), Toast.LENGTH_SHORT).show();
                    }else if( Constant.API_RESPONSE_ERROR_1 == data.getResult()){
                        Toast.makeText(mContext, data.getMessage(), Toast.LENGTH_SHORT).show();
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
     * Use Count 증가
     */
    private void api_UpdateMemberUseCount(){
        //POST
        retrofit2.Call<BaseDTO> data = API_Adapter.getInstance().updateMemberUseCount(
                String.valueOf(MyData.getInstance().getUid()), String.valueOf(MyData.getInstance().getUseCount()+1)
        );

        data.enqueue(new retrofit2.Callback<BaseDTO>() {
            @Override
            public void onResponse(retrofit2.Call<BaseDTO> call, retrofit2.Response<BaseDTO> response) {
                // 성공여부
                if(response.isSuccessful()){
                    BaseDTO data = response.body();
                    if(0 == data.getResult()) {
                        MyData.getInstance().setUseCount(MyData.getInstance().getUseCount()+1);
                    } else if(Constant.API_RESPONSE_ERROR_0 == data.getResult()){
                        Toast.makeText(mContext, data.getMessage(), Toast.LENGTH_SHORT).show();
                    }else if( Constant.API_RESPONSE_ERROR_1 == data.getResult()){
                        Toast.makeText(mContext, data.getMessage(), Toast.LENGTH_SHORT).show();
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

    CustomDialog finishDlg;
    public void finishDialog(){
        finishDlg = new CustomDialog(mContext, getString(R.string.roasring_force_finish),
                new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishDlg.dismiss();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishDlg.dismiss();

                mBoolCheckAddRorData = false;
                mBoolCheckConnection = false;

                ForceStart.setVisibility(View.VISIBLE);
                ForceTempReset.setVisibility(View.GONE);
                ForceRestart.setVisibility(View.GONE);

                mByteProfileData[12] = (byte) 0x02;
                mByteProfileData[13] = setParityBit(mByteProfileData);
                if(timeHandler != null) {
                    timeHandler.removeMessages(0);
                }

                sendDataClickListener.sendData(mByteProfileData);
                sendDataClickListener.isViewTransparent(false);

            }
        }, getString(R.string.btn_cancel), getString(R.string.btn_finish));
        finishDlg.show();
    }

}

