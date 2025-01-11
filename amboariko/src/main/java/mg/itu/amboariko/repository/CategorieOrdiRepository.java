package mg.itu.amboariko.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mg.itu.amboariko.model.CategorieOrdi;
import mg.itu.amboariko.model.Client;

@Repository
public interface CategorieOrdiRepository extends CrudRepository<CategorieOrdi, Long>{
    
}
