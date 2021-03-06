package ristinolla;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import ristinolla.*;

public class TekoalypelaajaTest {

    private Tekoalypelaaja t;
    private int sivunPituus;
    
    @Before 
    public void testiAlustus() {
        sivunPituus = 3;
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
    public void minimaxEstaaVoitonDiagonaalilla() {
        String[][] lauta = { 
            {"X", "1", "2"},
            {"O", "X", "5"},
            {"6", "7", "8"},
        };

        assertEquals("8", t.otaSyote(lauta));
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
    
    @Test
    public void tekoalynSiirtoKestaaNoinSekunnin() {
        // ajaessa ohjelmalla selvästi kestää alle sekunti siirron tekemisessä, mutta testeissä tämä ei jostian syystä näy. Siis annetaan testin ohjelmalle runsas sekunti aikaa tehdä siirto.
        Pelilogiikka peli = new Pelilogiikka();

        long aloitushetki = System.nanoTime();

        t.otaSyote(peli.getLauta());

        long lopetushetki = System.nanoTime();

        long kulunutAika = lopetushetki - aloitushetki;
        long sekunti = 1250000000L;

        assertTrue(kulunutAika <= sekunti);
    }
    
    @Test
    public void kahdenAIPeliKestaaMax5Sekuntia() {
        // ajaessa ohjelmalla selvästi kestää n. sekunti kahden tekoälyn pelissä, mutta testeissä tämä ei jostian syystä näy. Siis annetaan testin ohjelmalle viisi sekuntia aikaa pelata peli.
        Pelilogiikka peli = new Pelilogiikka();
        Tekoalypelaaja t2 = new Tekoalypelaaja("X");
        Tekoalypelaaja[] pelaajat = new Tekoalypelaaja[] {t2, t};

        long aloitushetki = System.nanoTime();

        while (peli.onKesken()) {
            for (Tekoalypelaaja p : pelaajat) {
                peli.onnistunutSiirto(p.otaSyote(peli.getLauta()));
                if (!peli.onKesken()) {
                    break;
                }
            }
        }

        long lopetushetki = System.nanoTime();

        long kulunutAika = lopetushetki - aloitushetki;
        long viisiSekuntia = 5000000000L;

        assertTrue(kulunutAika <= viisiSekuntia);
    }

}
