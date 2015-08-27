package com.cinchwallet.issuer.msg.spec;

import java.text.SimpleDateFormat;

import com.cinchwallet.core.TransContext;
import com.cinchwallet.core.TxnMsg;
import com.cinchwallet.core.exception.SwitchException;
import com.cinchwallet.core.msg.IProcessorSpecification;
import com.cinchwallet.core.msg.TransactionType;
import com.cinchwallet.core.processor.communication.http.HTTPRequest;
import com.cinchwallet.core.processor.communication.http.HTTPResponse;
import com.cinchwallet.core.processor.communication.http.HttpMethods;
import com.cinchwallet.issuer.msg.IssuerConstant;
import com.cinchwallet.issuer.msg.IssuerResponse;

public class IssuerSpecification implements IProcessorSpecification{

    public static final SimpleDateFormat dateTime           = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    @Override
    public String getSpecificationName() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void populateProcessorRequest(TransContext context) throws SwitchException {
	HTTPRequest httpRequest = new HTTPRequest();
	try {
	    //ProcessorIMF has not been created, as it is not required now.
	    TxnMsg processorRequestIMF = context.getAcquirerIMF();
	    //IssuerRequest request = new IssuerRequest();
	    //request.setCardNumber(processorRequestIMF.getPan());

	    StringBuilder request = new StringBuilder();
	    request.append("{\"cardNumber\":\"").append(processorRequestIMF.getPan());
	    request.append("\",\"txnDate\":\"").append(dateTime.format(processorRequestIMF.getDtTransaction())).append("\"");
	    if(processorRequestIMF.getTxnAmount()!=null){
		request.append(",\"txnAmount\":").append(processorRequestIMF.getTxnAmount());
	    }
	    request.append("}");

	    if (processorRequestIMF.getTransactionType().equals(TransactionType.ACTNL.toString())) {
		httpRequest.setScriptName(IssuerConstant.TxnType.activate.name());
	    } else if (processorRequestIMF.getTransactionType().equals(TransactionType.BALIQ.toString())) {
		httpRequest.setScriptName(IssuerConstant.TxnType.getbalance.name());
	    } else if (processorRequestIMF.getTransactionType().equals(TransactionType.REDMP.toString())) {
		httpRequest.setScriptName(IssuerConstant.TxnType.sale.name());
	    } else if (processorRequestIMF.getTransactionType().equals(TransactionType.LOADV.toString())) {
		httpRequest.setScriptName(IssuerConstant.TxnType.load.name());
	    } else if (processorRequestIMF.getTransactionType().equals(TransactionType.DACTN.toString())) {
		httpRequest.setScriptName(IssuerConstant.TxnType.deactivate.name());
	    } else if (processorRequestIMF.getTransactionType().equals(TransactionType.GET_USR.toString())) {
		httpRequest.setScriptName(IssuerConstant.TxnType.getcarddetail.name());
	    }
	    httpRequest.setRequestBody(request.toString());
	} catch (Exception _exp) {
	    throw new SwitchException(_exp.toString(), _exp);
	}
	httpRequest.setMethod(HttpMethods.JSONOVERHTTP);
	context.setProcessorRequest(httpRequest);
    }

    @Override
    public void populateProcessorRequestIMF(TransContext context) throws SwitchException {}

    @Override
    public void populateProcessorResponseIMF(TransContext context) throws SwitchException {
	TxnMsg processorRequestIMF = context.getAcquirerIMF();
	HTTPResponse processorResponse;
	processorResponse = (HTTPResponse) context.getProcessorResponse();
	IssuerResponse response = IssuerResponse.getInstance(processorResponse.getResponseBody());

	processorRequestIMF.setBalance(response.getBalance());
	processorRequestIMF.setResultCd(response.getResponseCode());
	processorRequestIMF.setCardHolderName(response.getCardHolderName());
	context.setIRC(response.getResponseCode());

    }

    @Override
    public void populateReversalProcessorRequestIMF(TransContext context) throws SwitchException {
	// TODO Auto-generated method stub

    }

    @Override
    public void populateReversalProcessorResponseIMF(TransContext context) throws SwitchException {
	// TODO Auto-generated method stub

    }

    @Override
    public void populateReversalRequest(TransContext context) throws SwitchException {
	// TODO Auto-generated method stub

    }

}
