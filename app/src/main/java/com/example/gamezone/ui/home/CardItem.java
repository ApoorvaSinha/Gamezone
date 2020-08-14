package com.example.gamezone.ui.home;

public class CardItem {
    private int mTextResource;
    private int mTitleResource;
    private int imageResource;

    public CardItem(int title, int text,int image) {
        mTitleResource = title;
        mTextResource = text;
        imageResource=image;
    }

    public int getText() {
        return mTextResource;
    }

    public int getTitle() {
        return mTitleResource;
    }
    public int getImage() {
        return imageResource;
    }
}
