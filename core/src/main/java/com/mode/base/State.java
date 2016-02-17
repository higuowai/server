package com.mode.base;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * State codes for reading/writing to the status columns of database tables.
 *
 * @author chao
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum State {

    /* State code from 0 to 20 are reserved for common settings. */
    NORMAL(0, "State Normal."),
    SUBMITTED(1, "State Submitted");

    private final int code;
    private final String message;

    private State(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "{\"code\":\"" + code + "\", \"message\":\"" + message + "\"}";
    }
}