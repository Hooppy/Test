package com.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController{
	
	@RequestMapping
	public String Error(ModelMap model) {
		
		model.addAttribute("errormsg", "HTTP404");
		
		return "error";
	}
	
}
