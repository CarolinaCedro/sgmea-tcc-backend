package tcc.sgmeabackend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tcc.sgmeabackend.model.User;
import tcc.sgmeabackend.model.dtos.UserResponseDto;
import tcc.sgmeabackend.service.AbstractService;
import tcc.sgmeabackend.service.impl.UserServiceImpl;

@RestController
@RequestMapping("api/sgmea/v1/users")
public class UserController extends AbstractController<User, UserResponseDto> {

    private final UserServiceImpl service;

    public UserController(UserServiceImpl service, ModelMapper modelMapper) {
        super(modelMapper);
        this.service = service;
    }

    @Override
    protected Class<UserResponseDto> getDtoClass() {
        return UserResponseDto.class;
    }

    @Override
    protected AbstractService<User> getService() {
        return service;
    }
}
