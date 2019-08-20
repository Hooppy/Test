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
		//������ �Է��� ������ ���̵� ������θ����. (�α����� �������̵� ��������� ��´�)
		UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) authentication; 
		
		TestModel testModel = customUserDetailsService.loadUserByUsername(authToken.getName()); //UserDetailsService���� ���������� �ҷ��´�.
		
		if(!testDao.isExist(authToken.getName())) {
			throw new BadCredentialsException("Non Exist");
	    }else {
	    	
	    	if (testModel.getCnt() >= 5) {
	    		throw new DisabledException("Not Match Five");
		    }
	    	
	    	// ��ȣȭ ���� ���� authToken.getCredentials(id�Է°�)�� ��ȣȭ�� testModel.getPassword(DB�� ����� ��)�� ���Ͽ� ������ true
	    	if(!standardEncoder.matches((String) authToken.getCredentials(), testModel.getPassword())) {
	    		// Ʋ�� ��� cnt ���� 5�� Ʋ���� �α��� �Ұ�
	    		testDao.update(authToken.getName());
		    	
		    	throw new BadCredentialsException("Not Match Password");
		    }
	    	
	    	authorities = (List<GrantedAuthority>) testModel.getAuthorities();
	    	
	    }
		// DB ��ȸ�� pw�� �Է��� pw ��
	    /*if (!matchPassword(testModel.getPassword(), authToken.getCredentials())) {
	    	
	    	testDao.update(authToken.getName());
	    	
	    	throw new BadCredentialsException("Not Match Password");
	    }*/
	    
	    // DB ��ȣȭ password ��
	    /*System.out.println("�α��� : " + testModel.getPassword() + " " + (String) authToken.getCredentials() + " " + testModel.getUsername());
	    
	    if (!passwordEncoder.isPasswordValid(testModel.getPassword(), (String) authToken.getCredentials(), testModel.getUsername())) {
	    	testDao.update(authToken.getName());
	    	
	    	throw new BadCredentialsException("Not Match Password");
	    }*/
	    
	    // Session�� ��� ���� ID��
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
