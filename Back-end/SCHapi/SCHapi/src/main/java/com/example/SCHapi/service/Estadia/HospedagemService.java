package com.example.SCHapi.service.Estadia;

import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.*;
import com.example.SCHapi.model.entity.Estadia.Hospedagem;
import com.example.SCHapi.model.entity.Estadia.Reserva;
import com.example.SCHapi.model.entity.Estadia.Lista.ProdutoSolicitado;
import com.example.SCHapi.model.entity.Estadia.Lista.QuartoHospedagem;
import com.example.SCHapi.model.entity.Estadia.Lista.ServicoSolicitado;
import com.example.SCHapi.model.entity.Estadia.Lista.TipoQuartoReserva;
import com.example.SCHapi.model.entity.Pessoa.Hotel;
import com.example.SCHapi.model.entity.Estadia.AvaliacaoQuarto;
import com.example.SCHapi.model.repository.Estadia.HospedagemRepository;
import com.example.SCHapi.service.Estadia.Lista.ProdutoSolicitadoService;
import com.example.SCHapi.service.Estadia.Lista.QuartoHospedagemService;
import com.example.SCHapi.service.Estadia.Lista.ServicoSolicitadoService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;



@Service
public class HospedagemService {

    private HospedagemRepository repository;
    private final QuartoHospedagemService quartoHospedagemService;
    private final ProdutoSolicitadoService produtoSolicitadoService;
    private final ServicoSolicitadoService servicoSolicitadoService;
    private final AvaliacaoQuartoService avaliacaoQuartoService;

    public HospedagemService(HospedagemRepository repository, QuartoHospedagemService quartoHospedagemService, ProdutoSolicitadoService produtoSolicitadoService, ServicoSolicitadoService servicoSolicitadoService, AvaliacaoQuartoService avaliacaoQuartoService) {
        this.repository = repository;
        this.quartoHospedagemService = quartoHospedagemService;
        this.produtoSolicitadoService = produtoSolicitadoService;
        this.servicoSolicitadoService = servicoSolicitadoService;
        this.avaliacaoQuartoService = avaliacaoQuartoService;
    }

    public List<Hospedagem> getHospedagens() {
        return repository.findAll();
    }

    public Optional<Hospedagem> getHospedagemById(Long id) {
        return repository.findById(id);
    }

    // essa query é pra retornar a lista de todos os tipos quarto
    public Hospedagem getHospedagemByReserva(Optional<Reserva> reserva) {
        return repository.findByReserva(reserva);
    }

    public List<Hospedagem> getHospedagemByHotel(Optional<Hotel> hotel) {
        return repository.findByHotel(hotel);
    }

    @Transactional
    public Hospedagem salvar(Hospedagem hospedagem) {
        validar(hospedagem);
        return repository.save(hospedagem);
    }

    @Transactional
    public void excluir(Hospedagem hospedagem) {
        Objects.requireNonNull(hospedagem.getId());
        List<QuartoHospedagem> listaQuartos = quartoHospedagemService.getQuartoHospedagemByHospedagem(Optional.of(hospedagem));
        // loop para cada elemento da lista deletar o quartohospedagem
        for (QuartoHospedagem quartoHospedagem : listaQuartos) {
            quartoHospedagemService.excluir(quartoHospedagem);
        }
        List<ProdutoSolicitado> produtoSolicitados = produtoSolicitadoService.getProdutoSolicitadoByHospedagem(Optional.of(hospedagem));
        // loop para cada elemento da lista deletar o produtosolicitado
        for (ProdutoSolicitado produtoSolicitado : produtoSolicitados) {
            produtoSolicitadoService.excluir(produtoSolicitado);
        }
        List<ServicoSolicitado> servicoSolicitados = servicoSolicitadoService.getServicoSolicitadoByHospedagem(Optional.of(hospedagem));
        // loop para cada elemento da lista deletar o servicosolicitado
        for (ServicoSolicitado servicoSolicitado : servicoSolicitados) {
            servicoSolicitadoService.excluir(servicoSolicitado);
        }
        List<AvaliacaoQuarto> avaliacaoQuartos = avaliacaoQuartoService.getAvaliacaoQuartoByHospedagem(Optional.of(hospedagem));
        // loop para cada elemento da lista deletar o avaliacaoquarto
        for (AvaliacaoQuarto avaliacaoQuarto : avaliacaoQuartos) {
            avaliacaoQuartoService.excluir(avaliacaoQuarto);
        }
        repository.delete(hospedagem);
    }


    public void validar(Hospedagem hospedagem) {

        Float valorEstadia = hospedagem.getValorEstadia();
        Float valotTotalPago = hospedagem.getValorTotalPago();


        // if (hospedagem.getDataInicio() == null || hospedagem.getDataInicio().trim().equals("") || !hospedagem.getDataInicio().matches("^\\d{2}/\\d{2}/\\d{4}-\\d{2}:\\d{2}$")){
        //     throw new RegraNegocioException("Data de inicio Invalido!!! Insira uma data valida dd/mm/yyy-hh:mm.");
        // }
        // if (hospedagem.getDataFim1() == null || hospedagem.getDataFim1().trim().equals("") || !hospedagem.getDataFim1().matches("^\\d{2}/\\d{2}/\\d{4}-\\d{2}:\\d{2}$")){
        //     throw new RegraNegocioException("data fim Invalido!!! Insira uma data valida dd/mm/yyy-hh:mm.");
        // }
        // if (hospedagem.getDataFim2() != null || !hospedagem.getDataFim1().trim().equals("")) {
        //     if(!hospedagem.getDataFim1().matches("^\\d{2}/\\d{2}/\\d{4}-\\d{2}:\\d{2}$")) {
        //         throw new RegraNegocioException("Data fim extendida Invalido!!! uma data valida dd/mm/yyy-hh:mm.");
        //     }
        // }
        if (hospedagem.getValorEstadia() <= 0 || valorEstadia == null) {
            throw new RegraNegocioException("O valor de estadia não pode ser menor ou igual a 0 ou nulo.");
        }
        if (hospedagem.getValorConsumo() < 0 ) {
            throw new RegraNegocioException("O valor de consumo não pode ser menor que 0..");
        }
        if (hospedagem.getValorServicos() < 0 ) {
            throw new RegraNegocioException("O valor de serviços não pode ser menor que 0.");
        }
        if (hospedagem.getValorEstadiaAdicional() < 0) {
            throw new RegraNegocioException("O valor de estadia adicional não pode ser menor que 0.");
        }
        if (valotTotalPago != null || valotTotalPago != 0) {
            if (hospedagem.getValorTotalPago() < 0) {
                throw new RegraNegocioException("O valor pago antecipado não pode ser menor que 0.");
            }
        }
        if (hospedagem.getCliente() == null || hospedagem.getCliente().getId() == null || hospedagem.getCliente().getId() == 0) {
            throw new RegraNegocioException("Cliente inválido!!!!");
        }
        if (hospedagem.getFuncionario() == null || hospedagem.getFuncionario().getId() == null || hospedagem.getFuncionario().getId() == 0) {
            throw new RegraNegocioException("Funcionario inválido!!!!");
        }
        if (hospedagem.getHotel() == null || hospedagem.getHotel().getId() == null || hospedagem.getHotel().getId() == 0) {
            throw new RegraNegocioException("Hotel inválido!!!!");
        }
        if (hospedagem.getStatusHospedagem() == null || hospedagem.getStatusHospedagem().getId() == null || hospedagem.getStatusHospedagem().getId() == 0) {
            throw new RegraNegocioException("Status de hospedagem inválido!!!!");
        }
        // if (hospedagem.getReserva() != null) {
        //     if (hospedagem.getAvaliacaoHospedagem().getId() == null || hospedagem.getAvaliacaoHospedagem().getId() == 0) {
        //         throw new RegraNegocioException("Reserva atrelada a hospedagem não pode ter id 0!!!");
        //     }
        // }
        // if (hospedagem.getAvaliacaoHospedagem() == null || hospedagem.getAvaliacaoHospedagem().getId() == null || hospedagem.getAvaliacaoHospedagem().getId() == 0) {
        //     throw new RegraNegocioException("Avaliação de hospedagem inválida!!!!");
        // }
        

        

    }
}