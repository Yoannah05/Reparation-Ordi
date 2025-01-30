package mg.itu.amboariko.service;

import mg.itu.amboariko.model.Probleme;
import mg.itu.amboariko.model.Reparation;
import mg.itu.amboariko.model.ReparationOrdi;
import mg.itu.amboariko.model.VModelComposantUtilise;
import mg.itu.amboariko.model.ComposantsUtilises;
import mg.itu.amboariko.model.Composant;
import mg.itu.amboariko.model.ReparationOrdi;
import mg.itu.amboariko.repository.ComposantRepository;
import mg.itu.amboariko.repository.ComposantUtiliseRepository;
import mg.itu.amboariko.repository.ProblemeRepository;
import mg.itu.amboariko.repository.ReparationRepository;
import mg.itu.amboariko.repository.ReparationOrdiRepository;
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
    @Autowired
    private ReparationOrdiRepository reparationOrdiRepository;



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

    private double getCoutReparation(Long idReparation) {
        double cout = 0;
        List<VModelComposantUtilise> composantsFromView = compsUtiliseRepo.findByIdReparation(idReparation);
    
        for (VModelComposantUtilise composantView : composantsFromView) {
            if (composantView.getIdReparation().equals(idReparation)) {
                cout += composantView.getTotalPrix();
            }
        }
        return cout;
    }    

//     public Reparation reparer(Reparation rep, List<Long> problemes, List<Long> composants, List<Integer> quantites, Long idOrdinateur, Long idTypeReparation) {
//         double coutReparation = getCoutReparation(rep.getIdReparation());
//         rep.setPrixReparation(coutReparation);
//         Reparation savedReparation = reparationRepository.save(rep);

//         for (Long idProbleme : problemes) {
//         ReparationOrdi reparationOrdi = ReparationOrdi.builder()
//                 .idOrdi(idOrdinateur)
//                 .idProbleme(idProbleme)
//                 .idReparation(savedReparation.getIdReparation())
//                 .idTypeRep(idTypeReparation)
//                 .build();

//         ReparationOrdi savedReparationOrdi = reparationOrdiRepository.save(reparationOrdi);

//         // Enregistrer les composants utilisés avec leurs quantités
//         for (int i = 0; i < composants.size(); i++) {
//             ComposantsUtilises composantUtilise = new ComposantsUtilises();
//             composantUtilise.setIdRepOrdi(savedReparationOrdi.getIdRepOrdi()); // Référence à la réparation sur ordinateur
//             composantUtilise.setIdComposant(composants.get(i)); // ID du composant
//             composantUtilise.setQuantiteUtilisee(quantites.get(i)); // Quantité utilisée
//             compsUtiliseRepo.save(composantUtilise);
//         }
    


//     }
//             return savedReparation;

//     // Optional method to handle stock updates (e.g., reduce stock after using
//     // components)
//     // private void updateStockAfterRepair(List<ComposantsUtilises> comps) {
//     //     for (ComposantsUtilises composantsUtilise : comps) {
//     //         Optional<Composant> compOpt = compsRepo.findById(composantsUtilise.getIdComposant());
//     //         if (compOpt.isPresent()) {
//     //             Composant comp = compOpt.get();
//     //             // You might want to update stock by reducing the quantity of the component
//     //             // This is a simplistic approach; you can adapt based on your Stock entity
//     //             // Example: update the stock quantity based on how much was used
//     //             comp.setStock(comp.getStock() - composantsUtilise.getQuantiteUtilisee());
//     //             compsRepo.save(comp);
//     //         }
//     //     }
//     // }

// }

public Reparation reparer(Reparation rep, List<Long> problemes, List<Long> composants, List<Integer> quantites, Long idOrdinateur, Long idTypeReparation) throws InterruptedException {
    // 1. Sauvegarder la reparation pour obtenir un ID
    Reparation savedReparation = reparationRepository.save(rep);

    // 2. Enregistrer reparationOrdi et les composants utilisés
    for (Long idProbleme : problemes) {
        ReparationOrdi reparationOrdi = ReparationOrdi.builder()
            .idOrdi(idOrdinateur)
            .idProbleme(idProbleme)
            .idReparation(savedReparation.getIdReparation())
            .idTypeRep(idTypeReparation)
            .build();

        ReparationOrdi savedReparationOrdi = reparationOrdiRepository.save(reparationOrdi);

        for (int i = 0; i < composants.size(); i++) {
            ComposantsUtilises composantUtilise = new ComposantsUtilises();
            composantUtilise.setIdRepOrdi(savedReparationOrdi.getIdRepOrdi());
            composantUtilise.setIdComposant(composants.get(i));
            composantUtilise.setQuantiteUtilisee(quantites.get(i));
            compsUtiliseRepo.save(composantUtilise);
        }
    }

    // 3. Maintenant que tout est inséré, recalculer le coût
    Thread.sleep(200);
    double coutReparation = getCoutReparation(savedReparation.getIdReparation());
    savedReparation.setPrixReparation(coutReparation);

    // 4. Sauvegarder à nouveau la réparation avec le coût correct
    Reparation updatedReparation = reparationRepository.save(savedReparation);

    return updatedReparation;
}


    
}
