package com.example.restapp.repository;

import com.example.restapp.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PublisherRepository extends JpaRepository<Publisher, UUID> {

}
