package ristinolla;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner lukija = new Scanner(System.in);
        UI ui = new UI(lukija);
        ui.kaynnista();
    }
    
}
