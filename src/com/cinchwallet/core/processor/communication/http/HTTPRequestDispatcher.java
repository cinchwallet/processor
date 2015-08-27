package com.cinchwallet.core.processor.communication.http;

import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.OptionsMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;

import com.cinchwallet.core.processor.communication.IRequestDispatcher;


public class HTTPRequestDispatcher implements IRequestDispatcher {

    private HttpClient client;
    private String end_point_url;
    private HttpMethods method;
    private HostConfiguration hostconfig;
    private static Logger logger;
    private String LOG_FIELD_SEPARATOR = "::";
    private String LOG_FIELD_VALUE_SEPARATOR = ":";

    public Serializable execute(Serializable request, long timeout) {
        Serializable response = null;
		try
		{
          if (request instanceof HTTPRequest) {
            HTTPRequest httpRequest = (HTTPRequest) request;
            HttpMethods executeMethod = method;
            if (httpRequest.getMethod() != null) {
                executeMethod = httpRequest.getMethod();
            }
            switch (executeMethod) {
                case POST:
                    response = doPost(httpRequest, HttpMethods.POST, timeout);
                    break;
                case XMLOVERHTTP:
                    response = doPost(httpRequest, HttpMethods.XMLOVERHTTP, timeout);
                    break;
                case JSONOVERHTTP:
                    response = doPost(httpRequest, HttpMethods.JSONOVERHTTP, timeout);
                    break;
                case GET:
                    response = doGet(httpRequest, timeout);
                    break;
                case OPTIONS:
                    response = doOptions(httpRequest, timeout);
                    break;
                default:
                    throw new RuntimeException("Method not supported.");
            }
          }
		  else
		  {
            throw new RuntimeException(
                    "Serializable object must be instance of com.bhn.beas.core.issuer.communication.http.HTTPRequest");
          }
	    }
	    //catch(IOException e)
	    //{
		//	getLog4j().error("HTTPRequestDispatcher:execute()::**IOException**", e);
		//}
		catch(Exception e)
		{
				getLog4j().error("Error in HTTPRequestDispatcher.execute() Exception block" + e.getMessage());
            	if(null==response || !(response instanceof HTTPResponse))
				{
            		response = new HTTPResponse();
				}
				((HTTPResponse)response).setException(e);
    	}
        return response;
    }

    public Serializable doPost(HTTPRequest request, HttpMethods requestType, long timeout)throws Exception {
        PostMethod postMethod = null;
        HTTPResponse response = null;
        try {
            postMethod = new PostMethod(getURL(request));
            postMethod.getParams().setParameter("http.socket.timeout", new Integer((int) timeout));
            prepareRequest(postMethod, request);
            if (requestType.equals(HttpMethods.JSONOVERHTTP)) {
        	postMethod.setRequestEntity(new StringRequestEntity(request.getRequestBody(), "application/json", "UTF-8"));
            } else if (requestType.equals(HttpMethods.XMLOVERHTTP)){
                postMethod.setRequestEntity(new StringRequestEntity(request.getRequestBody(), "text/xml", "UTF-8"));
            }else{
        	postMethod.setRequestBody(request.getRequestParameters());
            }

            client.executeMethod(hostconfig, postMethod);
            response = new HTTPResponse();
            response.setContentLength(postMethod.getResponseContentLength());
            prepareResponse(response, postMethod);
        } catch (HttpException he) {
            getLog4j().error("HTTPRequestDispatcher:doPost()::**HttpException**", he);
            throw he;
        } catch (Exception ioe) {
            getLog4j().error("HTTPRequestDispatcher:doPost()::**Exception**", ioe);
            throw ioe;
        } finally {
            if (postMethod != null) {
                postMethod.releaseConnection();
            }
        }
        return response;
    }

    public Serializable doGet(HTTPRequest request, long timeout)throws Exception {
        GetMethod getMethod = null;
        HTTPResponse response = null;
        try {
            getMethod = new GetMethod(getURL(request));
            getMethod.getParams().setParameter("http.socket.timeout", new Integer((int) timeout));
            prepareRequest(getMethod, request);
            getMethod.setQueryString(request.getRequestParameters());
            client.executeMethod(hostconfig, getMethod);
            response = new HTTPResponse();
            response.setContentLength(getMethod.getResponseContentLength());
            prepareResponse(response, getMethod);
        } catch (HttpException he) {
            getLog4j().error("HTTPRequestDispatcher:doGet()::**HttpException**", he);
            throw he;
        } catch (Exception ioe) {
            getLog4j().error("HTTPRequestDispatcher:doGet()::**Exception**", ioe);
            throw ioe;
        } finally {
            if (getMethod != null) {
                getMethod.releaseConnection();
            }
        }
        return response;
    }

    public Serializable doOptions(HTTPRequest request, long timeout)throws Exception {
        OptionsMethod optionsMethod = null;
        HTTPResponse response = null;
        try {
            optionsMethod = new OptionsMethod(getURL(request));
            optionsMethod.getParams().setParameter("http.socket.timeout", new Integer((int) timeout));
            prepareRequest(optionsMethod, request);
            client.executeMethod(hostconfig, optionsMethod);
            response = new HTTPResponse();
            response.setContentLength(optionsMethod.getResponseContentLength());
            prepareResponse(response, optionsMethod);
        } catch (HttpException he) {
            getLog4j().error("HTTPRequestDispatcher:doOptions()::**HttpException**", he);
            throw he;
        } catch (Exception ioe) {
            getLog4j().error("HTTPRequestDispatcher:doOptions()::**Exception**", ioe);
            throw ioe;
        } finally {
            if (optionsMethod != null) {
                optionsMethod.releaseConnection();
            }
        }
        return response;
    }

    private String getURL(HTTPRequest request) {
        if (request.getScriptName() == null || request.getScriptName().length() == 0) {
            return end_point_url;
        } else {
            return end_point_url + request.getScriptName();
        }
    }

    public void prepareRequest(HttpMethod method, HTTPRequest request) {
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
        if (request.getRequestHeaders() != null) {
            for (NameValuePair header : request.getRequestHeaders()) {
                method.addRequestHeader(header.getName(), header.getValue());
                //method.setRequestHeader(header.getName(), header.getValue());
                //2009-06-01 gkapl01 to handle Cabelas Basic Authentication
                if ("Authorization".equals(header.getName())&&
                    header.getValue()!=null &&
                    header.getValue().startsWith("Basic")) {
                    method.setDoAuthentication(true);
                }
            }
        }
    }

    public void prepareResponse(HTTPResponse response, HttpMethod method) throws IOException {
        response.setStatusCode(method.getStatusCode());
        response.setStatusText(method.getStatusText());
        response.setResponseHeaders(method.getResponseHeaders());
        response.setResponseBody(method.getResponseBodyAsString());
    }

    public HttpClient getClient() {
        return client;
    }

    public void setConfiguration(Configuration cfg) throws ConfigurationException {
        if (client != null) {
            client = null;
        }
        method = null;
        end_point_url = cfg.get("endpoint-url");
        if (end_point_url == null || end_point_url.trim().length() == 0) {
            throw new ConfigurationException("'endpoint-url' is a mandatory property, specify host URL in this");
        }
        String defaultMethod = cfg.get("method");
        if (defaultMethod != null && defaultMethod.length()>0) {
            defaultMethod = defaultMethod.trim().toUpperCase();
            for (HttpMethods definedMethods : HttpMethods.values()) {
                if (definedMethods.toString().equals(defaultMethod)) {
                    method = definedMethods;
                    break;
                }
            }
            if (method == null) {
                throw new ConfigurationException("Method not supported[" + defaultMethod + "]");
            }

        } else {
            method = HttpMethods.POST;
        }
        client = new HttpClient();
        hostconfig = new HostConfiguration();
        hostconfig.setHost(end_point_url);
    }

    private Logger getLog4j() {
        if (logger == null)
            logger = Logger.getLogger(HTTPRequestDispatcher.class);
        return logger;
    }

    public Serializable getRequestAuditLog(Serializable request) {
        StringBuilder requestLog = new StringBuilder("HTTPRequest::");
        if (request != null) {
            HTTPRequest httpRequest = (HTTPRequest) request;

            requestLog.append(getURL(httpRequest));
            requestLog.append(LOG_FIELD_SEPARATOR);

            HttpMethods executeMethod = method;
            if (httpRequest.getMethod() != null) {
                executeMethod = httpRequest.getMethod();
            }
            requestLog.append(executeMethod.name());
            requestLog.append(LOG_FIELD_SEPARATOR);
            switch (executeMethod) {
                case XMLOVERHTTP:
                	if (httpRequest.getRequestHeaders()!= null) {
                		for (NameValuePair nvPair : httpRequest.getRequestHeaders()) {
                            requestLog.append(getString(nvPair.getName()));
                            requestLog.append(LOG_FIELD_VALUE_SEPARATOR);
                            requestLog.append(getString(nvPair.getValue()));
                            requestLog.append(LOG_FIELD_SEPARATOR);
                        }
                    }
                    if (httpRequest.getRequestBody() != null) {
                        requestLog.append(getString(httpRequest.getRequestBody()));
                        requestLog.append(LOG_FIELD_SEPARATOR);
                    }

                    break;
                default:
                    if (httpRequest.getRequestParameters() != null) {
                        for (NameValuePair nvPair : httpRequest.getRequestParameters()) {
                            requestLog.append(getString(nvPair.getName()));
                            requestLog.append(LOG_FIELD_VALUE_SEPARATOR);
                            requestLog.append(getString(nvPair.getValue()));
                            requestLog.append(LOG_FIELD_SEPARATOR);
                        }
                    } else if (httpRequest.getRequestBody() != null) {
                        requestLog.append(getString(httpRequest.getRequestBody()));
                        requestLog.append(LOG_FIELD_SEPARATOR);
                    }
            }
        }
        return requestLog.toString();
    }

    public Serializable getResponseAuditLog(Serializable request, Serializable response) {
        StringBuilder responseLog = new StringBuilder("HTTPResponse::");

        if (request != null) {
            HTTPRequest httpRequest = (HTTPRequest) request;
            responseLog.append(getURL(httpRequest));
            responseLog.append(LOG_FIELD_SEPARATOR);

            HttpMethods executeMethod = method;
            if (httpRequest.getMethod() != null) {
                executeMethod = httpRequest.getMethod();
            }
            responseLog.append(executeMethod.name());
            responseLog.append(LOG_FIELD_SEPARATOR);
        }

        if (response != null) {
            HTTPResponse httpResponse = (HTTPResponse) response;
            if (httpResponse.getResponseBody() != null) {
                responseLog.append(getString(httpResponse.getResponseBody()));
                responseLog.append(LOG_FIELD_SEPARATOR);
            }
            if (httpResponse.getException() != null) {
                responseLog.append(httpResponse.getException().toString());
                responseLog.append(LOG_FIELD_SEPARATOR);
            }
        }
        return responseLog.toString();
    }

    private String getString(String val) {
        if (val != null)
            return val;
        else
            return "";
    }
}