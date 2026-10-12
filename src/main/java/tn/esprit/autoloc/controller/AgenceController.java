package tn.esprit.autoloc.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.service.IAgenceService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/agences")
public class AgenceController {
    private final IAgenceService agenceService;

    // http://localhost:8080/agences/afficherToutesAgences
    @GetMapping("/afficherToutesAgences")
    public List<Agence> listAgences() {
        return agenceService.afficherAllAgences();
    }
    // http://localhost:8080/agences/ajouterAgence
    @PostMapping("/ajouterAgence")
    public Agence ajouterAgence(@RequestBody Agence agence) {
        return agenceService.ajouterAgence(agence);
    }
    // http://localhost:8080/agences/modifierAgence
    @PutMapping
    public Agence modifierAgence(@RequestBody Agence agence) {
        return agenceService.modifierAgence(agence);
    }
    // http://localhost:8080/agences/afficherAgence/{id}
    @GetMapping("/afficherAgence/{id}")
    public Agence afficherAgence(@PathVariable Long id) {
        return agenceService.afficherAgenceById(id);
    }

    // http://localhost:8080/agences/supprimerAgence/{id}
    @DeleteMapping("/supprimerAgence/{id}")
    public void supprimerAgence(@PathVariable Long id) {
       agenceService.supprimerAgence(id);
    }
}

