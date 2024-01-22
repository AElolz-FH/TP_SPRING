/*
**************************
@author -Lagneaux Grégory-
**************************
 */

package BANQUE.TP.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Client
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String prenom;
    private String nom;
    private Date dateNaissance;
    private String telephone;
    private String adressePostale;
    private String adresseModification;
    private Instant dateCreation;
    @ManyToMany
    private List<Compte> comptes;
    @OneToMany
    private List<Virement> virements;
}


