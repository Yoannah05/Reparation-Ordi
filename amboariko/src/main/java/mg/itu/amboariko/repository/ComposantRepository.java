package mg.itu.amboariko.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import mg.itu.amboariko.model.Composant;

@Repository
public interface ComposantRepository extends CrudRepository<Composant, Long>{

}
