package ignoreTests; 

import java.util.Scanner;
import ristinolla.Pelilogiikka;

public class UI {

    private Scanner lukija;
    private Pelilogiikka peli;
//    private Pelaaja[] pelaajat;
    
    public UI() {
        lukija = new Scanner(System.in);
    }

    public void kaynnista() {
        System.out.println("\nTervetuloa pelaamaan ristinollaa.\n");
        String syote = "";
        while (true) {
            System.out.println("Valitse ihmispelaajien määrä (1-2):");
            syote = lukija.nextLine();
            try {
                int syoteArvo = Integer.parseInt(syote);
                if (syoteArvo >= 1 || syoteArvo <= 2) {
/*                    
                    Pelaaja[] pelaajat = new pelaajat[1];
                    pelaajat[0] = new Pelaaja(ihminen);
                    if (syoteArvo == 1) {

                    }
*/                    
                    break;
                } else {}
            } catch (Exception e) {
                System.out.println("\nEi sallittu arvo.\n");
            }
        }

        System.out.println("Aloitetaan!\n");
        peli = new Pelilogiikka(Integer.parseInt(syote)); 
        System.out.println("---------------------\n");
        pelaa();
    }

    public void pelaa() {
/*
        for (Pelaaja pelaaja : pelaajat) {

        }
*/
        while (peli.onKesken()) {            
            tulostaLauta();
            String syöte = "";

            // Ihmispelaajan (X-merkki) vuoro
            while (true) {
                // Ihmispelaajan (X-merkki) vuoro
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

            // Konepelaajan (O-merkki) vuoro
            while (true) {
                System.out.println(peli.getVuoro() + "-merkin vuoro.");
                System.out.println("Syötä ruudun numero, jonne haluat sijoittaa merkin " + peli.getVuoro() + ":");
                if (peli.onnistunutSiirto("" + peli.tekoalySiirto())) {
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
            case "X":
                System.out.println("X voitti!");
                break;
            case "O":
                System.out.println("O voitti!");
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