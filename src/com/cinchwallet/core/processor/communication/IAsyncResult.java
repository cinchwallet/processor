package com.cinchwallet.core.processor.communication;

import java.io.Serializable;

public interface IAsyncResult {

    public Serializable getResponse();

    public Serializable getRequest();

    public Throwable getException();
}