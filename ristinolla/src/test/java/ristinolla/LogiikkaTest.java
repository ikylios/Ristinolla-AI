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
        for (int i = 0; i < sivunPituus; i++) {
            for (int j = 0; j < sivunPituus; j++) {
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
    public void pelaajanMerkkiOnX() {
        assertTrue(peli.pelaajanVuoro());
        peli.onnistunutSiirto("4");
        assertFalse(peli.pelaajanVuoro());
    }

    @Test
    public void vaihtaaVuoroaOikein() {
        assertEquals("X", peli.getVuoro());
        peli.onnistunutSiirto("4");
        assertEquals("O", peli.getVuoro());
    }

    @Test
    public void salliiValidinSijoituksen() {
        assertTrue(peli.onnistunutSiirto("4"));
    }

    @Test
    public void eiSalliVarattuaSijoitusta() {
        peli.onnistunutSiirto("4");
        assertFalse(peli.onnistunutSiirto("4"));
    }


}