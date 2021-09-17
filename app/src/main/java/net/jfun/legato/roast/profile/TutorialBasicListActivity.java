package net.jfun.legato.roast.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import net.jfun.legato.BaseActivity;
import net.jfun.legato.R;
import net.jfun.legato.tutorial.TutorialDetailActivity;
import net.jfun.legato.util.Constant;

public class TutorialBasicListActivity extends BaseActivity {
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_basic_list);

        mContext = TutorialBasicListActivity.this;

    }

    public void onClick(View v){
        Intent intent;
        switch (v.getId()) {
            case R.id.tutorial_basic_list_btn_back :
                finish();
                break;
        }
    }
}
