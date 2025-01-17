package mg.itu.amboariko.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import mg.itu.amboariko.model.Ordinateur;

@Repository
public interface OrdinateurRepository extends CrudRepository<Ordinateur, Long> {

}
