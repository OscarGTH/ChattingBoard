package oope2018ht.kayttoliittyma;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import oope2018ht.apulaiset.*;
import oope2018ht.viestit.Keskustelualue;

/**
 * Ohjelman tekstipohjaista k�ytt�liittymm�� kuvaava luokka, miss� luetaan
 * k�ytt�j�n sy�tteet ja kutsutaan niiden kautta eri luokkien operaatioita.
 * <p>
 * Harjoitusty�, Olio-ohjelmoinnin perusteet, kev�t 2018.
 * <p>
 * 
 * @author Oskari Niskanen (Niskanen.Oskari.T@student.uta.fi) Luonnontieteiden
 *         tiedekunta, Tampereen yliopisto.
 */
public class Kayttoliittyma {
    /** Ensimm�isen ajokerran lippu */
    private boolean ensiKerta = true;
    /** Komentokehotteen vakioitu merkki */
    private final char KEHOTE = '>';
    /** K�ytt�j�n antama komento */
    public String komento;
    /** Ohjelmassa oleva keskustelualue-olio, mik� sis�lt�� viestiketjut. */
    public Keskustelualue keskustelualue;
    /** Lippu ohjelman lopettamiseen */
    private boolean lopetus = false;
    /** Vakioitu tervehdys k�ytt�j�lle */
    private final String TERVEHDYS = "Welcome to S.O.B.";
    /** Virheilmoituksen vakioitu merkkijono */
    private final String VIRHE = "Error!";

    /**
     * Luo alueen ja tervehtii k�ytt�j��. Lis�ksi ottaa komentoja vastaan
     * k�ytt�j�lt�.
     */
    public void aloita() {
        // Alustetaan uusi keskustelualue.
        keskustelualue = new Keskustelualue();
        System.out.println(TERVEHDYS);
        /*
         * Ensimm�isen kerran ajettaessa t�m� toistuu kunnes poistutaan tai tehd��n uusi
         * viestiketju.
         */
        while (ensiKerta == true) {
            try {
                System.out.print(KEHOTE);
                komento = In.readString();
                if (komento.length() > 0) {
                    /*
                     * K�ytt�j�lle ei anneta mahdollisuuksia antaa muita komentoja, kuin add tai
                     * exit, sill� ne eiv�t tekisi mit��n ilman ainuttakaan viestiketjua.
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

        /* P��silmukka komentojen kuunteluun. */
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
     * K�sittelee k�ytt�j�n antaman komennon
     * 
     * @param komento,
     *            k�ytt�j�n antama komento
     * @throws IllegalArgumentException,
     *             jos komentoa ei tunnisteta.
     */
    public void kasitteleKomento(String komento) throws IllegalArgumentException {
        /*
         * Tarkistaa ett� parametrinaan saatu komento on tarpeeksi pitk� eik�
         * ensimm�inen kirjain ole v�lily�nti.
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
                    // Jos viestiss� on mukana tiedosto, otetaan sen nimi
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
     *            komennon tekstisis�lt�.
     * @return numero, palauttaa komennon parametrina olevan numeron
     * @throws IllegalArgumentException,
     *             jos parametria ei voi k��nt�� integeriksi.
     */
    public int erotteleNumero(String sisalto) throws IllegalArgumentException {
        try {
            tarkistaParametrit(sisalto);
            // Muutetaan sis�lt� int-tyyppiseksi.
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
     *            k�ytt�j�n antaman komennon parametrit merkkijonona.
     * @return taulukko, palauttaa numeron ja viestin aiheen sek� mahdollisesti
     *         tiedoston nimen taulukkona.
     * @throws IllegalArgumentException,
     *             heitet��n jos parametrina on annettu vain tyhj��/v�lily�nti tai
     *             jos sis�ll�ss� ei ole v�lily�nti�.
     */
    public String[] erotteleNumeroJaTeksti(String sisalto) throws IllegalArgumentException {
        try {
            String[] taulukko = new String[3];
            taulukko[1] = (sisalto.substring(0, sisalto.indexOf(" ")));
            String teksti = erotteleSisalto(sisalto);
            // Jos tekstiss� on merkki ilmaisemaan tiedostosta, otetaan tiedoston nimi
            // talteen.
            if (teksti.contains(" &")) {
                String tied_nimi = teksti.substring(teksti.indexOf("&") + 1);
                teksti = teksti.substring(0, teksti.indexOf("&") - 1);
                taulukko[2] = tied_nimi;
            }
            taulukko[0] = teksti;
            // Jos sis�lt�n� on vain yksi v�lily�nti tai tyhj��, niin
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
     * Erottelee komennosta sis�ll�n.
     * 
     * @param komento,
     *            k�ytt�j�n antama komento.
     * @return sisalto, palauttaa komennon sis�ll�n tekstiosuuden.
     * @throws IllegalArgumentException,
     *             heitet��n jos komennossa ei ole v�lily�nti� tai jos sis�ll�ss� ei
     *             ole muita merkkej� kuin v�lily�ntej�.
     */
    public String erotteleSisalto(String komento) throws IllegalArgumentException {
        // Asetetaan Pattern-luokan olio hyv�ksym��n kaikki muut merkit paitsi
        // v�lily�nnit.
        Pattern pat = Pattern.compile("[^ ]");
        String sisalto;
        // Jos komennossa on v�lily�nti ja v�lily�nnin j�lkeinen osa on
        // pituudeltaan enemm�n kuin 0.
        if (komento.contains(" ") && (komento.substring(komento.indexOf(" ") + 1).length() > 0)) {
            sisalto = komento.substring(komento.indexOf(" ") + 1);
            // Alustetaan matcher-olio patternilla ja annetaan parametriksi siistitty
            // komento.
            Matcher matcher = pat.matcher(sisalto);
            // Etsit��n onko sis�ll�ss� ainoastaan v�lily�ntej�.
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
     * Tarkistaa, onko merkkijonossa enemm�n kuin yksi v�lily�nti.
     * 
     * @param sisalto,
     *            viestin sis�lt�.
     * @throws IllegalArgumentException,
     *             heitet��n jos v�lily�ntej� on enemm�n kuin yksi.
     */
    public void tarkistaParametrit(String sisalto) throws IllegalArgumentException {
        int space_counter = 0;
        // K�yd��n l�pi komennon sis�lt� yksi kirjain kerrallaan.
        for (int i = 0; i < sisalto.length(); i++) {
            if (sisalto.charAt(i) == ' ') {
                space_counter++;
            }
        }
        // Jos v�lily�ntej� on enemm�n kuin yksi, heitet��n virhe.
        if (space_counter > 1) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Hyv�stelee k�ytt�j�n.
     */
    public void hyvastele() {
        System.out.println("Bye! See you soon.");
    }

    /**
     * Herjaa k�ytt�j�� kertomalla virheen tapahtuneen.
     */
    public void herjaa() {
        System.out.println(VIRHE);
    }
}
