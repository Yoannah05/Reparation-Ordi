package mg.itu.amboariko.controller;

import mg.itu.amboariko.model.Retour;
import mg.itu.amboariko.model.Reparation;
import mg.itu.amboariko.service.RetourService;
import mg.itu.amboariko.repository.CategorieOrdiRepository;
import mg.itu.amboariko.repository.ProblemeRepository;
import mg.itu.amboariko.repository.ReparationRepository;
import mg.itu.amboariko.repository.TypeReparationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class RetourController {

    @Autowired
    private RetourService retourService;

    @Autowired
    private ReparationRepository reparationRepository;

    @Autowired
    private TypeReparationRepository typeRepRepository;

    @Autowired
    private CategorieOrdiRepository categorieOrdiRepository;

    @Autowired
    private ProblemeRepository probRepo;

    /**
     * Affiche la page d'insertion de retour avec les réparations disponibles
     */
    @GetMapping("/retours/new")
    public String showInsertForm(Model model) {
        List<Reparation> reparations = (List<Reparation>) reparationRepository.findAll();
        model.addAttribute("reparations", reparations);
        model.addAttribute("body", "Retours/add-retour");
        return "layout"; 
    }

    /**
     * Soumettre le formulaire pour enregistrer le retour
     */
    @PostMapping("/retours")
    public String saveRetour(@RequestParam Long idReparation,
            @RequestParam String dateRetour, Model model) {
        try {
            Retour retour = Retour.builder()
                    .idReparation(idReparation)
                    .dateRetour(LocalDate.parse(dateRetour))
                    .build();

            retourService.saveRetourAndUpdateReparation(retour, idReparation);

            model.addAttribute("message", "Retour enregistré et réparation mise à jour avec succès.");
            model.addAttribute("body", "Retours/add-retour");
            return "layout"; 
        } catch (Exception e) {
            model.addAttribute("message", "Erreur lors de l'enregistrement du retour: " + e.getMessage());
            model.addAttribute("body", "Retours/add-retour");
            return "layout"; 
        }
    }

    @GetMapping("/retours")
    public String showRetoursPage(Model model,
            @RequestParam(required = false) String categorieOrdi,
            @RequestParam(required = false) String probleme,
            @RequestParam(required = false) String typeReparation) {

        List<Retour> retours = retourService.findByCriteria(categorieOrdi, probleme, typeReparation);

        model.addAttribute("retours", retours);
        model.addAttribute("categorieOrdi", categorieOrdi);
        model.addAttribute("probleme", probleme);
        model.addAttribute("typeReparation", typeReparation);

        model.addAttribute("categories", categorieOrdiRepository.findAll());
        model.addAttribute("problemes", probRepo.findAll());
        model.addAttribute("typesReparation", typeRepRepository.findAll());

        model.addAttribute("body", "Retours/retour-list");

        return "layout";
    }
}
