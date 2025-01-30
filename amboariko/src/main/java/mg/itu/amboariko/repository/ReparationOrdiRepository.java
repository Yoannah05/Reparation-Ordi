package mg.itu.amboariko.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import mg.itu.amboariko.model.ReparationOrdi;

@Repository
public interface ReparationOrdiRepository extends CrudRepository<ReparationOrdi, Long>{

}
