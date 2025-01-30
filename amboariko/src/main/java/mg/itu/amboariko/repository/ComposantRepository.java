package mg.itu.amboariko.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import mg.itu.amboariko.model.Composant;

@Repository
public interface ComposantRepository extends CrudRepository<Composant, Long>{

    @Query("SELECT c.* FROM composants c JOIN composants_utilises cu ON c.id_composant = cu.id_composant WHERE cu.id_rep_ordi = :idReparation")
    List<Composant> findByReparationId(Long idReparation);

}
