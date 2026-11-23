package tn.esprit.autoloc.service;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.AgenceRepository;
import tn.esprit.autoloc.repository.VehiculeRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
@AllArgsConstructor
public class IAgenceServiceImp implements IAgenceService {
    private final AgenceRepository agenceRepository;
    private final VehiculeRepository vehiculeRepository;

    @Override
    public Agence ajouterAgence(Agence agence) {
        return agenceRepository.save(agence);
    }

    @Override
    public Agence modifierAgence(Agence agence) {
        return agenceRepository.save(agence);
    }

    @Override
    public Agence afficherAgenceById(Long id) {
        return agenceRepository.findById(id).orElse(null);
    }

    @Override
    public List<Agence> afficherAllAgences() {
        return agenceRepository.findAll();
    }

    @Override
    public void supprimerAgence(Long id) {
        agenceRepository.deleteById(id);
    }

    @Override
    public Agence ajouterAgenceEtAffecterVehicules(Agence agence) {
        Agence ret = agenceRepository.save(agence);
        for (Vehicule vehicule : agence.getVehicules()) {
            vehicule.setAgence(agence);
        }
        vehiculeRepository.saveAll(agence.getVehicules());
        return ret;
    }


    @Override
    @Scheduled(fixedDelay = 2, timeUnit = TimeUnit.MINUTES)
    public void suivreNombreAgences() {
        Integer n = agenceRepository.findAll().size();
        System.out.println("------------------------------------------");
        System.out.println("Le nombre total de agences = " + n);
        System.out.println("Heure et date = " + LocalDateTime.now());
        System.out.println("------------------------------------------");
    }
}
