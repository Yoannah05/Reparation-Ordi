package mg.itu.amboariko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import mg.itu.amboariko.model.Client;
import mg.itu.amboariko.service.ClientService;

@Controller
public class ClientController {

    @Autowired
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

     @GetMapping("/add")
    public String showAddClientForm(Model model) {
        model.addAttribute("client", new Client());
        return "Client/add-client";
    }

    @PostMapping("/add")
    public String addClient(Client client) {
        clientService.save(client);
        return "redirect:/clients";
    }

    @GetMapping("/clients")
    public String getClients(Model model) {
        model.addAttribute("clients", clientService.getAllClients());
        return "Client/client-list"; 
    }
}
