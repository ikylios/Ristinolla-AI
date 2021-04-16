package ristinolla;

import java.util.*;
import java.util.Random;

public class Tekoalypelaaja implements Pelaaja {

    private Random r;
//    String[][] lauta;
    String merkki;
    String vastustajanMerkki;
    boolean ensimmaisenaVuorossa;
    int sivunPituus;
    int maksimivuorot;

    public Tekoalypelaaja(String m) {
        merkki = m;
        if (merkki.equals("X")) {
            ensimmaisenaVuorossa = true;
            vastustajanMerkki = "O";
        } else {
            vastustajanMerkki = "X";
            ensimmaisenaVuorossa = false;
        }
        r = new Random();        
    }
    
    public Tekoalypelaaja(Random rand) {
        r = rand;
    }

    public String otaSyote(String[][] argLauta) {
//        String vastaus = "" + tekoalySiirto();
        String[][] lauta = argLauta;
        sivunPituus = lauta.length;
        maksimivuorot = sivunPituus*sivunPituus+1;
        String parasRuutu = "";
        int parasTulos = -100000;
//        System.out.println("alkaa käymään läpi kaikkia mahdollisia vapaita paikkoja.");
        for (int j = 0; j < sivunPituus; j++) {
            for (int i = 0; i < sivunPituus; i++) {
                // ruutu on vapaa, jos ruudun sisältö on numero
                if (onNumero(lauta[j][i])) { 
                    String ruutunumero = lauta[j][i];
//                    System.out.println("löysi vapaan ruudun " + ruutunumero);
                    lauta[j][i] = merkki;
//                    System.out.println("sijoitetaan tämän pelaajan merkki " + merkki);
//                    System.out.println("ja saatiin lauta:");
//                    tulostaLauta(lauta);
//                    System.out.println("lähtee minmaxaamaan.");
                    int tulos = minimax(lauta, 0, false);
//                    int tulos = minimax(lauta, 1, ensimmaisenaVuorossa);
//                    System.out.println("valmis tämän ruudun minmax. tulos: " + tulos);
                    lauta[j][i] = ruutunumero;
                    if (tulos > parasTulos) {                        
                        parasRuutu = lauta[j][i];
                        parasTulos = tulos;
//                        System.out.println("saatiin uusi parasTulos:" + parasTulos + ". uusi paras siirto on ruutuun " + parasRuutu);
                    }
                }
            }
        }

        System.out.println("Yrittää sijoittaa ruutuun " + parasRuutu);
        return parasRuutu;
    } 

    private int minimax(String[][] lauta, int syvyys, boolean maximoi) {
//        tulostaLauta(lauta);
        // tarkistaa, onko voitettu (= päästy gametreen loppuun) 
//        System.out.println("käy jokaisen ruudun läpi, tarkistaa merkkien kohdalla onko voitettu");
        for (int j = 0; j < sivunPituus; j++) {
            for (int i = 0; i < sivunPituus; i++) {
                if (!onNumero(lauta[j][i])) {
//                    System.out.println("löysi merkin " + lauta[j][i]);
                    String voittaja = laskePisteet(lauta, j, i);
//                    System.out.println("voittaja: " + voittaja);
                    if (!voittaja.equals("kesken")) {
                        if (!voittaja.equals("tasa")) {
                            if (voittaja.equals(merkki)) {
//                                System.out.println("voittaja: " + voittaja);
//                                System.out.println("puun loppu. syvyys: " + syvyys + " lauta:");
//                                System.out.println("pelaaja voitti pisteillä" + (maksimivuorot-syvyys));
//                                tulostaLauta(lauta);
                                return maksimivuorot - syvyys;
                            } else {
//                                System.out.println("puun loppu. syvyys: " + syvyys + ". lauta:");
//                                tulostaLauta(lauta);
//                                System.out.println("vastustaja voitti pisteillä" + (-maksimivuorot+syvyys));
                                return -maksimivuorot + syvyys;
                            }
                        } else {
//                            System.out.println("puun loppu. syvyys: " + syvyys + ". lauta:");
//                            tulostaLauta(lauta);
//                            System.out.println("tasapeli");
                            return 0;
                        }

                    }

                }
            }
        }
//        System.out.println("ei löytänyt voittajaa.");
        if (maximoi) {
//            System.out.println("maximoi");
            String parasRuutu = "";
            int parasTulos = -100000;
//            System.out.println("käy ruudun läpi ja alkaa maksimoimaan kun löytyy vapaa ruutu");
            for (int i = 0; i < sivunPituus; i++) {
                for (int j = 0; j < sivunPituus; j++) {
                    // ruutu on vapaa, jos ruudun sisältö on numero
                    if (onNumero(lauta[j][i])) {
                        String ruutunumero = lauta[j][i];
                        lauta[j][i] = merkki;
//                        System.out.println("sijoitti: " + merkki + " ruutuun " + ruutunumero);
//                        tulostaLauta(lauta);
                        int tulos = minimax(lauta, syvyys+1, false);
//                        System.out.println("maksimissa minimax valmis. tulos: " + tulos);
                        lauta[j][i] = ruutunumero;
                        if (tulos > parasTulos) {
                            parasTulos = tulos;
                        }
                    }
                }
            }
//            System.out.println("coming up with parastulos " + parasTulos);
            return parasTulos;
        } else {
//            System.out.println("minimoi");
            String parasRuutu = "";
            int parasTulos = 100000;
 //           System.out.println("käy ruudun läpi ja alkaa minimoimaan kun löytyy vapaa ruutu");
            for (int i = 0; i < sivunPituus; i++) {
                for (int j = 0; j < sivunPituus; j++) {
                    // ruutu on vapaa, jos ruudun sisältö on numero
                    if (onNumero(lauta[j][i])) {
                        String ruutunumero = lauta[j][i];
                        lauta[j][i] = vastustajanMerkki;
//                        System.out.println("sijoitti: " + vastustajanMerkki + " ruutuun " + ruutunumero);
//                        tulostaLauta(lauta);
                        int tulos = minimax(lauta, syvyys+1, true);
                        lauta[j][i] = ruutunumero;
//                        System.out.println("minimissä minimax valmis. tulos: " + tulos);
                        if (tulos < parasTulos) {
                            parasTulos = tulos;
                        }
                    }
                }
            }
//            System.out.println("coming up with parastulos " + parasTulos);
            return parasTulos;
        }
    }

    private int tekoalySiirto() {
        return r.nextInt(9);
    }

    public String laskePisteet(String[][] lauta, int y, int x) {
        // rivi
        int rivisumma = 0;
        String voittajamerkki = null; 
        String tempm = lauta[y][x];
        for (int i = 0; i < sivunPituus; i++) {
//            System.out.println("laudalla: " + lauta[i][x]);
            if (tempm.equals(lauta[y][i])) {
//                System.out.println("rivisumma kasvaa");
                rivisumma++;
//                tempm = lauta[i][x];
                if (rivisumma == sivunPituus) {
                    voittajamerkki = lauta[y][i];
                    return lauta[y][i];
                }
            }
        }

        // sarake
        int sarakesumma = 0;
        for (int i = 0; i < sivunPituus; i++) {
            if (tempm.equals(lauta[i][x])) {
//                System.out.println("sarakesumma kasvaa");
                sarakesumma++;
                tempm = lauta[i][x];
                if (sarakesumma == sivunPituus) {
                    voittajamerkki = lauta[i][x];
                    return lauta[i][x];
                }
            }
        }

        int diagonaalisumma = 0;
        int kaanteisdiagonaalisumma = 0;
        if (onDiagonaalilla(y, x)) {
            // diagonaali [0,0],[1,1], [2,2]
            for (int i = 0; i < sivunPituus; i++) {
                if (tempm.equals(lauta[i][i])) {
//                    System.out.println("diagsumma kasvaa");
                    diagonaalisumma++;
                    tempm = lauta[i][i];
                    if (diagonaalisumma == sivunPituus) {
                        voittajamerkki = lauta[i][i];
                        return lauta[i][i];
                    }
                }
            }

            // käänteisdiagonaali [0,2], [1,1], [2,0]
            int i = 0;
            int j = sivunPituus-1;
            while (i < sivunPituus) {
                if (tempm.equals(lauta[j][i])) {
                    kaanteisdiagonaalisumma++;
//                    System.out.println("k-diagsumma kasvaa");
                    tempm = lauta[j][i];
                    if (kaanteisdiagonaalisumma == sivunPituus) {
                        voittajamerkki = lauta[j][i];
                        return lauta[j][i];
                    }
                }
                i++;
                j--;
            }
        }

//        System.out.println("rivi:" + rivisumma + ", sarake:" + sarakesumma + ", diag:" +diagonaalisumma + ", kdiag:" + kaanteisdiagonaalisumma);
/*
        if (rivisumma == sivunPituus 
            || sarakesumma == sivunPituus 
            || diagonaalisumma == sivunPituus
            || kaanteisdiagonaalisumma == sivunPituus
        ) {

            return voittajamerkki;
        }
*/
        if (laskeVapaatRuudut(lauta) == 0) {
            return "tasa";
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
