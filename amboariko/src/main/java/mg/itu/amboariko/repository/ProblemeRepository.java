package mg.itu.amboariko.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mg.itu.amboariko.model.Probleme;

@Repository
public interface ProblemeRepository extends CrudRepository<Probleme, Long>{
    @Query("SELECT p.* FROM Problemes p " +
           "JOIN Reparations_ordi ro ON p.id_probleme = ro.id_probleme " +
           "WHERE ro.id_reparation = :idReparation")
    List<Probleme> findProbByReparation(Long idReparation);
}
