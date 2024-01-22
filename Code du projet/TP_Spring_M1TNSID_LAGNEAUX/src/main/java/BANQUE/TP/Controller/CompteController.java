/*
**************************
@author -Lagneaux Grégory-
**************************
 */

package BANQUE.TP.Controller;

import BANQUE.TP.Exception.BadRequest;
import BANQUE.TP.Exception.NotFound;
import BANQUE.TP.Repo.ClientRepository;
import BANQUE.TP.Repo.CompteRepository;
import BANQUE.TP.Repo.transactionRepository;
import BANQUE.TP.Services.CompteServices;
import BANQUE.TP.odt.Reponses.*;
import BANQUE.TP.odt.Request.CreerComptePayload;
import BANQUE.TP.odt.Request.creerCartePayload;
import BANQUE.TP.odt.Request.creerPaiementPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/Compte")
public class CompteController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private CompteServices compteService;
    @Autowired
    private transactionRepository transactionRepository;

    @GetMapping("/{clientId}/comptes")
    public ResponseEntity<List<recupererCompteReponse>> getComptesByClientId(@PathVariable Integer clientId) {
        List<recupererCompteReponse> comptes = compteService.recupererComptesClient(clientId);
        return ResponseEntity.ok().body(comptes);
    }

    @PostMapping("/creerCompte")
    public ResponseEntity<CreerCompteReponse> creerCompte(@RequestBody CreerComptePayload payload) throws NotFound, BadRequest, ParseException {
        Integer idClient = payload.getTitulairesCompte().get(0);
        if(this.clientRepository.existsById(Long.valueOf(idClient))){
            CreerCompteReponse compte = compteService.creerCompte(payload,Long.valueOf(idClient));
            return ResponseEntity.status(HttpStatus.CREATED).body(compte);
        }
        throw new NotFound("Le client avec l'id " + idClient + " n'existe pas.");
    }


    @PostMapping("/{iban}/cartes")
    public ResponseEntity<creerCarteReponse> creerCarte(@PathVariable String iban, @RequestBody creerCartePayload payload) {
        try {
            creerCarteReponse carte = compteService.createCarte(iban, payload);
            return ResponseEntity.status(HttpStatus.CREATED).body(carte);
        } catch (NotFound e) {
            creerCarteReponse erreurResponse = new creerCarteReponse("Titulaire non trouvé", null, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erreurResponse);
        }
    }

    @GetMapping("/{iban}/cartes")
    public ResponseEntity<List<recupererCarteReponse>> recupererCarte(@PathVariable String iban) {
        try {
            List<recupererCarteReponse> cartes = compteService.recupererCarte(iban);
            return ResponseEntity.ok().body(cartes);
        } catch (NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PostMapping("/{iban}/cartes/{numeroCarte}/paiement")
    public ResponseEntity<creerPaiementReponse> creerPaiement(@PathVariable String iban, @PathVariable String numeroCarte, @RequestBody creerPaiementPayload payload)
    {
        try {
            creerPaiementReponse paiementReponse = this.compteService.creerPaiement(iban, numeroCarte, payload);
            return ResponseEntity.ok().body(paiementReponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new creerPaiementReponse("Une erreur s'est produite lors de la création du paiement."));
        }
    }






}

