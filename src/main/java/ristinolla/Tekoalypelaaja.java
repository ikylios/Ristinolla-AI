package ristinolla;

import java.util.Random;

public class Tekoalypelaaja implements Pelaaja {
    
    public String otaSyote() {
//        System.out.println("olen kone otan syötteen");
        String vastaus = "" + tekoalySiirto();
        System.out.println("Yrittää sijoittaa ruutuun " + vastaus);
        return vastaus;
    } 
   
    public String toString() {
        return "kone";
    }

    private int tekoalySiirto() {
        Random r = new Random();
        return r.nextInt(9);
    }
}
