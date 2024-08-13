package com.example.SCHapi.model.entity.Pessoa;

import com.example.SCHapi.model.entity.Usuario;

import javax.persistence.Entity;
// import javax.persistence.MappedSuperclass;
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
