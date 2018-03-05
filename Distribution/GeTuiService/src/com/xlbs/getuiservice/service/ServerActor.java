package com.xlbs.getuiservice.service;

import akka.actor.UntypedActor;
import com.xlbs.serviceinterface.messageentity.Event;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ServerActor extends UntypedActor {

    private Map<String, Object> proxyBeans;

    public ServerActor(Map<Class<?>, Object> beans){
        proxyBeans = new HashMap<String, Object>();
        for (Class<?> inface : beans.keySet()) {
            proxyBeans.put(inface.getName(), beans.get(inface));
        }
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Event.CallMethod) {
            Event.CallMethod event = (Event.CallMethod) message;
            Object bean = proxyBeans.get(event.getBeanName());
            Object[] params = event.getParams();
            Class<?>[] paramerTypes = event.getParamTypes();
            try {
                Method method = bean.getClass().getMethod(event.getMethodName(), paramerTypes);
                Object o = method.invoke(bean, params);
                if (o != null) {
                    getSender().tell(o, getSelf());
                } else {
                    getSender().tell(new NullPointerException("noResult"),getSelf());
                }
            } catch (Exception e) {
                getSender().tell(e, getSelf());
            }
        }

    }

}
