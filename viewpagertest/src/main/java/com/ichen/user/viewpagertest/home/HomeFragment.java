package com.ichen.user.viewpagertest.home;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ichen.user.viewpagertest.R;

/**
 * Created by user on 2017/5/11.
 */
public class HomeFragment extends Fragment {

    private static final String ARG_HOME_DELAY = "arg_home_delay";
    private static final long ARG_HOME_DELAY_DEFAULT_TIME = 2000l;
    private long mDelay = 0;
    private Handler mHandler = new Handler();

    /* declare Listener */
    private IHomeFragmentListener iHomeFragmentListener;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(long delay) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_HOME_DELAY, (delay > 01) ? delay : ARG_HOME_DELAY_DEFAULT_TIME);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDelay = getArguments().getLong(ARG_HOME_DELAY, ARG_HOME_DELAY_DEFAULT_TIME);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mHandler.postDelayed(mFragmentRunnable, mDelay);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mHandler.removeCallbacks(mFragmentRunnable);
    }

    /* ********** Life Cycle Line ********** */
    private Runnable mFragmentRunnable = new Runnable() {
        @Override
        public void run() {
            if (iHomeFragmentListener != null) {
                iHomeFragmentListener.onShowEnd();
            }
        }
    };

    /* Interface Block */
    public interface IHomeFragmentListener {
        void onShowEnd();
    }

    public void setIHomeFragmentListener(IHomeFragmentListener listener) {
        iHomeFragmentListener = listener;
    }
}
