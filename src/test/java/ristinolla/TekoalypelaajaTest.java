package ristinolla;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.util.Random;
import ristinolla.*;

public class TekoalypelaajaTest {

    private Tekoalypelaaja t;
    
    @Before 
    public void testiAlustus() {
        t = new Tekoalypelaaja("O");
    }

    @Test
    public void minimaxEstaaVoitonSarakkeella() {
        String[][] lauta = { 
            {"X", "O", "2"},
            {"X", "4", "5"},
            {"6", "7", "8"},
        };

        assertEquals(t.otaSyote(lauta), "6");
    }

    @Test
    public void minimaxEstaaVoitonRivilla() {
        String[][] lauta = { 
            {"X", "X", "2"},
            {"3", "O", "5"},
            {"6", "7", "8"},
        };

        assertEquals(t.otaSyote(lauta), "2");
    }

    @Test
    public void minimaxEstaaVoitonKDiagonaalilla() {
        String[][] lauta = { 
            {"0", "1", "X"},
            {"3", "X", "5"},
            {"6", "7", "O"},
        };

        assertEquals(t.otaSyote(lauta), "6");
    }

    @Test
    public void tasapeliKahdenAInPelissa() {
        Pelilogiikka peli = new Pelilogiikka();
        Tekoalypelaaja t2 = new Tekoalypelaaja("X");
        Tekoalypelaaja[] pelaajat = new Tekoalypelaaja[] {t2, t};

        while (peli.onKesken()) {
            for (Tekoalypelaaja p : pelaajat) {
                peli.onnistunutSiirto(p.otaSyote(peli.getLauta()));
                if (!peli.onKesken()) {
                    break;
                }
            }
        }            

        assertEquals(peli.kukaVoitti(), 0);
    }

}
