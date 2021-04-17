package ristinolla;

public class Pelilogiikka {

    private String[][] lauta;
    private boolean onKesken;
    private boolean oVuoro;
    private int sivunPituus;
    private int pieninIndeksi;
    private int suurinIndeksi;
    private int vapaatRuudut;
    private int voittaja;

    public Pelilogiikka() {
        sivunPituus = 3;
        lauta = new String[sivunPituus][sivunPituus];
        int luku = 0;
        for (int j = 0; j < sivunPituus; j++) {
            for (int i = 0; i < sivunPituus; i++) {
                lauta[j][i] = "" + luku;
                luku++;
            }
        }
/*        
        lauta[0] = new String[] {"0", "1", "2"};  
        lauta[1] = new String[] {"3", "4", "5"};  
        lauta[2] = new String[] {"6", "7", "8"};  
*/        
        
        pieninIndeksi = 0;
        suurinIndeksi = luku;
        vapaatRuudut = sivunPituus*sivunPituus;
        voittaja = 2;

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
            for (int j = 0; j < sivunPituus; j++) {
                for (int i = 0; i < sivunPituus; i++) {
                    String laudanNumero = lauta[j][i];
                    if (laudanNumero.equals(ruutunumero)) {
                        lauta[j][i] = getVuoro();
                        vapaatRuudut--;
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
            if (getVuoro().equals("X")) {
                voittaja = 1;
            } else {
                voittaja = -1;
            }
            onKesken = false;
        }

        if (vapaatRuudut == 0) {
            voittaja = 0;
            onKesken = false;
        }

        if (voittaja != 2) {
            onKesken = false;
            setVoittaja(voittaja);
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
            if (getVuoro().equals(lauta[y][i])) {
                rivisumma++;
            }
        }

        // sarake
        int sarakesumma = 0;
        for (int i = 0; i < sivunPituus; i++) {
            if (getVuoro().equals(lauta[i][x])) {
                sarakesumma++;
            }
        }

        int diagonaalisumma = 0;
        int kaanteisdiagonaalisumma = 0;
        if (onDiagonaalilla(y, x)) {
            // diagonaali [0,0],[1,1], [2,2]
            for (int i = 0; i < sivunPituus; i++) {
                if (getVuoro().equals(lauta[i][i])) {
                    diagonaalisumma++;
                }
            }

            // käänteisdiagonaali [0,2], [1,1], [2,0]
            int i = 0;
            int j = sivunPituus-1;
            while (i < sivunPituus) {
                if (getVuoro().equals(lauta[j][i])) {
                    kaanteisdiagonaalisumma++;
                }
                i++;
                j--;
            }
        }

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

    public int kukaVoitti() {
        return voittaja;
    }
    
    public void setVoittaja(int syote) {
        voittaja = syote;
    }

    public String[][] getLauta() {
        return lauta;
    }

}