package br.com.daniel.healthycalculator.helper;

import java.text.NumberFormat;
import java.util.Locale;

public class formatResponse {
    public static String responseFormat(double valor) {
        Locale locale = new Locale("pt", "BR");
        final NumberFormat numberInstance = NumberFormat.getInstance(locale);
        return numberInstance
                .format(valor);
    }
}
