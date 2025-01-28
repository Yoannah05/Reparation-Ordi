package mg.itu.amboariko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mg.itu.amboariko.model.VModelTechnicienCommission;
import mg.itu.amboariko.model.Technicien;
import mg.itu.amboariko.model.Sexe;
import mg.itu.amboariko.service.TechnicienService;
import mg.itu.amboariko.service.SexeService;

import java.time.LocalDate;
import java.util.List;

@Controller
public class TechnicienController {

    @Autowired
    private TechnicienService technicienService;

    @Autowired
    private SexeService sexeService;

    @GetMapping("/commissions")
    public String getSommeCommissions(
            @RequestParam(required = false) Long idTechnicien,
            @RequestParam(required = false) Long idSexe,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date1,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date2,
            Model model) {

        List<VModelTechnicienCommission> vmodel = null;
        if (idTechnicien != null && idTechnicien != 0) {
            vmodel = technicienService.getTechnicienCommission(idTechnicien, date1, date2);
        } else {
            vmodel = technicienService.getTechnicienCommissionBySexe(idSexe, date1, date2);
        }

        double sommeCommissions = technicienService.calculerSommeCommissions(vmodel);
        List<Technicien> techniciens = technicienService.getAllTechniciens();
        List<Sexe> sexe = sexeService.getAllSexe();

        // Ajout des données au modèle
        model.addAttribute("sommeCommissions", sommeCommissions);
        model.addAttribute("vmodel", vmodel);
        model.addAttribute("techniciens", techniciens);
        model.addAttribute("sexe", sexe);
        model.addAttribute("body", "Technicien/commissions");
        return "layout";
    }
}
