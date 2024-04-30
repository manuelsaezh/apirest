package com.ey.apirest.controller;

import com.ey.apirest.dto.UserDto;
import com.ey.apirest.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDtoReq) {
        UserDto userDtoResp = userService.crear(userDtoReq);
        return new ResponseEntity<>(userDtoResp, HttpStatus.CREATED);
    }

    @PutMapping("{id}")

    public ResponseEntity<UserDto> updateUser(@PathVariable String id, @RequestBody UserDto userDtoReq) {
        UserDto userDtoResp = userService.update(id, userDtoReq);
        return new ResponseEntity<>(userDtoResp, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> usersDto = userService.getAllUsers();
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") String id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("id") String id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

}
