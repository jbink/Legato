package net.jfun.legato.tutorial;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import net.jfun.legato.BaseActivity;
import net.jfun.legato.R;
import net.jfun.legato.util.Constant;

public class TutorialActivity extends BaseActivity {
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        mContext = TutorialActivity.this;

    }

    public void onClick(View v){
        Intent intent;
        switch (v.getId()) {
            case R.id.tutorial_btn_back :
                finish();
                break;
            case R.id.tutorial_img_basic :
                intent = new Intent(mContext, TutorialDetailActivity.class);
                intent.putExtra(Constant.EXTRA_TUTORIAL, Constant.EXTRA_TUTORIAL_BASIC);
                startActivity(intent);
                break;
            case R.id.tutorial_img_pro :
                intent = new Intent(mContext, TutorialDetailActivity.class);
                intent.putExtra(Constant.EXTRA_TUTORIAL, Constant.EXTRA_TUTORIAL_PRO);
                startActivity(intent);
                break;
            case R.id.tutorial_img_qr :
                intent = new Intent(mContext, TutorialDetailActivity.class);
                intent.putExtra(Constant.EXTRA_TUTORIAL, Constant.EXTRA_TUTORIAL_QR);
                startActivity(intent);
                break;
            case R.id.tutorial_img_history :
                intent = new Intent(mContext, TutorialDetailActivity.class);
                intent.putExtra(Constant.EXTRA_TUTORIAL, Constant.EXTRA_TUTORIAL_HISTORY);
                startActivity(intent);
                break;
            case R.id.tutorial_img_setting :
                intent = new Intent(mContext, TutorialDetailActivity.class);
                intent.putExtra(Constant.EXTRA_TUTORIAL, Constant.EXTRA_TUTORIAL_SETTING);
                startActivity(intent);
                break;
        }
    }
}
