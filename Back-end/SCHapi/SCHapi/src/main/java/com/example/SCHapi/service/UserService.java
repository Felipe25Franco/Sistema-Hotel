package com.example.SCHapi.service;

import com.example.SCHapi.exception.RegraNegocioException;
import com.example.SCHapi.exception.SenhaInvalidaException;

import com.example.SCHapi.model.entity.User;
import com.example.SCHapi.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository repository;

    public List<User> getUsers() {
        return repository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public User salvar(User user){
        validar(user);
        return repository.save(user);
    }

    public UserDetails autenticar(User user){
        UserDetails user2 = loadUserByUsername(user.getLogin());
        boolean senhasBatem = encoder.matches(user.getSenha(), user2.getPassword());

        if (senhasBatem){
            return user2;
        }
        throw new SenhaInvalidaException();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = repository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        String[] roles = user.isAdmin()
                ? new String[]{"ADMIN", "USER"}
                : new String[]{"USER"};

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getLogin())
                .password(user.getSenha())
                .roles(roles)
                .build();
    }

    @Transactional
    public void excluir(User user) {
        Objects.requireNonNull(user.getId());
        repository.delete(user);
    }

    public void validar(User user) {
        if (user.getLogin() == null || user.getLogin().trim().equals("")) {
            throw new RegraNegocioException("Login inválido");
        }
        if (user.getCpf() == null || user.getCpf().trim().equals("")) {
            throw new RegraNegocioException("CPF inválido");
        }
    }
}