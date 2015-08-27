package com.cinchwallet.core.processor.communication;

import java.io.Serializable;

public interface IServiceRequestor {

    public Serializable request(Serializable request, long timeout) throws Exception;

    public void requestNoWait(Serializable request, long timeout, IAsyncCallback callBackHandler) throws Exception;
}