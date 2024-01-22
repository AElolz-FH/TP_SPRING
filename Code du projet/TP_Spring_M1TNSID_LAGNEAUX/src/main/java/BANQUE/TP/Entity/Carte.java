/*
**************************
@author -Lagneaux Grégory-
**************************
 */

package BANQUE.TP.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Carte {
    @Id
    private Long numeroCarte;
    private int moisExpiration;
    private int anneeExpiration;
    private String password;
    @OneToMany
    private List<Client> listeTitulaires;
    @ManyToOne
    private Compte compte;


}




