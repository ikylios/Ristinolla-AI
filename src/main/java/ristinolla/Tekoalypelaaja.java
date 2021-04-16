package ristinolla;

import java.util.*;
import java.util.Random;

public class Tekoalypelaaja implements Pelaaja {

    private Random r;
    String merkki;
    String vastustajanMerkki;
    int sivunPituus;
    int maksimivuorot;

    public Tekoalypelaaja(String m) {
        merkki = m;
        if (merkki.equals("X")) {
            vastustajanMerkki = "O";
        } else {
            vastustajanMerkki = "X";
        }
    }
    
    public Tekoalypelaaja(Random rand) {
        r = rand;
    }

    public String otaSyote(String[][] argLauta) {
        String[][] lauta = argLauta;
        sivunPituus = lauta.length;
        maksimivuorot = sivunPituus*sivunPituus+1;
        String parasRuutu = "";
        int parasTulos = -100000;
        for (int j = 0; j < sivunPituus; j++) {
            for (int i = 0; i < sivunPituus; i++) {
                // ruutu on vapaa, jos ruudun sisältö on numero
                if (onNumero(lauta[j][i])) { 
                    String ruutunumero = lauta[j][i];
                    lauta[j][i] = merkki;
                    int tulos = minimax(lauta, 0, false);
                    lauta[j][i] = ruutunumero;
                    if (tulos > parasTulos) {                        
                        parasRuutu = lauta[j][i];
                        parasTulos = tulos;
                    }
                }
            }
        }
        System.out.println("Sijoittaa ruutuun " + parasRuutu);
        return parasRuutu;
    } 

    private int minimax(String[][] lauta, int syvyys, boolean maximoi) {
        // tarkistaa, onko voitettu (= päästy gametreen loppuun) 
        for (int j = 0; j < sivunPituus; j++) {
            for (int i = 0; i < sivunPituus; i++) {
                if (!onNumero(lauta[j][i])) {
                    String voittaja = laskePisteet(lauta, j, i);
                    if (!voittaja.equals("kesken")) {
                        if (!voittaja.equals("tasapeli")) {
                            if (voittaja.equals(merkki)) {
                                return maksimivuorot - syvyys;
                            } else {
                                return -maksimivuorot + syvyys;
                            }
                        } else {
                            return 0;
                        }                
                    }                    
                }     
            }
        }
        
        
        if (maximoi) {
            String parasRuutu = "";
            int parasTulos = -100000;
            for (int j = 0; j < sivunPituus; j++) {
                for (int i = 0; i < sivunPituus; i++) {
                    if (onNumero(lauta[j][i])) {
                        String ruutunumero = lauta[j][i];
                        lauta[j][i] = merkki;
                        int tulos = minimax(lauta, syvyys+1, false);
                        lauta[j][i] = ruutunumero;
                        if (tulos > parasTulos) {
                            parasTulos = tulos;
                        }
                    }
                }
            }
            return parasTulos;
        } else {
            String parasRuutu = "";
            int parasTulos = 100000;
            for (int j = 0; j < sivunPituus; j++) {
                for (int i = 0; i < sivunPituus; i++) {
                    if (onNumero(lauta[j][i])) {
                        String ruutunumero = lauta[j][i];
                        lauta[j][i] = vastustajanMerkki;
                        int tulos = minimax(lauta, syvyys+1, true);
                        lauta[j][i] = ruutunumero;
                        if (tulos < parasTulos) {
                            parasTulos = tulos;
                        }
                    }
                }
            }
            return parasTulos;
        }
    }

    public String laskePisteet(String[][] lauta, int y, int x) {
        String tempm = lauta[y][x];
        
        // rivi
        int rivisumma = 0;
        for (int i = 0; i < sivunPituus; i++) {
            if (tempm.equals(lauta[y][i])) {
                rivisumma++;
                tempm = lauta[y][i];
                if (rivisumma == sivunPituus) {
                    return lauta[y][i];
                }
            }
        }

        // sarake
        int sarakesumma = 0;
        for (int i = 0; i < sivunPituus; i++) {
            if (tempm.equals(lauta[i][x])) {
                sarakesumma++;
                tempm = lauta[i][x];
                if (sarakesumma == sivunPituus) {
                    return lauta[i][x];
                }
            }
        }

        if (onDiagonaalilla(y, x)) {
            // diagonaali [0,0],[1,1], [2,2]
            int diagonaalisumma = 0;
            for (int i = 0; i < sivunPituus; i++) {
                if (tempm.equals(lauta[i][i])) {
                    diagonaalisumma++;
                    tempm = lauta[i][i];
                    if (diagonaalisumma == sivunPituus) {
                        return lauta[i][i];
                    }
                }
            }

            // käänteisdiagonaali [0,2], [1,1], [2,0]
            int kaanteisdiagonaalisumma = 0;
            int i = 0;
            int j = sivunPituus-1;
            while (i < sivunPituus) {
                if (tempm.equals(lauta[j][i])) {
                    kaanteisdiagonaalisumma++;
                    tempm = lauta[j][i];
                    if (kaanteisdiagonaalisumma == sivunPituus) {
                        return lauta[j][i];
                    }
                }
                i++;
                j--;
            }
        }

        if (laskeVapaatRuudut(lauta) == 0) {
            return "tasapeli";
        }
        
        return "kesken";
    }

    public int laskeVapaatRuudut(String[][] lauta) {
        int vapaatRuudut = sivunPituus*sivunPituus;
        for (int j = 0; j < sivunPituus; j++) {
            for (int i = 0; i < sivunPituus; i++) {
                if (!onNumero(lauta[j][i])) {
                    vapaatRuudut--;
                }
            }
        }
        return vapaatRuudut;
    }
    
    public boolean onDiagonaalilla(int y, int x) {
        // diagonaali: tapaukset [0,2], [1,1] [2,0] 
        if (y+x == sivunPituus-1) {
            return true;
        }
        // käänteisdiagonaali: tapaukset [0,0], [1,1], [2,2]
        if (x == y) {
            return true;
        }
        return false;
    }

    private boolean onNumero(String syote) {
        try {
            Integer.parseInt(syote);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public void tulostaLauta(String[][] lauta) {
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
