package mg.itu.amboariko.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import mg.itu.amboariko.model.Ordinateur;

@Repository
public interface OrdinateurRepository extends CrudRepository<Ordinateur, Long> {
    @Query("SELECT * FROM ordinateurs WHERE id_client = :idClient")
    List<Ordinateur> findByClientId(Long idClient);

}
