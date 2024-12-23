package mg.itu.amboariko.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mg.itu.amboariko.model.Client;

import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {

    @Query("SELECT * FROM Clients WHERE email = :email")
    Optional<Client> findByEmail(String email);

}
