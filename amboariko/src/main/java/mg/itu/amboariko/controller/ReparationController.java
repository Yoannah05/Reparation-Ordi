package mg.itu.amboariko.controller;

import mg.itu.amboariko.model.Reparation;
import mg.itu.amboariko.repository.ProblemeRepository;
import mg.itu.amboariko.service.ReparationService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ReparationController {

    @Autowired
    private ProblemeRepository probRepo;

    @Autowired
    private final ReparationService reparationService;

    public ReparationController(ReparationService reparationService, ProblemeRepository probRepo) {
        this.reparationService = reparationService;
        this.probRepo = probRepo;
    }

    // Afficher le formulaire d'ajout de réparation
    @GetMapping("/reparations/new")
    public String showAddReparationForm(Model model) {
        model.addAttribute("reparation", new Reparation());
        model.addAttribute("body", "Reparation/add-reparation");
        return "layout";
    }

    // Ajouter une nouvelle réparation
    @PostMapping("/reparations")
    public String addReparation(Reparation reparation) {
        reparationService.save(reparation);
        return "redirect:/reparations";
    }

    // Supprimer une réparation par ID
    @DeleteMapping("/reparations/{id}")
    public String deleteReparation(@PathVariable Long id) {
        reparationService.deleteById(id);
        return "redirect:/reparations";
    }

    // Mettre à jour une réparation par ID
    @PutMapping("/reparations/{id}")
    public String updateReparation(@PathVariable Long id, @RequestBody Reparation reparation) {
        reparation.setIdReparation(id);
        reparationService.save(reparation);
        return "redirect:/reparations/" + id;
    }

    // Afficher les détails d'une réparation par ID
    @GetMapping("/reparations/{id}")
    public String getReparationDetails(@PathVariable Long id, Model model) {
        Optional<Reparation> reparation = reparationService.getReparationById(id);
        model.addAttribute("reparation", reparation);
        model.addAttribute("problemes", reparationService.getProblemesByReparation(id));
        model.addAttribute("body", "Reparation/details");
        return "layout";
    }

    @GetMapping("/reparations/reparer/{id}")
    public String getMethodName(@RequestParam(value = "id", required = false) Long id) {
        return new String();
    }
    

    // Liste des réparations avec possibilité de filtrer par problème
    @GetMapping("/reparations")
    public String getReparations(@RequestParam(value = "idProbleme", required = false) Long idProbleme, Model model) {
        if (idProbleme != null) {
            model.addAttribute("reparations", reparationService.findByProb(idProbleme));
        } else {
            model.addAttribute("reparations", reparationService.getAllReparations());
        }
        model.addAttribute("problemes", probRepo.findAll());
        model.addAttribute("body", "Reparation/reparation-list");
        return "layout";
    }
}
