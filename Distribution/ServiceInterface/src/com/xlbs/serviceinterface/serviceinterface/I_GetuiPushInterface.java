package com.xlbs.serviceinterface.serviceinterface;

import java.util.Set;

public interface I_GetuiPushInterface {

    /**
     *指定列表用户(安卓透传，苹果透传)推送
     * @param titile 标题内容
     * @param body 文本内容
     * @param transmissionContent 透传内容
     * @param clientIdSet 接收用户集合
     * @return
     * @throws Exception
     */
    public String pushTransmissionMsgToList(String titile, String body, String transmissionContent, Set<String> clientIdSet) throws Exception;

    /**
     *指定列表用户(安卓通知，苹果透传)推送
     * @param titile 标题内容
     * @param body 文本内容
     * @param clientIdSet 接收用户集合
     * @return
     * @throws Exception
     */
    public String pushNotifyMsgToList(String titile, String body, Set<String> clientIdSet) throws Exception;


}
