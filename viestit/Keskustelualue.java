package oope2018ht.viestit;

import oope2018ht.apulaiset.Getteri;
import oope2018ht.apulaiset.Setteri;
import oope2018ht.omalista.*;
import oope2018ht.tiedostot.*;

/**
 * Keskustelualuetta kuvaava luokka, mik� sis�lt�� erilaisia siihen kohdistuvia
 * metodeja.
 * <p>
 * Harjoitusty�, Olio-ohjelmoinnin perusteet, kev�t 2018.
 * <p>
 * 
 * @author Oskari Niskanen (Niskanen.Oskari.T@student.uta.fi) Luonnontieteiden
 *         tiedekunta, Tampereen yliopisto.
 */
public class Keskustelualue {
    /** Keskustelualueella sijatsevien viestiketjujen lista */
    private OmaLista viestiketjut;
    /** Ajohetkell� aktiivinen viestiketju, mihin kohdistetaan toimintoja. */
    private Viestiketju aktiivinenKetju;
    /** Viesteille annettavat tunnukset */
    private int tunniste;
    /** Viestiin liitetyn tiedoston nimi */
    private String liiteTiedosto;

    /**
     * Oletusrakentaja keskustelualueelle.
     * 
     * Asettaa tunnisteeksi numeron yksi ja luo alustaa viestiketjuille listan.
     */
    public Keskustelualue() {
        tunniste = 1;
        viestiketjut = new OmaLista();
    }

    /**
     * Palauttaa liitetiedosto-merkkijonon komennon kutsujalle.
     * 
     * @return liiteTiedosto, merkkijonoesitys tiedoston nimest�.
     */
    @Getteri
    public String haeTiedostoNimi() {
        return liiteTiedosto;
    }

    /**
     * Asettaa tiedoston nimen parametrinaan saaduksi nimeksi.
     * 
     * @param nimi,
     *            tiedoston nimi.
     * @throws IllegalArgumentException,
     *             jos nimen pituus on alle yhden.
     */
    @Setteri
    public void asetaTiedostoNimi(String nimi) throws IllegalArgumentException {
        if (nimi.length() > 0) {
            liiteTiedosto = nimi;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Metodi lis�� uuden viestiketjun keskustelualueelle.
     * 
     * @param ketjunNimi,
     *            viestiketjun aihe merkkijonona.
     * @throws IllegalArgumentException,
     *             jos tulee virheit� ketjun luomisessa.
     */
    public void lisaaViestiketju(String ketjunNimi) throws IllegalArgumentException {
        try {
            Viestiketju ketju = new Viestiketju(ketjunNimi, (viestiketjut.koko() + 1));
            viestiketjut.lisaaLoppuun(ketju);
            aktiivinenKetju = ketju;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Valitsee aktiivisen viestiketjun parametrinaan saadun numeron avulla.
     * 
     * @param numero,
     *            luku jolla yksil�id��n valittava aktiivinen ketju.
     * @throws IllegalArgumentException,
     *             jos parametri on virheellinen.
     */
    public void valitseAktiivinen(int numero) throws IllegalArgumentException {
        if (numero > 0 && numero <= viestiketjut.koko()) {
            aktiivinenKetju = (Viestiketju) viestiketjut.alkio((numero) - 1);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Tulostaa kaikkien alueelle luotujen viestiketjujen tiedot.
     */
    public void tulostaViestiketjut() {
        if (viestiketjut != null) {
            int koko = viestiketjut.koko();
            for (int i = 0; i < koko; i++) {
                Object alkio = viestiketjut.alkio(i);
                System.out.println(alkio);
            }
        }

    }

    /**
     * Luo uuden tiedoston ja palauttaa viitteen siihen.
     * 
     * @return kuvaTiedosto/videoTiedosto, riippuen siit�, kumpi tiedosto on.
     * @throws IllegalArgumentException,
     *             jos tulee virheit� tiedoston luonnissa tai tyyppimuunnoksissa.
     */
    public Tiedosto luoTiedosto() throws IllegalArgumentException {
        try {
            Tiedostonlukija lukija = new Tiedostonlukija();
            String[] tiedot = lukija.lueTiedosto(liiteTiedosto);
            if (tiedot[0].equals("Kuva")) {
                int koko = Integer.parseInt(tiedot[1]);
                int leveys = Integer.parseInt(tiedot[2]);
                int korkeus = Integer.parseInt(tiedot[3]);
                // Luodaan tiedosto saaduilla parametreilla.
                Kuva kuvaTiedosto = new Kuva(haeTiedostoNimi(), koko, leveys, korkeus);
                return kuvaTiedosto;
            } else {
                int koko = Integer.parseInt(tiedot[1]);
                double pituus = Double.parseDouble(tiedot[2]);
                Video videoTiedosto = new Video(haeTiedostoNimi(), koko, pituus);
                return videoTiedosto;
            }
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Lis�� uuden oksaviestin aktiivisen viestiketjun listalle.
     * 
     * @param sisalto,
     *            uuden viestin tekstisis�lt� merkkijonona.
     * @param tiedosto,
     *            viestiin liitett�v�n tiedoston nimi merkkijonona.
     */
    public void lisaaUusiViesti(String sisalto, String tiedosto) {
        Tiedosto valmisTiedosto = null;
        // Jos tiedostonimi ei ole null, luodaan tiedosto.
        if (tiedosto != null) {
            asetaTiedostoNimi(tiedosto);
            valmisTiedosto = luoTiedosto();
        }
        Viesti uusiViesti = new Viesti(tunniste, sisalto, null, valmisTiedosto, aktiivinenKetju.haeOksalista());
        aktiivinenKetju.asetaOksalistalle(uusiViesti);
        // Viestin lis��misen j�lkeen lis�t��n yksi tunnistelaskuriin.
        tunniste++;
    }

    /**
     * Vastaa olemassa olevaan viestiin toisella viestill�.
     * 
     * @param sisalto,
     *            vastaavan viestin sanoma merkkijonona.
     * @param tunnisteNro,
     *            viestin tunnus jolle halutaan vastata.
     * @param tiedosto,
     *            liitetiedoston nimi merkkijonona.
     * @throws IllegalArgumentException,
     *             jos viesti�, johon halutaan vastata ei l�ydy.
     */
    public void vastaaViestiin(String sisalto, int tunnisteNro, String tiedosto) throws IllegalArgumentException {
        Tiedosto valmisTiedosto = null;
        Viesti vastattava = null;
        if (tiedosto != null) {
            asetaTiedostoNimi(tiedosto);
            valmisTiedosto = luoTiedosto();
        }
        // Etsit��n aktiivisesta viestiketjusta viesti� parametrinaan saadulla
        // tunnistenumerolla.
        if ((vastattava = etsiKetjustaTunnus(tunnisteNro)) != null) {
            Viesti uusiViesti = new Viesti(tunniste, sisalto, vastattava, valmisTiedosto,
                    aktiivinenKetju.haeVastaulista());
            // Asetetaan vastaus viestiketjun listalle.
            aktiivinenKetju.asetaVastauslistalle(uusiViesti);
            // Asetetaan l�ydetylle vastattavalle viestille uusi viesti vastaukseksi.
            vastattava.lisaaVastaus(uusiViesti);
            tunniste++;
        } else {
            throw new IllegalArgumentException();
        }

    }

    /**
     * Kutsuu viestin tyhjent�v�� operaatiota l�ydetylle viestille.
     * 
     * @param tunnus,
     *            viestin tunnistenumero.
     */
    public void tyhjennaViesti(int tunnus) {
        Viesti tyhjennettava = etsiKetjustaTunnus(tunnus);
        tyhjennettava.tyhjenna();
    }

    /**
     * @param tunnus,
     *            k�ytt�j�n antama tunnus, jolla tunnistetaan tietty viesti.
     * @return etsittyViesti, l�ydetty vastaava Viesti-olio.
     * @throws IllegalArgumentException,
     *             jos ei l�ydetty vastaavaa tunnusta.
     */
    public Viesti etsiKetjustaTunnus(int tunnus) throws IllegalArgumentException {

        // Alustetaan etsitty viesti.
        Viesti etsittyViesti = null;
        int koko = aktiivinenKetju.haeVastaulista().koko();
        for (int i = 0; i < koko; i++) {
            // Tehd��n apuolio, jota voidaan vertailla tunnuksien avulla.
            Viesti olio = (Viesti) aktiivinenKetju.haeVastaulista().alkio(i);
            if (tunnus == olio.haeTunniste()) {
                etsittyViesti = olio;
                return etsittyViesti;
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * Kutsuu aktiivisen viestiketjun operaatiota etsi�kseen sielt� parametrin
     * sis�lt�v�t viestit.
     * 
     * @param merkkijono,
     *            haettu merkkijono.
     */
    public void etsiViesti(String merkkijono) {
        aktiivinenKetju.etsiViesti(merkkijono);
    }

    /**
     * Kutsuu aktiivisen viestiketjun operaatiota, mik� tulostaa ketjun puuna.
     */
    public void tulostaPuuna() {
        aktiivinenKetju.tulostaPuuna();
    }

    /**
     * Kutsuu aktiivisen viestiketjun operaatiota, mik� tulostaa ketjun listana.
     */
    public void tulostaListana() {
        aktiivinenKetju.tulostaListana();
    }

    /**
     * Kutsuu aktiivisen viestiketjun operaatiota, mik� tulostaa parametrin verran
     * vanhimpia viestej�.
     * 
     * @param numero,
     *            haluttu m��r� vanhimpia viestej�.
     */
    public void tulostaPaa(int numero) {
        aktiivinenKetju.tulostaPaa(numero);
    }

    /**
     * Kutsuu aktiivisen viestiketjun operaatiota, mik� tulostaa parametrin verran
     * uusimpia viestej�.
     * 
     * @param numero,
     *            haluttu m��r� uusimpia viestej�.
     */
    public void tulostaHanta(int numero) {
        aktiivinenKetju.tulostaHanta(numero);
    }

}
