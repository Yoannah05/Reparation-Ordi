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
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrdinateur;

    // @ManyToOne
    // @JoinColumn(name = "idClient")
    private Client client;

    // @ManyToOne
    // @JoinColumn(name = "idModele")
    private Modele modele;

    private String numeroSerie;
}
