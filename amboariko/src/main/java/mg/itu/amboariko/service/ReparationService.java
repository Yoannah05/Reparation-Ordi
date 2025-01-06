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
}
