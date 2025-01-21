package mg.itu.amboariko.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("ordinateurs")
public class Ordinateur {
    @Id
    private Long idOrdinateur;
    private Long idClient;
    private Long idModele;
    private String numeroSerie;
}
