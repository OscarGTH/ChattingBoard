package oope2018ht.viestit;

import oope2018ht.apulaiset.*;
import oope2018ht.omalista.*;
import oope2018ht.tiedostot.*;

/**
 * Viesti� mallintava luokka. Sis�lt�� siihen kohdistuvia operaatioita.
 * <p>
 * Harjoitusty�, Olio-ohjelmoinnin perusteet, kev�t 2018.
 * <p>
 * 
 * @author Oskari Niskanen (Niskanen.Oskari.T@student.uta.fi) Luonnontieteiden
 *         tiedekunta, Tampereen yliopisto.
 */
public class Viesti implements Comparable<Viesti>, Komennettava<Viesti> {
    /** Vakioitu v�lily�nnin merkki */
    private final char EROTIN = ' ';
    /** Viestin tunnistenumero */
    private int tunniste;
    /** Viestin merkkijono */
    private String viesti;
    /** Viestiin lis�ttyihin vastauksiin liittyv� lista */
    private OmaLista vastaukset;
    /** Viestin tiedosto */
    private Tiedosto tiedosto;
    /** Viesti, johon viesti vastaa */
    private Viesti vastattava;

    /**
     * Rakentaa viesti-olion.
     * 
     * @param tunnisteNro,
     *            viestin tunnistenumero.
     * @param viesti_txt,
     *            viestin tekstisis�lt�.
     * @param vastattavaViesti,
     *            viesti johon vastataan.
     * @param uusiTiedosto,
     *            viestiin liitett�v� tiedosto.
     * @param uusiLista,
     *            lista mihin viesti asetetaan.
     * @throws IllegalArgumentException
     *             jos jokin asettavista metodeista heitt�� virheen.
     */
    public Viesti(int tunnisteNro, String viesti_txt, Viesti vastattavaViesti, Tiedosto uusiTiedosto,
            OmaLista uusiLista) throws IllegalArgumentException {
        // Yritet��n asettaa parametreinaan saatuja tietoja viestille.
        try {
            asetaTunniste(tunnisteNro);
            asetaViesti(viesti_txt);
            vastaukset = new OmaLista();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }
        asetaVastattava(vastattavaViesti);
        asetaTiedosto(uusiTiedosto);
    }

    /**
     * Yritt�� asettaa tunnistenumeron viestille.
     * 
     * @param uusiTunniste,
     *            viestin tunnistenumero.
     * @throws IllegalArgumentException
     *             jos tunnistenumero on alle yhden.
     */
    @Setteri
    public void asetaTunniste(int uusiTunniste) throws IllegalArgumentException {
        if (uusiTunniste > 0) {
            tunniste = uusiTunniste;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Yritt�� asettaa viestin sanoman merkkijonona.
     * 
     * @param uusiViesti,
     *            viestin sanoma merkkijonona.
     * @throws IllegalArgumentException
     *             jos viestin pituus on alle yhden.
     */
    @Setteri
    public void asetaViesti(String uusiViesti) throws IllegalArgumentException {
        if (uusiViesti != null & uusiViesti.length() > 0) {
            viesti = uusiViesti;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Asettaa viestille listan.
     * 
     * @param uusiLista,
     *            s�il�� viestiin liittyv�t vastaukset.
     * @throws IllegalArgumentException
     *             jos uusiLista on null-arvoinen.
     */
    @Setteri
    public void asetaLista(OmaLista uusiLista) throws IllegalArgumentException {
        if (uusiLista != null) {
            vastaukset = uusiLista;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Asettaa viestiin liittyv�n tiedoston.
     * 
     * @param uusiTiedosto,
     *            viestiin liitetty kuva tai videotiedosto.
     */
    @Setteri
    public void asetaTiedosto(Tiedosto uusiTiedosto) {
        tiedosto = uusiTiedosto;
    }

    /**
     * Asettaa viestin vastaamaan toiseen viestiin.
     * 
     * @param uusiVastattava,
     *            vastattavan viestin viite.
     */
    @Setteri
    public void asetaVastattava(Viesti uusiVastattava) {
        vastattava = uusiVastattava;
    }

    /**
     * Palauttaa viestin tunnistenumeron.
     * 
     * @return tunniste
     */
    @Getteri
    public int haeTunniste() {
        return tunniste;
    }

    /**
     * Palauttaa viestin merkkijonon.
     * 
     * @return viesti
     */
    @Getteri
    public String haeViesti() {
        return viesti;
    }

    /**
     * Palauttaa viitteen viestin listaan.
     * 
     * @return lista
     */
    @Getteri
    public OmaLista haeLista() {
        return vastaukset;
    }

    /**
     * Palauttaa viitteen tiedostoon.
     * 
     * @return tiedosto
     */
    @Getteri
    public Tiedosto haeTiedosto() {
        return tiedosto;
    }

    /**
     * Palauttaa viitteen vastattavaan viestiin.
     * 
     * @return vastattava
     */
    @Getteri
    public Viesti haeVastattava() {
        return vastattava;
    }

    /**
     * Vertailee kahta viestioliota niiden tunnisteen perusteella.
     * 
     * @return -1,0,1, riippuen vertailun tuloksesta.
     */
    @Override
    public int compareTo(Viesti vertailtava) {
        Viesti apuViesti = vertailtava;
        if (haeTunniste() < apuViesti.haeTunniste()) {
            return -1;
        } else if (haeTunniste() > apuViesti.haeTunniste()) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Vertaa onko kahden viestin tunnisteet samat.
     * 
     * @return totuusarvo, riippuen vertailun onnistuneisuudesta ja tuloksesta.
     */
    @Override
    public boolean equals(Object obj) {
        try {
            Viesti apuViesti = (Viesti) obj;
            return (haeTunniste() == apuViesti.haeTunniste());
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Palauttaa viestin kaikki sis�lt�m�t tiedot merkkijonona.
     */
    @Override
    public String toString() {
        String tiedosto = "";
        if (haeTiedosto() != null) {
            Tiedosto apuTiedosto = haeTiedosto();
            tiedosto = EROTIN + "(" + apuTiedosto.toString() + ")";
        }
        return "#" + haeTunniste() + EROTIN + haeViesti() + tiedosto;
    }

    /**
     * Hakee viesti� listalta, joka s�il�� viitteet viestiin vastaaviin viesteihin.
     * Hy�dynt�� OmaLista-luokan hae-operaatiota.
     *
     * @param haettava
     *            viite haettavaan viestiin.
     * @return viite l�ydettyyn tietoon. Palauttaa null-arvon, jos haettavaa viesti�
     *         ei l�ydet�.
     * @throws IllegalArgumentException
     *             jos parametri on null-arvoinen.
     */
    @Override
    public Viesti hae(Viesti haettava) throws IllegalArgumentException {
        if (haettava == null) {
            throw new IllegalArgumentException("Virheellinen parametri!");
        } else {
            return ((Viesti) vastaukset.hae(haettava));
        }
    }

    /**
     * Lis�� uuden viitteen listalle, joka s�il�� viitteet viestiin vastaaviin
     * viesteihin. Uusi viite lis�t��n listan loppuun. Viitett� ei lis�t�, jos
     * listalla on jo viite lis�tt�v��n vastaukseen. Hy�dynt�� hae-metodia.
     *
     * @param lisattava
     *            viite lis�tt�v��n viestiin.
     * @throws IllegalArgumentException
     *             jos lis�ys ep�onnistui, koska parametri on null-arvoinen tai
     *             koska vastaus on jo lis�tty listalle.
     */
    @Override
    public void lisaaVastaus(Viesti lisattava) throws IllegalArgumentException {
        if (lisattava != null && vastaukset.hae(lisattava) == null) {
            vastaukset.lisaa(lisattava);
        } else {
            throw new IllegalArgumentException("Virheellinen parametri!");
        }
    }

    /**
     * Asettaa viestin tekstiksi vakion POISTETTUTEKSTI ja poistaa viestiin
     * mahdollisesti liitetyn tiedoston asettamalla attribuutin arvoksi null-arvon.
     */
    @Override
    public void tyhjenna() {
        asetaViesti(POISTETTUTEKSTI);
        asetaTiedosto(null);
    }
}
