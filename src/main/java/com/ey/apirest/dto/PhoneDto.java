package com.ey.apirest.dto;

import java.util.UUID;

import com.ey.apirest.model.Phone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhoneDto {
    private UUID id;
    private String number;
    private String cityCode;
    private String countryCode; 

    public static PhoneDto fromEntity(Phone phone) {
        PhoneDto phoneDto = new PhoneDto();
        phoneDto.setId(phone.getId());
        phoneDto.setNumber(phone.getNumber());
        phoneDto.setCityCode(phone.getCityCode());
        phoneDto.setCountryCode(phone.getCountryCode());
        return phoneDto;
    }
}
