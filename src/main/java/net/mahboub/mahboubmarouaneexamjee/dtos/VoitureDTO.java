package net.mahboub.mahboubmarouaneexamjee.dtos;

import lombok.Data;
import net.mahboub.mahboubmarouaneexamjee.enums.BoiteVitesse;
import net.mahboub.mahboubmarouaneexamjee.enums.StatutVehicule;
import net.mahboub.mahboubmarouaneexamjee.enums.TypeCarburant;

import java.util.Date;

@Data
public class VoitureDTO extends VehiculeDTO {
    private String id;
    private String marque;
    private String modele;
    private String matricule;
    private double prixParJour;
    private Date dateMiseEnService;
    private StatutVehicule statut;
    private AgenceDTO agenceDTO;
    private int nombrePortes;
    private TypeCarburant typeCarburant;
    private BoiteVitesse boiteVitesse;
}
