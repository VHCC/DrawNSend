package com.ichen.user.viewpagertest.myview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ichen.user.viewpagertest.R;

/**
 * Created by user on 2017/5/11.
 */
public class MyActionBar extends LinearLayout {

    private LinearLayout actionbarLayout;
    private TextView actionBarText, textViewRight;
    private ImageView btnLeft, btnRight;

    /* Declare Listenner */
    private ClickListener mClickListener = new ClickListener();
    private IActionListener iActionListener;

    public MyActionBar(Context context) {
        super(context);
        initLayout();
        initListener();
    }

    public MyActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout();
        initAttr(attrs);
        initListener();
    }

    private void initLayout() {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.my_action_bar, this, false);
        actionbarLayout = (LinearLayout) v.findViewById(R.id.actionbarLayout);
        actionBarText = (TextView) v.findViewById(R.id.textViewActionBarTitle);
        textViewRight = (TextView) v.findViewById(R.id.textViewActionBarRight);
        btnLeft = (ImageView) v.findViewById(R.id.btnActionbarLeft);
        btnRight = (ImageView) v.findViewById(R.id.btnActionbarRight);
        this.addView(v);
    }

    /** Initializing XML attributes. **/
    private void initAttr(AttributeSet attrs) {
        Context context = getContext();
        Resources resources = context.getResources();
        TypedArray typeAttr = context.obtainStyledAttributes(attrs, R.styleable.MyActionBar);
        try {
            setTitle(typeAttr.getString(R.styleable.MyActionBar_titleText));

            int barTextColor = typeAttr.getColor(R.styleable.MyActionBar_textColor, resources.getColor(R.color.action_bar_title_font_color));
            actionBarText.setTextColor(barTextColor);
            textViewRight.setTextColor(barTextColor);

            boolean leftIconVisibility = typeAttr.getBoolean(R.styleable.MyActionBar_leftIconVisibility, false);
            btnLeft.setVisibility(leftIconVisibility ? View.VISIBLE : View.GONE);

            boolean rightIconVisibility = typeAttr.getBoolean(R.styleable.MyActionBar_rightIconVisibility, false);
            btnRight.setVisibility(rightIconVisibility ? View.VISIBLE : View.GONE);

            boolean rightTextVisibility = typeAttr.getBoolean(R.styleable.MyActionBar_rightTextVisibility, false);
            textViewRight.setVisibility(rightTextVisibility ? View.VISIBLE : View.GONE);

            btnLeft.setImageDrawable(typeAttr.getDrawable(R.styleable.MyActionBar_leftIcon));
            btnRight.setImageDrawable(typeAttr.getDrawable(R.styleable.MyActionBar_rightIcon));

        } finally {
            typeAttr.recycle();
        }
    }

    private void initListener() {
        btnLeft.setOnClickListener(mClickListener);
        btnRight.setOnClickListener(mClickListener);
    }

    /* **************Life Cycle Line************** */

    public void setTitle(String title) {
        actionBarText.setText(title);
    }

    public void setBackgroundColor(int color) {
        actionbarLayout.setBackgroundColor(color);
    }

    /**
     * Setup the visibility using {@link View#VISIBLE} or {@link View#GONE}.
     */
    public void setRightIconVisibility(int visibility) {
        btnRight.setVisibility((visibility == View.VISIBLE) ? View.VISIBLE : View.GONE);
    }

    /* Interface Block */
    public interface IActionListener {
        void onClickLeftButton();

        void onClickRightButton();
    }

    public void setiActionListener(IActionListener listener) {
        iActionListener = listener;
    }

    /* Listener Block */
    private class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnActionbarLeft: {
                    if (iActionListener != null) {
                        iActionListener.onClickLeftButton();
                    }
                }
                break;

                case R.id.btnActionbarRight: {
                    if (iActionListener != null) {
                        iActionListener.onClickRightButton();
                    }
                }
                break;
            }
        }
    }
}
