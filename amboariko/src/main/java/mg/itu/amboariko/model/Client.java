package mg.itu.amboariko.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("Clients") 
public class Client {

    @Id
    private Long idClient;

    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
    private String email;
}
