package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.LoginService;

@Controller
public class LoginController {
	@Autowired 
	LoginService service;
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(ModelMap model) {
		return "login";
		
	}
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String wlecome(ModelMap model,@RequestParam String name,@RequestParam String password) 
	{
		boolean isValidUser=service.validateUserLogin(name, password);
		if(!isValidUser) {
			System.out.println("Inside login");
			model.put("errormessage", "Invalid Credentials");
			return "login";
		}
		model.put("name",name);
		model.put("password","password");
		System.out.println("Inside welcome");
		return "welcome";
	}
	
	
	
}
