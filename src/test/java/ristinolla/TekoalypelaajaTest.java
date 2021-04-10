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
        t = new Tekoalypelaaja(new Random(4));
    }

    @Test
    public void ottaaSyotteenKoneelta() {
        assertEquals(t.otaSyote(), "8");
    }

}
