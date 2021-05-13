## Testausdokumentti

### Pelilogiikka
Käyttää suurimmissa osissa tyhjää lautaa, tai testikohtaisia erilaisia lautatilanteita. Testataan
- alustaa laudan oikein, eli numeroi ruudut 0-8.
- hyväksyy siirron vapaaseen ruutuun
- ei hyväksy siirtoa varattuun ruutuun
- vaihtaa vuoroa sallitun siirron jälkeen
- tunnistaa tasapelin, voiton rivillä, sarakkeessa, diagonaalilla sekä käänteisdiaagonaalilla.

### Tekoälypelaaja
Käyttää testikohtaisesti erilaisia lautatilanteita. Testataan
- estää vastustajan voiton sarakkeella, rivillä, diagonaalilla sekä käänteisdiagonaalilla.
- päätyy tasapeliin kahden tekoälyn pelissä
- tekoalyn siirto kestää enintään noin sekunnin - mitä enemmän vapaita paikkoja laudalla on, sitä kauemmin minimax kestää: pelin ensimmäinen siirto kestää pisimpään
- kahden tekoalyn peli kestää alle viisi sekuntia 
