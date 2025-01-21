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
import java.util.Optional;

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
    public List<Retour> findByCriteria(Long categorieOrdi, Long probleme, Long typeReparation) {
        return retourRepository.findByCriteria(categorieOrdi, probleme, typeReparation);
    }

    @Transactional
    public void deleteRetour(Long idRetours) {
        Retour retour = retourRepository.findById(idRetours)
                .orElseThrow(() -> new RuntimeException("Retour non trouvé avec l'ID: " + idRetours));
        retourRepository.deleteById(idRetours);
    }

    @Transactional
    public void updateRetour(Long idRetours, LocalDate dateRetour) {
        Retour retour = retourRepository.findById(idRetours)
                .orElseThrow(() -> new RuntimeException("Retour non trouvé avec l'ID: " + idRetours));

        retour.setDateRetour(dateRetour);
        retourRepository.save(retour);
    }

    public Optional<Retour> findById(Long id) {
        return retourRepository.findById(id);
    }

    public List<Retour> findAll() {
        return (List<Retour>) retourRepository.findAll();
    }

}
