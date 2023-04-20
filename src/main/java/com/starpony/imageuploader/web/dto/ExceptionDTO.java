package com.starpony.imageuploader.web.dto;

import java.time.LocalDateTime;
import java.util.Date;

public record ExceptionDTO(LocalDateTime dateTime,
                           String message) {
}
