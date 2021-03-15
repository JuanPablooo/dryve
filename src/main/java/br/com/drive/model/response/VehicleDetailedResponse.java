package br.com.drive.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public  class VehicleDetailedResponse {
    private String placa;
    private BigDecimal precoAnuncio;
    private Integer ano;
    private BigDecimal precoKbb;
    private String modelo;
    private String marca;
}
