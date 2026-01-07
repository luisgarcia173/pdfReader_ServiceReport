package br.com.pdfreader.service;

import br.com.pdfreader.model.Service;
import br.com.pdfreader.model.ServiceParser;
import br.com.pdfreader.rules.RulesValidator;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OCRReader {

  public List<Service> extractData(String pdfName) throws Exception {

    String pdfData = extractDataFromImage(pdfName);

    List<Service> servicos = processText(pdfData);

    return servicos;
  }

  private String extractDataFromImage(String pdfName) throws TesseractException {
    // Tesseract config
    ITesseract tesseract = new Tesseract();
    tesseract.setDatapath("D:\\Tesseract-OCR\\tessdata");
    tesseract.setLanguage("eng");

    // Process file
    return tesseract.doOCR(new File(pdfName));
  }

  private List<Service> processText(String texto) throws ParseException {
    List<Service> servicos = new ArrayList<>();

    String[] linhas = texto.split("\\r?\\n");

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");

    for (int i = 1; i < linhas.length; i++) {

      String linha = linhas[i].trim();

      if (!linha.startsWith("beauty") && !linha.startsWith("DATA")) {
        String[] colunas = linha.split("\\s");

        if (colunas.length >= 9) {

          Service servico = new Service();

          servico.setData(sdf.parse(colunas[0]));
          ServiceParser.setDescricaoCliente(servico, colunas);
          ServiceParser.setComissaoPerc(servico, colunas);
          ServiceParser.setTaxaComissao(servico, colunas);

          if (RulesValidator.isValidService(servico.getDescricao())) {
            servicos.add(servico);
          }
        }
      }
    }

    return servicos;
  }


}
