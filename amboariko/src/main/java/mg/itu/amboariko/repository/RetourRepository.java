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
                SELECT r.*
                FROM retours r
                JOIN reparations rp ON r.id_reparation = rp.id_reparation
                JOIN reparations_ordi ro ON rp.id_reparation = ro.id_reparation
                JOIN ordinateurs o ON rp.id_ordinateur = o.id_ordinateur
                JOIN modeles m ON o.id_modele = m.id_modele
                JOIN categorie_ordi c ON m.id_categorie_ordi = c.id_categorie_ordi
                JOIN problemes p ON ro.id_probleme = p.id_probleme
                JOIN type_reparations tr ON ro.id_type_rep = tr.id_type_rep
                WHERE
                    (:categorieOrdi IS NULL OR c.val = :categorieOrdi) AND
                    (:probleme IS NULL OR p.val = :probleme) AND
                    (:typeReparation IS NULL OR tr.val = :typeReparation)
            """)
    List<Retour> findByCriteria(String categorieOrdi, String probleme, String typeReparation);
}
