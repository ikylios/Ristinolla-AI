package ristinolla;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class LogiikkaTest {

    String[][] testilauta;
    int sivunPituus;
    Pelilogiikka peli;

    @Before
    public void pelinJaTestilaudanAlustus() {
        peli = new Pelilogiikka();
        
        sivunPituus = 3;
        testilauta = new String[sivunPituus][sivunPituus];
        int luku = 0;
        for (int j = 0; j < sivunPituus; j++) {
            for (int i = 0; i < sivunPituus; i++) {
                testilauta[j][i] = "" + luku;
                luku++;
            }
        }
    }

    @Test
    public void alustaaLaudanOikein() {
        assertEquals(testilauta, peli.getLauta());
    }
    
    @Test
    public void vaihtaaVuoroaOikein() {
        assertEquals("X", peli.getVuoro());
        peli.onnistunutSiirto("4");
        assertEquals("O", peli.getVuoro());
    }

    @Test
    public void salliiSijoituksenVapaaseenRuutuun() {
        assertTrue(peli.onnistunutSiirto("4"));
    }

    @Test
    public void eiSalliSijoitustaVarattuunRuutuun() {
        peli.onnistunutSiirto("4");
        assertFalse(peli.onnistunutSiirto("4"));
    }

    @Test
    public void voittotilanneTasapeliKunLautaTäynnä() {
        String syöte = "480352176";
        for (int i = 0; i < syöte.length(); i++) {
            peli.onnistunutSiirto(syöte.substring(i, i+1));
        }
        assertEquals("tasapeli", peli.getVoittotilanne());
    }

    @Test
    public void xVoittoRivilla() {
        String syöte = "41325";
        for (int i = 0; i < syöte.length(); i++) {
            peli.onnistunutSiirto(syöte.substring(i, i+1));
        }
        assertEquals("X", peli.getVoittotilanne());
    }

    @Test
    public void oVoittoSarakkeella() {
        String syöte = "456218";
        for (int i = 0; i < syöte.length(); i++) {
            peli.onnistunutSiirto(syöte.substring(i, i+1));
        }
        assertEquals("O", peli.getVoittotilanne());
    }
    
    @Test
    public void xVoittoDiagonaalilla() {
        String syöte = "845276";
        for (int i = 0; i < syöte.length(); i++) {
            peli.onnistunutSiirto(syöte.substring(i, i+1));
        }
        assertEquals("O", peli.getVoittotilanne());
    }
}