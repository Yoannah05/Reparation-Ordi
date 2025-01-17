package mg.itu.amboariko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import mg.itu.amboariko.model.Ordinateur;
import mg.itu.amboariko.repository.OrdinateurRepository;
import mg.itu.amboariko.service.ClientService;

@Controller
public class OrdinateurController {

    @Autowired
    private OrdinateurRepository ordiRepo;

    @Autowired
    private ClientService clientService;


    public OrdinateurController(OrdinateurRepository ordiRepo, ClientService clientservice) {
        this.ordiRepo = ordiRepo;
        this.clientService = clientservice;
    }

    @GetMapping("/ordinateurs")
    public String listOrdinateurs(Model model) {
        model.addAttribute("ordinateurs", ordiRepo.findAll()); 
        model.addAttribute("body", "Ordinateur/ordinateurs-list");
        return "layout"; 
    }

    @GetMapping("/ordinateurs/new")
    public String showAddForm(Model model) {
        model.addAttribute("clients", clientService.getAllClients());
        model.addAttribute("ordinateur", new Ordinateur()); 
        model.addAttribute("body", "Ordinateur/add-ordinateur");
        return "layout"; 
    }

    @PostMapping("/ordinateurs")
    public String addOrdinateur(@ModelAttribute Ordinateur ordinateur) {
        ordiRepo.save(ordinateur); 
        return "redirect:/ordinateurs"; 
    }
}
