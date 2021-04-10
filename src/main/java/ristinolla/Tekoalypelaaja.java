package ristinolla;

import java.util.Random;

public class Tekoalypelaaja implements Pelaaja {

    private Random r;

    public Tekoalypelaaja() {
        r = new Random();
    }
    
    public Tekoalypelaaja(Random rand) {
        r = rand;
    }
    
    public String otaSyote() {
        String vastaus = "" + tekoalySiirto();
        System.out.println("Yrittää sijoittaa ruutuun " + vastaus);
        return vastaus;
    } 

    private int tekoalySiirto() {
        return r.nextInt(9);
    }
}
