package com.BikkadIT.ShopElectric.payloads;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse {

    private String message;

    private boolean success;
}
