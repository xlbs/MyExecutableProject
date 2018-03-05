package com.xlbs.clientservice.test;

import com.xlbs.clientservice.daofactory.DAOFactory;

import java.util.List;
import java.util.Map;

public class QueryServiceTest {

    public static String test(){
        String result = null;
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT * FROM `u_unit`");
            List<Map> list = DAOFactory.getInstance().getQueryInterface().querySqlReturnListMap(sb.toString());
            result = "测试成功："+list.toString();
        } catch (RuntimeException e) {
            e.printStackTrace();
            result = "测试失败："+e.getMessage();
        }
        return result;
    }
}
