package mg.itu.amboariko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import mg.itu.amboariko.model.Composant;
import mg.itu.amboariko.model.Recommandation;
import mg.itu.amboariko.repository.RecommandationRepository;
import mg.itu.amboariko.repository.ComposantRepository;

import java.time.LocalDate;
import java.util.List;

@Controller
public class RecommandationController {

    @Autowired
    private RecommandationRepository recommandationRepository;

    @Autowired
    private ComposantRepository composantRepository;

    @GetMapping("/recommandations/new")
    public String showAddRecommandationForm(Model model) {
        model.addAttribute("recommandation", new Recommandation());
        model.addAttribute("composants", composantRepository.findAll());
        model.addAttribute("body", "Recommandation/add-recommandation");
        return "layout";
    }

    // Traiter la soumission du formulaire d'inscription
    @PostMapping("/recommandations")
    public String addRecommandation(@ModelAttribute Recommandation recommandation) {
        recommandationRepository.save(recommandation);
        return "redirect:/recommandations";
    }

    // Afficher la liste des recommandations avec filtre de date
    @GetMapping("/recommandations")
    public String listRecommandations(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Model model) {

        List<Recommandation> recommandations;

        if (date != null) {
            recommandations = recommandationRepository.findByDate(date);
        } else {
            recommandations = (List<Recommandation>) recommandationRepository.findAll();
        }

        model.addAttribute("recommandations", recommandations);
        model.addAttribute("body", "Recommandation/recommandation-list");

        return "layout";
    }

    // Afficher les détails d'une recommandation
    @GetMapping("/recommandations/{id}/details")
    public String showRecommandationDetails(@PathVariable Long id, Model model) {
        Recommandation recommandation = recommandationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recommandation non trouvée avec l'ID: " + id));

        Composant composant = composantRepository.findById(recommandation.getIdComposant())
                .orElseThrow(() -> new RuntimeException(
                        "Composant non trouvé avec l'ID: " + recommandation.getIdComposant()));

        model.addAttribute("recommandation", recommandation);
        model.addAttribute("composant", composant);
        model.addAttribute("body", "Recommandation/recommandation-details");

        return "layout";
    }
}