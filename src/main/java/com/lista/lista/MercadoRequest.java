package com.lista.lista;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MercadoRequest {
    @NotNull(message = "")
    private BigDecimal valor;
    @NotBlank(message = "")
    private String produto;
    @NotNull(message = "")
    private BigDecimal limite;

}
