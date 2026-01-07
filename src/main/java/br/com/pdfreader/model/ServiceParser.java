package br.com.pdfreader.model;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class ServiceParser {

  private static final List<String> SERVICOS_UM = Arrays.asList("SELAGEM", "SECAGEM");
  private static final List<String> SERVICOS_DOIS = Arrays.asList("BABYLISS");
  private static final List<String> SERVICOS_TRES = Arrays.asList("ESCOVA");
  private static final List<String> SERVICOS_TRES_A = Arrays.asList("PROGRESSIVA", "LISA");
  private static final List<String> SERVICOS_TRES_B = Arrays.asList("LONGO", "LONGA", "MEDIA");

  public static Service setComissaoPerc(Service servico, String[] colunas){
    //COMISSAO
    int comissaoIndex = 0;
    for(int i = 0; i < colunas.length; i++) {
      if (colunas[i].contains("%")) {
        comissaoIndex = i;
        servico.setComissaoPerc(colunas[i]);
      }
    }

    //CARTAO
    int taxaIndex = colunas.length - 2;
    if (comissaoIndex + 1 == taxaIndex - 1) {
      servico.setCartao(colunas[comissaoIndex + 1]);
    } else {
      int i = comissaoIndex + 1;
      StringBuilder cartao = new StringBuilder();
      while (i < taxaIndex) {
        cartao.append(colunas[i]).append(" ");
        i++;
      }
      servico.setCartao(cartao.toString());
    }

    // VALOR E DESCONTO
    servico.setValor(ServiceParser.getValor(colunas, comissaoIndex - 2));     // BigDecimal
    servico.setDesconto(ServiceParser.getValor(colunas, comissaoIndex - 1));  // BigDecimal

    return servico;
  }

  public static Service setTaxaComissao(Service servico, String[] colunas){
    servico.setTaxa(ServiceParser.getValor(colunas, colunas.length - 2));           // BigDecimal
    servico.setComissaoValor(ServiceParser.getValor(colunas, colunas.length - 1));  // BigDecimal
    return servico;
  }

  private static BigDecimal getValor(String[] colunas, int index) {
    String valor = colunas[index].replace(".", "");
    valor = valor.replace(",", ".");
    return new BigDecimal(valor);
  }

  public static Service setDescricaoCliente(Service servico, String[] colunas){
    //COMISSAO
    int comissaoIndex = 0;
    for(int i = 0; i < colunas.length; i++) {
      if (colunas[i].contains("%")) {
        comissaoIndex = i;
      }
    }

    // SERVICO E NOME
    StringBuilder desc = new StringBuilder();
    StringBuilder cliente = new StringBuilder();

    if (SERVICOS_UM.stream().anyMatch(t -> colunas[1].contains(t))) {
      desc.append(colunas[1]);
      getCliente(2, comissaoIndex, colunas, cliente);
    }

    if (SERVICOS_DOIS.stream().anyMatch(t -> colunas[1].contains(t))) {
      desc.append(colunas[1]).append(" ").append(colunas[2]);
      getCliente(3, comissaoIndex, colunas, cliente);
    }

    if (SERVICOS_TRES.stream().anyMatch(t -> colunas[1].contains(t))) {
      if (SERVICOS_TRES_A.stream().anyMatch(ta -> colunas[2].contains(ta))) {
        if (SERVICOS_TRES_B.stream().anyMatch(tb -> colunas[3].contains(tb))) {
          desc.append(colunas[1]).append(" ").append(colunas[2]).append(" ").append(colunas[3]);
          getCliente(4, comissaoIndex, colunas, cliente);
        }
      }
    }

    servico.setDescricao(desc.toString());
    servico.setCliente(cliente.toString());
    return servico;
  }

  public static BigDecimal getValorReal(String servico){
    if (servico.equals("SELAGEM") || servico.equals("BABYLISS AUXILIAR")) {
      return new BigDecimal(100);
    } else if (servico.equals("SECAGEM")) {
      return new BigDecimal(40);
    } else if (servico.equals("ESCOVA LISA LONGO")) {
      return new BigDecimal(80);
    } else if (servico.equals("ESCOVA LISA MEDIA")) {
      return new BigDecimal(70);
    } else if (servico.equals("ESCOVA PROGRESSIVA LONGO")) {
      return new BigDecimal(200);
    } else {
      return new BigDecimal(0);
    }
  }

  private static void getCliente(int index, int comissaoIndex, String[] colunas, StringBuilder cliente) {
    int i = index;
    while(i < comissaoIndex - 2) {
      cliente.append(colunas[i]).append(" ");
      i++;
    }
  }

}
