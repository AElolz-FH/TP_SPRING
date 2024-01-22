/*
**************************
@author -Lagneaux Grégory-
**************************
 */

package BANQUE.TP.Controller;

import BANQUE.TP.Services.VirementService;
import BANQUE.TP.odt.Reponses.creerVirementReponse;
import BANQUE.TP.odt.Request.creerVirementPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/virement")
public class VirementController {
    @Autowired
    VirementService virementService;

    @PostMapping("/virements")
    public ResponseEntity<creerVirementReponse> creerVirement(@RequestBody creerVirementPayload payload)
    {
        try {
            creerVirementReponse virementReponse = this.virementService.creerVirement(payload);
            return ResponseEntity.ok().body(virementReponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new creerVirementReponse("Une erreur s'est produite lors de la création du virement."));
        }
    }


}
