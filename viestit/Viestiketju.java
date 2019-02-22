package oope2018ht.viestit;

import oope2018ht.apulaiset.Getteri;
import oope2018ht.apulaiset.Setteri;
import oope2018ht.omalista.*;

/**
 * Viestiketjua kuvaava luokka, mik‰ sis‰lt‰‰ siihen kohdistuvia operaatioita.
 * <p>
 * Harjoitustyˆ, Olio-ohjelmoinnin perusteet, kev‰t 2018.
 * <p>
 * 
 * @author Oskari Niskanen (Niskanen.Oskari.T@student.uta.fi) Luonnontieteiden
 *         tiedekunta, Tampereen yliopisto.
 */
public class Viestiketju {
    /** Vakioitu merkki v‰lilyˆnnille. */
    private final char EROTIN = ' ';
    /** Syvennyksess‰ k‰ytett‰v‰ osuus merkkijono */
    private final String SYVENNYS = "   ";
    /** Sis‰lt‰‰ kaikki viestit jotka eiv‰t ole vastauksia toisiin. */
    private OmaLista oksaviestit;
    /** Sis‰lt‰‰ kaikki viestit viestiketjussa. */
    private OmaLista viestiViitteet;
    /** Viestiketjun tunniste. */
    private int tunniste;
    /** Viestiketjun aloitusaihe. */
    private String aihe;

    /**
     * Viestiketjun rakentajametodi. Rakentaa viestiketjun saamillaan parametreilla.
     * 
     * @param viestinAihe,
     *            viestin aiheen merkkijono.
     * @param nro,
     *            viestiketjun tunnisteen numero.
     * @throws IllegalArgumentException,
     *             jos parametrit ovat ep‰korrekteja.
     */
    public Viestiketju(String viestinAihe, int nro) throws IllegalArgumentException {
        if (viestinAihe != null && nro > 0) {
            this.aihe = viestinAihe;
            tunniste = nro;
            // Alustetaan listat.
            oksaviestit = new OmaLista();
            viestiViitteet = new OmaLista();
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Asettaa tunnisteen viestiketjulle.
     * 
     * @param nro,
     *            viestiketjun tunnistenumero.
     * @throws IllegalArgumentException,
     *             jos parametri ei ole korrekti.
     */
    @Setteri
    public void asetaTunniste(int nro) throws IllegalArgumentException {
        if (nro > 0) {
            tunniste = nro;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Palauttaa viestiketjun tunnistenumeron.
     * 
     * @return tunniste, viestiketjun tunnistenumero.
     */
    @Getteri
    public int haeTunniste() {
        return tunniste;
    }

    /**
     * Asettaa viestin oksalistalle.
     * 
     * @param viesti,
     *            Viesti-olio mik‰ lis‰t‰‰n listalle.
     * @throws IllegalArgumentException,
     *             jos parametri on null.
     */
    @Setteri
    public void asetaOksalistalle(Viesti viesti) throws IllegalArgumentException {
        if (viesti != null) {
            oksaviestit.lisaa(viesti);
            viestiViitteet.lisaa(viesti);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Palauttaa viitteen listaan, mik‰ sis‰lt‰‰ oksaviestit.
     * 
     * @return oksaviestit, viite oksaviesti-listaan.
     */
    @Getteri
    public OmaLista haeOksalista() {
        return oksaviestit;
    }

    /**
     * Asettaa viestin listalle, mik‰ sis‰lt‰‰ kaikki viestit.
     * 
     * @param viesti,
     *            Viesti-olio mik‰ lis‰t‰‰n kaikkien viestien listalle.
     */
    @Setteri
    public void asetaVastauslistalle(Viesti viesti) {
        if (viesti != null) {
            viestiViitteet.lisaa(viesti);
        }
    }

    /**
     * Palauttaa viitteen listaan, miss‰ on kaikki viestit viestiketjusta.
     * 
     * @return viestiViitteet, kaikki viestit sis‰lt‰v‰ lista.
     */
    @Getteri
    public OmaLista haeVastaulista() {
        return viestiViitteet;
    }

    /**
     * Palauttaa viitteen viestiketjun aiheeseen.
     * 
     * @return aihe, viestiketjun aihe.
     */
    @Getteri
    public String haeAihe() {
        return aihe;
    }

    /**
     * Asettaa parametrinaan saadun merkkijonon viestiketjun aiheeksi.
     * 
     * @param ketjunNimi,
     *            ketjulle annettava nimi.
     * @throws IllegalArgumentException,
     *             jos parametri on virheellinen.
     */
    @Setteri
    public void asetaAihe(String ketjunNimi) throws IllegalArgumentException {
        if (ketjunNimi != null) {
            aihe = ketjunNimi;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Tulostaa kaikki viestit viestiketjussa listana.
     */
    public void tulostaListana() {
        System.out.println("=\n==" + EROTIN + toString());
        System.out.println("===");
        for (int i = 0; i < viestiViitteet.koko(); i++) {
            System.out.println(viestiViitteet.alkio(i).toString());
        }
    }

    /**
     * Tulostaa oksaviestit ja kutsuu niiden vastauksia tulostavaa metodia.
     */
    public void tulostaPuuna() {
        if (viestiViitteet != null) {
            System.out.println("=\n==" + EROTIN + toString());
            System.out.println("===");
            // K‰yd‰‰n l‰pi viestiketjun oksaviestit s‰ilˆv‰ lista.
            for (int i = 0; i < oksaviestit.koko(); i++) {
                tulostaPuuna((Viesti) haeOksalista().alkio(i), 0);
            }
        }
    }

    /**
     * Tulostaa oksaviesti-listalla olevien viestien vastaukset.
     * 
     * @param viestiOlio,
     *            oksalistalla sijaitseva Viesti-olio.
     * @param syvyys,
     *            syvennyksen m‰‰r‰.
     */
    public void tulostaPuuna(Viesti viestiOlio, int syvyys) {
        if (viestiOlio != null) {
            for (int b = 0; b < syvyys; b++) {
                System.out.print(SYVENNYS);
            }
            System.out.println(viestiOlio);
            // K‰yd‰‰n l‰pi viestin vastauksia sis‰lt‰v‰ lista alkio kerrallaan.
            for (int indeksi = 0; indeksi < viestiOlio.haeLista().koko(); indeksi++) {
                // Luodaan apuolio, jotta se voidaan tulostaa ja kutsua metodia uudestaan.
                Viesti haettava = (Viesti) viestiOlio.haeLista().alkio(indeksi);
                if (viestiOlio == haettava.haeVastattava()) {
                    tulostaPuuna(haettava, (syvyys + 1));
                }
            }
        }

    }

    /**
     * Etsii kaikista viesteist‰ merkkijonoa sis‰lt‰vi‰ Viesti-olioita, ja tulostaa
     * t‰sm‰‰v‰t.
     * 
     * @param merkkijono,
     *            etsitt‰v‰ merkkijono.
     */
    public void etsiViesti(String merkkijono) {
        if (merkkijono.length() > 0) {
            // K‰yd‰‰n l‰pi kaikki viestit sis‰lt‰v‰ lista alkio kerrallaan.
            for (int i = 0; i < viestiViitteet.koko(); i++) {
                Viesti olio = (Viesti) viestiViitteet.alkio(i);
                if (olio.toString().contains(merkkijono)) {
                    System.out.println(olio.toString());
                }
            }
        }
    }

    /**
     * Luo uuden apulistan, johon asetetaan halutun m‰‰r‰n alkioita p‰‰st‰ ja kutsuu
     * tulostavaa metodia.
     * 
     * @param indeksi,
     *            tulostettavien viestien m‰‰r‰.
     */
    public void tulostaPaa(int indeksi) {
        OmaLista apuLista = viestiViitteet.annaAlku(indeksi);
        tulostaLista(apuLista);
    }

    /**
     * Luo uuden apulistan, johon asetetaan halutun m‰‰r‰n alkioita h‰nn‰st‰ ja
     * kutsuu tulostavaa metodia.
     * 
     * @param indeksi,
     *            tulostettavien viestien m‰‰r‰.
     */
    public void tulostaHanta(int indeksi) {
        // Luodaan uusi OmaLista-olio ja kutsutaan listametodia antamaan loppup‰‰st‰
        // parametrien verran alkioita sis‰lt‰v‰n listan.
        OmaLista apuLista = viestiViitteet.annaLoppu(indeksi);
        tulostaLista(apuLista);
    }

    /**
     * Apumetodi listan tulostukseen, tulostaa alkiot saamastaan listasta.
     * 
     * @param lista,
     *            lista mink‰ alkiot tulostetaan.
     */
    public void tulostaLista(OmaLista lista) {
        // K‰yd‰‰n l‰pi jokainen alkio listalta ja tulostetaan se.
        for (int i = 0; i < lista.koko(); i++) {
            System.out.println(lista.alkio(i));
        }
    }

    /**
     * Palauttaa viestiketjun tiedot.
     * 
     * @return merkkijono, sis‰lt‰‰ kaikki tiedot viestiketjusta.
     */
    public String toString() {
        // Haetaan eri tiedot viestiketjusta ja lis‰t‰‰n siihen viel‰ viestiketjun
        // kaikkien viestien m‰‰r‰.
        return "#" + haeTunniste() + EROTIN + haeAihe() + EROTIN + '(' + viestiViitteet.koko() + " messages" + ')';
    }
}
