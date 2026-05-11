package net.mahboub.mahboubmarouaneexamjee.services;

import net.mahboub.mahboubmarouaneexamjee.entities.Agence;
import net.mahboub.mahboubmarouaneexamjee.entities.Location;
import net.mahboub.mahboubmarouaneexamjee.entities.Moto;
import net.mahboub.mahboubmarouaneexamjee.entities.Vehicule;
import net.mahboub.mahboubmarouaneexamjee.entities.Voiture;
import net.mahboub.mahboubmarouaneexamjee.enums.BoiteVitesse;
import net.mahboub.mahboubmarouaneexamjee.enums.TypeCarburant;
import net.mahboub.mahboubmarouaneexamjee.enums.TypeMoto;
import net.mahboub.mahboubmarouaneexamjee.exceptions.AgenceNotFoundException;
import net.mahboub.mahboubmarouaneexamjee.exceptions.VehiculeIndisponibleException;
import net.mahboub.mahboubmarouaneexamjee.exceptions.VehiculeNotFoundException;

import java.util.Date;
import java.util.List;

public interface GestionLocationService {
    Agence saveAgence(Agence agence);
    Voiture saveVoiture(String marque, String modele, String matricule, double prixParJour, int nombrePortes, TypeCarburant typeCarburant, BoiteVitesse boiteVitesse, String agenceId) throws AgenceNotFoundException;
    Moto saveMoto(String marque, String modele, String matricule, double prixParJour, int cylindree, TypeMoto typeMoto, boolean casqueInclus, String agenceId) throws AgenceNotFoundException;
    List<Agence> listAgences();
    Vehicule getVehicule(String vehiculeId) throws VehiculeNotFoundException;
    Location louerVehicule(String vehiculeId, Date dateDebut, Date dateFin, double montant) throws VehiculeNotFoundException, VehiculeIndisponibleException;
    List<Vehicule> vehiculeList();
}
