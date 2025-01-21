package mg.itu.amboariko.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor



public class VModelTechnicienCommission {

    private String nom;
    private Long idRetours;
    private Double prix;
    private Double pourcentageCommission;
    private Double commission;
    private LocalDate dateRetour; 
    
}
