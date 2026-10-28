package com.lista.lista;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MercadoResponse {
    private BigDecimal valor;
    private String produto;
    private BigDecimal limite;

}
