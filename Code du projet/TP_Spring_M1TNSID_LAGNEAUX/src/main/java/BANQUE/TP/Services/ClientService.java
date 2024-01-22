/*
**************************
@author -Lagneaux Grégory-
**************************
 */

package BANQUE.TP.Services;

import BANQUE.TP.Entity.Client;
import BANQUE.TP.Exception.BadRequest;
import BANQUE.TP.Exception.NotFound;
import BANQUE.TP.Repo.ClientRepository;
import BANQUE.TP.Repo.CompteRepository;
import BANQUE.TP.odt.Reponses.CreerClientReponse;
import BANQUE.TP.odt.Reponses.modifierClientReponse;
import BANQUE.TP.odt.Reponses.recupererClientReponse;
import BANQUE.TP.odt.Request.CreerClientPayload;
import BANQUE.TP.odt.Request.ModifierClientPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    CompteRepository compteRepository;
    //récupère une entité du repo

    //service de création d'un client
    public CreerClientReponse creerClient(CreerClientPayload creerClientPayload) throws BadRequest, ParseException {
        Date dateNaissance = new SimpleDateFormat("dd-MM-yyyy").parse(creerClientPayload.getDateNaissance());
        if (!creerClientPayload.getTelephone().matches("(0|\\+33|0033)[1-9][0-9]{8}") ||
                creerClientPayload.getPrenom().isEmpty() ||
                creerClientPayload.getNom().isEmpty() ||
                creerClientPayload.getAdressePostale().isEmpty()) {
            throw new BadRequest("Les donnnées en entrée du service sont non renseignes ou incorrectes. Erreur 400");
        }


        Client clientAEnregistrer = Client.builder()
                .prenom(creerClientPayload.getPrenom())
                .nom(creerClientPayload.getNom())
                .dateNaissance(dateNaissance)
                .telephone(creerClientPayload.getTelephone())
                .adressePostale(creerClientPayload.getAdressePostale())
                .dateCreation(Instant.now())
                .build();

        Client clientSauvegardeEnBase = this.clientRepository.save(clientAEnregistrer);

        return CreerClientReponse.builder()
                .id(clientSauvegardeEnBase.getId())
                .prenom(clientSauvegardeEnBase.getPrenom())
                .nom(clientSauvegardeEnBase.getNom())
                .dateNaissance(clientSauvegardeEnBase.getDateNaissance())
                .telephone(clientSauvegardeEnBase.getTelephone())
                .adressePostale(clientSauvegardeEnBase.getAdressePostale())
                .dateCreation(Instant.now())
                .build();

    }

    public List<recupererClientReponse> getAllClients() throws NotFound {
        List<Client> clients = clientRepository.findAll();
        if (clients.isEmpty()) {
            throw new NotFound("Aucune donnée à renvoyer. Erreur 204");
        }
        List<recupererClientReponse> reponse = new ArrayList<>();
        for (Client client : clients) {
            reponse.add(recupererClientReponse.builder()
                    .prenom(client.getPrenom())
                    .nom(client.getNom())
                    .adressePostale(client.getAdressePostale())
                    .telephone(client.getTelephone())
                    .dateNaissance(client.getDateNaissance())
                    .build());
        }
        return reponse;
    }

    //récupérer un client grâce à son nom et son prénom
    public List<recupererClientReponse> recupererClientParNomPrenom(String prenom, String nom) throws BadRequest, NotFound {
        if (nom.isEmpty() || prenom.isEmpty()) {
            throw new BadRequest("Les données en entrée sont non renseignées ou incorrectes");
        }
        List<Client> clients = clientRepository.findByPrenomAndNom(prenom, nom);
        if (clients.isEmpty()) {
            throw new NotFound("Aucune donnée à renvoyer. Erreur 404");
        }
        List<recupererClientReponse> reponse = new ArrayList<>();
        for (Client client : clients) {
            reponse.add(recupererClientReponse.builder()
                    .prenom(client.getPrenom())
                    .nom(client.getNom())
                    .adressePostale(client.getAdressePostale())
                    .telephone(client.getTelephone())
                    .dateNaissance(client.getDateNaissance())
                    .build());
        }
        return reponse;
    }

    public List<recupererClientReponse> recupererClientParNomPrenomTelephone(String nom, String prenom, String telephone) throws BadRequest, NotFound {
        if (nom.isEmpty() || prenom.isEmpty() || telephone.isEmpty()) {
            throw new BadRequest("Les données en entrée sont non renseignées ou incorrectes");
        }
        List<Client> clients = clientRepository.findByPrenomAndNomAndTelephone(nom, prenom, telephone);
        if (clients.isEmpty()) {
            throw new NotFound("Aucune donnée à renvoyer. Erreur 500");
        }
        List<recupererClientReponse> reponse = new ArrayList<>();
        for (Client client : clients) {
            reponse.add(recupererClientReponse.builder()
                    .prenom(client.getPrenom())
                    .nom(client.getNom())
                    .adressePostale(client.getAdressePostale())
                    .telephone(client.getTelephone())
                    .dateNaissance(client.getDateNaissance())
                    .build());
        }
        return reponse;
    }

    public modifierClientReponse modifierClient(Integer idClient, ModifierClientPayload payload) throws NotFound, BadRequest {
        String nouvelleAdresse = payload.getNewAdressePostale();
        String telephone = payload.getTelephone();
        if (idClient == null || idClient <= 0) {
            throw new BadRequest("L'ID du client est invalide");
        }

        Client client = this.clientRepository.findById(idClient);

        if (client != null) {

            client.setAdresseModification(nouvelleAdresse);
            client.setTelephone(telephone);

            clientRepository.save(client);

            return modifierClientReponse.builder()
                    .prenom(client.getPrenom())
                    .nom(client.getNom())
                    .dateNaissance(client.getDateNaissance())
                    .telephone(client.getTelephone())
                    .adressePostale(client.getAdressePostale())
                    .nouvelleAdresse(client.getAdresseModification())
                    .build();
        } else {
            throw new NotFound("Le client n'a pas été trouvé avec l'ID : " + idClient);
        }
    }

}



