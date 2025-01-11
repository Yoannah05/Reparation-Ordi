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
    @GetMapping("/insert")
    public String showInsertForm(Model model) {
        // Récupérer la liste des réparations depuis la base de données
        List<Reparation> reparations = (List<Reparation>) reparationRepository.findAll();
        model.addAttribute("reparations", reparations);
        return "Retours/add-retour"; // Nom du fichier Thymeleaf (insertRetour.html)
    }

    /**
     * Soumettre le formulaire pour enregistrer le retour
     */
    @PostMapping("/save")
    public String saveRetour(@RequestParam Long idReparation,
            @RequestParam String dateRetour, Model model) {
        try {
            // Create and populate the Retour object
            Retour retour = new Retour();
            retour.setDateRetour(LocalDate.parse(dateRetour));

            // Call the service method to save the Retour and update the associated
            // Reparation
            retourService.saveRetourAndUpdateReparation(retour, idReparation);

            // Provide success message
            model.addAttribute("message", "Retour enregistré et réparation mise à jour avec succès.");
            return "Retours/add-retour"; // Return to the form page
        } catch (Exception e) {
            // Provide error message in case of failure
            model.addAttribute("message", "Erreur lors de l'enregistrement du retour: " + e.getMessage());
            return "Retours/add-retour"; // Return to the form page
        }
    }

    @GetMapping("/retours")
    public String showRetoursPage(Model model,
            @RequestParam(required = false) String categorieOrdi,
            @RequestParam(required = false) String probleme,
            @RequestParam(required = false) String typeReparation) {

        // Get the filtered list of retours
        List<Retour> retours = retourService.findByCriteria(categorieOrdi, probleme, typeReparation);

        // Add all data needed for the page
        model.addAttribute("retours", retours);
        model.addAttribute("categorieOrdi", categorieOrdi);
        model.addAttribute("probleme", probleme);
        model.addAttribute("typeReparation", typeReparation);

        // Possible filter options (e.g., categories, problems, and types of repairs)
        model.addAttribute("categories", categorieOrdiRepository.findAll());
        model.addAttribute("problemes", probRepo.findAll());
        model.addAttribute("typesReparation", typeRepRepository.findAll());

        return "Retours/retour-list"; // Return the view to render
    }
}
