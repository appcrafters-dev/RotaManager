package com.deeppatel.rotamanager.models;

public class RepositoryResult<T> {
    String errorMessage;
    T result;

    public RepositoryResult() { }

    public RepositoryResult(T result, String errorMessage) {
        this.errorMessage = errorMessage;
        this.result = result;
    }


    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.result = null;
        this.errorMessage = errorMessage;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.errorMessage = null;
        this.result = result;
    }


}
