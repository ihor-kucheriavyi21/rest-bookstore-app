package com.example.restapp.service;

import com.example.restapp.model.Publisher;
import com.example.restapp.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherService {

    @Autowired
    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public List<Publisher> findAll(){
        return publisherRepository.findAll();
    }

    public Publisher savePublisher(Publisher publisher){
        return publisherRepository.save(publisher);
    }

}
