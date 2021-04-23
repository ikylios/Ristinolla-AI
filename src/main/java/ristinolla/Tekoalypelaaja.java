package ristinolla;

public class Tekoalypelaaja implements Pelaaja {

    String merkki;
    String vastustajanMerkki;
    int sivunPituus;
    int maksimivuorot;
    int vapaatRuudut;

    public Tekoalypelaaja(String m, int sivunPituus) {
        merkki = m;
        if (merkki.equals("X")) {
            vastustajanMerkki = "O";
        } else {
            vastustajanMerkki = "X";
        }
        vapaatRuudut = sivunPituus*sivunPituus;
        maksimivuorot = vapaatRuudut+1;
    }
    
    /**
     * Aloittaa minimax-algoritmin. Käy parametrina annetun 
     * laudan läpi. Jos löydetään vapaa ruutu (sisältö on numero), 
     * niin tutkitaan minimaxin avulla siirron kannattavuus. 
     * Jos tutkitun ruudun pisteet ovat paremmat kuin aikaisemman 
     * parhaan ruudun pisteet, korvataan paras ruutu tällä uudella 
     * ruudulla. Kun on käynyt koko laudan läpi, palauttaa parhaan 
     * ruudun numeron.
     */
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
                    int tulos = minimax(lauta, 0, false, 10000, -10000, vapaatRuudut-1);
                    lauta[j][i] = ruutunumero;
                    if (tulos > parasTulos) {                        
                        parasRuutu = lauta[j][i];
                        parasTulos = tulos;
                    }                    
                }
            }
        }
        System.out.println("Sijoittaa ruutuun " + parasRuutu);
        vapaatRuudut--;
        return parasRuutu;
    }

    /**
     * Aluksi käy argumenttina annetun laudan läpi ja tutkii, onko
     * peli ohi. Jos maksimoiva pelaaja on voittanut, palauttaa
     * maksimivuorojen määrän (10) ja rekursion syvyyden erotuksen. 
     * Jos minimoiva pelaaja on voittanut, palautetaan negatiivinen
     * maksimivuorojen määrän (-10) ja rekursion syvyyden summa.
     * Jos päädyttiin tasapeliin, palauttaa 0. Jos peli on vielä
     * kesken, jatkaa suoritusta. Riippuen onko vuorossa minimoivan
     * vai maksimoivan pelaajan vuoro, suorittaa minimaxin uudestaan
     * uusilla parametreilla. Maksimoiva pelaaja valitsee suurimman
     * mahdollisen pistemäärän, kun taas minimoiva pelaaja valitsee
     * pienimmän mahdollisen pistemäärän. Maksimoivan pelaajan jälkeen
     * on minimoivan pelaajan vuoro, jonka jälkeen on taas maksimoivan
     * pelaajan vuoro jne.
     */
    private int minimax(String[][] lauta, int syvyys, boolean maximoi, int alpha, int beta, int vapaat) {
        // tarkistaa, onko voitettu (= päästy gametreen loppuun) 
        for (int j = 0; j < sivunPituus; j++) {
            for (int i = 0; i < sivunPituus; i++) {
                if (!onNumero(lauta[j][i])) {
                    int voittaja = laskePisteet(lauta, j, i, vapaat);                                        
                    if (voittaja != 2) {                // peli on päättynyt jotenkin
                        int pelinPisteet = 0;           // oletusarvoisesti tasapeli 
                        switch (voittaja) {
                            case 1: pelinPisteet = maksimivuorot - syvyys;      // oman merkin pelaaja on voittanut
                            case -1: pelinPisteet = -maksimivuorot + syvyys;    // vastustaja on voittanut
                        }
                        return pelinPisteet;
                    }                    
                }     
            }
        }
        
        
        if (maximoi) {
            int parasTulos = -100000;
            for (int j = 0; j < sivunPituus; j++) {
                for (int i = 0; i < sivunPituus; i++) {
                    if (onNumero(lauta[j][i])) {
                        String ruutunumero = lauta[j][i];
                        lauta[j][i] = merkki;                        
                        int tulos = minimax(lauta, syvyys+1, false, alpha, beta, vapaat-1);
                        lauta[j][i] = ruutunumero;
                        if (tulos > parasTulos) {
                            parasTulos = tulos;
                            if (parasTulos > alpha) {
                                alpha = parasTulos;
                            }
                            if (beta <= alpha) {
                                break;
                            }
                        }
                    }
                }
            }
            return parasTulos;

        } else {
            int parasTulos = 100000;
            for (int j = 0; j < sivunPituus; j++) {
                for (int i = 0; i < sivunPituus; i++) {
                    if (onNumero(lauta[j][i])) {
                        String ruutunumero = lauta[j][i];
                        lauta[j][i] = vastustajanMerkki;
                        int tulos = minimax(lauta, syvyys+1, true, alpha, beta, vapaat-1);
                        lauta[j][i] = ruutunumero;
                        if (tulos < parasTulos) {
                            parasTulos = tulos;
                        }
                        if (parasTulos < beta) {
                            beta = parasTulos;
                        }
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            return parasTulos;
        }
    }

    /**
     * Tarkistaa, onko voitettu. Palauttaa voittaneen merkin merkkijonon,
     * tasapelin, tai pelin olevan vielä kesken.
     */
    public int laskePisteet(String[][] lauta, int y, int x, int vapaat) {
        String tutkittavaMerkki = lauta[y][x];
        
        // rivi
        int rivisumma = 0;
        for (int i = 0; i < sivunPituus; i++) {
            if (tutkittavaMerkki.equals(lauta[y][i])) {
                rivisumma++;
                tutkittavaMerkki = lauta[y][i];
                if (rivisumma == sivunPituus) {
                    return merkkiToInt(tutkittavaMerkki);
                }
            }
        }

        // sarake
        int sarakesumma = 0;
        for (int i = 0; i < sivunPituus; i++) {
            if (tutkittavaMerkki.equals(lauta[i][x])) {
                sarakesumma++;
                tutkittavaMerkki = lauta[i][x];
                if (sarakesumma == sivunPituus) {
                    return merkkiToInt(tutkittavaMerkki);
                }
            }
        }

        if (onDiagonaalilla(y, x)) {
            // diagonaali [0,0],[1,1], [2,2]
            int diagonaalisumma = 0;
            for (int i = 0; i < sivunPituus; i++) {
                if (tutkittavaMerkki.equals(lauta[i][i])) {
                    diagonaalisumma++;
                    tutkittavaMerkki = lauta[i][i];
                    if (diagonaalisumma == sivunPituus) {
                        return merkkiToInt(tutkittavaMerkki);
                    }
                }
            }

            // käänteisdiagonaali [0,2], [1,1], [2,0]
            int kaanteisdiagonaalisumma = 0;
            int i = 0;
            int j = sivunPituus-1;
            while (i < sivunPituus) {
                if (tutkittavaMerkki.equals(lauta[j][i])) {
                    kaanteisdiagonaalisumma++;
                    tutkittavaMerkki = lauta[j][i];
                    if (kaanteisdiagonaalisumma == sivunPituus) {
                        return merkkiToInt(tutkittavaMerkki);
                    }
                }
                i++;
                j--;
            }
        }
        if (vapaat == 0) {
            return 0;
        }
        
        return 2;
    }

    public int merkkiToInt(String syote) {
        if (syote.equals(merkki)) {
            return 1;
        }
        return -1;
    }

    /**
    * Apumetodi diagonaaliruutujen tunnistamiseen.
     */ 
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

    /**
     * Apumetodi numeroiden tunnistamiseen.
     */
    private boolean onNumero(String syote) {
        try {
            Integer.parseInt(syote);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
