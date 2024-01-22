/*
**************************
@author -Lagneaux Grégory-
**************************
 */

package BANQUE.TP.odt.Reponses;

import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class creerPaiementReponse {
    private int idTransaction;
    private double montant;
    private String typeTransaction;
    private String dateCreation;

    public creerPaiementReponse(String s) {
    }
}
