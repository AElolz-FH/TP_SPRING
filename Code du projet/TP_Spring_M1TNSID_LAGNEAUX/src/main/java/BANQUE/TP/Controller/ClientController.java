/*
**************************
@author -Lagneaux Grégory-
**************************
 */

package BANQUE.TP.Controller;



import BANQUE.TP.Entity.Client;
import BANQUE.TP.Entity.Compte;
import BANQUE.TP.Exception.BadRequest;
import BANQUE.TP.Exception.NotFound;
import BANQUE.TP.Repo.ClientRepository;
import BANQUE.TP.Services.ClientService;
import BANQUE.TP.odt.Reponses.CreerClientReponse;
import BANQUE.TP.odt.Reponses.modifierClientReponse;
import BANQUE.TP.odt.Reponses.recupererClientReponse;
import BANQUE.TP.odt.Request.CreerClientPayload;
import BANQUE.TP.odt.Request.ModifierClientPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("clients")
public class ClientController {
    @Autowired
    private ClientService clientService;
    private ClientRepository clientRepository;

    @GetMapping("/getClients")
    public ResponseEntity<List<recupererClientReponse>> recupererClients(@RequestParam(required = true) String prenom,
                                                                         @RequestParam(required = true) String nom,
                                                                         @RequestParam(required = false) String telephone) {
        if(telephone.isEmpty()) {
            try {
                return ResponseEntity.ok().body(this.clientService.recupererClientParNomPrenom(prenom, nom));
            } catch (BadRequest e) {
                return ResponseEntity.badRequest().build();
            } catch (NotFound e) {
                return ResponseEntity.notFound().build();
            }
        } else {
            try {
                return ResponseEntity.ok().body(this.clientService.recupererClientParNomPrenomTelephone(prenom, nom,telephone));
            } catch (BadRequest e) {
                return ResponseEntity.badRequest().build();
            } catch (NotFound e) {
                return ResponseEntity.notFound().build();
            }
        }
    }

    @GetMapping("/getAllClients")
    public ResponseEntity<List<recupererClientReponse>> getAllClients() {
            try {
                return ResponseEntity.ok().body(this.clientService.getAllClients());
            } catch (NotFound e) {
                return ResponseEntity.notFound().build();
            }

    }

    @PostMapping
    public ResponseEntity<CreerClientReponse> addClient(@RequestBody CreerClientPayload client) {
        try {
            return ResponseEntity.ok().body(this.clientService.creerClient(client));
        } catch (BadRequest | ParseException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/modifClient")
    public ResponseEntity<modifierClientReponse> modifDateClient(@RequestBody ModifierClientPayload client) {
        try{
            return ResponseEntity.ok().body(this.clientService.modifierClient(client.getId(),client));
        }catch (BadRequest e){
            return ResponseEntity.badRequest().build();
        } catch (NotFound e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{clientId}/comptes")
    public ResponseEntity<List<Compte>> getComptesByClientId(@PathVariable Integer clientId) {
        Client client = clientRepository.findById(clientId);
        if (client != null) {
            List<Compte> comptes = client.getComptes();
            return ResponseEntity.ok().body(comptes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

