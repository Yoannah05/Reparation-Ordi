package mg.itu.amboariko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mg.itu.amboariko.model.VModelTechnicienCommission;
import mg.itu.amboariko.model.Technicien;
import mg.itu.amboariko.repository.TechnicienRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

@Service
public class TechnicienService {
    @Autowired
    private TechnicienRepository technicienRepository;

    public double calculerSommeCommissions(List<VModelTechnicienCommission> vmodel) {
        if (vmodel == null || vmodel.isEmpty()) {
            return 0.0; // Retourne 0 si la liste est vide ou nulle
        }
        
        return vmodel.stream()
                .map(VModelTechnicienCommission::getCommission)  // Récupère les valeurs de commission
                .filter(commission -> commission != null)  // Ignore les commissions nulles
                .mapToDouble(Double::doubleValue)  // Convertit en double
                .sum();  // Calcule la somme
    }

    public List<VModelTechnicienCommission> getTechnicienCommission(Long idTechnicien, LocalDate date1, LocalDate date2) {
        return technicienRepository.findTechnicienCommission(idTechnicien, date1, date2);
    }

    public List<VModelTechnicienCommission> getTechnicienCommissionBySexe(Long idSexe, LocalDate date1, LocalDate date2) {
        return technicienRepository.findTechnicienCommissionBySexe(idSexe, date1, date2);
    }

    public List<Technicien> getAllTechniciens() {
        Iterable<Technicien> techniciensIterable = technicienRepository.findAll();
        List<Technicien> techniciens = new ArrayList<>();
        techniciensIterable.forEach(techniciens::add);
        return techniciens;
    }

    public Optional<Technicien> getTechnicienById(Long idTechnicien) {
        return technicienRepository.findById(idTechnicien);
    }

    public Technicien saveTechnicien(Technicien technicien) {
        return technicienRepository.save(technicien);
    }

}