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

public class GestionLocationServiceImpl implements GestionLocationService {

    @Override
    public Agence saveAgence(Agence agence) {
        return null;
    }

    @Override
    public Voiture saveVoiture(String marque, String modele, String matricule, double prixParJour, int nombrePortes, TypeCarburant typeCarburant, BoiteVitesse boiteVitesse, String agenceId) {
        return null;
    }

    @Override
    public Moto saveMoto(String marque, String modele, String matricule, double prixParJour, int cylindree, TypeMoto typeMoto, boolean casqueInclus, String agenceId) {
        return null;
    }

    @Override
    public List<Agence> listAgences() {
        return List.of();
    }

    @Override
    public Vehicule getVehicule(String vehiculeId) {
        return null;
    }

    @Override
    public Location louerVehicule(String vehiculeId, Date dateDebut, Date dateFin, double montant) {
        return null;
    }

    @Override
    public List<Vehicule> vehiculeList() {
        return List.of();
    }
}
