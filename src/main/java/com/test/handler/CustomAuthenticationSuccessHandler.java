package com.test.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.test.dao.TestDao;

// https://zgundam.tistory.com/52?category=430446 - 참고
// extends SimpleUrlAuthenticationSuccessHandler, SavedRequestAwareAuthenticationSuccessHandler 2가지 가능
// implements AuthenticationSuccessHandler 1가지 가능
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	@Autowired
	TestDao testDao;
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	// 요청정보(request), 응답정보(response), 인증정보(authentication)를 가져온다.
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		
		Authentication authentication) throws IOException, ServletException {
		
		HttpSession httpSession = request.getSession();
		
		String username = request.getParameter("username");
		
		//Session에 저장해서 Controller에서 활용
		httpSession.setAttribute("username", request.getParameter("username"));
		httpSession.setAttribute("password", request.getParameter("password"));
		
		RequestCache requestCache = new HttpSessionRequestCache();
		
		// spring-security 타기 직전 URL 값 저장
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		
		// cnt 초기화
		testDao.success(username);
				
		// spring-security 타기 직전 URL 값 저장해서 Redirect (login 성공시, login 화면 전에 입력한 url로 이동)
		String url = savedRequest.getRedirectUrl();
		
		redirectStrategy.sendRedirect(request, response, url);
		
		//super.onAuthenticationSuccess(request, response, authentication);
	}
}
