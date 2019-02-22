package oope2018ht.tiedostot;

import oope2018ht.apulaiset.Getteri;
import oope2018ht.apulaiset.Setteri;

/**
 * Luokka mallintaa kuva-tiedostoa.
 * <p>
 * Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2018.
 * <p>
 * 
 * @author Oskari Niskanen (Niskanen.Oskari.T@student.uta.fi) Luonnontieteiden
 *         tiedekunta, Tampereen yliopisto.
 */
public class Kuva extends Tiedosto {
    /** Kuvan leveys */
    private int leveys;
    /** Kuvan korkeus */
    private int korkeus;

    /**
     * Rakentaa kuva-olion.
     * 
     * @param nimi,
     *            kuvan nimi.
     * 
     * @param koko,
     *            kuvan koko.
     * 
     * @param kv_leveys,
     *            kuvan leveys.
     * 
     * @param kv_korkeus,
     *            kuvan korkeus.
     */
    public Kuva(String nimi, int koko, int kv_leveys, int kv_korkeus) {
        super(nimi, koko);
        asetaLeveys(kv_leveys);
        asetaKorkeus(kv_korkeus);
    }

    /**
     * Yrittää asettaa leveyden.
     * 
     * @param kv_leveys,
     *            merkitsee kuvan leveyttä.
     * 
     * @throws IllegalArgumentException
     *             jos leveys alle yhden.
     */
    @Setteri
    public void asetaLeveys(int kv_leveys) throws IllegalArgumentException {
        // Jos leveys on suurempi kuin nolla, asetetaan se leveydeksi.
        if (kv_leveys > 0) {
            leveys = kv_leveys;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Yrittää asettaa korkeuden.
     * 
     * @param kv_korkeus,
     *            merkitsee kuvan korkeutta.
     * 
     * @throws IllegalArgumentException
     *             jos korkeus alle yhden.
     */
    @Setteri
    public void asetaKorkeus(int kv_korkeus) throws IllegalArgumentException {
        if (kv_korkeus > 0) {
            korkeus = kv_korkeus;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Hakee leveyden ja palauttaa sen arvon.
     * 
     * @return leveys
     */
    @Getteri
    public int haeLeveys() {
        return leveys;
    }

    /**
     * Hakee korkeuden ja palauttaa sen arvon.
     * 
     * @return korkeus
     */
    @Getteri
    public int haeKorkeus() {
        return korkeus;
    }

    /**
     * Palauttaa merkkijonona kuvan leveyden ja korkeuden.
     * 
     * @see Tiedosto#toString()
     * @return mittasuhteet, kuvan leveys ja korkeus.
     */
    @Override
    public String toString() {
        return super.toString() + EROTIN + haeLeveys() + "x" + haeKorkeus();
    }
}
