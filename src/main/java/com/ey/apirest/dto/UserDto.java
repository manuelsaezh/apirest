package com.ey.apirest.dto;

import com.ey.apirest.model.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private UUID id;
    private String name;
    @NotNull
    private String email;
    @NotNull
    private String password;
    private List<PhoneDto> phones;
    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime lastLogin;
    private String token;
    private Boolean isActive;

    public static UserDto fromEntity(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setPhones(user.getPhones().stream().map(PhoneDto::fromEntity).toList());
        userDto.setCreated(user.getCreated());
        userDto.setModified(user.getModified());
        userDto.setLastLogin(user.getLastLogin());
        userDto.setToken(user.getToken());
        userDto.setIsActive(user.getIsActive());

        return userDto;
    }
}
