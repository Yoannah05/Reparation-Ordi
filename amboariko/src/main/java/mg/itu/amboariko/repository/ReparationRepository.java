package mg.itu.amboariko.repository;

import mg.itu.amboariko.model.Reparation;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
public interface ReparationRepository extends CrudRepository<Reparation, Long> {
    @Query("SELECT r.id_reparation, r.id_ordinateur, r.date_debut, r.date_fin, r.prix_reparation, r.statut " +
            "FROM Reparations r " +
            "JOIN Reparations_ordi ro ON r.id_reparation = ro.id_reparation " +
            "JOIN Problemes p ON ro.id_probleme = p.id_probleme " +
            "WHERE p.id_probleme = :idProbleme")
    List<Reparation> findByProbleme(Long idProbleme);

}
