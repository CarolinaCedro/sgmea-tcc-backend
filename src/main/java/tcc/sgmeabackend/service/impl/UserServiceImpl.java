package tcc.sgmeabackend.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tcc.sgmeabackend.model.Pessoa;
import tcc.sgmeabackend.model.User;
import tcc.sgmeabackend.repository.PessoaRepository;
import tcc.sgmeabackend.repository.UserRepository;
import tcc.sgmeabackend.service.AbstractService;

@Service
public class UserServiceImpl extends AbstractService<User> {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    protected JpaRepository<User, String> getRepository() {
        return userRepository;
    }
}
