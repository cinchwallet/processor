package com.cinchwallet.core.processor.communication;

import java.io.Serializable;

import org.jpos.core.ReConfigurable;

public interface IRequestDispatcher extends ReConfigurable {

    public abstract Serializable execute(Serializable request, long timeout);

    public abstract Serializable getRequestAuditLog(Serializable request);

    public abstract Serializable getResponseAuditLog(Serializable request, Serializable response);
}