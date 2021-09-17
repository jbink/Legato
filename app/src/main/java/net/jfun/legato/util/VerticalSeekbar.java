package net.jfun.legato.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatSeekBar;

import net.jfun.legato.R;

public class VerticalSeekbar extends AppCompatSeekBar {

    private int progressValue = 0;

    public VerticalSeekbar(Context context) {
        super(context);
        initView(context);
    }

    public VerticalSeekbar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    public VerticalSeekbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void initView(Context context) {
        setProgressDrawable(ContextCompat.getDrawable(context, R.drawable.test));
//        setThumb(ContextCompat.getDrawable(context, R.drawable.progress_thumb));
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldh, oldw);
    }

    public void setEnableSeekbar(Context context, boolean value) {

        Rect bounds = getProgressDrawable().getBounds();
        if (value == true){
            setProgressDrawable(ContextCompat.getDrawable(context, R.drawable.progressbar_set_temperature_enable));
        } else {
            setProgressDrawable(ContextCompat.getDrawable(context, R.drawable.test));
        }
        getProgressDrawable().setBounds(bounds);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    protected void onDraw(Canvas c) {
        c.rotate(-90f);
        c.translate(-getHeight(),0f);

        super.onDraw(c);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                int i=0;
                i=getMax() - (int) (getMax() * event.getY() / getHeight());
                setProgress(i);
                Log.i("Progress",getProgress()+"");
                onSizeChanged(getWidth(), getHeight(), 0, 0);
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }
}