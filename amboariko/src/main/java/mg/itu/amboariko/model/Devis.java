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
@Table("devis")
public class Devis {
    @Id
    private Long idDevis;
    private Long idOrdinateur;
    private Date dateDuDevis;
    private Double montantTotal;
    private String statutDuDevis;
}
