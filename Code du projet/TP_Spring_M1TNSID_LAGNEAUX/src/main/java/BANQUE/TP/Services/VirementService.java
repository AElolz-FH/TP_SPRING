/*
**************************
@author -Lagneaux Grégory-
**************************
 */

package BANQUE.TP.Services;


import BANQUE.TP.Entity.Client;
import BANQUE.TP.Entity.Compte;
import BANQUE.TP.Entity.Transactions;
import BANQUE.TP.Repo.CompteRepository;
import BANQUE.TP.Repo.transactionRepository;
import BANQUE.TP.odt.Reponses.creerVirementReponse;
import BANQUE.TP.odt.Request.creerVirementPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class VirementService {
    @Autowired
    transactionRepository transactionRepository;
    @Autowired
    CompteRepository compteRepository;

    public creerVirementReponse creerVirement(creerVirementPayload payload) {
        // On récupère le compte émetteur
        Compte compteEmetteur = this.compteRepository.findCompteByIban(payload.getIbanCompteEmetteur());

        // On récupère le client pour obtenir son ID
        Client clientEmetteur = (Client) compteEmetteur.getTitulairesCompte();
        Integer idClientEmetteur = clientEmetteur.getId();

        // On crée une nouvelle transaction
        Transactions transactions = Transactions.builder()
                .montant(payload.getMontant())
                .typeTransaction("Virement")
                .typeSource("Compte")
                .idSource(idClientEmetteur)
                .compte(compteEmetteur)
                .dateTransaction("test")
                .build();

        //on enregistre la transaction
        Transactions virementEnBase = this.transactionRepository.save(transactions);

        compteEmetteur.getTransactionsCompte().add(virementEnBase);
        this.compteRepository.save(compteEmetteur);

        return creerVirementReponse.builder()
                .idVirement(virementEnBase.getIdTransaction())
                .dateCreation(virementEnBase.getDateTransaction())
                .transactions(compteEmetteur.getTransactionsCompte())
                .build();
    }


}
