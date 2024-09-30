package tcc.sgmeabackend.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tcc.sgmeabackend.model.UpdateUser;
import tcc.sgmeabackend.model.User;
import tcc.sgmeabackend.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {


    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private  JavaMailSender mailSender;


    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        List<User> users = Arrays.asList(new User(), new User());
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void findById() {
        String id = "1";
        User user = new User();
        user.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void create() {
        User user = new User();
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.create(user);
        assertNotNull(result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void update() {

        String id = "1";
        User user = new User();
        user.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.update(id, user);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void delete() {
        String id = "1";
        User user = new User();
        user.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));


        doNothing().when(userRepository).deleteById("1");

        userService.delete("1");

        verify(userRepository, times(1)).deleteById("1");
    }

    @Test
    void findByIds() {

        String[] ids = {"1", "2"};
        List<User> funcionarios = Arrays.asList(new User(), new User());

        when(userRepository.findAllById(Arrays.asList(ids))).thenReturn(funcionarios);

        Set<User> result = userService.findByIds(ids);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findAllById(Arrays.asList(ids));
    }

    @Test
    void processForgotPassword() {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(user);
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        userService.processForgotPassword(email);

        assertNotNull(user.getResetToken());
        verify(userRepository, times(1)).findByEmail(email);
        verify(userRepository, times(1)).save(user);
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }


    @Test
    void updateUser() {
        String id = "1";
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";

        User user = new User();
        user.setId(id);
        user.setSenha(new BCryptPasswordEncoder().encode(oldPassword));

        UpdateUser payloadUser = new UpdateUser();
        payloadUser.setId(id);
        payloadUser.setNome("Updated Name");
        payloadUser.setCpf("12345678900");
        payloadUser.setEmail("updated@example.com");
        payloadUser.setOldSenha(oldPassword);
        payloadUser.setNovaSenha(newPassword);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = (User) userService.updateUser(Optional.of(user), payloadUser);

        assertNotNull(result);
        assertEquals("Updated Name", result.getNome());
        assertTrue(new BCryptPasswordEncoder().matches(newPassword, result.getPassword()));
        verify(userRepository, times(1)).save(any(User.class));
    }

}