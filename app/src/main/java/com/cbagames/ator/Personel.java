package com.cbagames.ator;

public class Personel {
    private int per_id;
    private String per_name;
    private int per_total_skor;
    private int per_icon_nu;
    private int per_rozet_sayisi;

    public Personel() {
    }

    public Personel(int per_id, String per_name, int per_total_skor, int per_icon_nu, int per_rozet_sayisi) {
        this.per_id = per_id;
        this.per_name = per_name;
        this.per_total_skor = per_total_skor;
        this.per_icon_nu = per_icon_nu;
        this.per_rozet_sayisi = per_rozet_sayisi;
    }

    public int getPer_id() {
        return per_id;
    }

    public void setPer_id(int per_id) {
        this.per_id = per_id;
    }

    public String getPer_name() {
        return per_name;
    }

    public void setPer_name(String per_name) {
        this.per_name = per_name;
    }

    public int getPer_total_skor() {
        return per_total_skor;
    }

    public void setPer_total_skor(int per_total_skor) {
        this.per_total_skor = per_total_skor;
    }

    public int getPer_icon_nu() {
        return per_icon_nu;
    }

    public void setPer_icon_nu(int per_icon_nu) {
        this.per_icon_nu = per_icon_nu;
    }

    public int getPer_rozet_sayisi() {
        return per_rozet_sayisi;
    }

    public void setPer_rozet_sayisi(int per_rozet_sayisi) {
        this.per_rozet_sayisi = per_rozet_sayisi;
    }
}
