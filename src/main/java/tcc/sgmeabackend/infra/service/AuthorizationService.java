package tcc.sgmeabackend.infra.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tcc.sgmeabackend.repository.PessoaRepository;

@Service
public class AuthorizationService implements UserDetailsService {

    private final PessoaRepository repository;

    public AuthorizationService(PessoaRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String nome) throws UsernameNotFoundException {
        return repository.findById(nome).orElseThrow();
    }
}
