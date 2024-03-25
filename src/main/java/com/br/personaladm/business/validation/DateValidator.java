package com.br.personaladm.business.validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

public class DateValidator {

    public static Optional<LocalDate> isValidData(String data){
        try{
            if(Objects.isNull(data)
                    || data.trim().isEmpty()
                    || data.length()==7)
                return Optional.empty();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy", Locale.US);
            LocalDate localDate = LocalDate.parse(data, formatter);
            return Optional.of(localDate);
        }catch (Exception e){
            return Optional.empty();
        }

    }
}
