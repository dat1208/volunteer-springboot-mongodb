package com.volunteer.springbootmongo.models.jwt;
import com.volunteer.springbootmongo.models.response.ResponseUser;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
public class JwtResponse implements Serializable{
    private static final long serialVersionUID = -8091879091924046844L;
    private boolean success;
    private HttpStatus statusCode;
    private String message;

    private final String jwttoken;
    private ResponseUser responseUser;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getJwttoken() {
        return jwttoken;
    }

    public ResponseUser getResponseUser() {
        return responseUser;
    }

    public void setResponseUser(ResponseUser responseUser) {
        this.responseUser = responseUser;
    }

    public JwtResponse(boolean success, HttpStatus statusCode, String message, String jwttoken, ResponseUser responseUser) {
        this.success = success;
        this.statusCode = statusCode;
        this.message = message;
        this.jwttoken = jwttoken;
        this.responseUser = responseUser;
    }


    public String getToken() {
        return this.jwttoken;
    }
}
