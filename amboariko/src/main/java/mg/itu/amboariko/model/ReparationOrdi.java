package mg.itu.amboariko.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("reparations_ordi")
public class ReparationOrdi {
    @Id
    private Long idRepOrdi;
    private Long idOrdi;
    private Long idProbleme;
    private Long idReparation;
    private Long idTypeRep;
}