package ristinolla;

public class Pelilogiikka {

    private String[][] lauta;
    private boolean onKesken;
    private boolean oVuoro;
    private int sivunPituus;
    private int pieninIndeksi;
    private int suurinIndeksi;
    private int vapaatRuudut;
    private String voittotilanne;

    public Pelilogiikka(int ihmispelaajienMaara) {
        sivunPituus = 3;
        lauta = new String[sivunPituus][sivunPituus];
        int luku = 0;
        for (int i = 0; i < sivunPituus; i++) {
            for (int j = 0; j < sivunPituus; j++) {
                lauta[j][i] = "" + luku;
                luku++;
            }
        }
        pieninIndeksi = 0;
        suurinIndeksi = luku;
        vapaatRuudut = sivunPituus*sivunPituus;
        voittotilanne = "";

        /*
        // Luo taulukon, jossa kaikkien mahdollisten indeksien arvot
        indeksit = new String[suurinIndeksi];
        for (int i = 0; i < indeksit.length; i++) {
            indeksit[i] = "" + i;
        }
        */

        onKesken = true;
        oVuoro = false;
    }  

    /**
     * Palauttaa true, jos onnistuneesti saatu asetettua X tai O 
     * laudalle. Vähentää vapaiden ruutujen lukumäärää, tarkistaa
     * onko kumpikaan pelaaja voittanut, sekä togglaa vuoron 
     * toiselle merkille.
    */
    public boolean onnistunutSiirto(String ruutunumero) {
        if (onValidiIndeksi(ruutunumero)) {
            for (int i = 0; i < sivunPituus; i++) {
                for (int j = 0; j < sivunPituus; j++) {
                    String laudanNumero = lauta[j][i];
//                    System.out.println("laudannumero: " + laudanNumero + ", ruutunumero: " + ruutunumero);
                    if (laudanNumero.equals(ruutunumero)) {
//                        System.out.println("laudannumero ja ruudunnumero samat");
                        lauta[j][i] = getVuoro();
                        vapaatRuudut--;
                        System.out.println("vapaatruudut:" + vapaatRuudut);
                        tarkistaVoittotilanne(j, i);
                        toggleVuoro();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
    * Tarkistaa, onko annettu ruutu vapaana. Ruutu on vapaa, jos
    * syöte on väliltä [pienin indeksi, suurin indeksi].
    */
    public boolean onValidiIndeksi(String syote) {
        /*
        for (int i = 0; i < indeksit.length; i++)  {
            if (indeksit[i].equals(syote)) {
               return true;
            }
        }
        
        return false;
        */

        try {
           int syoteArvo = Integer.parseInt(syote);
           if (syoteArvo >= pieninIndeksi && syoteArvo <= suurinIndeksi) {
               return true;
           }
        } catch (Exception e) {
        
        }
        return false;
    }

    /**
     * Laskee pelaajien pisteet. Määrittää, päättyikö peli X-merkin 
     * voittoon, O-merkin voittoon vai tasapeliin. Muuttaa 
     * voittotilanne-muuttujan arvon voittokondition perusteella.
     * Vaihtaa onKesken-arvon falseksi, sillä peli keskeytyy.
    */
    public void tarkistaVoittotilanne(int y, int x) {
        if (laskePisteet(y, x)) {
            setVoittotilanne(getVuoro());
            onKesken = false;
        }

        if (vapaatRuudut == 0) {
            setVoittotilanne("tasapeli");
            onKesken = false;
        }
    }
    
    /**
     * Laskee annetun koordinaatin rivin summan ja sarakkeen summan. 
     * Tämän jälkeen tarkistaa, onko annettu koordinaatti 
     * diagonaalilla. Jos koordinaatti on diagonaalilla, laskee 
     * niiden summat. Jos vähintään yksi näistä summista on yhtä
     * suuri kuin sivun pituus (3), niin tämän vuoron pelaaja on
     * voittanut. Tällöin palauttaa true, muutoin false.
     */
    public boolean laskePisteet(int y, int x) {
        
        // rivi
        int rivisumma = 0;
        for (int i = 0; i < sivunPituus; i++) {
//            System.out.println("lauta: " + lauta[i][x]);
            if (getVuoro().equals(lauta[i][x])) {
                rivisumma += 1;
            }
        }

        // sarake
        int sarakesumma = 0;
        for (int i = 0; i < sivunPituus; i++) {
            if (getVuoro().equals(lauta[y][i])) {
                sarakesumma += 1;
            }
        }

        int diagonaalisumma = 0;
        int kaanteisdiagonaalisumma = 0;
        if (onDiagonaalilla(y, x)) {
            // diagonaali [0,0],[1,1], [2,2]
            for (int i = 0; i < sivunPituus; i++) {
                if (getVuoro().equals(lauta[i][i])) {
                    diagonaalisumma += 1;
                }
            }

            // käänteisdiagonaali [0,2], [1,1], [2,0]
            int j = 0;
            int i = sivunPituus-1;
            while (j < sivunPituus) {
                if (getVuoro().equals(lauta[j][i])) {
                    kaanteisdiagonaalisumma += 1;
                }
                i--;
                j++;
            }
        }

//        System.out.println("rivi:" + rivisumma + ", sarake:" + sarakesumma + ", diag:" +diagonaalisumma + ", kdiag:" + kaanteisdiagonaalisumma);

        if (rivisumma == sivunPituus 
            || sarakesumma == sivunPituus 
            || diagonaalisumma == sivunPituus
            || kaanteisdiagonaalisumma == sivunPituus
        ) {
            return true;
        }

        return false;
    }
    
    /**
     * Tarkistaa, onko annettu koordinaatti diagonaalilla.
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

    public void toggleVuoro() {
        oVuoro = !oVuoro;
    }

    public String getVuoro() {
        if (oVuoro) {
            return "O";
        }
        return "X";
    }

    public boolean onKesken() {
        return onKesken;
    }

    public String getVoittotilanne() {
        return voittotilanne;
    }
    
    public void setVoittotilanne(String syote) {
        voittotilanne = syote;
    }

    public String[][] getLauta() {
        return lauta;
    }

}