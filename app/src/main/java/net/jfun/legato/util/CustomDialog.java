package net.jfun.legato.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.jfun.legato.R;


public class CustomDialog extends Dialog {
	
	String mLeftText = null;
	String mRightText = null;

	Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();    
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.6f;
        getWindow().setAttributes(lpWindow);
         
        setContentView(R.layout.custom_dialog);
         
        setLayout();
        setContent(mContent);
        Log.d("where", "222222222222");
        setClickListener(mLeftClickListener , mRightClickListener);
	}

	public CustomDialog(Context context) {
        // Dialog 배경을 투명 처리 해준다.
        super(context , android.R.style.Theme_Translucent_NoTitleBar);
    }

     
    public CustomDialog(Context context, String content, View.OnClickListener singleListener) {
        super(context , android.R.style.Theme_Translucent_NoTitleBar);
        mContext = context;
        this.mContent = content;
        this.mLeftClickListener = singleListener;
    }
    
    public CustomDialog(Context context, String content, View.OnClickListener singleListener, String lBtnText) {
        super(context , android.R.style.Theme_Translucent_NoTitleBar);
        Log.d("where", "1111111111111");
        mContext = context;
        this.mRightClickListener = singleListener;
//        this.mLeftClickListener = singleListener;
        this.mContent = content;
        this.mRightText = lBtnText;
    }
     
    public CustomDialog(Context context , String content ,
                        View.OnClickListener leftListener , View.OnClickListener rightListener) {
        super(context , android.R.style.Theme_Translucent_NoTitleBar);
        mContext = context;
        this.mContent = content;
        this.mLeftClickListener = leftListener;
        this.mRightClickListener = rightListener;
    }
     

    public CustomDialog(Context context , String content ,
                        View.OnClickListener leftListener, View.OnClickListener rightListener, String lBtnText, String rBtnText) {
        super(context , android.R.style.Theme_Translucent_NoTitleBar);
        mContext = context;
        this.mContent = content;
        this.mLeftClickListener = leftListener;
        this.mRightClickListener = rightListener;
        this.mLeftText = lBtnText;
        this.mRightText = rBtnText;
    }
     
    private void setContent(String content){
        mContentView.setText(Html.fromHtml(content));
    }
     
    private void setClickListener(View.OnClickListener left , View.OnClickListener right){
        if(left!=null && right!=null){
        	
        	if(!TextUtils.isEmpty(mLeftText))
        		mLeftButton.setText(mLeftText);
        	if(!TextUtils.isEmpty(mRightText))
        		mRightButton.setText(mRightText);

        	mLeftButton.setOnClickListener(left);
            mRightButton.setOnClickListener(right);
            mLeftLayout.setOnClickListener(left);
            mRightLayout.setOnClickListener(right);

            mIvDivide.setVisibility(View.VISIBLE);
        }else if(left!=null && right==null){
        	if(!TextUtils.isEmpty(mLeftText))
        		mLeftButton.setText(mLeftText);
//        	mLeftButton.setBackgroundColor(Color.rgb(0x00, 0x68, 0x38));//006838
        	mLeftButton.setOnClickListener(left);
            mLeftLayout.setOnClickListener(left);

        	mRightButton.setVisibility(View.GONE);
            mRightLayout.setVisibility(View.GONE);
            mIvDivide.setVisibility(View.GONE);
        }else if(left==null && right!=null){
            if(!TextUtils.isEmpty(mRightText))
                mRightButton.setText(mRightText);
//        	mLeftButton.setBackgroundColor(Color.rgb(0x00, 0x68, 0x38));//006838
            mRightButton.setOnClickListener(right);
            mRightLayout.setOnClickListener(right);

            mLeftButton.setVisibility(View.GONE);
            mLeftLayout.setVisibility(View.GONE);
            mIvDivide.setVisibility(View.GONE);
        }else {
             
        }
    }
     
    private TextView mContentView;
    private TextView mLeftButton;
    private TextView mRightButton;
    private RelativeLayout mLeftLayout;
    private RelativeLayout mRightLayout;
    private String mContent;
    private ImageView mIvDivide;
     
    private View.OnClickListener mLeftClickListener;
    private View.OnClickListener mRightClickListener;
     
    /*
     * Layout
     */
    private void setLayout(){
        mContentView = (TextView) findViewById(R.id.dlg_tv_content);
        mLeftButton = (TextView) findViewById(R.id.dlg_btn_left);
        mRightButton = (TextView) findViewById(R.id.dlg_btn_right);
        mLeftLayout = (RelativeLayout) findViewById(R.id.dlg_lay_left);
        mRightLayout = (RelativeLayout) findViewById(R.id.dlg_lay_right);
        mIvDivide = (ImageView) findViewById(R.id.dlg_btn_divide);
    }

//	@Override
//	public void onBackPressed() {
//	}


}
