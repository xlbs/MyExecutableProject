package com.xlbs.getuiservice.config;


import com.xlbs.getuiservice.utils.XmlUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"appId","appKey","masterSecret", "host"})
@XmlRootElement(name = "GetuiInfoConfig")
public class GetuiInfoConfig {
	
	private String appId="N6OSTLVjkb9HQR5jRtvcR3";
	
	private String appKey="437vzeKGDV9zpRVnQpm9A5";
	
	private String masterSecret="c3oYmN6he47DOuaBRa24u3";
	
	private String host="http://sdk.open.api.igexin.com/apiex.htm";
	
	public static GetuiInfoConfig getConfig(){
		try{
			return (GetuiInfoConfig) XmlUtils.convertXmlFileToObject(GetuiInfoConfig.class, "config/GetuiInfoConfig.xml");
		}catch (Exception e){
			GetuiInfoConfig cc = new GetuiInfoConfig();
			XmlUtils.convertToXml(cc, "config/GetuiInfoConfig.xml");
			return cc;
		}
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getMasterSecret() {
		return masterSecret;
	}

	public void setMasterSecret(String masterSecret) {
		this.masterSecret = masterSecret;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
}
