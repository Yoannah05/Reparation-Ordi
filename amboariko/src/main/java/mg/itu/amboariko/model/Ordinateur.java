package mg.itu.amboariko.model;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ordinateur {
    @Id
    private Long idOrdinateur;
    private Client client;
    private Modele modele;
    private String numeroSerie;
}
