/*
**************************
@author -Lagneaux Grégory-
**************************
 */

package BANQUE.TP.Services;

import BANQUE.TP.Entity.Carte;
import BANQUE.TP.Entity.Client;
import BANQUE.TP.Entity.Compte;
import BANQUE.TP.Entity.Transactions;
import BANQUE.TP.Exception.BadRequest;
import BANQUE.TP.Exception.NotFound;
import BANQUE.TP.Repo.*;
import BANQUE.TP.odt.Reponses.*;
import BANQUE.TP.odt.Request.CreerComptePayload;
import BANQUE.TP.odt.Request.creerCartePayload;
import BANQUE.TP.odt.Request.creerPaiementPayload;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CompteServices {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    CompteRepository compteRepository;
    @Autowired
    transactionRepository transactionRepository;
    @Autowired
    CarteRepository carteRepository;
    private static final Logger logger = LoggerFactory.getLogger(CompteServices.class);


    /*
    Les comptes possèdent un solde. Les comptes sont identifiés à l’aide d’un IBAN (International Bank Account Number).
    Les iban français sont composés comme ceci : le code pays sur 2 caractères, la clé de contrôle sur 2 caractères :
    Pour un iban français FR76. Puis du code banque sur 5 caractères (sera celui du client),
    le code guichet/agence sur 5 caractères (sera celui du client) puis numéro de compte sur 11 caractères et
    une clé RIB de 2 caractères calculée de la manière suivante :

    Clé RIB = 97 - ( (
            89 x Code banque +
            15 x Code guichet +
            3 x Numéro de compte ) modulo 97 )
    Exemple pour l’iban suivant :
    FR76 30003 02054 10313400399 49

    Exemple de code banque : 30003
    Exemple de code guichet : 02054
    */


    @Transactional
    public CreerCompteReponse creerCompte(CreerComptePayload payload, Long idClient) throws BadRequest, NotFound {
        Objects.requireNonNull(payload, "Le payload ne peut pas être null. Erreur 400");
        Objects.requireNonNull(payload.getIntituleCompte(), "L'intitulé du compte ne peut pas être null. Erreur 400");
        Objects.requireNonNull(payload.getTypeCompte(), "Le type du compte ne peut pas être null. Erreur 400");
        Objects.requireNonNull(payload.getTitulairesCompte(), "La liste des titulaires ne peut pas être null. Erreur 400");

        if (!this.clientRepository.existsById(idClient)) {
            throw new NotFound("Le client avec l'id " + idClient + " n'existe pas. Erreur 500");
        }

        List<Integer> idTitulaires = payload.getTitulairesCompte();
        List<Client> titulaires = new ArrayList<>();

        for (Integer idTitulaire : idTitulaires) {
            Long titulaireId = Long.valueOf(idTitulaire);
            Client titulaire = this.clientRepository.findById(titulaireId)
                    .orElseThrow(() -> new NotFound("Le titulaire avec l'id " + titulaireId + " n'existe pas. Erreur 404"));
            titulaires.add(titulaire);
        }

        List<Integer> idTitulairesReponse = titulaires.stream()
                .map(Client::getId)
                .collect(Collectors.toList());


        Integer idC = idTitulairesReponse.get(0);
        String formattedIdClient = String.format("%05d", idC);
        Long idCompte = Long.valueOf(titulaires.get(0).getId());
        String formattedIdCompte = String.format("%05d", idCompte);

        Long ribKey = 97 - ((89L *idC
                + 15L *idC
                +3*idCompte)%97);
        String formattedRib = String.format("%02d",ribKey);

        String iban = "FR76"+" "+formattedIdClient+" "+formattedIdClient+" "+formattedIdCompte+" "+formattedRib;

        Compte compte = Compte.builder()
                .iban(iban)
                .intituleCompte(payload.getIntituleCompte())
                .typeCompte(payload.getTypeCompte())
                .titulairesCompte(titulaires)
                .transactionsCompte(null)
                .solde((double)0)
                .build();

        Compte compteEnBase = this.compteRepository.save(compte);

        return CreerCompteReponse.builder()
                .intituleCompte(compteEnBase.getIntituleCompte())
                .typeCompte(compteEnBase.getTypeCompte())
                .titulairesCompte(idTitulairesReponse)
                .iban(iban)
                .dateCreation(String.valueOf(Instant.now()))
                .build();
    }

    @Transactional
    public List<recupererCompteReponse> recupererComptesClient(Integer idClient) {
        List<Compte> comptes = compteRepository.findComptesByClientId(Math.toIntExact(idClient));
        List<recupererCompteReponse> responses = new ArrayList<>();

        for (Compte compte : comptes) {
            List<Transactions> transactions = transactionRepository.findTransactionsByCompteId(compte.getIdCompte());

            recupererCompteReponse response = recupererCompteReponse.builder()
                    .iban(compte.getIban())
                    .solde(compte.getSolde())
                    .intituleCompte(compte.getIntituleCompte())
                    .typeCompte(compte.getTypeCompte())
                    .titulairesCompte(compte.getTitulairesCompte().stream().map(Client::getId).collect(Collectors.toList()))
                    .transactions(transactions)
                    .build();

            responses.add(response);
        }

        return responses;
    }

    public static int generateCardCode() {
        Random random = new Random();
        return random.nextInt(10000);
    }



    private String generateRandomCardNumber() {
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            int digit = random.nextInt(10);
            cardNumber.append(digit);
        }
        return cardNumber.toString();
    }

    private String generateDateExpiration() {
        LocalDate currentDate = LocalDate.now();
        LocalDate expirationDate = currentDate.plusYears(5);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        return expirationDate.format(formatter);
    }

    @Transactional
    public creerCarteReponse createCarte(String iban, creerCartePayload payload) throws NotFound {
        // on récupère le client qui est titulaire du compte grâce au payload
        Client titulaire = clientRepository.findById(Integer.parseInt(payload.getTitulaireCarte().get(0)));

        if(titulaire == null)
        {
            throw new NotFound("Le titulaire n'a pas été trouvé.");
        }

        // on récupère le compte qui a l'iban passé en paramètres
        Compte compte = this.compteRepository.findCompteByIban(iban);
        if (compte == null) {
            throw new NotFound("Pas de compte trouvé pour l'iban : "+iban);
        }

        //on génère un numéro de carte aléatoire pour la carte
        String numeroCarte = generateRandomCardNumber();

        //on génère la date d'expiration
        String dateExpiration = generateDateExpiration();
        //on sépare le mois et l'année
        String moisExpiration = dateExpiration.substring(0,2);
        String anneeExpiration = dateExpiration.substring(3,5);

        //on crée une carte
        Carte carte = Carte.builder()
                .numeroCarte(Long.parseLong(numeroCarte))
                .moisExpiration(Integer.parseInt(moisExpiration))
                .anneeExpiration(Integer.parseInt(anneeExpiration))
                .password(String.valueOf(generateCardCode()))
                .compte(compte)
                .listeTitulaires(List.of(titulaire))
                .build();

        //on sauvegarde la carte dans le repository
        String premierTitulaire = payload.getTitulaireCarte().isEmpty() ? "" : String.valueOf(payload.getTitulaireCarte().get(0));
        this.carteRepository.save(carte);

        //on retourne une reponse avec un string qui représente l'idClient, un numéro de carte qui a été généré,
        //une date d'expiration au format MMYY
        return creerCarteReponse.builder()
                .titulaireCarte(String.valueOf(payload.getTitulaireCarte().get(0)))
                .numeroCarte(numeroCarte)
                .dateExpiration(String.format("%02d/%02d", Integer.parseInt(moisExpiration), Integer.parseInt(anneeExpiration)))
                .build();
    }


    public List<recupererCarteReponse> recupererCarte(String iban) throws NotFound {
        // récupérer la liste des cartes d'un compte grâce à son iban
        Compte compte = this.compteRepository.findCompteByIban(iban);

        if (compte == null) {
            throw new NotFound("Le compte n'a pas été trouvé pour l'iban : "+iban);
        }

        List<Carte> cartes = compte.getCartes();
        List<recupererCarteReponse> reponses = new ArrayList<>();

        for (Carte carte : cartes) {
            recupererCarteReponse carteReponse = recupererCarteReponse.builder()
                    .numeroCarte(carte.getNumeroCarte())
                    .dateExpiration(String.format("%02d/%02d", carte.getMoisExpiration(), carte.getAnneeExpiration()))
                    .titulaireCarte(String.valueOf(carte.getListeTitulaires().get(0).getId()))
                    .build();
            reponses.add(carteReponse);
        }

        return reponses;
    }


    //méthode qui pourra être utilisée pour gérer les débits et crédits
    public double debit_credit(Compte c,double montant,String type)
    {
        if(type.equals("DEBIT"))
        {
            return c.getSolde() - montant;
        } else if (type.equals("CREDIT")) {
            return c.getSolde() + montant;
        }
        System.out.println("Le montant du solde du compte reste inchangé");
        return montant;
    }

    public String credit_ou_debit(double montant)
    {
        if(montant < 0)
        {
            return "DEBIT";
        } else{
            return "CREDIT";
        }
    }

    @Transactional
    public creerPaiementReponse creerPaiement(String iban, String numeroCarte, creerPaiementPayload payload) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            String dateString = "2022-11-28T18:46:19";
            LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);


            logger.info("Montant de la transaction : {}", payload.getMontant());

            Transactions paiement = Transactions.builder()
                    .montant(payload.getMontant())
                    .dateTransaction(String.valueOf(dateTime))
                    .build();

            if (paiement == null) {
                throw new Exception("La transaction est nulle");
            }

            logger.info("Montant de la transaction : {}", paiement.getMontant());

            Compte compte = this.compteRepository.findCompteByIban(iban);

            if (compte == null) {
                throw new Exception("Le compte est nul");
            }

            compte.getTransactionsCompte().add(paiement);

            Transactions savedTransaction = this.transactionRepository.save(paiement);
            Compte sauvegardé = this.compteRepository.save(compte);

            logger.info("Montant de la transaction : {}", savedTransaction.getMontant());

            return creerPaiementReponse.builder()
                    .idTransaction(Integer.parseInt(String.valueOf(savedTransaction.getIdTransaction())))
                    .montant(savedTransaction.getMontant())
                    .typeTransaction("DEBIT")
                    .dateCreation(savedTransaction.getDateTransaction())
                    .build();
        } catch (Exception e) {
            logger.error("Une erreur s'est produite lors de la création du paiement", e);
            return null;
        }
    }






















}

