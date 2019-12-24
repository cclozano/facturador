package com.aurora.pos.sri.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StrUtil {

    public static String quitarCaracteresEspeciales(String input) {
        // Descomposición canónica
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        // Nos quedamos únicamente con los caracteres ASCII
        Pattern pattern = Pattern.compile("\\p{ASCII}+");
        return pattern.matcher(normalized).replaceAll("");
    }
}
