package com.example.gamezone.ui.home;

import androidx.cardview.widget.CardView;

interface CardAdapter {
    int MAX_ELEVATION_FACTOR = 8;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    int getCount();

}
