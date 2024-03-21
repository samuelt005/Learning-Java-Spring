package com.apirest.apirest.interfaces;

import com.apirest.apirest.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
