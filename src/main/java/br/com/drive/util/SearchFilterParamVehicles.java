package br.com.drive.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchFilterParamVehicles {
    private String placa;
    private String modelo;
    private UUID modeloId;
    private String marca;
    private UUID marcaId;
}
