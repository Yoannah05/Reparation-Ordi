package mg.itu.amboariko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import mg.itu.amboariko.model.Composant;
import mg.itu.amboariko.model.ComposantPrix;
import mg.itu.amboariko.repository.ComposantRepository;
import mg.itu.amboariko.repository.ComposantPrixRepository;

@Controller
public class ComposantController {
    @Autowired
    private ComposantRepository compRepo;

    @Autowired
    private ComposantPrixRepository compPrixRepo;

    public ComposantController(ComposantRepository composantrepo) {
        compRepo = composantrepo;
    }

    @GetMapping("/composants")
    public String listComposants(Model model) {
        model.addAttribute("composants", compRepo.findAll());
        model.addAttribute("body", "Composant/composants-list");
        return "layout";
    }

    @GetMapping("/composants/historiquePrix")
    public String listComposantsPrix(Model model) {
        model.addAttribute("composants", compPrixRepo.findAll());
        model.addAttribute("body", "Composant/composant-list-prix");
        return "layout";
    }

    @GetMapping("/composants/new")
    public String showAddForm(Model model) {
        model.addAttribute("composant", new Composant());
        model.addAttribute("body", "Composant/add-composant");
        return "layout";
    }

    @GetMapping("/composants/newPrix")
    public String showAddPrixForm(Model model) {
        model.addAttribute("composantPrix", new ComposantPrix());
        model.addAttribute("composants", compRepo.findAll()); // Charger tous les composants pour le select
        model.addAttribute("body", "Composant/add-composantPrix");
        return "layout";
    }

    @GetMapping("/composants/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Composant composant = compRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Composant non trouvé avec l'ID: " + id));
        model.addAttribute("composant", composant);
        model.addAttribute("body", "Composant/add-composant");
        return "layout";
    }

    @PostMapping("/composants/newPrix")
    public String addComposantPrix(@ModelAttribute ComposantPrix composantPrix) {
        compPrixRepo.save(composantPrix);
        return "redirect:/composants/historiquePrix";
    }

    @PostMapping("/composants")
    public String addComposant(@ModelAttribute Composant comp) {
        compRepo.save(comp);
        return "redirect:/composants";
    }

    @PostMapping("/composants/{id}")
    public String updateComposant(@PathVariable Long id, @ModelAttribute Composant comp) {
        comp.setIdComposant(id); // Assurez-vous que l'ID est correctement défini
        compRepo.save(comp);
        return "redirect:/composants";
    }

    @DeleteMapping("/composants/{id}")
    public String deleteComposant(@PathVariable Long id) {
        compRepo.deleteById(id);
        return "redirect:/composants"; // Redirige vers la liste des composants après la suppression
    }
}