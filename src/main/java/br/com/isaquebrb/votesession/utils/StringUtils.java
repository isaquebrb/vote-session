package br.com.isaquebrb.votesession.utils;

import java.text.Normalizer;

public class StringUtils {

    private StringUtils(){}

    public static String normalize(String text){
        return Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    public static String hideDocument(String document){
        return "XXXXXX" + document.substring(document.length() - 5);
    }
}
