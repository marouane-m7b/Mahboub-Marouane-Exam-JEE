package net.mahboub.mahboubmarouaneexamjee.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mahboub.mahboubmarouaneexamjee.entities.Agence;
import net.mahboub.mahboubmarouaneexamjee.entities.Location;
import net.mahboub.mahboubmarouaneexamjee.entities.Moto;
import net.mahboub.mahboubmarouaneexamjee.entities.Vehicule;
import net.mahboub.mahboubmarouaneexamjee.entities.Voiture;
import net.mahboub.mahboubmarouaneexamjee.enums.BoiteVitesse;
import net.mahboub.mahboubmarouaneexamjee.enums.StatutVehicule;
import net.mahboub.mahboubmarouaneexamjee.enums.TypeCarburant;
import net.mahboub.mahboubmarouaneexamjee.enums.TypeMoto;
import net.mahboub.mahboubmarouaneexamjee.exceptions.AgenceNotFoundException;
import net.mahboub.mahboubmarouaneexamjee.exceptions.VehiculeIndisponibleException;
import net.mahboub.mahboubmarouaneexamjee.exceptions.VehiculeNotFoundException;
import net.mahboub.mahboubmarouaneexamjee.repositories.AgenceRepository;
import net.mahboub.mahboubmarouaneexamjee.repositories.LocationRepository;
import net.mahboub.mahboubmarouaneexamjee.repositories.VehiculeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class GestionLocationServiceImpl implements GestionLocationService {

    private AgenceRepository agenceRepository;
    private VehiculeRepository vehiculeRepository;
    private LocationRepository locationRepository;

    @Override
    public Agence saveAgence(Agence agence) {
        log.info("Saving new Agence");
        if (agence.getId() == null) {
            agence.setId(UUID.randomUUID().toString());
        }
        return agenceRepository.save(agence);
    }

    @Override
    public Voiture saveVoiture(String marque, String modele, String matricule, double prixParJour, int nombrePortes, TypeCarburant typeCarburant, BoiteVitesse boiteVitesse, String agenceId) throws AgenceNotFoundException {
        log.info("Saving new Voiture");
        Agence agence = agenceRepository.findById(agenceId).orElse(null);
        if (agence == null) throw new AgenceNotFoundException("Agence Not Found");

        Voiture voiture = new Voiture();
        voiture.setId(UUID.randomUUID().toString());
        voiture.setMarque(marque);
        voiture.setModele(modele);
        voiture.setMatricule(matricule);
        voiture.setPrixParJour(prixParJour);
        voiture.setDateMiseEnService(new Date());
        voiture.setStatut(StatutVehicule.DISPONIBLE);
        voiture.setAgence(agence);
        voiture.setNombrePortes(nombrePortes);
        voiture.setTypeCarburant(typeCarburant);
        voiture.setBoiteVitesse(boiteVitesse);

        return vehiculeRepository.save(voiture);
    }

    @Override
    public Moto saveMoto(String marque, String modele, String matricule, double prixParJour, int cylindree, TypeMoto typeMoto, boolean casqueInclus, String agenceId) throws AgenceNotFoundException {
        log.info("Saving new Moto");
        Agence agence = agenceRepository.findById(agenceId).orElse(null);
        if (agence == null) throw new AgenceNotFoundException("Agence Not Found");

        Moto moto = new Moto();
        moto.setId(UUID.randomUUID().toString());
        moto.setMarque(marque);
        moto.setModele(modele);
        moto.setMatricule(matricule);
        moto.setPrixParJour(prixParJour);
        moto.setDateMiseEnService(new Date());
        moto.setStatut(StatutVehicule.DISPONIBLE);
        moto.setAgence(agence);
        moto.setCylindree(cylindree);
        moto.setTypeMoto(typeMoto);
        moto.setCasqueInclus(casqueInclus);

        return vehiculeRepository.save(moto);
    }

    @Override
    public List<Agence> listAgences() {
        return agenceRepository.findAll();
    }

    @Override
    public Vehicule getVehicule(String vehiculeId) throws VehiculeNotFoundException {
        return vehiculeRepository.findById(vehiculeId).orElseThrow(() -> new VehiculeNotFoundException("Vehicule Not Found"));
    }

    @Override
    public Location louerVehicule(String vehiculeId, Date dateDebut, Date dateFin, double montant) throws VehiculeNotFoundException, VehiculeIndisponibleException {
        log.info("Creating new Location for Vehicule: {}", vehiculeId);
        Vehicule vehicule = getVehicule(vehiculeId);
        
        if (vehicule.getStatut() != StatutVehicule.DISPONIBLE) {
            throw new VehiculeIndisponibleException("Vehicule is not available for rent");
        }

        Location location = new Location();
        location.setDateDebut(dateDebut);
        location.setDateFin(dateFin);
        location.setMontant(montant);
        location.setVehicule(vehicule);

        vehicule.setStatut(StatutVehicule.LOUE);
        vehiculeRepository.save(vehicule);

        return locationRepository.save(location);
    }

    @Override
    public List<Vehicule> vehiculeList() {
        return vehiculeRepository.findAll();
    }
}
