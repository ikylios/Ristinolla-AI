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

        assertEquals("6", t.otaSyote(lauta));
    }

    @Test
    public void minimaxEstaaVoitonRivilla() {
        String[][] lauta = { 
            {"X", "X", "2"},
            {"3", "O", "5"},
            {"6", "7", "8"},
        };

        assertEquals("2", t.otaSyote(lauta));
    }

    @Test
    public void minimaxEstaaVoitonKDiagonaalilla() {
        String[][] lauta = { 
            {"0", "1", "X"},
            {"3", "X", "5"},
            {"6", "7", "O"},
        };

        assertEquals("6", t.otaSyote(lauta));
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

        assertEquals(0, peli.kukaVoitti());
    }

}
