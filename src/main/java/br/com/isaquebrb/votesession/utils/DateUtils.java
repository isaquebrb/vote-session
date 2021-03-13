package br.com.isaquebrb.votesession.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private DateUtils(){}

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static String toDateTime(LocalDateTime time){
        return time.format(formatter);
    }
}
