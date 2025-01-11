package mg.itu.amboariko.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@Table("retours")
@AllArgsConstructor
@NoArgsConstructor
public class Retour {

    @Id
    private Long idRetour; // Correspond à id_retours

    private Long idReparation; // Correspond à id_reparation

    private LocalDate dateRetour; // Correspond à date_retour
}
