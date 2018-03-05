package com.xlbs.jedis.utils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * IP和端口
 * @author xiongyue
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ipport")
public class IP {

	private String ip;
	private String port;
	private String pwd;
	@XmlAttribute
	private String desc;
	
	
	public IP() {
		super();
	}
	public IP(String ip, String port,String pwd) {
		super();
		this.ip = ip;
		this.port = port;
		this.pwd = pwd;
	}
	
	@Override
	public String toString() {
		return "IP [ip=" + ip + ", port=" + port + "]"+desc;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
}
