package com.xlbs.getuiservice.apppush;

import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.xlbs.getuiservice.utils.ConstantParamets;


public class TransmissionMsg {

    public static TransmissionTemplate getTemplate(String titile, String body, String transmissionContent) {
        TransmissionTemplate template = new TransmissionTemplate();
        // 设置APPID与APPKEY
        template.setAppId(ConstantParamets.appId);
        template.setAppkey(ConstantParamets.appKey);
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(2);
        template.setTransmissionContent(transmissionContent);

        //IOS使用透传
        APNPayload payload = new APNPayload();
        payload.setContentAvailable(1);
        payload.setSound("default");
        payload.setAlertMsg(getDictionaryAlertMsg(titile,body));
        template.setAPNInfo(payload);
        return template;
    }


    private static APNPayload.DictionaryAlertMsg getDictionaryAlertMsg(String titile, String body){
        APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
        alertMsg.setTitle(titile);
        alertMsg.setBody(body);
        return alertMsg;
    }


}
