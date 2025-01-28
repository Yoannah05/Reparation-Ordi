package mg.itu.amboariko.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import mg.itu.amboariko.model.Sexe;

@Repository
public interface SexeRepository extends CrudRepository<Sexe, Long> {
    
}