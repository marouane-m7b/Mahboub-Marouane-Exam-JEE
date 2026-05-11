package net.mahboub.mahboubmarouaneexamjee.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mahboub.mahboubmarouaneexamjee.dtos.*;
import net.mahboub.mahboubmarouaneexamjee.entities.*;
import net.mahboub.mahboubmarouaneexamjee.enums.StatutVehicule;
import net.mahboub.mahboubmarouaneexamjee.exceptions.AgenceNotFoundException;
import net.mahboub.mahboubmarouaneexamjee.exceptions.VehiculeIndisponibleException;
import net.mahboub.mahboubmarouaneexamjee.exceptions.VehiculeNotFoundException;
import net.mahboub.mahboubmarouaneexamjee.mappers.VehiculeMapperImpl;
import net.mahboub.mahboubmarouaneexamjee.repositories.AgenceRepository;
import net.mahboub.mahboubmarouaneexamjee.repositories.LocationRepository;
import net.mahboub.mahboubmarouaneexamjee.repositories.VehiculeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class GestionLocationServiceImpl implements GestionLocationService {

    private AgenceRepository agenceRepository;
    private VehiculeRepository vehiculeRepository;
    private LocationRepository locationRepository;
    private VehiculeMapperImpl dtoMapper;

    @Override
    public AgenceDTO saveAgence(AgenceDTO agenceDTO) {
        log.info("Saving new Agence");
        Agence agence = dtoMapper.fromAgenceDTO(agenceDTO);
        if (agence.getId() == null) {
            agence.setId(UUID.randomUUID().toString());
        }
        Agence savedAgence = agenceRepository.save(agence);
        return dtoMapper.fromAgence(savedAgence);
    }

    @Override
    public VoitureDTO saveVoiture(double prixParJour, int nombrePortes, String marque, String modele, String matricule, String agenceId) throws AgenceNotFoundException {
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

        Voiture savedVoiture = vehiculeRepository.save(voiture);
        return dtoMapper.fromVoiture(savedVoiture);
    }

    @Override
    public MotoDTO saveMoto(double prixParJour, int cylindree, String marque, String modele, String matricule, String agenceId) throws AgenceNotFoundException {
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

        Moto savedMoto = vehiculeRepository.save(moto);
        return dtoMapper.fromMoto(savedMoto);
    }

    @Override
    public List<AgenceDTO> listAgences() {
        List<Agence> agences = agenceRepository.findAll();
        return agences.stream().map(agence -> dtoMapper.fromAgence(agence)).collect(Collectors.toList());
    }

    @Override
    public VehiculeDTO getVehicule(String vehiculeId) throws VehiculeNotFoundException {
        Vehicule vehicule = vehiculeRepository.findById(vehiculeId).orElseThrow(() -> new VehiculeNotFoundException("Vehicule Not Found"));
        if (vehicule instanceof Voiture) {
            return dtoMapper.fromVoiture((Voiture) vehicule);
        } else {
            return dtoMapper.fromMoto((Moto) vehicule);
        }
    }

    @Override
    public void louerVehicule(String vehiculeId, Date dateDebut, Date dateFin, double montant) throws VehiculeNotFoundException, VehiculeIndisponibleException {
        log.info("Creating new Location for Vehicule: {}", vehiculeId);
        Vehicule vehicule = vehiculeRepository.findById(vehiculeId).orElseThrow(() -> new VehiculeNotFoundException("Vehicule Not Found"));

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
        locationRepository.save(location);
    }

    @Override
    public List<VehiculeDTO> vehiculeList() {
        List<Vehicule> vehicules = vehiculeRepository.findAll();
        return vehicules.stream().map(vehicule -> {
            if (vehicule instanceof Voiture) return dtoMapper.fromVoiture((Voiture) vehicule);
            else return dtoMapper.fromMoto((Moto) vehicule);
        }).collect(Collectors.toList());
    }

    @Override
    public AgenceDTO getAgence(String agenceId) throws AgenceNotFoundException {
        Agence agence = agenceRepository.findById(agenceId).orElseThrow(() -> new AgenceNotFoundException("Agence Not Found"));
        return dtoMapper.fromAgence(agence);
    }

    @Override
    public AgenceDTO updateAgence(AgenceDTO agenceDTO) {
        log.info("Updating Agence");
        Agence agence = dtoMapper.fromAgenceDTO(agenceDTO);
        Agence savedAgence = agenceRepository.save(agence);
        return dtoMapper.fromAgence(savedAgence);
    }

    @Override
    public void deleteAgence(String agenceId) {
        agenceRepository.deleteById(agenceId);
    }

    @Override
    public List<LocationDTO> locationHistory(String vehiculeId) {
        List<Location> locations = locationRepository.findByVehiculeId(vehiculeId);
        return locations.stream().map(loc -> dtoMapper.fromLocation(loc)).collect(Collectors.toList());
    }

    @Override
    public VehiculeHistoryDTO getVehiculeHistory(String vehiculeId, int page, int size) throws VehiculeNotFoundException {
        Vehicule vehicule = vehiculeRepository.findById(vehiculeId).orElseThrow(() -> new VehiculeNotFoundException("Vehicule Not Found"));
        Page<Location> locations = locationRepository.findByVehiculeId(vehiculeId, PageRequest.of(page, size));
        VehiculeHistoryDTO vehiculeHistoryDTO = new VehiculeHistoryDTO();
        List<LocationDTO> locationDTOS = locations.getContent().stream().map(loc -> dtoMapper.fromLocation(loc)).collect(Collectors.toList());
        vehiculeHistoryDTO.setLocationDTOS(locationDTOS);
        vehiculeHistoryDTO.setVehiculeId(vehicule.getId());
        vehiculeHistoryDTO.setPrixParJour(vehicule.getPrixParJour());
        vehiculeHistoryDTO.setCurrentPage(page);
        vehiculeHistoryDTO.setPageSize(size);
        vehiculeHistoryDTO.setTotalPages(locations.getTotalPages());
        return vehiculeHistoryDTO;
    }
}
