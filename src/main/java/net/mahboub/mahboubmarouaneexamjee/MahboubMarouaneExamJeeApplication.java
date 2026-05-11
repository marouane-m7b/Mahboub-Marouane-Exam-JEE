package net.mahboub.mahboubmarouaneexamjee;

import net.mahboub.mahboubmarouaneexamjee.entities.Agence;
import net.mahboub.mahboubmarouaneexamjee.entities.Location;
import net.mahboub.mahboubmarouaneexamjee.entities.Moto;
import net.mahboub.mahboubmarouaneexamjee.entities.Voiture;
import net.mahboub.mahboubmarouaneexamjee.enums.BoiteVitesse;
import net.mahboub.mahboubmarouaneexamjee.enums.StatutVehicule;
import net.mahboub.mahboubmarouaneexamjee.enums.TypeCarburant;
import net.mahboub.mahboubmarouaneexamjee.enums.TypeMoto;
import net.mahboub.mahboubmarouaneexamjee.repositories.AgenceRepository;
import net.mahboub.mahboubmarouaneexamjee.repositories.LocationRepository;
import net.mahboub.mahboubmarouaneexamjee.repositories.VehiculeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class MahboubMarouaneExamJeeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MahboubMarouaneExamJeeApplication.class, args);
    }

    // @Bean
    CommandLineRunner start(AgenceRepository agenceRepository,
                            VehiculeRepository vehiculeRepository,
                            LocationRepository locationRepository) {
        return args -> {
            Stream.of("Agence Paris", "Agence Lyon", "Agence Marseille").forEach(nom -> {
                Agence agence = new Agence();
                agence.setId(UUID.randomUUID().toString());
                agence.setNom(nom);
                agence.setAdresse("Adresse " + nom);
                agence.setVille(nom.replace("Agence ", ""));
                agence.setTelephone("0102030405");
                agenceRepository.save(agence);
            });

            agenceRepository.findAll().forEach(agence -> {
                Voiture voiture = new Voiture();
                voiture.setId(UUID.randomUUID().toString());
                voiture.setMarque("Renault");
                voiture.setModele("Clio");
                voiture.setMatricule("AB-123-CD");
                voiture.setPrixParJour(50.0);
                voiture.setDateMiseEnService(new Date());
                voiture.setStatut(StatutVehicule.DISPONIBLE);
                voiture.setAgence(agence);
                voiture.setNombrePortes(5);
                voiture.setTypeCarburant(TypeCarburant.ESSENCE);
                voiture.setBoiteVitesse(BoiteVitesse.MANUELLE);
                vehiculeRepository.save(voiture);

                Moto moto = new Moto();
                moto.setId(UUID.randomUUID().toString());
                moto.setMarque("Yamaha");
                moto.setModele("MT-07");
                moto.setMatricule("12-ABC-34");
                moto.setPrixParJour(30.0);
                moto.setDateMiseEnService(new Date());
                moto.setStatut(StatutVehicule.DISPONIBLE);
                moto.setAgence(agence);
                moto.setCylindree(689);
                moto.setTypeMoto(TypeMoto.ROADSTER);
                moto.setCasqueInclus(true);
                vehiculeRepository.save(moto);
            });

            vehiculeRepository.findAll().forEach(vehicule -> {
                for (int i = 0; i < 5; i++) {
                    Location location = new Location();
                    location.setDateDebut(new Date());
                    location.setDateFin(new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 3))); // +3 jours
                    location.setMontant(vehicule.getPrixParJour() * 3);
                    location.setVehicule(vehicule);
                    locationRepository.save(location);
                }
            });
        };
    }

    @Bean
    CommandLineRunner commandLineRunner(net.mahboub.mahboubmarouaneexamjee.services.VehiculeService vehiculeService) {
        return args -> {
            vehiculeService.consulter();
        };
    }
}
