/*
**************************
@author -Lagneaux Grégory-
**************************
 */

package BANQUE.TP.odt.Reponses;

import java.util.List;
import BANQUE.TP.Entity.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreerCompteReponse {
    private String intituleCompte;
    private String typeCompte;
    private List<Integer> titulairesCompte;
    private String iban;
    private String dateCreation;
}
