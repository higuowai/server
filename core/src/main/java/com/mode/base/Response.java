package com.mode.base;

import java.util.Map;

/**
 * This is the response object that represents the server responses to client
 * requests. Please don't change this class, as it will impact the mobile
 * clients which will require users to upgrade the client app.
 * <p/>
 * The response object consists of the return code and message, and the payload which is a <K, V>
 * map. For different API calls, the payload may differ.
 *
 * @author chao
 */
public class Response {

    private int code;
    private String message;
    private Map<String, ?> payload;

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

    public Map<String, ?> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, ?> payload) {
        this.payload = payload;
    }
}