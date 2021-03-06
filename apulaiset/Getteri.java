package oope2018ht.apulaiset;

import java.lang.annotation.*;

/**
  * Annotaatio, jolla autetaan WETOa tunnistamaan attribuuttien lukumetodit (getterit).
  * 
  * <p>
  * Harjoitusty�, Olio-ohjelmoinnin perusteet, kev�t 2018.
  * <p>
  * @author Jorma Laurikkala (jorma.laurikkala@uta.fi), Luonnontieteiden tiedekunta,
  * Tampereen yliopisto.
  *
  */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Getteri {
}
