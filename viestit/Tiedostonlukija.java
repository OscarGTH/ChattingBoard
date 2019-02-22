package oope2018ht.viestit;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Tiedoston lukemiseen tarvittava luokka, sis�lt�� metodin mik� lukee tiedoston.
 * <p>
 * Harjoitusty�, Olio-ohjelmoinnin perusteet, kev�t 2018.
 * <p>
 * 
 * @author Oskari Niskanen (Niskanen.Oskari.T@student.uta.fi) Luonnontieteiden
 *         tiedekunta, Tampereen yliopisto.
 */
public class Tiedostonlukija {
    /**
     * Lukee tiedoston ja palauttaa sen tiedot.
     * 
     * @param nimi,
     *          tiedoston nimi merkkijonona.
     * @return tiedostonTiedot,
     *              palauttaa taulukon, mik� sis�lt�� tiedoston tietoja.     
     * @throws IllegalArgumentException,
     *              jos tiedostoa ei l�ydy tai muita virheit� ilmenee.
     */
    public String[] lueTiedosto(String nimi) throws IllegalArgumentException {
      try { 
          // Luodaan uusi String-taulukko, johon l�ytyneet tiedot tallennetaan.
          String[] tiedostonTiedot = new String[4];
          File tiedosto = new File(nimi);
          BufferedReader tiedostonLukija = new BufferedReader(new FileReader(tiedosto));
          // Asetetaan lukija p�tkim��n v�lily�ntien kohdalla ja asettamaan ne taulukkoon.
          tiedostonTiedot = tiedostonLukija.readLine().split(" ");
          // Suljetaan lukija.
          tiedostonLukija.close();
          return tiedostonTiedot;
      } catch(Exception e) {
          throw new IllegalArgumentException();
      }
    }    
}
