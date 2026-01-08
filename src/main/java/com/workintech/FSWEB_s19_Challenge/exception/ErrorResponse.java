package com.workintech.FSWEB_s19_Challenge.exception;


import java.time.LocalDateTime;

public record ErrorResponse(Integer status, String message, LocalDateTime localDateTime) {
}
