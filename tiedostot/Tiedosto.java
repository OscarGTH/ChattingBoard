package oope2018ht.tiedostot;

import oope2018ht.apulaiset.Getteri;
import oope2018ht.apulaiset.Setteri;

/**
 * Abstrakti luokka, mikä mallintaa tiedostoa ja siihen liittyviä piirteitä.
 * <p>
 * Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2018.
 * <p>
 * 
 * @author Oskari Niskanen (Niskanen.Oskari.T@student.uta.fi) Luonnontieteiden
 *         tiedekunta, Tampereen yliopisto. 
 */
public abstract class Tiedosto {
    /** Vakioitu välilyönti */
    public final char EROTIN = ' ';
    /** Tiedoston nimi. */
    private String nimi;
    /** Tiedoston koko */
    private int koko;

    /**
     * Rakentaa tiedostolle nimen ja koon.
     * 
     * @param tnimi,
     *            tiedoston nimi.
     * 
     * @param tkoko,
     *            tiedoston koko.
     */
    public Tiedosto(String tnimi, int tkoko) {
        asetaNimi(tnimi);
        asetaKoko(tkoko);
    }

    /**
     * Yrittää asettaa tiedoston nimen.
     * 
     * @param tnimi,
     *            tiedoston nimi.
     * 
     * @throws IllegalArgumentException,
     *             kun parametri on virheellinen.
     */
    @Setteri
    public void asetaNimi(String tnimi) throws IllegalArgumentException {
        if (tnimi != null && tnimi.length() > 0) {
            nimi = tnimi;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Yrittää asettaa tiedoston koon.
     * 
     * @param tkoko,
     *            tiedoston koko.
     * @throws IllegalArgumentException,
     *             kun parametri on virheellinen.
     */
    @Setteri
    public void asetaKoko(int tkoko) throws IllegalArgumentException {
        if (tkoko > 0) {
            koko = tkoko;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Palauttaa tiedoston nimen.
     * 
     * @return nimi, tiedoston nimi merkkijonona.
     */
    @Getteri
    public String haeNimi() {
        return nimi;
    }

    /**
     * Palauttaa tiedoston koon.
     * 
     * @return koko, tiedoston koko kokonaislukuna.
     */
    @Getteri
    public int haeKoko() {
        return koko;
    }

    /**
     * Palauttaa tiedoston nimen ja pituuden merkkijonona.
     * 
     * @return nimi & koko
     */
    @Override
    public String toString() {
        return haeNimi() + EROTIN + haeKoko() + EROTIN + "B";
    }

}
