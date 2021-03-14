package br.com.drive.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleRequest {
    @NotNull(message = "a placa não pode ser nula")
    @NotEmpty(message = "a placa não pode estar vazia")
//    @Pattern(regexp = "[A-Z]{3}[0-9]{1}[A-Z]{1}[0-9]{2}|[A-Z]{3}[0-9]{4}", message = "placa inválida")
    private String placa;

    @NotNull(message = "o id da marca não pode ser nulo")
    private UUID marcaId;

    @NotNull(message = "o id do modelo não pode ser nulo")
    private UUID modeloId;

    @NotNull(message = "o preço não pode ser nulo")
    private BigDecimal precoAnuncio;

    @NotNull(message = "o ano não pode ser nulo")
    private Integer ano;
}
