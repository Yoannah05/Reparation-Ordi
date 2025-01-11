package mg.itu.amboariko.service;

import mg.itu.amboariko.model.Retour;
import mg.itu.amboariko.model.Reparation; // La classe Reparation, vous devrez la définir
import mg.itu.amboariko.repository.RetourRepository;
import mg.itu.amboariko.repository.ReparationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class RetourService {

    @Autowired
    private RetourRepository retourRepository; // Le repository pour Retour

    @Autowired
    private ReparationRepository reparationRepository; // Le repository pour Reparation

    /**
     * Cette méthode permet de sauvegarder un retour et de mettre à jour l'état de
     * la réparation associée.
     * 
     * @param retour       Le retour à sauvegarder
     * @param idReparation L'identifiant de la réparation à mettre à jour
     */
    @Transactional
    public void saveRetourAndUpdateReparation(Retour retour, Long idReparation) {
        retourRepository.save(retour);
        Reparation reparation = reparationRepository.findById(idReparation)
                .orElseThrow(() -> new RuntimeException("Reparation non trouvée avec l'ID: " + idReparation));
        reparation.setStatut(true); 
        reparationRepository.save(reparation); // Sauvegarder la réparation mise à jour
    }

    /**
     * Recherche des retours en fonction des critères
     * @param categorieOrdi : catégorie d'ordinateur (optionnel)
     * @param probleme : problème associé à la réparation (optionnel)
     * @param typeReparation : type de réparation (optionnel)
     * @return Liste de retours correspondant aux critères
     */
    public List<Retour> findByCriteria(String categorieOrdi, String probleme, String typeReparation) {
        return retourRepository.findByCriteria(categorieOrdi, probleme, typeReparation);
    }
}
