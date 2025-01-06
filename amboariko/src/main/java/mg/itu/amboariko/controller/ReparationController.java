package mg.itu.amboariko.controller;

import mg.itu.amboariko.model.Reparation;
import mg.itu.amboariko.service.ReparationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReparationController {

    @Autowired
    private final ReparationService reparationService;

    public ReparationController(ReparationService reparationService) {
        this.reparationService = reparationService;
    }

    @GetMapping("/addReparation")
    public String showAddReparationForm(Model model) {
        model.addAttribute("reparation", new Reparation());
        return "Reparation/add-reparation"; 
    }

    @PostMapping("/addReparation")
    public String addReparation(Reparation reparation) {
        reparationService.save(reparation); 
        return "redirect:/reparationList"; 
    }

    @GetMapping("/reparationList")
    public String getReparations(Model model) {
        model.addAttribute("reparations", reparationService.getAllReparations()); // Get all reparations
        return "Reparation/reparation-list"; 
    }
}
