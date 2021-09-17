package net.jfun.legato.roast.save;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

import net.jfun.legato.BaseActivity;
import net.jfun.legato.R;
import net.jfun.legato.setting.language.LocaleWrapper;
import net.jfun.legato.util.Constant;

import java.util.Locale;

public class SaveDataActivity extends BaseActivity {

    private Context mContext;
    Locale mLocale;
    Configuration mConfigure;
    EditText mEdtName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roaster_data_save_dialog);

        mEdtName = (EditText)findViewById(R.id.dlg_save_data_name);

        mContext = SaveDataActivity.this;

        mConfigure = new Configuration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mLocale = getApplicationContext().getResources().getConfiguration().getLocales().get(0);
        } else {
            mLocale = getApplicationContext().getResources().getConfiguration().locale;
        }


    }



    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_save :
                Intent intent = new Intent();
                intent.putExtra(Constant.EXTRA_SAVE_DATA_NAME, mEdtName.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.btn_no_save :
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleWrapper.wrap(newBase));
    }

}
