package ristinolla;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.util.Random;

public class TekoalypelaajaTest {

    private Tekoalypelaaja t;
    
    @Before 
    public void testiAlustus() {
        t = new Tekoalypelaaja("O");
    }

    @Test
    public void minimaxEstaaVoitonSarakkeella() {
        String parasRuutu = "";
        String[][] lauta = { 
            {"X", "O", "2"},
            {"X", "4", "5"},
            {"6", "7", "8"},
        };

        assertEquals(t.otaSyote(lauta), "6");
    }

    @Test
    public void minimaxEstaaVoitonRivilla() {
        String parasRuutu = "";
        String[][] lauta = { 
            {"X", "X", "2"},
            {"3", "O", "5"},
            {"6", "7", "8"},
        };

        assertEquals(t.otaSyote(lauta), "2");
    }

    @Test
    public void minimaxEstaaVoitonKDiagonaalilla() {
        String parasRuutu = "";
        String[][] lauta = { 
            {"0", "1", "X"},
            {"3", "X", "5"},
            {"6", "7", "O"},
        };

        assertEquals(t.otaSyote(lauta), "6");
    }

}
