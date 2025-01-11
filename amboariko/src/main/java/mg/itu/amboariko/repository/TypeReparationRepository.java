package mg.itu.amboariko.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mg.itu.amboariko.model.Client;
import mg.itu.amboariko.model.TypeReparation;

@Repository
public interface TypeReparationRepository extends CrudRepository<TypeReparation, Long>{

    
} 
