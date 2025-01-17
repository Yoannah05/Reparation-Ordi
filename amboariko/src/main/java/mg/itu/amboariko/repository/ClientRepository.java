package mg.itu.amboariko.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mg.itu.amboariko.model.Client;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {

    @Query("SELECT * FROM Clients WHERE email = :email")
    Optional<Client> findByEmail(String email);

    @Query("SELECT id_client, nom, prenom, email FROM v_clients_retours WHERE date_retour = :dateRetour")
    List<Client> findClientsByReturnDate(String dateRetour);

}
