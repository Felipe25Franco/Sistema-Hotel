package com.example.SCHapi.api.controller;

import org.modelmapper.ModelMapper;
import com.example.SCHapi.api.dto.HotelDTO;
import com.example.SCHapi.model.entity.Hotel;

public class HotelController {
    public Hotel converter(HotelDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Hotel.class);
    }
}