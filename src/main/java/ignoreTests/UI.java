package ignoreTests; 

import java.util.Scanner;
import ristinolla.*;

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
        int sivunPituus = 3;
        
        while (true) {
            System.out.println("Valitse ihmispelaajien määrä (0-2):");
            syote = lukija.nextLine();            
            try {                
                int syoteArvo = Integer.parseInt(syote);

                if (syoteArvo >= 0 && syoteArvo <= 2) {
                    Pelaaja p1 = new Tekoalypelaaja("X");
                    Pelaaja p2 = new Tekoalypelaaja("O");
                    if (syoteArvo > 0) {
                        p1 = new Ihmispelaaja(lukija);
                        if (syoteArvo == 2) {
                            p2 = new Ihmispelaaja(lukija);
                        }
                    }
                    pelaajat = new Pelaaja[] {p1, p2};
                    break;                
                }
                
            } catch (Exception e) {
                System.out.println("\nEi sallittu arvo.\n");
            }
        }    

        System.out.println("Aloitetaan!\n");
        peli = new Pelilogiikka(); 
        System.out.println("---------------------\n");
        pelaa();
    }

    public void pelaa() {
        pelataan:
        while(true) {
            for (Pelaaja pelaaja : pelaajat) {
                tulostaLauta();
                System.out.println(peli.getVuoro() + "-merkin vuoro.");
                while (true) {
                    if (peli.onnistunutSiirto(pelaaja.otaSyote(peli.getLauta()))) {
                        System.out.println("---------------------\n");
                        break;
                    } else {
                        System.out.println("\nVäärä siirto! Yritä uudestaan.\n");
                    }
                }
                if (!peli.onKesken()) {
                    break pelataan;
                }
            }
        }
        peliOhi();
    }

    public void peliOhi() {
        String lopputulos = "Jotain outoa tapahtui.";
        switch (peli.kukaVoitti()) {
            case 0: lopputulos = "Tasapeli!"; break;
            case 1: lopputulos = "X voitti!"; break;
            case -1: lopputulos = "O voitti!"; break;
        }
        System.out.println(lopputulos);
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