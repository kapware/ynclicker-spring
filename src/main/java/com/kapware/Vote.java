package com.kapware;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Vote {
	private String token;
	private VoteResponse response;

	@Id
	@GeneratedValue
	private String eventId;

	Vote() {
	}

	Vote(String token, VoteResponse response) {
		this.token = token;
		this.response = response;
	}

	public String getToken() {
		return token;
	}

	public VoteResponse getResponse() {
		return response;
	}

	public String getEventId() {
		return eventId;
	}

	enum VoteResponse {
		YES, NO
	}
}
