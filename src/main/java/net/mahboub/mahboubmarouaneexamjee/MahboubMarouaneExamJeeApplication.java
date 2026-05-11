package net.mahboub.mahboubmarouaneexamjee;

import net.mahboub.mahboubmarouaneexamjee.dtos.*;
import net.mahboub.mahboubmarouaneexamjee.exceptions.AgenceNotFoundException;
import net.mahboub.mahboubmarouaneexamjee.exceptions.VehiculeIndisponibleException;
import net.mahboub.mahboubmarouaneexamjee.exceptions.VehiculeNotFoundException;
import net.mahboub.mahboubmarouaneexamjee.services.GestionLocationService;
import net.mahboub.mahboubmarouaneexamjee.services.VehiculeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class MahboubMarouaneExamJeeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MahboubMarouaneExamJeeApplication.class, args);
    }

    // @Bean
    CommandLineRunner start(GestionLocationService gestionLocationService) {
        return args -> {
            Stream.of("Agence Paris", "Agence Lyon", "Agence Marseille").forEach(nom -> {
                AgenceDTO agenceDTO = new AgenceDTO();
                agenceDTO.setNom(nom);
                agenceDTO.setAdresse("Adresse " + nom);
                agenceDTO.setVille(nom.replace("Agence ", ""));
                agenceDTO.setTelephone("0102030405");
                gestionLocationService.saveAgence(agenceDTO);
            });

            gestionLocationService.listAgences().forEach(agence -> {
                try {
                    for (int i = 0; i < 2; i++) {
                        gestionLocationService.saveVoiture(50.0, 5, "Renault", "Clio", "AB-123-CD-" + i, agence.getId());
                        gestionLocationService.saveMoto(30.0, 689, "Yamaha", "MT-07", "12-ABC-34-" + i, agence.getId());
                    }
                } catch (AgenceNotFoundException e) {
                    e.printStackTrace();
                }
            });

            List<VehiculeDTO> vehicules = gestionLocationService.vehiculeList();
            for (VehiculeDTO vehiculeDTO : vehicules) {
                String vehiculeId = (vehiculeDTO instanceof VoitureDTO) ? ((VoitureDTO) vehiculeDTO).getId() : ((MotoDTO) vehiculeDTO).getId();
                try {
                    for (int i = 0; i < 5; i++) {
                        gestionLocationService.louerVehicule(vehiculeId, new Date(), new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 3)), 150.0);
                    }
                } catch (VehiculeNotFoundException | VehiculeIndisponibleException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    // @Bean
    CommandLineRunner commandLineRunner(VehiculeService vehiculeService) {
        return args -> {
            vehiculeService.consulter();
        };
    }
}
