package mg.itu.amboariko.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
@Table("problemes")
public class Probleme {
    @Id
    private Long idProbleme;
    private String val;
    private String descriptionProbleme;
}
