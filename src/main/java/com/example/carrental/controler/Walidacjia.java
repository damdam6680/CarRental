package com.example.carrental.controler;

import java.time.LocalDate;
import java.util.Date;

public class Walidacjia {
    public static boolean isNrRejestracjiOK(String s){
        return s.matches("[AZ]{2}([AZ]|[09])[09]{4}") ;
    }

    public static boolean isCyfra(String s){
        return s.matches("-?\\d+(\\.\\d+)?");
    }

    public static boolean isOdWieksza(LocalDate d, LocalDate o){
        return  o.isAfter(d);
    }

    public static boolean isPrzeszlosc(LocalDate d, LocalDate o){
        return  o.isBefore(LocalDate.now()) && d.isBefore(LocalDate.now());
    }

}
