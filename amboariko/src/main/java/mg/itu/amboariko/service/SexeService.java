package mg.itu.amboariko.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mg.itu.amboariko.model.Sexe;
import mg.itu.amboariko.repository.SexeRepository;


@Service
public class SexeService {
    @Autowired
    private SexeRepository sexeRepository;

    public Sexe getSexeById(Long id) {
        return sexeRepository.findById(id).orElse(null);
    }

    public List<Sexe> getAllSexe() {
        Iterable<Sexe> sexeIterable = sexeRepository.findAll();
        List<Sexe> sexe = new ArrayList<>();
        sexeIterable.forEach(sexe::add);
        return sexe;
    }
}
