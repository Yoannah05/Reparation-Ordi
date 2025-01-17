package mg.itu.amboariko.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;

@Data
@Builder
public class Paiement {
    @Id
    private Long idPaiement;
    private Long idReparation;
    private Double montantPaye;
    private LocalDate datePaiement;
    private boolean statut;
}
