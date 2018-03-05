package com.xlbs.clientservice.daofactory;

import com.xlbs.serviceinterface.serviceinterface.I_GetuiPushInterface;
import com.xlbs.serviceinterface.serviceinterface.I_QueryInterface;
import com.xlbs.clientservice.service.ServiceOpen;
import org.apache.log4j.Logger;

public class DAOFactory {
	
	protected final Logger log = Logger.getLogger(DAOFactory.class);
	
	private static DAOFactory instance = new DAOFactory();
	
	private I_QueryInterface queryInterface;

	private I_GetuiPushInterface getuiPushI;
	
	public static DAOFactory getInstance() {
		return instance;
	}
	
	private DAOFactory(){
		log.info("DAO初始化....");
		ServiceOpen.getInstance().initActorSystem(null);
	}
	
	public I_QueryInterface getQueryInterface() {
		try {
			if (queryInterface == null)
				queryInterface = ServiceOpen.getInstance().getBean(I_QueryInterface.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return queryInterface;
	}

	public I_GetuiPushInterface getGetuiPushI() {
		try {
			if(getuiPushI == null){
				getuiPushI = ServiceOpen.getInstance().getBean(I_GetuiPushInterface.class);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return getuiPushI;
	}

}
