/*
**************************
@author -Lagneaux Grégory-
**************************
 */


package BANQUE.TP.odt.Reponses;

import java.util.List;

import BANQUE.TP.Entity.Transactions;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class recupererCompteReponse {
    private String iban;
    private double solde;
    private String intituleCompte;
    private String typeCompte;
    private List<Integer> titulairesCompte;
    private List<Transactions> transactions;
}
