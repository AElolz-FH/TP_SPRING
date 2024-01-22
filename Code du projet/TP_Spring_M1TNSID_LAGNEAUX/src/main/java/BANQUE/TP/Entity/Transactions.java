/*
**************************
@author -Lagneaux Grégory-
**************************
 */

package BANQUE.TP.Entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "transactions")
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransaction;
    private double montant;
    private String typeTransaction;
    private String typeSource;
    private int idSource;
    @ManyToOne
    private Compte compte;
    private String dateTransaction;
}
