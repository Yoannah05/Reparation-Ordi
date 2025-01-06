package mg.itu.amboariko.repository;

import mg.itu.amboariko.model.Reparation;
import org.springframework.data.repository.CrudRepository;
public interface ReparationRepository extends CrudRepository<Reparation, Long> {
    // This will automatically provide basic CRUD operations for Reparation
}
