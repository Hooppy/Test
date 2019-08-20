package com.test.model;

import org.springframework.stereotype.Repository;

@Repository
public class Ktoto{
	
	private String username;
	private String code;
	private String totoname;
	private String totoaddr;
	private String contractor;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTotoname() {
		return totoname;
	}
	public void setTotoname(String totoname) {
		this.totoname = totoname;
	}
	public String getTotoaddr() {
		return totoaddr;
	}
	public void setTotoaddr(String totoaddr) {
		this.totoaddr = totoaddr;
	}
	public String getContractor() {
		return contractor;
	}
	public void setContractor(String contractor) {
		this.contractor = contractor;
	}
}
