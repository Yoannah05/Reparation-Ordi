package mg.itu.amboariko.controller;

import mg.itu.amboariko.model.Reparation;
import mg.itu.amboariko.repository.ProblemeRepository;
import mg.itu.amboariko.service.ReparationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ReparationController {
    @Autowired
    private ProblemeRepository probRepo;

    @Autowired
    private final ReparationService reparationService;

    public ReparationController(ReparationService reparationService, ProblemeRepository probrep) {
        this.reparationService = reparationService;
        this.probRepo = probrep;
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

    @GetMapping("path")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    

    @GetMapping("/reparations")
    public String getReparations(@RequestParam(value = "idProbleme", required = false) Long idProbleme, Model model) {
        if (idProbleme != null) {
            model.addAttribute("reparations", reparationService.findByProb(idProbleme));
        } else {
            model.addAttribute("reparations", reparationService.getAllReparations());
        }

        model.addAttribute("problemes", probRepo.findAll());
        return "Reparation/reparation-list";
    }
}
