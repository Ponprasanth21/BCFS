package com.bornfire.entity;

public class UserProfileResponse {

	private String Status;
	private UserProfile userProfile;

	public UserProfileResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserProfileResponse(String status) {
		super();
		Status = status;
	}

	public UserProfileResponse(String status, UserProfile userProfile) {
		super();
		Status = status;
		this.userProfile = userProfile;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}
	
	

}
