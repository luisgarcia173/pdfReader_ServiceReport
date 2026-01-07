package br.com.pdfreader.rules;

import java.util.Arrays;

public class RulesValidator {

  public static boolean isValidService(String service) {
    return Arrays.asList("SELAGEM", "SECAGEM", "BABYLISS AUXILIAR", "ESCOVA PROGRESSIVA LONGO", "ESCOVA LISA LONGO", "ESCOVA LISA MEDIA").contains(service);
  }

}
