package fr.makizart.common.storageservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class AuthResponseDTO {
    private String token;
    private boolean user_enabled;
}

/*
{
 "token" : kjdsiokpflpds
}
 */