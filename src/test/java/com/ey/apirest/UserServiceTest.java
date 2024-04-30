package com.ey.apirest;

import com.ey.apirest.dto.PhoneDto;
import com.ey.apirest.dto.UserDto;
import com.ey.apirest.exception.BusinessException;
import com.ey.apirest.model.User;
import com.ey.apirest.repository.UserRepository;
import com.ey.apirest.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Before
    public void inicializaMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void creaUsuarioTestOK() throws Exception {

        List<PhoneDto> phones = new ArrayList<>();
        phones.add(PhoneDto.builder().number("97179673").cityCode("9").countryCode("56").build());
        phones.add(PhoneDto.builder().number("97197534").cityCode("9").countryCode("56").build());

        UserDto req = UserDto.builder()
                .name("Pedro Pablo Perez Pereira")
                .email("pppp@gmail.com")
                .password("Password1!")
                .phones(phones)
                .build();

        Optional<User> optUsuario = Optional.empty();
        when(userRepository.findByEmail(anyString())).thenReturn(optUsuario);

        when(userRepository.saveAndFlush(any(User.class)))
                .thenReturn(User.builder().id(UUID.randomUUID()).isActive(true).build());

        // when(phoneRepository.save(any(Phone.class))).thenReturn(Phone.builder().build());

        UserDto resp = userService.crear(req);

        assertNotNull(resp);
        assertNotNull(resp.getId());
        assertEquals(resp.getIsActive(), true);

    }

    @Test
    public void creaUsuarioCorreoExiste() {

        List<PhoneDto> phones = new ArrayList<>();
        phones.add(PhoneDto.builder().number("97179673").cityCode("9").countryCode("56").build());

        UserDto req = UserDto.builder()
                .name("Juan Rodriguez")
                .email("juan@rodriguez.org")
                .password("Hunter22!")
                .phones(phones)
                .build();

        Optional<User> optUsuario = Optional.of(User.builder().email("juan@rodriguez.org").build());
        when(userRepository.findByEmail(anyString())).thenReturn(optUsuario);

        BusinessException ex = assertThrows(BusinessException.class, () -> {
            userService.crear(req);
        });
        assertEquals(ex.getMessage(), "Correo ya registrado");
    }

    @Test
    public void creaUsuarioErrorFormatoCorreo()  {

        List<PhoneDto> phones = new ArrayList<>();
        phones.add(PhoneDto.builder().number("97179673").cityCode("9").countryCode("56").build());

        UserDto req = UserDto.builder()
                .name("Juan Rodriguez")
                .email("juan@rodriguez")
                .password("Hunter22!")
                .phones(phones)
                .build();

        Optional<User> optUsuario = Optional.empty();
        when(userRepository.findByEmail(anyString())).thenReturn(optUsuario);

        BusinessException ex = assertThrows(BusinessException.class, () -> {
            userService.crear(req);
        });

        assertEquals(ex.getMessage(), "Formato de correo inválido");
    }

    @Test
    public void creaUsuarioErrorFormatoPassword() {

        List<PhoneDto> phones = new ArrayList<>();
        phones.add(PhoneDto.builder().number("97179673").cityCode("9").countryCode("56").build());

        UserDto req = UserDto.builder()
                .name("Claudio Pavez")
                .email("cpavezba@gmail.com")
                .password("1234")
                .phones(phones)
                .build();

        Optional<User> optUsuario = Optional.empty();
        when(userRepository.findByEmail(anyString())).thenReturn(optUsuario);

        BusinessException ex = assertThrows(BusinessException.class, () -> {
            userService.crear(req);
        });

        assertEquals(ex.getMessage(), "Formato de password inválido");
    }
}
