package mg.itu.amboariko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mg.itu.amboariko.model.Client;
import mg.itu.amboariko.repository.ClientRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getAllClients() {
        return (List<Client>) clientRepository.findAll();
    }

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    public Optional<Client> getClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    public List<Client> getClientsByReturnDate(LocalDate dateRetour) {
        return clientRepository.findClientsByReturnDate(dateRetour);
    }

    // Méthode pour mettre à jour un client
    public Client update(Client client) {
        Optional<Client> existingClient = clientRepository.findById(client.getIdClient());
        if (existingClient.isPresent()) {
            Client updatedClient = existingClient.get();
            updatedClient.setNom(client.getNom());
            updatedClient.setPrenom(client.getPrenom());
            updatedClient.setAdresse(client.getAdresse());
            updatedClient.setTelephone(client.getTelephone());
            updatedClient.setEmail(client.getEmail());
            // Sauvegarde le client mis à jour
            return clientRepository.save(updatedClient);
        } else {
            throw new RuntimeException("Client non trouvé avec l'ID : " + client.getIdClient());
        }
    }
}