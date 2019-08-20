package com.test.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.access.AccessDeniedHandler;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	private String errorPage;
	
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		if(principal instanceof UserDetails) {
			String username = ((UserDetails) principal).getUsername();
			request.setAttribute("username", username);
		}
		request.setAttribute("errormsg", accessDeniedException);
		request.getRequestDispatcher(errorPage).forward(request, response);
	}
	
	// security-context.xml
	// bean id="accessDenied"의 property name="errorPage"의 value 값 들어옴
	public void setErrorPage(String errorPage) {
		if((errorPage != null) && !errorPage.startsWith("/")) {
			throw new IllegalArgumentException("errorPage must begin with '/'");
		}
		
		this.errorPage = errorPage;
	}
	
}
