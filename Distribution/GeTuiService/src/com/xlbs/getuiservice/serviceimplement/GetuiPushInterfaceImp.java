package com.xlbs.getuiservice.serviceimplement;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.xlbs.getuiservice.apppush.TransmissionMsg;
import com.xlbs.getuiservice.utils.ConstantParamets;
import com.xlbs.serviceinterface.serviceinterface.I_GetuiPushInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GetuiPushInterfaceImp implements I_GetuiPushInterface {

    @Override
    public String pushTransmissionMsgToList(String titile, String body, String transmissionContent, Set<String> clientIdSet) {
        IPushResult result = null;
        try {
            if(!clientIdSet.isEmpty()){
                List<Target> CIDList = new ArrayList<>();
                for (String CId : clientIdSet) {
                    Target target = new Target();
                    target.setAppId(ConstantParamets.appId);
                    target.setClientId(CId);
                    CIDList.add(target);
                }
                TransmissionTemplate template = TransmissionMsg.getTemplate(titile,body,transmissionContent);
                ListMessage message = new ListMessage();
                message.setData(template);
                // 设置消息离线，并设置离线时间
                message.setOffline(true);
                // 离线有效时间，单位为毫秒，可选
                message.setOfflineExpireTime(24 * 1000 * 3600);
                IGtPush push = new IGtPush(ConstantParamets.host, ConstantParamets.appKey, ConstantParamets.masterSecret);
                String taskId = push.getContentId(message);
                result = push.pushMessageToList(taskId, CIDList);
            }else{
                return "推送失败：用户列表为空";
            }
        }catch (Exception e){
            return "推送失败："+e.getMessage();
        }
        return "推送成功："+result.getResponse().toString();
    }

    @Override
    public String pushNotifyMsgToList(String titile, String body, Set<String> clientIdSet) throws Exception {
        return null;
    }
}
