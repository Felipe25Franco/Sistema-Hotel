package com.example.SCHapi.service.Quarto;

import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Pessoa.Hotel;
import com.example.SCHapi.model.entity.Quarto.Quarto;
import com.example.SCHapi.model.entity.Quarto.TipoQuarto;
import com.example.SCHapi.model.entity.Servico.Servico;
import com.example.SCHapi.model.entity.Servico.TipoServico;
import com.example.SCHapi.model.repository.Quarto.QuartoRepository;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;


@Service
public class QuartoService {

    private QuartoRepository repository;

    public QuartoService(QuartoRepository repository) {
        this.repository = repository;
    }

    public List<Quarto> getQuartos(){
        return repository.findAll();
    }

    public Optional<Quarto> getQuartoById(Long id) {
        return  repository.findById(id);
    }

    public List<Quarto> getQuartosByHotel(Optional<Hotel> hotel) {
        return repository.findByHotel(hotel);
    }

    public List<Quarto> getQuartoByTipoQuarto(Optional<TipoQuarto> TipoQuarto) {
        return repository.findByTipoQuarto(TipoQuarto);
    }


    @Transactional
    public Quarto salvar(Quarto quarto) {
        validar(quarto);
        return repository.save(quarto);
    }

    @Transactional
    public void excluir(Quarto quarto) {
        Objects.requireNonNull(quarto.getId());
        repository.delete(quarto);
    }


    public void validar(Quarto quarto) {

        Integer andar = quarto.getAndar();
        Integer numero = quarto.getNumero();

        if (quarto.getBloco() == null || quarto.getBloco().trim().equals("") || !quarto.getBloco().matches("^[A-Z]+$")){
            throw new RegraNegocioException("Bloco Invalido!!! Insira um bloco valido, letras de A a Z.");
        }
        if (quarto.getAndar() <= 0 || andar == null ) {
            throw new RegraNegocioException("Andar Invalido!!! Insira um andar valido.");
        }
        if (quarto.getNumero() <= 100 || quarto.getNumero() >= 10000 || numero == null) {
            throw new RegraNegocioException("Numero de quarto invalido insira um numero valido, a numeração dos quartos começam a partir do numero 101 e vai ate 9999.");
        }
        if (quarto.getHotel() == null || quarto.getHotel().getId() == null || quarto.getHotel().getId() == 0) {
            throw new RegraNegocioException("Hotel inválid0!!!!");
        }
        if (quarto.getTipoQuarto() == null || quarto.getTipoQuarto().getId() == null || quarto.getTipoQuarto().getId() == 0) {
            throw new RegraNegocioException("Tipo de quarto inválid0!!!!");
        }
        if (quarto.getStatusQuarto() == null || quarto.getStatusQuarto().getId() == null || quarto.getStatusQuarto().getId() == 0) {
            throw new RegraNegocioException("Status de quarto inválid0!!!!");
        }

        List<Quarto> quartos = getQuartoByTipoQuarto(Optional.of(quarto.getTipoQuarto()));
        if(quartos.stream().anyMatch((x) -> !quarto.getId().equals(x.getId())&&x.getNumero()==quarto.getNumero()&&x.getHotel().getId()==quarto.getHotel().getId()&&x.getBloco().trim().equals(quarto.getBloco().trim()))) {
            throw new RegraNegocioException("Título já cadastrado para a categoria, hotel e andar selecionados");
        }
    }

}





