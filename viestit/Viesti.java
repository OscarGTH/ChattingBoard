package oope2018ht.viestit;

import oope2018ht.apulaiset.*;
import oope2018ht.omalista.*;
import oope2018ht.tiedostot.*;

/**
 * Viestiä mallintava luokka. Sisältää siihen kohdistuvia operaatioita.
 * <p>
 * Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2018.
 * <p>
 * 
 * @author Oskari Niskanen (Niskanen.Oskari.T@student.uta.fi) Luonnontieteiden
 *         tiedekunta, Tampereen yliopisto.
 */
public class Viesti implements Comparable<Viesti>, Komennettava<Viesti> {
    /** Vakioitu välilyönnin merkki */
    private final char EROTIN = ' ';
    /** Viestin tunnistenumero */
    private int tunniste;
    /** Viestin merkkijono */
    private String viesti;
    /** Viestiin lisättyihin vastauksiin liittyvä lista */
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
     *            viestin tekstisisältö.
     * @param vastattavaViesti,
     *            viesti johon vastataan.
     * @param uusiTiedosto,
     *            viestiin liitettävä tiedosto.
     * @param uusiLista,
     *            lista mihin viesti asetetaan.
     * @throws IllegalArgumentException
     *             jos jokin asettavista metodeista heittää virheen.
     */
    public Viesti(int tunnisteNro, String viesti_txt, Viesti vastattavaViesti, Tiedosto uusiTiedosto,
            OmaLista uusiLista) throws IllegalArgumentException {
        // Yritetään asettaa parametreinaan saatuja tietoja viestille.
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
     * Yrittää asettaa tunnistenumeron viestille.
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
     * Yrittää asettaa viestin sanoman merkkijonona.
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
     *            säilöö viestiin liittyvät vastaukset.
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
     * Asettaa viestiin liittyvän tiedoston.
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
     * Palauttaa viestin kaikki sisältämät tiedot merkkijonona.
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
     * Hakee viestiä listalta, joka säilöö viitteet viestiin vastaaviin viesteihin.
     * Hyödyntää OmaLista-luokan hae-operaatiota.
     *
     * @param haettava
     *            viite haettavaan viestiin.
     * @return viite löydettyyn tietoon. Palauttaa null-arvon, jos haettavaa viestiä
     *         ei löydetä.
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
     * Lisää uuden viitteen listalle, joka säilöö viitteet viestiin vastaaviin
     * viesteihin. Uusi viite lisätään listan loppuun. Viitettä ei lisätä, jos
     * listalla on jo viite lisättävään vastaukseen. Hyödyntää hae-metodia.
     *
     * @param lisattava
     *            viite lisättävään viestiin.
     * @throws IllegalArgumentException
     *             jos lisäys epäonnistui, koska parametri on null-arvoinen tai
     *             koska vastaus on jo lisätty listalle.
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
