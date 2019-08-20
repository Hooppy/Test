package com.test.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.test.dao.TestDao;
import com.test.model.TestModel;

public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	TestModel testModel;
	
	@Autowired
	TestDao testDao;
	
	public TestModel loadUserByUsername(String username) throws UsernameNotFoundException {
		
		try {
			//디비 정보를 불러와 유저정보 조회
			testModel = testDao.read(username);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return testModel;
	}

}
