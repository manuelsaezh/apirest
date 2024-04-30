package com.ey.apirest.service;

import com.ey.apirest.dto.UserDto;
import com.ey.apirest.exception.BusinessException;
import com.ey.apirest.exception.UserNotFoundException;
import com.ey.apirest.model.Phone;
import com.ey.apirest.model.User;
import com.ey.apirest.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Log4j2
@PropertySource("classpath:constants.properties")
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private Environment env;

    public UserDto crear(UserDto userDto) {
        
        log.info("Creando usuario. Datos del usuario: {}", userDto.toString());

        validaFormatos(userDto);

        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new BusinessException("Correo ya registrado");
        }

        User user = User.fromDto(userDto);
        user.setIsActive(true);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        LocalDateTime fechaActual = LocalDateTime.now();
        user.setCreated(fechaActual);
        user.setLastLogin(fechaActual);
        return UserDto.fromEntity(userRepository.save(user));
    }

    public UserDto update(String id, UserDto userDto) {

        log.info("Actualizando usuario. ");
        log.info("UUID: {}", id);
        log.info("Datos del usuario: {}", userDto.toString());

        UUID userUUID = UUID.fromString(id);
        User user = userRepository.findById(userUUID).orElseThrow(() -> new UserNotFoundException(id));

        validaFormatos(userDto);

        if (userDto.getEmail().equals(user.getEmail()) || (!userDto.getEmail().equals(user.getEmail())
                && !userRepository.findByEmail(userDto.getEmail()).isPresent())) {
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setPhones(userDto.getPhones().stream().map(Phone::fromDto).collect(Collectors.toList()));
            user.setModified(LocalDateTime.now());
            user.setIsActive(userDto.getIsActive());

        } else if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new BusinessException("Correo ya registrado");

        }
        return UserDto.fromEntity(userRepository.save(user));

    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().filter(User::getIsActive).map(UserDto::fromEntity).toList();
    }

    public void deleteUserById(String id) {
        log.info("Desactivando usuario. ");
        log.info("UUID: {}", id);
        UUID userUUID = UUID.fromString(id);
        User user = userRepository.findById(userUUID).orElseThrow(() -> new UserNotFoundException(id));
        if (!user.getIsActive()) {
            throw new BusinessException("El usuario no está activo");
        }
        user.setIsActive(false);
        user.setModified(LocalDateTime.now());
        userRepository.save(user);
    }

    public UserDto getUserById(String id) {
        log.info("Buscando datos de usuario ID: " + id);
        UUID userUUID = UUID.fromString(id);
        User user = userRepository.findById(userUUID).orElseThrow(() -> new UserNotFoundException(id));
        return UserDto.fromEntity(user);
    }

    private void validaFormatos(UserDto userDto) {

        Pattern patternEmail = Pattern.compile(env.getProperty("regex.valid.email"));
        Pattern patternPassword = Pattern.compile(env.getProperty("regex.valid.password"));
        
        if (!patternEmail.matcher(userDto.getEmail()).matches()) {
            throw new BusinessException("Formato de correo inválido");
        }

        if (!patternPassword.matcher(userDto.getPassword()).matches()) {
            throw new BusinessException("Formato de password inválido");
        }

    }

}
