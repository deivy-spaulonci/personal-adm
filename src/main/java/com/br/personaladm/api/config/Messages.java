package com.br.personaladm.api.config;

import java.util.Locale;
import java.util.ResourceBundle;

public class Messages {
    public static String getMessage(String messageKey) {
        return ResourceBundle.getBundle("messages", Locale.getDefault()).getString(messageKey);
    }
}
