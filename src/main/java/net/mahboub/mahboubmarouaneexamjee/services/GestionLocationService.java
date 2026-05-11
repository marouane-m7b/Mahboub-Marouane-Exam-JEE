package net.mahboub.mahboubmarouaneexamjee.services;

import net.mahboub.mahboubmarouaneexamjee.dtos.*;
import net.mahboub.mahboubmarouaneexamjee.exceptions.AgenceNotFoundException;
import net.mahboub.mahboubmarouaneexamjee.exceptions.VehiculeIndisponibleException;
import net.mahboub.mahboubmarouaneexamjee.exceptions.VehiculeNotFoundException;

import java.util.Date;
import java.util.List;

public interface GestionLocationService {
    AgenceDTO saveAgence(AgenceDTO agenceDTO);
    VoitureDTO saveVoiture(double prixParJour, int nombrePortes, String marque, String modele, String matricule, String agenceId) throws AgenceNotFoundException;
    MotoDTO saveMoto(double prixParJour, int cylindree, String marque, String modele, String matricule, String agenceId) throws AgenceNotFoundException;
    List<AgenceDTO> listAgences();
    VehiculeDTO getVehicule(String vehiculeId) throws VehiculeNotFoundException;
    void louerVehicule(String vehiculeId, Date dateDebut, Date dateFin, double montant) throws VehiculeNotFoundException, VehiculeIndisponibleException;
    List<VehiculeDTO> vehiculeList();

    AgenceDTO getAgence(String agenceId) throws AgenceNotFoundException;
    AgenceDTO updateAgence(AgenceDTO agenceDTO);
    void deleteAgence(String agenceId);
    List<LocationDTO> locationHistory(String vehiculeId);
    VehiculeHistoryDTO getVehiculeHistory(String vehiculeId, int page, int size) throws VehiculeNotFoundException;
}
