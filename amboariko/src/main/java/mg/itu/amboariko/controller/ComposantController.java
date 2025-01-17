package mg.itu.amboariko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import mg.itu.amboariko.model.Composant;
import mg.itu.amboariko.repository.ComposantRepository;

@Controller
public class ComposantController {
    @Autowired
    private ComposantRepository compRepo;

    public ComposantController(ComposantRepository composantrepo) {
        compRepo=composantrepo; 
    }

    @GetMapping("/composants")
    public String listComposants(Model model) {
        model.addAttribute("composants", compRepo.findAll()); 
        model.addAttribute("body", "Composant/composants-list");
        return "layout"; 
    }

    @GetMapping("/composants/new")
    public String showAddForm(Model model) {
        model.addAttribute("clients", compRepo.findAll());
        model.addAttribute("composant", new Composant()); 
        model.addAttribute("body", "Composant/add-composant");
        return "layout"; 
    }

    @PostMapping("/composants")
    public String addComposant(@ModelAttribute Composant comp) {
        compRepo.save(comp); 
        return "redirect:/composants"; 
    }
}
