package com.example.SCHapi.service.Quarto;


import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Estadia.Hospedagem;
import com.example.SCHapi.model.entity.Estadia.Reserva;
import com.example.SCHapi.model.entity.Estadia.Lista.TipoQuartoReserva;
import com.example.SCHapi.model.entity.Pessoa.Hotel;
import com.example.SCHapi.model.entity.Produto.Produto;
import com.example.SCHapi.model.entity.Quarto.Quarto;
import com.example.SCHapi.model.entity.Quarto.TipoCama;
import com.example.SCHapi.model.entity.Quarto.TipoQuarto;
import com.example.SCHapi.model.entity.Quarto.Lista.ComodidadeTipoQuarto;
import com.example.SCHapi.model.entity.Quarto.Lista.TipoCamaTipoQuarto;
import com.example.SCHapi.model.repository.Quarto.TipoQuartoRepository;
import com.example.SCHapi.service.Quarto.Lista.ComodidadeTipoQuartoService;
import com.example.SCHapi.service.Quarto.Lista.TipoCamaTipoQuartoService;

import org.hibernate.mapping.Collection;
import org.hibernate.mapping.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TipoQuartoService {

    private TipoQuartoRepository repository;
    private final QuartoService quartoService;
    private final TipoCamaTipoQuartoService tipoCamaTipoQuartoService;
    private final ComodidadeTipoQuartoService comodidadeTipoQuartoService;

    public TipoQuartoService(TipoQuartoRepository repository, QuartoService quartoService, TipoCamaTipoQuartoService tipoCamaTipoQuartoService, ComodidadeTipoQuartoService comodidadeTipoQuartoService) {
        this.repository = repository;
        this.quartoService = quartoService;
        this.tipoCamaTipoQuartoService = tipoCamaTipoQuartoService;
        this.comodidadeTipoQuartoService = comodidadeTipoQuartoService;
    }

    public List<TipoQuarto> getTipoQuartos(){
        return repository.findAll();
    }

    public Optional<TipoQuarto> getTipoQuartoById(Long id) {
        return repository.findById(id);
    }

    public List<TipoQuarto> getTipoQuartosByHotel(Optional<Hotel> hotel) {
        List<Quarto> quartos = quartoService.getQuartosByHotel(hotel);
        HashSet<TipoQuarto> tipoQuartos = new HashSet<TipoQuarto>();
        for (Quarto quarto : quartos) {
            tipoQuartos.add(quarto.getTipoQuarto());
        }
        return List.copyOf(tipoQuartos);
        // List<TipoQuarto> quartosCopy = List.copyOf(getTipoQuartos());
        // quartosCopy.removeAll(quartos.stream().map(x->x.getTipoQuarto()).collect(Collectors.toList()));
        // return quartosCopy;


    }

    // @Transactional
    // public TipoQuarto salvar(TipoQuarto tipoQuarto) {
    //     validar(tipoQuarto);
    //     return repository.save(tipoQuarto);
    // }

    @Transactional
    public TipoQuarto salvarFull(TipoQuarto tipoquarto, List<TipoCamaTipoQuarto> tipoCamaTipoQuartos, List<ComodidadeTipoQuarto> comodidadeTipoQuartos) {
        validar(tipoquarto, tipoCamaTipoQuartos, comodidadeTipoQuartos);
        tipoquarto = repository.save(tipoquarto);

        // excluir a lista antiga de 
        List<TipoCamaTipoQuarto> tipoCamaTipoQuartosDel = tipoCamaTipoQuartoService.getTipoCamaTipoQuartoByTipoQuarto(Optional.of(tipoquarto));
        // loop para cada elemento da lista deletar o tipocamatipoquarto
        for (TipoCamaTipoQuarto tipoCamaTipoQuarto : tipoCamaTipoQuartosDel) {
            //tipoCamaTipoQuartoService.excluir(tipoCamaTipoQuarto);
            if (!tipoCamaTipoQuartos.stream().anyMatch((o)-> tipoCamaTipoQuarto.getId().equals(o.getId()))){
                tipoCamaTipoQuartoService.excluir(tipoCamaTipoQuarto);
            }
        }
        // Salvar os novos 
        for (TipoCamaTipoQuarto tipoCamaTipoQuarto : tipoCamaTipoQuartos) {
            tipoCamaTipoQuarto.setTipoQuarto(tipoquarto);
            tipoCamaTipoQuartoService.salvar(tipoCamaTipoQuarto);
        }
        
        // excluir a lista antiga de 
        List<ComodidadeTipoQuarto> listaQuartos = comodidadeTipoQuartoService.getComodidadeTipoQuartoByTipoQuarto(Optional.of(tipoquarto));
        // loop para cada elemento da lista deletar o comodidadetipoquarto
        for (ComodidadeTipoQuarto comodidadeTipoQuarto : listaQuartos) {
            //comodidadeTipoQuartoService.excluir(comodidadeTipoQuarto);
            if (!comodidadeTipoQuartos.stream().anyMatch((o)-> comodidadeTipoQuarto.getId().equals(o.getId()))){
                comodidadeTipoQuartoService.excluir(comodidadeTipoQuarto);
            }
        }
        // Salvar os novos 
        for (ComodidadeTipoQuarto comodidadeTipoQuarto : comodidadeTipoQuartos) {
            comodidadeTipoQuarto.setTipoQuarto(tipoquarto);
            comodidadeTipoQuartoService.salvar(comodidadeTipoQuarto);
        }
        return tipoquarto;
    }


    @Transactional
    public void excluir(TipoQuarto tipoQuarto) {
        Objects.requireNonNull(tipoQuarto.getId());
        repository.delete(tipoQuarto);
    }


    public void validar(TipoQuarto tipoQuarto, List<TipoCamaTipoQuarto> tipoCamaTipoQuartos, List<ComodidadeTipoQuarto> comodidadeTipoQuartos) {

        Integer limiteAdultos = tipoQuarto.getLimiteAdultos();
        Integer diasCancelarReserva = tipoQuarto.getDiasCancelarReserva();

        Float precoBase = tipoQuarto.getPrecoBase();
        Float avaliacaoMedia = tipoQuarto.getAvaliacaoMedia();
        Float area = tipoQuarto.getArea();


        if (tipoQuarto.getTitulo() == null || tipoQuarto.getTitulo().trim().equals("")){
            throw new RegraNegocioException("Titulo Invalido!!! Insira um titulo valido.");
        }
        if (tipoQuarto.getDescricao() == null || tipoQuarto.getDescricao().trim().equals("")) {
            throw new RegraNegocioException("Descrição Invalida!!! Insira uma descrição valida.");
        }
        if (tipoQuarto.getLimiteAdultos() <= 0 || limiteAdultos == null) {
            throw new RegraNegocioException("A quantidade de limite de adulto tem que ser maior que 0.");
        }
        if (tipoQuarto.getLimiteCriancas() < 0 ) {
            throw new RegraNegocioException("A quantidade de limite de crianças não pode ser menor que 0.");
        }

        if (tipoQuarto.getPrecoBase() < 0 || precoBase == null) {
            throw new RegraNegocioException("O preço de um tipo de quarto tem que ser maior que 0.");
        }
        // if (tipoQuarto.getAvaliacaoMedia() <= 0 || avaliacaoMedia == null) {
        //     throw new RegraNegocioException("O preço de um tipo de quarto tem que ser maior que 0.");
        // }
        if (tipoQuarto.getArea() <= 0 || area == null) {
            throw new RegraNegocioException("A area do quarto não pode ser menor ou igual a 0.");
        }
        if (tipoQuarto.getDiasCancelarReserva() <= 0 || diasCancelarReserva == null) {
            throw new RegraNegocioException("A quantidade de dias para se cancelar a reserva tem que ser maior que 0.");
        }

        // NÃO FEITO A DE LISTA
        List<TipoQuarto> tipoQuartos = getTipoQuartos();
        if(tipoQuartos.stream().anyMatch((x) -> {return x.getTitulo().trim().equals(tipoQuarto.getTitulo().trim())&&!tipoQuarto.getId().equals(x.getId());})) {
            throw new RegraNegocioException("Título já cadastrado");
        }

    }
}
