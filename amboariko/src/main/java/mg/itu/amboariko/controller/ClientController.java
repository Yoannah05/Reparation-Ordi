package mg.itu.amboariko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import mg.itu.amboariko.service.ClientService;

@Controller
public class ClientController {

    @Autowired
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/clients")
    public String getClients(Model model) {
        model.addAttribute("clients", clientService.getAllClients());
        return "client-list"; 
    }
}
