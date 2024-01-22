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
public class CreerClientPayload {
    private String prenom;
    private String nom;
    private String dateNaissance;
    private String telephone;
    private String adressePostale;
}

