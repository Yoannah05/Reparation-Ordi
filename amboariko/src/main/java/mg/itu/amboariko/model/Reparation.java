package mg.itu.amboariko.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("reparations")
public class Reparation {
    @Id
    private Long idReparation;
    private Long idOrdinateur;
    private Long idTechnicien;
    private Long idCommission;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Double prixReparation;
    private Boolean statut = false;
}
