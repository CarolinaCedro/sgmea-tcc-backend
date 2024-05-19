package tcc.sgmeabackend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tcc.sgmeabackend.model.User;
import tcc.sgmeabackend.service.AbstractService;
import tcc.sgmeabackend.service.impl.UserServiceImpl;

@RestController
@RequestMapping("api/sgmea/v1/users")
public class UserController extends AbstractController<User> {

    private final UserServiceImpl service;

    public UserController(UserServiceImpl service) {
        this.service = service;
    }


    @Override
    protected AbstractService<User> getService() {
        return service;
    }
}
