package com.xlbs.getuiservice.config;

import com.xlbs.getuiservice.utils.XmlUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"ip","port","clusterName", "clusterNode"})
@XmlRootElement(name = "GetuiPushService")
public class GetuiPushService {
	
	private String ip="localhost";
	
	private String port="1001";
	
	private String clusterName="test";
	
	private String clusterNode="akka.tcp://test@localhost:1001";
	
	public static GetuiPushService getConfig(){
		try{
			return (GetuiPushService) XmlUtils.convertXmlFileToObject(GetuiPushService.class, "config/GetuiPushService.xml");
		}catch (Exception e){
			GetuiPushService cc = new GetuiPushService();
			XmlUtils.convertToXml(cc, "config/GetuiPushService.xml");
			return cc;
		}
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

	public String getClusterNode() {
		return clusterNode;
	}

	public void setClusterNode(String clusterNode) {
		this.clusterNode = clusterNode;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	
	
	

}
