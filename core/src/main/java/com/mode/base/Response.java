package com.mode.base;

import java.util.Map;

/**
 * This is the response object that represents the server responses to client
 * requests. Please don't change this class, as it will impact the mobile
 * clients which will require users to upgrade the client app.
 * <p/>
 * The response object consists of the return code and message, and the payload. For different
 * API calls, the payload may differ.
 *
 * @author chao
 */
public class Response {

    private int code;
    private String message;
    private Object payload;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }
}