package com.example.backend.dto;

public class RpcResult {
    private String rawRequest;
    private String rawResponse;
    private String error;

    public String getRawRequest() { return rawRequest; }
    public void setRawRequest(String rawRequest) { this.rawRequest = rawRequest; }

    public String getRawResponse() { return rawResponse; }
    public void setRawResponse(String rawResponse) { this.rawResponse = rawResponse; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
}