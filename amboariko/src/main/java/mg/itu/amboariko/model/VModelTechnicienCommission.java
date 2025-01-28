package mg.itu.amboariko.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor



public class VModelTechnicienCommission {

    private String nom;
    private Long idSexe;
    private Long idRetours;
    private Double prix;
    private Double pourcentageCommission;
    private Double commission;
    private LocalDate dateRetour; 
    
}
