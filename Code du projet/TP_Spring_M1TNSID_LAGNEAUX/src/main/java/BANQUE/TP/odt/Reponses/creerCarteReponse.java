/*
**************************
@author -Lagneaux Grégory-
**************************
 */

package BANQUE.TP.odt.Reponses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class creerCarteReponse {
    private String titulaireCarte;
    private String numeroCarte;
    private String dateExpiration;
}
