package com.example.SCHapi.model.entity.Pessoa;


import javax.persistence.Entity;
// import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente extends Pessoa {

    private String descricao;
}
