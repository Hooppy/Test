package com.test.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public class TestModel implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String bldname;
	private String addr;
	private String name;
	private String code;
	private int cnt;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	
	public String getBldname() {
		return bldname;
	}
	public void setBldname(String bldname) {
		this.bldname = bldname;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	// 계정이 갖고 있는 권한 목록
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("USER"));
		if (username != null && username.equals("admin")) {
			authorities.add(new SimpleGrantedAuthority("USER_MANAGER"));
		}
		return authorities;
	}
	
	// 계정 만료 여부
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	
	// 계정 잠김(Lock) 여부
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	
	// 계정암호 만료 여부
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	
	// 사용 가능 계정 여부
	public boolean isEnabled() {
		return true;
	}
}
