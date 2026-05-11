package net.mahboub.mahboubmarouaneexamjee.dtos;

import lombok.Data;

import java.util.List;

@Data
public class VehiculeHistoryDTO {
    private String vehiculeId;
    private double prixParJour;
    private int currentPage;
    private int totalPages;
    private int pageSize;
    private List<LocationDTO> locationDTOS;
}
