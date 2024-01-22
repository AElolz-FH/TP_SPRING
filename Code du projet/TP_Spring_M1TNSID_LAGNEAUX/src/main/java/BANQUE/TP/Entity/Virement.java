/*
**************************
@author -Lagneaux Grégory-
**************************
 */

package BANQUE.TP.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Virement {
    @Id
    private int id;
    private String ibanCompteEmetteur;
    private String ibanBeneficiaire;
    private int montant;
    private String libelleVirement;
    private Instant dateExecution;
    @ManyToOne
    private Client client;
}
