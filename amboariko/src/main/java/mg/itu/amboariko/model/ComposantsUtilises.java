package mg.itu.amboariko.model;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComposantsUtilises {
    @Id
    private Long idCu;
    private Long idRepOrdi;
    private Long idComposant;
    private Integer quantiteUtilisee;
}
