package com.test.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

// https://zgundam.tistory.com/52?category=430446 - 참고
// extends SimpleUrlLogoutSuccessHandler 1가지 가능
public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
	
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
		
		super.setDefaultTargetUrl("/logouts");
        
		super.onLogoutSuccess(request, response, authentication);
    }
}
