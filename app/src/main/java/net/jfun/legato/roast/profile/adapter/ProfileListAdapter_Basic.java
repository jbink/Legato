package net.jfun.legato.roast.profile.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.vision.text.Line;

import net.jfun.legato.R;
import net.jfun.legato.history.mode.ModeListDTO;
import net.jfun.legato.roast.profile.ProfileListFragment;
import net.jfun.legato.tutorial.TutorialDetailActivity;
import net.jfun.legato.util.Constant;

import java.util.ArrayList;
import java.util.List;


public class ProfileListAdapter_Basic extends BaseAdapter {

    Context mContext;
    List<ModeListDTO.ContentBean.BasicBean> mRowList;
    LayoutInflater mInflater;
    ProfileListFragment profileListFragment;

//    private boolean mBoolShowEntireData = true;

    public ProfileListAdapter_Basic(Context context, ProfileListFragment profileListFragment) {
        this.mContext = context;
        this.mRowList = new ArrayList<ModeListDTO.ContentBean.BasicBean>();
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.profileListFragment = profileListFragment;
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
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;

        final ModeListDTO.ContentBean.BasicBean data = mRowList.get(position);
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.row_profile_list_basic, viewGroup, false);
            holder = new ViewHolder();

            holder.copy = (ImageButton) convertView.findViewById(R.id.row_profile_basic_btn_left);
            holder.info = (ImageButton) convertView.findViewById(R.id.row_profile_basic_btn_info);
            holder.term = (TextView) convertView.findViewById(R.id.row_profile_basic_term);
            holder.name = (TextView) convertView.findViewById(R.id.row_profile_basic_name);
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
        holder.copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileListFragment.copyList(data.getUid(), Constant.PROFILE_TYPE_BASIC);
            }
        });
        holder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TutorialDetailActivity.class);
                intent.putExtra(Constant.EXTRA_TUTORIAL, Constant.EXTRA_TUTORIAL_BASIC_INFO);
                intent.putExtra(Constant.EXTRA_TUTORIAL_BASIC_INFO_I, (position+1));
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    private class ViewHolder{
        private TextView term;
        private TextView name;
        private TextView time;
        private ImageButton copy;
        private ImageButton info;
    }
}
