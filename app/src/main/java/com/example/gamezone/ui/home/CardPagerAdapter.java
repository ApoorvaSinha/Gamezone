package com.example.gamezone.ui.home;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.gamezone.ui.home.CardAdapter;
import com.example.gamezone.R;

import java.util.ArrayList;
import java.util.List;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {
    private List<CardView> mViews;
    private List<CardItem> mData;
    private float mBaseElevation;

    public CardPagerAdapter() {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    public void addCardItem(CardItem item) {
        mViews.add(null);
        mData.add(item);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.adapter, container, false);
        container.addView(view);
        FrameLayout frameView=(FrameLayout) view.findViewById(R.id.frame);
        frameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position==0){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=h0gWfVCSGQQ"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setPackage("com.google.android.youtube");
                    view.getContext().startActivity(intent);
                }
                if(position==1){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=bAyrObl7TYE"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setPackage("com.google.android.youtube");
                    view.getContext().startActivity(intent);
                }
                if(position==2){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=SSo_EIwHSd4"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setPackage("com.google.android.youtube");
                    view.getContext().startActivity(intent);
                }
                if(position==3){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=HcqpanDadyQ"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setPackage("com.google.android.youtube");
                    view.getContext().startActivity(intent);
                }

            }
        });

        bind(mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(CardItem item, View view) {
        TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        TextView contentTextView = (TextView) view.findViewById(R.id.contentTextView);
        ImageView imageView=(ImageView)view.findViewById(R.id.image);
        titleTextView.setText(item.getTitle());
        contentTextView.setText(item.getText());
        imageView.setImageResource(item.getImage());
    }

}
