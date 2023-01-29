package com.example.carrental.controler;

import java.time.LocalDate;
import java.util.Date;

public class Walidacjia {
    public static boolean isNrRejestracjiOK(String s){
        return s.length() >= 5 && s.length() <= 7;
    }

    public static boolean isCyfra(String s){
        return s.matches("-?\\d+(\\.\\d+)?");
    }

    public static boolean isOdWieksza(LocalDate d, LocalDate o){
        return  o.isAfter(d);
    }

    public static boolean isPrzeszlosc(LocalDate d, LocalDate o){
        return  o.isBefore(LocalDate.now()) || d.isBefore(LocalDate.now());
    }

    public static boolean isData(String s){return  s.matches("^\\d{1,2}-[a-zA-Z]{3}-\\d{4}$");}

    public static boolean isBezZnakowSpecialnych(String s){return  s.matches("^[A-Za-z0-9 ]+$");}

    public static boolean isPesel(String s){return  s.matches("^\\d{11}$");}

    public static boolean isNumerTelefonu(String s){return  s.matches(" ^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$ ");}

}
