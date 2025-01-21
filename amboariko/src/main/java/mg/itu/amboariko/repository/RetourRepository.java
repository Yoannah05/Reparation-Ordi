package mg.itu.amboariko.repository;

import mg.itu.amboariko.model.Retour;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RetourRepository extends CrudRepository<Retour, Long> {

    @Query("SELECT * FROM retours WHERE id_reparation = :idReparation")
    List<Retour> findByReparationId(Long idReparation);

    /**
     * Requête pour rechercher les retours en fonction de plusieurs critères.
     * 
     * @param categorieOrdi  : critère pour la catégorie de l'ordinateur
     * @param probleme       : critère pour le problème
     * @param typeReparation : critère pour le type de réparation
     * @return une liste de retours correspondant aux critères
     */
    @Query("""
                SELECT id_retours, date_retour, id_reparation
                FROM v_retours_criteria
                WHERE
                    (:categorieOrdi IS NULL OR categorie = :categorieOrdi) AND
                    (:probleme IS NULL OR probleme = :probleme) AND
                    (:typeReparation IS NULL OR typerep = :typeReparation)
            """)
    List<Retour> findByCriteria(Long categorieOrdi, Long probleme, Long typeReparation);

}
