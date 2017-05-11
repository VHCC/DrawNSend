package com.ichen.user.viewpagertest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.ichen.user.viewpagertest.home.HomeFragment;
import com.ichen.user.viewpagertest.page.Page1Fragment;
import com.ichen.user.viewpagertest.page.Page2Fragment;
import com.ichen.user.viewpagertest.page.Page3Fragment;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setPageTransformer(false, new FadePageTransformer());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(mSectionsPagerAdapter);
    }

    /* SectionPager Block */
    private class SectionsPagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {
        /* Declare Variable */
        public final int PAGE_HOME = 0;
        public final int PAGE_1 = 1;
        public final int PAGE_2 = 2;
        public final int PAGE_3 = 3;

        private final int[] PAGE_GROUP = new int[]{
                PAGE_HOME, PAGE_1, PAGE_2, PAGE_3
        };

        /* Constructor */
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case PAGE_HOME: {
                    HomeFragment homeFragment = HomeFragment.newInstance(3000);
                    homeFragment.setIHomeFragmentListener(new HomeFragment.IHomeFragmentListener() {
                        public void onShowEnd() {
                            mViewPager.setCurrentItem(PAGE_1, true);
                        }
                    });
                    fragment = homeFragment;
                }
                break;
                case PAGE_1: {
                    Page1Fragment page1Fragment = Page1Fragment.newInstance();
                    fragment = page1Fragment;
                }
                break;
                case PAGE_2: {
                    Page2Fragment page2Fragment = Page2Fragment.newInstance();
                    fragment = page2Fragment;
                }
                break;
                case PAGE_3: {
                    Page3Fragment page3Fragment = Page3Fragment.newInstance();
                    fragment = page3Fragment;
                }
                break;
                default: {

                }
                break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return PAGE_GROUP.length;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // None
        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {

            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "SECTION " + position;
        }
    }

    public class FadePageTransformer implements ViewPager.PageTransformer {
        @Override
        public void transformPage(View view, float position) {
            if (position <= -1.0F || position >= 1.0F) {
                view.setTranslationX(view.getWidth() * position);
                view.setAlpha(0.0F);
            } else if (position == 0.0F) {
                view.setTranslationX(view.getWidth() * position);
                view.setAlpha(1.0F);
            } else {
                // position is between -1.0F & 0.0F OR 0.0F & 1.0F
                view.setTranslationX(view.getWidth() * -position);
                view.setAlpha(1.0F - Math.abs(position));
            }
        }
    }


}
