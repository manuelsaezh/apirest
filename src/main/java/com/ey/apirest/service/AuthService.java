package com.ey.apirest.service;

import com.ey.apirest.dto.LoginDto;
import com.ey.apirest.dto.UserDto;
import com.ey.apirest.exception.BusinessException;
import com.ey.apirest.exception.UserNotFoundException;
import com.ey.apirest.model.User;
import com.ey.apirest.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Log4j2
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public UserDto login(LoginDto loginDto) {
        log.info("Autentificación de usuario: {}", loginDto.getEmail());
        try {
            User user = userRepository.findByEmail(loginDto.getEmail())
                    .orElseThrow(() -> new UserNotFoundException(loginDto.getEmail()));

            if (!user.getIsActive()) {
                throw new BusinessException("El usuario no está activo");
            }

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

            String jwt = jwtService.generateToken(user);
            user.setLastLogin(LocalDateTime.now());
            user.setToken(jwt);
            user = userRepository.save(user);
            return UserDto.fromEntity(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }

}
