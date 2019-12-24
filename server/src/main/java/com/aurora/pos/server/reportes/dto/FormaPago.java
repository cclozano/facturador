package com.aurora.pos.server.reportes.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class FormaPago {

    private String formaPagoEnum;
    private BigDecimal total = BigDecimal.ZERO;
}
