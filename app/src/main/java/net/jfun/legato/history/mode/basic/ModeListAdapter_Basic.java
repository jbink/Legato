package net.jfun.legato.history.mode.basic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.jfun.legato.R;
import net.jfun.legato.history.mode.ModeListDTO;
import net.jfun.legato.util.Constant;

import java.util.ArrayList;
import java.util.List;


public class ModeListAdapter_Basic extends BaseAdapter {

    Context mContext;
    List<ModeListDTO.ContentBean.BasicBean> mRowList;
    LayoutInflater mInflater;

//    private boolean mBoolShowEntireData = true;

    public ModeListAdapter_Basic(Context context) {
        this.mContext = context;
        this.mRowList = new ArrayList<ModeListDTO.ContentBean.BasicBean>();
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return mRowList.size();
    }

    @Override
    public ModeListDTO.ContentBean.BasicBean getItem(int i) {
        return mRowList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void addAllData(List<ModeListDTO.ContentBean.BasicBean> data){
        mRowList = new ArrayList<ModeListDTO.ContentBean.BasicBean>();
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

        final ModeListDTO.ContentBean.BasicBean data = mRowList.get(position);
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.row_mode_list_basic, viewGroup, false);
            holder = new ViewHolder();

            holder.term = (TextView) convertView.findViewById(R.id.row_mode_basic_term);
            holder.name = (TextView) convertView.findViewById(R.id.row_mode_basic_name);
//            holder.order_code = (TextView)convertView.findViewById(R.id.row_order_txt_order_code);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        if (data.getTerm().equals(Constant.PROFILE_TERM_SHORTTIME)){
            holder.term.setText("SHORT TIME");
        } else if (data.getTerm().equals(Constant.PROFILE_TERM_LONGTIME)){
            holder.term.setText("LONG TIME");
        } else if (data.getTerm().equals(Constant.PROFILE_TERM_EXTREME_LONGTIME)){
            holder.term.setText("EXTREME\nLONG TIME");
        }
//        if (data.getProfileType() == Constant.PROFILE_TYPE_BASIC){
//            holder.term.setText("STANDARD");
//        } else if (data.getTerm() == Constant.PROFILE_TERM_SHORTTIME){
//            holder.term.setText("SHOTR TIME");
//        } else if (data.getTerm() == Constant.PROFILE_TERM_LONGTIME){
//            holder.term.setText("LONG TIME");
//        }
        holder.name.setText(data.getProfileName());

        return convertView;
    }

    private class ViewHolder{
        private TextView term;
        private TextView name;
        private TextView time;
    }
}
