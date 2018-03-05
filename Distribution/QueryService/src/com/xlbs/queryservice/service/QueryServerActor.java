package com.xlbs.queryservice.service;

import java.lang.reflect.Method;
import java.util.Map;

import akka.actor.UntypedActor;
import com.xlbs.serviceinterface.messageentity.Event;
import com.xlbs.serviceinterface.messageentity.Event.CallMethod;


public class QueryServerActor extends UntypedActor {

	private Map<String, Object> proxyBeans;

	public QueryServerActor(Map<String, Object> proxyBeans) {
		this.proxyBeans = proxyBeans;
	}

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof Event.CallMethod) {
			CallMethod event = (CallMethod) message;
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
