package br.com.drive.model.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleRequest {
    @NotEmpty
//    @Pattern(regexp = "[A-Z]{3}[0-9]{1}[A-Z]{1}[0-9]{2}|[A-Z]{3}[0-9]{4}", message = "placa inválida")
    private String placa;

    @NotNull(message = "id da marca não pode ser nulo")
    private UUID marcaId;

    @NotNull
    private UUID modeloId;

    @NotNull
    private BigDecimal precoAnuncio;

    @NotNull
    private Integer ano;
}
