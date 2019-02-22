package oope2018ht.omalista;


import fi.uta.csjola.oope.lista.*;
import oope2018ht.apulaiset.*;
/**
 * OmaLista luokka, mik� sis�lt�� linkitettyyn listaan kohdistuvia operaatioita.
 * <p>
 * Harjoitusty�, Olio-ohjelmoinnin perusteet, kev�t 2018.
 * <p>
 * 
 * @author Oskari Niskanen (Niskanen.Oskari.T@student.uta.fi) Luonnontieteiden
 *         tiedekunta, Tampereen yliopisto.
 */
public class OmaLista extends LinkitettyLista implements Ooperoiva {

    /** 
     * @see oope2018ht.apulaiset.Ooperoiva#hae(java.lang.Object)
     */
    @Override
    public Object hae(Object haettava) {
        if (haettava != null || onkoTyhja() != true) {
            for (int i = 0; i < koko; i++) {
                // Jos haettava l�ytyi, palautetaan se.
                if (haettava.equals(alkio(i))) {
                    return alkio(i);
                }
            }
            // Jos haettavaa ei l�ytynyt, palautetaan null.
            return null;
        }
        // Jos haettavaa oli null, palautetaan null arvo.
        return null;
    }

    /**
     * Lis�� listalle alkion nousevassa j�rjestyksess�.
     * 
     * @see oope2018ht.apulaiset.Ooperoiva#lisaa(java.lang.Object)
     * @return boolean, riippuen siit�, onnistuiko lis�ys.
     */
    @Override
    @SuppressWarnings({ "unchecked" })
    public boolean lisaa(Object uusi) {
        // Jos olio ei ole null ja se perii Comparable rajapinnan, edet��n.
        if (uusi != null && uusi instanceof Comparable) {
            Comparable vertailtava =  (Comparable) uusi;
            if (onkoTyhja() == true) {
                lisaaAlkuun(uusi);
            } else if (vertailtava.compareTo(alkio(0)) == -1) {
                lisaaAlkuun(uusi);
            } else {
                for (int i = 0; i < koko;) {
                    Comparable alkio = (Comparable) alkio(i);
                    if (alkio.compareTo(uusi) < 1) {
                        i++;
                    } else {
                        lisaa(i, uusi);
                        return true;
                    }
                }
                lisaaLoppuun(uusi);
            }
            return true;
        }
        return false;
    }

    /**
     * @see oope2018ht.apulaiset.Ooperoiva#annaAlku(int)
     */
    @Override
    public OmaLista annaAlku(int n) throws IllegalArgumentException {
        if (n > 0 && n <= koko && onkoTyhja() != true) {
            Object alkio = new Object();
            OmaLista uusiLista = new OmaLista();
            for (int i = 0; i < n; i++) {
                alkio = alkio(i);
                if (uusiLista.onkoTyhja() == true) {
                    uusiLista.lisaaAlkuun(alkio);
                } else {
                    uusiLista.lisaaLoppuun(alkio);
                }
            }
            return uusiLista;
        } else {
            throw new IllegalArgumentException();
        }
        
    }

    /**
     * @see oope2018ht.apulaiset.Ooperoiva#annaLoppu(int)
     */
    @Override
    public OmaLista annaLoppu(int n) throws IllegalArgumentException{
        if (n > 0 &&  n <= koko && onkoTyhja() != true) {
            Object alkio = new Object();
            // Luodaan uusi lista, johon lis�t��n alkiot.
            OmaLista uusiLista = new OmaLista();
            for (int i = 0; i < n; i++) {
                alkio = alkio((koko - n + i));
                if (uusiLista.onkoTyhja() == true) {
                    uusiLista.lisaaAlkuun(alkio);
                } else {
                    uusiLista.lisaaLoppuun(alkio);
                }
            }
            return uusiLista;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
