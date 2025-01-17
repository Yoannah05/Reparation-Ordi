package mg.itu.amboariko.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import mg.itu.amboariko.model.Paiement;

@Repository
public interface PaiementRepository extends CrudRepository<Paiement, Long>{
    
}
