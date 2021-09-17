package net.jfun.legato.roast.profile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import net.jfun.legato.R;
import net.jfun.legato.history.mode.ModeListDTO;
import net.jfun.legato.roast.profile.ProfileListFragment;
import net.jfun.legato.util.Constant;

import java.util.ArrayList;
import java.util.List;


public class ProfileListAdapter_Select extends BaseAdapter {

    Context mContext;
    List<ModeListDTO.ContentBean.SelectBean> mRowList;
    LayoutInflater mInflater;
    ProfileListFragment profileListFragment;

//    private boolean mBoolShowEntireData = true;

    public ProfileListAdapter_Select(Context context, ProfileListFragment profileListFragment) {
        this.mContext = context;
        this.mRowList = new ArrayList<ModeListDTO.ContentBean.SelectBean>();
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.profileListFragment = profileListFragment;
    }


    @Override
    public int getCount() {
        return mRowList.size();
    }

    @Override
    public ModeListDTO.ContentBean.SelectBean getItem(int i) {
        return mRowList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void addAllData(List<ModeListDTO.ContentBean.SelectBean> data){
        mRowList = new ArrayList<ModeListDTO.ContentBean.SelectBean>();
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
        final ViewHolder holder;

        final ModeListDTO.ContentBean.SelectBean data = mRowList.get(position);
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.row_profile_list_pro, viewGroup, false);
            holder = new ViewHolder();

            holder.name = (TextView) convertView.findViewById(R.id.row_profile_pro_name);
            holder.temperature = (TextView) convertView.findViewById(R.id.row_profile_pro_temperature);
            holder.coffee = (TextView) convertView.findViewById(R.id.row_profile_pro_coffee);
            holder.date = (TextView) convertView.findViewById(R.id.row_profile_pro_date);
            holder.time = (TextView) convertView.findViewById(R.id.row_profile_pro_time);

            holder.star = (ImageButton) convertView.findViewById(R.id.row_profile_btn_star);
            holder.pencil = (ImageButton) convertView.findViewById(R.id.row_profile_btn_pencil);
            holder.trash = (ImageButton) convertView.findViewById(R.id.row_profile_btn_trash);

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
        if (data.isIsWish() == false ) {
            holder.star.setBackground(ContextCompat.getDrawable(mContext, R.drawable.btn_star));
        } else {
            holder.star.setBackground(ContextCompat.getDrawable(mContext, R.drawable.btn_star_solid));
        }
        holder.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.star.setBackground(ContextCompat.getDrawable(mContext, R.drawable.btn_star_solid));
                profileListFragment.setSelectList(data.getUid(), !data.isIsWish(), Constant.PROFILE_TYPE_PRO);
            }
        });
        holder.trash.setVisibility(View.GONE);
        holder.pencil.setVisibility(View.GONE);
//        holder.trash.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                profileListFragment.deleteList(data.getUid(), Constant.PROFILE_TYPE_PRO);
//            }
//        });
        return convertView;
    }

    private class ViewHolder{
        private TextView name;
        private TextView coffee;
        private TextView temperature;
        private TextView date;
        private TextView time;
        private ImageButton trash, star, pencil;
    }
}
