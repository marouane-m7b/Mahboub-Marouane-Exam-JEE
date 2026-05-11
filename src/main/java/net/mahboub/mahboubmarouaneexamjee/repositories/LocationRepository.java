package net.mahboub.mahboubmarouaneexamjee.repositories;

import net.mahboub.mahboubmarouaneexamjee.entities.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByVehiculeId(String vehiculeId);
    Page<Location> findByVehiculeIdOrderByDateDebutDesc(String vehiculeId, Pageable pageable);
}
