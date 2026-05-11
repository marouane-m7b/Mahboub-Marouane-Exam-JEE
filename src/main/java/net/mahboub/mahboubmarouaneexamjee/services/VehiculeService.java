package net.mahboub.mahboubmarouaneexamjee.services;

import net.mahboub.mahboubmarouaneexamjee.entities.Moto;
import net.mahboub.mahboubmarouaneexamjee.entities.Vehicule;
import net.mahboub.mahboubmarouaneexamjee.entities.Voiture;
import net.mahboub.mahboubmarouaneexamjee.repositories.VehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VehiculeService {
    
    @Autowired
    private VehiculeRepository vehiculeRepository;
    
    public void consulter() {
        Vehicule vehicule = vehiculeRepository.findAll().stream().findFirst().orElse(null);
        
        if (vehicule != null) {
            System.out.println("*****************************");
            System.out.println("ID: " + vehicule.getId());
            System.out.println("Marque/Modele: " + vehicule.getMarque() + " " + vehicule.getModele());
            System.out.println("Prix par jour: " + vehicule.getPrixParJour());
            System.out.println("Statut: " + vehicule.getStatut());
            System.out.println("Mise en service: " + vehicule.getDateMiseEnService());
            System.out.println("Agence: " + vehicule.getAgence().getNom());
            System.out.println("Type: " + vehicule.getClass().getSimpleName());
            
            if (vehicule instanceof Voiture) {
                System.out.println("Type Carburant=>" + ((Voiture) vehicule).getTypeCarburant());
                System.out.println("Boite Vitesse=>" + ((Voiture) vehicule).getBoiteVitesse());
            } else if (vehicule instanceof Moto) {
                System.out.println("Type Moto=>" + ((Moto) vehicule).getTypeMoto());
                System.out.println("Cylindrée=>" + ((Moto) vehicule).getCylindree());
            }
            
            System.out.println("--- Locations ---");
            vehicule.getLocations().forEach(loc -> {
                System.out.println("Du " + loc.getDateDebut() + " au " + loc.getDateFin() + " \tMontant: " + loc.getMontant());
            });
            System.out.println("*****************************");
        } else {
            System.out.println("Aucun véhicule trouvé en base de données.");
        }
    }
}
