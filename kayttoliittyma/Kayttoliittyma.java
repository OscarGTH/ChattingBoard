package oope2018ht.kayttoliittyma;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import oope2018ht.apulaiset.*;
import oope2018ht.viestit.Keskustelualue;

/**
 * Ohjelman tekstipohjaista käyttöliittymmää kuvaava luokka, missä luetaan
 * käyttäjän syötteet ja kutsutaan niiden kautta eri luokkien operaatioita.
 * <p>
 * Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2018.
 * <p>
 * 
 * @author Oskari Niskanen (Niskanen.Oskari.T@student.uta.fi) Luonnontieteiden
 *         tiedekunta, Tampereen yliopisto.
 */
public class Kayttoliittyma {
    /** Ensimmäisen ajokerran lippu */
    private boolean ensiKerta = true;
    /** Komentokehotteen vakioitu merkki */
    private final char KEHOTE = '>';
    /** Käyttäjän antama komento */
    public String komento;
    /** Ohjelmassa oleva keskustelualue-olio, mikä sisältää viestiketjut. */
    public Keskustelualue keskustelualue;
    /** Lippu ohjelman lopettamiseen */
    private boolean lopetus = false;
    /** Vakioitu tervehdys käyttäjälle */
    private final String TERVEHDYS = "Welcome to S.O.B.";
    /** Virheilmoituksen vakioitu merkkijono */
    private final String VIRHE = "Error!";

    /**
     * Luo alueen ja tervehtii käyttäjää. Lisäksi ottaa komentoja vastaan
     * käyttäjältä.
     */
    public void aloita() {
        // Alustetaan uusi keskustelualue.
        keskustelualue = new Keskustelualue();
        System.out.println(TERVEHDYS);
        /*
         * Ensimmäisen kerran ajettaessa tämä toistuu kunnes poistutaan tai tehdään uusi
         * viestiketju.
         */
        while (ensiKerta == true) {
            try {
                System.out.print(KEHOTE);
                komento = In.readString();
                if (komento.length() > 0) {
                    /*
                     * Käyttäjälle ei anneta mahdollisuuksia antaa muita komentoja, kuin add tai
                     * exit, sillä ne eivät tekisi mitään ilman ainuttakaan viestiketjua.
                     */
                    if (komento.startsWith("add")) {
                        kasitteleKomento(komento);
                        ensiKerta = false;
                    } else if (komento.equals("exit")) {
                        kasitteleKomento(komento);
                        ensiKerta = false;
                    }
                }
            } catch (IllegalArgumentException e) {
                herjaa();
            }
        }

        /* Pääsilmukka komentojen kuunteluun. */
        while (lopetus != true) {
            try {
                System.out.print(KEHOTE);
                komento = In.readString();
                kasitteleKomento(komento);
            } catch (IllegalArgumentException e) {
                herjaa();
            }
        }
    }

    /**
     * Käsittelee käyttäjän antaman komennon
     * 
     * @param komento,
     *            käyttäjän antama komento
     * @throws IllegalArgumentException,
     *             jos komentoa ei tunnisteta.
     */
    public void kasitteleKomento(String komento) throws IllegalArgumentException {
        /*
         * Tarkistaa että parametrinaan saatu komento on tarpeeksi pitkä eikä
         * ensimmäinen kirjain ole välilyönti.
         */
        if (komento.length() > 0 && komento.charAt(0) != ' ') {
            int numero;
            if (komento.matches("exit")) {
                hyvastele();
                lopetus = true;
            } else if (komento.matches("catalog")) {
                keskustelualue.tulostaViestiketjut();
            } else if (komento.matches("tree")) {
                keskustelualue.tulostaPuuna();
            } else if (komento.matches("list")) {
                keskustelualue.tulostaListana();
            } else {
                String sisalto = erotteleSisalto(komento);
                if (komento.startsWith("add")) {
                    keskustelualue.lisaaViestiketju(sisalto);
                } else if (komento.startsWith("select")) {
                    numero = erotteleNumero(sisalto);
                    keskustelualue.valitseAktiivinen(numero);
                } else if (komento.startsWith("new")) {
                    String tied_nimi = null;
                    sisalto = erotteleSisalto(komento);
                    // Jos viestissä on mukana tiedosto, otetaan sen nimi
                    // talteen.
                    if (sisalto.contains(" &")) {
                        tied_nimi = sisalto.substring(sisalto.indexOf("&") + 1);
                        sisalto = sisalto.substring(0, sisalto.indexOf("&") - 1);
                    }
                    keskustelualue.lisaaUusiViesti(sisalto, tied_nimi);
                } else if (komento.startsWith("reply")) {
                    String[] tiedot = erotteleNumeroJaTeksti(sisalto);
                    numero = Integer.parseInt(tiedot[1]);
                    String tiedosto = tiedot[2];
                    keskustelualue.vastaaViestiin(tiedot[0], numero, tiedosto);
                } else if (komento.startsWith("head")) {
                    numero = erotteleNumero(sisalto);
                    keskustelualue.tulostaPaa(numero);
                } else if (komento.startsWith("tail")) {
                    numero = erotteleNumero(sisalto);
                    keskustelualue.tulostaHanta(numero);
                } else if (komento.startsWith("empty")) {
                    numero = erotteleNumero(sisalto);
                    keskustelualue.tyhjennaViesti(numero);
                } else if (komento.startsWith("find")) {
                    keskustelualue.etsiViesti(sisalto);
                } else {
                    throw new IllegalArgumentException();
                }
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Erottelee merkkijonosta tunnistenumeron ja palauttaa sen.
     * 
     * @param sisalto,
     *            komennon tekstisisältö.
     * @return numero, palauttaa komennon parametrina olevan numeron
     * @throws IllegalArgumentException,
     *             jos parametria ei voi kääntää integeriksi.
     */
    public int erotteleNumero(String sisalto) throws IllegalArgumentException {
        try {
            tarkistaParametrit(sisalto);
            // Muutetaan sisältö int-tyyppiseksi.
            int numero = Integer.parseInt(sisalto);
            return numero;
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Erottelee numeron ja tekstin komennosta ja palauttaa ne.
     * 
     * @param sisalto,
     *            käyttäjän antaman komennon parametrit merkkijonona.
     * @return taulukko, palauttaa numeron ja viestin aiheen sekä mahdollisesti
     *         tiedoston nimen taulukkona.
     * @throws IllegalArgumentException,
     *             heitetään jos parametrina on annettu vain tyhjää/välilyönti tai
     *             jos sisällössä ei ole välilyöntiä.
     */
    public String[] erotteleNumeroJaTeksti(String sisalto) throws IllegalArgumentException {
        try {
            String[] taulukko = new String[3];
            taulukko[1] = (sisalto.substring(0, sisalto.indexOf(" ")));
            String teksti = erotteleSisalto(sisalto);
            // Jos tekstissä on merkki ilmaisemaan tiedostosta, otetaan tiedoston nimi
            // talteen.
            if (teksti.contains(" &")) {
                String tied_nimi = teksti.substring(teksti.indexOf("&") + 1);
                teksti = teksti.substring(0, teksti.indexOf("&") - 1);
                taulukko[2] = tied_nimi;
            }
            taulukko[0] = teksti;
            // Jos sisältönä on vain yksi välilyönti tai tyhjää, niin
            // palautetaan null.
            if (taulukko[0].matches(" ") == true || taulukko[0].isEmpty()) {
                throw new IllegalArgumentException();
            }
            return taulukko;
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Erottelee komennosta sisällön.
     * 
     * @param komento,
     *            käyttäjän antama komento.
     * @return sisalto, palauttaa komennon sisällön tekstiosuuden.
     * @throws IllegalArgumentException,
     *             heitetään jos komennossa ei ole välilyöntiä tai jos sisällössä ei
     *             ole muita merkkejä kuin välilyöntejä.
     */
    public String erotteleSisalto(String komento) throws IllegalArgumentException {
        // Asetetaan Pattern-luokan olio hyväksymään kaikki muut merkit paitsi
        // välilyönnit.
        Pattern pat = Pattern.compile("[^ ]");
        String sisalto;
        // Jos komennossa on välilyönti ja välilyönnin jälkeinen osa on
        // pituudeltaan enemmän kuin 0.
        if (komento.contains(" ") && (komento.substring(komento.indexOf(" ") + 1).length() > 0)) {
            sisalto = komento.substring(komento.indexOf(" ") + 1);
            // Alustetaan matcher-olio patternilla ja annetaan parametriksi siistitty
            // komento.
            Matcher matcher = pat.matcher(sisalto);
            // Etsitään onko sisällössä ainoastaan välilyöntejä.
            if (matcher.find()) {
                return sisalto;
            } else {
                throw new IllegalArgumentException();
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Tarkistaa, onko merkkijonossa enemmän kuin yksi välilyönti.
     * 
     * @param sisalto,
     *            viestin sisältö.
     * @throws IllegalArgumentException,
     *             heitetään jos välilyöntejä on enemmän kuin yksi.
     */
    public void tarkistaParametrit(String sisalto) throws IllegalArgumentException {
        int space_counter = 0;
        // Käydään läpi komennon sisältö yksi kirjain kerrallaan.
        for (int i = 0; i < sisalto.length(); i++) {
            if (sisalto.charAt(i) == ' ') {
                space_counter++;
            }
        }
        // Jos välilyöntejä on enemmän kuin yksi, heitetään virhe.
        if (space_counter > 1) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Hyvästelee käyttäjän.
     */
    public void hyvastele() {
        System.out.println("Bye! See you soon.");
    }

    /**
     * Herjaa käyttäjää kertomalla virheen tapahtuneen.
     */
    public void herjaa() {
        System.out.println(VIRHE);
    }
}
