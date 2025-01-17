package mg.itu.amboariko.repository;

import java.util.List;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import mg.itu.amboariko.model.Recommandation;

@Repository
public interface RecommandationRepository extends CrudRepository<Recommandation, Long> {

    @Query("SELECT r FROM Recommandation r WHERE MONTH(r.date) = :month")
    List<Recommandation> findByDateMonth(int month);
}
