/*
**************************
@author -Lagneaux Grégory-
**************************
 */

package BANQUE.TP.Repo;

import BANQUE.TP.Entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {

    public List<Client> findByPrenomAndNom(String prenom,String nom);
    public List<Client> findByPrenomAndNomAndTelephone(String nom,String prenom,String telephone);
    public Client findById(Integer Id);

}
