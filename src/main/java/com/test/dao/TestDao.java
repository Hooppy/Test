package com.test.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.test.model.Ktoto;
import com.test.model.TestModel;

@Repository
public class TestDao {
	
	@Autowired
	TestModel testModel;
	
	@Autowired
	Ktoto ktoto;
	
	@Autowired
	Ktoto ktoto2;
	
	@Autowired
	SqlSession sqlSession;
	
	List<TestModel> modelList = new ArrayList<TestModel>();
	
	//@Autowired
	//private ShaPasswordEncoder passwordEncoder;
	
	@Autowired
	private PasswordEncoder standardEncoder;
	
	private static String namespace = "com.test.userMapper";
	
	public TestModel read(String username) throws Exception{
		
		try {
			testModel = sqlSession.selectOne(namespace+".read" , username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return testModel;
	}
	
	public Ktoto pdf(String username) throws Exception{
		try {
			ktoto = sqlSession.selectOne(namespace + ".pdf", username);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ktoto;
	}
	
	public boolean isExist(String username) {
		int count = sqlSession.selectOne(namespace+".exist" , username);
		
		if(count > 0) {
			return true;
		}else {
			return false;
		}
	}
	
	public int cnt(String username) {
		
		int count = sqlSession.selectOne(namespace+".count" , username);
		
		return count;
	}
	
	public void create(String username, String password, String bldname, String addr, String name){
		
		// 비밀번호 암호화
		// String encPassword = passwordEncoder.encodePassword(password, username);
		String encPassword = standardEncoder.encode(password);
		
		testModel.setUsername(username);
		testModel.setPassword(encPassword);
		testModel.setBldname(bldname);
		testModel.setAddr(addr);
		testModel.setName(name);
		
		sqlSession.insert(namespace+".create" , testModel);
	}
	
	public void success(String username) {
		sqlSession.update(namespace+".success", username);
	}
	
	public void update(String username) {
		
		sqlSession.update(namespace+".update" , username);
	}

	public void delete(String username, String password) {
		
		testModel.setUsername(username);
		testModel.setPassword(password);
		
		System.out.println(username + " " + password);
		sqlSession.delete(namespace+".delete" , testModel);
	}
	
}
