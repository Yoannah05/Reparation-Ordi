package mg.itu.amboariko.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Table("categorie_ordi")
public class CategorieOrdi {

    @Id
    private Long idCategorieOrdi; // Correspond à id_categorie_ordi

    private String val; // Correspond à val
}
