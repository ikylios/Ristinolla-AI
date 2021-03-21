package ristinolla; 

import java.util.Scanner;
import ristinolla.Pelilogiikka;

public class UI {

    private Scanner lukija;
    private Pelilogiikka peli;
    
    public UI(Scanner s) {
//        lukija = s;
        lukija = new Scanner(System.in);
    }

    void kaynnista() {
       peli = new Pelilogiikka(); 

       System.out.println("\nTervetuloa pelaamaan. Aloitetaan.\n");

       pelaa();
    }

    void pelaa() {
        while(peli.onKesken()) {
            tulostaLauta();
            String syöte = "";
            while (true) {
                System.out.println(peli.getVuoro() + "-merkin vuoro.");
                System.out.println("Syötä ruudun numero, jonne haluat sijoittaa merkin " + peli.getVuoro() + ":");
                syöte = lukija.nextLine();
                if (peli.onnistunutSiirto(syöte)) {
                    peli.toggleVuoro();
                    break;
                } else {
                    System.out.println("\nVäärä siirto! Yritä uudestaan.\n");
                }
            }
        }
    }


    void tulostaLauta() {
       String[][] lauta = peli.getLauta();

       System.out.println("------------------\n");
       String rivit = "";
        for (int i = 0; i < lauta.length; i++) {
            rivit += "| ";
            for (int j = 0; j < lauta.length; j++) {
                rivit += lauta[j][i] + " | ";
            }
            rivit += "\n";
        }        
        System.out.println(rivit);
    }

}