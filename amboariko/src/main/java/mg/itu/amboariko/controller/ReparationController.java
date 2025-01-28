package mg.itu.amboariko.controller;

import mg.itu.amboariko.model.Client;
import mg.itu.amboariko.model.Composant;
import mg.itu.amboariko.model.ComposantsUtilises;
import mg.itu.amboariko.model.Ordinateur;
import mg.itu.amboariko.model.Probleme;
import mg.itu.amboariko.model.Reparation;
import mg.itu.amboariko.model.Technicien;
import mg.itu.amboariko.repository.ClientRepository;
import mg.itu.amboariko.repository.CommissionRepository;
import mg.itu.amboariko.repository.ComposantRepository;
import mg.itu.amboariko.repository.ComposantUtiliseRepository;
import mg.itu.amboariko.repository.OrdinateurRepository;
import mg.itu.amboariko.repository.ProblemeRepository;
import mg.itu.amboariko.repository.ReparationRepository;
import mg.itu.amboariko.repository.TypeReparationRepository;
import mg.itu.amboariko.repository.TechnicienRepository; // Add this import
import mg.itu.amboariko.service.TechnicienService; // Add this import
import mg.itu.amboariko.service.CommissionService; // Add this import
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReparationController {

    @Autowired
    private ReparationRepository reparationRepository;

    @Autowired
    private OrdinateurRepository ordinateurRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProblemeRepository problemeRepository;

    @Autowired
    private TypeReparationRepository typeReparationRepository;

    @Autowired
    private ComposantRepository composantRepository;

    @Autowired
    private ComposantUtiliseRepository composantUtiliseRepository;

    @Autowired
    private TechnicienRepository technicienRepository;

    @Autowired // Add this line
    private TechnicienService technicienService;

    @Autowired // Add this line
    private CommissionRepository commissionRepository;

    // Afficher la liste des réparations avec filtres
    @GetMapping("/reparations")
    public String listReparations(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin,
            @RequestParam(required = false) Long idProbleme,
            @RequestParam(required = false) Long idTypeReparation,
            Model model) {

        List<Reparation> reparations;

        if (dateDebut != null && dateFin != null) {
            reparations = reparationRepository.findByDateDebutBetween(dateDebut, dateFin);
        } else if (idProbleme != null) {
            reparations = reparationRepository.findByProbleme(idProbleme);
        } else if (idTypeReparation != null) {
            reparations = reparationRepository.findByTypeReparation(idTypeReparation);
        } else {
            reparations = (List<Reparation>) reparationRepository.findAll();
        }

        model.addAttribute("reparations", reparations);
        model.addAttribute("problemes", problemeRepository.findAll());
        model.addAttribute("typesReparation", typeReparationRepository.findAll());
        model.addAttribute("techniciens", technicienService.getAllTechniciens()); // Fixed
        model.addAttribute("body", "Reparation/reparation-list");

        return "layout";
    }

    // Afficher les détails d'une réparation
    @GetMapping("/reparations/{id}/details")
    public String showReparationDetails(@PathVariable Long id, Model model) {
        Reparation reparation = reparationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réparation non trouvée avec l'ID: " + id));

        Ordinateur ordinateur = ordinateurRepository.findById(reparation.getIdOrdinateur())
                .orElseThrow(() -> new RuntimeException("Ordinateur non trouvé avec l'ID: " + reparation.getIdOrdinateur()));

        Client client = clientRepository.findById(ordinateur.getIdClient())
                .orElseThrow(() -> new RuntimeException("Client non trouvé avec l'ID: " + ordinateur.getIdClient()));

        List<Probleme> problemes = problemeRepository.findProbByReparation(id);
        List<Composant> composantsUtilises = composantRepository.findByReparationId(id);
        List<Technicien> techniciens = technicienRepository.findTechnicienById(id); // Ensure this returns valid Technicien objects

        model.addAttribute("reparation", reparation);
        model.addAttribute("ordinateur", ordinateur);
        model.addAttribute("client", client);
        model.addAttribute("problemes", problemes);
        model.addAttribute("composantsUtilises", composantsUtilises);
        model.addAttribute("techniciens", techniciens);
        model.addAttribute("body", "Reparation/reparation-details");

        return "layout";
    }

    // Rediriger vers l'insertion d'un retour avec l'ID de la réparation pré-rempli
    @GetMapping("/reparations/{id}/retourner")
    public String redirectToRetour(@PathVariable Long id) {
        return "redirect:/retours/new?idReparation=" + id;
    }

    // Afficher le formulaire pour réparer (choisir les composants utilisés)
    @GetMapping("/reparations/{id}/reparer")
    public String showReparerForm(@PathVariable Long id, Model model) {
        Reparation reparation = reparationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réparation non trouvée avec l'ID: " + id));

        List<Composant> composants = (List<Composant>) composantRepository.findAll();

        model.addAttribute("reparation", reparation);
        model.addAttribute("composants", composants);
        model.addAttribute("body", "Reparation/reparer-form");

        return "layout";
    }

    // Supprimer une réparation
    @DeleteMapping("/reparations/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteReparation(@PathVariable Long id) {
        try {
            reparationRepository.deleteById(id);
            return ResponseEntity.ok("Réparation supprimée avec succès.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la suppression de la réparation: " + e.getMessage());
        }
    }

    @GetMapping("/reparations/new")
    public String showAddReparationForm(Model model) {
        model.addAttribute("reparation", new Reparation());
        model.addAttribute("ordinateurs", ordinateurRepository.findAll());
        model.addAttribute("problemes", problemeRepository.findAll());
        model.addAttribute("typesReparation", typeReparationRepository.findAll());
        model.addAttribute("composants", composantRepository.findAll());
        model.addAttribute("techniciens", technicienService.getAllTechniciens()); // Fixed
        model.addAttribute("commissions", commissionRepository.findAll());
        model.addAttribute("body", "Reparation/add-reparation");
        return "layout";
    }

    @PostMapping("/reparations")
    public String addReparation(
            @ModelAttribute Reparation reparation,
            @RequestParam("composants[]") List<Long> composants,
            @RequestParam("quantites[]") List<Integer> quantites,
            Model model) {

        // Enregistrer la réparation
        Reparation savedReparation = reparationRepository.save(reparation);

        // Enregistrer les composants utilisés avec leurs quantités
        for (int i = 0; i < composants.size(); i++) {
            ComposantsUtilises composantUtilise = new ComposantsUtilises();
            composantUtilise.setIdRepOrdi(savedReparation.getIdReparation()); // Référence à la réparation
            composantUtilise.setIdComposant(composants.get(i)); // ID du composant
            composantUtilise.setQuantiteUtilisee(quantites.get(i)); // Quantité utilisée
            composantUtiliseRepository.save(composantUtilise);
        }

        return "redirect:/reparations";
    }
}