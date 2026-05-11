package net.mahboub.mahboubmarouaneexamjee.dtos;

import lombok.Data;
import net.mahboub.mahboubmarouaneexamjee.enums.StatutVehicule;
import net.mahboub.mahboubmarouaneexamjee.enums.TypeMoto;

import java.util.Date;

@Data
public class MotoDTO extends VehiculeDTO {
    private String id;
    private String marque;
    private String modele;
    private String matricule;
    private double prixParJour;
    private Date dateMiseEnService;
    private StatutVehicule statut;
    private AgenceDTO agenceDTO;
    private int cylindree;
    private TypeMoto typeMoto;
    private boolean casqueInclus;
}
