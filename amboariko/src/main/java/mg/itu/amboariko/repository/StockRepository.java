package mg.itu.amboariko.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import mg.itu.amboariko.model.Stock;

@Repository
public interface StockRepository extends CrudRepository<Stock, Long>{
    
}
