package ignoreTests; 

import java.util.Scanner;
import ristinolla.*;
import java.util.*;

public class UI {

    private Scanner lukija;
    private Pelilogiikka peli;
    private Pelaaja[] pelaajat;
    
    public UI() {
        lukija = new Scanner(System.in);
    }

    public void kaynnista() {
        System.out.println("\nTervetuloa pelaamaan ristinollaa.\n");
        String syote = "";
/*        
        while (true) {
            System.out.println("Valitse ihmispelaajien määrä (0-2):");
            syote = lukija.nextLine();
            try {
                int syoteArvo = Integer.parseInt(syote);
                
                Pelaaja p1 = new Tekoalypelaaja("X");
*/                
                Pelaaja p1 = new Ihmispelaaja(lukija);
                Pelaaja p2 = new Tekoalypelaaja("O");
/*                
                if (syoteArvo > 0) {
                    p1 = new Ihmispelaaja(lukija);
                    
                    if (syoteArvo == 2) {
                        p2 = new Ihmispelaaja(lukija);
                    }
                }
*/                
                pelaajat = new Pelaaja[] {p1, p2};
/*                
                break;                
            } catch (Exception e) {
                System.out.println("\nEi sallittu arvo.\n");
            }
        }    
*/        

//        System.out.println(Arrays.toString(pelaajat));

        System.out.println("Aloitetaan!\n");
        peli = new Pelilogiikka(); 
        System.out.println("---------------------\n");
        pelaa();
    }

    public void pelaa() {
//        System.out.println(Arrays.toString(pelaajat));
        while (peli.onKesken()) {
            for (Pelaaja pelaaja : pelaajat) {
                tulostaLauta();
                System.out.println(peli.getVuoro() + "-merkin vuoro.");
//                if (pelaaja instanceof Tekoalypelaaja) {
//                    pelaaja.otaSyote(peli.getLauta());                
//                } else {
//                    pelaaja.otaSyote();
//                }
                while (true) {
                    if (peli.onnistunutSiirto(pelaaja.otaSyote(peli.getLauta()))) {
                        System.out.println("---------------------\n");
                        break;
                    } else {
                        System.out.println("\nVäärä siirto! Yritä uudestaan.\n");
                    }
                }
                if (!peli.onKesken()) {
                    break;
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
        for (int j = 0; j < lauta.length; j++) {
            rivit += "  | ";
            for (int i = 0; i < lauta.length; i++) {
                rivit += lauta[j][i] + " | ";
            }
            rivit += "\n";
        }        
        System.out.println(rivit);
    }

}