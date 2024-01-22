/*
**************************
@author -Lagneaux Grégory-
**************************
 */

package BANQUE.TP.Repo;


import BANQUE.TP.Entity.Carte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CarteRepository extends JpaRepository<Carte,Integer> {
    @Query("SELECT c FROM Carte c WHERE :titulaire IN elements(c.listeTitulaires)")
    Carte findCarteByTitulaireCarte(@Param("titulaire") String titulaire);

    Carte findCarteByNumeroCarte(Long numeroCarte);
}
