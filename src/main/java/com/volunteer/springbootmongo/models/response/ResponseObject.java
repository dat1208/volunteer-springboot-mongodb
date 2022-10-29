package com.volunteer.springbootmongo.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

public class ResponseObject {
    @JsonProperty("status")
    private String status;

    @JsonProperty("data")
    private Object data;

    public ResponseObject(){}

    public ResponseObject(String status, Object data) {
        this.status = status;

        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
