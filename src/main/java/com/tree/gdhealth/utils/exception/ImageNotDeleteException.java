package com.tree.gdhealth.utils.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ImageNotDeleteException extends RuntimeException{
    public ImageNotDeleteException(String message) {
        super(message);
    }

    public ImageNotDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}
