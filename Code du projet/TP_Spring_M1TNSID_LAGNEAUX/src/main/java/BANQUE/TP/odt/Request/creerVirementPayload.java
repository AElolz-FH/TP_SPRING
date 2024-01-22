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
public class creerVirementPayload {
    private String ibanCompteEmetteur;
    private String ibanCompteBeneficiaire;
    private double montant;
    private String libelleVirement;
}
