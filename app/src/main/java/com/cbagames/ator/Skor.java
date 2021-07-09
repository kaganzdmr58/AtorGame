package com.cbagames.ator;

public class Skor {
    private int skor_id;
    private Personel per_id;
    private int skor_best;

    public Skor() {
    }

    public Skor(int skor_id, Personel per_id, int skor_best) {
        this.skor_id = skor_id;
        this.per_id = per_id;
        this.skor_best = skor_best;
    }


    public int getSkor_id() {
        return skor_id;
    }

    public void setSkor_id(int skor_id) {
        this.skor_id = skor_id;
    }

    public Personel getPer_id() {
        return per_id;
    }

    public void setPer_id(Personel per_id) {
        this.per_id = per_id;
    }

    public int getSkor_best() {
        return skor_best;
    }

    public void setSkor_best(int skor_best) {
        this.skor_best = skor_best;
    }
}
