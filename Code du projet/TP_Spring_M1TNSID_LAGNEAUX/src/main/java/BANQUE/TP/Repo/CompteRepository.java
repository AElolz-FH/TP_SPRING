/*
**************************
@author -Lagneaux Grégory-
**************************
 */

package BANQUE.TP.Repo;

import BANQUE.TP.Entity.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompteRepository extends JpaRepository<Compte,String> {
    Compte findAllByTitulairesCompte_id(Integer id);
    @Query("SELECT c FROM Compte c JOIN c.titulairesCompte t WHERE t.id = :clientId")
    List<Compte> findComptesByClientId(@Param("clientId") Integer clientId);
    List<Compte> findComptesByTitulairesCompte_id(Integer id);


    @Query("SELECT c FROM Compte c WHERE c.iban = :iban")
    Compte findCompteByIban(@Param("iban") String iban);


    Compte findByIban(String iban);
}
