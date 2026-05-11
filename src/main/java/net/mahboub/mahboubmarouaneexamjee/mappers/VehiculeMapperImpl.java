package net.mahboub.mahboubmarouaneexamjee.mappers;

import net.mahboub.mahboubmarouaneexamjee.dtos.*;
import net.mahboub.mahboubmarouaneexamjee.entities.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class VehiculeMapperImpl {
    public AgenceDTO fromAgence(Agence agence){
        AgenceDTO agenceDTO = new AgenceDTO();
        BeanUtils.copyProperties(agence, agenceDTO);
        return agenceDTO;
    }
    public Agence fromAgenceDTO(AgenceDTO agenceDTO){
        Agence agence = new Agence();
        BeanUtils.copyProperties(agenceDTO, agence);
        return agence;
    }
    public VoitureDTO fromVoiture(Voiture voiture){
        VoitureDTO voitureDTO = new VoitureDTO();
        BeanUtils.copyProperties(voiture, voitureDTO);
        voitureDTO.setAgenceDTO(fromAgence(voiture.getAgence()));
        voitureDTO.setType(voiture.getClass().getSimpleName());
        return voitureDTO;
    }
    public Voiture fromVoitureDTO(VoitureDTO voitureDTO){
        Voiture voiture = new Voiture();
        BeanUtils.copyProperties(voitureDTO, voiture);
        voiture.setAgence(fromAgenceDTO(voitureDTO.getAgenceDTO()));
        return voiture;
    }
    public MotoDTO fromMoto(Moto moto){
        MotoDTO motoDTO = new MotoDTO();
        BeanUtils.copyProperties(moto, motoDTO);
        motoDTO.setAgenceDTO(fromAgence(moto.getAgence()));
        motoDTO.setType(moto.getClass().getSimpleName());
        return motoDTO;
    }
    public Moto fromMotoDTO(MotoDTO motoDTO){
        Moto moto = new Moto();
        BeanUtils.copyProperties(motoDTO, moto);
        moto.setAgence(fromAgenceDTO(motoDTO.getAgenceDTO()));
        return moto;
    }
    public LocationDTO fromLocation(Location location){
        LocationDTO locationDTO = new LocationDTO();
        BeanUtils.copyProperties(location, locationDTO);
        return locationDTO;
    }
    public Location fromLocationDTO(LocationDTO locationDTO){
        Location location = new Location();
        BeanUtils.copyProperties(locationDTO, location);
        return location;
    }
}
