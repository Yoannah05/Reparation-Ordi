package mg.itu.amboariko.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Table("type_reparations")
public class TypeReparation {

    @Id
    private Long idTypeRep; // Correspond à id_type_rep

    private String val; // Correspond à val
}
