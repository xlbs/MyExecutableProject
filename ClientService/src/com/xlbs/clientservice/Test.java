package com.xlbs.clientservice;

import com.xlbs.clientservice.test.GetuiServiceTest;
import com.xlbs.clientservice.test.QueryServiceTest;

public class Test {

    public static void main(String[] args) {

        System.out.println("个推服务--"+GetuiServiceTest.test());
        System.out.println("查询服务--"+QueryServiceTest.test());

    }

}
