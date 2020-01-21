package com.skysoft.friends.bussines.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ReadFileException extends RuntimeException {

    private ReadFileException(String message) {
        super(message);
    }

    public static ReadFileException notSupportedMediaType() {
        return new ReadFileException("Provided media type of the file is not supported.");
    }

    public static ReadFileException invalidFile() {
        return new ReadFileException("Something wrong with your file. Please try another one");
    }

}
