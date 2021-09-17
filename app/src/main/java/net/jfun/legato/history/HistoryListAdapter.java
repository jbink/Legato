package net.jfun.legato.history;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import net.jfun.legato.R;

import java.util.ArrayList;
import java.util.List;


public class HistoryListAdapter extends BaseAdapter {

    Context mContext;
    List<HistoryListApiDTO.ContentBean.DataListBean> mRowList;
    LayoutInflater mInflater;

    private TypedArray colors;

//    private boolean mBoolShowEntireData = true;


    public HistoryListAdapter(Context context) {
        this.mContext = context;
        this.mRowList = new ArrayList<HistoryListApiDTO.ContentBean.DataListBean>();
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Color arrays
        colors = mContext.getResources().obtainTypedArray(R.array.color_array);

    }


    @Override
    public int getCount() {
        return mRowList.size();
    }

    @Override
    public HistoryListApiDTO.ContentBean.DataListBean getItem(int i) {
        return mRowList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void addAllData(List<HistoryListApiDTO.ContentBean.DataListBean> data){
        mRowList = new ArrayList<HistoryListApiDTO.ContentBean.DataListBean>();
        mRowList.addAll(data);

        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;

        final HistoryListApiDTO.ContentBean.DataListBean data = mRowList.get(position);
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.row_history_list, viewGroup, false);
            holder = new ViewHolder();

            holder.background = (RelativeLayout) convertView.findViewById(R.id.row_history_background);
            holder.uid = (TextView) convertView.findViewById(R.id.row_history_uid);
            holder.date = (TextView) convertView.findViewById(R.id.row_history_date);
            holder.time = (TextView) convertView.findViewById(R.id.row_history_time);
//            holder.order_code = (TextView)convertView.findViewById(R.id.row_order_txt_order_code);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.time.setText(data.getProfileRunTime());
        holder.date.setText(data.getProfileRunDate());
        holder.uid.setText(String.valueOf(data.getSaveDataName()));
//        holder.time.setText(data.getTime());

        if (data.isSelected() == true) {
            holder.background.setBackgroundColor(colors.getColor(data.getColorIndex(), 0));
        } else {
            holder.background.setBackgroundColor(ContextCompat.getColor(mContext, R.color.COLOR_141414));
        }


        return convertView;
    }

    private class ViewHolder{
        private RelativeLayout background;
        private TextView uid;
        private TextView date;
        private TextView time;
    }
}
