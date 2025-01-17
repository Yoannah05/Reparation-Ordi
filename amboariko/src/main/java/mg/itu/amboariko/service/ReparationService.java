package mg.itu.amboariko.service;

import mg.itu.amboariko.model.Probleme;
import mg.itu.amboariko.model.Reparation;
import mg.itu.amboariko.model.ComposantsUtilises;
import mg.itu.amboariko.model.Composant;
import mg.itu.amboariko.repository.ComposantRepository;
import mg.itu.amboariko.repository.ComposantUtiliseRepository;
import mg.itu.amboariko.repository.ProblemeRepository;
import mg.itu.amboariko.repository.ReparationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReparationService {

    @Autowired
    private final ReparationRepository reparationRepository;
    @Autowired
    private ProblemeRepository problemeRepository;
    @Autowired 
    private ComposantRepository compsRepo;
    @Autowired
    private ComposantUtiliseRepository compsUtiliseRepo;



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

    public void deleteById(Long id) {
        reparationRepository.deleteById(id);
    }

    public Optional<Reparation> getReparationById(Long id) {
        return reparationRepository.findById(id);
    }

    public List<Probleme> getProblemesByReparation(Long idReparation) {
        return problemeRepository.findProbByReparation(idReparation);
    }

    private double getCoutReparation(List<ComposantsUtilises> comps) {
        double cout = 0;
        for (ComposantsUtilises composantsUtilise : comps) {
            Optional<Composant> compOpt = compsRepo.findById(composantsUtilise.getIdComposant());
            if (compOpt.isPresent()) {
                Composant comp = compOpt.get();
                cout += comp.getPu() * composantsUtilise.getQuantiteUtilisee();
            } else {
                System.out.println("Composant not found for ID: " + composantsUtilise.getIdComposant());
            }
        }
        return cout;
    }

    public Reparation reparer(Reparation rep, List<ComposantsUtilises> comps) {
        compsUtiliseRepo.saveAll(comps);
        double coutReparation = getCoutReparation(comps);
        rep.setPrixReparation(coutReparation);
        rep.setStatut(true); 
        reparationRepository.save(rep);

        // You might need to implement stock reduction logic here (assuming you want to
        // manage stock)
        // updateStockAfterRepair(comps);

        return rep;
    }

    // Optional method to handle stock updates (e.g., reduce stock after using
    // components)
    // private void updateStockAfterRepair(List<ComposantsUtilises> comps) {
    //     for (ComposantsUtilises composantsUtilise : comps) {
    //         Optional<Composant> compOpt = compsRepo.findById(composantsUtilise.getIdComposant());
    //         if (compOpt.isPresent()) {
    //             Composant comp = compOpt.get();
    //             // You might want to update stock by reducing the quantity of the component
    //             // This is a simplistic approach; you can adapt based on your Stock entity
    //             // Example: update the stock quantity based on how much was used
    //             comp.setStock(comp.getStock() - composantsUtilise.getQuantiteUtilisee());
    //             compsRepo.save(comp);
    //         }
    //     }
    // }

    
}
