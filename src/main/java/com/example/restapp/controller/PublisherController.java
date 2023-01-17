package com.example.restapp.controller;

import com.example.restapp.model.Publisher;
import com.example.restapp.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/publisher")
public class PublisherController {

    @Autowired
    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Publisher>> getAllPublishers() {
        return new ResponseEntity<>(publisherService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Publisher> createPublisher(Publisher publisher) {
        return new ResponseEntity<>(publisherService.savePublisher(publisher), HttpStatus.CREATED);
    }
}
