package net.mahboub.mahboubmarouaneexamjee.web;

import lombok.AllArgsConstructor;
import net.mahboub.mahboubmarouaneexamjee.dtos.LocationDTO;
import net.mahboub.mahboubmarouaneexamjee.dtos.MotoDTO;
import net.mahboub.mahboubmarouaneexamjee.dtos.VehiculeDTO;
import net.mahboub.mahboubmarouaneexamjee.dtos.VehiculeHistoryDTO;
import net.mahboub.mahboubmarouaneexamjee.dtos.VoitureDTO;
import net.mahboub.mahboubmarouaneexamjee.exceptions.AgenceNotFoundException;
import net.mahboub.mahboubmarouaneexamjee.exceptions.VehiculeNotFoundException;
import net.mahboub.mahboubmarouaneexamjee.services.GestionLocationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class VehiculeRestController {
    private GestionLocationService gestionLocationService;

    @GetMapping("/vehicules/{vehiculeId}")
    public VehiculeDTO getVehicule(@PathVariable String vehiculeId) throws VehiculeNotFoundException {
        return gestionLocationService.getVehicule(vehiculeId);
    }

    @GetMapping("/vehicules")
    public List<VehiculeDTO> listVehicules() {
        return gestionLocationService.vehiculeList();
    }

    @DeleteMapping("/vehicules/{vehiculeId}")
    public void deleteVehicule(@PathVariable String vehiculeId) {
        gestionLocationService.deleteVehicule(vehiculeId);
    }

    @PutMapping("/vehicules/{vehiculeId}/voiture")
    public VoitureDTO updateVoiture(@PathVariable String vehiculeId, @RequestBody VoitureDTO voitureDTO) throws VehiculeNotFoundException, AgenceNotFoundException {
        return gestionLocationService.updateVoiture(vehiculeId, voitureDTO);
    }

    @PutMapping("/vehicules/{vehiculeId}/moto")
    public MotoDTO updateMoto(@PathVariable String vehiculeId, @RequestBody MotoDTO motoDTO) throws VehiculeNotFoundException, AgenceNotFoundException {
        return gestionLocationService.updateMoto(vehiculeId, motoDTO);
    }

    @GetMapping("/vehicules/{vehiculeId}/locations")
    public List<LocationDTO> getHistory(@PathVariable String vehiculeId) {
        return gestionLocationService.locationHistory(vehiculeId);
    }

    @GetMapping("/vehicules/{vehiculeId}/pageLocations")
    public VehiculeHistoryDTO getVehiculeHistory(
            @PathVariable String vehiculeId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) throws VehiculeNotFoundException {
        return gestionLocationService.getVehiculeHistory(vehiculeId, page, size);
    }
}
