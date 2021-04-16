package ristinolla;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import java.util.Scanner;
import java.io.ByteArrayInputStream;
import static org.mockito.Mockito.*;

public class IhmispelaajaTest {

    private Ihmispelaaja i;
    private Scanner mockScanner;
    
    @Before 
    public void testiAlustus() {
        mockScanner = mock(Scanner.class);
        i = new Ihmispelaaja(mockScanner);
    }

    @After
    public void testiPurku() {
        System.setIn(System.in);
    }

    @Test
    public void ottaaSyotteenIhmiselta() {
        when(mockScanner.nextLine()).thenReturn("4");
        
        String vastaus = i.otaSyote(new String[3][3]);
        
        assertEquals(vastaus, "4");
    }

}