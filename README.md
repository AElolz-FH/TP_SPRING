# TP_SPRING

## Problèmes

### Formats de données
- Les formats de données sont pour la plupart corrects, mais ils ne sont pas tous identiques au Swagger.

### Requêtes

#### Requête POST /comptes/{iban}/cartes
- Actuellement, on ne peut poster qu'une seule carte par IBAN. Cependant, le modèle ne correspond pas exactement au schéma car il devrait permettre plusieurs cartes par compte.

#### Requête GET /comptes/{iban}/cartes
- Malgré le problème mentionné dans la requête POST correspondante, la méthode GET /comptes/{iban}/cartes récupère correctement une liste de cartes.

#### Requête POST /comptes/{iban}/cartes/{numeroCarte}/paiement
- La requête renvoie des valeurs null, et il semble y avoir un problème lors de la sauvegarde des entités en base. Aucun enregistrement n'est présent dans la base de données.

#### Requête POST /virement/
- La requête affiche des valeurs null, et il n'y a pas d'enregistrement en base de données.


