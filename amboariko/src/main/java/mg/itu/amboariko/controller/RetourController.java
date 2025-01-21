package mg.itu.amboariko.controller;

import mg.itu.amboariko.model.Retour;
import mg.itu.amboariko.model.Client;
import mg.itu.amboariko.model.Ordinateur;
import mg.itu.amboariko.model.Reparation;
import mg.itu.amboariko.service.ClientService;
import mg.itu.amboariko.service.RetourService;
import mg.itu.amboariko.repository.CategorieOrdiRepository;
import mg.itu.amboariko.repository.OrdinateurRepository;
import mg.itu.amboariko.repository.ProblemeRepository;
import mg.itu.amboariko.repository.ReparationRepository;
import mg.itu.amboariko.repository.TypeReparationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class RetourController {

    @Autowired
    private RetourService retourService;

    @Autowired
    private ReparationRepository reparationRepository;

    @Autowired
    private TypeReparationRepository typeRepRepository;

    @Autowired
    private CategorieOrdiRepository categorieOrdiRepository;

    @Autowired
    private ProblemeRepository probRepo;

    @Autowired
    private ClientService clientService;
    @Autowired

    private OrdinateurRepository ordinateurRepository;

    // Afficher le formulaire d'édition d'un retour
    @GetMapping("/retours/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Retour retour = retourService.findById(id)
                .orElseThrow(() -> new RuntimeException("Retour non trouvé avec l'ID: " + id));
        model.addAttribute("retour", retour);
        model.addAttribute("body", "Retours/edit-retour");
        return "layout";
    }

    // Afficher les détails d'un retour
    @GetMapping("/retours/{id}/details")
    public String showRetourDetails(@PathVariable("id") Long id, Model model) {
        // Récupérer le retour par son ID
        Optional<Retour> retour = retourService.findById(id);
        if (retour.isEmpty()) {
            throw new RuntimeException("Retour non trouvé avec l'ID: " + id);
        }

        // Récupérer la réparation associée au retour
        Optional<Reparation> reparation = reparationRepository.findById(retour.get().getIdReparation());
        if (reparation.isEmpty()) {
            throw new RuntimeException("Réparation non trouvée pour le retour avec l'ID: " + id);
        }

        // Récupérer l'ordinateur associé à la réparation
        Optional<Ordinateur> ordinateur = ordinateurRepository.findById(reparation.get().getIdOrdinateur());
        if (ordinateur.isEmpty()) {
            throw new RuntimeException(
                    "Ordinateur non trouvé pour la réparation avec l'ID: " + reparation.get().getIdReparation());
        }

        // Récupérer le client associé à l'ordinateur
        Optional<Client> client = clientService.getClientById(ordinateur.get().getIdClient());
        if (client.isEmpty()) {
            throw new RuntimeException(
                    "Client non trouvé pour l'ordinateur avec l'ID: " + ordinateur.get().getIdOrdinateur());
        }

        // Ajouter les données au modèle
        model.addAttribute("retour", retour.get());
        model.addAttribute("reparation", reparation.get());
        model.addAttribute("ordinateur", ordinateur.get());
        model.addAttribute("client", client.get());
        model.addAttribute("body", "Retours/retour-details"); // Vue pour les détails du retour

        return "layout";
    }

    @DeleteMapping("/retours/{id}")
    public String deleteRetour(@PathVariable Long id) {
        try {
            retourService.deleteRetour(id);
            return "redirect:/retours";
        } catch (Exception e) {
            return "redirect:/retours";
        }
    }

    @PutMapping("/retours/{id}")
    public String updateRetour(@PathVariable Long id, @RequestParam String dateRetour, Model model) {
        try {
            LocalDate parsedDate = LocalDate.parse(dateRetour);
            retourService.updateRetour(id, parsedDate);
            model.addAttribute("message", "Retour mis à jour avec succès.");
        } catch (Exception e) {
            model.addAttribute("message", "Erreur lors de la mise à jour du retour: " + e.getMessage());
        }
        return "redirect:/retours"; // Rediriger vers la liste des retours
    }

    /**
     * Affiche la page d'insertion de retour avec les réparations disponibles
     */
    @GetMapping("/retours/new")
    public String showInsertForm(Model model) {
        List<Reparation> reparationsNonRetournees = reparationRepository.findReparationsNonRetournees();
        model.addAttribute("reparations", reparationsNonRetournees);
        model.addAttribute("body", "Retours/add-retour");
        return "layout";
    }

    /**
     * Soumettre le formulaire pour enregistrer le retour
     */
    @PostMapping("/retours")
    public String saveRetour(@RequestParam Long idReparation,
            @RequestParam String dateRetour, Model model) {
        try {
            Retour retour = Retour.builder()
                    .idReparation(idReparation)
                    .dateRetour(LocalDate.parse(dateRetour))
                    .build();

            retourService.saveRetourAndUpdateReparation(retour, idReparation);

            model.addAttribute("message", "Retour enregistré et réparation mise à jour avec succès.");
            model.addAttribute("body", "Retours/add-retour");
            return "layout";
        } catch (Exception e) {
            model.addAttribute("message", "Erreur lors de l'enregistrement du retour: " + e.getMessage());
            model.addAttribute("body", "Retours/add-retour");
            return "layout";
        }
    }

    @GetMapping("/retours")
    public String showRetoursPage(Model model,
            @RequestParam(required = false) Long categorieOrdi,
            @RequestParam(required = false) Long probleme,
            @RequestParam(required = false) Long typeReparation) {

        List<Retour> retours = retourService.findByCriteria(categorieOrdi, probleme, typeReparation);

        model.addAttribute("retours", retours);
        model.addAttribute("categorieOrdi", categorieOrdi);
        model.addAttribute("probleme", probleme);
        model.addAttribute("typeReparation", typeReparation);

        model.addAttribute("categories", categorieOrdiRepository.findAll());
        model.addAttribute("problemes", probRepo.findAll());
        model.addAttribute("typesReparation", typeRepRepository.findAll());

        model.addAttribute("body", "Retours/retour-list");

        return "layout";
    }
}
