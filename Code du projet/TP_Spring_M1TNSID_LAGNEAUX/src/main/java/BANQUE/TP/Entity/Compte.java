/*
**************************
@author -Lagneaux Grégory-
**************************
 */

package BANQUE.TP.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Compte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCompte;
    @Column(name = "iban")
    private String iban;
    private Double solde;
    private String intituleCompte;
    private String typeCompte;
    @ManyToMany
    private List<Client> titulairesCompte;
    @OneToMany
    private List<Transactions> transactionsCompte;
    @OneToMany(mappedBy = "compte")
    private List<Carte> cartes;
    private int cleRib;
}
