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
        return  o.isBefore(LocalDate.now()) && d.isBefore(LocalDate.now());
    }

    public static boolean isData(String s){return  s.matches("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]|(?:Jan|Mar|May|Jul|Aug|Oct|Dec)))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2]|(?:Jan|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec))\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)(?:0?2|(?:Feb))\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9]|(?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep))|(?:1[0-2]|(?:Oct|Nov|Dec)))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$");}
}
