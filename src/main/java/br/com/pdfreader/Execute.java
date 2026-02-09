package br.com.pdfreader;

import br.com.pdfreader.model.Service;
import br.com.pdfreader.model.ServiceParser;
import br.com.pdfreader.service.OCRReader;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

public class Execute {

  public static void main(String[] args) throws Exception {

    // LER PDF
    List<Service> servicos = new OCRReader().extractData("src/main/resources/Rodrigomes122025.pdf");

    // GERAR PDF SAIDA COM VALORES
    StringBuilder sb = new StringBuilder();
    BigDecimal total = BigDecimal.ZERO;

    sb.append(String.format("%-20s %-40s %-40s %-10s\n", "Data", "Cliente", "Descrição", "Valor"));

    for (Service s : servicos) {
      sb.append(String.format("%-20s %-40s %-40s %-10s\n",
          new SimpleDateFormat("dd/MM/yyyy").format(s.getData()),
          s.getCliente(),
          s.getDescricao(),
          ServiceParser.getValorReal(s.getDescricao())));
      total = total.add(ServiceParser.getValorReal(s.getDescricao()));
    }

    sb.append("\n");
    sb.append(String.format("TOTAL: R$ %.2f (%.2f)\n", total, total.multiply(new BigDecimal("0.15"))));
    System.out.println(sb.toString());

  }

}