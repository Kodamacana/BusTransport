package com.bustransport.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {
    private Boolean warning;

    private final String message;
    private final String messageLanguageKey;

    public ServiceException(String messageLanguageKey, String message) {
        super(message);
        this.warning = Boolean.FALSE;
        this.messageLanguageKey = messageLanguageKey;
        this.message = message;
    }

    public ServiceException(String messageLanguageKey, String message, boolean warning) {
        super(message);
        this.warning = warning;
        this.messageLanguageKey = messageLanguageKey;
        this.message = message;
    }

}