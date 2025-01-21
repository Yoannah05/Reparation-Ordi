package mg.itu.amboariko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import mg.itu.amboariko.model.Client;
import mg.itu.amboariko.model.Ordinateur;
import mg.itu.amboariko.model.Reparation;
import mg.itu.amboariko.model.Retour;
import mg.itu.amboariko.repository.OrdinateurRepository;
import mg.itu.amboariko.repository.ReparationRepository;
import mg.itu.amboariko.repository.RetourRepository;
import mg.itu.amboariko.service.ClientService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ClientController {

    @Autowired
    private final ClientService clientService;
    @Autowired
    private OrdinateurRepository ordinateurRepository;
    @Autowired
    private ReparationRepository reparationRepository;
    @Autowired
        private RetourRepository retourRepository;
    
    
        
        public ClientController(ClientService clientService, OrdinateurRepository ordrep, ReparationRepository rerep, RetourRepository retrep) {
            this.clientService = clientService;
            this.ordinateurRepository = ordrep;
            this.reparationRepository = rerep;
            this.retourRepository = retrep;
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
        public String getClients(
                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateRetour,
                Model model) {
            List<Client> clients;
            if (dateRetour != null) {
                clients = clientService.getClientsByReturnDate(dateRetour);
            } else {
                clients = clientService.getAllClients();
            }
            model.addAttribute("clients", clients);
            model.addAttribute("body", "Client/client-list");
            return "layout";
        }
    
        @GetMapping("/clients/edit/{id}")
        public String showUpdateClientForm(@PathVariable("id") Long id, Model model) {
            Optional<Client> client = clientService.getClientById(id);
            model.addAttribute("client", client); // Ajoute le client à modifier au modèle
            model.addAttribute("body", "Client/add-client"); // Utilise la même vue que pour l'ajout
            return "layout";
        }
    
        @PostMapping("/clients/update/{id}")
        public String updateClient(@PathVariable("id") Long id, Client client) {
            client.setIdClient(id); // Assurez-vous que l'ID est correctement défini
            clientService.update(client);
            return "redirect:/clients";
        }
    
        @GetMapping("/clients/{id}/details")
        public String showClientDetails(@PathVariable("id") Long id, Model model) {
            // Récupérer le client par son ID
            Optional<Client> client = clientService.getClientById(id);
            if (client.isEmpty()) {
                throw new RuntimeException("Client non trouvé avec l'ID: " + id);
            }
    
            // Récupérer les ordinateurs associés à ce client
            List<Ordinateur> ordinateurs = ordinateurRepository.findByClientId(id);
    
            // Récupérer les réparations et retours pour chaque ordinateur
            List<Reparation> reparations = new ArrayList<>();
            List<Retour> retours = new ArrayList<>();
    
            for (Ordinateur ordinateur : ordinateurs) {
                List<Reparation> reparationsOrdi = reparationRepository.findByOrdinateurId(ordinateur.getIdOrdinateur());
                reparations.addAll(reparationsOrdi);
    
                for (Reparation reparation : reparationsOrdi) {
                    List<Retour> retoursReparation = retourRepository.findByReparationId(reparation.getIdReparation());
                retours.addAll(retoursReparation);
            }
        }

        // Ajouter les données au modèle
        model.addAttribute("client", client.get());
        model.addAttribute("ordinateurs", ordinateurs);
        model.addAttribute("reparations", reparations);
        model.addAttribute("retours", retours);
        model.addAttribute("body", "Client/client-details"); // Vue pour les détails du client

        return "layout";
    }
}