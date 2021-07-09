package com.cbagames.ator;

import java.io.Serializable;

public class Soru implements Serializable {
    private int soruId,soruCevap,soruKategori;
    private String soru,soruSıkA,soruSıkB,soruSıkC,soruSıkD;

    public Soru() {
    }

    public Soru(int soruId, int soruCevap, int soruKategori, String soru, String soruSıkA, String soruSıkB, String soruSıkC, String soruSıkD) {
        this.soruId = soruId;
        this.soruCevap = soruCevap;
        this.soruKategori = soruKategori;
        this.soru = soru;
        this.soruSıkA = soruSıkA;
        this.soruSıkB = soruSıkB;
        this.soruSıkC = soruSıkC;
        this.soruSıkD = soruSıkD;
    }

    public int getSoruId() {
        return soruId;
    }

    public void setSoruId(int soruId) {
        this.soruId = soruId;
    }

    public int getSoruCevap() {
        return soruCevap;
    }

    public void setSoruCevap(int soruCevap) {
        this.soruCevap = soruCevap;
    }

    public int getSoruKategori() {
        return soruKategori;
    }

    public void setSoruKategori(int soruKategori) {
        this.soruKategori = soruKategori;
    }

    public String getSoru() {
        return soru;
    }

    public void setSoru(String soru) {
        this.soru = soru;
    }

    public String getSoruSıkA() {
        return soruSıkA;
    }

    public void setSoruSıkA(String soruSıkA) {
        this.soruSıkA = soruSıkA;
    }

    public String getSoruSıkB() {
        return soruSıkB;
    }

    public void setSoruSıkB(String soruSıkB) {
        this.soruSıkB = soruSıkB;
    }

    public String getSoruSıkC() {
        return soruSıkC;
    }

    public void setSoruSıkC(String soruSıkC) {
        this.soruSıkC = soruSıkC;
    }

    public String getSoruSıkD() {
        return soruSıkD;
    }

    public void setSoruSıkD(String soruSıkD) {
        this.soruSıkD = soruSıkD;
    }
}
