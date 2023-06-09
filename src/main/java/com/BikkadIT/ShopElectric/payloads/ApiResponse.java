package com.BikkadIT.ShopElectric.payloads;

import lombok.*;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse {

    private String message;

    private boolean success;

    private HttpStatus status;
}
