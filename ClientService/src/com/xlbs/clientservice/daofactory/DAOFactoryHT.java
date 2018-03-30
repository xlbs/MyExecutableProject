package com.xlbs.clientservice.daofactory;

import com.xlbs.clientservice.service.ServiceOpen;
import com.xlbs.serviceinterface.serviceinterface.I_GetuiPushInterface;
import com.xlbs.serviceinterface.serviceinterface.I_QueryInterface;
import org.apache.log4j.Logger;

public class DAOFactoryHT {

    protected final Logger log = Logger.getLogger(DAOFactoryHT.class);

    private static DAOFactoryHT instance = new DAOFactoryHT();

    private I_QueryInterface queryInterface;

    private I_GetuiPushInterface getuiPushI;

    public static DAOFactoryHT getInstance() {
        return instance;
    }

    private DAOFactoryHT(){
        log.info("DAO初始化....");
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
