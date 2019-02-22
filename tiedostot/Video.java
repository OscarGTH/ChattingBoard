package oope2018ht.tiedostot;

import oope2018ht.apulaiset.Getteri;
import oope2018ht.apulaiset.Setteri;

/**
 * Luokka mallintaa Video-tiedostoa ja perii Tiedosto-luokan. Sisältää ominaisia
 * piirteitä videosta.
 * <p>
 * Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2018.
 * <p>
 * 
 * @author Oskari Niskanen (Niskanen.Oskari.T@student.uta.fi) Luonnontieteiden
 *         tiedekunta, Tampereen yliopisto.
 * 
 */
public class Video extends Tiedosto {
    /** Videon pituus */
    private double pituus;

    /**
     * Rakentaa video-olion.
     * 
     * @param nimi,
     *            merkitsee videon nimeä.
     * 
     * @param koko,
     *            merkitsee videon kokoa.
     * 
     * @param v_pituus,
     *            merkitsee videon pituutta.
     * 
     */
    public Video(String nimi, int koko, double v_pituus) {
        /* Kutsutaan yliluokan rakentajaa */
        super(nimi, koko);
        asetaPituus(v_pituus);
    }

    /**
     * Yrittää asettaa videon pituuden
     * 
     * @param v_pituus,
     *            merkitsee videon pituutta.
     * 
     * @throws IllegalArgumentException,
     *             kun parametri on virheellinen.
     */
    @Setteri
    public void asetaPituus(double v_pituus) throws IllegalArgumentException {
        if (v_pituus > 0) {
            pituus = v_pituus;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Metodi palauttaa pituuden.
     * 
     * @return pituus, palauttaa videon pituuden.
     */
    @Getteri
    public double haePituus() {
        return pituus;
    }

    /**
     * Palauttaa merkkijonona videon pituuden.
     * 
     * @see Tiedosto#toString()
     * @return pituus, videon pituus.
     */
    @Override
    public String toString() {
        return super.toString() + EROTIN + haePituus() + EROTIN + "s";
    }
}
