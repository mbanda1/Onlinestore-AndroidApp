package com.cyphertech.biashara.Others;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import androidx.viewpager.widget.ViewPager;

public class ViewPager_ScrollDown extends ScrollView {


    float touchX = 0;
    float touchY = 0;

    ViewPager parentPager;

    public void setParentPager(ViewPager parentPager) {
        this.parentPager = parentPager;
    }

    public ViewPager_ScrollDown(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public ViewPager_ScrollDown(Context context) {
        super(context);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                touchX = ev.getX();
                touchY = ev.getY();
                return super.onTouchEvent(ev);
            case MotionEvent.ACTION_MOVE:
                if(Math.abs(touchX-ev.getX())<40){
                    return super.onTouchEvent(ev);
                }else{
                    if (parentPager==null) {
                        return false;
                    } else {
                        return parentPager.onTouchEvent(ev);
                    }
                }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                touchX=0;
                touchY=0;
                break;
        }
        return super.onTouchEvent(ev);
    }
}
