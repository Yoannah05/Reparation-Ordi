package mg.itu.amboariko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mg.itu.amboariko.model.Paiement;
import mg.itu.amboariko.model.Reparation;
import mg.itu.amboariko.repository.PaiementRepository;
import mg.itu.amboariko.repository.ReparationRepository;

@Service
public class PaiementService {

    @Autowired
    private PaiementRepository paiementRepo;

    @Autowired
    private ReparationRepository reparationRepo;

    public boolean payer(Reparation rep, double montant) {
        Paiement paiement = Paiement.builder()
                .idReparation(rep.getIdReparation())
                .montantPaye(montant)
                .datePaiement(java.time.LocalDate.now()) 
                .statut(montant == rep.getPrixReparation()) 
                .build();

        if (paiement.isStatut()) {
            rep.setStatut(true); 
            reparationRepo.save(rep); 
        }
        paiementRepo.save(paiement);

        return paiement.isStatut();  
    }
}
