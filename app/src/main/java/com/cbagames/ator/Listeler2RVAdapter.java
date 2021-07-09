package com.cbagames.ator;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

public class Listeler2RVAdapter extends RecyclerView.Adapter<Listeler2RVAdapter.CardViewTasarimNesneleriTutucu> implements Serializable {

    private Context mContext;
    private ArrayList<Skor> skorArrayList;

    public Listeler2RVAdapter(Context mContext, ArrayList<Skor> skorArrayList) {
        this.mContext = mContext;
        this.skorArrayList = skorArrayList;
    }

    public class CardViewTasarimNesneleriTutucu extends RecyclerView.ViewHolder{
        public TextView textViewAd,textViewCardSkor;
        public ImageView imageViewResim;
        public CardView cardViewPersonel;
        public RatingBar ratingBarCard;

        public CardViewTasarimNesneleriTutucu(@NonNull View itemView) {
            super(itemView);
            this.textViewAd = itemView.findViewById(R.id.textViewAd);
            this.textViewCardSkor = itemView.findViewById(R.id.textViewCardSkor);
            this.imageViewResim = itemView.findViewById(R.id.imageViewResim);
            this.cardViewPersonel = itemView.findViewById(R.id.cardViewPersonel);
            this.ratingBarCard = itemView.findViewById(R.id.ratingBarCard);
        }
    }


    @NonNull
    @Override
    public CardViewTasarimNesneleriTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listeler_kisi_siralama_card_tasarim,parent,false);

        return new Listeler2RVAdapter.CardViewTasarimNesneleriTutucu(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewTasarimNesneleriTutucu holder, int position) {

        final Skor skor = skorArrayList.get(position);
        Resources res = mContext.getResources();

        holder.textViewAd.setText(skor.getPer_id().getPer_name());
        holder.textViewCardSkor.setText(String.valueOf(skor.getSkor_best()));
        holder.ratingBarCard.setRating(skor.getPer_id().getPer_rozet_sayisi());

       /* // liste Başlığı Gösterme
        if ((skor.getPer_id().getPer_id() == 0) &&(skor.getPer_id().getPer_name() == "EN İYİ SKORLAR")){
            holder.imageViewResim.setVisibility(View.INVISIBLE);
            holder.textViewCardSkor.setVisibility(View.INVISIBLE);
            holder.textViewAd.setGravity(17);
            holder.textViewAd.setTextColor(res.getColor(R.color.red));
        }*/


        switch (skor.getPer_id().getPer_icon_nu()){
            case 1:
                holder.imageViewResim.setImageDrawable(res.getDrawable(R.drawable.avatar_mutlu));
                break;
            case 2:
                holder.imageViewResim.setImageDrawable(res.getDrawable(R.drawable.avatar_mutlu_s2));
                break;
            case  3:
                holder.imageViewResim.setImageDrawable(res.getDrawable(R.drawable.avatar_mutlu_s3));
                break;
            case 4:
                holder.imageViewResim.setImageDrawable(res.getDrawable(R.drawable.avatar_mutlu_s4));
                break;
            case 5:
                holder.imageViewResim.setImageDrawable(res.getDrawable(R.drawable.avatar_mutlu_s5));
                break;
            case 6:
                holder.imageViewResim.setImageDrawable(res.getDrawable(R.drawable.avatar_mutlu_s6));
                break;
            default:
                break;
        }


        int ID = Integer.valueOf(KarsilamaEkraniActivity.personelIDString);
        if (ID == skor.getPer_id().getPer_id()){
            holder.cardViewPersonel.setCardBackgroundColor(res.getColor(R.color.avatar_acik_mavi));
        }else {
            holder.cardViewPersonel.setCardBackgroundColor(res.getColor(R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        return skorArrayList.size();
    }


}
