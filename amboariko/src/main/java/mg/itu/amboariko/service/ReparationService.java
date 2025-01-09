package mg.itu.amboariko.service;

import mg.itu.amboariko.model.Reparation;
import mg.itu.amboariko.repository.ReparationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReparationService {

    @Autowired
    private final ReparationRepository reparationRepository;

    public ReparationService(ReparationRepository reparationRepository) {
        this.reparationRepository = reparationRepository;
    }

    public void save(Reparation reparation) {
        reparationRepository.save(reparation); 
    }

    public List<Reparation> getAllReparations() {
        return (List<Reparation>) reparationRepository.findAll();
    }

    public List<Reparation> findByProb(Long idProb) {
        return (List<Reparation>) reparationRepository.findByProbleme(idProb);
    }

    // public double getCoutReparation(Long idRep) {

    // }
    // public Reparation finirReparation(Long idrep) {
    //     // select reparation_odi where id_rep = idrep join composant_utilises joinn composant, sum (pu * quantite utilises) group by composant
    //     //  
    //     //  insert sortie stock 
    //     return null;
    // }

    // public boolean payer(double montant, Long idRep) {
    //     //update status = TRUE paiement where id_rep 
    // }
}
