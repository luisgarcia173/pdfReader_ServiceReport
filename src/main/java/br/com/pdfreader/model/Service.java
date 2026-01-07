package br.com.pdfreader.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Service {

  private Date data;
  private String descricao;
  private String cliente;
  private BigDecimal valor;
  private BigDecimal desconto;
  private String comissaoPerc;
  private String cartao;
  private BigDecimal taxa;
  private BigDecimal comissaoValor;

}
