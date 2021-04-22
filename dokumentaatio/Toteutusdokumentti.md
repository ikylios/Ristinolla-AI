## Toteutusdokumentti

### Yleisrakenne
UI alustaa pelilogiikan sekä pelaajat (2 ihmistä, 1 ihminen ja 1 tekoäly, 2 tekoälyä). Aloittava pelaaja perinteen mukaan on X. Tällä hetkellä ihmispelaaja aina aloittaa. UI vastaanottaa syötteen pelaajilta vuorotellen. Tekoälypelaaja syötettä pyytäessä laskee seuraavan parhaan siirron minimax-algoritmin ja alfa-beta-karsinnan avulla. Jokaisen siirron jälkeen pelilogiikka tarkistaa, onko voitettu tai saavuttu tasapeliin. Jos peli on ohi, muutetaan voittaja-muuttujaa ja vaihdetaan onKesken-muuttujan arvo falseksi. Tällöin UI:n pelisilmukka keskeytyy ja siirrytään peliOhi-metodiin. Metodi ilmoittaa voittajan ja näyttää lopullisen laudan.

### Tekoälypelaaja ja minimax
Minimax-algoritmi aloitetaan otaSyote()-metodissa. Metodi saa argumenttina laudan, jota käytetään minimaxissa. Huomioitavana on, että minimax käyttää voittotilanteen tarkistamiseen melko samanlaista metodia kuin pelilogiikan laskePisteet(), mutta muutamia kohtia on muokattu.


### Saavutetut aika- ja tilavaativuudet
Minimaxin analyysi, yksittäinen kutsu:
- voittajan tarkistaminen 2 sisäkkäistä for-loop -> O(ij) = O(sivunpituus^2) = O(n^2)
- jonka jälkeen joko maksimoi O(ij- eiVapaatRuudut) = O(n^2) tai minimoi O(ij- eiVapaatRuudut) = O(n^2)

Minimaxia kutsutaan ij-varatutRuudut kertaa = maksimissaan O(ij-1) = O(n^2) kertaa. Siis tekoälyn siirtoon menee aikaa O(n^2)\*O(n^2) - alfa-beta-karsitut minimax-kutsut = O(n^4).

### Mahdolliset puutteet ja parannusehdotukset
- Tekoälypelaaja käyttää vapaiden ruutujen laskemiseen kahta sisäkkäistä for-looppia, joiden karsiminen on varmasti mahdollista.

### Tietolähteet
[Video minimax-algoritmista ja alfa-beta-karsinnasta](https://www.youtube.com/watch?v=l-hh51ncgDI&ab_channel=SebastianLague) 
