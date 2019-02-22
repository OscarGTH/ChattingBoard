import oope2018ht.kayttoliittyma.*;
/**
 * Harjoitustyön ajoluokka, missä luodaan uusi käyttöliittymä olio ja kutsutaan sen metodia.
 * <p>
 * Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2018.
 * <p>
 * 
 * @author Oskari Niskanen (Niskanen.Oskari.T@student.uta.fi) Luonnontieteiden
 *         tiedekunta, Tampereen yliopisto.
 */
public class Oope2018HT {
    public static void main(String[] args) {
        // Luodaan uusi käyttöliittymä olio.
        Kayttoliittyma kayttoliittyma = new Kayttoliittyma();
        kayttoliittyma.aloita(); 
    }
}
