package com.dsuperior.trabalhochapter1.service;

import com.dsuperior.trabalhochapter1.DTO.ClientDTO;
import com.dsuperior.trabalhochapter1.entities.Client;
import com.dsuperior.trabalhochapter1.repository.ClientRepository;
import com.dsuperior.trabalhochapter1.service.exceptions.DatabaseException;
import com.dsuperior.trabalhochapter1.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;


    @Transactional(readOnly = true)
    public Page<ClientDTO> findALl(PageRequest request){
        Page<Client> clients = repository.findAll(request);
        return clients.map(ClientDTO::new);
    }

    @Transactional(readOnly = true)
    public ClientDTO findByID(Long id){
        Optional<Client> obj = repository.findById(id);
        Client client = obj.orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
        return new ClientDTO(client);
    }

    @Transactional
    public ClientDTO insert(ClientDTO clientDTO){
        Client client = new Client();
        fromDTO(client, clientDTO);
        repository.save(client);
        return new ClientDTO(client);
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO clientDTO){
        try {
             Client client = repository.getOne(id);
             fromDTO(client, clientDTO);
             client = repository.save(client);
            return new ClientDTO(client);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Cliente não encontrado");
        }
    }

    @Transactional
    public void delete(Long id){
         try {
             repository.deleteById(id);
         }
         catch(DataIntegrityViolationException e){
             throw new DatabaseException("Não é possível deletar esse cliente");
         }
         catch(EmptyResultDataAccessException e){
             throw new ResourceNotFoundException("Cliente não encontrado");
         }
    }

    public void fromDTO(Client client, ClientDTO clientDTO){
        client.setName(clientDTO.getName());
        client.setCpf(clientDTO.getCpf());
        client.setIncome(clientDTO.getIncome());
        client.setBirthDate(clientDTO.getBirthDate());
        client.setChildren(clientDTO.getChildren());
    }
}
