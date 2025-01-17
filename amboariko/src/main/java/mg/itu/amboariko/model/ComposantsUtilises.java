package mg.itu.amboariko.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ComposantsUtilises {
    private Long idReparation;
    private Long idComposant;
    private Integer quantiteUtilisee;
}
