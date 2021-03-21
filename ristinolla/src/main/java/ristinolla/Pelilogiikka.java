package ristinolla;

public class Pelilogiikka {

    private int[][] lauta;
    private boolean onKesken;
    private boolean Ovuoro;

    public Pelilogiikka() {
        int sivunPituus = 3;

        lauta = new int[sivunPituus][sivunPituus];
        int luku = 0;
        for (int i = 0; i < sivunPituus; i++) {
            for (int j = 0; j < sivunPituus; j++) {
                lauta[j][i] = luku;
                luku++;
            }
        }

        onKesken = true;
        Ovuoro = false;
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

    public int[][] getLauta() {
        return lauta;
    }

}