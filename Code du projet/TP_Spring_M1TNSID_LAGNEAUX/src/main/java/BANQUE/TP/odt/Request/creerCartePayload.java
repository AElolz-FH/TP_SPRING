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

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class creerCartePayload {
    private List<String> titulaireCarte;
    private int code;
}
