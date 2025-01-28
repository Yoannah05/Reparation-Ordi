package mg.itu.amboariko.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mg.itu.amboariko.model.Composant;
import mg.itu.amboariko.model.Technicien;
import mg.itu.amboariko.model.VModelTechnicienCommission;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TechnicienRepository extends CrudRepository<Technicien, Long> {

    @Query("SELECT * FROM Techniciens t JOIN Reparations r ON t.id_technicien = r.id_technicien WHERE r.id_reparation = :idReparation")
    List<Technicien> findTechnicienById(Long idReparation);

    @Query("""
        SELECT * 
        FROM v_commissions_techniciens 
        WHERE id_technicien = COALESCE(:idTechnicien, id_technicien)
            AND date_retour >= COALESCE(:date1, date_retour)
            AND date_retour <= COALESCE(:date2, date_retour)
    """)
    List<VModelTechnicienCommission> findTechnicienCommission(
        Long idTechnicien, 
        LocalDate date1, 
        LocalDate date2
    );

    @Query("""
        SELECT * 
        FROM v_commissions_techniciens 
        WHERE id_sexe = COALESCE(:idSexe, id_sexe)
            AND date_retour >= COALESCE(:date1, date_retour)
            AND date_retour <= COALESCE(:date2, date_retour)
    """)
    List<VModelTechnicienCommission> findTechnicienCommissionBySexe(
        Long idSexe, 
        LocalDate date1, 
        LocalDate date2
    );

    
}

