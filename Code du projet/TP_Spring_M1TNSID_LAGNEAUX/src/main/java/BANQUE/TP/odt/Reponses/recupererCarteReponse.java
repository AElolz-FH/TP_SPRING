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
public class recupererCarteReponse {
    private Long numeroCarte;
    private String dateExpiration;
    private String titulaireCarte;
}
