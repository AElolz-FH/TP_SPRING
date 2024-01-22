/*
**************************
@author -Lagneaux Grégory-
**************************
 */

package BANQUE.TP.odt.Reponses;

import BANQUE.TP.Entity.Transactions;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class creerVirementReponse {
    private Long idVirement;
    private String dateCreation;
    private List<Transactions> transactions;

    public creerVirementReponse(String s) {
    }
}
