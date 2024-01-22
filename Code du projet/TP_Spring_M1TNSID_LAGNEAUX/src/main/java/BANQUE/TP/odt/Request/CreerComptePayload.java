/*
**************************
@author -Lagneaux Grégory-
**************************
 */

package BANQUE.TP.odt.Request;

import BANQUE.TP.Entity.Client;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
public class CreerComptePayload {
    private String intituleCompte;
    private String typeCompte;
    private List<Integer> titulairesCompte;
    @JsonCreator
    public CreerComptePayload(@JsonProperty("intituleCompte")String intituleCompte,
                              @JsonProperty("typeCompte") String typeCompte,
                              @JsonProperty("titulairesCompte") List<Integer> titulairesCompte){
        this.intituleCompte = intituleCompte;
        this.typeCompte = typeCompte;
        this.titulairesCompte = titulairesCompte;
    }
}


