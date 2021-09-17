package net.jfun.legato.tutorial;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.smarteist.autoimageslider.SliderView;

import net.jfun.legato.BaseActivity;
import net.jfun.legato.R;
import net.jfun.legato.util.Constant;

import java.util.ArrayList;

public class TutorialDetailActivity extends BaseActivity {

    private Context mContext;

    private SliderView mBannerImage;
    private BannerSliderAdapter mAdapterBanner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_detail);

        mContext = TutorialDetailActivity.this;


        mBannerImage = findViewById(R.id.store_detail_banner_slider);
        mAdapterBanner = new BannerSliderAdapter(this);
        mBannerImage.setSliderAdapter(mAdapterBanner);

        if (getIntent().getStringExtra(Constant.EXTRA_TUTORIAL).equals(Constant.EXTRA_PROFILE_TUTORIAL_BASIC)){
            ArrayList<String> tempData = new ArrayList<>();
            for (int i=1 ; i<4 ; i++) {
                tempData.add("@drawable/basic_list_tutorial_" + i);
            }
            mAdapterBanner.renewItems(tempData);
        } else if (getIntent().getStringExtra(Constant.EXTRA_TUTORIAL).equals(Constant.EXTRA_TUTORIAL_MAIN)){
            ArrayList<String> tempData = new ArrayList<>();
            for (int i=1 ; i<4 ; i++) {
                tempData.add("@drawable/tutorial_" + i);
            }
            mAdapterBanner.renewItems(tempData);
        } else if (getIntent().getStringExtra(Constant.EXTRA_TUTORIAL).equals(Constant.EXTRA_TUTORIAL_BASIC)){
            ArrayList<String> tempData = new ArrayList<>();
            for (int i=1 ; i<4 ; i++) {
                tempData.add("@drawable/tutorial_basic_" + i);
            }
            mAdapterBanner.renewItems(tempData);
        } else if (getIntent().getStringExtra(Constant.EXTRA_TUTORIAL).equals(Constant.EXTRA_TUTORIAL_PRO)) {
            ArrayList<String> tempData = new ArrayList<>();
            for (int i=1 ; i<5 ; i++) {
                tempData.add("@drawable/tutorial_pro_" + i);
            }
            mAdapterBanner.renewItems(tempData);
        } else if (getIntent().getStringExtra(Constant.EXTRA_TUTORIAL).equals(Constant.EXTRA_TUTORIAL_QR)) {
            ArrayList<String> tempData = new ArrayList<>();
            for (int i=1 ; i<5 ; i++) {
                tempData.add("@drawable/tutorial_qr_" + i);
            }
            mAdapterBanner.renewItems(tempData);
        } else if (getIntent().getStringExtra(Constant.EXTRA_TUTORIAL).equals(Constant.EXTRA_TUTORIAL_HISTORY)) {
            ArrayList<String> tempData = new ArrayList<>();
            for (int i=1 ; i<2 ; i++) {
                tempData.add("@drawable/tutorial_history_" + i);
            }
            mAdapterBanner.renewItems(tempData);
        } else if (getIntent().getStringExtra(Constant.EXTRA_TUTORIAL).equals(Constant.EXTRA_TUTORIAL_SETTING)) {
            ArrayList<String> tempData = new ArrayList<>();
            for (int i=1 ; i<5 ; i++) {
                tempData.add("@drawable/tutorial_set_" + i);
            }
            mAdapterBanner.renewItems(tempData);
        }  else if (getIntent().getStringExtra(Constant.EXTRA_TUTORIAL).equals(Constant.EXTRA_TUTORIAL_BASIC_INFO)) {
            findViewById(R.id.tutorial_btn_close).setVisibility(View.VISIBLE);
            findViewById(R.id.tutorial_btn_skip).setVisibility(View.GONE);
            int index = getIntent().getIntExtra(Constant.EXTRA_TUTORIAL_BASIC_INFO_I, 0);
            ArrayList<String> tempData = new ArrayList<>();
            tempData.add("@drawable/basic_info_" + index);
            mAdapterBanner.renewItems(tempData);
        }else {
            Toast.makeText(mContext, getString(R.string.file_not_exist), Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.tutorial_btn_close : // 건너뛰기
                finish();
                break;
            case R.id.tutorial_btn_skip : // 건너뛰기
                finish();
                break;
        }
    }
}
