package mg.itu.amboariko.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("recommandations")
public class Recommandation {
    @Id
    private Long idRecommandation; 

    private Long idComposant; 

    private LocalDate date; 
}
