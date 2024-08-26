package com.example.SCHapi.service.Estadia;

import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Estadia.Hospedagem;
import com.example.SCHapi.model.entity.Estadia.Reserva;
import com.example.SCHapi.model.entity.Estadia.StatusHospedagem;
import com.example.SCHapi.model.entity.Estadia.Lista.TipoQuartoReserva;
import com.example.SCHapi.model.entity.Pessoa.Hotel;
import com.example.SCHapi.model.repository.Estadia.ReservaRepository;
import com.example.SCHapi.service.Estadia.Lista.TipoQuartoReservaService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReservaService {
    
    private ReservaRepository repository;
    private final TipoQuartoReservaService tipoQuartoReservaService;
    private final HospedagemService hospedagemService;

    public ReservaService(ReservaRepository repository, TipoQuartoReservaService tipoQuartoReservaService, HospedagemService hospedagemService) {
        this.repository = repository;
        this.tipoQuartoReservaService = tipoQuartoReservaService;
        this.hospedagemService = hospedagemService;
    }

    public List<Reserva> getReservas(){
        return repository.findAll();
    }

    public Optional<Reserva> getReservaById(Long id) {
        return repository.findById(id);
    }

    // @Transactional
    // public Reserva salvar(Reserva reserva) {
    //     validar(reserva);
    //     return repository.save(reserva);
    // }

    @Transactional
    public Reserva salvarFull(Reserva reserva, List<TipoQuartoReserva> tipoQuartoReservas) {
        validar(reserva, tipoQuartoReservas);
        reserva = repository.save(reserva);
        // excluir a lista antiga de TipoQUarto
        List<TipoQuartoReserva> tipoQuartoReservasDel = tipoQuartoReservaService.getTipoQuartoReservaByReserva(Optional.of(reserva));
        // loop para cada elemento da lista deletar o tipoquartoreserva
        for (TipoQuartoReserva tipoQuartoReserva : tipoQuartoReservasDel) {
            //tipoQuartoReservaService.excluir(tipoQuartoReserva);
            if (!tipoQuartoReservas.stream().anyMatch((o)-> tipoQuartoReserva.getId().equals(o.getId()))){
                tipoQuartoReservaService.excluir(tipoQuartoReserva);
            }
        }
        // Salvar os novos TipoQUarto
        for (TipoQuartoReserva tipoQuartoReserva : tipoQuartoReservas) {
            tipoQuartoReserva.setReserva(reserva);
            tipoQuartoReservaService.salvar(tipoQuartoReserva);
        }
        return reserva;
    }

    @Transactional
    public void excluir(Reserva reserva) {
        Objects.requireNonNull(reserva.getId());
        List<TipoQuartoReserva> listaQuartos = tipoQuartoReservaService.getTipoQuartoReservaByReserva(Optional.of(reserva));
        //listaQuartos.stream().map(x->{tipoQuartoReservaService.excluir(x);});
        // loop para cada elemento da lista deletar o tipoquartoreserva
        for (TipoQuartoReserva tipoQuartoReserva : listaQuartos) {
            tipoQuartoReservaService.excluir(tipoQuartoReserva);
        }
        // encontrar o hotel e nullar a reserva respectiva
        Hospedagem hospedagem = hospedagemService.getHospedagemByReserva(Optional.of(reserva));
        if(hospedagem != null)
            hospedagem.setReserva(null);

        repository.delete(reserva);
    }


    public void validar(Reserva reserva, List<TipoQuartoReserva> tipoQuartoReservas) {

        Float valorReserva = reserva.getValorReserva();


        if (reserva.getDataInicio() == null || reserva.getDataInicio().trim().equals("") || !reserva.getDataInicio().matches("^\\d{4}-\\d{2}-\\d{2}$")){
             throw new RegraNegocioException("Data de inicio Invalido!!! Insira uma data valida yyyy/mm/dd.");
        }
        if (reserva.getDataFim() == null || reserva.getDataFim().trim().equals("") || !reserva.getDataFim().matches("^\\d{4}-\\d{2}-\\d{2}$")){
            throw new RegraNegocioException("data fim Invalido!!! Insira uma data valida dd/mm/yyy-hh:mm.");
        }
        if (valorReserva == null || valorReserva <= 0 ) {
            //System.out.println(valorReserva);
            throw new RegraNegocioException("Valor de reserva invalido, valor não pode ser menor ou igual a 0 ou nulo.");
        }

        if (reserva.getHotel() == null || reserva.getHotel().getId() == null || reserva.getHotel().getId() == 0) {
            throw new RegraNegocioException("Hotel inválid0!!!!");
        }
        if (reserva.getCliente() == null || reserva.getCliente().getId() == null || reserva.getCliente().getId() == 0) {
            throw new RegraNegocioException("Cliente inválid0!!!!");
        }
        if (reserva.getFuncionario() == null || reserva.getFuncionario().getId() == null || reserva.getFuncionario().getId() == 0) {
            throw new RegraNegocioException("Funcionario inválid0!!!!");
        }
        if (reserva.getStatusReserva() == null || reserva.getStatusReserva().getId() == null || reserva.getStatusReserva().getId() == 0) {
            throw new RegraNegocioException("Status de reserva inválid0!!!!");
        }

        // FALTA LISTA DE QUARTOS

        if (tipoQuartoReservas.isEmpty()) {
            throw new RegraNegocioException("Selecione pelo menos um quarto válido");
        }
    }
}
