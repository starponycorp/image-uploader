package com.starpony.imageuploader.web;

import com.starpony.imageuploader.images.exceptions.*;
import com.starpony.imageuploader.web.dto.ExceptionDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.time.LocalDateTime;


@RestControllerAdvice
public class ExceptionsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionsController.class);

    @ExceptionHandler({InvalidImageException.class, InvalidPathException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ExceptionDTO handleInvalidUploadFileForm(Exception ex) {
        LOGGER.warn(ex.getMessage());
        return new ExceptionDTO(
                LocalDateTime.now(),
                ex.getMessage()
        );
    }

    @ExceptionHandler({MaxUploadSizeExceededException.class})
    @ResponseStatus(value = HttpStatus.PAYLOAD_TOO_LARGE)
    public ExceptionDTO handleMaxUploadSizeExceeded(Exception ex) {
        LOGGER.warn(ex.getMessage());
        return new ExceptionDTO(
                LocalDateTime.now(),
                ex.getMessage()
        );
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionDTO handleAllExceptions(Exception ex) {
        LOGGER.error("Server exception", ex);
        return new ExceptionDTO(
                LocalDateTime.now(),
                ex.getMessage()
        );
    }
}
