package mg.itu.amboariko.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("composants")
public class Composant {
    @Id
    private Long idComposant;
    private Double pu;
    private String nomComposant;
}
