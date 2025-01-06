package mg.itu.amboariko.model;

import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class Probleme {
    @Id
    private Long idProbleme;
    private String descriptionProbleme;
}
