package mg.itu.amboariko.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mg.itu.amboariko.model.ComposantsUtilises;

@Repository
public interface ComposantUtiliseRepository  extends CrudRepository<ComposantsUtilises, Long>{
    
}
