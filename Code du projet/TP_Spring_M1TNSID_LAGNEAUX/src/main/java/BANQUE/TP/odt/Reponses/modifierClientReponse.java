/*
**************************
@author -Lagneaux Grégory-
**************************
 */

package BANQUE.TP.odt.Reponses;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class modifierClientReponse {
    private String prenom;
    private String nom;
    private Date dateNaissance;
    private String telephone;
    private String adressePostale;
    private String nouvelleAdresse;
}