package com.sheikplastic.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface HistoricoPrecoDTO {

    BigDecimal getValorVenda();

    LocalDate getDtValidade();

    // campo calculado no front:
    // dtValidade == null → "Atual"
}
