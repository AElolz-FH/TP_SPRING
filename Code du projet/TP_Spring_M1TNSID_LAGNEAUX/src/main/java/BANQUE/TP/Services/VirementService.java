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
import jakarta.transaction.Transactional;
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
    @Transactional
    public creerVirementReponse creerVirement(creerVirementPayload payload) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.now();

            Compte compteEmetteur = this.compteRepository.findCompteByIban(payload.getIbanCompteEmetteur());

            if (compteEmetteur == null) {
                throw new RuntimeException("Le compte émetteur est nul");
            }

            Client clientEmetteur = (Client) compteEmetteur.getTitulairesCompte();
            Integer idClientEmetteur = clientEmetteur.getId();

            Transactions transactionsEmetteur = Transactions.builder()
                    .montant(payload.getMontant())
                    .typeTransaction("Virement")
                    .typeSource("Compte")
                    .idSource(idClientEmetteur)
                    .compte(compteEmetteur)
                    .dateTransaction(dateTime.toString())
                    .build();

            Transactions virementEmetteurEnBase = this.transactionRepository.save(transactionsEmetteur);

            compteEmetteur.getTransactionsCompte().add(virementEmetteurEnBase);

            this.compteRepository.save(compteEmetteur);

            Compte compteBeneficiaire = this.compteRepository.findCompteByIban(payload.getIbanCompteBeneficiaire());

            if (compteBeneficiaire == null) {
                throw new RuntimeException("Le compte bénéficiaire est nul");
            }

            Transactions transactionsBeneficiaire = Transactions.builder()
                    .montant(payload.getMontant())
                    .typeTransaction("Virement")
                    .typeSource("Compte")
                    .idSource(idClientEmetteur)
                    .compte(compteBeneficiaire)
                    .dateTransaction(dateTime.toString())
                    .build();

            Transactions virementBeneficiaireEnBase = this.transactionRepository.save(transactionsBeneficiaire);

            compteBeneficiaire.getTransactionsCompte().add(virementBeneficiaireEnBase);

            this.compteRepository.save(compteBeneficiaire);

            return creerVirementReponse.builder()
                    .idVirement(virementEmetteurEnBase.getIdTransaction())
                    .dateCreation(virementEmetteurEnBase.getDateTransaction())
                    .transactions(compteEmetteur.getTransactionsCompte())
                    .build();
        } catch (Exception e) {
            return null;
        }
    }






}
