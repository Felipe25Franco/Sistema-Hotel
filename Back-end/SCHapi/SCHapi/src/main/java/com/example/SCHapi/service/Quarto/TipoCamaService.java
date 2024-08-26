package com.example.SCHapi.service.Quarto;

import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.model.entity.Estadia.StatusHospedagem;
import com.example.SCHapi.model.entity.Quarto.StatusQuarto;
import com.example.SCHapi.model.entity.Quarto.TipoCama;
import com.example.SCHapi.model.entity.Quarto.TipoComodidade;
import com.example.SCHapi.model.repository.Estadia.StatusHospedagemRepository;
import com.example.SCHapi.model.repository.Quarto.TipoCamaRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
public class TipoCamaService {

    private TipoCamaRepository repository;

    public TipoCamaService(TipoCamaRepository repository) {
        this.repository = repository;
    }

    public List<TipoCama> getTipoCamas(){
        return repository.findAll();
    }

    public Optional<TipoCama> getTipoCamaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public TipoCama salvar(TipoCama tipoCama) {
        validar(tipoCama);
        return repository.save(tipoCama);
    }

    @Transactional
    public void excluir(TipoCama tipoCama) {
        Objects.requireNonNull(tipoCama.getId());
        repository.delete(tipoCama);
    }

    public void validar(TipoCama tipoCama) {

        Integer ocupantes = tipoCama.getOcupantes();

        if (tipoCama.getTitulo() == null || tipoCama.getTitulo().trim().equals("")){
            throw new RegraNegocioException("Titulo Invalido!!! Insira um titulo valido.");
        }
        if (tipoCama.getDescricao() == null || tipoCama.getDescricao().trim().equals("")) {
            throw new RegraNegocioException("Descrição Invalida!!! Insira uma descrição valida.");
        }
        if (ocupantes <= 0 || ocupantes == null) {
            throw new RegraNegocioException("A quantidade de ocupupantes em uma cama tem que ser maior que 0.");
        }
        List<TipoCama> tipoCamas = getTipoCamas();
        if(tipoCamas.stream().anyMatch((x) -> {return x.getTitulo().trim().equals(tipoCama.getTitulo().trim())&&!tipoCama.getId().equals(x.getId());})) {
            throw new RegraNegocioException("Título já cadastrado");
        }
    }
}
