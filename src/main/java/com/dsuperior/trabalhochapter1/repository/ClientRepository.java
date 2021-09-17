package com.dsuperior.trabalhochapter1.repository;

import com.dsuperior.trabalhochapter1.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
}
