package com.example.fetch.response;

public class InvalidInput {
    private String errorMsg;

    public InvalidInput(String error){
        this.setErrorMsg(error);
    }
    
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
