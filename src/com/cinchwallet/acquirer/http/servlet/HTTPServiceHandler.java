package com.cinchwallet.acquirer.http.servlet;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jpos.space.Space;
import org.jpos.space.SpaceFactory;

import com.cinchwallet.acquirer.http.constant.HTTPResponseCode;
import com.cinchwallet.acquirer.http.msg.HttpRequest;
import com.cinchwallet.acquirer.http.msg.HttpResponse;
import com.cinchwallet.core.TransContext;
import com.cinchwallet.core.msg.TransactionType;
import com.cinchwallet.core.utils.CWLogger;

@Path("/api/v1.0")
public class HTTPServiceHandler {

    @GET
    @Path("/getserverstatus")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse getServerStatus() {
	HttpResponse responseMsg = new HttpResponse();
	responseMsg.setReasonCode(HTTPResponseCode.SUCCESS_CODE);
	responseMsg.setRespCode(HTTPResponseCode.SUCCESS_CODE);
	responseMsg.setRespMsg(HTTPResponseCode.SERVER_STATUS_MSG);
	return responseMsg;
    }

    @POST
    @Path("/getbalance")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse getBalance(HttpRequest httpMsg) {
	HttpResponse responseMsg = null;
	try {
	    httpMsg.setTxnType(TransactionType.BALIQ.name());
	    // Log the incoming request
	    CWLogger.httpLog.info("Request received : " + httpMsg.toString());
	    responseMsg = getResponse(httpMsg);
	} catch (Exception e) {
	    CWLogger.httpLog.error("Request processing failed : " + e.getMessage());
	    responseMsg = getExceptionResponse();
	    CWLogger.httpLog.info("Response send : " + responseMsg.toString());
	}
	return responseMsg;
    }

    @POST
    @Path("/dosale")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse doSale(HttpRequest httpMsg) {
	HttpResponse responseMsg = null;
	try {
	    httpMsg.setTxnType(TransactionType.REDMP.name());
	    // Log the incoming request
	    CWLogger.httpLog.info("Request received : " + httpMsg.toString());
	    responseMsg = getResponse(httpMsg);
	} catch (Exception e) {
	    CWLogger.httpLog.error("Request processing failed : " + e.getMessage());
	    responseMsg = getExceptionResponse();
	    CWLogger.httpLog.info("Response send : " + responseMsg.toString());
	}
	return responseMsg;
    }

    @POST
    @Path("/doreload")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse doReload(HttpRequest httpMsg) {
	HttpResponse responseMsg = null;
	try {
	    httpMsg.setTxnType(TransactionType.LOADV.name());
	    // Log the incoming request
	    CWLogger.httpLog.info("Request received : " + httpMsg.toString());
	    responseMsg = getResponse(httpMsg);
	} catch (Exception e) {
	    CWLogger.httpLog.error("Request processing failed : " + e.getMessage());
	    responseMsg = getExceptionResponse();
	    CWLogger.httpLog.info("Response send : " + responseMsg.toString());
	}
	return responseMsg;
    }

    @POST
    @Path("/activate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse activateCard(HttpRequest httpMsg) {
	HttpResponse responseMsg = null;
	try {
	    httpMsg.setTxnType(TransactionType.ACTNL.name());
	    // Log the incoming request
	    CWLogger.httpLog.info("Request received : " + httpMsg.toString());
	    responseMsg = getResponse(httpMsg);
	} catch (Exception e) {
	    CWLogger.httpLog.error("Request processing failed : " + e.getMessage());
	    responseMsg = getExceptionResponse();
	    CWLogger.httpLog.info("Response send : " + responseMsg.toString());
	}
	return responseMsg;
    }

    @POST
    @Path("/deactivate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse deActivateCard(HttpRequest httpMsg) {
	HttpResponse responseMsg = null;
	try {
	    httpMsg.setTxnType(TransactionType.DACTN.name());
	    // Log the incoming request
	    CWLogger.httpLog.info("Request received : " + httpMsg.toString());
	    responseMsg = getResponse(httpMsg);
	} catch (Exception e) {
	    CWLogger.httpLog.error("Request processing failed : " + e.getMessage());
	    responseMsg = getExceptionResponse();
	    CWLogger.httpLog.info("Response send : " + responseMsg.toString());
	}
	return responseMsg;
    }

    @POST
    @Path("/getcardprofile")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse getCardProfile(HttpRequest httpMsg) {
	HttpResponse responseMsg = null;
	try {
	    httpMsg.setTxnType(TransactionType.GET_USR.name());
	    // Log the incoming request
	    CWLogger.httpLog.info("Request received : " + httpMsg.toString());
	    responseMsg = getResponse(httpMsg);
	} catch (Exception e) {
	    CWLogger.httpLog.error("Request processing failed : " + e.getMessage());
	    responseMsg = getExceptionResponse();
	    CWLogger.httpLog.info("Response send : " + responseMsg.toString());
	}
	return responseMsg;
    }

    @POST
    @Path("/getministmt")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse getMiniStatement(HttpRequest httpMsg) {
	HttpResponse responseMsg = null;
	try {
	    httpMsg.setTxnType(TransactionType.MINI_STMT.name());
	    // Log the incoming request
	    CWLogger.httpLog.info("Request received : " + httpMsg.toString());
	    responseMsg = getResponse(httpMsg);
	} catch (Exception e) {
	    CWLogger.httpLog.error("Request processing failed : " + e.getMessage());
	    responseMsg = getExceptionResponse();
	    CWLogger.httpLog.info("Response send : " + responseMsg.toString());
	}
	return responseMsg;
    }

    private HttpResponse getResponse(HttpRequest msg) throws Exception {
	TransContext httpTransContext = new TransContext();
	httpTransContext.setAcquirerRequest(msg);
	Space spc = SpaceFactory.getSpace("acquirer-space");
	spc.out("acquirer-http-queue", httpTransContext, 900000);
	synchronized (httpTransContext) {
	    httpTransContext.wait(10000);
	}
	HttpResponse responseMsg = (HttpResponse) httpTransContext.getAcquirerResponse();
	// Log the outgoing response
	CWLogger.httpLog.info("Response send : " + responseMsg.toString());
	return responseMsg;
    }

    protected HttpResponse getExceptionResponse() {
	HttpResponse httpResponse = new HttpResponse();
	httpResponse.setReasonCode(HTTPResponseCode.ERROR_SYSTEM_ERROR_RESPONSE_CODE);
	httpResponse.setRespCode(HTTPResponseCode.HTTP_ERROR);
	httpResponse.setRespMsg(HTTPResponseCode.ERROR_SYSTEM_ERROR_MESSAGE);
	return httpResponse;
    }

}
