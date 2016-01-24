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

@Path("/loyalty/api/v1.0")
public class HTTPServiceHandler {

	@GET
	@Path("/serverstatus")
	@Produces(MediaType.APPLICATION_JSON)
	public HttpResponse getServerStatus() {
		HttpResponse responseMsg = new HttpResponse();
		responseMsg.setReasonCode(HTTPResponseCode.SUCCESS_CODE);
		responseMsg.setRespCode(HTTPResponseCode.SUCCESS_CODE);
		responseMsg.setRespMsg(HTTPResponseCode.SERVER_STATUS_MSG);
		return responseMsg;
	}

	@POST
	@Path("/registeruser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public HttpResponse registerUser(HttpRequest httpMsg) {
		HttpResponse responseMsg = null;
		try {
			httpMsg.setTxnType(TransactionType.USR_REG.name());
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
	@Path("/carddetail")
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
	@Path("/userprofile")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public HttpResponse getCardProfile(HttpRequest httpMsg) {
		HttpResponse responseMsg = null;
		try {
			httpMsg.setTxnType(TransactionType.USR_PROFILE.name());
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
	@Path("/updateprofile")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public HttpResponse updateProfile(HttpRequest httpMsg) {
		HttpResponse responseMsg = null;
		try {
			httpMsg.setTxnType(TransactionType.UPDATE_PROFILE.name());
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
	@Path("/earnpoint")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public HttpResponse earnPoints(HttpRequest httpMsg) {
		HttpResponse responseMsg = null;
		try {
			httpMsg.setTxnType(TransactionType.EARN_POINT.name());
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
	@Path("/burnpoint")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public HttpResponse burnPoints(HttpRequest httpMsg) {
		HttpResponse responseMsg = null;
		try {
			httpMsg.setTxnType(TransactionType.BURN_POINT.name());
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
	@Path("/addpoint")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public HttpResponse addPoints(HttpRequest httpMsg) {
		HttpResponse responseMsg = null;
		try {
			httpMsg.setTxnType(TransactionType.ADD_POINT.name());
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
	@Path("/reissuecard")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public HttpResponse reissueCard(HttpRequest httpMsg) {
		HttpResponse responseMsg = null;
		try {
			httpMsg.setTxnType(TransactionType.REISSUE_CARD.name());
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
	@Path("/reverse")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public HttpResponse doReversal(HttpRequest httpMsg) {
		HttpResponse responseMsg = null;
		try {
			httpMsg.setTxnType(TransactionType.RVSAL.name());
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
	@Path("/void")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public HttpResponse doVoid(HttpRequest httpMsg) {
		HttpResponse responseMsg = null;
		try {
			httpMsg.setTxnType(TransactionType.VOIDX.name());
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
	@Path("/txnhistory")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public HttpResponse getMiniStatement(HttpRequest httpMsg) {
		HttpResponse responseMsg = null;
		try {
			httpMsg.setTxnType(TransactionType.TXN_HSTRY.name());
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
	
	// TODO - below 3 API are for gift card only
	@POST
	@Path("/dosale")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public HttpResponse doSale(HttpRequest httpMsg) {
		HttpResponse responseMsg = null;
		try {
			//httpMsg.setTxnType(TransactionType.REDMP.name());
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
			//httpMsg.setTxnType(TransactionType.LOADV.name());
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
			//httpMsg.setTxnType(TransactionType.ACTNL.name());
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
			httpTransContext.wait(60000);
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
