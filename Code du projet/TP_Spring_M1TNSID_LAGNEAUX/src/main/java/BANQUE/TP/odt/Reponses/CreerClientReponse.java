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
import java.util.Date;
import java.time.Instant;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreerClientReponse {
    private Integer id;
    private String prenom;
    private String nom;
    private Date dateNaissance;
    private String telephone;
    private String adressePostale;
    private Instant dateCreation;
}
