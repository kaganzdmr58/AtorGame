package com.cbagames.ator;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

public class CardViewSiklar {
    private CardView cardView;
    private TextView textViewIcerik;
    private TextView textViewSık;
    private ImageView imageViewSık;

    public CardViewSiklar() {
    }

    public CardViewSiklar(CardView cardView, TextView textViewIcerik, TextView textViewSık, ImageView imageViewSık) {
        this.cardView = cardView;
        this.textViewIcerik = textViewIcerik;
        this.textViewSık = textViewSık;
        this.imageViewSık = imageViewSık;
    }

    public CardView getCardView() {
        return cardView;
    }

    public void setCardView(CardView cardView) {
        this.cardView = cardView;
    }

    public TextView getTextViewIcerik() {
        return textViewIcerik;
    }

    public void setTextViewIcerik(TextView textViewIcerik) {
        this.textViewIcerik = textViewIcerik;
    }

    public TextView getTextViewSık() {
        return textViewSık;
    }

    public void setTextViewSık(TextView textViewSık) {
        this.textViewSık = textViewSık;
    }

    public ImageView getImageViewSık() {
        return imageViewSık;
    }

    public void setImageViewSık(ImageView imageViewSık) {
        this.imageViewSık = imageViewSık;
    }
}
