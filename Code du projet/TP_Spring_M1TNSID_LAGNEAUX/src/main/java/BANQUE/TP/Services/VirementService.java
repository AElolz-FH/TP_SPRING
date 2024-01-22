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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
public class VirementService {
    @Autowired
    transactionRepository transactionRepository;
    @Autowired
    CompteRepository compteRepository;

    public creerVirementReponse creerVirement(creerVirementPayload payload) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            String dateString = "2022-11-28T18:46:19";
            LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);

            Compte compteEmetteur = this.compteRepository.findCompteByIban(payload.getIbanCompteEmetteur());

            if (compteEmetteur == null) {
                throw new Exception("Le compte émetteur est nul");
            }

            Client clientEmetteur = (Client) compteEmetteur.getTitulairesCompte();
            Integer idClientEmetteur = clientEmetteur.getId();

            Transactions transactions = Transactions.builder()
                    .montant(payload.getMontant())
                    .typeTransaction("Virement")
                    .typeSource("Compte")
                    .idSource(idClientEmetteur)
                    .compte(compteEmetteur)
                    .dateTransaction(String.valueOf(dateTime))
                    .build();

            if (transactions == null) {
                throw new Exception("La transaction est nulle");
            }

            //on enregistre la transaction
            Transactions virementEnBase = this.transactionRepository.save(transactions);

            compteEmetteur.getTransactionsCompte().add(virementEnBase);
            Compte sauvegardé = this.compteRepository.save(compteEmetteur);

            return creerVirementReponse.builder()
                    .idVirement(virementEnBase.getIdTransaction())
                    .dateCreation(virementEnBase.getDateTransaction())
                    .transactions(compteEmetteur.getTransactionsCompte())
                    .build();
        } catch (Exception e) {
            return null;
        }
    }




}
