package mg.itu.amboariko.controller;

import mg.itu.amboariko.service.ReparationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private final ReparationService reparationService;

    public DashboardController(ReparationService reparationService) {
        this.reparationService = reparationService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("body", "home");
        return "layout";
    }
    // @GetMapping("/")
    // public String showDashboard(Model model) {

    //     long totalReparations = reparationService.getAllReparations().size();
    //     long pendingReparations = reparationService.getAllReparations().stream()
    //             .filter(reparation -> !reparation.getStatut()).count(); // Count pending repairs
    //     long completedReparations = totalReparations - pendingReparations;

    //     // Add statistics to the model to display in the dashboard
    //     model.addAttribute("totalReparations", totalReparations);
    //     model.addAttribute("pendingReparations", pendingReparations);
    //     model.addAttribute("completedReparations", completedReparations);

    //     model.addAttribute("content", "home :: content");
    //     return "layout";
    // }
}
