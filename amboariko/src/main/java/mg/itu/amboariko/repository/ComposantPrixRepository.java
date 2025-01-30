package mg.itu.amboariko.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import mg.itu.amboariko.model.ComposantPrix;

@Repository
public interface ComposantPrixRepository extends CrudRepository<ComposantPrix, Long>{

    

}
