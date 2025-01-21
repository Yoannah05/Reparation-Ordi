package mg.itu.amboariko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import mg.itu.amboariko.model.VModelTechnicienCommission;
import mg.itu.amboariko.model.Technicien;
import mg.itu.amboariko.model.Reparation;
import mg.itu.amboariko.model.Retour;
import mg.itu.amboariko.repository.OrdinateurRepository;
import mg.itu.amboariko.repository.ReparationRepository;
import mg.itu.amboariko.repository.RetourRepository;
import mg.itu.amboariko.service.TechnicienService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class TechnicienController {

    @Autowired
    private TechnicienService technicienService;

    @GetMapping("/commissions")
    public String getSommeCommissions(
            @RequestParam(required = false) Long idTechnicien,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date1,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date2,
            Model model) {  // Add Model parameter here

        List<VModelTechnicienCommission> vmodel =  technicienService.getTechnicienCommmission(idTechnicien, date1, date2);
        double sommeCommissions = technicienService.calculerSommeCommissions(vmodel);

        List<Technicien> techniciens = technicienService.getAllTechniciens();

        // Ajout des données au modèle
        model.addAttribute("sommeCommissions", sommeCommissions);
        model.addAttribute("vmodel", vmodel);
        model.addAttribute("techniciens", techniciens);
        model.addAttribute("body", "Technicien/commissions");
        return "layout";
    }
}
