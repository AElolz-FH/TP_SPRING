# TP_SPRING

## Problèmes

### Formats de données
- Les formats de données sont pour la plupart corrects, mais ils ne sont pas tous identiques au Swagger.

### Requêtes

#### Requête POST /comptes/{iban}/cartes
- Actuellement, on ne peut poster qu'une seule carte par IBAN. Cependant, le modèle ne correspond pas exactement au schéma car il devrait permettre plusieurs cartes par compte.

#### Requête GET /comptes/{iban}/cartes
- Malgré le problème mentionné dans la requête POST correspondante, la méthode GET /comptes/{iban}/cartes récupère correctement une liste de cartes.

#### Requête POST /virement/
- La requête n'affiche pas de valeurs de retour, malgré le payload, il doit sûrement y avoir une erreur d'enregistrement

##Remarque
-Lorsque je crée une transaction, et je que récupère les comptes avec la méthode : GET/Compte/{id}/comptes la liste des transactions n'est pas mise à jour


