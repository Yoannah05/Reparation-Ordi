package mg.itu.amboariko.repository;

import mg.itu.amboariko.model.Reparation;

import java.time.LocalDate;
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

    @Query("""
                SELECT rp.*
                FROM reparations rp
                LEFT JOIN retours rt ON rp.id_reparation = rt.id_reparation
                WHERE rt.id_retours IS NULL
            """)
    List<Reparation> findReparationsNonRetournees();

    @Query("SELECT * FROM reparations WHERE id_ordinateur = :idOrdinateur")
    List<Reparation> findByOrdinateurId(Long idOrdinateur);

    @Query("SELECT * FROM reparations WHERE date_debut BETWEEN :dateDebut AND :dateFin")
    List<Reparation> findByDateDebutBetween(LocalDate dateDebut, LocalDate dateFin);

    @Query("SELECT * FROM reparations WHERE id_reparation IN (SELECT id_reparation FROM reparations_ordi WHERE id_type_rep = :idTypeReparation)")
    List<Reparation> findByTypeReparation(Long idTypeReparation);

}
