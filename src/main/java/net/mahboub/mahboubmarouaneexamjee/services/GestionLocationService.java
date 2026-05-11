package net.mahboub.mahboubmarouaneexamjee.services;

import net.mahboub.mahboubmarouaneexamjee.entities.Agence;
import net.mahboub.mahboubmarouaneexamjee.entities.Location;
import net.mahboub.mahboubmarouaneexamjee.entities.Moto;
import net.mahboub.mahboubmarouaneexamjee.entities.Vehicule;
import net.mahboub.mahboubmarouaneexamjee.entities.Voiture;
import net.mahboub.mahboubmarouaneexamjee.enums.BoiteVitesse;
import net.mahboub.mahboubmarouaneexamjee.enums.TypeCarburant;
import net.mahboub.mahboubmarouaneexamjee.enums.TypeMoto;

import java.util.Date;
import java.util.List;

public interface GestionLocationService {
    Agence saveAgence(Agence agence);
    Voiture saveVoiture(String marque, String modele, String matricule, double prixParJour, int nombrePortes, TypeCarburant typeCarburant, BoiteVitesse boiteVitesse, String agenceId);
    Moto saveMoto(String marque, String modele, String matricule, double prixParJour, int cylindree, TypeMoto typeMoto, boolean casqueInclus, String agenceId);
    List<Agence> listAgences();
    Vehicule getVehicule(String vehiculeId);
    Location louerVehicule(String vehiculeId, Date dateDebut, Date dateFin, double montant);
    List<Vehicule> vehiculeList();
}
