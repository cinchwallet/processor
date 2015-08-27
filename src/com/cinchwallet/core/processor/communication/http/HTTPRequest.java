package com.cinchwallet.core.processor.communication.http;

import java.io.Serializable;

import org.apache.commons.httpclient.NameValuePair;

public class HTTPRequest implements Serializable {

    private String scriptName;
    private HttpMethods method;
    private NameValuePair[] requestParameters;
    private NameValuePair[] requestHeaders;
    private String requestBody;

    public HTTPRequest() {
    }

    public HttpMethods getMethod() {
        return method;
    }

    public void setMethod(HttpMethods method) {
        this.method = method;
    }

    public NameValuePair[] getRequestParameters() {
        return requestParameters;
    }

    public void setRequestParameters(NameValuePair[] requestParameters) {
        this.requestParameters = requestParameters;
    }

    public String getScriptName() {
        return scriptName;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    public NameValuePair[] getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(NameValuePair[] requestHeader) {
        this.requestHeaders = requestHeader;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }
}