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
public class Modele {
    @Id
    private Long idModele;
    private Marque marque;
    private String nomModele;
}
