package ristinolla;

import java.util.Scanner;

public class Ihmispelaaja implements Pelaaja {

    private Scanner lukija;

    public Ihmispelaaja(Scanner s) {
        lukija = s;
    }

    public String otaSyote() {
        System.out.println("Syötä ruudun numero, jonne haluat sijoittaa merkin:");
        String syote = lukija.nextLine();
        return syote;
    } 

}