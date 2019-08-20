package com.test.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.test.dao.TestDao;
import com.test.model.TestModel;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	TestDao testDao;

	// @Autowired
	// private ShaPasswordEncoder passwordEncoder;
	
	@Autowired
	private PasswordEncoder standardEncoder;
	
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		//유저가 입력한 정보를 이이디 비번으로만든다. (로그인한 유저아이디 비번정보를 담는다)
		UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) authentication; 
		
		TestModel testModel = customUserDetailsService.loadUserByUsername(authToken.getName()); //UserDetailsService에서 유저정보를 불러온다.
		
		if(!testDao.isExist(authToken.getName())) {
			throw new BadCredentialsException("Non Exist");
	    }else {
	    	
	    	if (testModel.getCnt() >= 5) {
	    		throw new DisabledException("Not Match Five");
		    }
	    	
	    	// 암호화 되지 않은 authToken.getCredentials(id입력값)과 암호화된 testModel.getPassword(DB에 저장된 값)을 비교하여 맞으면 true
	    	if(!standardEncoder.matches((String) authToken.getCredentials(), testModel.getPassword())) {
	    		// 틀릴 경우 cnt 증가 5번 틀리면 로그인 불가
	    		testDao.update(authToken.getName());
		    	
		    	throw new BadCredentialsException("Not Match Password");
		    }
	    	
	    	authorities = (List<GrantedAuthority>) testModel.getAuthorities();
	    	
	    }
		// DB 조회된 pw와 입력한 pw 비교
	    /*if (!matchPassword(testModel.getPassword(), authToken.getCredentials())) {
	    	
	    	testDao.update(authToken.getName());
	    	
	    	throw new BadCredentialsException("Not Match Password");
	    }*/
	    
	    // DB 암호화 password 비교
	    /*System.out.println("로그인 : " + testModel.getPassword() + " " + (String) authToken.getCredentials() + " " + testModel.getUsername());
	    
	    if (!passwordEncoder.isPasswordValid(testModel.getPassword(), (String) authToken.getCredentials(), testModel.getUsername())) {
	    	testDao.update(authToken.getName());
	    	
	    	throw new BadCredentialsException("Not Match Password");
	    }*/
	    
	    // Session에 담긴 유저 ID값
	    // System.out.println("Principal : " + authToken.getPrincipal());
	    // System.out.println("Authorities : " + authToken.getAuthorities());
	    // System.out.println("Name : " + authToken.getName());
	    
	    return new UsernamePasswordAuthenticationToken(testModel, null, authorities);
	}
	
	/*private boolean matchPassword(String password, Object credentials) {
		return password.equals(credentials);
	}*/
	
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
