package ristinolla;

public class Pelilogiikka {

    private String[][] lauta;
    private boolean onKesken;
    private boolean Ovuoro;
    private int sivunpituus;
    private int pieninIndeksi;
    private int suurinIndeksi;
    private int vapaatRuudut;
    private String voittotilanne;
//    private String[] indeksit;

    public Pelilogiikka() {
//        sivunPituus = 3;
        int sivunPituus = 3;

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

        /**
         * Luo taulukon, jossa kaikkien mahdollisten indeksien arvot
         */
        indeksit = new String[suurinIndeksi];
        for (int i = 0; i < indeksit.length; i++) {
            indeksit[i] = "" + i;
        }

        onKesken = true;
        Ovuoro = false;
    }  

    /**
     * Palauttaa true, jos onnistuneesti saatu asetettua X tai O 
     * laudalle. Vähentää vapaiden ruutujen lukumäärää, tarkistaa
     * onko kumpikaan pelaaja voittanut, sekä togglaa vuoron 
     * toiselle merkille.
    */
    public boolean onnistunutSiirto(String ruutunumero) {
        int sivunPituus = 3;

        if (onValidiIndeksi(ruutunumero)) {
            for (int i = 0; i < sivunPituus; i++) {
                for (int j = 0; j < sivunPituus; j++) {
                    String laudanNumero = lauta[j][i];
//                    System.out.println("laudannumero: " + laudanNumero + ", ruutunumero: " + ruutunumero);
                    if (laudanNumero.equals(ruutunumero)) {
//                        System.out.println("laudannumero ja ruudunnumero samat");
                        lauta[j][i] = getVuoro();
                        vapaatRuudut--;
                        tarkistaVoittotilanne();
                        toggleVuoro();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Tarkistaa, onko annettu ruutu vapaana.
    */
    public boolean onValidiIndeksi(String syöte) {
        /*
        for (int i = 0; i < indeksit.length; i++)  {
            if (indeksit[i].equals(syöte)) {
               return true;
            }
        }
        
        return false;
        */

        try {
           int syöteArvo = Integer.parseInt(syöte);
           if (syöteArvo >= pieninIndeksi && syöteArvo <= suurinIndeksi) {
               return true;
           }
        } catch (Exception e) {
        
        }
        
        return false;
    }

    /**
     * Määrittää, päättyikö peli X-merkin voittoon, 
     * O-merkin voittoon vai tasapeliin. Muuttaa 
     * voittotilanne-muuttujan voittokondition perusteella.
    */
    public void tarkistaVoittotilanne() {
        String tulos = "";
        if (vapaatRuudut == 0) {
            tulos = "tasapeli";
            voittotilanne = tulos;
            onKesken = false;
        }


    }


    public void toggleVuoro() {
        Ovuoro = !Ovuoro;
    }

    public boolean pelaajanVuoro() {
        if (getVuoro().equals("X")) {
            return true;
        }
        return false;
    }

    public String getVuoro() {
        if (Ovuoro) {
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

    public String[][] getLauta() {
        return lauta;
    }

}