package com.cinchwallet.issuer.msg.spec;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.httpclient.NameValuePair;

import com.cinchwallet.core.Card;
import com.cinchwallet.core.Cardholder;
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

public class IssuerSpecification implements IProcessorSpecification {

	@Override
	public String getSpecificationName() {
		return "IssuerSpecification";
	}

	@Override
	public void populateProcessorRequest(TransContext context) throws SwitchException {
		HTTPRequest httpRequest = new HTTPRequest();
		try {
			// ProcessorIMF has not been created, as it is not required now.
			TxnMsg processorRequestIMF = context.getAcquirerIMF();

			StringBuilder request = createRequest(processorRequestIMF);
			populateEndPoint(processorRequestIMF, httpRequest);

			httpRequest.setRequestBody(request.toString());
		} catch (Exception _exp) {
			throw new SwitchException(_exp.toString(), _exp);
		}
		context.setProcessorRequest(httpRequest);
	}

	private StringBuilder createRequest(TxnMsg processorRequestIMF) {
		final SimpleDateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		StringBuilder request = new StringBuilder();
		request.append("{\"merchantId\":\"").append(processorRequestIMF.getMid());
		request.append("\",\"txnDate\":\"").append(dateTime.format(processorRequestIMF.getDtTransaction()))
				.append("\"");
		request.append(",\"terminalId\":\"").append(processorRequestIMF.getTid()).append("\"");
		request.append(",\"stan\":\"").append(processorRequestIMF.getStan()).append("\"");
		if(processorRequestIMF.getPan()!=null){
			request.append(",\"cardNumber\":\"").append(processorRequestIMF.getPan()).append("\"");	
		}
		if(processorRequestIMF.getPhoneNumber()!=null){
			request.append(",\"phoneNumber\":\"").append(processorRequestIMF.getPhoneNumber()).append("\"");
		}
		if (processorRequestIMF.getTransactionType().equals(TransactionType.USR_REG.name()) ||
				processorRequestIMF.getTransactionType().equals(TransactionType.UPDATE_PROFILE.name())) {
			request.append(",\"cardHolder\":");
			request.append("{\"firstName\":\"").append(processorRequestIMF.getCardholder().getFirstName()).append("\"");
			request.append(",\"lastName\":\"").append(processorRequestIMF.getCardholder().getLastName()).append("\"");
			request.append(",\"gender\":\"").append(processorRequestIMF.getCardholder().getGender()).append("\"");
			request.append(",\"dateOfBirth\":\"").append(processorRequestIMF.getCardholder().getDateOfBirth())
					.append("\"");
			request.append(",\"phoneNumber\":\"").append(processorRequestIMF.getCardholder().getPhoneNumber())
					.append("\"");
			request.append(",\"email\":\"").append(processorRequestIMF.getCardholder().getEmail()).append("\"");
			request.append(",\"address\":\"").append(processorRequestIMF.getCardholder().getAddress()).append("\"");
			request.append(",\"city\":\"").append(processorRequestIMF.getCardholder().getCity()).append("\"");
			request.append(",\"state\":\"").append(processorRequestIMF.getCardholder().getState()).append("\"");
			request.append(",\"zip\":\"").append(processorRequestIMF.getCardholder().getZip()).append("\"");
			request.append(",\"country\":\"").append(processorRequestIMF.getCardholder().getCountry()).append("\"}");
		} else if (processorRequestIMF.getTransactionType().equals(TransactionType.EARN_POINT.name())
				|| processorRequestIMF.getTransactionType().equals(TransactionType.BURN_POINT.name())
				|| processorRequestIMF.getTransactionType().equals(TransactionType.ADD_POINT.name())) {
			request.append(",\"points\":").append(processorRequestIMF.getTxnPoints());
		} else if (processorRequestIMF.getTransactionType().equals(TransactionType.REISSUE_CARD.name())) {
			request.append(",\"newCardNumber\":\"").append(processorRequestIMF.getNewCardNumber()).append("\"");
		}
		request.append("}");
		return request;

	}

	private void populateEndPoint(TxnMsg processorRequestIMF, HTTPRequest httpRequest) {
		httpRequest.setMethod(HttpMethods.JSONOVERHTTP);
		if (processorRequestIMF.getTransactionType().equals(TransactionType.USR_REG.toString())) {
			httpRequest.setScriptName(IssuerConstant.TxnType.REGISTERUSER.getEndPoint());
		} else if (processorRequestIMF.getTransactionType().equals(TransactionType.BALIQ.toString())) {
			httpRequest.setScriptName(IssuerConstant.TxnType.CARDDETAIL.getEndPoint() + "/"
					+ processorRequestIMF.getPan());
			httpRequest.setMethod(HttpMethods.GET);
			NameValuePair nameValuePairs = new NameValuePair();
			nameValuePairs.setName("cardNumber");
			nameValuePairs.setValue(processorRequestIMF.getPan());
			
			NameValuePair[] pair = new NameValuePair[1]; 
			pair[0] = nameValuePairs;
			httpRequest.setRequestParameters(pair);
			
		} else if (processorRequestIMF.getTransactionType().equals(TransactionType.USR_PROFILE.toString())) {
			NameValuePair nameValuePairs = new NameValuePair();
			if(processorRequestIMF.getPan()==null){
				//phone is primary instrument for txn
				httpRequest.setScriptName(IssuerConstant.TxnType.USEPROFILE.getEndPoint() + "/p/"
						+ processorRequestIMF.getPhoneNumber());
				httpRequest.setMethod(HttpMethods.GET);
				nameValuePairs.setName("phoneNumber");
				nameValuePairs.setValue(processorRequestIMF.getPhoneNumber());
			}else{
				httpRequest.setScriptName(IssuerConstant.TxnType.USEPROFILE.getEndPoint() + "/c/"
						+ processorRequestIMF.getPan());
				httpRequest.setMethod(HttpMethods.GET);
				
				nameValuePairs.setName("cardNumber");
				nameValuePairs.setValue(processorRequestIMF.getPan());
			}
			
			NameValuePair[] pair = new NameValuePair[1]; 
			pair[0] = nameValuePairs;
			httpRequest.setRequestParameters(pair);
		} else if (processorRequestIMF.getTransactionType().equals(TransactionType.EARN_POINT.toString())) {
			httpRequest.setScriptName(IssuerConstant.TxnType.EARNPOINT.getEndPoint());
		} else if (processorRequestIMF.getTransactionType().equals(TransactionType.BURN_POINT.toString())) {
			httpRequest.setScriptName(IssuerConstant.TxnType.BURNPOINT.getEndPoint());
		} else if (processorRequestIMF.getTransactionType().equals(TransactionType.ADD_POINT.toString())) {
			httpRequest.setScriptName(IssuerConstant.TxnType.ADDPOINT.getEndPoint());
		} else if (processorRequestIMF.getTransactionType().equals(TransactionType.REISSUE_CARD.toString())) {
			httpRequest.setScriptName(IssuerConstant.TxnType.REISSUECARD.getEndPoint());
		} else if (processorRequestIMF.getTransactionType().equals(TransactionType.RVSAL.toString())) {
			// httpRequest.setScriptName(IssuerConstant.TxnType.CARDDETAIL);
		} else if (processorRequestIMF.getTransactionType().equals(TransactionType.VOIDX.toString())) {
			// httpRequest.setScriptName(IssuerConstant.TxnType.cancel.name());
		} else if (processorRequestIMF.getTransactionType().equals(TransactionType.TXN_HSTRY.toString())) {
			// httpRequest.setScriptName(IssuerConstant.TxnType.txnhistory.name());
		} else if (processorRequestIMF.getTransactionType().equals(TransactionType.DACTN.toString())) {
			httpRequest.setScriptName(IssuerConstant.TxnType.DEACTIVATE.getEndPoint());
		}else if (processorRequestIMF.getTransactionType().equals(TransactionType.UPDATE_PROFILE.toString())) {
			httpRequest.setScriptName(IssuerConstant.TxnType.UPDATEPROFILE.getEndPoint() + "/"
					+ processorRequestIMF.getCardholder().getMembershipId());
		} 
	}

	@Override
	public void populateProcessorRequestIMF(TransContext context) throws SwitchException {
	}

	@Override
	public void populateProcessorResponseIMF(TransContext context) throws SwitchException {
		TxnMsg processorRequestIMF = context.getAcquirerIMF();
		HTTPResponse processorResponse;
		processorResponse = (HTTPResponse) context.getProcessorResponse();
		IssuerResponse response = IssuerResponse.getInstance(processorResponse.getResponseBody());
		processorRequestIMF.setBalance(response.getBalance());
		processorRequestIMF.setPointBalance(response.getPointsAvailable());
		processorRequestIMF.setPointExpireOn(response.getPointsExpireOn());
		processorRequestIMF.setResultCd(response.getResponseCode());
		processorRequestIMF.setDisplayMessage(response.getResponseMsg());
		if(response.getCardHolder()!=null){
			//populate card holder data
			Cardholder cardholder = new Cardholder();
			processorRequestIMF.setCardholder(cardholder);
			
			cardholder.setMembershipId(response.getCardHolder().getMembershipId());
			cardholder.setFirstName(response.getCardHolder().getFirstName());
			cardholder.setLastName(response.getCardHolder().getLastName());
			cardholder.setEmail(response.getCardHolder().getEmail());
			cardholder.setPhoneNumber(response.getCardHolder().getPhoneNumber());
			cardholder.setGender(response.getCardHolder().getGender());
			cardholder.setDateOfBirth(response.getCardHolder().getDateOfBirth());
			cardholder.setAddress(response.getCardHolder().getAddress());
			cardholder.setCity(response.getCardHolder().getCity());
			cardholder.setState(response.getCardHolder().getState());
			cardholder.setZip(response.getCardHolder().getZip());
			cardholder.setCountry(response.getCardHolder().getCountry());
		}
		
		if(response.getCard()!=null){
			//populate card details
			Card card = new Card();
			processorRequestIMF.setCard(card);
			
			card.setBalance(response.getCard().getBalance());
			card.setCardNumber(response.getCard().getCardNumber());
			card.setMembershipId(response.getCard().getMembershipId());
			card.setPointExpireOn(response.getCard().getPointExpireOn());
			card.setPoints(response.getCard().getPoints());
			card.setStatus(response.getCard().getStatus());
		}
		
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

	public static void main(String[] args) {
		IssuerSpecification specification = new IssuerSpecification();
		/*
		 * {"merchantId":"10000000001","txnDate":"2015-08-17T18:18:21.305+08:00",
		 * "terminalId":"10000000001","stan":"201508101781",
		 * "cardNumber":"8888880018904568",
		 * "cardHolder":{"firstName":"Manoj","lastName"
		 * :"Singh","gender":"M","dateOfBirth"
		 * :"1982-06-29","phoneNumber":"9810403543"
		 * ,"email":"m.manojsingh@gmail.com"
		 * ,"address":"Noida","city":"Noida","state"
		 * :"UP","zip":"201301","country":"India"}}
		 */
		TxnMsg txnMsg = new TxnMsg();
		txnMsg.setMid("10000000001");
		txnMsg.setTid("10000000001");
		txnMsg.setDtTransaction(new Date());
		txnMsg.setStan("201508101781");
		txnMsg.setPan("8888880018904568");
		txnMsg.setNewCardNumber("8888880018904000");
		txnMsg.setTxnPoints(34);
		
		Cardholder cardholder = new Cardholder();
		txnMsg.setCardholder(cardholder);
		cardholder.setFirstName("Manoj");
		cardholder.setLastName("Singh");
		cardholder.setGender("M");
		cardholder.setPhoneNumber("9810123123");
		cardholder.setEmail("m.manojsingh@gmail.com");
		cardholder.setAddress("Sector53");
		cardholder.setCity("Noida");
		cardholder.setZip("201301");
		cardholder.setState("UP");
		cardholder.setCountry("India");
		
		txnMsg.setTransactionType(TransactionType.DACTN.name());
		System.out.println(specification.createRequest(txnMsg).toString());
	}
}
