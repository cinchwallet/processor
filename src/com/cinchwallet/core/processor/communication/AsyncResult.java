package com.cinchwallet.core.processor.communication;

import java.io.Serializable;

public class AsyncResult implements IAsyncResult {

    protected Serializable response;
    protected Serializable request;
    protected Throwable exception;

    public Serializable getResponse() {
        return response;
    }

    public Serializable getRequest() {
        return request;
    }

    public Throwable getException() {
        return exception;
    }
}