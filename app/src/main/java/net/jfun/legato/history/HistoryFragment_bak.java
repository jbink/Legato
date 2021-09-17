package net.jfun.legato.history;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;

import net.jfun.legato.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment_bak extends Fragment{
//public class HistoryFragment_bak extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private final int TAB_COUNT = 4;
    /**
        VISIBLE_COUNT : 현재 보여지고 있는 Tab이 몇번째 인지 체크.
     */
    private int VISIBLE_COUNT = 0;
    private int COLOR_COUNT = 0; // 5개의 COLOR 값 중 체크 안된 값은 몇뻔째 인지 체크


    private Context mContext;
    private ListView[] mListView;
    private TextView[] mTvTab;
    private List<HistoryListGraphDTO>[] mBasicData;
    private HistoryListAdapter mAdapter;

    private int mSelectCount = 0;
    private TypedArray mColors;

    private boolean[] mBoolCheck = {false, false, false, false, false};

    // 그래프 관련
    private LineChart mChartRoasting;
    private XAxis mX_Roasting;
    private YAxis mY_Roasting;

    //그래프 데이터 관련
    ArrayList<Entry>[] mEntryData;

    public HistoryFragment_bak newInstance() {
//    public CoffeeFragment newInstance(int page) {
        HistoryFragment_bak fragment = new HistoryFragment_bak();

//        Bundle bundle = new Bundle();
//        bundle.putInt("page" , page);
//        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

//        mColors = mContext.getResources().obtainTypedArray(R.array.color_array);
//
//        mTvTab = new TextView[]{
//                rootView.findViewById(R.id.roast_history_tab_0),
//                rootView.findViewById(R.id.roast_history_tab_1),
//                rootView.findViewById(R.id.roast_history_tab_2),
//                rootView.findViewById(R.id.roast_history_tab_3)
//        };
//        mListView = new ListView[]{
//                rootView.findViewById(R.id.roast_history_list_0),
//                rootView.findViewById(R.id.roast_history_list_1),
//                rootView.findViewById(R.id.roast_history_list_2),
//                rootView.findViewById(R.id.roast_history_list_3)
//        };
//
//        mAdapter = new HistoryListAdapter(mContext);
//
//        setDummyListData();
//        for (int i=0 ; i<TAB_COUNT ; i++) {
//            mTvTab[i].setOnClickListener(this);
//            mListView[i].setOnItemClickListener(this);
//            mListView[i].setAdapter(mAdapter);
//        }
//
//
//        mChartRoasting = rootView.findViewById(R.id.frag_history_roasting_chart);
//        initGraph();

        return rootView;
    }
//
//    @Override
//    public void onClick(View v){
//        switch (v.getId()) {
//            case R.id.roast_history_tab_0 :
//                setTabButtonAndListView(0);
//                mAdapter.addAllData(mBasicData[0]);
//                break;
//            case R.id.roast_history_tab_1 :
//                setTabButtonAndListView(1);
//                mAdapter.addAllData(mBasicData[1]);
//                break;
//            case R.id.roast_history_tab_2 :
//                setTabButtonAndListView(2);
//                mAdapter.addAllData(mBasicData[2]);
//                break;
//            case R.id.roast_history_tab_3 :
//                setTabButtonAndListView(3);
//                mAdapter.addAllData(mBasicData[3]);
//                break;
//        }
//    }
//
//    private void setTabButtonAndListView(int position) {
//        for (int i=0 ; i<TAB_COUNT ; i++) {
//            mTvTab[i].setTextColor(ContextCompat.getColor(mContext, R.color.COLOR_3B3B3B));
//            mTvTab[i].setBackground(ContextCompat.getDrawable(mContext, R.drawable.btn_tab_back));
//            mListView[i].setVisibility(View.GONE);
//        }
//        mTvTab[position].setTextColor(ContextCompat.getColor(mContext, R.color.COLOR_6B6B6B));
//        mTvTab[position].setBackground(ContextCompat.getDrawable(mContext, R.drawable.btn_tab_back_prs));
//        mListView[position].setVisibility(View.VISIBLE);
//
//        VISIBLE_COUNT = position;
//    }
//
//    private void initGraph(){
//        //그래프관련
//        mChartRoasting.setBackgroundColor(Color.BLACK);
//        mChartRoasting.getDescription().setEnabled(false);
//        mChartRoasting.setDragEnabled(true);
//        mChartRoasting.setScaleEnabled(true);
//        mChartRoasting.setPinchZoom(true);
//
////        mChartRoasting.animateX(1500);
//
//        //x,y축
//        mX_Roasting = mChartRoasting.getXAxis();
//        mX_Roasting.setTextColor(Color.WHITE);
//        mX_Roasting.setPosition(XAxis.XAxisPosition.BOTTOM);
//        mX_Roasting.setDrawGridLines(false);
//
//        mY_Roasting = mChartRoasting.getAxisLeft();
//        mY_Roasting.setTextColor(Color.WHITE);
//        mY_Roasting.setAxisMinimum(0f);
//        mY_Roasting.setAxisMaximum(200f);
//        mY_Roasting.setDrawGridLines(false);
//
//
//        setDummyGraphData1(0);
//
//
//        mEntryData = new ArrayList[]{
//                new ArrayList<Entry>(),
//                new ArrayList<Entry>(),
//                new ArrayList<Entry>(),
//                new ArrayList<Entry>(),
//                new ArrayList<Entry>()
//        };
//    }
//
//    private void setDummyListData(){
//        mBasicData = new ArrayList[]{
//                new ArrayList<HistoryListDTO>(),
//                new ArrayList<HistoryListDTO>(),
//                new ArrayList<HistoryListDTO>(),
//                new ArrayList<HistoryListDTO>()
//        };
////        mBasicData = new ArrayList<>();
//
//        ArrayList<Entry> values1 = new ArrayList<>();
//        ArrayList<Entry> values2 = new ArrayList<>();
//        ArrayList<Entry> values3 = new ArrayList<>();
//
//        for (int i = 0; i < 100; i++) {
//            float val = (float) (Math.random() * 10) + (i*3);
//            float val2 = (float) (Math.random() * 5) + (i*2);
//            float val3 = (float) (Math.random() * 150);
//            values1.add(new Entry(i, val));
//            values2.add(new Entry(i, val2));
//            values3.add(new Entry(i, val3));
//        }
//        mBasicData[0].add(new HistoryListDTO("F01", "2021.02.05", "21:03", 0, false, values1));
//        mBasicData[0].add(new HistoryListDTO("F01", "2021.02.04", "14:03", 0, false, values2));
//        mBasicData[0].add(new HistoryListDTO("F02", "2021.02.03", "11:18", 0, false, values3));
//        mBasicData[0].add(new HistoryListDTO("F03", "2021.02.01", "15:53", 0, false, values1));
//        mBasicData[0].add(new HistoryListDTO("F03", "2021.12.25", "19:55", 0, false, values1));
//        mBasicData[0].add(new HistoryListDTO("F04", "2021.11.25", "17:06", 0, false, values1));
//        mBasicData[0].add(new HistoryListDTO("F03", "2021.10.25", "11:59", 0, false, values1));
//        mBasicData[0].add(new HistoryListDTO("F03", "2021.09.07", "10:05", 0, false, values1));
//        mBasicData[0].add(new HistoryListDTO("F03", "2021.08.15", "05:26", 0, false, values1));
//        mBasicData[0].add(new HistoryListDTO("F03", "2021.08.05", "11:06", 0, false, values1));
//        mBasicData[0].add(new HistoryListDTO("F01", "2020.12.11", "09:05", 0, false, values1));
//        mBasicData[1].add(new HistoryListDTO("F03", "2021.12.25", "11:06", 0, false, values1));
//        mBasicData[1].add(new HistoryListDTO("F01", "2020.12.11", "09:05", 0, false, values1));
//        mBasicData[2].add(new HistoryListDTO("PRO_NAME", "2020.12.11", "09:05", 0, false, values1));
////
//        mAdapter.addAllData(mBasicData[0]);
//    }
//
//    private void setDummyGraphData1(int colorIndex){
//        ArrayList<Entry> values1 = new ArrayList<>();
//
//
//        LineDataSet set1;
//
//        if (mChartRoasting.getData() != null &&
//                mChartRoasting.getData().getDataSetCount() > 0) {
//            set1 = (LineDataSet) mChartRoasting.getData().getDataSetByIndex(0);
//            set1.setValues(values1);
//            mChartRoasting.getData().notifyDataChanged();
//            mChartRoasting.notifyDataSetChanged();
//        } else {
//            // create a dataset and give it a type
//            set1 = new LineDataSet(values1, "DataSet 1");
//            set1.setMode(LineDataSet.Mode.LINEAR);
//            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
//            set1.setColor(mColors.getColor(colorIndex, 0));
//            set1.setVisible(true);
//            set1.setValueTextSize(0f);
//            set1.setDrawCircles(false);
//            set1.setDrawValues(false);
////            set1.setCircleColor(Color.RED);
////            set1.setValueTextColor(Color.RED);
////            set1.setFillColor(ColorTemplate.getHoloBlue());
////            set1.setHighLightColor(Color.rgb(244, 117, 117));
////            set1.setLineWidth(1f);
////            set1.setCircleRadius(6f);
//            set1.setDrawCircleHole(false);
////            set1.setCircleHoleRadius(5f);
////            set1.setCircleHoleColor(Color.BLACK);
//            set1.setDrawFilled(true);
//            set1.setFillFormatter(new IFillFormatter() {
//                @Override
//                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
//                    return mY_Roasting.getAxisMinimum();
//                }
//            });
//            if (Utils.getSDKInt() >= 18) {
//                // drawables only supported on api level 18 and above
//                Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.graph_gradation);
//                set1.setFillDrawable(drawable);
//            } else {
//                set1.setFillColor(Color.BLACK);
//            }
//
//
//            // create a data object with the data sets
//            LineData data = new LineData(set1);
//            data.setValueTextColor(Color.WHITE);
//            data.setValueTextSize(9f);
//
//            // set data
//            mChartRoasting.setData(data);
//
//            // get the legend (only possible after setting data)
//            Legend l = mChartRoasting.getLegend();
//            l.setTextColor(Color.WHITE);
//
//            // draw legend entries as lines
//            l.setForm(Legend.LegendForm.LINE);
//
//        }
//    }
//
//    private void setDummyGraphData(int colorIndex){
//
//        mEntryData = new ArrayList[]{
//                new ArrayList<Entry>(),
//                new ArrayList<Entry>(),
//                new ArrayList<Entry>(),
//                new ArrayList<Entry>(),
//                new ArrayList<Entry>()
//        };
//
//
//        /*
//
//        if (data.isSelected() == true) {
//            holder.background.setBackgroundColor(colors.getColor(data.getColor(), 0));
//        } else {
//            holder.background.setBackgroundColor(ContextCompat.getColor(mContext, R.color.COLOR_141414));
//        }
//
//         */
//        ArrayList<Entry> values1 = new ArrayList<>();
//
//        for (int i = 0; i < 100; i++) {
//            float val = (float) (Math.random() * 10) + (i*2);
//            values1.add(new Entry(i, val));
//        }
//
//        LineDataSet set1;
//
//        if (mChartRoasting.getData() != null &&
//                mChartRoasting.getData().getDataSetCount() > 0) {
//            Log.d("where", "카운트 : "+mChartRoasting.getData().getDataSetCount());
//            set1 = (LineDataSet) mChartRoasting.getData().getDataSetByIndex(0);
//            set1.setValues(values1);
//            mChartRoasting.getData().notifyDataChanged();
//            mChartRoasting.notifyDataSetChanged();
//        } else {
//            Log.d("where", "카운트 : 0");
//            // create a dataset and give it a type
//            set1 = new LineDataSet(values1, "DataSet 1");
//            set1.setMode(LineDataSet.Mode.LINEAR);
//            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
//            set1.setColor(mColors.getColor(colorIndex, 0));
//            set1.setVisible(true);
//            set1.setValueTextSize(0f);
//            set1.setDrawCircles(false);
//            set1.setDrawValues(false);
////            set1.setCircleColor(Color.RED);
////            set1.setValueTextColor(Color.RED);
////            set1.setFillColor(ColorTemplate.getHoloBlue());
////            set1.setHighLightColor(Color.rgb(244, 117, 117));
////            set1.setLineWidth(1f);
////            set1.setCircleRadius(6f);
//            set1.setDrawCircleHole(false);
////            set1.setCircleHoleRadius(5f);
////            set1.setCircleHoleColor(Color.BLACK);
//            set1.setDrawFilled(true);
//            set1.setFillFormatter(new IFillFormatter() {
//                @Override
//                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
//                    return mY_Roasting.getAxisMinimum();
//                }
//            });
//            if (Utils.getSDKInt() >= 18) {
//                // drawables only supported on api level 18 and above
//                Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.graph_gradation);
//                set1.setFillDrawable(drawable);
//            } else {
//                set1.setFillColor(Color.BLACK);
//            }
//
//
//            // create a data object with the data sets
//            LineData data = new LineData(set1);
//            data.setValueTextColor(Color.WHITE);
//            data.setValueTextSize(9f);
//
//            // set data
//            mChartRoasting.setData(data);
//
//            data.notifyDataChanged();
//            mChartRoasting.notifyDataSetChanged();
//
//            // get the legend (only possible after setting data)
//            Legend l = mChartRoasting.getLegend();
//            l.setTextColor(Color.WHITE);
//
//            // draw legend entries as lines
//            l.setForm(Legend.LegendForm.LINE);
//
//        }
//    }
//
//
//    private void newEntry(int position) {
//
//        LineDataSet[] sets = new LineDataSet[5];
//        LineData lineData = new LineData();
//        for (int i=0 ; i<5 ; i++){
//            if (mBasicData[VISIBLE_COUNT].get(i).isSelected() == true) {
//                Log.d("where", "Date : " + mBasicData[VISIBLE_COUNT].get(position).getDate());
//                sets[i] = new LineDataSet(mBasicData[VISIBLE_COUNT].get(position).getEntry(), mBasicData[VISIBLE_COUNT].get(position).getDate());
//                sets[i].setMode(LineDataSet.Mode.LINEAR);
//                sets[i].setAxisDependency(YAxis.AxisDependency.LEFT);
//                sets[i].setColor(mColors.getColor(position-1, 0));
//                sets[i].setVisible(true);
//                sets[i].setValueTextSize(0f);
//                sets[i].setDrawCircles(false);
//                sets[i].setDrawValues(false);
//
//                lineData.addDataSet(sets[i]);
//            }
//        }
//
////        LineDataSet set1;
////        set1 = new LineDataSet(mBasicData[VISIBLE_COUNT].get(position).getEntry(), mBasicData[VISIBLE_COUNT].get(position).getDate());
////        set1.setMode(LineDataSet.Mode.LINEAR);
////        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
////        set1.setColor(mColors.getColor(position, 0));
////        set1.setVisible(true);
////        set1.setValueTextSize(0f);
////        set1.setDrawCircles(false);
////        set1.setDrawValues(false);
////
////        LineData lineData = new LineData(set1);
//
//        mChartRoasting.setData(lineData);
//        mChartRoasting.invalidate();
//    }
//    private void refreshEntry() {
//
//        Log.d("where", "mSelectCount : "+mSelectCount);
//        LineDataSet[] sets = new LineDataSet[5];
//        LineData lineData = new LineData();
//        for (int i=0 ; i<mEntryData.length ; i++){
//            if (mEntryData[i].size() > 0 ) {
//                sets[i] = new LineDataSet(mEntryData[i], "Data Set " + i);
//                sets[i].setMode(LineDataSet.Mode.LINEAR);
//                sets[i].setAxisDependency(YAxis.AxisDependency.LEFT);
//                sets[i].setColor(mColors.getColor(i, 0));
//                sets[i].setVisible(true);
//                sets[i].setValueTextSize(0f);
//                sets[i].setDrawCircles(false);
//                sets[i].setDrawValues(false);
//
//                lineData.addDataSet(sets[i]);
//            }
//        }
//
//        mChartRoasting.setData(lineData);
//        mChartRoasting.invalidate();
//    }
//
//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//
//        if (mBasicData[VISIBLE_COUNT].get(position).isSelected() == false) {
//            if (mSelectCount >= 5) {
//                Toast.makeText(mContext, "최대 5개까지 선택 가능합니다.", Toast.LENGTH_SHORT).show();
//                return;
//            }
//        }
//        for (int i=0 ; i<mBoolCheck.length ; i++) {
//            if (mBoolCheck[i] == false) {
//                COLOR_COUNT = i;
//                break;
//            }
//        }
//        setListRowBackground(position, !mBasicData[VISIBLE_COUNT].get(position).isSelected());
//    }
//
//    public void setListRowBackground(int position, boolean value) {
//        mBasicData[VISIBLE_COUNT].get(position).setSelected(value);
//
//        if (value == true) { // 선택 됨
//            mEntryData[mSelectCount] = mBasicData[VISIBLE_COUNT].get(position).getEntry();
//            mSelectCount++;
//            mBasicData[VISIBLE_COUNT].get(position).setColorIndex(COLOR_COUNT);
//            mBoolCheck[mBasicData[VISIBLE_COUNT].get(position).getColorIndex()] = true;
//
//
//        } else { // 선택 해제 됨
//            mSelectCount--;
//            mEntryData[mSelectCount] = new ArrayList<>();
//            mBoolCheck[mBasicData[VISIBLE_COUNT].get(position).getColorIndex()] = false;
//            mBasicData[VISIBLE_COUNT].get(position).setColorIndex(-1);
//
//
//        }
//        refreshEntry();
//        mAdapter.notifyDataSetChanged();
//    }
}
