package mg.itu.amboariko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mg.itu.amboariko.model.Client;
import mg.itu.amboariko.service.ClientService;
import java.util.List;

@Controller
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/clients/new")
    public String showAddClientForm(Model model) {
        model.addAttribute("client", new Client());
        model.addAttribute("body", "Client/add-client");
        return "layout";
    }

    @PostMapping("/clients")
    public String addClient(Client client) {
        clientService.save(client);
        return "redirect:/clients";
    }

    @GetMapping("/clients")
    public String getClients(@RequestParam(required = false) String dateRetour, Model model) {
        List<Client> clients;
        if (dateRetour != null && !dateRetour.isEmpty()) {
            clients = clientService.getClientsByReturnDate(dateRetour);
        } else {
            clients = clientService.getAllClients(); 
        }
        model.addAttribute("clients", clients);
        model.addAttribute("body", "Client/client-list");
        return "layout";
    }
}
