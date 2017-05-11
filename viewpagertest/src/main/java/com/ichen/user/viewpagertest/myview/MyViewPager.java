package com.ichen.user.viewpagertest.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.ichen.user.viewpagertest.R;

/**
 * Created by Ichen on 2017/5/10.
 */
public class MyViewPager extends ViewPager {

    protected boolean isSwipeable;

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typeAttr = context.obtainStyledAttributes(attrs, R.styleable.MyViewPager);
        try {
            setSwipeable(typeAttr.getBoolean(R.styleable.MyViewPager_swipeable, false));
        } finally {
            typeAttr.recycle();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isSwipeable ? super.onInterceptTouchEvent(ev) : false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isSwipeable ? super.onTouchEvent(ev) : false;
    }

    /* Customization */
    /** Set to enable or disable swipe. **/
    public void setSwipeable(boolean flag) {
        isSwipeable = flag;
    }
}
