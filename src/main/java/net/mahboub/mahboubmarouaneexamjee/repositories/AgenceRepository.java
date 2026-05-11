package net.mahboub.mahboubmarouaneexamjee.repositories;

import net.mahboub.mahboubmarouaneexamjee.entities.Agence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AgenceRepository extends JpaRepository<Agence, String> {
    @Query("select a from Agence a where a.nom like :kw")
    List<Agence> searchAgence(@Param("kw") String keyword);
}
