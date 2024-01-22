Bonjour, j'ai eu quelques problèmes pour mes requêtes, les formats sont pour la plupart respectés, or pour ce qu'il en ait de la requête POST /comptes/{iban}/cartes on ne peut poster qu'une seule carte par iban, ce qui était demandé était de pouvoir avoir plusieurs cartes par compte, (mon modèle ne correspond donc pas exactement à mon schéma)
Sur ma requête GET /comptes/{iban}/cartes je récupère bien une liste de cartes
Ma requête POST /comptes/{iban}/cartes/{numeroCarte}/paiement doit ne pas marcher à cause de ça,
Il est possible aussi que mes données soient mal reliées.