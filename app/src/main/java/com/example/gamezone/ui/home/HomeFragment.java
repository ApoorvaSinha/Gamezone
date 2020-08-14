package com.example.gamezone.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.gamezone.R;

import java.util.Timer;
import java.util.TimerTask;


public class HomeFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {


    private HomeViewModel homeViewModel;

    private Button mButton;
    private ViewPager mViewPager;

    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private CardFragmentPagerAdapter mFragmentCardAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;

    private Handler sliderhandler = new Handler();

    private boolean mShowingFragments = false;

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 100;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 5000;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, null);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewPager = (ViewPager) getActivity().findViewById(R.id.viewPager);
        FrameLayout layout = (FrameLayout) getActivity().findViewById(R.id.frame);
        mCardAdapter = new CardPagerAdapter();
        mCardAdapter.addCardItem(new CardItem(R.string.title_1, R.string.text_1, R.drawable.iot));
        mCardAdapter.addCardItem(new CardItem(R.string.title_2, R.string.text_1, R.drawable.bigdata));
        mCardAdapter.addCardItem(new CardItem(R.string.title_3, R.string.text_1, R.drawable.blockchain));
        mCardAdapter.addCardItem(new CardItem(R.string.title_4, R.string.text_1, R.drawable.ml));
        mFragmentCardAdapter = new CardFragmentPagerAdapter(getActivity().getSupportFragmentManager(),
                dpToPixels(2, getContext()));

        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
        mFragmentCardShadowTransformer = new ShadowTransformer(mViewPager, mFragmentCardAdapter);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);


        final Runnable sliderRunnable = new Runnable() {
            @Override
            public void run() {
                int currentPage = mViewPager.getCurrentItem();
                int numberOfBannerImage = 4;
                //mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
                if (currentPage == numberOfBannerImage - 1) {
                    currentPage = -1;
                }
                mViewPager.setCurrentItem(currentPage + 1, true);
            }
        };
        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                sliderhandler.post(sliderRunnable);
            }
        }, DELAY_MS, PERIOD_MS);


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                sliderhandler.removeCallbacks(sliderRunnable);
                sliderhandler.postDelayed(sliderRunnable, 2000);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        if (!mShowingFragments) {
            mButton.setText("Views");
            mViewPager.setAdapter(mFragmentCardAdapter);
            mViewPager.setPageTransformer(false, mFragmentCardShadowTransformer);
        } else {
            mButton.setText("Fragments");
            mViewPager.setAdapter(mCardAdapter);
            mViewPager.setPageTransformer(false, mCardShadowTransformer);
        }

        mShowingFragments = !mShowingFragments;

    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean b) {
        mCardShadowTransformer.enableScaling(b);
        mFragmentCardShadowTransformer.enableScaling(b);

    }




}