package net.mahboub.mahboubmarouaneexamjee.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mahboub.mahboubmarouaneexamjee.dtos.AgenceDTO;
import net.mahboub.mahboubmarouaneexamjee.exceptions.AgenceNotFoundException;
import net.mahboub.mahboubmarouaneexamjee.services.GestionLocationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class AgenceRestController {
    private GestionLocationService gestionLocationService;

    @GetMapping("/agences")
    public List<AgenceDTO> agences() {
        return gestionLocationService.listAgences();
    }

    @GetMapping("/agences/{id}")
    public AgenceDTO getAgence(@PathVariable(name = "id") String agenceId) throws AgenceNotFoundException {
        return gestionLocationService.getAgence(agenceId);
    }

    @PostMapping("/agences")
    public AgenceDTO saveAgence(@RequestBody AgenceDTO agenceDTO) {
        return gestionLocationService.saveAgence(agenceDTO);
    }

    @PutMapping("/agences/{agenceId}")
    public AgenceDTO updateAgence(@PathVariable String agenceId, @RequestBody AgenceDTO agenceDTO) {
        agenceDTO.setId(agenceId);
        return gestionLocationService.updateAgence(agenceDTO);
    }

    @DeleteMapping("/agences/{agenceId}")
    public void deleteAgence(@PathVariable String agenceId) {
        gestionLocationService.deleteAgence(agenceId);
    }
}
