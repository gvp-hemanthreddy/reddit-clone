package com.hemanth.redditclone.exceptions;

import org.springframework.mail.MailException;

public class SpringRedditException extends RuntimeException {
    public SpringRedditException(String message) {
        super(message);
    }

    public SpringRedditException(String message, MailException e) {
        super(message, e);
    }
}
