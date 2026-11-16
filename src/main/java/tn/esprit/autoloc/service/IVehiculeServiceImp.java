package tn.esprit.autoloc.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.EquipementRepository;
import tn.esprit.autoloc.repository.VehiculeRepository;

import java.util.List;
import java.util.Optional;

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
}
