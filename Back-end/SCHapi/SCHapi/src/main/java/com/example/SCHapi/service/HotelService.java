package com.example.SCHapi.service;

import com.example.SCHapi.model.entity.*;
import com.example.SCHapi.model.repository.HotelRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;



@Service
public class HotelService {

    private HotelRepository repository;

    public HotelService(HotelRepository repository) {
        this.repository = repository;
    }

    public List<Hotel> getHoteis() {
        return repository.findAll();
    }

    public Optional<Hotel> getHotelById(Long id) {
        return repository.findById(id);
    }
}