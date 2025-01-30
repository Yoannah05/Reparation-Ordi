package mg.itu.amboariko.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VModelComposantUtilise {
    private Long idComposantUtilise;
    private Long idRepOrdi;
    private Long idComposant;
    private String nomComposant;
    private Long idReparation;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Double prixComposant;
    private Integer quantiteUtilisee;
    private Double totalPrix;
}