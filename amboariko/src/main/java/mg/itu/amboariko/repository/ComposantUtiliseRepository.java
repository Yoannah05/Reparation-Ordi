package mg.itu.amboariko.repository;

import mg.itu.amboariko.model.VModelComposantUtilise;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mg.itu.amboariko.model.ComposantsUtilises;


import java.util.List;

@Repository
public interface ComposantUtiliseRepository extends CrudRepository<ComposantsUtilises, Long> {

    // Méthode pour extraire toutes les informations de la vue
    @Query("SELECT * FROM v_composants_utilises WHERE id_reparation = :idReparation ")
    List<VModelComposantUtilise> findByIdReparation(Long idReparation);

}
