package com.xlbs.clientservice;

import com.xlbs.clientservice.daofactory.DAOFactory;
import com.xlbs.clientservice.test.GetuiServiceTest;
import com.xlbs.clientservice.test.QueryServiceTest;

import java.util.HashSet;
import java.util.Set;

public class Test {

    public static void main(String[] args) {

//        System.out.println("个推服务--"+GetuiServiceTest.test());
//        System.out.println("查询服务--"+QueryServiceTest.test());
        Set<String> set = new HashSet<>();
        try {
            String res = DAOFactory.getInstance().getGetuiPushI().pushNotifyMsgToList("", "", set) ;
            System.out.println(res);
        }catch (Exception e){
            e.printStackTrace();;

        }

    }

}
