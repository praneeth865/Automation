package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class LoginService {
	public boolean validateUserLogin(String userId,String password) {
		return !(userId.equals("null") && password.equals("null"));
	}

}
