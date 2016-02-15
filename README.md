OOP Jali Rainio, Roni Ronkainen, Samuli Virtanen
Yatzy
Luokat
Noppa, pisteidenlaskija, main

Metodit

Kaikkien noppien heitto

Tiettyjen noppien reroll

Nopalle numeron arpominen

Käden tarkastavat metodit

Käden yhteenlasku

Toimintaperiaate

Pyrimme toutettamaan Yatzy-pelin, muutamaa sääntöä mahdollisesti muuttaen.

Pelissä on kaksi pelaajaa. Ensimmäisessä vaiheessa molemmilla on kolme vuoroa, jotka
tapahtuvat vuorotellen, joiden jälkeen siirrytään toiseseen vaiheeseen. Toinen vaihe jatkuu,
kunnes molemmat pelaajat ovat täyttäneet pöytäkirjansa. Peli loppuu molempien pelaajien
taulujen täytyttyä ja enemmän pisteitä kerännyt pelaaja voittaa.

Vuorollaan pelaaja heittää viisi noppaa. Nopat ovat olioita, jotka arpovat itselleen arvon väliltä
1-6. Heiton jälkeen pelaajalle tarjotaan kahdesti mahdollisuus heittää noppia uudestaan.
Molemmilla pelaajilla on oma ArrayList, johon noppien silmäluvut tallennetaan vuoron lopuksi.

Kun pelaaja on joko heittänyt kolme kertaa tai lopettanut vuoronsa, lähetetään listan tiedot
laskumetodille. Metodi tarkistaa nopat ja laskee, paljonko pisteitä kukin käsi tarjoaa pelaajalle.

Tämän jälkeen metodi palauttaa int[]-taulukon, jossa jokaisen käden arvoa kuvaa solu
taulukossa. Pelaaja valitsee, minkä käden hän haluaa, ja käden arvo lisätään hänen
kokonaispistemääräänsä. 

Samaa kättä ei saa valita kahdesti, joten ennen pistelaskua booleantaulusta tarkistetaan pelaajan aikaisemmat valinnat.

Vuoron vaihto toteutetaan vaihtamalla aktiiviseksi toisen pelaajan pisteitä laskeva ArrayList.

Pelin tallentaminen toteutetaan kirjoittamalla toiseen tiedostoon tieto pelaajien pöytäkirjoista
sekä kumman vuoroon peli jäi.
