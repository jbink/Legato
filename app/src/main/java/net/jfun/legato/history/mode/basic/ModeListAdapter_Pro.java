package net.jfun.legato.history.mode.basic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.jfun.legato.R;
import net.jfun.legato.history.mode.ModeListDTO;

import java.util.ArrayList;
import java.util.List;


public class ModeListAdapter_Pro extends BaseAdapter {

    Context mContext;
    List<ModeListDTO.ContentBean.ProBean> mRowList;
    LayoutInflater mInflater;


    public ModeListAdapter_Pro(Context context) {
        this.mContext = context;
        this.mRowList = new ArrayList<ModeListDTO.ContentBean.ProBean>();
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return mRowList.size();
    }

    @Override
    public ModeListDTO.ContentBean.ProBean getItem(int i) {
        return mRowList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void addAllData(List<ModeListDTO.ContentBean.ProBean> data){
        mRowList = new ArrayList<ModeListDTO.ContentBean.ProBean>();
        mRowList = data;
        notifyDataSetChanged();
    }

//    public void setChangeBackground(int position){
//        notifyDataSetChanged();
//    }
//    public void addOneData(ModeListDTO.ContentBean data){
//        mRowList.add(mRowList.size(), data);
//        notifyDataSetChanged();
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;

        final ModeListDTO.ContentBean.ProBean data = mRowList.get(position);
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.row_mode_list_pro, viewGroup, false);
            holder = new ViewHolder();

            holder.name = (TextView) convertView.findViewById(R.id.row_mode_pro_name);
            holder.temperature = (TextView) convertView.findViewById(R.id.row_mode_pro_temperature);
            holder.coffee = (TextView) convertView.findViewById(R.id.row_mode_pro_coffee);
            holder.date = (TextView) convertView.findViewById(R.id.row_mode_pro_date);
            holder.time = (TextView) convertView.findViewById(R.id.row_mode_pro_time);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

//        if (data.getProfileType() == Constant.PROFILE_TYPE_BASIC){
//            holder.term.setText("STANDARD");
//        } else if (data.getTerm() == Constant.PROFILE_TERM_SHORTTIME){
//            holder.term.setText("SHOTR TIME");
//        } else if (data.getTerm() == Constant.PROFILE_TERM_LONGTIME){
//            holder.term.setText("LONG TIME");
//        }
        holder.name.setText(data.getProfileName());
        holder.coffee.setText(data.getBeanName());
        holder.temperature.setText(String.valueOf(data.getTargetTemperature()));
        holder.date.setText(data.getCreateDateTime());
        holder.time.setVisibility(View.GONE);

        return convertView;
    }

    private class ViewHolder{
        private TextView name;
        private TextView coffee;
        private TextView temperature;
        private TextView date;
        private TextView time;
    }
}
