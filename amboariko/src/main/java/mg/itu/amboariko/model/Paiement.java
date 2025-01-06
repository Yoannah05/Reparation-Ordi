package mg.itu.amboariko.model;

import lombok.Builder;
import lombok.Data;
import java.sql.Date;
import org.springframework.data.annotation.Id;

@Data
@Builder
public class Paiement {
    @Id
    private Long idPaiement;
    private Long idReparation;
    private Double montantPaye;
    private Date datePaiement;
}
