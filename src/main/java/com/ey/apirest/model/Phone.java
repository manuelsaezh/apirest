package com.ey.apirest.model;

import java.util.UUID;

import com.ey.apirest.dto.PhoneDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Phone {
    
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    @Column(name = "phone_id")
    private UUID id;
    private String number;
    private String cityCode;
    private String countryCode;

    public static Phone fromDto(PhoneDto phoneDto) {
        Phone phone = new Phone();
        phone.setId(phoneDto.getId());
        phone.setNumber(phoneDto.getNumber());
        phone.setCityCode(phoneDto.getCityCode());
        phone.setCountryCode(phoneDto.getCountryCode());
        return phone;
    }
}
