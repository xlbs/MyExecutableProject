package com.xlbs.clientservice.test;

import com.xlbs.clientservice.daofactory.DAOFactory;
import com.xlbs.clientservice.utils.JSONUtils;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class GetuiServiceTest {

    public static String test(){
        String result = null;
        try {
            Map<Object, Object> resMap = new LinkedHashMap<>();
            resMap.put("eventDate", "11");
            resMap.put("eventDateExact","22");
            resMap.put("equiName", "33");
            resMap.put("equiNo", "44");
            resMap.put("eventClassNum", "55");
            resMap.put("eventClass", "66");
            resMap.put("eventType", "77");
            resMap.put("eventProcess", "88");
            resMap.put("eventDescribe", "99");
            Set<String> clientIdSet = new HashSet<>();
            clientIdSet.add("6df726f583e01b5b423c161c18f86b17");
            result = DAOFactory.getInstance().getGetuiPushI().pushTransmissionMsgToList("aa","bb", JSONUtils.serialize(resMap),clientIdSet);
            result = "测试成功："+result;
        } catch (Exception e) {
            e.printStackTrace();
            result = "测试失败："+e.getMessage();
        }
        return result;

    }

}
