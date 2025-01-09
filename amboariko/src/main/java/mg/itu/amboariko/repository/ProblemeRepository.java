package mg.itu.amboariko.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mg.itu.amboariko.model.Probleme;

@Repository
public interface ProblemeRepository extends CrudRepository<Probleme, Long>{
    // Optional<Probleme> findByIdProbleme(Long idProbleme);
}
