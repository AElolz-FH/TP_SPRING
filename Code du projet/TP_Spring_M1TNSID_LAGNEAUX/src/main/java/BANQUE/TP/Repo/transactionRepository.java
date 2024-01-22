/*
**************************
@author -Lagneaux Grégory-
**************************
 */

package BANQUE.TP.Repo;


import java.util.List;
import BANQUE.TP.Entity.Transactions;
import org.hibernate.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface transactionRepository extends JpaRepository<Transactions, Long> {
    @Query("SELECT t FROM Transactions t WHERE t.compte.idCompte = :idCompte")
    List<Transactions> findTransactionsByCompteId(@Param("idCompte") Long idCompte);
}


