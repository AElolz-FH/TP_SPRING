/*
**************************
@author -Lagneaux Grégory-
**************************
 */

package BANQUE.TP.odt.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class creerPaiementPayload {
    private double montant;
    private String dateCreation;
}
