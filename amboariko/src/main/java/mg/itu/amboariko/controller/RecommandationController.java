package mg.itu.amboariko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;

import mg.itu.amboariko.model.Recommandation;
import mg.itu.amboariko.repository.RecommandationRepository;
import mg.itu.amboariko.repository.ComposantRepository;

import java.util.List;

@Controller
public class RecommandationController {

    @Autowired
    private RecommandationRepository recommandationRepository;

    @Autowired
    private ComposantRepository compsRepo;

    // List all recommandations
    @GetMapping("/recommandations")
    public String listRecommandations(@RequestParam(value = "month", required = false) Integer month, Model model) {
        List<Recommandation> recommandations;

        if (month != null) {
            recommandations = recommandationRepository.findByDateMonth(month);
        } else {
            recommandations = (List<Recommandation>) recommandationRepository.findAll();
        }

        model.addAttribute("recommandations", recommandations);
        model.addAttribute("body", "Recommandation/rec-list");

        return "layout"; 
    }

    // Show form to add a new recommandation
    @GetMapping("/recommandations/new")
    public String showAddForm(Model model) {
        model.addAttribute("composants", compsRepo.findAll()); 
        model.addAttribute("recommandation", new Recommandation()); 
        model.addAttribute("body", "Recommandation/add-recommandation");
        return "layout";
    }

    @PostMapping("/recommandations")
    public String addRecommandation(@ModelAttribute Recommandation recommandation) {
        recommandationRepository.save(recommandation);
        return "redirect:/recommandations"; 
    }
}
