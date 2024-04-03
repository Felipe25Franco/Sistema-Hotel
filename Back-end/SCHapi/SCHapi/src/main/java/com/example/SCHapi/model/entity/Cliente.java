package com.example.SCHapi.model.entity;

import jakarta.persistence.Entity;
// import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente extends Usuario{

    private String descricao;
}
