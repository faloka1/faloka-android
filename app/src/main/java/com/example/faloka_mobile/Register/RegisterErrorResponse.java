package com.example.faloka_mobile.Register;

import com.example.faloka_mobile.Model.RegisterError;

public class RegisterErrorResponse {
	private RegisterError error;

	public void setError(RegisterError error){
		this.error = error;
	}
	public RegisterError getError(){
		return error;
	}
}
