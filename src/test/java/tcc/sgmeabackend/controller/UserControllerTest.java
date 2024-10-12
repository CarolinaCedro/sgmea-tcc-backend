package tcc.sgmeabackend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tcc.sgmeabackend.model.PageableResource;
import tcc.sgmeabackend.model.UpdateUser;
import tcc.sgmeabackend.model.User;
import tcc.sgmeabackend.model.dtos.UserForgotPasswordRequestDto;
import tcc.sgmeabackend.repository.UserRepository;
import tcc.sgmeabackend.service.impl.UserServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void list() {
        List<User> users = List.of(new User(), new User());
        PageableResource<User> expectedPageableResource = new PageableResource<>(users);
        when(userService.findAll()).thenReturn(users);

        ResponseEntity<PageableResource<User>> response = userController.list(null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedPageableResource, response.getBody());
    }

    @Test
    void findByIds() {
        User user1 = new User("1", "User1");
        User user2 = new User("2", "User2");
        Set<User> users = Set.of(user1, user2);

        when(userService.findByIds(any(String[].class))).thenReturn(users);

        ResponseEntity<Set<User>> response = userController.findByIds(new String[]{"1", "2"});

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    void findById() {
        String id = "1";
        User user = new User();
        user.setId(id);
        when(userService.findById(id)).thenReturn(Optional.of(user));
        when(modelMapper.map(user, User.class)).thenReturn(user);

        ResponseEntity<Optional<User>> response = userController.findById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void create() {
        User user = new User();
        User userDto = new User();
        userDto.setId("1");
        user.setSenha("12345");
        when(userService.create(any(User.class))).thenReturn(user);
        when(modelMapper.map(user, User.class)).thenReturn(userDto);

        ResponseEntity<User> response = userController.create(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDto, response.getBody());
    }

    @Test
    void update() {
        String id = "1";
        User user = new User();
        User userDto = new User();

        when(userService.update(eq(id), any(User.class))).thenReturn(userDto);
        when(modelMapper.map(user, User.class)).thenReturn(userDto);

        ResponseEntity<User> response = userController.update(id, user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(userDto, response.getBody());
    }

    @Test
    void delete() {
        String id = "1";
        doNothing().when(userService).delete(id);
        userController.delete(id);
        verify(userService, times(1)).delete(id);
    }

    @Test
    void forgotPassword() {

        String email = "user@example.com";
        doNothing().when(userService).processForgotPassword(email);
        UserForgotPasswordRequestDto emailDto = new UserForgotPasswordRequestDto();
        emailDto.setEmail(email);

        userController.forgotPassword(emailDto);
        verify(userService, times(1)).processForgotPassword(email);
    }

    @Test
    void processForgotPassword() {
        // Simulação de email e DTO
        String email = "user2@example.com";
        UserForgotPasswordRequestDto emailDto = new UserForgotPasswordRequestDto();
        emailDto.setEmail(email);
        User user = new User();
        user.setEmail(email);
        user.setResetToken(UUID.randomUUID().toString());
        user.setSenha("12345");

        when(userRepository.findByEmail(email)).thenReturn(user);

        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        userController.forgotPassword(emailDto);

        verify(userRepository, times(0)).findByEmail(email);

        verify(mailSender, times(0)).send(any(SimpleMailMessage.class));
    }

    @Test
    void updateUser() {

        String userId = "1";
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setSenha("hashedOldPassword");

        UpdateUser payloadUser = new UpdateUser();
        payloadUser.setId(userId);
        payloadUser.setNome("Novo Nome");
        payloadUser.setCpf("11111111111");
        payloadUser.setEmail("newemail@example.com");
        payloadUser.setNovaSenha("newPassword");
        payloadUser.setOldSenha("oldPassword");


        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));


        when(passwordEncoder.matches(payloadUser.getOldSenha(), existingUser.getPassword())).thenReturn(true);


        when(passwordEncoder.encode(payloadUser.getNovaSenha())).thenReturn("hashedNewPassword");


        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setNome(payloadUser.getNome());
        updatedUser.setCpf(payloadUser.getCpf());
        updatedUser.setEmail(payloadUser.getEmail());
        updatedUser.setSenha("hashedNewPassword");

        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        userController.updateUser(payloadUser);

        verify(userRepository, times(0)).save(any(User.class));
    }
}

