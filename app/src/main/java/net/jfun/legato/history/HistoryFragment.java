package net.jfun.legato.history;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;

import net.jfun.legato.MyData;
import net.jfun.legato.R;
import net.jfun.legato.api.API_Adapter;
import net.jfun.legato.roast.temp.DataEntry;
import net.jfun.legato.util.Constant;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private final int TAB_COUNT = 4;
    private int COLOR_COUNT = 0; // 5개의 COLOR 값 중 체크 안된 값은 몇뻔째 인지 체크


    private Context mContext;
    private ListView mListView;
    private List<HistoryListApiDTO.ContentBean.DataListBean> mDataList;
//    private List<HistoryListGraphDTO> mBasicData;
    private HistoryListAdapter mAdapter;

    private int mSelectCount = 0;
    private TypedArray mColors;

    private boolean[] mBoolCheck = {false, false, false, false, false};

    // 그래프 관련
    private LineChart[] mChartRoasting;
    private XAxis[] mX_Roasting = new XAxis[3];
    private YAxis[] mY_Roasting = new YAxis[3];


    int mMemberUid, mProfileUid =0;
    private String mStrProfileType = null;
    private String mStrProfileName = null;
    private TextView mTvName;


    TestData[] mRoastData;//
    TestData[] mRorData;//
    TestData[] mAdData;//

    //그래프 데이터 관련
//    TestData[] mEntryData;

    public HistoryFragment() {
        Log.d("where", "HistoryFragment Constructor Empty");
    }
    public HistoryFragment(int memberUid, int uid, String name) {
        Log.d("where", "HistoryFragment");
        mMemberUid = MyData.getInstance().getUid();
        mProfileUid = uid;
        mStrProfileName = name;
    }

    public HistoryFragment newInstance() {
        Log.d("where", "newInstance");
//    public CoffeeFragment newInstance(int page) {
        HistoryFragment fragment = new HistoryFragment();

//        Bundle bundle = new Bundle();
//        bundle.putInt("page" , page);
//        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("where", "onCreate : " + mMemberUid);
        mContext = getActivity();

        Bundle bundle = getArguments();
        if (bundle != null){
            Log.d("where", "bundle이 있다.");
//            mStartPage = bundle.getInt("page");
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_history, container, false);

        mColors = mContext.getResources().obtainTypedArray(R.array.color_array);

        mListView = rootView.findViewById(R.id.roast_history_list);
        mTvName = rootView.findViewById(R.id.frag_history_name);
        mTvName.setText(mStrProfileName);

        mAdapter = new HistoryListAdapter(mContext);


        mListView.setOnItemClickListener(this);
        mListView.setAdapter(mAdapter);

        mChartRoasting = new LineChart[]{
                rootView.findViewById(R.id.frag_history_roasting_chart),
                rootView.findViewById(R.id.frag_history_ror_chart),
                rootView.findViewById(R.id.frag_history_ad_chart)
        };
        initGraph();

        mRoastData = new TestData[]{
            new TestData(),
            new TestData(),
            new TestData(),
            new TestData(),
            new TestData()
        };
        mRorData = new TestData[]{
            new TestData(),
            new TestData(),
            new TestData(),
            new TestData(),
            new TestData()
        };
        mAdData = new TestData[]{
            new TestData(),
            new TestData(),
            new TestData(),
            new TestData(),
            new TestData()
        };

        return rootView;
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
        }
    }


    private void initGraph(){

        Log.d("where", "initGraph");
        for (int i=0 ; i<mChartRoasting.length ; i++) {
            mChartRoasting[i].setBackgroundColor(Color.BLACK);
            mChartRoasting[i].getDescription().setEnabled(false);
            mChartRoasting[i].setDragEnabled(true);
            mChartRoasting[i].setScaleEnabled(true);
            mChartRoasting[i].setPinchZoom(true);
            mX_Roasting[i] = mChartRoasting[i].getXAxis();
            mY_Roasting[i] = mChartRoasting[i].getAxisLeft();

            mX_Roasting[i].setTextColor(Color.WHITE);
            mX_Roasting[i].setPosition(XAxis.XAxisPosition.BOTTOM);
            mX_Roasting[i].setDrawGridLines(true);

            mY_Roasting[i].setTextColor(Color.WHITE);
            mY_Roasting[i].setAxisMinimum(0f);
            mY_Roasting[i].setDrawGridLines(false);
        }

        //Y츅 Max 값 셋팅
        mY_Roasting[0].setAxisMaximum(240f);
        mY_Roasting[1].setAxisMaximum(30f);
        mY_Roasting[2].setAxisMaximum(80f);
        //그래프관련
//        mChartRoasting.setBackgroundColor(Color.BLACK);
//        mChartRoasting.getDescription().setEnabled(false);
//        mChartRoasting.setDragEnabled(true);
//        mChartRoasting.setScaleEnabled(true);
//        mChartRoasting.setPinchZoom(true);

//        mChartRoasting.animateX(1500);

        //x,y축
//        mX_Roasting = mChartRoasting.getXAxis();
//        mX_Roasting.setTextColor(Color.WHITE);
//        mX_Roasting.setPosition(XAxis.XAxisPosition.BOTTOM);
//        mX_Roasting.setDrawGridLines(true);
//
//        mY_Roasting = mChartRoasting.getAxisLeft();
//        mY_Roasting.setTextColor(Color.WHITE);
//        mY_Roasting.setAxisMinimum(0f);
//        mY_Roasting.setAxisMaximum(200f);
//        mY_Roasting.setDrawGridLines(false);


        setDummyGraphData1(0);

    }

    private void setDummyListData(HistoryListApiDTO member){

        for (int i=0 ; i<mDataList.size() ; i++) {
//            ArrayList<Entry>[] values1_roast = new ArrayList<>();
            ArrayList<Entry> values1_ror = new ArrayList<>();
            ArrayList<Entry> values1_ad = new ArrayList<>();

//            for (int j=0 ; j<mDataList.get(i).getRoasting().size() ; j++) {
//                values1_roast.add(new Entry(mDataList.get(i).getRoasting().get(j).getX(), mDataList.get(i).getRoasting().get(j).getY()));
//            }
            for (int j=0 ; j<mDataList.get(i).getRor().size() ; j++) {
                values1_ror.add(new Entry(mDataList.get(i).getRor().get(j).getX(), mDataList.get(i).getRor().get(j).getY()));
            }
            for (int j=0 ; j<mDataList.get(i).getAd().size() ; j++) {
                values1_ad.add(new Entry(mDataList.get(i).getAd().get(j).getX(), mDataList.get(i).getAd().get(j).getY()));
            }

//            mBasicData.add(new HistoryListGraphDTO(0, false, values1_roast, values1_ror, values1_ad));
        }

//        mBasicData = new ArrayList<>();

//        ArrayList<Entry> values1_roast = new ArrayList<>();
//        ArrayList<Entry> values1_ror = new ArrayList<>();
//        ArrayList<Entry> values1_ad = new ArrayList<>();
//        ArrayList<Entry> values2_roast = new ArrayList<>();
//        ArrayList<Entry> values2_ror = new ArrayList<>();
//        ArrayList<Entry> values2_ad = new ArrayList<>();
//        ArrayList<Entry> values3_roast = new ArrayList<>();
//        ArrayList<Entry> values3_ror = new ArrayList<>();
//        ArrayList<Entry> values3_ad = new ArrayList<>();
//
//        for (int i = 0; i < 100; i++) {
//            float val = (float) (Math.random() * 10) + (i*3);
//            float val2 = (float) (Math.random() * 5) + (i*2);
//            float val3 = (float) (Math.random() * 150);
//            float val4 = (float) (Math.random() * 50);
//
//            values1_roast.add(new Entry(i, val));
//            values1_ror.add(new Entry(i, val2));
//            values1_ad.add(new Entry(i, val3));
//
//            values2_roast.add(new Entry(i, val2));
//            values2_ror.add(new Entry(i, val4));
//            values2_ad.add(new Entry(i, val));
//
//            values3_roast.add(new Entry(i, val3));
//            values3_ror.add(new Entry(i, val));
//            values3_ad.add(new Entry(i, val4));
//
//
//        }

//mBasicData.add(new HistoryListGraphDTO("F01", "2021.02.05", "21:03", 0, false, values1_roast, values1_ror, values1_ad));
//TODO:
//        mAdapter.addAllData(mBasicData);
    }

    private void setDummyGraphData1(int colorIndex){

        ArrayList<Entry> values1 = new ArrayList<>();

        LineDataSet set1;

        for (int i=0 ; i<mChartRoasting.length ; i++ ){

            if (mChartRoasting[i].getData() != null &&
                    mChartRoasting[i].getData().getDataSetCount() > 0) {
                set1 = (LineDataSet) mChartRoasting[i].getData().getDataSetByIndex(0);
                set1.setValues(values1);
                mChartRoasting[i].getData().notifyDataChanged();
                mChartRoasting[i].notifyDataSetChanged();
            } else {
                final int idx = i;

                // create a dataset and give it a type
                set1 = new LineDataSet(values1, "Set 1");
                set1.setMode(LineDataSet.Mode.LINEAR);
                set1.setAxisDependency(YAxis.AxisDependency.LEFT);
                set1.setColor(mColors.getColor(colorIndex, 0));
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
                        return mY_Roasting[idx].getAxisMinimum();
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
                mChartRoasting[i].setData(data);

                // get the legend (only possible after setting data)
                Legend l = mChartRoasting[i].getLegend();
                l.setTextColor(Color.WHITE);

                // draw legend entries as lines
                l.setForm(Legend.LegendForm.LINE);
            }
        }
    }

    private void graphNewSet(){

    }

    private void refreshEntry(int position) {
        Log.d("where", "position : "  + position);

        ArrayList<Entry> values1_roast = new ArrayList<>();
//        ArrayList<Entry> values1_ror = new ArrayList<>();
//        ArrayList<Entry> values1_ad = new ArrayList<>();
//        for (int i = 0; i < mDataList.get(position).getRoasting().size(); i++) {
//            values1_roast.add(new Entry(mDataList.get(position).getRoasting().get(i).getX(), mDataList.get(position).getRoasting().get(i).getY()));
//        }
//        for (int i = 0; i < mDataList.get(position).getRor().size(); i++) {
//            values1_ror.add(new Entry(mDataList.get(position).getRor().get(i).getX(), mDataList.get(position).getRor().get(i).getY()));
//        }
//        for (int i = 0; i < mDataList.get(position).getAd().size(); i++) {
//            values1_ad.add(new Entry(mDataList.get(position).getAd().get(i).getX(), mDataList.get(position).getAd().get(i).getY()));
//        }



        LineDataSet[] sets_roast = new LineDataSet[5];
        LineDataSet[] sets_ror = new LineDataSet[5];
        LineDataSet[] sets_ad = new LineDataSet[5];

        LineData lineData_roast = new LineData();
        LineData lineData_ror = new LineData();
        LineData lineData_ad = new LineData();


        for (int i=0 ; i<5 ; i++){
            if (mDataList.get(position).isSelected() == true) {

            }
            sets_roast[i] = new LineDataSet(mRoastData[i].getEntry(), mDataList.get(position).getProfileRunDate());
            sets_roast[i].setMode(LineDataSet.Mode.LINEAR);
            sets_roast[i].setAxisDependency(YAxis.AxisDependency.LEFT);
            sets_roast[i].setColor(mColors.getColor(mRoastData[i].getIdx(), 0));
            sets_roast[i].setVisible(true);
            sets_roast[i].setValueTextSize(0f);
            sets_roast[i].setDrawCircles(false);
            sets_roast[i].setDrawValues(false);
            lineData_roast.addDataSet(sets_roast[i]);


            sets_ror[i] = new LineDataSet(mRorData[i].getEntry(), mDataList.get(position).getProfileRunDate());
            sets_ror[i].setMode(LineDataSet.Mode.LINEAR);
            sets_ror[i].setAxisDependency(YAxis.AxisDependency.LEFT);
            sets_ror[i].setColor(mColors.getColor(mRorData[i].getIdx(), 0));
            sets_ror[i].setVisible(true);
            sets_ror[i].setValueTextSize(0f);
            sets_ror[i].setDrawCircles(false);
            sets_ror[i].setDrawValues(false);
            lineData_ror.addDataSet(sets_ror[i]);
//
//
            sets_ad[i] = new LineDataSet(mAdData[i].getEntry(), mDataList.get(position).getProfileRunDate());
            sets_ad[i].setMode(LineDataSet.Mode.LINEAR);
            sets_ad[i].setAxisDependency(YAxis.AxisDependency.LEFT);
            sets_ad[i].setColor(mColors.getColor(mAdData[i].getIdx(), 0));
            sets_ad[i].setVisible(true);
            sets_ad[i].setValueTextSize(0f);
            sets_ad[i].setDrawCircles(false);
            sets_ad[i].setDrawValues(false);
            lineData_ad.addDataSet(sets_ad[i]);
        }

//        for (int i=0 ; i<mDataList.get(position).getRoasting().size() ; i++) {
//            lineData_roast.addEntry(new Entry(mDataList.get(position).getRoasting().get(i).getX(), mDataList.get(position).getRoasting().get(i).getY()), 0);
//        }
//        for (int i=0 ; i<mDataList.get(position).getRor().size() ; i++) {
//            lineData_ror.addEntry(new Entry(mDataList.get(position).getRor().get(i).getX(), mDataList.get(position).getRor().get(i).getY()), 0);
//        }
//        for (int i=0 ; i<mDataList.get(position).getAd().size() ; i++) {
//            lineData_ad.addEntry(new Entry(mDataList.get(position).getAd().get(i).getX(), mDataList.get(position).getAd().get(i).getY()), 0);
//        }
        mChartRoasting[0].setData(lineData_roast);
        mChartRoasting[1].setData(lineData_ror);
        mChartRoasting[2].setData(lineData_ad);
//        lineData_roast.notifyDataChanged();
//        lineData_ror.notifyDataChanged();
//        lineData_ad.notifyDataChanged();
        for (int i=0 ; i<mChartRoasting.length ; i++){
            mChartRoasting[i].invalidate();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        HistoryListApiDTO.ContentBean.DataListBean data = (HistoryListApiDTO.ContentBean.DataListBean)adapterView.getAdapter().getItem(position);

//        api_GetHistoryData(String.valueOf(data.getHistoryUid()));

        if (mDataList.get(position).isSelected() == false) {
            if (mSelectCount >= 5) {
                Toast.makeText(mContext, getString(R.string.select_max_5), Toast.LENGTH_SHORT).show();
                return;
            }
        }

        setListRowBackground(position, mDataList.get(position).isSelected());

    }

    public ArrayList<Entry> getEntrys(int position, int type){
        ArrayList<Entry> values = new ArrayList<>();
        if (type == 0) {
            for (int i = 0; i < mDataList.get(position).getRoasting().size(); i++) {
                values.add(new Entry(mDataList.get(position).getRoasting().get(i).getX(), mDataList.get(position).getRoasting().get(i).getY()));
            }
        } else if (type == 1) {
            for (int i = 0; i < mDataList.get(position).getRor().size(); i++) {
                values.add(new Entry(mDataList.get(position).getRor().get(i).getX(), mDataList.get(position).getRor().get(i).getY()));
            }
        } else if (type == 2) {
            for (int i = 0; i < mDataList.get(position).getAd().size(); i++) {
                values.add(new Entry(mDataList.get(position).getAd().get(i).getX(), mDataList.get(position).getAd().get(i).getY()));
            }
        }
        return values;
    }

    public void setListRowBackground(int position, boolean value) {

        if (value == true) { // 선택 된 상태 -> 선택해제

            COLOR_COUNT = mDataList.get(position).getColorIndex();
            mSelectCount--;
            mRoastData[COLOR_COUNT] = new TestData();
            mRorData[COLOR_COUNT] = new TestData();
            mAdData[COLOR_COUNT] = new TestData();
            mDataList.get(position).setSelected(false);

            mBoolCheck[COLOR_COUNT] = false;
            mDataList.get(position).setColorIndex(-1);

        } else { // 선택 해제 된 상태 -> 선택
            mDataList.get(position).setSelected(true);
            for (int i=0 ; i<mBoolCheck.length ; i++) {
                if (mBoolCheck[i] == false) {
                    COLOR_COUNT = i;
                    break;
                }
            }
            mRoastData[COLOR_COUNT].setEntry(getEntrys(position, 0));
            mRoastData[COLOR_COUNT].setIdx(COLOR_COUNT);
            mRorData[COLOR_COUNT].setEntry(getEntrys(position, 1));
            mRorData[COLOR_COUNT].setIdx(COLOR_COUNT);
            mAdData[COLOR_COUNT].setEntry(getEntrys(position, 2));
            mAdData[COLOR_COUNT].setIdx(COLOR_COUNT);
            mDataList.get(position).setColorIndex(COLOR_COUNT);
            mSelectCount++;
            mBoolCheck[COLOR_COUNT] = true;
        }

        Log.d("where" , "COLOR_COUNT : " +COLOR_COUNT);

        refreshEntry(position);
        mAdapter.notifyDataSetChanged();
    }

    public void deliverProfileUIDAndName(int profileUid, String name, String profileType) {
        mProfileUid = profileUid;
        mStrProfileName = name;
        mStrProfileType = profileType;

        mTvName.setText(mStrProfileName);

        api_GetHistoryDataList();
    }

    private class TestData {
        int idx;
        ArrayList<Entry> entry;

        public int getIdx() {
            return idx;
        }

        public void setIdx(int idx) {
            this.idx = idx;
        }

        public ArrayList<Entry> getEntry() {
            return entry;
        }

        public void setEntry(ArrayList<Entry> entry) {
            this.entry = entry;
        }

        public TestData() {
        }
    }
    /**
     * History 목록
     */
    private void api_GetHistoryDataList(){

        //POST
        retrofit2.Call<HistoryListApiDTO> data = API_Adapter.getInstance().getHistoryDataList(
                String.valueOf(mProfileUid), mStrProfileType, String.valueOf(MyData.getInstance().getUid()));

        data.enqueue(new retrofit2.Callback<HistoryListApiDTO>() {
            @Override
            public void onResponse(retrofit2.Call<HistoryListApiDTO> call, retrofit2.Response<HistoryListApiDTO> response) {
                // 성공여부
                if(response.isSuccessful()){
                    HistoryListApiDTO member = response.body();
                    if(Constant.API_RESPONSE_SUCCESS == member.getResult()) {
                        mDataList = new ArrayList<>();
                        mDataList = member.getContent().getDataList();
                        mAdapter.addAllData(mDataList);
                        setDummyListData(member);
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
            public void onFailure(retrofit2.Call<HistoryListApiDTO> call, Throwable t) {
                Log.d("where", "실패 : " +t.toString());
            }
        });
    }

    public void initFargment() {
        mListView.smoothScrollToPosition(0);
        mDataList = null;
        mSelectCount = 0;
        for (int i = 0 ; i<mBoolCheck.length ; i++){
            mBoolCheck[i] = false;
        }

//        initGraph();
//        refreshEntry();
    }
}
