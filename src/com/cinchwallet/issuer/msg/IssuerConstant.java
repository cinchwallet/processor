package com.cinchwallet.issuer.msg;

public class IssuerConstant {

	public enum TxnType {
		SERVERSTATUS("serverstatus"), CARDDETAIL("carddetail"), USEPROFILE("userprofile"), REGISTERUSER("registeruser"), EARNPOINT(
				"earnpoint"), BURNPOINT("burnpoint"), ADDPOINT("addpoint"), DEACTIVATE("deactivate"), REISSUECARD(
				"reissuecard"), UPDATEPROFILE("updateprofile");

		private String endPoint;

		private TxnType(String endUrl) {
			endPoint = endUrl;
		}

		public String getEndPoint() {
			return endPoint;
		}

	}

}
