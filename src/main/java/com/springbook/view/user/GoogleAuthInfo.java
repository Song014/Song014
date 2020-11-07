package com.springbook.view.user;

public class GoogleAuthInfo {
	private String clientId;
	private String clientSecret;

	public GoogleAuthInfo(String clientId, String clientSecret) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}

	public String getClientId() {
		return clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}
}
