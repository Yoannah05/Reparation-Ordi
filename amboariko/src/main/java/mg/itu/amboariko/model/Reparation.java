package mg.itu.amboariko.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

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
    private Date dateDebut;
    private Date dateFin;
    private Double prixReparation;
    private Boolean statut = false;
}
