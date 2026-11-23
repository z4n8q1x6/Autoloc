package tn.esprit.autoloc.service;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.EquipementRepository;
import tn.esprit.autoloc.repository.VehiculeRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor

public class IVehiculeServiceImp implements IVehiculeService {

    private final VehiculeRepository vehiculeRepository;
    private final IEquipementService equipementService;

    @Override
    public Vehicule getVehiculeById(Long id) {
        return vehiculeRepository.findById(id).orElse(null);
    }

    @Override
    public Vehicule addVehicule(Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule updateVehicule(Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public void deleteVehicule(Long id) {
        vehiculeRepository.deleteById(id);
    }

    @Override
    public List<Vehicule> getAllVehicule() {
        return vehiculeRepository.findAll();
    }

    @Override
    public Vehicule affecterEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = getVehiculeById(idVehicule);
        if (vehicule == null) {
            return null;
        }
        Equipement equipement = equipementService.getEquipement(idEquipement);
        if (equipement == null) {
            return null;
        }
        vehicule.getEquipements().add(equipement);
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule desaffecterEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = getVehiculeById(idVehicule);
        if (vehicule == null) {
            return null;
        }
        Equipement equipement = equipementService.getEquipement(idEquipement);
        if (equipement == null) {
            return null;
        }
        vehicule.getEquipements().removeIf(
                item -> idEquipement.equals(item.getIdEquipement())
        );
        return vehiculeRepository.save(vehicule);
    }

    @Override
    @Scheduled(initialDelay = 5, fixedRate = 60, timeUnit = TimeUnit.SECONDS)
    public void actualiserEtatVehicules() {
        List<Vehicule> vehicules = vehiculeRepository.findAll();
        Integer n = vehicules.size();
        System.out.println("------------------------------------------");
        System.out.println("Le nombre total de vehicules = " + n);
        System.out.println("Heure et date = " + LocalDateTime.now());
        System.out.println("------------------------------------------");
    }


    @Override
    @Scheduled(cron = "0 0 9 * * 1")
    public void genererRapportVehicules() {
        int n = vehiculeRepository.findAll().size();
        String dateGeneration = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'à' HH:mm:ss"));

        System.out.printf("""
                ===== Rapport hebdomadaire des véhicules =====
                Véhicules enregistrés : %d
                Rapport généré le      : %s
                ==============================================
                """, n, dateGeneration);
    }
}
