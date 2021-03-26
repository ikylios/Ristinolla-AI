package ristinolla; 

import java.util.Scanner;
import ristinolla.Pelilogiikka;

public class UI {

    private Scanner lukija;
    private Pelilogiikka peli;
    
    public UI() {
        lukija = new Scanner(System.in);
    }

    public void kaynnista() {
       peli = new Pelilogiikka(); 

       System.out.println("\nTervetuloa pelaamaan ristinollaa. Aloitetaan.\n");

       pelaa();
    }

    public void pelaa() {
        while(peli.onKesken()) {
            tulostaLauta();
            String syöte = "";
            while (true) {
                System.out.println(peli.getVuoro() + "-merkin vuoro.");
                System.out.println("Syötä ruudun numero, jonne haluat sijoittaa merkin " + peli.getVuoro() + ":");
                syöte = lukija.nextLine();
                if (peli.onnistunutSiirto(syöte)) {
                    System.out.println("---------------------\n");
                    break;
                } else {
                    System.out.println("\nVäärä siirto! Yritä uudestaan.\n");
                }
            }
        }
        peliOhi();
    }

    public void peliOhi() {
        switch (peli.getVoittotilanne()) {
            case "tasapeli":
                System.out.println("Tasapeli!");
                break;
        
            default:
                System.out.println("Jotain outoa tapahtui.");
                break;
        }
        System.out.println("Lopullinen lauta:\n");
        tulostaLauta();
        lukija.close();
    }


    public void tulostaLauta() {
       String[][] lauta = peli.getLauta();

       String rivit = "";
        for (int i = 0; i < lauta.length; i++) {
            rivit += "  | ";
            for (int j = 0; j < lauta.length; j++) {
                rivit += lauta[j][i] + " | ";
            }
            rivit += "\n";
        }        
        System.out.println(rivit);
    }

}