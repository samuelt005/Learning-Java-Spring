package com.apirest.apirest.controllers;

import com.apirest.apirest.interfaces.ClientRepository;
import com.apirest.apirest.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping
    public List<Client> listar() {
        return clientRepository.findAll();
    }

    @PostMapping
    public Client adicionar(@RequestBody Client client) {
        return clientRepository.save(client);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        clientRepository.deleteById(id);
    }
}
