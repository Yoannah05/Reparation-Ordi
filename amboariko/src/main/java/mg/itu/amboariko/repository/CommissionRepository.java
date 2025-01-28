package mg.itu.amboariko.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import mg.itu.amboariko.model.Commission;

@Repository
public interface CommissionRepository extends CrudRepository<Commission, Long> {
    
}