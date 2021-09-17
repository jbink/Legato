package net.jfun.legato.tutorial;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.smarteist.autoimageslider.SliderViewAdapter;

import net.jfun.legato.R;

import java.util.ArrayList;
import java.util.List;


public class BannerSliderAdapter extends SliderViewAdapter<BannerSliderAdapter.InnerDTO> {

    private Activity mContext;
    private List<String> mSliderItems = new ArrayList<>();

    public BannerSliderAdapter(Activity context) {
        this.mContext = context;
    }

    public void renewItems(ArrayList<String> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(String sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }

    @Override
    public BannerSliderAdapter.InnerDTO onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_banner_slider, null);
        return new BannerSliderAdapter.InnerDTO(inflate);
    }

    @Override
    public void onBindViewHolder(InnerDTO viewHolder, int position) {
        String strUrl = mSliderItems.get(position);
//        viewHolder.itemView.setBackground(ContextCompat.getDrawable(mContext, mSliderItems.get(position)));
        viewHolder.itemView.setBackgroundResource(mContext.getResources().getIdentifier(mSliderItems.get(position), "drawable", mContext.getPackageName()));
//        Picasso.with(mContext).load(strUrl).into(viewHolder.imageViewBackground);

    }

    @Override
    public int getCount() {
        return mSliderItems.size();
    }

    class InnerDTO extends SliderViewAdapter.ViewHolder {
        View itemView;
        ImageView imageViewBackground;

        public InnerDTO(View itemView){
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.row_banner_slider);
            this.itemView = itemView;
        }
    }
}
