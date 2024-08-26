package com.example.SCHapi.service.Pessoa;

import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.repository.Pessoa.HotelRepository;
import com.example.SCHapi.service.Estadia.HospedagemService;
import com.example.SCHapi.service.Estadia.Lista.QuartoHospedagemService;
import com.example.SCHapi.model.entity.*;
import com.example.SCHapi.model.entity.Estadia.Hospedagem;
import com.example.SCHapi.model.entity.Pessoa.Cliente;
import com.example.SCHapi.model.entity.Pessoa.Hotel;
import com.example.SCHapi.model.entity.Pessoa.Uf;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
public class HotelService {

    private HotelRepository repository;
    private final HospedagemService hospedagemService;

    public HotelService(HotelRepository repository, HospedagemService hospedagemService) {
        this.repository = repository;
        this.hospedagemService = hospedagemService;
    }

    public List<Hotel> getHoteis() {
        return repository.findAll();
    }

    public Optional<Hotel> getHotelById(Long id) {
        return repository.findById(id);
    }

    public Optional<Hotel> getAvaliacoesHospedagensByHotel(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Hotel salvar(Hotel hotel) {
        validar(hotel);
        return repository.save(hotel);
    }

    @Transactional
    public void excluir(Hotel hotel) {
        Objects.requireNonNull(hotel.getId());
        repository.delete(hotel);
    }


    public void validar(Hotel hotel) {

        Float avaliacaoMedia = hotel.getAvaliacaoMedia();
        String telefone1 = hotel.getTelefone1();
        String telefone2 = hotel.getTelefone2();
        Integer numero = hotel.getEndereco().getNumero();
        String cep = hotel.getEndereco().getCep();

        if (hotel.getTitulo() == null || hotel.getTitulo().trim().equals("")){
            throw new RegraNegocioException("Titulo Invalido!!! Insira um titulo valido.");
        }
        if (hotel.getDescricao() == null || hotel.getDescricao().trim().equals("")) {
            throw new RegraNegocioException("Descrição Invalida!!! Insira uma descrição valida.");
        }
        if (avaliacaoMedia == null || (avaliacaoMedia < 0 || avaliacaoMedia > 5 )) {
            throw new RegraNegocioException("A avaliação media do hotel tem que estar entre 0 e 5 e não pode ser nula, avaliação media invalida.");
        }
        // if (!String.valueOf(avaliacaoMedia).matches("^\\d+(\\.\\d+)?$")) {
        //     throw new RegraNegocioException("A avaliação média do hotel deve conter apenas números e ponto decimal.");
        // }
        if (telefone1 != null) {
            telefone1 = telefone1.replaceAll("[()\\-]", ""); // Remove parênteses e traços
            if (telefone1.length() != 12 && telefone1.length() != 13) {
                throw new RegraNegocioException("O telefone 1 não pode estar nulo e deve ter 12 ou 13 dígitos.");
            }
        }
        if (telefone2 != null) {
            telefone2 = telefone2.replaceAll("[()\\-]", ""); // Remove parênteses e traços
            if (telefone2.length() != 12 && telefone2.length() != 13) {
                throw new RegraNegocioException("O telefone 2 não pode estar nulo e deve ter 12 ou 13 dígitos.");
            }
        }
        if (hotel.getEndereco().getLogradouro() == null || hotel.getEndereco().getLogradouro().trim().equals("")){
            throw new RegraNegocioException("Logradouro Invalido!!! Insira um Logradouro valido.");
        }
        if (hotel.getEndereco().getNumero() <= 0 || numero == null ) {
            throw new RegraNegocioException("Numero Invalido!!! O numero de endereço tem que ser maior que 0 e não pode ser nulo.");
        }
        if (hotel.getEndereco().getBairro() == null || hotel.getEndereco().getBairro().trim().equals("")){
            throw new RegraNegocioException("Bairro Invalido!!! Insira um Bairro valido.");
        }
        if (hotel.getEndereco().getCidade() == null || hotel.getEndereco().getCidade().trim().equals("")){
            throw new RegraNegocioException("Cidade Invalida!!! Insira uma Cidade valida.");
        }
        if (hotel.getEndereco().getCep() == null || hotel.getEndereco().getCep().trim().equals("")){
            throw new RegraNegocioException("CEP InvalidO!!! Insira uma CEP valido.");
        }
        cep = cep.replaceAll("\\s|-", "");       
        if (cep.length() != 8) {
            throw new RegraNegocioException("CEP Inválido!!! O CEP deve ter exatamente 8 dígitos.");
        }
        if (hotel.getEndereco().getUf() == null || hotel.getEndereco().getUf().getId() == null || hotel.getEndereco().getUf().getId() == 0) {
            throw new RegraNegocioException("Uf inválido!!!!");
        }
        if (hotel.getEndereco().getUf().getPais() == null || hotel.getEndereco().getUf().getPais().getId() == null || hotel.getEndereco().getUf().getPais().getId() == 0) {
            throw new RegraNegocioException("Pais inválido!!!!");
        }

        

        List<Hotel> hotels = getHoteis();
        if(hotels.stream().anyMatch((x) -> {return !hotel.getId().equals(x.getId())&&x.getTitulo().trim().equals(hotel.getTitulo().trim());})) {
            throw new RegraNegocioException("título já cadastrado");
        }
        
    }

    // metodos adicionais

    public Float getMediaAvaliacao(Optional<Hotel> hotel){
        List<Hospedagem> hospedagens = hospedagemService.getHospedagemByHotel(hotel);
        //hospedagens.stream().map(x->x.getAvaliacaoHospedagem().getNota()).toList();
        Float avaliacaoMedia = (float)0;
        Float i = (float)0;
        for(Hospedagem hospedagem : hospedagens) {
            if (hospedagem.getAvaliacaoHospedagem().getNota() > 0) {
                avaliacaoMedia += hospedagem.getAvaliacaoHospedagem().getNota();
                i++;
            }
        }
        //System.out.println(hospedagens);
        //System.out.println(hospedagens.stream());
        return avaliacaoMedia/i;
    }

}